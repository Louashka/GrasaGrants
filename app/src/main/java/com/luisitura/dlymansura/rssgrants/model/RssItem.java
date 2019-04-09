package com.luisitura.dlymansura.rssgrants.model;

import java.util.*;
import java.text.*;
import java.net.*;
import java.io.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

/**
 * Created by Admin on 22.03.2017.
 */

public class RssItem {

    private String resource;
    private String title;
    private String pubDate;
    private String link;

    public RssItem(String resource, String title, String link, String pubDate) {
        this.resource = resource;
        this.title = title;
        this.pubDate = pubDate;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
