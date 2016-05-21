package com.jli.gardener.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by john on 5/20/16.
 */
public class PlantModule implements Parcelable{

    String name;
    String imageUri;

    List<Record> records;

    protected PlantModule(Parcel in) {
        name = in.readString();
        imageUri = in.readString();
        records = in.createTypedArrayList(Record.CREATOR);
    }

    public static final Creator<PlantModule> CREATOR = new Creator<PlantModule>() {
        @Override
        public PlantModule createFromParcel(Parcel in) {
            return new PlantModule(in);
        }

        @Override
        public PlantModule[] newArray(int size) {
            return new PlantModule[size];
        }
    };

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public PlantModule(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(imageUri);
        dest.writeTypedList(records);
    }
}
