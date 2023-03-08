package homa.rano.juliandate;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.time.Instant;

//import homa.rano.juliandate.R;

public class JulianActivity extends Activity {
    // TODO: dedup implementations
    public static String getJulianDate(@NonNull Instant instant) {
        long beats = instant.toEpochMilli() / 864 + 244058750000L;
        return (beats / 100000) + "\n." + (beats % 100000);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.julian_activity);
    }

    public void onResume() {
        super.onResume();
        TextView textView = findViewById(R.id.text);
        textView.setText(getJulianDate(Instant.now()));
    }
}
