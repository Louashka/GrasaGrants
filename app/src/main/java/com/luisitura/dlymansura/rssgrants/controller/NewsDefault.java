package com.luisitura.dlymansura.rssgrants.controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.luisitura.dlymansura.rssgrants.R;
import com.luisitura.dlymansura.rssgrants.adapter.RSSAdapter;
import com.luisitura.dlymansura.rssgrants.model.RssItem;
import com.luisitura.dlymansura.rssgrants.service.RssService;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;

public class NewsDefault extends AppCompatActivity {

    public static RssItem selectedRssItem = null;
    public ListView rssListDefault = null;
    public ArrayList<RssItem> rssItems = new ArrayList<>();
    public String rssClass = "";
    public String rssLink = "";
    private ProgressBar progressbarAllDefault;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_default);

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        rssClass = intent.getStringExtra("rssClass");
        rssLink = intent.getStringExtra("rssLink");

        rssListDefault = (ListView) findViewById(R.id.rssListDefault);
        progressbarAllDefault = (ProgressBar) findViewById(R.id.progressbarAllDefault);
        startService();

        // here we specify what to execute when individual list items clicked
        rssListDefault.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //@Override
            public void onItemClick(AdapterView<?> av, View view, int index,
                                    long arg3) {
                selectedRssItem = rssItems.get(index);
                // we call the other activity that shows a single rss item in
                // one page
                Intent intent = new Intent(NewsDefault.this, RssItemDisplayer.class);
                intent.putExtra("title", selectedRssItem.getTitle());
                intent.putExtra("link", selectedRssItem.getLink());
                intent.putExtra("date", selectedRssItem.getPubDate());
                intent.putExtra("resource", rssClass);
                startActivity(intent);
            }
        });
    }

    private void startService() {
        Intent intent = new Intent(this, RssService.class);
        intent.putExtra(RssService.RECEIVER, resultReceiver);
        intent.putExtra("link", rssLink);
        intent.putExtra("resource", rssClass);
        this.startService(intent);
    }

    /**
     * Once the {@link RssService} finishes its task, the result is sent to this ResultReceiver.
     */
    private final ResultReceiver resultReceiver = new ResultReceiver(new Handler()) {
        @SuppressWarnings("unchecked")
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            rssItems = (ArrayList<RssItem>) resultData.getSerializable(RssService.ITEMS);
            if (rssItems != null) {
                RSSAdapter adapter = new RSSAdapter(NewsDefault.this, rssItems);
                rssListDefault.setAdapter(adapter);
            } else {
                Toast.makeText(NewsDefault.this, "Не удалось загрузить данные",
                        Toast.LENGTH_LONG).show();
            }
//            progressBar.setVisibility(View.GONE);
            rssListDefault.setVisibility(View.VISIBLE);
            progressbarAllDefault.setVisibility(View.GONE);
        };
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_important:
                // User chose the "Settings" item, show the app settings UI...
                Intent intent = new Intent(this, FavourActivity.class);
                startActivity(intent);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }

    }

}
