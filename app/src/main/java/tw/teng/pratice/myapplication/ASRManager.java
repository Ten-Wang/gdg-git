package tw.teng.pratice.myapplication;

import android.util.Pair;

import java.util.List;

public abstract class ASRManager {
    private static final int STATUS_IDLE = 1;
    public static final int STATUS_RECORDING_START = STATUS_IDLE << 1;
    public static final int STATUS_SPEECH_START = STATUS_IDLE << 2;
    public static final int STATUS_SPEECH_END = STATUS_IDLE << 3;
    public static final int STATUS_FINISH = STATUS_IDLE << 4;

    abstract public void init();

    abstract public void listen();

    abstract public void stop();

    abstract public void addRecognitionListener(ASRListener listener);

    abstract public void removeRecognitionListener(ASRListener listener);

    abstract public int getVolume();

    public interface ASRListener {
        void onRecognitionResults(List<Pair<String, Float>> results);

        void onRecognitionError(int error);

        void onVolumeUpdate(float volume);
    }

}
