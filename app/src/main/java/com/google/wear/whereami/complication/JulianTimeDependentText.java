//package com.google.wear.whereami.complication;
//
//import android.content.res.Resources;
//import android.os.Parcel;
//import android.os.Parcelable;
//import android.support.wearable.complications.TimeDependentText;
//
//import androidx.annotation.NonNull;
//
//public class JulianTimeDependentText implements TimeDependentText {
//    public JulianTimeDependentText() {}
//
//    public static final Parcelable.Creator<JulianTimeDependentText> CREATOR
//            = new Parcelable.Creator<JulianTimeDependentText>() {
//        public JulianTimeDependentText createFromParcel(Parcel in) {
//            return new JulianTimeDependentText();
//        }
//
//        public JulianTimeDependentText[] newArray(int size) {
//            return new JulianTimeDependentText[size];
//        }
//    };
//
//    @NonNull
//    @Override
//    public CharSequence getTextAt(@NonNull Resources resources, long epochMillis) {
//        long beats = epochMillis / 864 + 244058750000L;
//        return (beats/100000) + "." + (beats%100000);
//    }
//
//    @Override
//    public long getNextChangeTime(long fromTime) {
//        long next = fromTime+864;
//        return next - next%864;
//    }
//
//    @Override
//    public boolean returnsSameText(long firstMillis, long secondMillis) {
//        return firstMillis/864 == secondMillis/864;
//    }
//
//    @Override
//    public void writeToParcel(@NonNull Parcel parcel, int i) {
//        // No state to serialize.
//        return;
//    }
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//}
