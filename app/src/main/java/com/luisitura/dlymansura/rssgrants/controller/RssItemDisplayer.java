package com.luisitura.dlymansura.rssgrants.controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import java.io.IOException;
import java.text.*;

import com.luisitura.dlymansura.rssgrants.R;
import com.luisitura.dlymansura.rssgrants.model.RssItem;
import com.luisitura.dlymansura.rssgrants.model.SqlItem;
import com.luisitura.dlymansura.rssgrants.service.MySQLiteHelper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class RssItemDisplayer extends AppCompatActivity implements View.OnClickListener{

    private Element divElement;
    private Elements content;
    private String contentText = "";
    private String contentURL = "";
    private TextView contentTv;
    private String resource = "";
    private String contentDate = "";
    private String title = "";
    private ImageView image;
    private ImageView imageShare;
    private MySQLiteHelper db;
    private boolean state = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rss_item_displayer);

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        contentURL = intent.getStringExtra("link");
        resource = intent.getStringExtra("resource");
        contentDate = intent.getStringExtra("date");

        db = new MySQLiteHelper(this);

        imageShare = (ImageView)findViewById(R.id.imageShare);
        image = (ImageView)findViewById(R.id.imageButton);
        if (db.ifItemExist(contentURL)) {
            image.setImageResource(R.mipmap.ic_action_favourite_color);
            state = true;
        }
        image.setOnClickListener(this);
        imageShare.setOnClickListener(this);

        //Bundle extras = getIntent().getExtras();
        TextView titleTv = (TextView)findViewById(R.id.titleTextView);
        contentTv = (TextView)findViewById(R.id.contentTextView);

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd - hh:mm:ss");
        title = intent.getStringExtra("title");
        titleTv.setText(title);

        new DataThread().execute();

    }

    @Override
    public void onClick(View v) {
        if (v == image){
            if (state){
                db.deleteItem(new SqlItem(title, contentURL, contentDate, resource));
                image.setImageResource(R.mipmap.ic_action_favourite);
                state = false;
                Toast.makeText(this,"Статья удалена из избранного",Toast.LENGTH_SHORT).show();
            } else {
                db.addItem(new SqlItem(title, contentURL, contentDate, resource));
                image.setImageResource(R.mipmap.ic_action_favourite_color);
                state = true;
                Toast.makeText(this,"Статья добавлена в избранное",Toast.LENGTH_SHORT).show();
            }

        } else if (v == imageShare){
            try {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, "Grasa Grant");
                i.putExtra(Intent.EXTRA_TEXT, contentURL);
                startActivity(Intent.createChooser(i, "choose one"));
            } catch(Exception e) {
                //e.toString();
            }
        }

    }

    public class DataThread extends AsyncTask<String, Void, String>{

        Document doc;
        private ProgressDialog progressBar;
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
//            progressBar= new ProgressDialog(RssItemDisplayer.this);
//            progressBar.setMessage("Loading...");
//            progressBar.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                doc = Jsoup.connect(contentURL).get();
                if (resource.equals("rvc")) {
                    divElement = doc.getElementsByClass("news-detail-content").first();
                } else if (resource.equals("rsci")) {
                    divElement = doc.getElementsByClass("body").first();
                } else if (resource.equals("grantist")) {
                    divElement = doc.getElementsByClass("entry-content clearfix").first();
                } else if (resource.equals("vsekonkursy")) {
                    divElement = doc.getElementsByClass("component-content").first();
                } else if (resource.equals("fcp")) {
                    divElement = doc.getElementsByClass("reader_article_body").first();
                } else if (resource.equals("konkursgrant")) {
                    divElement = doc.getElementsByClass("component-content").first();
                } else {
                    divElement = doc.getElementsByClass("body").first();
                }

                content = divElement.getElementsByTag("p");
                for (Element x: content) {
                    contentText += x.text() + "\n";
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result){
            contentTv.setText(contentText);
//            progressBar.dismiss();
        }
    }

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
