package com.example.deepakp.gskgtsubd.gettersetter;

import java.util.ArrayList;

/**
 * Created by deepakp on 2/7/2017.
 */

public class WindowChecklistGetterSetter {

    String table_WINDOW_CHECKLIST;
    ArrayList<String> WINDOW_CD= new ArrayList<String>();
    ArrayList<String> CHECKLIST_CD= new ArrayList<String>();
    ArrayList<String> CHECKLIST= new ArrayList<String>();
    String ANSWER_CD="" ;
    String ANSWER= "";

    public String getANSWER_CD() {
        return ANSWER_CD;
    }

    public void setANSWER_CD(String ANSWER_CD) {
        this.ANSWER_CD = ANSWER_CD;
    }

    public String getANSWER() {
        return ANSWER;
    }

    public void setANSWER(String ANSWER) {
        this.ANSWER = ANSWER;
    }


    public ArrayList<String> getWINDOW_CD() {
        return WINDOW_CD;
    }

    public void setWINDOW_CD(String WINDOW_CD) {
        this.WINDOW_CD.add(WINDOW_CD);
    }

    public String getTable_WINDOW_CHECKLIST() {
        return table_WINDOW_CHECKLIST;
    }

    public void setTable_WINDOW_CHECKLIST(String table_WINDOW_CHECKLIST) {
        this.table_WINDOW_CHECKLIST = table_WINDOW_CHECKLIST;
    }

    public ArrayList<String> getCHECKLIST_CD() {
        return CHECKLIST_CD;
    }

    public void setCHECKLIST_CD(String CHECKLIST_CD) {
        this.CHECKLIST_CD.add(CHECKLIST_CD);
    }

    public ArrayList<String> getCHECKLIST() {
        return CHECKLIST;
    }

    public void setCHECKLIST(String CHECKLIST) {
        this.CHECKLIST.add(CHECKLIST);
    }
}
