package com.example.quaken;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

//    USGS Request URL to get json response of last 100 earthquakes.
    private static final String USGS_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=5&limit=100" ;

//  Adapter to show list of earthquakes in list View in main activity.
    private EarthquakeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//      creating a new adapter to show list of earthquakes in list view.
        mAdapter = new EarthquakeAdapter(this, new ArrayList<newEarthQuake>());

//        Executing network request in background thread.
        AsyncTask earthquakeAsyncTask = new AsyncTask();
        earthquakeAsyncTask.execute(USGS_URL);



    }

    private class AsyncTask extends android.os.AsyncTask<String,String , List<newEarthQuake>>{

//         This method runs on a background thread and returns a list of last 100 earthquakes
        @Override
        protected List<newEarthQuake> doInBackground(String... urls) {

//             @param result is a list of type newEarthQuake class which takes return vakue from fetch method of getOnlineContent class
            List<newEarthQuake> result = getOnlineContent.fetch(urls[0]);

            return result;
        }

//         This method is executed after completion of doInBackground method
        @Override
        protected void onPostExecute(List<newEarthQuake> newEarthQuakes) {
//             To clear any previous earthquake data in the adapter
            mAdapter.clear();

            if(newEarthQuakes != null && !newEarthQuakes.isEmpty()){
//                 it takes the list returned by do in background method and adds it to mAdapter and updates the ui to show the list
//                 of last 100 earthquakes
                mAdapter.addAll(newEarthQuakes);
                ListView earthquakeView = (ListView) findViewById(R.id.listView);
                earthquakeView.setAdapter(mAdapter);

            }

        }
    }

}