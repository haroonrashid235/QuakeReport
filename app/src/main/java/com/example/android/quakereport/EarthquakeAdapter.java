package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    private static final String LOCATION_SEPERATOR = " of ";
    private String locationOffset;
    private String primaryLocation;

    public EarthquakeAdapter(Context context, List<Earthquake> earthquakes) {
        super(context,0,earthquakes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }

        Earthquake earthquake = getItem(position);

        // Format Decimal value of magnitude to one decimal place
        double magnitude = earthquake.getMagnitude();
        String magnitudeString = formatDecimal(magnitude);

        // Display Earthquake magnitude as a String Value
        TextView magnitudeView = (TextView) listItemView.findViewById(R.id.magnitude_value);
        magnitudeView.setText(magnitudeString);

        // Set the dynamic color of magnitude background circle based on earthquake magnitude
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeView.getBackground();
        int magnitudeColor = getMagnitudeColor(magnitude);
        magnitudeCircle.setColor(magnitudeColor);

        String locationString = earthquake.getLocation();

        // Split location string into location offset and primary location
        if(locationString.contains(LOCATION_SEPERATOR)){
            String[] parts = locationString.split(LOCATION_SEPERATOR);
            locationOffset = parts[0] + LOCATION_SEPERATOR;
            primaryLocation = parts[1];
        }else{
            locationOffset = getContext().getString(R.string.near_the);
            primaryLocation = locationString;
        }

        // Display the offset location text
        TextView locationOffsetView = (TextView) listItemView.findViewById(R.id.offset_value);
        locationOffsetView.setText(locationOffset);

        // Display the primary location text
        TextView locationView = (TextView) listItemView.findViewById(R.id.location_value);
        locationView.setText(primaryLocation);

        // Format time in milliseconds to readable date format
        Date dateObject = new Date(earthquake.getTimeInMilliseconds());
        String dateString = formatDate(dateObject);

        // Display formatted Date in a TextView
        TextView dateView = (TextView) listItemView.findViewById(R.id.date_value);
        dateView.setText(dateString);

        // Format time in milliseconds to a readable time
        String timeString = formatTime(dateObject);

        // Display formatted time in a  TextView
        TextView timeView = (TextView) listItemView.findViewById(R.id.time_value);
        timeView.setText(timeString);

        return listItemView;



        }


    /* Returns the magnitude background color based on the earthquake value */
    private int getMagnitudeColor(double magnitude) {
        int magnitudeColor;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor){
            case 0:
            case 1:
                magnitudeColor = R.color.magnitude1;
                break;
            case 2:
                magnitudeColor = R.color.magnitude2;
                break;
            case 3:
                magnitudeColor = R.color.magnitude3;
                break;
            case 4:
                magnitudeColor = R.color.magnitude4;
                break;
            case 5:
                magnitudeColor = R.color.magnitude5;
                break;
            case 6:
                magnitudeColor = R.color.magnitude6;
                break;
            case 7:
                magnitudeColor = R.color.magnitude7;
                break;
            case 8:
                magnitudeColor = R.color.magnitude8;
                break;
            case 9:
                magnitudeColor = R.color.magnitude9;
                break;
            default:
                magnitudeColor = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(),magnitudeColor);
    }

    /**
         * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
         */
        private String formatDate(Date dateObject) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
            return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    /* Returns the formatted decimal value as a String*/
   private String formatDecimal(double value){
       DecimalFormat formatter = new DecimalFormat("0.0");
       return formatter.format(value);
   }
}


