package com.example.gamingnews;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<News>> {

    private static final String LOG_TAG = NewsLoader.class.getName();

    private String myUrl;

    public NewsLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
        Log.e(LOG_TAG, "Start Loading");
    }

    @Override
    public List<News> loadInBackground() {

        Log.e(LOG_TAG, "Load in background...");

        List<News> news = QueryUtils.fetchNewsData();
        return news;
    }
}
