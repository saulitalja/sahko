package com.example.sahko;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Handler handler = new Handler();
    private final int UPDATE_INTERVAL = 1000; // 1 sekunti

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Luodaan LinearLayout taustaksi
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        layout.setBackgroundColor(Color.BLACK); // tumma tausta

        // Luodaan TextView
        textView = new TextView(this);
        textView.setTextSize(36f); // iso fontti
        textView.setTextColor(Color.WHITE); // valkoinen teksti
        layout.addView(textView);

        setContentView(layout);

        // Käynnistetään aika-päivitys
        handler.post(updateRunnable);
    }

    // Runnable päivittää kellon ajan sekunnin välein
    private Runnable updateRunnable = new Runnable() {
        @Override
        public void run() {
            String currentTime = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault())
                    .format(new Date());
            textView.setText("Aika on nyt: " + currentTime);

            // Asetetaan uudelleen 1 sekunnin kuluttua
            handler.postDelayed(this, UPDATE_INTERVAL);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(updateRunnable); // pysäytetään päivitys
    }
}
