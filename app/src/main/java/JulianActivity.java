package homa.rano.juliandate;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_TIME;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

public class JulianActivity extends Activity {
    private Handler handler;

    // TODO: dedup implementations
    public static String getJulianDate(@NonNull Instant instant) {
        long beats = instant.toEpochMilli() / 864 + 244058750000L;
        return (beats / 100000) + "\n." + (beats % 100000);
    }

    private void redraw() {
        TextView textView = findViewById(R.id.text);
        String isoTime = LocalDate.now().format(ISO_LOCAL_DATE) + "\n" +
                LocalTime.now().format(ISO_LOCAL_TIME).substring(0,8);
        textView.setText("\n" + isoTime + "\n\n" + getJulianDate(Instant.now()));
    }

    private void scheduleRepeating(long intervalMillis) {
        // Define the code block to be executed
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                redraw();
                // Repeat the same runnable code block at the start of the next interval.
                long next = (SystemClock.uptimeMillis()/intervalMillis + 1) * intervalMillis;
                handler.postAtTime(this, next);
            }
        };
        // Start the initial runnable task.
        runnable.run();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.julian_activity);
        handler = new Handler();
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(null);
    }

    @Override
    public void onResume() {
        super.onResume();
        scheduleRepeating(864);
        scheduleRepeating(1000);
    }
}
