package com.example.deepakp.gskgtsubd.gettersetter;

import java.util.ArrayList;

/**
 * Created by deepakp on 3/3/2017.
 */

public class TownMasterGetterSetter {

    String table_TOWN_MASTER;
    ArrayList<String> TOWN_CD=new ArrayList();
    ArrayList<String> TOWN=new ArrayList();
    ArrayList<String> CITY_CD=new ArrayList();



    public ArrayList<String> getTOWN() {
        return TOWN;
    }

    public void setTOWN(String TOWN) {
        this.TOWN.add(TOWN);
    }

    public ArrayList<String> getCITY_CD() {
        return CITY_CD;
    }

    public void setCITY_CD(String CITY_CD) {
        this.CITY_CD.add(CITY_CD);
    }

    public ArrayList<String> getTOWN_CD() {
        return TOWN_CD;
    }

    public void setTOWN_CD(String TOWN_CD) {
        this.TOWN_CD.add(TOWN_CD);
    }

    public String getTable_TOWN_MASTER() {
        return table_TOWN_MASTER;
    }

    public void setTable_TOWN_MASTER(String table_TOWN_MASTER) {
        this.table_TOWN_MASTER = table_TOWN_MASTER;
    }

}
