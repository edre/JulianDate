package homa.rano.juliandate;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.time.Instant;

public class JulianActivity extends Activity {
    private Handler handler;
    private Runnable runnable;

    // TODO: dedup implementations
    public static String getJulianDate(@NonNull Instant instant) {
        long beats = instant.toEpochMilli() / 864 + 244058750000L;
        return (beats / 100000) + "\n." + (beats % 100000);
    }

    private void redraw() {
        TextView textView = findViewById(R.id.text);
        // TODO: add local time
        textView.setText(getJulianDate(Instant.now()));
    }

    private void scheduleNext() {
        // Define the code block to be executed
        runnable = new Runnable() {
            @Override
            public void run() {
                redraw();
                // Repeat this the same runnable code block again another 2 seconds
                handler.postDelayed(runnable, 864);
            }
        };
        // Start the initial runnable task by posting through the handler
        // TODO: calculate remaining time until update
        handler.postDelayed(runnable, 864);
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
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        redraw();
        scheduleNext();
    }
}
