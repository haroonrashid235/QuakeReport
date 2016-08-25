package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by Haroon Rashid on 8/25/2016.
 * Uses AsyncTask Loader to do Networking Task in Background to load data for earthquakes from API
 */
public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {

    // URL String to load data from
    private String mEarthquakeUrl;

    public EarthquakeLoader(Context context) {
        super(context);
    }

    public EarthquakeLoader(Context context, String earthquakeUrl) {
        super(context);
        this.mEarthquakeUrl = earthquakeUrl;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Earthquake> loadInBackground() {
        // Don't perform the request if there are no URLs, or the first URL is null.
        if (mEarthquakeUrl == null) {
            return null;
        }
        return QueryUtils.extractEarthquakes(mEarthquakeUrl);
    }
}
