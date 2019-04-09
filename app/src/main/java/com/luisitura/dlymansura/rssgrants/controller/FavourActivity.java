package com.luisitura.dlymansura.rssgrants.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.luisitura.dlymansura.rssgrants.R;
import com.luisitura.dlymansura.rssgrants.adapter.RSSAdapter;
import com.luisitura.dlymansura.rssgrants.model.RssItem;
import com.luisitura.dlymansura.rssgrants.model.SqlItem;
import com.luisitura.dlymansura.rssgrants.service.MySQLiteHelper;

import java.util.ArrayList;

public class FavourActivity extends AppCompatActivity {

    public static SqlItem selectedRssItem = null;
    public ListView rssListFavour = null;
    public ArrayList<RssItem> rssItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favour);

        // get the listview from layout.xml
        rssListFavour = (ListView) findViewById(R.id.rssListFavour);

        MySQLiteHelper db = new MySQLiteHelper(this);
        rssItems = db.getAllItems();

        RSSAdapter adapter = new RSSAdapter(this, rssItems);
        rssListFavour.setAdapter(adapter);

        // here we specify what to execute when individual list items clicked
        rssListFavour.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //@Override
            public void onItemClick(AdapterView<?> av, View view, int index,
                                    long arg3) {
                selectedRssItem = (SqlItem)rssItems.get(index);
                // we call the other activity that shows a single rss item in
                // one page
                Intent intent = new Intent(FavourActivity.this, RssItemDisplayer.class);
                intent.putExtra("title", selectedRssItem.getTitle());
                intent.putExtra("link", selectedRssItem.getLink());
                intent.putExtra("date", selectedRssItem.getPubDate());
                intent.putExtra("resource", selectedRssItem.getResource());
                startActivity(intent);
            }
        });
    }
}
