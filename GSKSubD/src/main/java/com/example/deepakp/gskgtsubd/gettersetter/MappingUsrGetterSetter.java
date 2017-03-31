package com.example.deepakp.gskgtsubd.gettersetter;

import java.util.ArrayList;

/**
 * Created by deepakp on 3/29/2017.
 */

public class MappingUsrGetterSetter {

    String table_mappingUsr;
    ArrayList<String> USR_CD = new ArrayList<String>();
    ArrayList<String> USR = new ArrayList<String>();

    public String getTable_mappingUsr() {
        return table_mappingUsr;
    }

    public void setTable_mappingUsr(String table_mappingUsr) {
        this.table_mappingUsr = table_mappingUsr;
    }

    public ArrayList<String> getUSR_CD() {
        return USR_CD;
    }

    public void setUSR_CD(String USR_CD) {
        this.USR_CD.add(USR_CD);
    }

    public ArrayList<String> getUSR() {
        return USR;
    }

    public void setUSR(String USR) {
        this.USR.add(USR);
    }
}
