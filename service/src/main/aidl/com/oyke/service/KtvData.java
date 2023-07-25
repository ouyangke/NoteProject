package com.oyke.service;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class KtvData implements Parcelable {
    private String name;
    private int type;

    public KtvData(){}

    protected KtvData(Parcel in) {
        name = in.readString();
        type = in.readInt();
    }

    public static final Creator<KtvData> CREATOR = new Creator<KtvData>() {
        @Override
        public KtvData createFromParcel(Parcel in) {
            return new KtvData(in);
        }

        @Override
        public KtvData[] newArray(int size) {
            return new KtvData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(type);
    }
    public void readFromParcel(Parcel source) {
        name = source.readString();
        type = source.readInt();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
