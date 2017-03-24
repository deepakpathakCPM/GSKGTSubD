package com.example.deepakp.gskgtsubd.gettersetter;

import java.util.ArrayList;

/**
 * Created by deepakp on 2/7/2017.
 */

public class StoreTypeMasterGetterSetter {

    String table_STORETYPE_MASTER;
    ArrayList<String> STORETYPE_CD =new ArrayList<String>();
    ArrayList<String> STORETYPE =new ArrayList<String>();

    public String getTable_STORETYPE_MASTER() {
        return table_STORETYPE_MASTER;
    }

    public void setTable_STORETYPE_MASTER(String table_STORETYPE_MASTER) {
        this.table_STORETYPE_MASTER = table_STORETYPE_MASTER;
    }

    public ArrayList<String> getSTORETYPE_CD() {
        return STORETYPE_CD;
    }

    public void setSTORETYPE_CD(String STORETYPE_CD) {
        this.STORETYPE_CD.add(STORETYPE_CD);
    }

    public ArrayList<String> getSTORETYPE() {
        return STORETYPE;
    }

    public void setSTORETYPE(String STORETYPE) {
        this.STORETYPE.add(STORETYPE);
    }
}
