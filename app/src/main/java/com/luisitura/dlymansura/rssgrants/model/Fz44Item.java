package com.luisitura.dlymansura.rssgrants.model;

import java.io.Serializable;

/**
 * Created by Optima on 09.06.2017.
 */

public class Fz44Item implements Serializable {
    private int id;
    private long purchaseNumber;
    private String region;
    private String purchaseObject;
    private String orgName;
    private String placingWay;
    private String startDate;
    private String endDate;
    private String place;
    private int maxPrice;
    private String currency;
    private String financeSource;
    private int applicationGuarantee;
    private int contractGuarantee;
    private String deliveryPlace;
    private String link;

    public Fz44Item(int id, long purchaseNumber, String region, String purchaseObject, String orgName, String placingWay, String startDate,
                    String endDate, String place, int maxPrice, String currency, String financeSource, int applicationGuarantee,
                    int contractGuarantee, String deliveryPlace, String link){
        this.id = id;
        this.purchaseNumber = purchaseNumber;
        this.region = region;
        this.purchaseObject = purchaseObject;
        this.orgName = orgName;
        this.placingWay = placingWay;
        this.startDate = startDate;
        this.endDate = endDate;
        this.place = place;
        this.maxPrice = maxPrice;
        this.currency = currency;
        this.financeSource = financeSource;
        this.applicationGuarantee = applicationGuarantee;
        this.contractGuarantee = contractGuarantee;
        this.deliveryPlace = deliveryPlace;
        this.link = link;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getPurchaseNumber() {
        return purchaseNumber;
    }

    public void setPurchaseNumber(long purchaseNumber) {
        this.purchaseNumber = purchaseNumber;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPurchaseObject() {
        return purchaseObject;
    }

    public void setPurchaseObject(String purchaseObject) {
        this.purchaseObject = purchaseObject;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getPlacingWay() {
        return placingWay;
    }

    public void setPlacingWay(String placingWay) {
        this.placingWay = placingWay;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getFinanceSource() {
        return financeSource;
    }

    public void setFinanceSource(String financeSource) {
        this.financeSource = financeSource;
    }

    public int getApplicationGuarantee() {
        return applicationGuarantee;
    }

    public void setApplicationGuarantee(int applicationGuarantee) {
        this.applicationGuarantee = applicationGuarantee;
    }

    public int getContractGuarantee() {
        return contractGuarantee;
    }

    public void setContractGuarantee(int contractGuarantee) {
        this.contractGuarantee = contractGuarantee;
    }

    public String getDeliveryPlace() {
        return deliveryPlace;
    }

    public void setDeliveryPlace(String deliveryPlace) {
        this.deliveryPlace = deliveryPlace;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
