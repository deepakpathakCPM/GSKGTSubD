package com.example.deepakp.gskgtsubd.gettersetter;

import java.util.ArrayList;

/**
 * Created by deepakp on 2/16/2017.
 */

public class PosmMasterGetterSetter {

    String table_POSMMaster;
    ArrayList<String> POSM_CD =new ArrayList<String>();
    ArrayList<String> POSM =new ArrayList<String>();

    public String getTable_POSMMaster() {
        return table_POSMMaster;
    }

    public void setTable_POSMMaster(String table_POSMMaster) {
        this.table_POSMMaster = table_POSMMaster;
    }

    public ArrayList<String> getPOSM_CD() {
        return POSM_CD;
    }

    public void setPOSM_CD(String POSM_CD) {
        this.POSM_CD.add(POSM_CD);
    }

    public ArrayList<String> getPOSM() {
        return POSM;
    }

    public void setPOSM(String POSM) {
        this.POSM.add(POSM);
    }
}
