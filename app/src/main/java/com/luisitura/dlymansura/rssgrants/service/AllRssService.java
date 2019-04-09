package com.luisitura.dlymansura.rssgrants.service;

import android.app.IntentService;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import com.luisitura.dlymansura.rssgrants.model.PcWorldRssParser;
import com.luisitura.dlymansura.rssgrants.model.RssItem;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class AllRssService extends IntentService {
    public static final String ITEMS = "items";
    public static final String RECEIVER = "receiver";
    private ProgressDialog dialog;
    ArrayList<String> rssLinks;
    ArrayList<String> resources;

    public AllRssService() {
        super("AllRssService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        rssLinks = intent.getStringArrayListExtra("links");
        resources = intent.getStringArrayListExtra("resources");
        List<RssItem> rssItems = new ArrayList<>();
        for(int i = 0; i < rssLinks.size(); i++){
            try {
                PcWorldRssParser parser = new PcWorldRssParser();
                List<RssItem> localItems = parser.parse(getInputStream(rssLinks.get(i)), resources.get(i));
                if(localItems != null){
                    rssItems.addAll(localItems);
                }
            } catch (XmlPullParserException e) {
                Log.w(e.getMessage(), e);
            } catch (IOException e) {
                Log.w(e.getMessage(), e);
            }
        }

        Bundle bundle = new Bundle();
        bundle.putSerializable(ITEMS, (Serializable) rssItems);
        ResultReceiver receiver = intent.getParcelableExtra(RECEIVER);
        receiver.send(0, bundle);
    }

    public InputStream getInputStream(String link) {
        try {
            URL url = new URL(link);
            return url.openConnection().getInputStream();
        } catch (IOException e) {
            return null;
        }
    }
}
