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
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Handler handler = new Handler();
    private final int UPDATE_INTERVAL = 1000; // 1 sekunti

    // Kohdeaika: 28.11.2025 17:30
    private final String TARGET_DATE = "28.11.2025 17:30";
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());

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

    // Runnable päivittää jäljellä olevan ajan sekunnin välein
    private Runnable updateRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                Date now = new Date();
                Date target = sdf.parse(TARGET_DATE);

                if (target != null) {
                    long diffInMillis = target.getTime() - now.getTime();

                    if (diffInMillis > 0) {
                        long days = TimeUnit.MILLISECONDS.toDays(diffInMillis);
                        long hours = TimeUnit.MILLISECONDS.toHours(diffInMillis) % 24;
                        long minutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis) % 60;
                        long seconds = TimeUnit.MILLISECONDS.toSeconds(diffInMillis) % 60;

                        String remaining = String.format(
                                Locale.getDefault(),
                                "%d päivää %02d:%02d:%02d",
                                days, hours, minutes, seconds
                        );

                        textView.setText("Buffaan aikaa jäljellä: " + remaining + "\nMenossa mukana myös Pandora!");
                    } else {
                        textView.setText("Aika saavutettu!");
                    }
                } else {
                    textView.setText("Virhe: kohdeaikaa ei voitu laskea");
                }
            } catch (Exception e) {
                textView.setText("Virhe ajassa: " + e.getMessage());
            }

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
