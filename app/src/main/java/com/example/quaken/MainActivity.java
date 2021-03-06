package com.example.quaken;

import androidx.appcompat.app.AppCompatActivity;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<newEarthQuake>> {

    //    USGS Request URL to get json response of last 100 earthquakes.
    private static final String USGS_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=5&limit=100";

    private static final int EARTHQUAKE_LOADER_ID = 1;
    private TextView mEmptyStateTextView;

    //  Adapter to show list of earthquakes in list View in main activity.
    private EarthquakeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//      creating a new adapter to show list of earthquakes in list view.
        mAdapter = new EarthquakeAdapter(this, new ArrayList<>());
        ListView earthquakeView = findViewById(R.id.listView);
        earthquakeView.setAdapter(mAdapter);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        earthquakeView.setEmptyView(mEmptyStateTextView);
//        Log.e("TEST", "TEST: BEFORE LOADER EXECUTE STARTED");
        LoaderManager loaderManager = getLoaderManager();

        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
        // because this activity implements the LoaderCallbacks interface).
        loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);

//        Log.e("TEST", "TEST: AFTER Loader EXECUTE STARTED");

    }


    @Override
    public Loader<List<newEarthQuake>> onCreateLoader(int id, Bundle args) {
        return new QuakeAsyncTaskLoader(this, USGS_URL);

    }

    @Override
    public void onLoadFinished(Loader<List<newEarthQuake>> loader, List<newEarthQuake> newEarthQuakes) {

        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);
        if(isConnected()) {
            mEmptyStateTextView.setText(R.string.no_earthquakes);
        }else{
            mEmptyStateTextView.setText("No Internet Connection!!");
        }
//        Log.e("TEST", "TEST: ON POST EXECUTE STARTED");
//      To clear any previous earthquake data in the adapter
        mAdapter.clear();

        if (newEarthQuakes != null && !newEarthQuakes.isEmpty()) {
            mAdapter.addAll(newEarthQuakes);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<newEarthQuake>> loader) {
        mAdapter.clear();
//        Log.e("TEST", "TEST: ON LOADER RESET CALLED STARTED");

    }

    public boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }


}