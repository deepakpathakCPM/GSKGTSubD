package com.example.deepakp.gskgtsubd.gettersetter;

import java.io.Serializable;

/**
 * Created by gagang on 21-10-2016.
 */

public class AddNewStoreGetterSetter implements Serializable {


    public int getKey_id() {
        return key_id;
    }

    public void setKey_id(int key_id) {
        this.key_id = key_id;
    }

    int key_id =0;
    String ed_StoreName = "";
    String ed_RetailerName = "";
    String ed_Address = "";
    String ed_Landmark = "";
    String City = "";
    String Town = "";
    String state = "";
    String ed_Phone = "";
    String dob = "";
    String doa = "";
    String hasSmartphone = "";
    String subDistributerName = "";
    String img_Camera="";
    String Upload_Status = "";
    String StoreType = "";
    String CHECKOUT_STATUS = "";
    int City_CD;
    int Town_CD;
    int state_cd;
    int store_id;
    int storeType_CD;
    int smartphoneAnswer_cd;


    public String getTown() {
        return Town;
    }

    public int getTown_CD() {
        return Town_CD;
    }

    public void setTown_CD(int town_CD) {
        Town_CD = town_CD;
    }

    public void setTown(String town) {
        Town = town;
    }

    public int getCoverage_id() {
        return coverage_id;
    }

    public void setCoverage_id(int coverage_id) {
        this.coverage_id = coverage_id;
    }

    int coverage_id=0 ;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getState_cd() {
        return state_cd;
    }


    public void setState_cd(int state_cd) {
        this.state_cd = state_cd;
    }

    public String getCHECKOUT_STATUS() {
        return CHECKOUT_STATUS;
    }

    public void setCHECKOUT_STATUS(String CHECKOUT_STATUS) {
        this.CHECKOUT_STATUS = CHECKOUT_STATUS;
    }


    public int getSmartphoneAnswer_cd() {
        return smartphoneAnswer_cd;
    }

    public void setSmartphoneAnswer_cd(int smartphoneAnswer_cd) {
        this.smartphoneAnswer_cd = smartphoneAnswer_cd;
    }

    public String getEd_RetailerName() {
        return ed_RetailerName;
    }

    public void setEd_RetailerName(String ed_RetailerName) {
        this.ed_RetailerName = ed_RetailerName;
    }

    public String getEd_Landmark() {
        return ed_Landmark;
    }

    public void setEd_Landmark(String ed_Landmark) {
        this.ed_Landmark = ed_Landmark;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDoa() {
        return doa;
    }

    public void setDoa(String doa) {
        this.doa = doa;
    }

    public String getHasSmartphone() {
        return hasSmartphone;
    }

    public void setHasSmartphone(String hasSmartphone) {
        this.hasSmartphone = hasSmartphone;
    }

    public String getSubDistributerName() {
        return subDistributerName;
    }

    public void setSubDistributerName(String subDistributerName) {
        this.subDistributerName = subDistributerName;
    }


    public String getStoreType() {
        return StoreType;
    }

    public void setStoreType(String storeType) {
        StoreType = storeType;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public int getStoreType_CD() {
        return storeType_CD;
    }

    public void setStoreType_CD(int storeType_CD) {
        this.storeType_CD = storeType_CD;
    }

    public int getCity_CD() {
        return City_CD;
    }

    public void setCity_CD(int city_CD) {
        City_CD = city_CD;
    }

    public String getEd_StoreName() {
        return ed_StoreName;
    }

    public void setEd_StoreName(String ed_StoreName) {
        this.ed_StoreName = ed_StoreName;
    }

    public String getEd_Address() {
        return ed_Address;
    }

    public void setEd_Address(String ed_Address) {
        this.ed_Address = ed_Address;
    }

    public String getImg_Camera() {
        return img_Camera;
    }

    public void setImg_Camera(String img_Camera) {
        this.img_Camera = img_Camera;
    }


    public String getEd_Phone() {
        return ed_Phone;
    }

    public void setEd_Phone(String ed_Phone) {
        this.ed_Phone = ed_Phone;
    }


    public String getUpload_Status() {
        return Upload_Status;
    }

    public void setUpload_Status(String upload_Status) {
        Upload_Status = upload_Status;
    }


    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }
}
