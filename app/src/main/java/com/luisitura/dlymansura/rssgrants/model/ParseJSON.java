package com.luisitura.dlymansura.rssgrants.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Optima on 09.06.2017.
 */

public class ParseJSON {

    public static List<Fz44Item> itemsParams;

    public static final String JSON_ARRAY = "result";
    private static final String KEY_ID = "id";
    private static final String KEY_PURCHASE_NUMBER  = "purchaseNumber";
    private static final String KEY_REGION = "region";
    private static final String KEY_PURCHASE_OBJECT = "purchaseObject";
    private static final String KEY_ORG_NAME = "orgName";
    private static final String KEY_PLACING_WAY = "placingWay";
    private static final String KEY_START_DATE = "startDate";
    private static final String KEY_END_DATE = "endDate";
    private static final String KEY_PLACE = "place";
    private static final String KEY_MAX_PRICE = "maxPrice";
    private static final String KEY_CURRENCY = "currency";
    private static final String KEY_FINANCE_SOURCE = "financeSource";
    private static final String KEY_APPLICATION = "applicationGuarantee";
    private static final String KEY_CONTRACT = "contractGuarantee";
    private static final String KEY_DELIVERY_PLACE = "deliveryPlace";
    private static final String KEY_LINK = "link";

    private JSONArray items = null;

    private String json;

    public ParseJSON(String json){
        this.json = json;
    }

    public void parseJSON(){
        JSONObject jsonObject=null;
        itemsParams = new ArrayList<>();
        try {
            jsonObject = new JSONObject(json);
            items = jsonObject.getJSONArray(JSON_ARRAY);

            for(int i = 0; i < items.length(); i++){
                JSONObject jo = items.getJSONObject(i);
                Fz44Item object44 = new Fz44Item(Integer.parseInt(jo.getString(KEY_ID)), Long.parseLong(jo.getString(KEY_PURCHASE_NUMBER)),
                        jo.getString(KEY_REGION), jo.getString(KEY_PURCHASE_OBJECT), jo.getString(KEY_ORG_NAME), jo.getString(KEY_PLACING_WAY),
                        jo.getString(KEY_START_DATE), jo.getString(KEY_END_DATE), jo.getString(KEY_PLACE), Integer.parseInt(jo.getString(KEY_MAX_PRICE)),
                        jo.getString(KEY_CURRENCY), jo.getString(KEY_FINANCE_SOURCE), Integer.parseInt(jo.getString(KEY_APPLICATION)),
                        Integer.parseInt(jo.getString(KEY_CONTRACT)), jo.getString(KEY_DELIVERY_PLACE), jo.getString(KEY_LINK));
                itemsParams.add(object44);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
