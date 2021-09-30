package ru.gb.cpourse1.calcapp;

import android.os.Parcel;
import android.os.Parcelable;

public class ParcelData implements Parcelable {
    private String parcelString;

    public ParcelData() {

    }

    public ParcelData(Parcel in) {
        parcelString = in.readString();
    }

    public static final Creator<ParcelData> CREATOR = new Creator<ParcelData>() {
        @Override
        public ParcelData createFromParcel(Parcel in) {
            return new ParcelData(in);
        }

        @Override
        public ParcelData[] newArray(int size) {
            return new ParcelData[size];
        }
    };

    public String getParcelString() {
        return parcelString;
    }

    public void setParcelString(String parcelString) {
        this.parcelString = parcelString;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(parcelString);
    }
}
