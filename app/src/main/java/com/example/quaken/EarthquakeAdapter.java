package com.example.quaken;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EarthquakeAdapter extends ArrayAdapter<newEarthQuake> {
    public EarthquakeAdapter(Activity context, ArrayList<newEarthQuake> newEarthQuakes) {
        super(context, 0, newEarthQuakes);
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
    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        newEarthQuake mEarthQuake = getItem(position);

        Date dateObject = new Date(mEarthQuake.getTime());

        TextView mMagnitude = (TextView) listItemView.findViewById(R.id.Magnitude);
        String formattedMagnitude = formatMagnitude(mEarthQuake.getMagnitude());
        mMagnitude.setText(formattedMagnitude);


        TextView mLocation = (TextView) listItemView.findViewById(R.id.Location);
        mLocation.setText(mEarthQuake.getLocation());

        TextView mDate = (TextView) listItemView.findViewById(R.id.Date);
        mDate.setText(formatDate(dateObject));

        TextView mTime = (TextView) listItemView.findViewById(R.id.Time);
        mTime.setText(formatTime(dateObject));




        return listItemView;
    }
}
