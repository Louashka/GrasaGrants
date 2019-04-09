package com.luisitura.dlymansura.rssgrants.controller;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.luisitura.dlymansura.rssgrants.R;
import com.luisitura.dlymansura.rssgrants.adapter.FilterAdapter;
import com.luisitura.dlymansura.rssgrants.adapter.Fz44Adapter;
import com.luisitura.dlymansura.rssgrants.model.Fz44Item;
import com.luisitura.dlymansura.rssgrants.model.ParseJSON;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Fz44NavigationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener, SearchView.OnQueryTextListener{

    public ListView list_view_fz44, filterListView;
    private View footer;
    private Fz44Adapter cl;
    public List<Fz44Item> items, loadItems;
    public static final String JSON_URL = "http://planoffice.ru/zakupki/retrieveDataFz44.php";
    private String sorts[], regions[], placing[], currencies[], ftpRegions[];
    private int sortChecked = 0;
    private int k = 0;
    private int pageCount = 0;
    private int offset = 100;
    private boolean regionsDialog[], placingDialog[], currencyDialog[];
    private String regionsData[], placingData[], currencyData[];
    private SharedPreferences sPref;
    private SharedPreferences.Editor ed;
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    private Button filterOk, filterReset;
    private EditText minDate, maxDate, minLotPrice, maxLotPrice, minAppPrice, maxAppPrice, minContractPrice, maxContractPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fz44_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                drawer.openDrawer(Gravity.RIGHT);
            }
        });

