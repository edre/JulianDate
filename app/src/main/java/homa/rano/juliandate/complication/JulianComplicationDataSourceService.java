package homa.rano.juliandate.complication;

import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.wear.watchface.complications.data.ComplicationData;
import androidx.wear.watchface.complications.data.ComplicationType;
import androidx.wear.watchface.complications.data.LongTextComplicationData;
import androidx.wear.watchface.complications.data.PlainComplicationText;
import androidx.wear.watchface.complications.data.ShortTextComplicationData;
import androidx.wear.watchface.complications.datasource.ComplicationDataSourceService;
import androidx.wear.watchface.complications.datasource.ComplicationRequest;

import java.time.Instant;

public class JulianComplicationDataSourceService extends ComplicationDataSourceService {
    public static String getJulianDate(@NonNull Instant instant) {
        long beats = instant.toEpochMilli() / 864 + 244058750000L;
        return (beats / 100000) + "\n." + (beats % 100000);
    }

    @Override
    public void onComplicationRequest(@NonNull ComplicationRequest request, @NonNull ComplicationRequestListener listener) {
        try {
            listener.onComplicationData(getComplicationData(request.getComplicationType()));
        } catch (RemoteException exception) {}
    }

    private static ComplicationData getComplicationData(@NonNull ComplicationType complicationType) {
        String jd = getJulianDate(Instant.now());
        PlainComplicationText label = new PlainComplicationText.Builder("Julian Date").build();
        switch (complicationType) {
            case SHORT_TEXT:
                return new ShortTextComplicationData.Builder(
                        new PlainComplicationText.Builder(jd.substring(8,12)).build(),label).build();
            case LONG_TEXT:
            default:
                return new LongTextComplicationData.Builder(
                        new PlainComplicationText.Builder(jd).build(),label).build();
        }
    }

    @Nullable
    @Override
    public ComplicationData getPreviewData(@NonNull ComplicationType complicationType) {
        return getComplicationData(complicationType);
    }
}
