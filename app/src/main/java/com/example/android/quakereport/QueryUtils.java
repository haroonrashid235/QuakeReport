package com.example.android.quakereport;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {


    /* LOG_TAG to identify log messages from this class */
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Return a list of {@link Earthquake} objects that has been built up from
     * parsing a JSON response.
     */
    public static ArrayList<Earthquake> extractEarthquakes(String earthquakeUrl) {

        // Build the URL to perform a network request
        URL url = createUrl(earthquakeUrl);

        // String to Store JSON response
        String jsonResponse = null;

        // Perform a networking request
        try {
            jsonResponse = makeHTTPRequest(url);
            if(jsonResponse == null){
                return null;
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<Earthquake> earthquakes = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            JSONObject JSONEarthquake = new JSONObject(jsonResponse);
            JSONArray featuresArray  = JSONEarthquake.getJSONArray("features");

            for(int i=0;i < featuresArray.length();i++){
                JSONObject earthquake = featuresArray.getJSONObject(i);
                JSONObject earthquakeProperties = earthquake.getJSONObject("properties");

                Double magnitude = earthquakeProperties.getDouble("mag");
                String location = earthquakeProperties.getString("place");
                Long time = earthquakeProperties.getLong("time");
                String open_url = earthquakeProperties.getString("url");

                earthquakes.add(new Earthquake(magnitude,location,time,open_url));
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakes;
    }

    /* Makes a network Request*/

    private static String makeHTTPRequest(URL url) throws IOException{
        String jsonResponse = null;

        if (url == null) {
            return null;
        }

        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;

        // Make a network request using (@link HTTPURLConnection}
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();

            // If network request was successful, read data from input Stream and get JSON Response
            if(urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }

        }catch (IOException e){
            Log.e(LOG_TAG, "Failed to Get JSON data" , e);
        }finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            }

            if(inputStream != null){
                inputStream.close();
            }
        }

        return jsonResponse;
    }

    /* Takes {@link InputStream} as an input and return data in form of String */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if(inputStream != null) {
            InputStreamReader streamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(streamReader);
            String line = reader.readLine();
            while(line != null ){
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String urlString) {
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG , "Error with creating URL ", e);
        }
        return url;
    }

}