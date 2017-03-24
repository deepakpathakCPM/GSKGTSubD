package com.example.deepakp.gskgtsubd.gettersetter;

import java.util.ArrayList;

/**
 * Created by deepakp on 2/7/2017.
 */

public class WindowMappingGetterSetter {

    String table_WINDOW_MAPPING;
    ArrayList<String> STATE_CD=new ArrayList<String>();
    ArrayList<String> STORETYPE_CD=new ArrayList<String>();
    ArrayList<String> WINDOW_CD=new ArrayList<String>();

    public String getTable_WINDOW_MAPPING() {
        return table_WINDOW_MAPPING;
    }

    public void setTable_WINDOW_MAPPING(String table_WINDOW_MAPPING) {
        this.table_WINDOW_MAPPING = table_WINDOW_MAPPING;
    }

    public ArrayList<String> getSTATE_CD() {
        return STATE_CD;
    }

    public void setSTATE_CD(String STATE_CD) {
        this.STATE_CD.add(STATE_CD);
    }

    public ArrayList<String> getWINDOW_CD() {
        return WINDOW_CD;
    }

    public void setWINDOW_CD(String WINDOW_CD) {
        this.WINDOW_CD.add(WINDOW_CD);
    }

    public ArrayList<String> getSTORETYPE_CD() {
        return STORETYPE_CD;
    }

    public void setSTORETYPE_CD(String STORETYPE_CD) {
        this.STORETYPE_CD.add(STORETYPE_CD);
    }
}
