package com.jli.gardener.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by john on 5/20/16.
 */
public class Record implements Parcelable, Comparable<Record>{

    @SerializedName("unit_id")
    String id;

    @SerializedName("light_direct_r")
    int directionalLightRed;

    @SerializedName("light_direct_g")
    int directionalLightGreen;

    @SerializedName("light_direct_b")
    int directionalLightBlue;

    @SerializedName("light_ambient")
    int ambientLight;

    @SerializedName("report_date")
    String timestamp;

    @SerializedName("soil_moisture")
    int soil;

    float humidity;

    @SerializedName("temperature_f")
    float temperaturFah;

    public float getTemperaturC() {
        return temperaturC;
    }

    public void setTemperaturC(float temperaturC) {
        this.temperaturC = temperaturC;
    }

    @SerializedName("temperature_c")
    float temperaturC;

    @SerializedName("rain_measure")
    int rain;

    protected Record(Parcel in) {
        timestamp = in.readString();
        soil = in.readInt();
        ambientLight = in.readInt();
        directionalLightRed = in.readInt();
        directionalLightGreen = in.readInt();
        directionalLightBlue = in.readInt();
        humidity = in.readFloat();
        temperaturFah = in.readFloat();
        temperaturC = in.readFloat();
        rain = in.readInt();
    }

    public static final Creator<Record> CREATOR = new Creator<Record>() {
        @Override
        public Record createFromParcel(Parcel in) {
            return new Record(in);
        }

        @Override
        public Record[] newArray(int size) {
            return new Record[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(timestamp);
        dest.writeInt(soil);
        dest.writeInt(ambientLight);
        dest.writeInt(directionalLightRed);
        dest.writeInt(directionalLightGreen);
        dest.writeInt(directionalLightBlue);
        dest.writeFloat(humidity);
        dest.writeFloat(temperaturFah);
        dest.writeFloat(temperaturC);
        dest.writeInt(rain);
    }

    @Override
    public int compareTo(Record another) {
        return another.timestamp.compareTo(timestamp);
    }

    public Date getDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        Date date = null;
        try {
            date = formatter.parse(timestamp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    public String getId() {
        return id;
    }

    public float getTemperaturFahrenheit() {
        return temperaturFah;
    }

    public void setTemperaturFah(float temperaturFah) {
        this.temperaturFah = temperaturFah;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }



    public void setId(String id) {
        this.id = id;
    }

    public int getDirectionalLightRed() {
        return directionalLightRed;
    }

    public void setDirectionalLightRed(int directionalLightRed) {
        this.directionalLightRed = directionalLightRed;
    }

    public int getDirectionalLightGreen() {
        return directionalLightGreen;
    }

    public void setDirectionalLightGreen(int directionalLightGreen) {
        this.directionalLightGreen = directionalLightGreen;
    }

    public int getDirectionalLightBlue() {
        return directionalLightBlue;
    }

    public void setDirectionalLightBlue(int directionalLightBlue) {
        this.directionalLightBlue = directionalLightBlue;
    }

    public int getAmbientLight() {
        return ambientLight;
    }

    public void setAmbientLight(int ambientLight) {
        this.ambientLight = ambientLight;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getSoil() {
        return soil;
    }

    public void setSoil(int soil) {
        this.soil = soil;
    }

    public float getTemperaturFah() {
        return temperaturFah;
    }

    public int getRain() {
        return rain;
    }

    public void setRain(int rain) {
        this.rain = rain;
    }
}
