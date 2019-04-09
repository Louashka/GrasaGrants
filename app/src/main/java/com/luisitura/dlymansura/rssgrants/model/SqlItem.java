package com.luisitura.dlymansura.rssgrants.model;

/**
 * Created by Admin on 07.04.2017.
 */

public class SqlItem extends RssItem{

    private String resource;

    public SqlItem(String title, String link, String pubDate, String resource) {
        super(resource, title, link, pubDate);
        this.resource = resource;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }
}
