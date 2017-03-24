package com.example.deepakp.gskgtsubd.gettersetter;

import java.util.ArrayList;

/**
 * Created by deepakp on 2/17/2017.
 */

public class AnswerMasterGetterSetter {

    ArrayList<String> ANSWER_CD = new ArrayList<String>();
    ArrayList<String> ANSWER = new ArrayList<String>();

    public ArrayList<String> getANSWER() {
        return ANSWER;
    }

    public void setANSWER(String ANSWER) {
        this.ANSWER.add(ANSWER);
    }

    public ArrayList<String> getANSWER_CD() {
        return ANSWER_CD;
    }

    public void setANSWER_CD(String ANSWER_CD) {
        this.ANSWER_CD.add(ANSWER_CD);
    }

}
