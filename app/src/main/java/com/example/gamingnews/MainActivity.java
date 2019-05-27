package com.example.gamingnews;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    public static final String LOG_TAG = MainActivity.class.getName();

    private static final int NEWS_LOADER_ID = 1;

    private NewsAdapter myAdapter;
    private SwipeRefreshLayout mySwipeRefreshLayout;
    private ProgressBar myProgressBar;
    private TextView emptyState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView newsGridView = (GridView) findViewById(R.id.gridView);
        newsGridView.setEmptyView(emptyState);

        emptyState = (TextView) findViewById(R.id.empty_view);

        mySwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);

        myProgressBar = (ProgressBar) findViewById(R.id.progress_circular);
        myProgressBar.setVisibility(View.INVISIBLE);

        myAdapter = new NewsAdapter(this, new ArrayList<News>());
        newsGridView.setAdapter(myAdapter);

        newsGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                News currentNews = myAdapter.getItem(position);

                Uri newsUri = Uri.parse(currentNews.getUrl());

                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);

                startActivity(websiteIntent);
            }
        });

        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        doYourUpdate();

                    }
                }
        );

        if(checkConnection() == true){
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
            Log.e(LOG_TAG, "Loader initialed");
        }else{
            emptyState.setText("No connection");
        }

    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        mySwipeRefreshLayout.setRefreshing(false);
        myProgressBar.setVisibility(View.VISIBLE);
        Loader<List<News>> createLoader =  new NewsLoader(this);
        Log.e(LOG_TAG, "Loader created");
        return createLoader;
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {


        // Clear the adapter of previous earthquake data
        myAdapter.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (data != null && !data.isEmpty()) {
            myAdapter.addAll(data);
        }else if(data.isEmpty()){
            emptyState.setText("No data found");
        }

        myProgressBar.setVisibility(View.GONE);
        Log.e(LOG_TAG, "Load Finished");
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        myAdapter.clear();

        Log.e(LOG_TAG, "Loader reset");
    }

    private void doYourUpdate(){
        if(checkConnection() == true){
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.restartLoader(NEWS_LOADER_ID, null, this);
            emptyState.setText("");
        }else{
            mySwipeRefreshLayout.setRefreshing(false);
            emptyState.setText("No connection");
        }


    }

    public boolean checkConnection(){
        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }
}
