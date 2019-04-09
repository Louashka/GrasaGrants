package com.luisitura.dlymansura.rssgrants.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.luisitura.dlymansura.rssgrants.R;
import com.luisitura.dlymansura.rssgrants.model.Fz44Item;

public class Fz44ItemDisplayer extends AppCompatActivity {

    private Fz44Item currentItem;
    private String textViewNames[] = {"setNumberTextView", "setRegionTextView", "setObjectTextView", "setOrgTextView",
            "setPlacingTextView", "setStartDateTextView", "setEndDateTextView", "setPlaceTextView", "setMaxPriceTextView",
            "setCurrencyTextView", "setFinanceSourceTextView", "setAppTextView", "setContractTextView", "setDeliveryPlaceTextView",
            "setLinkTextView"};
    private TextView textViews[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fz44_item_displayer);

        currentItem = (Fz44Item)getIntent().getSerializableExtra("data");

        textViews = new TextView[textViewNames.length];

        for(int i = 0; i < textViewNames.length; i++){
            int resID = getResources().getIdentifier(textViewNames[i], "id", getPackageName());
            textViews[i] = (TextView)findViewById(resID);
        }

        if(currentItem != null){
            textViews[0].setText(String.valueOf(currentItem.getPurchaseNumber()));
            textViews[1].setText(currentItem.getRegion());
            textViews[2].setText(currentItem.getPurchaseObject());
            textViews[3].setText(currentItem.getOrgName());
            textViews[4].setText(currentItem.getPlacingWay());
            textViews[5].setText(currentItem.getStartDate());
            textViews[6].setText(currentItem.getEndDate());
            textViews[7].setText(currentItem.getPlace());
            textViews[8].setText(String.valueOf(currentItem.getMaxPrice()));
            textViews[9].setText(currentItem.getCurrency());
            textViews[10].setText(currentItem.getFinanceSource());
            textViews[11].setText(String.valueOf(currentItem.getApplicationGuarantee()));
            textViews[12].setText(String.valueOf(currentItem.getContractGuarantee()));
            textViews[13].setText(currentItem.getDeliveryPlace());
            textViews[14].setText(currentItem.getLink());
        }

    }
}
