package com.example.deepakp.gskgtsubd.gettersetter;

import java.util.ArrayList;

/**
 * Created by deepakp on 2/7/2017.
 */

public class WindowChecklistAnswerGetterSetter {

    String table_WINDOW_CHECKLIST_ANSWER;
    ArrayList<String> ANSWER_CD=new ArrayList<String>();
    ArrayList<String> ANSWER=new ArrayList<String>();
    ArrayList<String> CHECKLIST_CD=new ArrayList<String>();

    public String getTable_WINDOW_CHECKLIST_ANSWER() {
        return table_WINDOW_CHECKLIST_ANSWER;
    }

    public void setTable_WINDOW_CHECKLIST_ANSWER(String table_WINDOW_CHECKLIST_ANSWER) {
        this.table_WINDOW_CHECKLIST_ANSWER = table_WINDOW_CHECKLIST_ANSWER;
    }

    public ArrayList<String> getANSWER_CD() {
        return ANSWER_CD;
    }

    public void setANSWER_CD(String ANSWER_CD) {
        this.ANSWER_CD.add(ANSWER_CD);
    }

    public ArrayList<String> getANSWER() {
        return ANSWER;
    }

    public void setANSWER(String ANSWER) {
        this.ANSWER.add(ANSWER);
    }

    public ArrayList<String> getCHECKLIST_CD() {
        return CHECKLIST_CD;
    }

    public void setCHECKLIST_CD(String CHECKLIST_CD) {
        this.CHECKLIST_CD.add(CHECKLIST_CD);
    }
}
