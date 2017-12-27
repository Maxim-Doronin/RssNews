package com.maximdoronin.rssnews;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    updateLent(NewsSubject.index);
                    return true;
                case R.id.navigation_subject:
                    SubjectListAdapter adapter = new SubjectListAdapter(MainActivity.this);
                    mListView.setAdapter(adapter);
                    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            NewsSubject newsSubject = NewsSubject.values()[i];
                            updateLent(newsSubject);
                        }
                    });
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.main_list_view);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        new RssDownloader().execute(NewsSubject.index.toString());
    }

    private void updateLent(NewsSubject newsSubject) {
        new RssDownloader().execute(newsSubject.toString());
    }

    private class RssDownloader extends AsyncTask<String, Void, List<RssItem>> {

        @Override
        protected List<RssItem> doInBackground(String... str) {
            return RssXmlParser.parse(str[0]);
        }

        @Override
        protected void onPostExecute(final List<RssItem> feedItems) {
            super.onPostExecute(feedItems);
            MainListAdapter adapter = new MainListAdapter(MainActivity.this, feedItems);
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    RssItem rssItem = feedItems.get(i);
                    Uri uri = Uri.parse(rssItem.getLink());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    MainActivity.this.startActivity(intent);
                }
            });
            mListView.setAdapter(adapter);
        }
    }

}
