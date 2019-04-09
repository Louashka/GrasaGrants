package com.luisitura.dlymansura.rssgrants.controller;

import android.content.Intent;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.luisitura.dlymansura.rssgrants.R;
import com.luisitura.dlymansura.rssgrants.adapter.RSSAdapter;
import com.luisitura.dlymansura.rssgrants.model.RssItem;
import com.luisitura.dlymansura.rssgrants.service.AllRssService;

import java.util.ArrayList;
import java.util.Arrays;

import android.os.Handler;

public class AllNews extends AppCompatActivity {

    public static RssItem selectedRssItem = null;
    private ProgressBar progressbarAllRss;
    ListView rssListView = null;
    ArrayList<RssItem> rssItems = new ArrayList<>();
    String resources[] = {"konkursgrant", "vsekonkursy", "rvc", "rsci", "grantist"};
    String links[] = {"http://www.konkursgrant.ru/index.php/ru/obshchie-konkursy?format=feed&type=rss",
            "http://feeds.feedburner.com/Vsekonkursy", "https://www.rvc.ru/press-service/news/rss/",
            "http://www.rsci.ru/export/rss/rss_grants.php", "http://grantist.com/contests/vse-konkursy/feed/"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_news);

        // get the listview from layout.xml
        rssListView = (ListView) findViewById(R.id.rssListView);
        progressbarAllRss = (ProgressBar) findViewById(R.id.progressbarAllRss);


        startService();

        // here we specify what to execute when individual list items clicked
        rssListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //@Override
            public void onItemClick(AdapterView<?> av, View view, int index,
                                    long arg3) {
                selectedRssItem = rssItems.get(index);
                // we call the other activity that shows a single rss item in
                // one page
                Intent intent = new Intent(AllNews.this, RssItemDisplayer.class);
                intent.putExtra("title", selectedRssItem.getTitle());
                intent.putExtra("link", selectedRssItem.getLink());
                intent.putExtra("date", selectedRssItem.getPubDate());
                startActivity(intent);
            }
        });
    }

    private void startService() {
        Intent intent = new Intent(this, AllRssService.class);
        intent.putExtra(AllRssService.RECEIVER, resultReceiver);
        intent.putStringArrayListExtra("resources", new ArrayList<>(Arrays.asList(resources)));
        intent.putStringArrayListExtra("links", new ArrayList<>(Arrays.asList(links)));
        this.startService(intent);
    }

    /**
     * Once the {@link AllRssService} finishes its task, the result is sent to this ResultReceiver.
     */
    private final ResultReceiver resultReceiver = new ResultReceiver(new Handler()) {
        @SuppressWarnings("unchecked")
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            rssItems = (ArrayList<RssItem>) resultData.getSerializable(AllRssService.ITEMS);
            if (rssItems != null) {
                RSSAdapter adapter = new RSSAdapter(AllNews.this, rssItems);
                rssListView.setAdapter(adapter);
            } else {
                Toast.makeText(AllNews.this, "Не удалось загрузить данные",
                        Toast.LENGTH_LONG).show();
            }
//            progressBar.setVisibility(View.GONE);
            rssListView.setVisibility(View.VISIBLE);
            progressbarAllRss.setVisibility(View.GONE);
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
