package com.example.deepakp.gskgtsubd.gettersetter;

import java.util.ArrayList;

/**
 * Created by deepakp on 2/7/2017.
 */

public class WindowMasterGetterSetter {

    String table_WINDOW_MASTER;
    boolean WINDOW_EXIST = true;
    String WINDOW_IMAGE = "";

    String key_id;

    public String getPaidOrNonpaid() {
        return paidOrNonpaid;
    }

    public void setPaidOrNonpaid(String paidOrNonpaid) {
        this.paidOrNonpaid = paidOrNonpaid;
    }

    String paidOrNonpaid="0";
    int cameraIcon;
    ArrayList<String> WINDOW_CD=new ArrayList<String>();
    ArrayList<String> WINDOW=new ArrayList<String>();
    ArrayList<String> SKU_HOLD=new ArrayList<String>();
    ArrayList<String> PLANOGRAM_IMAGE=new ArrayList<String>();

    public int getCameraIcon() {
        return cameraIcon;
    }

    public void setCameraIcon(int cameraIcon) {
        this.cameraIcon = cameraIcon;
    }
    public String getKey_id() {
        return key_id;
    }

    public void setKey_id(String key_id) {
        this.key_id = key_id;
    }


    public boolean isWINDOW_EXIST() {
        return WINDOW_EXIST;
    }

    public void setWINDOW_EXIST(boolean WINDOW_EXIST) {
        this.WINDOW_EXIST = WINDOW_EXIST;
    }

    public String getWINDOW_IMAGE() {
        return WINDOW_IMAGE;
    }

    public void setWINDOW_IMAGE(String WINDOW_IMAGE) {
        this.WINDOW_IMAGE = WINDOW_IMAGE;
    }

    public ArrayList<String> getWINDOW() {
        return WINDOW;
    }

    public void setWINDOW(String WINDOW) {
        this.WINDOW.add(WINDOW);
    }

    public ArrayList<String> getWINDOW_CD() {
        return WINDOW_CD;
    }

    public void setWINDOW_CD(String WINDOW_CD) {
        this.WINDOW_CD.add(WINDOW_CD);
    }

    public String getTable_WINDOW_MASTER() {
        return table_WINDOW_MASTER;
    }

    public void setTable_WINDOW_MASTER(String table_WINDOW_MASTER) {
        this.table_WINDOW_MASTER = table_WINDOW_MASTER;
    }

    public ArrayList<String> getSKU_HOLD() {
        return SKU_HOLD;
    }

    public void setSKU_HOLD(String SKU_HOLD) {
        this.SKU_HOLD.add(SKU_HOLD);
    }

    public ArrayList<String> getPLANOGRAM_IMAGE() {
        return PLANOGRAM_IMAGE;
    }

    public void setPLANOGRAM_IMAGE(String PLANOGRAM_IMAGE) {
        this.PLANOGRAM_IMAGE.add(PLANOGRAM_IMAGE);
    }

}
