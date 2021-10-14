package ru.gb.cpourse1.calcapp;

import android.os.Parcel;
import android.os.Parcelable;

public class ParcelData implements Parcelable {

    private String expressionParcelString;
    private String resultParcelString;

    ParcelData() {

    }

    ParcelData(String expressionParcelString, String resultParcelString) {
        this.setExpressionParcelString(expressionParcelString);// = parcelString;
        this.setResultParcelString(resultParcelString);// = resultParcelString;
    }

    public static final Creator<ParcelData> CREATOR = new Creator<ParcelData>() {
        @Override
        public ParcelData createFromParcel(Parcel in) {
            String expressionParcelString = in.readString();
            String resultParcelString = in.readString();
            return new ParcelData(expressionParcelString, resultParcelString);
        }

        @Override
        public ParcelData[] newArray(int size) {
            return new ParcelData[size];
        }
    };

    public String getExpressionParcelString() {
        return expressionParcelString;
    }

    public void setExpressionParcelString(String expressionParcelString) {
        this.expressionParcelString = expressionParcelString;
    }

    public String getResultParcelString() {
        return resultParcelString;
    }

    public void setResultParcelString(String resultParcelString) {
        this.resultParcelString = resultParcelString;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(expressionParcelString);
        dest.writeString(resultParcelString);
    }
}
