package com.example.deepakp.gskgtsubd.gettersetter;

import java.util.ArrayList;

/**
 * Created by deepakp on 2/16/2017.
 */

public class BrandMasterGetterSetter {

    String table_BrandMaster;
    ArrayList<String> BRAND_CD =new ArrayList<String>();
    ArrayList<String> BRAND =new ArrayList<String>();
    ArrayList<String> CATEGORY_CD =new ArrayList<String>();
    ArrayList<String> COMPANY_CD =new ArrayList<String>();
    ArrayList<String> BRAND_SEQUENCE =new ArrayList<String>();

    public String getTable_BrandMaster() {
        return table_BrandMaster;
    }

    public void setTable_BrandMaster(String table_BrandMaster) {
        this.table_BrandMaster = table_BrandMaster;
    }

    public ArrayList<String> getBRAND_CD() {
        return BRAND_CD;
    }

    public void setBRAND_CD(String BRAND_CD) {
        this.BRAND_CD.add(BRAND_CD);
    }

    public ArrayList<String> getBRAND() {
        return BRAND;
    }

    public void setBRAND(String BRAND) {
        this.BRAND.add(BRAND);
    }

    public ArrayList<String> getCATEGORY_CD() {
        return CATEGORY_CD;
    }

    public void setCATEGORY_CD(String CATEGORY_CD) {
        this.CATEGORY_CD.add(CATEGORY_CD);
    }

    public ArrayList<String> getCOMPANY_CD() {
        return COMPANY_CD;
    }

    public void setCOMPANY_CD(String COMPANY_CD) {
        this.COMPANY_CD.add(COMPANY_CD);
    }

    public ArrayList<String> getBRAND_SEQUENCE() {
        return BRAND_SEQUENCE;
    }

    public void setBRAND_SEQUENCE(String BRAND_SEQUENCE) {
        this.BRAND_SEQUENCE.add(BRAND_SEQUENCE);
    }
}
