package com.example.quaken;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.AsyncTaskLoader;

import java.util.List;

public class QuakeAsyncTaskLoader extends AsyncTaskLoader<List<newEarthQuake>> {

    private String murl;

    public QuakeAsyncTaskLoader(@NonNull Context context, String url) {
        super(context);
        murl = url;
    }

    @Override
    protected void onStartLoading() {
        onForceLoad();
    }


    @Nullable
    @Override
    public List<newEarthQuake> loadInBackground() {
//        Log.e("TEST","TEST: LOAD IN BACKGROUND STARTED");

//       @param result is a list of type newEarthQuake class which takes return vaLue from fetch method of getOnlineContent class
        List<newEarthQuake> result = getOnlineContent.fetch(murl);

        return result;
    }
}