//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        items = new ArrayList<>();
        cl = new Fz44Adapter(this, items);
        loadItems = new ArrayList<>();
        list_view_fz44 = (ListView) findViewById(R.id.list_view_fz44);
        // Add footer view
        footer = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.progress, null, false);
        list_view_fz44.addFooterView(footer);
        sendRequest("priceCondition", "LIMIT 30 OFFSET 0");
        list_view_fz44.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Fz44NavigationActivity.this, Fz44ItemDisplayer.class);
                intent.putExtra("data", items.get(position));
                startActivity(intent);
            }
        });
        // Implementing scroll refresh
        list_view_fz44.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
            }

            @Override
            public void onScroll(AbsListView absListView, int firstItem, int visibleItemCount, final int totalItems) {
                Log.e("Get position", "--firstItem:" + firstItem + "  visibleItemCount:" + visibleItemCount + "  totalItems:" + totalItems + "  pageCount:" + pageCount);
                int total = firstItem + visibleItemCount;
                // Total array list i have so it
                if (pageCount < 10) {

                    if (total == totalItems) {

                        // Execute some code after 15 seconds have passed

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                final StringRequest stringRequest = new StringRequest(Request.Method.POST, JSON_URL,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                ParseJSON pj = new ParseJSON(response);
                                                pj.parseJSON();
                                                loadItems = ParseJSON.itemsParams;
                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Toast.makeText(Fz44NavigationActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        }) {
                                    @Override
                                    protected Map<String, String> getParams() {
                                        Map<String, String> params = new HashMap<>();
                                        params.put("priceCondition", "LIMIT 30 OFFSET " + offset);
                                        return params;
                                    }
                                };

                                RequestQueue requestQueue = Volley.newRequestQueue(Fz44NavigationActivity.this);
                                requestQueue.add(stringRequest);
                                items.addAll(loadItems);
                                cl.notifyDataSetChanged();
                                list_view_fz44.setAdapter(cl);
                                list_view_fz44.setSelection(totalItems);
                                pageCount++;
                                offset++;
                            }
                        }, 500);
                    }
                } else {
                    Log.e("hide footer", "footer hide");
                    list_view_fz44.removeFooterView(footer);
                }
            }
        });

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                minDate.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                maxDate.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        filterListView = (ListView) findViewById(R.id.filterListView);
        filterListView.setAdapter(new FilterAdapter(this));
        filterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        showSortDialog();
                        break;
                    case 1:
                        showRegionDialog();
                        break;
                    case 2:
                        showPlacingDialog();
                        break;
                    case 3:
                        showCurrencyDialog();
                        break;
                }
            }
        });

        sorts = getResources().getStringArray(R.array.array_sort);
        regions = getResources().getStringArray(R.array.array_regions);
        ftpRegions = getResources().getStringArray(R.array.array_regions_ftp);
        placing = getResources().getStringArray(R.array.array_placing);
        currencies = getResources().getStringArray(R.array.array_currency);

        regionsData = new String[regions.length + 1];
        regionsData[0] = "Все";

        placingData = new String[placing.length + 1];
        placingData[0] = "Все";

        currencyData = new String[currencies.length + 1];
        currencyData[0] = "Все";

        sPref = getPreferences(MODE_PRIVATE);
        ed = sPref.edit();
        ed.clear();

        ed.putBoolean("region0", true);
        for (int i = 1; i < regionsData.length; i++){
            regionsData[i] = regions[i-1];
            ed.putBoolean("region" + i, true);
        }

        ed.putBoolean("placing0", true);
        for (int i = 1; i < placingData.length; i++){
            placingData[i] = placing[i-1];
            ed.putBoolean("placing" + i, true);
        }

        ed.putBoolean("currency0", true);
        for (int i = 1; i < currencyData.length; i++){
            currencyData[i] = currencies[i-1];
            ed.putBoolean("currency" + i, true);
        }

        ed.commit();
        regionsDialog = new boolean[regionsData.length];
        placingDialog = new boolean[placingData.length];
        currencyDialog = new boolean[currencyData.length];

        minDate = (EditText)findViewById(R.id.minDate);
        minDate.setInputType(InputType.TYPE_NULL);
        maxDate = (EditText)findViewById(R.id.maxDate);
        maxDate.setInputType(InputType.TYPE_NULL);
        minLotPrice = (EditText)findViewById(R.id.minLotPrice);
        maxLotPrice = (EditText)findViewById(R.id.maxLotPrice);
        minAppPrice = (EditText)findViewById(R.id.minAppPrice);
        maxAppPrice = (EditText)findViewById(R.id.maxAppPrice);
        minContractPrice = (EditText)findViewById(R.id.minContractPrice);
        maxContractPrice = (EditText)findViewById(R.id.maxContractPrice);

        filterOk = (Button)findViewById(R.id.buttonOk);
        filterReset = (Button)findViewById(R.id.buttonReset);

        minDate.setOnClickListener(this);
        maxDate.setOnClickListener(this);
        filterOk.setOnClickListener(this);
        filterReset.setOnClickListener(this);
    }

    private void showSortDialog(){

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Выберите сортировку");
        builder.setSingleChoiceItems(sorts, sortChecked, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                k = which;
            }
        });

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sortChecked = k;
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void showRegionDialog(){
        sPref = getPreferences(MODE_PRIVATE);
        k = 0;
        for(int i = 0; i < regionsDialog.length; i++){
            regionsDialog[i] = sPref.getBoolean("region" + i, false);
            if(regionsDialog[i] && i != 0)
                k++;
        }


        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Выберите регионы");
        builder.setMultiChoiceItems(regionsData, regionsDialog, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                regionsDialog[which] = isChecked;
                if(which == 0){
                    if(isChecked){
                        k = regionsDialog.length - 1;
                        for(int i = 1; i < regionsDialog.length; i++){
                            regionsDialog[i] = true;
                            ((AlertDialog) dialog).getListView().setItemChecked(i, true);
                        }
                    } else {
                        k = 0;
                        for(int i = 1; i < regionsDialog.length; i++){
                            regionsDialog[i] = false;
                            ((AlertDialog) dialog).getListView().setItemChecked(i, false);
                        }
                    }

                } else {
                    if(isChecked){
                        k++;
                        if (k == regionsDialog.length - 1){
                            regionsDialog[0] = true;
                            ((AlertDialog) dialog).getListView().setItemChecked(0, true);
                        }
                    } else {
                        k--;
                        regionsDialog[0] = false;
                        ((AlertDialog) dialog).getListView().setItemChecked(0, false);
                    }
                }
            }
        });
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                k = 0;
                ed = sPref.edit();
                for (int i = 0; i < regionsDialog.length; i++){
                    ed.putBoolean("region" + i, regionsDialog[i]);
                    if(regionsDialog[i] && i != 0)
                        k++;
                }
                ed.commit();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void showPlacingDialog(){
        k = 0;
        sPref = getPreferences(MODE_PRIVATE);
        for(int i = 0; i < placingDialog.length; i++){
            placingDialog[i] = sPref.getBoolean("placing" + i, false);
            if(placingDialog[i] && i != 0)
                k++;
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Выберите размещение");
        builder.setMultiChoiceItems(placingData, placingDialog, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                placingDialog[which] = isChecked;
                if(which == 0){
                    if(isChecked){
                        k = regionsDialog.length - 1;
                        for(int i = 1; i < regionsDialog.length; i++){
                            placingDialog[i] = true;
                            ((AlertDialog) dialog).getListView().setItemChecked(i, true);
                        }
                    } else {
                        k = 0;
                        for(int i = 1; i < placingDialog.length; i++){
                            placingDialog[i] = false;
                            ((AlertDialog) dialog).getListView().setItemChecked(i, false);
                        }
                    }

                } else {
                    if(isChecked){
                        k++;
                        if (k == placingDialog.length - 1){
                            placingDialog[0] = true;
                            ((AlertDialog) dialog).getListView().setItemChecked(0, true);
                        }
                    } else {
                        k--;
                        placingDialog[0] = false;
                        ((AlertDialog) dialog).getListView().setItemChecked(0, false);
                    }
                }
            }
        });
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                k = 0;
                ed = sPref.edit();
                for (int i = 0; i < placingDialog.length; i++){
                    ed.putBoolean("placing" + i, placingDialog[i]);
                    if(placingDialog[i] && i != 0)
                        k++;
                }
                ed.commit();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void showCurrencyDialog(){
        k = 0;
        sPref = getPreferences(MODE_PRIVATE);
        for(int i = 0; i < currencyDialog.length; i++){
            currencyDialog[i] = sPref.getBoolean("currency" + i, false);
            if(currencyDialog[i] && i != 0)
                k++;
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Выберите валюты");
        builder.setMultiChoiceItems(currencyData, currencyDialog, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                currencyDialog[which] = isChecked;
                if(which == 0){
                    if(isChecked){
                        k = regionsDialog.length - 1;
                        for(int i = 1; i < currencyDialog.length; i++){
                            currencyDialog[i] = true;
                            ((AlertDialog) dialog).getListView().setItemChecked(i, true);
                        }
                    } else {
                        k = 0;
                        for(int i = 1; i < currencyDialog.length; i++){
                            currencyDialog[i] = false;
                            ((AlertDialog) dialog).getListView().setItemChecked(i, false);
                        }
                    }

                } else {
                    if(isChecked){
                        k++;
                        if (k == currencyDialog.length - 1){
                            currencyDialog[0] = true;
                            ((AlertDialog) dialog).getListView().setItemChecked(0, true);
                        }
                    } else {
                        k--;
                        currencyDialog[0] = false;
                        ((AlertDialog) dialog).getListView().setItemChecked(0, false);
                    }
                }
            }
        });
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                k = 0;
                ed = sPref.edit();
                for (int i = 0; i < currencyDialog.length; i++){
                    ed.putBoolean("currency" + i, currencyDialog[i]);
                    if(currencyDialog[i] && i != 0)
                        k++;
                }
                ed.commit();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void sendRequest(final String key, final String value) {

        final StringRequest stringRequest = new StringRequest(Request.Method.POST, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        showJSON(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Fz44NavigationActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put(key, value);
                            return params;
                        }
                    };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String json){
        ParseJSON pj = new ParseJSON(json);
        pj.parseJSON();
        items = ParseJSON.itemsParams;
        cl = new Fz44Adapter(this, items);
        list_view_fz44.setAdapter(cl);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(Gravity.RIGHT)) {
            drawer.closeDrawer(Gravity.RIGHT);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default

        searchView.setOnQueryTextListener(this);

        MenuItem menuItem = menu.findItem(R.id.app_bar_search);
        MenuItemCompat.setOnActionExpandListener(menuItem,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionExpand(MenuItem menuItem) {
                        // Return true to allow the action view to expand
                        searchView.requestFocus();
                        return true;
                    }
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                        // When the action view is collapsed, reset the query
                        sendRequest("priceCondition", "LIMIT 30 OFFSET 0");
                        searchView.setQuery("", false);
                        // Return true to allow the action view to collapse
                        return true;
                    }
                });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        String fromDate = "";
        String toDate = "";
        String minLot = "";
        String maxLot = "";
        String minApp = "";
        String maxApp = "";
        String minCon = "";
        String maxCon = "";
        String order = "";
        String regionClause = "";
        String placingClause = "";
        String currencyClause = "";
        String clause = "";
        ArrayList<String> regionCon = new ArrayList<>();
        ArrayList<String> placingCon = new ArrayList<>();
        ArrayList<String> currencyCon = new ArrayList<>();
        ArrayList<String> conditions = new ArrayList<>();
        switch (v.getId()){
            case R.id.buttonOk:
                fromDate = minDate.getText().toString();
                toDate = maxDate.getText().toString();
                minLot = minLotPrice.getText().toString();
                maxLot = maxLotPrice.getText().toString();
                minApp = minAppPrice.getText().toString();
                maxApp = maxAppPrice.getText().toString();
                minCon = minContractPrice.getText().toString();
                maxCon = maxContractPrice.getText().toString();

                for (int i = 0; i < regions.length; i++){
                    if(regionsDialog[i+1]){
                        regionCon.add("region = '" + ftpRegions[i] + "'");
                    }
                }

                for (int i = 0; i < placing.length; i++){
                    if(placingDialog[i+1]){
                        placingCon.add("placingWay = '" + placing[i] + "'");
                    }
                }

                for (int i = 0; i < currencies.length; i++){
                    if(currencyDialog[i+1]){
                        currencyCon.add("currency = '" + currencies[i] + "'");
                    }
                }

                if(!regionCon.isEmpty()){
                    if(regions.length > 1)
                        regionClause += "(";
                    for (int i = 0; i < regionCon.size(); i++) {
                        if(i != regionCon.size() - 1){
                            regionClause += regionCon.get(i) + " or ";
                        } else {
                            regionClause += regionCon.get(i);
                        }
                    }
                    if(regions.length > 1)
                        regionClause += ")";
                }

                if(!regionClause.isEmpty()){
                    conditions.add(regionClause);
                }

                if(!placingCon.isEmpty()){
                    if(placing.length > 1)
                        placingClause += "(";
                    for (int i = 0; i < placingCon.size(); i++) {
                        if(i != placingCon.size() - 1){
                            placingClause += placingCon.get(i) + " or ";
                        } else {
                            placingClause += placingCon.get(i);
                        }
                    }
                    if(placing.length > 1)
                        placingClause += ")";
                }

                if(!placingClause.isEmpty()){
                    conditions.add(placingClause);
                }

                if(!currencyCon.isEmpty()){
                    if(currencies.length > 1)
                        currencyClause += "(";
                    for (int i = 0; i < currencyCon.size(); i++) {
                        if(i != currencyCon.size() - 1){
                            currencyClause += currencyCon.get(i) + " or ";
                        } else {
                            currencyClause += currencyCon.get(i);
                        }
                    }
                    if(currencies.length > 1)
                        currencyClause += ")";
                }

                if(!currencyClause.isEmpty()){
                    conditions.add(currencyClause);
                }

                if(!fromDate.isEmpty() && !toDate.isEmpty()) {
                    conditions.add("endDate >= '" + fromDate + "' and endDate <= " + toDate + "'");
                } else if (!fromDate.isEmpty()){
                    conditions.add("endDate >= '" + fromDate + "'");
                } else if (!toDate.isEmpty()){
                    conditions.add("endDate <= '" + toDate + "'");
                }

                if(!minLot.isEmpty() && !maxLot.isEmpty()) {
                    conditions.add("maxPrice >= " + minLot + " and maxPrice <= " + maxLot);
                } else if (!minLot.isEmpty()){
                    conditions.add("maxPrice >= " + minLot);
                } else if (!maxLot.isEmpty()){
                    conditions.add("maxPrice <= " + maxLot);
                }

                if(!minApp.isEmpty() && !maxApp.isEmpty()) {
                    conditions.add("applicationGuarantee >= " + minApp + " and applicationGuarantee <= " + maxApp);
                } else if (!minApp.isEmpty()){
                    conditions.add("applicationGuarantee >= " + minApp);
                } else if (!maxApp.isEmpty()){
                    conditions.add("applicationGuarantee <= " + maxApp);
                }

                if(!minCon.isEmpty() && !maxCon.isEmpty()) {
                    conditions.add("contractGuarantee >= " + minCon + " and contractGuarantee <= " + maxCon);
                } else if (!minCon.isEmpty()){
                    conditions.add("contractGuarantee >= " + minCon);
                } else if (!maxCon.isEmpty()){
                    conditions.add("contractGuarantee <= " + maxCon);
                }

                if(!conditions.isEmpty()){
                    for (int i = 0; i < conditions.size(); i++){
                        if(i != conditions.size() - 1){
                            clause += conditions.get(i) + " and ";
                        } else {
                            clause += conditions.get(i);
                        }
                    }
                }

                if(sortChecked == 1){
                    order = "order by maxPrice";
                } else if(sortChecked == 2){
                    order = "order by endDate";
                }

                if(!clause.isEmpty()){
                    sendRequest("priceCondition", "WHERE " + clause + " " + order);
                } else if(!order.isEmpty()){
                    sendRequest("priceCondition", order);
                }

                drawer.closeDrawer(Gravity.RIGHT);
                break;
            case R.id.minDate:
                fromDatePickerDialog.show();
                break;
            case R.id.maxDate:
                toDatePickerDialog.show();
                break;
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        sendRequest("searchQuery", query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

}
