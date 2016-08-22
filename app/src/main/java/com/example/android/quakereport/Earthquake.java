package com.example.android.quakereport;

/**
 * Created by Haroon Rashid on 8/10/2016.
 */
public class Earthquake {
    //Data Items

    /* Magnitude of the Earthquake */
    private double mMagnitude;

    /* Location of the Earthquake*/
    private String mLocation;

    /* Date of the Earthquake*/
    private long mTimeInMilliseconds;

    /* String containing the URL of the relevant earthquake */
    private String mUrl;



    /**
     * Constructs a new {@link Earthquake} object.
     *
     * @param magnitude is the magnitude (size) of the earthquake
     * @param location is the city location of the earthquake
     * @param timeInMilliseconds is the time in milliseconds (from the Epoch) when the
     *  earthquake happened
     */
    public Earthquake(double magnitude, String location, long timeInMilliseconds) {
        this.mMagnitude = magnitude;
        this.mLocation = location;
        this.mTimeInMilliseconds = timeInMilliseconds;
    }


    /**
     * Constructs a new {@link Earthquake} object.
     *
     * @param magnitude is the magnitude (size) of the earthquake
     * @param location is the city location of the earthquake
     * @param timeInMilliseconds is the time in milliseconds (from the Epoch) when the
     *  earthquake happened
     *  @param url is url string containing the information about the earthquake
     */
    public Earthquake(double magnitude, String location, long timeInMilliseconds, String url) {
        this.mMagnitude = magnitude;
        this.mLocation = location;
        this.mTimeInMilliseconds = timeInMilliseconds;
        this.mUrl = url;
    }

    public double getMagnitude() {
        return mMagnitude;
    }


    public String getLocation() {
        return mLocation;
    }

    public long getTimeInMilliseconds() {
        return mTimeInMilliseconds;
    }

    public String getUrl(){ return mUrl;}
}
