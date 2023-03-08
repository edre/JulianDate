//package com.google.wear.whereami.complication;
//
//
//import androidx.annotation.NonNull;
//import android.content.res.Resources;
//import android.support.wearable.complications.TimeDependentText;
//import android.util.Log;
//
//import androidx.wear.watchface.complications.data.ComplicationText;
//
//import java.time.Duration;
//import java.time.Instant;
//
//public class JulianComplicationText implements ComplicationText {
//    public JulianComplicationText() {
//        Log.w("xxx", "Julian constructor");
//    }
//
//    @NonNull
//    @Override
//    public CharSequence getTextAt(@NonNull Resources resources, @NonNull Instant instant) {
//        Log.w("xxx", "Julian getTextAt");
//        return getJulianDate(instant);
//    }
//
//    public static CharSequence getJulianDate(@NonNull Instant instant) {
//        long beats = instant.toEpochMilli() / 864 + 244058750000L;
//        return (beats / 100000) + "\n." + (beats % 100000);
//    }
//
//    @NonNull
//    @Override
//    public Instant getNextChangeTime(@NonNull Instant instant) {
//        return instant.plusMillis(864);
//    }
//    //long next = fromTime+864;
////        return next - next%864;
//
//    @Override
//    public boolean isAlwaysEmpty() {
//        return false;
//    }
//
//    @Override
//    public boolean returnsSameText(@NonNull Instant instant1, @NonNull Instant instant2) {
//        return Duration.between(instant1, instant2).abs().toMillis() < 864;
//    }
//
//    @Override
//    public boolean isPlaceholder() {
//        return false;
//    }
//
//    //@NonNull
//    @Override
//    public TimeDependentText getTimeDependentText() {
//        Log.w("xxx", "Julian getTimeDependentText");
//        //return new JulianTimeDependentText();
//        return null;
//    }
//
//    @Override
//    public android.support.wearable.complications.ComplicationText toWireComplicationText() {
//        Log.w("xxx", "Julian toWireComplicationText");
//        return null;//return new android.support.wearable.complications.ComplicationText.plainText(getJulianDate(Instant.now()));
//    }
//}