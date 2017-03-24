package com.example.deepakp.gskgtsubd.gettersetter;

import java.util.ArrayList;

/**
 * Created by deepakp on 2/7/2017.
 */

public class CityMasterGetterSetter {

    String table_CityMaster;
    ArrayList<String> CITY_CD =new ArrayList<String>();
    ArrayList<String> CITY =new ArrayList<String>();
    ArrayList<String> STATE_CD =new ArrayList<String>();

    public ArrayList<String> getSTATE_CD() {
        return STATE_CD;
    }

    public void setSTATE_CD(String STATE_CD) {
        this.STATE_CD.add(STATE_CD);
    }

    public ArrayList<String> getCITY() {
        return CITY;
    }

    public void setCITY(String CITY) {
        this.CITY.add(CITY);
    }

    public ArrayList<String> getCITY_CD() {
        return CITY_CD;
    }

    public void setCITY_CD(String CITY_CD) {
        this.CITY_CD.add(CITY_CD);
    }

    public String getTable_CityMaster() {
        return table_CityMaster;
    }

    public void setTable_CityMaster(String table_CityMaster) {
        this.table_CityMaster = table_CityMaster;
    }



}
