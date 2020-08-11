package tw.teng.pratice.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    private EditText editText;
    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editTest);
        Button btn = findViewById(R.id.button);
        textToSpeech = new TextToSpeech(this, this);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
 //               textToSpeech.speak("三加五等於" + max(3, 5) + "啦！笨蛋",
 //                       TextToSpeech.QUEUE_FLUSH, null, "ten");

                startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:0800")));
            }
        });
    }

    @Override
    public void onInit(int status) {
        Log.i("Ten", "status" + status);
    }

    public static int max(int a, int b) {
        return a + b;
    }

}

