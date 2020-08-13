package tw.teng.pratice.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    private EditText editText;
    private TextToSpeech textToSpeech;

    private Handler handler;
    private Handler handler2;

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


        handler.post(runnable);
    }

    @Override
    public void onInit(int status) {
        Log.i("Ten", "status" + status);
    }

    public static int max(int a, int b) {
        return a + b;
    }


    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            HttpURLConnection connection = null;
            try {
                URL url = new URL("https://www.xxx.com/");
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");//設定訪問方式為“GET”
                connection.setConnectTimeout(8000);//設定連線伺服器超時時間為8秒
                connection.setReadTimeout(8000);//設定讀取伺服器資料超時時間為8秒
                if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                    //從伺服器獲取響應並把響應資料轉為字串列印
                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while (null != (line = reader.readLine())) {
                        response.append(line);
                    }
                    Log.d("", response.toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (null!= connection) {
                    connection.disconnect();
                }
            }
        }
    };

    Thread thread = new Thread(){
        @Override
        public void run() {
            super.run();
            Log.i("Ten","Thread 被觸發了!");
        }
    };

}

