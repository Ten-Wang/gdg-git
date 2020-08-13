package tw.teng.pratice.myapplication;

import android.Manifest;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements
        ASRManager.ASRListener, TextToSpeech.OnInitListener {

    private static final String TAG = "MainActivity";
    private EditText editText = null;
    private TextToSpeech textToSpeech = null;
    protected ASRManager mRecognitionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        textToSpeech = new TextToSpeech(this, this);
        textToSpeech.setLanguage(Locale.TRADITIONAL_CHINESE);
        findViewById(R.id.btn_talk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakNow(v);
            }
        });

        mRecognitionManager = RecognitionManager.getInstance(this);
        mRecognitionManager.init();

        mRecognitionManager.addRecognitionListener(this);
        findViewById(R.id.btn_listener).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withContext(getApplicationContext())
                        .withPermission(Manifest.permission.RECORD_AUDIO)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                mRecognitionManager.listen();
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {
                                Toast.makeText(getApplicationContext(), R.string.please_open_mic_permission, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                            }
                        }).check();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        textToSpeech.shutdown();
    }

    public void speakNow(View v) {
        Log.i(TAG, "speakNow [" + editText.getText().toString() + "]");
        textToSpeech.speak(editText.getText().toString(), TextToSpeech.QUEUE_FLUSH, null, "test");
    }

    public void onInit(int status) {
        Log.i(TAG, "onInit [" + status + "]");
    }

    @Override
    public void onRecognitionResults(List<Pair<String, Float>> results) {
        textToSpeech.setLanguage(Locale.TRADITIONAL_CHINESE);
        textToSpeech.speak(results.get(0).first, TextToSpeech.QUEUE_FLUSH, null, "test");
    }

    @Override
    public void onRecognitionError(int error) {

    }

    @Override
    public void onVolumeUpdate(float volume) {

    }
}
