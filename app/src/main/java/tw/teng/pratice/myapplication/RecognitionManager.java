package tw.teng.pratice.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;

public class RecognitionManager extends ASRManager implements RecognitionListener {
    private static final String TAG = RecognitionManager.class.getSimpleName();

    private static RecognitionManager sInstance;

    private final SpeechRecognizer mSpeechRecognizer;
    private final Intent mRecognizerIntent;
    private final HashSet<ASRListener> mRecognitionListeners = new HashSet<>();

    private boolean mListening;

    private RecognitionManager(Context context) {
        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(context);
        mSpeechRecognizer.setRecognitionListener(this);
        mRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.TRADITIONAL_CHINESE.toString());
    }

    public static synchronized RecognitionManager getInstance(Context context) {
        if (sInstance == null) {
            context = context.getApplicationContext();
            sInstance = new RecognitionManager(context);
        }
        return sInstance;
    }

    @Override
    public void init() {

    }

    public void listen() {
        mSpeechRecognizer.startListening(mRecognizerIntent);
    }

    public void stop() {
        mSpeechRecognizer.cancel();
        mListening = false;
    }

    @Override
    public void addRecognitionListener(ASRListener listener) {
        mRecognitionListeners.add(listener);
    }

    @Override
    public void removeRecognitionListener(ASRListener listener) {
        mRecognitionListeners.remove(listener);
    }

    @Override
    public int getVolume() {
        return 0;
    }

    @Override
    public void onReadyForSpeech(Bundle params) {
        mListening = true;
        Log.d(TAG, "ready for speech");
    }

    @Override
    public void onBeginningOfSpeech() {
        Log.d(TAG, "beginning of speech");
    }

    @Override
    public void onRmsChanged(float rmsdB) {
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
    }

    @Override
    public void onEndOfSpeech() {
        Log.d(TAG, "end of speech");
    }

    @Override
    public void onError(int error) {
        Log.d(TAG, "onError");
        if (mListening) {
            mListening = false;
            for (ASRListener listener : mRecognitionListeners) {
                listener.onRecognitionError(error);
            }

            final String text = String.format(Locale.ROOT, "error: %d", error);
            Log.e(TAG, text);
        }
    }

    @Override
    public void onResults(Bundle results) {
        Log.d(TAG, "onResults");
        mListening = false;
        final ArrayList<String> texts =
                results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        final float[] scores = results.getFloatArray(SpeechRecognizer.CONFIDENCE_SCORES);
        final ArrayList<Pair<String, Float>> pairs = new ArrayList<>();
        if (texts != null && scores != null) {
            for (int i = 0; i < scores.length; i++) {
                final String text = texts.get(i);
                final float score = scores[i];
                final Pair<String, Float> pair = Pair.create(text, score);
                pairs.add(pair);
            }
        }
        for (ASRListener listener : mRecognitionListeners) {
            listener.onRecognitionResults(pairs);
        }

        if (scores != null) {
            final String text = String.format(Locale.ROOT, "%d results", scores.length);
            Log.d(TAG, text);
        }
    }

    @Override
    public void onPartialResults(Bundle partialResults) {
    }

    @Override
    public void onEvent(int eventType, Bundle params) {
    }

}
