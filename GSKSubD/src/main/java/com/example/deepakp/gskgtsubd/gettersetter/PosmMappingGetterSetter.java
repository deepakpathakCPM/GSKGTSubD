package com.example.deepakp.gskgtsubd.gettersetter;

import java.util.ArrayList;

/**
 * Created by deepakp on 3/3/2017.
 */

public class PosmMappingGetterSetter {

    String table_POSMMAPPING;
    ArrayList<String> STATE_CD = new ArrayList<>();
    ArrayList<String> STORETYPE_CD = new ArrayList<>();
    ArrayList<String> BRAND_CD = new ArrayList<>();
    ArrayList<String> POSM_CD = new ArrayList<>();

    public String getTable_POSMMAPPING() {
        return table_POSMMAPPING;
    }

    public void setTable_POSMMAPPING(String table_POSMMAPPING) {
        this.table_POSMMAPPING = table_POSMMAPPING;
    }

    public ArrayList<String> getSTATE_CD() {
        return STATE_CD;
    }

    public void setSTATE_CD(String STATE_CD) {
        this.STATE_CD.add(STATE_CD);
    }

    public ArrayList<String> getSTORETYPE_CD() {
        return STORETYPE_CD;
    }

    public void setSTORETYPE_CD(String STORETYPE_CD) {
        this.STORETYPE_CD.add(STORETYPE_CD);
    }

    public ArrayList<String> getBRAND_CD() {
        return BRAND_CD;
    }

    public void setBRAND_CD(String BRAND_CD) {
        this.BRAND_CD.add(BRAND_CD);
    }

    public ArrayList<String> getPOSM_CD() {
        return POSM_CD;
    }

    public void setPOSM_CD(String POSM_CD) {
        this.POSM_CD.add(POSM_CD);
    }
}
