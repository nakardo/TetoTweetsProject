package com.example.tetotweets;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.List;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class MainActivity extends FragmentActivity {
    private ProgressBar mProgressBar;
    private ListView mListView;

    private boolean isRequestingData = false;
    private int mCurrentPage = 1;
    private ArrayAdapter<Status> mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = (ProgressBar) findViewById(android.R.id.progress);

        mListView = (ListView) findViewById(android.R.id.list);
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {}

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                boolean isLastRowVisible = firstVisibleItem + visibleItemCount >= totalItemCount;
                if (totalItemCount > 0 && isLastRowVisible && !isRequestingData) {
                    mCurrentPage++; new GetUserTimelineAsyncTask().execute();
                }
            }
        });

        new GetUserTimelineAsyncTask().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void onUserTimelineLoaded(List<Status> statuses) {
        Log.d("Loaded " + statuses.size() + " tweets");

        if (mListAdapter == null) {
            mListAdapter = new TweetsListAdapter(this, statuses);
            mListView.setAdapter(mListAdapter);
        } else {
            mListAdapter.addAll(statuses);
            mListAdapter.notifyDataSetChanged();

            if (!Settings.isPaidVersion()) {
                new MotivationalDialog().show(getSupportFragmentManager(), "motivational_dialog");
            }
        }

        mProgressBar.setVisibility(View.GONE);
        mListView.setVisibility(View.VISIBLE);
    }

    private class GetUserTimelineAsyncTask extends AsyncTask<Void, Void, List<Status>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute(); isRequestingData = true;
        }

        @Override
        protected List<twitter4j.Status> doInBackground(Void... params) {
            ConfigurationBuilder cb = new ConfigurationBuilder();
            cb.setDebugEnabled(true)
                    .setOAuthConsumerKey("6QaN7v6SsctEaeNTUBTGA")
                    .setOAuthConsumerSecret("GjOPLRPDdMPyDSVR3jNLYzVNMmDwzsxf4rG5ttaBnk0")
                    .setOAuthAccessToken("139300130-aoAbLRcJUT0Te4C2kv69Wb68XSKq6pm6p6WtYHOL")
                    .setOAuthAccessTokenSecret("EpwFoPOqy4MPsBTbO5p5i1HIANSIc0XlhaX38OrI");

            Twitter twitter = new TwitterFactory(cb.build()).getInstance();

            List<twitter4j.Status> statuses = null;
            try {
                statuses = twitter.getUserTimeline("tetomedinaok", new Paging(mCurrentPage));
            } catch (TwitterException e) {
                e.printStackTrace();
            }

            return statuses;
        }

        @Override
        protected void onPostExecute(List<twitter4j.Status> statuses) {
            super.onPostExecute(statuses); isRequestingData = false;
            onUserTimelineLoaded(statuses);
        }
    }
}
