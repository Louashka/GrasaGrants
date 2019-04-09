package com.luisitura.dlymansura.rssgrants.service;

import android.app.IntentService;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.preference.PreferenceManager;
import android.provider.SyncStateContract;
import android.util.Log;

import com.luisitura.dlymansura.rssgrants.model.PcWorldRssParser;
import com.luisitura.dlymansura.rssgrants.model.RssItem;
import com.luisitura.dlymansura.rssgrants.model.ZakupkiRssParser;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.List;

public class RssService extends IntentService {
    public static final String ITEMS = "items";
    public static final String RECEIVER = "receiver";
    private ProgressDialog dialog;
    String rssLink = "";
    String resource = "";

    public RssService() {
        super("RssService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        rssLink = intent.getStringExtra("link");
        resource = intent.getStringExtra("resource");
        List<RssItem> rssItems = null;
        if (resource.equals("gosZakupki")){
            try {
                ZakupkiRssParser parser = new ZakupkiRssParser();
                rssItems = parser.parse(getInputStream(rssLink), resource);
            } catch (XmlPullParserException e) {
                Log.w(e.getMessage(), e);
            } catch (IOException e) {
                Log.w(e.getMessage(), e);
            }
        } else {
            try {
                PcWorldRssParser parser = new PcWorldRssParser();
                rssItems = parser.parse(getInputStream(rssLink), resource);
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
