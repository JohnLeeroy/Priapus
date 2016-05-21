package com.jli.gardener.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by john on 5/21/16.
 */
public class Report implements Parcelable{

    String status;
    String message;
    List<Record> data;

    protected Report(Parcel in) {
        status = in.readString();
        message = in.readString();
        data = in.createTypedArrayList(Record.CREATOR);
    }

    public static final Creator<Report> CREATOR = new Creator<Report>() {
        @Override
        public Report createFromParcel(Parcel in) {
            return new Report(in);
        }

        @Override
        public Report[] newArray(int size) {
            return new Report[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(status);
        dest.writeString(message);
        dest.writeTypedList(data);
    }

    public List<Record> getRecords() { return data; }
}
