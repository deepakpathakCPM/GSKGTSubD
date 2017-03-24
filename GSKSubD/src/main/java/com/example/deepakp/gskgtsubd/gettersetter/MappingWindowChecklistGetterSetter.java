package com.example.deepakp.gskgtsubd.gettersetter;

import java.util.ArrayList;

/**
 * Created by deepakp on 2/7/2017.
 */

public class MappingWindowChecklistGetterSetter {

    String table_MAPPING_WINDOW_CHECKLIST;
    ArrayList<String> WINDOW_CD = new ArrayList<String>();
    ArrayList<String> CHECKLIST_CD = new ArrayList<String>();

    public String getTable_MAPPING_WINDOW_CHECKLIST() {
        return table_MAPPING_WINDOW_CHECKLIST;
    }

    public void setTable_MAPPING_WINDOW_CHECKLIST(String table_MAPPING_WINDOW_CHECKLIST) {
        this.table_MAPPING_WINDOW_CHECKLIST = table_MAPPING_WINDOW_CHECKLIST;
    }

    public ArrayList<String> getWINDOW_CD() {
        return WINDOW_CD;
    }

    public void setWINDOW_CD(String WINDOW_CD) {
        this.WINDOW_CD.add(WINDOW_CD);
    }

    public ArrayList<String> getCHECKLIST_CD() {
        return CHECKLIST_CD;
    }

    public void setCHECKLIST_CD(String CHECKLIST_CD) {
        this.CHECKLIST_CD.add(CHECKLIST_CD);
    }
}
