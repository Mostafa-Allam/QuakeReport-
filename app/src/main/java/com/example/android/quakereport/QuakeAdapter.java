package com.example.android.quakereport;

import android.graphics.drawable.GradientDrawable;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class QuakeAdapter extends ArrayAdapter<Quake> {

    public QuakeAdapter(Activity context, List<Quake> quakes) {

        super(context, 0, quakes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Quake currentQuake = getItem(position);

        DecimalFormat formatter = new DecimalFormat("0.0");
        String  MyMag = formatter.format(currentQuake.getMag());
        TextView magTextView = listItemView.findViewById(R.id.mag);
        magTextView.setText(MyMag);
        //next 3 lines of code about color of the circle of magnitude
        GradientDrawable magnitudeCircle = (GradientDrawable) magTextView.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentQuake.getMag());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);


        TextView primaryLocationTextView =listItemView.findViewById(R.id.primary_location);
        TextView locationOffsetTextView =listItemView.findViewById(R.id.location_offset);

        String location = currentQuake.getPlace();
        String []  locations = location.split("of ");
        if(locations.length == 1 ) {
            locationOffsetTextView.setText("Near The");
            primaryLocationTextView.setText(locations[0]);
        }else {
            locationOffsetTextView.setText(locations[0] + " of");
            primaryLocationTextView.setText(locations[1]);
        }


        Date dateObject = new Date(currentQuake.getTime());
        //displaying date
        String dateToDisplay = formatDate(dateObject);
        TextView dateTextView =listItemView.findViewById(R.id.date);
        dateTextView.setText(dateToDisplay);
        //displaying time
        TextView timeView = listItemView.findViewById(R.id.time);
        String formattedTime = formatTime(dateObject);
        timeView.setText(formattedTime);

        return listItemView;
    }

    private int getMagnitudeColor(double mag) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(mag);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);

        }
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    }
