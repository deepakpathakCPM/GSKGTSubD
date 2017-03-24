package com.example.deepakp.gskgtsubd.gettersetter;

import java.util.ArrayList;

/**
 * Created by deepakp on 2/7/2017.
 */

public class StateMasterGetterSetter {

    String table_STATE_MASTER;
    ArrayList<String> STATE_CD=new ArrayList<String>();
    ArrayList<String> STATE=new ArrayList<String>();

    public ArrayList<String> getSTATE() {
        return STATE;
    }

    public void setSTATE(String STATE) {
        this.STATE.add(STATE);
    }

    public ArrayList<String> getSTATE_CD() {
        return STATE_CD;
    }

    public void setSTATE_CD(String STATE_CD) {
        this.STATE_CD.add(STATE_CD);
    }

    public String getTable_STATE_MASTER() {
        return table_STATE_MASTER;
    }

    public void setTable_STATE_MASTER(String table_STATE_MASTER) {
        this.table_STATE_MASTER = table_STATE_MASTER;
    }
}
