package com.example.deepakp.gskgtsubd.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.deepakp.gskgtsubd.R;
import com.example.deepakp.gskgtsubd.bean.CoverageBean;
import com.example.deepakp.gskgtsubd.bean.TableBean;
import com.example.deepakp.gskgtsubd.constants.CommonString;
import com.example.deepakp.gskgtsubd.gettersetter.AddNewStoreGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.AnswerMasterGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.BrandMasterGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.CityMasterGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.JCPMasterGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.LineEntryGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.MappingWindowChecklistGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.NonWorkingReasonGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.PosmEntryGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.PosmMappingGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.PosmMasterGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.StateMasterGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.StoreTypeMasterGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.TownMasterGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.WindowChecklistAnswerGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.WindowChecklistGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.WindowMappingGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.WindowMasterGetterSetter;
import com.example.deepakp.gskgtsubd.utilities.CommonFunctions;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by yadavendras on 21-06-2016.
 */
public class Database extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = CommonString.DATABASE_NAME;
    public static final int DATABASE_VERSION = 2;
    private SQLiteDatabase db;

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void open() {
        try {
            db = this.getWritableDatabase();
        } catch (Exception e) {
            Log.e("Database Exception", "Database is not open " + e.getMessage());
        }
    }

    public void close() {
        db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TableBean.getTable_JCPMaster());
        db.execSQL(TableBean.getTable_CityMaster());
        db.execSQL(TableBean.getTable_StateMaster());
        db.execSQL(TableBean.getTable_StoreTypeMaster());
        db.execSQL(TableBean.getTable_WindowMaster());
        db.execSQL(TableBean.getTable_windowChecklist());
        db.execSQL(TableBean.getTable_mappingWindowChecklist());
        db.execSQL(TableBean.getTable_windowChecklistAnswer());
        db.execSQL(TableBean.getTable_WindowMapping());
        db.execSQL(TableBean.getTable_BrandMaster());
        db.execSQL(TableBean.getTable_PosmMaster());
        db.execSQL(TableBean.getTable_TownMaster());
        db.execSQL(TableBean.getTable_posmMapping());
        db.execSQL(TableBean.getNonworkingtable());
        //   db.execSQL(CommonString.CREATE_TABLE_INSERT_ORDERENTRYHEADER_DATA);
        db.execSQL(CommonString.CREATE_TABLE_SECONDARY_WINDOW_CHILD_DATA);
        db.execSQL(CommonString.CREATE_TABLE_SECONDARY_WINDOW_HEADER_DATA);
        db.execSQL(CommonString.CREATE_TABLE_POSM_DATA);
        db.execSQL(CommonString.CREATE_TABLE_LINE_ENTRY);
        db.execSQL(CommonString.CREATE_TABLE_COVERAGE_DATA);
        db.execSQL(CommonString.CREATE_TABLE_ADD_NEW_STORE);

        db.execSQL(CommonString.CREATE_TABLE_ADDSTORE_SECONDARY_WINDOW_CHILD_DATA);
        db.execSQL(CommonString.CREATE_TABLE_ADDSTORE_SECONDARY_WINDOW_HEADER_DATA);
        db.execSQL(CommonString.CREATE_TABLE_ADDSTORE_POSM_DATA);
        db.execSQL(CommonString.CREATE_TABLE_ADDSTORE_LINE_ENTRY);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public void deleteAllTables() {
        // DELETING TABLES
        db.delete(CommonString.TABLE_COVERAGE_DATA, null, null);

        db.delete(CommonString.TABLE_POSM_DATA, null, null);
        db.delete(CommonString.TABLE_SECONDARY_WINDOW_HEADER_DATA, null, null);
        db.delete(CommonString.TABLE_SECONDARY_WINDOW_CHILD_DATA, null, null);
        db.delete(CommonString.TABLE_ADD_LINE_ENTRY, null, null);
    }

    public void deleteSpecificTables(String coverage_id) {
        // DELETING TABLES
        db.delete(CommonString.TABLE_COVERAGE_DATA, CommonString.KEY_ID + "= '" + coverage_id + "'", null);

        db.delete(CommonString.TABLE_POSM_DATA, CommonString.KEY_COVERAGE_ID + "= '" + coverage_id + "'", null);
        db.delete(CommonString.TABLE_SECONDARY_WINDOW_HEADER_DATA, CommonString.KEY_COVERAGE_ID + "= '" + coverage_id + "'", null);
        db.delete(CommonString.TABLE_SECONDARY_WINDOW_CHILD_DATA, CommonString.KEY_COVERAGE_ID + "= '" + coverage_id + "'", null);
        db.delete(CommonString.TABLE_ADD_LINE_ENTRY, CommonString.KEY_COVERAGE_ID + "= '" + coverage_id + "'", null);
    }

    public Cursor rawQuery(String query, String[] arg) {
        Cursor cr = db.rawQuery(query, arg);
        return cr;
    }

    public void delete(String query, String whereClause, String[] arg) {
        db.delete(query, whereClause, arg);
    }


    //insert CityMaster data
    public void insertCityMasterData(CityMasterGetterSetter data) {

        db.delete("CITY_MASTER", null, null);
        ContentValues values = new ContentValues();

        try {
            for (int i = 0; i < data.getCITY_CD().size(); i++) {
                values.put("CITY_CD", Integer.parseInt(data.getCITY_CD().get(i)));
                values.put("CITY", data.getCITY().get(i));
                values.put("STATE_CD", Integer.parseInt(data.getSTATE_CD().get(i)));

                db.insert("CITY_MASTER", null, values);
            }

        } catch (Exception ex) {
            Log.d("Error in Inserting CITY_MASTER", ex.toString());
        }

    }

    // getNonWorkingData
    public ArrayList<NonWorkingReasonGetterSetter> getNonWorkingData() {
        Log.d("FetchingAssetdata--------------->Start<------------",
                "------------------");
        ArrayList<NonWorkingReasonGetterSetter> list = new ArrayList<NonWorkingReasonGetterSetter>();
        Cursor dbcursor = null;

        try {

            dbcursor = db
                    .rawQuery(
                            "SELECT * FROM NON_WORKING_REASON"
                            , null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    NonWorkingReasonGetterSetter sb = new NonWorkingReasonGetterSetter();


                    sb.setReason_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("REASON_CD")));

                    sb.setReason(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("REASON")));

                    sb.setEntry_allow(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("ENTRY_ALLOW")));

                    sb.setIMAGE_ALLOW(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("IMAGE_ALLOW")));
                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Non working!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("Fetching non working data---------------------->Stop<-----------",
                "-------------------");
        return list;
    }


    //insert insertTownMasterData data
    public void insertTownMasterData(TownMasterGetterSetter data) {

        db.delete("TOWN_MASTER", null, null);
        ContentValues values = new ContentValues();

        try {
            for (int i = 0; i < data.getCITY_CD().size(); i++) {
                values.put("TOWN_CD", Integer.parseInt(data.getTOWN_CD().get(i)));
                values.put("TOWN", data.getTOWN().get(i));
                values.put("CITY_CD", Integer.parseInt(data.getCITY_CD().get(i)));

                db.insert("TOWN_MASTER", null, values);
            }

        } catch (Exception ex) {
            Log.d("Error in Inserting TOWN_MASTER", ex.toString());
        }

    }


    //insert insertTownMasterData data
    public void insertPosmMappingData(PosmMappingGetterSetter data) {

        db.delete("POSM_MAPPING", null, null);
        ContentValues values = new ContentValues();

        try {
            for (int i = 0; i < data.getSTATE_CD().size(); i++) {
                values.put("STATE_CD", Integer.parseInt(data.getSTATE_CD().get(i)));
                values.put("STORETYPE_CD", Integer.parseInt(data.getSTORETYPE_CD().get(i)));
                values.put("BRAND_CD", Integer.parseInt(data.getBRAND_CD().get(i)));
                values.put("POSM_CD", Integer.parseInt(data.getPOSM_CD().get(i)));

                db.insert("POSM_MAPPING", null, values);
            }

        } catch (Exception ex) {
            Log.d("Error in Inserting POSM_MAPPING", ex.toString());
        }

    }

    //insert JCP Master data
    public void insertJCPMasterData(JCPMasterGetterSetter data) {

        db.delete("JOURNEY_PLAN", null, null);
        ContentValues values = new ContentValues();

        try {
            for (int i = 0; i < data.getSTORE_CD().size(); i++) {
                values.put("STORE_CD", Integer.parseInt(data.getSTORE_CD().get(i)));
                values.put("EMP_CD", Integer.parseInt(data.getEMP_CD().get(i)));
                values.put("VISIT_DATE", (data.getVISIT_DATE().get(i)));
                values.put("KEYACCOUNT", (data.getKEYACCOUNT().get(i)));
                values.put("STORENAME", (data.getSTORENAME().get(i)));
                values.put("CITY", (data.getCITY().get(i)));
                values.put("STORETYPE", (data.getSTORETYPE().get(i)));
                values.put("STATE_CD", Integer.parseInt(data.getSTATE_CD().get(i)));
                values.put("STORETYPE_CD", Integer.parseInt(data.getSTORETYPE_CD().get(i)));
                values.put("UPLOAD_STATUS", (data.getUPLOAD_STATUS().get(i)));
                values.put("CHECKOUT_STATUS", (data.getCHECKOUT_STATUS().get(i)));
                values.put("GEO_TAG", (data.getGEO_TAG().get(i)));

                db.insert("JOURNEY_PLAN", null, values);
            }

        } catch (Exception ex) {
            Log.d("Error in Inserting JOURNEY_PLAN", ex.toString());
        }

    }


    //State Master Data
    public void insertStateMasterData(StateMasterGetterSetter data) {
        db.delete("STATE_MASTER", null, null);
        ContentValues values = new ContentValues();

        try {
            for (int i = 0; i < data.getSTATE_CD().size(); i++) {

                values.put("STATE_CD", Integer.parseInt(data.getSTATE_CD().get(i)));
                values.put("STATE", data.getSTATE().get(i));

                db.insert("STATE_MASTER", null, values);
            }

        } catch (Exception ex) {
            Log.d("Error in insertStateMasterData", ex.toString());
        }

    }


    public long insertLineEntryData(LineEntryGetterSetter data, String store_cd, String coverage_id) {
        db.delete(CommonString.TABLE_ADD_LINE_ENTRY, "STORE_CD = '" + store_cd + "' AND COVERAGE_ID = '" + coverage_id + "'", null);
        long id = 0;
        ContentValues values = new ContentValues();
        try {

            values.put("STORE_CD", Integer.parseInt(store_cd));
            values.put("COVERAGE_ID", Integer.parseInt(coverage_id));
            values.put("TOTAL_LINE_AVAILABLE", Integer.parseInt(data.getTotalLineAvailable()));
            if(data.getNoOfBuildSkuLine().equalsIgnoreCase(""))
            {
                values.put("NO_OF_SKULINE", "");
            }
            else
            {
                values.put("NO_OF_SKULINE", Integer.parseInt(data.getNoOfBuildSkuLine()));
            }

             if(data.getTotalBillAmount().equalsIgnoreCase(""))
            {
                values.put("TOTAL_BILL_AMOUNT", "");
            }
            else
            {
                values.put("TOTAL_BILL_AMOUNT", Integer.parseInt(data.getTotalBillAmount()));
            }

            id = db.insert(CommonString.TABLE_ADD_LINE_ENTRY, null, values);


        } catch (Exception ex) {
            Log.d("Error in  insertLineEntryData", ex.toString());
        }

        return id;
    }

    public long insertPosmEntryData(ArrayList<PosmEntryGetterSetter> data, String store_cd, String coverage_id) {

        long id = 0;
        db.delete(CommonString.TABLE_POSM_DATA, CommonString.KEY_STORE_CD + " = '" + store_cd + "' AND COVERAGE_ID = '" + coverage_id + "'", null);
        ContentValues values = new ContentValues();

        try {

            for (int i = 0; i < data.size(); i++) {

                values.put(CommonString.KEY_STORE_CD, Integer.parseInt(store_cd));
                values.put(CommonString.KEY_COVERAGE_ID, Integer.parseInt(coverage_id));
                values.put(CommonString.KEY_BRAND_CD, Integer.parseInt(data.get(i).getBrand_cd()));
                values.put(CommonString.KEY_BRAND, (data.get(i).getBrand()));
                values.put(CommonString.KEY_POSM_CD, Integer.parseInt(data.get(i).getPosm_cd()));
                values.put(CommonString.KEY_POSM, data.get(i).getPosm());
                values.put(CommonString.KEY_QUANTITY, data.get(i).getQuantity());
                values.put(CommonString.KEY_IMAGE, data.get(i).getImage());

                id = db.insert(CommonString.TABLE_POSM_DATA, null, values);
            }

        } catch (Exception ex) {
            Log.d("Excep  insertPosmEntryData",
                    ex.getMessage());
        }
        return id;
    }

    public void deletePosmEntryData(Long id) {

        try {
            db.delete(CommonString.TABLE_POSM_DATA, CommonString.KEY_ID + " = " + id, null);
        } catch (Exception ex) {
            Log.d("Excep delete Touchpoint data",
                    ex.getMessage());
        }
    }


    public long[] insertWindowData(ArrayList<WindowMasterGetterSetter> data, String store_cd, String coverage_id, String paidOrUnpaidStr) {

        long id[] = new long[data.size()];
        for (int i = 0; i < data.size(); i++) {
            db.delete(CommonString.TABLE_SECONDARY_WINDOW_CHILD_DATA, CommonString.KEY_COMMON_ID + " = '" + data.get(i).getKey_id() + "'", null);
        }

        db.delete(CommonString.TABLE_SECONDARY_WINDOW_HEADER_DATA, CommonString.KEY_STORE_CD + " = '" + store_cd + "' AND COVERAGE_ID = '" + coverage_id + "'", null);
        ContentValues values = new ContentValues();

        try {

            for (int i = 0; i < data.size(); i++) {
                values.put(CommonString.KEY_STORE_CD, Integer.parseInt(store_cd));
                values.put(CommonString.KEY_COVERAGE_ID, Integer.parseInt(coverage_id));
                values.put(CommonString.KEY_WINDOW_CD, Integer.parseInt(data.get(i).getWINDOW_CD().get(0)));
                values.put(CommonString.KEY_WINDOW, (data.get(i).getWINDOW().get(0)));
                values.put(CommonString.KEY_IS_EXISTS, (data.get(i).isWINDOW_EXIST()));
                values.put(CommonString.KEY_WINDOW_IMAGE, (data.get(i).getWINDOW_IMAGE()));
                values.put(CommonString.KEY_PAID_OR_NONPAID, paidOrUnpaidStr);

                id[i] = db.insert(CommonString.TABLE_SECONDARY_WINDOW_HEADER_DATA, null, values);
            }

        } catch (Exception ex) {
            Log.d("Excep  insertPosmEntryData",
                    ex.getMessage());
        }
        return id;
    }


    public long insertWindowChildData(long common_id[], String store_cd, String coverage_id, ArrayList<WindowMasterGetterSetter> windowList, HashMap<String, ArrayList<WindowChecklistGetterSetter>> map) {

        long id = 0;
        boolean isGood = true;
        //db.delete(CommonString.TABLE_SECONDARY_WINDOW_CHILD_DATA, CommonString.KEY_STORE_CD + " = " + store_cd, null);
        ContentValues values = new ContentValues();

        try {

            for (int i = 0; i < windowList.size(); i++) {
                ArrayList<WindowChecklistGetterSetter> list = map.get(windowList.get(i).getWINDOW().get(0));

                for (int j = 0; j < list.size(); j++) {

                    values.put(CommonString.KEY_COMMON_ID, (common_id[i]));
                    values.put(CommonString.KEY_STORE_CD, Integer.parseInt(store_cd));
                    values.put(CommonString.KEY_COVERAGE_ID, Integer.parseInt(coverage_id));
                    values.put(CommonString.KEY_WINDOW_CD, Integer.parseInt(windowList.get(i).getWINDOW_CD().get(0)));
                    values.put(CommonString.KEY_CHECKLIST_CD, Integer.parseInt(list.get(j).getCHECKLIST_CD().get(0)));
                    values.put(CommonString.KEY_CHECKLIST, (list.get(j).getCHECKLIST().get(0)));
                    values.put(CommonString.KEY_ANSWER_CD, Integer.parseInt(list.get(j).getANSWER_CD()));
                    values.put(CommonString.KEY_ANSWER, (list.get(j).getANSWER()));

                    id = db.insert(CommonString.TABLE_SECONDARY_WINDOW_CHILD_DATA, null, values);
                    if (id == 0) {
                        isGood = false;
                        break;
                    }

                }

                if (isGood == false) {
                    break;
                }
            }

        } catch (Exception ex) {
            Log.d("Excep  insertWindowChildData",
                    ex.getMessage());
        }
        return id;
    }


    public ArrayList<PosmEntryGetterSetter> getPosmEntryData(String storeid, String coverage_id) {

        ArrayList<PosmEntryGetterSetter> list = new ArrayList<PosmEntryGetterSetter>();
        Cursor dbcursor = null;
        PosmEntryGetterSetter posmEntryGetterSetter;

        try {
            dbcursor = db.rawQuery("SELECT * from POSM_DATA where STORE_CD = " + storeid + " AND COVERAGE_ID = '" + coverage_id + "'"
                    , null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {

                    posmEntryGetterSetter = new PosmEntryGetterSetter();

                    posmEntryGetterSetter.setId(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_ID)));
                    posmEntryGetterSetter.setBrand(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_BRAND)));
                    posmEntryGetterSetter.setBrand_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_BRAND_CD)));
                    posmEntryGetterSetter.setPosm_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_POSM_CD)));
                    posmEntryGetterSetter.setPosm(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_POSM)));
                    posmEntryGetterSetter.setQuantity(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_QUANTITY)));
                    posmEntryGetterSetter.setImage(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE)));

                    list.add(posmEntryGetterSetter);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching comptetion!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("comptetion---------------------->Stop<-----------",
                "-------------------");
        return list;

    }

//    public void deleteISDDataOnNoData() {
//        db.delete("STORE_ISD", null, null);
//    }

    //Quiz Question data
    public void insertStoreTypeData(StoreTypeMasterGetterSetter data) {

        db.delete("STORETYPE_MASTER", null, null);
        ContentValues values = new ContentValues();

        try {
            for (int i = 0; i < data.getSTORETYPE_CD().size(); i++) {

                values.put("STORETYPE_CD", Integer.parseInt(data.getSTORETYPE_CD().get(i)));
                values.put("STORETYPE", data.getSTORETYPE().get(i));

                db.insert("STORETYPE_MASTER", null, values);
            }

        } catch (Exception ex) {
            Log.d("Error in insertStoreTypeData", ex.toString());
        }
    }

    //Audit Checklist data
    public void insertWindowMasterData(WindowMasterGetterSetter data) {
        db.delete("WINDOW_MASTER", null, null);
        ContentValues values = new ContentValues();

        try {
            for (int i = 0; i < data.getWINDOW_CD().size(); i++) {

                values.put("WINDOW_CD", Integer.parseInt(data.getWINDOW_CD().get(i)));
                values.put("WINDOW", data.getWINDOW().get(i));
                values.put("SKU_HOLD", data.getSKU_HOLD().get(i));
                values.put("PLANOGRAM_IMAGE", data.getPLANOGRAM_IMAGE().get(i));

                db.insert("WINDOW_MASTER", null, values);
            }

        } catch (Exception ex) {
            Log.d("Error in Insert WindowMasterData", ex.toString());
        }

    }


    //insertBrandMasterData
    public void insertBrandMasterData(BrandMasterGetterSetter data) {
        db.delete("BRAND_MASTER", null, null);
        ContentValues values = new ContentValues();
        try {
            for (int i = 0; i < data.getBRAND_CD().size(); i++) {

                values.put("BRAND_CD", Integer.parseInt(data.getBRAND_CD().get(i)));
                values.put("BRAND", data.getBRAND().get(i));
                values.put("CATEGORY_CD", Integer.parseInt(data.getCATEGORY_CD().get(i)));
                values.put("COMPANY_CD", Integer.parseInt(data.getCOMPANY_CD().get(i)));
                values.put("BRAND_SEQUENCE", Integer.parseInt(data.getBRAND_SEQUENCE().get(i)));

                db.insert("BRAND_MASTER", null, values);
            }

        } catch (Exception ex) {
            Log.d("Error in Insert insertBrandMasterData", ex.toString());
        }

    }

    //insertPosmMasterData
    public void insertPosmMasterData(PosmMasterGetterSetter data) {
        db.delete("POSM_MASTER", null, null);
        ContentValues values = new ContentValues();
        try {
            for (int i = 0; i < data.getPOSM_CD().size(); i++) {

                values.put("POSM_CD", Integer.parseInt(data.getPOSM_CD().get(i)));
                values.put("POSM", data.getPOSM().get(i));

                db.insert("POSM_MASTER", null, values);
            }

        } catch (Exception ex) {
            Log.d("Error in Insert insertPosmMasterData", ex.toString());
        }

    }

    // insertWindowChecklistData
    public void insertWindowChecklistData(WindowChecklistGetterSetter data) {

        db.delete("WINDOW_CHECKLIST", null, null);
        ContentValues values = new ContentValues();

        try {

            for (int i = 0; i < data.getCHECKLIST_CD().size(); i++) {

                values.put("CHECKLIST_CD", Integer.parseInt(data.getCHECKLIST_CD().get(i)));
                values.put("CHECKLIST", data.getCHECKLIST().get(i));

                db.insert("WINDOW_CHECKLIST", null, values);

            }

        } catch (Exception ex) {
            Log.d("Error in insertWindowChecklistData",
                    ex.toString());
        }
    }


    // insertWindowChecklistData
    public void insertmappingWindowChecklistData(MappingWindowChecklistGetterSetter data) {

        db.delete("MAPPING_WINDOW_CHECKLIST", null, null);
        ContentValues values = new ContentValues();

        try {

            for (int i = 0; i < data.getWINDOW_CD().size(); i++) {

                values.put("WINDOW_CD", Integer.parseInt(data.getWINDOW_CD().get(i)));
                values.put("CHECKLIST_CD", Integer.parseInt(data.getCHECKLIST_CD().get(i)));

                db.insert("MAPPING_WINDOW_CHECKLIST", null, values);

            }

        } catch (Exception ex) {
            Log.d("Error in insertmappingWindowChecklistData",
                    ex.toString());
        }
    }

    // insertWindowChecklistAnswerData
    public void insertWindowChecklistAnswerData(WindowChecklistAnswerGetterSetter data) {

        db.delete("WINDOW_CHECKLIST_ANSWER", null, null);
        ContentValues values = new ContentValues();

        try {

            for (int i = 0; i < data.getANSWER_CD().size(); i++) {

                values.put("ANSWER_CD", Integer.parseInt(data.getANSWER_CD().get(i)));
                values.put("ANSWER", data.getANSWER().get(i));
                values.put("CHECKLIST_CD", Integer.parseInt(data.getCHECKLIST_CD().get(i)));

                db.insert("WINDOW_CHECKLIST_ANSWER", null, values);

            }

        } catch (Exception ex) {
            Log.d("Error in insertWindowChecklistAnswerData",
                    ex.toString());
        }
    }

    // insertWindowChecklistAnswerData
    public void insertWindowMappingData(WindowMappingGetterSetter data) {

        db.delete("WINDOW_MAPPING", null, null);
        ContentValues values = new ContentValues();

        try {

            for (int i = 0; i < data.getWINDOW_CD().size(); i++) {

                values.put("STATE_CD", Integer.parseInt(data.getSTATE_CD().get(i)));
                values.put("STORETYPE_CD", data.getSTORETYPE_CD().get(i));
                values.put("WINDOW_CD", Integer.parseInt(data.getWINDOW_CD().get(i)));

                db.insert("WINDOW_MAPPING", null, values);

            }

        } catch (Exception ex) {
            Log.d("Error in insertWindowMappingData",
                    ex.toString());
        }
    }


//Non Working data

    public void insertNonWorkingReasonData(NonWorkingReasonGetterSetter data) {

        db.delete("NON_WORKING_REASON", null, null);
        ContentValues values = new ContentValues();

        try {

            for (int i = 0; i < data.getReason_cd().size(); i++) {

                values.put("REASON_CD", Integer.parseInt(data.getReason_cd().get(i)));
                values.put("REASON", data.getReason().get(i));
                values.put("ENTRY_ALLOW", data.getEntry_allow().get(i));
                values.put("IMAGE_ALLOW", data.getIMAGE_ALLOW().get(i));


                db.insert("NON_WORKING_REASON", null, values);

            }

        } catch (Exception ex) {
            Log.d("Database Exception while Insert Non Working Data ",
                    ex.toString());
        }

    }


    public void updateStoreStatusOnCheckout(String storeid, String visitdate,
                                            String status) {

        try {
            ContentValues values = new ContentValues();
            values.put(CommonString.KEY_CHECKOUT_STATUS, status);

            db.update("JOURNEY_PLAN", values,
                    CommonString.KEY_STORE_CD + "='" + storeid + "' AND "
                            + CommonString.KEY_VISIT_DATE + "='" + visitdate
                            + "'", null);
        } catch (Exception e) {

        }
    }

    public void updateStoreStatus(String storeid, String visitdate,
                                  String status) {

        try {
            ContentValues values = new ContentValues();
            values.put(CommonString.KEY_UPLOAD_STATUS, status);

            db.update("JOURNEY_PLAN", values,
                    CommonString.KEY_STORE_CD + "='" + storeid + "' AND "
                            + CommonString.KEY_VISIT_DATE + "='" + visitdate
                            + "'", null);
        } catch (Exception e) {

        }
    }

    public void updateStoreStatusOnStoreinfo(int coverage, String status) {

        try {
            ContentValues values = new ContentValues();
            values.put(CommonString.KEY_UPLOAD_STATUS, status);

            db.update("Add_NewStore", values,
                    CommonString.KEY_COVERAGE_ID + "='" + coverage + "'", null);
        } catch (Exception e) {

        }
    }


    /*
        public void deletePerformanceDataOnNoData() {
            db.delete("ISD_PERFORMANCE", null, null);
        }
        //get Posm Data
        public ArrayList<PosmGetterSetter> getPOSMData() {
            Log.d("FetchStoreType>Start<--",
                    "----");
            ArrayList<PosmGetterSetter> list = new ArrayList<>();
            Cursor dbcursor = null;

            try {
                dbcursor = db.rawQuery("SELECT DISTINCT POSM_CD, POSM from POSM_MASTER"
                        , null);

                if (dbcursor != null) {
                    dbcursor.moveToFirst();
                    while (!dbcursor.isAfterLast()) {
                        PosmGetterSetter df = new PosmGetterSetter();


                        df.setPosm_cd(dbcursor.getString(dbcursor
                                .getColumnIndexOrThrow("POSM_CD")));
                        df.setPosm(dbcursor.getString(dbcursor
                                .getColumnIndexOrThrow("POSM")));
                        df.setQuantity("");
                        df.setPosm_img_str("");
                        //df.setStoredf("");

                        list.add(df);
                        dbcursor.moveToNext();
                    }
                    dbcursor.close();
                    return list;
                }

            } catch (Exception e) {
                Log.d("Ex fetching Posm!",
                        e.toString());
                return list;
            }

            Log.d("FetcPosm data->Stop<-",
                    "-");
            return list;

        }

        //Insert Answered data
        public void insertAnsweredData(ArrayList<QuizAnwserGetterSetter> data, String store_cd, long mid) {

            //db.delete(CommonString.TABLE_STORE_DATA, null, null);
            ContentValues values = new ContentValues();
            long key;

            try {
                for (int i = 0; i < data.size(); i++) {

                    values.put(CommonString.KEY_STORE_CD, store_cd);
                    values.put(CommonString.KEY_ISD_CD, data.get(i).getIsd_cd());
                    values.put(CommonString.KEY_TOPIC_CD, data.get(i).getTopic_cd());
                    values.put(CommonString.KEY_QUESTION_CD, data.get(i).getQuestion_cd());
                    values.put(CommonString.KEY_ANSWER_CD, data.get(i).getAnswer_cd());
                    values.put(CommonString.KEY_ANSWER, data.get(i).getAnswer());
                    values.put(CommonString.KEY_TRAINING_MODE_CD, data.get(i).getTraining_mode_cd());
                    values.put(CommonString.KEY_MID, mid);

                    key = db.insert(CommonString.TABLE_ANSWERED_DATA, null, values);
                }
            } catch (Exception ex) {
                Log.d("DB Excep Answer Insert", ex.toString());
                //return 0;
            }
            // return 0;
        }

        //Insert New ISD data
        public void insertNewIsdData(EmpCdIsdGetterSetter data, String store_cd) {

            //db.delete(CommonString.TABLE_STORE_DATA, null, null);
            ContentValues values = new ContentValues();
            long key;

            try {

                values.put(CommonString.KEY_STORE_CD, store_cd);
                values.put(CommonString.KEY_ISD_CD, data.getIsd_cd());
                values.put(CommonString.KEY_ISD_NAME, data.getIsd());

                key = db.insert(CommonString.TABLE_NEW_ISD, null, values);

            } catch (Exception ex) {
                Log.d("DB Excep new isd Insert", ex.toString());
                //return 0;
            }
            // return 0;
        }

        //Insert Audit Checklist data
        public void insertAuditChecklistData(ArrayList<AuditChecklistGetterSetter> data, String store_cd, String isd_cd, long mid) {

            //db.delete(CommonString.TABLE_STORE_DATA, null, null);
            ContentValues values = new ContentValues();
            long key;

            try {
                for (int i = 0; i < data.size(); i++) {

                    values.put(CommonString.KEY_STORE_CD, store_cd);
                    values.put(CommonString.KEY_ISD_CD, isd_cd);
                    values.put(CommonString.KEY_CHECKLIST_CD, data.get(i).getCHECKLIST_CD().get(0));
                    values.put(CommonString.KEY_CHECKLIST, data.get(i).getCHECKLIST().get(0));
                    values.put(CommonString.KEY_AVAILABILITY, data.get(i).getAvailability());
                    values.put(CommonString.KEY_MID, mid);

                    key = db.insert(CommonString.TABLE_CHECKLIST_INSERTED_DATA, null, values);
                }
            } catch (Exception ex) {
                Log.d("DB Excep Audit Insert", ex.toString());
                //return 0;
            }
            // return 0;
        }

        //Insert new Employee data
        public long insertNewEmployeeData(AddNewEmployeeGetterSetter data, String store_cd) {
            //db.delete(CommonString.TABLE_STORE_DATA, null, null);
            ContentValues values = new ContentValues();
            long key = 0;

            try {
                // for (int i = 0; i < data.size(); i++) {
                values.put(CommonString.KEY_STORE_CD, store_cd);
                values.put(CommonString.KEY_NAME, data.getName());
                values.put(CommonString.KEY_EMAIL, data.getEmail());
                values.put(CommonString.KEY_PHONE_NO, data.getPhone());
                values.put(CommonString.KEY_IS_ISD, data.isIsd());

                key = db.insert(CommonString.TABLE_ADD_NEW_EMPLOYEE, null, values);
                // }
            } catch (Exception ex) {
                Log.d("DB Excep ", "Employee Insert" + ex.toString());
                //return 0;
            }
            return key;
        }

        //Store data
        public long insertStoreData(StoreDataGetterSetter data) {

            //db.delete(CommonString.TABLE_STORE_DATA, null, null);
            ContentValues values = new ContentValues();
            long key;

            try {
                // for (int i = 0; i < data.getPosm_cd().size(); i++) {

                values.put(CommonString.KEY_STORE_TYPE_CD, Integer.parseInt(data.getStore_type_cd()));
                values.put(CommonString.KEY_CITY, data.getCity());
                values.put(CommonString.KEY_EMAIL, data.getEmail());
                values.put(CommonString.KEY_LOCALITY, data.getLocality());
                values.put(CommonString.KEY_MARKET_NAME, data.getMarket_name());
                values.put(CommonString.KEY_OWNER_CONTACT_NO, data.getOwner_contactno());
                values.put(CommonString.KEY_OWNER_NAME, data.getOwner_name());
                values.put(CommonString.KEY_SHOP_NO, data.getShop_no());
                values.put(CommonString.KEY_STORE_IMAGE, data.getStore_img());
                values.put(CommonString.KEY_STORE_NAME, data.getStore_name());
                values.put(CommonString.KEY_STORE_TYPE, data.getStore_type());
                values.put(CommonString.KEY_TELEPHONE_NO_ONE, data.getTelephone1());
                values.put(CommonString.KEY_TELEPHONE_NO_TWO, data.getTelephone2());
                values.put(CommonString.KEY_LATITUDE, data.getLat());
                values.put(CommonString.KEY_LONGITUDE, data.getLon());
                values.put(CommonString.KEY_VISIT_DATE, data.getVisit_date());
                values.put(CommonString.KEY_UPLOAD_STATUS, data.getUploaod_status());

                key = db.insert(CommonString.TABLE_STORE_DATA, null, values);
                //  }
            } catch (Exception ex) {
                Log.d("DB Excep in POSM Insert", ex.toString());
                return 0;
            }
            return key;
        }

        //get Isd Performance Data
        public ArrayList<IsdPerformanceGetterSetter> getIsdPerfromanceData(String visit_date, String store_cd) {
            Log.d("FetchStorelist>Start<--",
                    "----");
            ArrayList<IsdPerformanceGetterSetter> list = new ArrayList<>();
            Cursor dbcursor = null;

            try {
                dbcursor = db.rawQuery("SELECT * from ISD_PERFORMANCE WHERE ISD_CD IN ( " + "SELECT ISD_CD from STORE_ISD where STORE_CD = '" + store_cd + "' )", null);

                if (dbcursor != null) {
                    dbcursor.moveToFirst();
                    while (!dbcursor.isAfterLast()) {
                        IsdPerformanceGetterSetter df = new IsdPerformanceGetterSetter();


                        df.setTRAINING_DATE(dbcursor.getString(dbcursor
                                .getColumnIndexOrThrow("TRAINING_DATE")));

                        df.setTOPIC(dbcursor.getString(dbcursor
                                .getColumnIndexOrThrow("TOPIC")));

                        df.setTRAINING_TYPE(dbcursor.getString(dbcursor
                                .getColumnIndexOrThrow("TRAINING_TYPE")));
                        df.setISD_CD(dbcursor.getString(dbcursor
                                .getColumnIndexOrThrow("ISD_CD")));
                        df.setISD(dbcursor.getString(dbcursor
                                .getColumnIndexOrThrow("ISD")));
                        df.setGROOMING_SCORE(dbcursor.getString(dbcursor
                                .getColumnIndexOrThrow("GROOMING_SCORE")));
                        df.setQUIZ_SCORE(dbcursor.getString(dbcursor
                                .getColumnIndexOrThrow("QUIZ_SCORE")));

                        list.add(df);
                        dbcursor.moveToNext();
                    }
                    dbcursor.close();
                    return list;
                }

            } catch (Exception e) {
                Log.d("Ex fetching Store!", e.toString());
                return list;
            }

            Log.d("Fetcstore data->Stop<-", "-");
            return list;

        }

        //Get Store ISD data
        public ArrayList<StoreISDGetterSetter> getStoreIsdData(String store_cd) {

            Log.d("FetchStoreType>Start<--",
                    "----");
            ArrayList<StoreISDGetterSetter> list = new ArrayList<>();
            Cursor dbcursor = null;

            try {
                dbcursor = db.rawQuery("SELECT * from STORE_ISD where STORE_CD = '" + store_cd + "'", null);

                if (dbcursor != null) {
                    dbcursor.moveToFirst();
                    while (!dbcursor.isAfterLast()) {
                        StoreISDGetterSetter posm = new StoreISDGetterSetter();

                        posm.setSTORE_CD(dbcursor.getString(dbcursor
                                .getColumnIndexOrThrow("STORE_CD")));
                        posm.setISD_CD(dbcursor.getString(dbcursor
                                .getColumnIndexOrThrow("ISD_CD")));
                        posm.setISD_NAME(dbcursor.getString(dbcursor.getColumnIndexOrThrow("ISD_NAME")));

                        list.add(posm);
                        dbcursor.moveToNext();
                    }
                    dbcursor.close();
                    return list;
                }

            } catch (Exception e) {
                Log.d("Ex fetching Posm!",
                        e.toString());
                return list;
            }

            Log.d("FetcPosm data->Stop<-",
                    "-");
            return list;

        }

        //Get Audit data
        public ArrayList<AuditChecklistGetterSetter> getAuditData() {

            Log.d("FetchAudit>Start<--",
                    "----");
            ArrayList<AuditChecklistGetterSetter> list = new ArrayList<>();
            Cursor dbcursor = null;

            try {
                dbcursor = db.rawQuery("SELECT * from AUDIT_CHECKLIST ", null);

                if (dbcursor != null) {
                    dbcursor.moveToFirst();
                    while (!dbcursor.isAfterLast()) {
                        AuditChecklistGetterSetter audit = new AuditChecklistGetterSetter();

                        audit.setCHECKLIST_CD(dbcursor.getString(dbcursor
                                .getColumnIndexOrThrow("CHECKLIST_CD")));
                        audit.setCHECKLIST(dbcursor.getString(dbcursor
                                .getColumnIndexOrThrow("CHECKLIST")));
                        audit.setAvailability(0);

                        list.add(audit);
                        dbcursor.moveToNext();
                    }
                    dbcursor.close();
                    return list;
                }

            } catch (Exception e) {
                Log.d("Ex fetching Audit!",
                        e.toString());
                return list;
            }

            Log.d("FetcAudit data->Stop<-",
                    "-");
            return list;

        }

        //Get Topic data
        public ArrayList<TrainingTopicGetterSetter> getTopicData() {

            Log.d("FetchTopic>Start<--",
                    "----");
            ArrayList<TrainingTopicGetterSetter> list = new ArrayList<>();
            Cursor dbcursor = null;

            try {
                dbcursor = db.rawQuery("SELECT * from TRAINING_TOPIC ", null);

                if (dbcursor != null) {
                    dbcursor.moveToFirst();
                    while (!dbcursor.isAfterLast()) {
                        TrainingTopicGetterSetter topic = new TrainingTopicGetterSetter();

                        topic.setTOPIC_CD(dbcursor.getString(dbcursor
                                .getColumnIndexOrThrow("TOPIC_CD")));
                        topic.setTOPIC(dbcursor.getString(dbcursor
                                .getColumnIndexOrThrow("TOPIC")));

                        list.add(topic);
                        dbcursor.moveToNext();
                    }
                    dbcursor.close();
                    return list;
                }

            } catch (Exception e) {
                Log.d("Ex fetching Audit!",
                        e.toString());
                return list;
            }

            Log.d("FetcTopic data->Stop<-",
                    "-");
            return list;

        }

        //Get Quiz Question data
        public ArrayList<QuizQuestionGettersetter> getQuizQuestionData(String topic_cd) {

            Log.d("FetchQuiz>Start<--",
                    "----");
            ArrayList<QuizQuestionGettersetter> list = new ArrayList<>();
            Cursor dbcursor = null;

            try {
                dbcursor = db.rawQuery("SELECT * from QUIZ_QUESTION where TOPIC_CD = '" + topic_cd + "'", null);

                if (dbcursor != null) {
                    dbcursor.moveToFirst();
                    while (!dbcursor.isAfterLast()) {
                        QuizQuestionGettersetter quiz = new QuizQuestionGettersetter();

                        quiz.setTOPIC_CD(dbcursor.getString(dbcursor
                                .getColumnIndexOrThrow("TOPIC_CD")));
                        quiz.setQUESTION_CD(dbcursor.getString(dbcursor
                                .getColumnIndexOrThrow("QUESTION_CD")));
                        quiz.setQUESTION(dbcursor.getString(dbcursor
                                .getColumnIndexOrThrow("QUESTION")));
                        quiz.setANSWER_CD(dbcursor.getString(dbcursor
                                .getColumnIndexOrThrow("ANSWER_CD")));
                        quiz.setANSWER(dbcursor.getString(dbcursor
                                .getColumnIndexOrThrow("ANSWER")));
                        quiz.setRIGHT_ANSWER(dbcursor.getString(dbcursor
                                .getColumnIndexOrThrow("RIGHT_ANSWER")));

                        list.add(quiz);
                        dbcursor.moveToNext();
                    }
                    dbcursor.close();
                    return list;
                }

            } catch (Exception e) {
                Log.d("Ex fetching Quiz!",
                        e.toString());
                return list;
            }

            Log.d("FetcQuiz data->Stop<-",
                    "-");
            return list;

        }

        //Get All distinct Quiz data
        public ArrayList<QuizQuestionGettersetter> getAllQuizData(String topic_cd) {

            Log.d("FetchQuizs>Start<--",
                    "----");
            ArrayList<QuizQuestionGettersetter> list = new ArrayList<>();
            Cursor dbcursor = null;

            try {
                dbcursor = db.rawQuery("SELECT DISTINCT QUESTION_CD, QUESTION from QUIZ_QUESTION where TOPIC_CD = '" + topic_cd + "'", null);

                if (dbcursor != null) {
                    dbcursor.moveToFirst();
                    while (!dbcursor.isAfterLast()) {
                        QuizQuestionGettersetter quiz = new QuizQuestionGettersetter();

                        quiz.setQUESTION_CD(dbcursor.getString(dbcursor
                                .getColumnIndexOrThrow("QUESTION_CD")));
                        quiz.setQUESTION(dbcursor.getString(dbcursor
                                .getColumnIndexOrThrow("QUESTION")));

                        list.add(quiz);
                        dbcursor.moveToNext();
                    }
                    dbcursor.close();
                    return list;
                }

            } catch (Exception e) {
                Log.d("Ex fetching Quizs!",
                        e.toString());
                return list;
            }

            Log.d("FetcQuizs data->Stop<-",
                    "-");
            return list;

        }

        //POSM List data
        public void insertPosmData(List<PosmGetterSetter> data, String common_id) {

            //db.delete(CommonString.TABLE_STORE_DATA, null, null);
            ContentValues values = new ContentValues();
            long key;

            try {
                for (int i = 0; i < data.size(); i++) {

                    values.put(CommonString.KEY_POSM_CD, Integer.parseInt(data.get(i).getPosm_cd().get(0)));
                    values.put(CommonString.KEY_POSM, data.get(i).getPosm().get(0));
                    values.put(CommonString.KEY_COMMON_ID, Long.parseLong(common_id));
                    values.put(CommonString.KEY_QUANTITY, data.get(i).getQuantity());
                    values.put(CommonString.KEY_POSM_IMAGE, data.get(i).getPosm_img_str());

                    db.insert(CommonString.TABLE_POSM_DATA, null, values);
                }
            } catch (Exception ex) {
                Log.d("DB Excep in POSM Insert", ex.toString());

            }

        }

        //get Posm stored Data
        public ArrayList<PosmGetterSetter> getPosmInsertedData(String key_id) {

            Log.d("FetchStoreType>Start<--",
                    "----");
            ArrayList<PosmGetterSetter> list = new ArrayList<>();
            Cursor dbcursor = null;

            try {
                dbcursor = db.rawQuery("SELECT * from " + CommonString.TABLE_POSM_DATA + " where " + CommonString.KEY_COMMON_ID + " = '" + key_id + "'", null);

                if (dbcursor != null) {
                    dbcursor.moveToFirst();
                    while (!dbcursor.isAfterLast()) {
                        PosmGetterSetter posm = new PosmGetterSetter();

                        posm.setPosm_cd(dbcursor.getString(dbcursor
                                .getColumnIndexOrThrow(CommonString.KEY_POSM_CD)));
                        posm.setPosm(dbcursor.getString(dbcursor
                                .getColumnIndexOrThrow(CommonString.KEY_POSM)));
                        posm.setCommon_id(dbcursor.getInt(dbcursor
                                .getColumnIndexOrThrow(CommonString.KEY_COMMON_ID)));
                        posm.setQuantity(dbcursor.getString(dbcursor
                                .getColumnIndexOrThrow(CommonString.KEY_QUANTITY)));
                        posm.setPosm_img_str(dbcursor.getString(dbcursor
                                .getColumnIndexOrThrow(CommonString.KEY_POSM_IMAGE)));

                        list.add(posm);
                        dbcursor.moveToNext();
                    }
                    dbcursor.close();
                    return list;
                }

            } catch (Exception e) {
                Log.d("Ex fetching Posm!",
                        e.toString());
                return list;
            }

            Log.d("FetcPosm data->Stop<-",
                    "-");
            return list;

        }

        //get Audit stored Data
        public ArrayList<AuditChecklistGetterSetter> getAuditInsertedData(String store_cd) {

            Log.d("FetchAudit>Start<--",
                    "----");
            ArrayList<AuditChecklistGetterSetter> list = new ArrayList<>();
            Cursor dbcursor = null;

            try {
                dbcursor = db.rawQuery("SELECT * from " + CommonString.TABLE_CHECKLIST_INSERTED_DATA + " WHERE " + CommonString.KEY_STORE_CD + " = '" + store_cd + "' AND " + CommonString.KEY_ISD_CD + " != '0' ", null);

                if (dbcursor != null) {
                    dbcursor.moveToFirst();
                    while (!dbcursor.isAfterLast()) {
                        AuditChecklistGetterSetter posm = new AuditChecklistGetterSetter();

                        posm.setCHECKLIST(dbcursor.getString(dbcursor
                                .getColumnIndexOrThrow(CommonString.KEY_CHECKLIST)));
                        posm.setCHECKLIST_CD(dbcursor.getString(dbcursor
                                .getColumnIndexOrThrow(CommonString.KEY_CHECKLIST_CD)));
                        posm.setAvailability(dbcursor.getInt(dbcursor
                                .getColumnIndexOrThrow(CommonString.KEY_AVAILABILITY)));
                        posm.setIsd_cd(dbcursor.getString(dbcursor
                                .getColumnIndexOrThrow(CommonString.KEY_ISD_CD)));
                        posm.setStore_cd(dbcursor.getString(dbcursor
                                .getColumnIndexOrThrow(CommonString.KEY_STORE_CD)));

                        list.add(posm);
                        dbcursor.moveToNext();
                    }
                    dbcursor.close();
                    return list;
                }

            } catch (Exception e) {
                Log.d("Ex fetching Posm!",
                        e.toString());
                return list;
            }

            Log.d("FetcAudit data->Stop<-",
                    "-");
            return list;

        }

        //get Audit stored Data for New employee
        public ArrayList<AuditChecklistGetterSetter> getAuditInsertedNewEmpData(String store_cd) {
            Log.d("FetchAudit>Start<--",
                    "----");
            ArrayList<AuditChecklistGetterSetter> list = new ArrayList<>();
            Cursor dbcursor = null;

            try {
                dbcursor = db.rawQuery("SELECT * from " + CommonString.TABLE_CHECKLIST_INSERTED_DATA + " WHERE " + CommonString.KEY_STORE_CD + " = '" + store_cd + "' AND " + CommonString.KEY_ISD_CD + " = '0' ", null);

                if (dbcursor != null) {
                    dbcursor.moveToFirst();
                    while (!dbcursor.isAfterLast()) {
                        AuditChecklistGetterSetter posm = new AuditChecklistGetterSetter();

                        posm.setKey_id(dbcursor.getString(dbcursor
                                .getColumnIndexOrThrow(CommonString.KEY_MID)));
                        posm.setCHECKLIST(dbcursor.getString(dbcursor
                                .getColumnIndexOrThrow(CommonString.KEY_CHECKLIST)));
                        posm.setCHECKLIST_CD(dbcursor.getString(dbcursor
                                .getColumnIndexOrThrow(CommonString.KEY_CHECKLIST_CD)));
                        posm.setAvailability(dbcursor.getInt(dbcursor
                                .getColumnIndexOrThrow(CommonString.KEY_AVAILABILITY)));
                        posm.setIsd_cd(dbcursor.getString(dbcursor
                                .getColumnIndexOrThrow(CommonString.KEY_ISD_CD)));
                        posm.setStore_cd(dbcursor.getString(dbcursor
                                .getColumnIndexOrThrow(CommonString.KEY_STORE_CD)));

                        list.add(posm);
                        dbcursor.moveToNext();
                    }
                    dbcursor.close();
                    return list;
                }

            } catch (Exception e) {
                Log.d("Ex fetching Posm!",
                        e.toString());
                return list;
            }

            Log.d("FetcAudit data->Stop<-",
                    "-");
            return list;

        }

        //get New Added Employee Data
        public ArrayList<AddNewEmployeeGetterSetter> getNewEmployeeInsertedData(String store_cd) {

            Log.d("FetchEmp>Start<--",
                    "----");
            ArrayList<AddNewEmployeeGetterSetter> list = new ArrayList<>();
            Cursor dbcursor = null;

            try {
                dbcursor = db.rawQuery("SELECT * from " + CommonString.TABLE_ADD_NEW_EMPLOYEE + " WHERE " + CommonString.KEY_STORE_CD + " = '" + store_cd + "' ", null);

                if (dbcursor != null) {
                    dbcursor.moveToFirst();
                    while (!dbcursor.isAfterLast()) {
                        AddNewEmployeeGetterSetter posm = new AddNewEmployeeGetterSetter();

                        posm.setKey_id(dbcursor.getString(dbcursor
                                .getColumnIndexOrThrow(CommonString.KEY_ID)));
                        posm.setName(dbcursor.getString(dbcursor
                                .getColumnIndexOrThrow(CommonString.KEY_NAME)));
                        posm.setEmail(dbcursor.getString(dbcursor
                                .getColumnIndexOrThrow(CommonString.KEY_EMAIL)));
                        posm.setPhone(dbcursor.getString(dbcursor
                                .getColumnIndexOrThrow(CommonString.KEY_PHONE_NO)));
                        if (dbcursor.getString(dbcursor
                                .getColumnIndexOrThrow(CommonString.KEY_IS_ISD)).equals("0")) {
                            posm.setIsIsd(false);
                        } else {
                            posm.setIsIsd(true);
                        }


                        list.add(posm);
                        dbcursor.moveToNext();
                    }
                    dbcursor.close();
                    return list;
                }

            } catch (Exception e) {
                Log.d("Ex fetching Posm!",
                        e.toString());
                return list;
            }

            Log.d("FetcAudit data->Stop<-",
                    "-");
            return list;

        }

        //get all ISD and employee for inseted quiz data for store_cd
        public ArrayList<AllIsdNEmployeeGetterSetter> getAllIsdNEmployeeData(String store_cd) {

            Log.d("FetchEmp>Start<--",
                    "----");
            ArrayList<AllIsdNEmployeeGetterSetter> list = new ArrayList<>();
            Cursor dbcursor = null;

            try {
                dbcursor = db.rawQuery("SELECT DISTINCT 'Existing' AS TYPE,  A.ISD_CD AS ISD_CD, I.ISD_NAME AS NAME, T.TOPIC AS TOPIC  FROM ANSWERED_DATA A INNER JOIN TRAINING_TOPIC T ON A.TOPIC_CD = T.TOPIC_CD " +
                        "INNER JOIN STORE_ISD I ON  A.ISD_CD = I.ISD_CD " +
                        "WHERE A.ISD_CD <> 0 AND A.STORE_CD = '" + store_cd + "' " +
                        "UNION " +
                        "SELECT DISTINCT 'Existing' AS TYPE,  A.ISD_CD AS ISD_CD, I.ISD_NAME AS NAME, T.TOPIC AS TOPIC  FROM ANSWERED_DATA A INNER JOIN TRAINING_TOPIC T ON A.TOPIC_CD = T.TOPIC_CD " +
                        " INNER JOIN NEW_ISD I ON  A.ISD_CD = I.ISD_CD " +
                        " WHERE A.ISD_CD <> 0 AND A.STORE_CD = '" + store_cd + "'" +
                        " UNION " +
                        "SELECT DISTINCT 'New' AS TYPE, A.ISD_CD, I.NAME, T.TOPIC  FROM ANSWERED_DATA A INNER JOIN TRAINING_TOPIC T ON A.TOPIC_CD = T.TOPIC_CD " +
                        "INNER JOIN ADD_NEW_EMPLOYEE I ON  A.MID = I.KEY_ID " +
                        " WHERE A.ISD_CD = 0 AND A.STORE_CD = '" + store_cd + "'", null);

                if (dbcursor != null) {
                    dbcursor.moveToFirst();
                    while (!dbcursor.isAfterLast()) {
                        AllIsdNEmployeeGetterSetter isdemp = new AllIsdNEmployeeGetterSetter();

                        isdemp.setName(dbcursor.getString(dbcursor
                                .getColumnIndexOrThrow("NAME")));
                        isdemp.setIsd_cd(dbcursor.getString(dbcursor
                                .getColumnIndexOrThrow(CommonString.KEY_ISD_CD)));
                        isdemp.setTopic(dbcursor.getString(dbcursor
                                .getColumnIndexOrThrow("TOPIC")));
                        isdemp.setType(dbcursor.getString(dbcursor
                                .getColumnIndexOrThrow("TYPE")));

                        list.add(isdemp);
                        dbcursor.moveToNext();
                    }
                    dbcursor.close();
                    return list;
                }

            } catch (Exception e) {
                Log.d("Ex fetching Posm!",
                        e.toString());
                return list;
            }

            Log.d("FetcAudit data->Stop<-",
                    "-");
            return list;

        }

        //get Quiz stored Data
        public ArrayList<QuizAnwserGetterSetter> getQuizData(String store_cd) {

            Log.d("FetchQuiz>Start<--",
                    "----");
            ArrayList<QuizAnwserGetterSetter> list = new ArrayList<>();
            Cursor dbcursor = null;

            try {
                dbcursor = db.rawQuery("SELECT * from " + CommonString.TABLE_ANSWERED_DATA + " WHERE " + CommonString.KEY_STORE_CD + " = '" + store_cd + "' AND " + CommonString.KEY_ISD_CD + " != '0' ", null);

                if (dbcursor != null) {
                    dbcursor.moveToFirst();
                    while (!dbcursor.isAfterLast()) {
                        QuizAnwserGetterSetter quiz = new QuizAnwserGetterSetter();

                        quiz.setIsd_cd(dbcursor.getString(dbcursor
                                .getColumnIndexOrThrow(CommonString.KEY_ISD_CD)));
                        quiz.setTopic_cd(dbcursor.getString(dbcursor
                                .getColumnIndexOrThrow(CommonString.KEY_TOPIC_CD)));
                        quiz.setQuestion_cd(dbcursor.getString(dbcursor
                                .getColumnIndexOrThrow(CommonString.KEY_QUESTION_CD)));
                        quiz.setAnswer_cd(dbcursor.getString(dbcursor
                                .getColumnIndexOrThrow(CommonString.KEY_ANSWER_CD)));
                        quiz.setTraining_mode_cd(dbcursor.getString(dbcursor
                                .getColumnIndexOrThrow(CommonString.KEY_TRAINING_MODE_CD)));
                        list.add(quiz);
                        dbcursor.moveToNext();
                    }
                    dbcursor.close();
                    return list;
                }

            } catch (Exception e) {
                Log.d("Ex fetching Posm!",
                        e.toString());
                return list;
            }

            Log.d("FetcAudit data->Stop<-",
                    "-");
            return list;

        }

        //get Quiz stored Data for new employee
        public ArrayList<QuizAnwserGetterSetter> getQuizNewEmployeeData(String store_cd) {

            Log.d("FetchQuiz>Start<--",
                    "----");
            ArrayList<QuizAnwserGetterSetter> list = new ArrayList<>();
            Cursor dbcursor = null;

            try {
                dbcursor = db.rawQuery("SELECT * from " + CommonString.TABLE_ANSWERED_DATA + " WHERE " + CommonString.KEY_STORE_CD + " = '" + store_cd + "' AND " + CommonString.KEY_ISD_CD + " = '0' ", null);

                if (dbcursor != null) {
                    dbcursor.moveToFirst();
                    while (!dbcursor.isAfterLast()) {
                        QuizAnwserGetterSetter quiz = new QuizAnwserGetterSetter();

                        quiz.setKey_id(dbcursor.getString(dbcursor
                                .getColumnIndexOrThrow(CommonString.KEY_MID)));
                        quiz.setIsd_cd(dbcursor.getString(dbcursor
                                .getColumnIndexOrThrow(CommonString.KEY_ISD_CD)));
                        quiz.setTopic_cd(dbcursor.getString(dbcursor
                                .getColumnIndexOrThrow(CommonString.KEY_TOPIC_CD)));
                        quiz.setQuestion_cd(dbcursor.getString(dbcursor
                                .getColumnIndexOrThrow(CommonString.KEY_QUESTION_CD)));
                        quiz.setAnswer_cd(dbcursor.getString(dbcursor
                                .getColumnIndexOrThrow(CommonString.KEY_ANSWER_CD)));
                        quiz.setTraining_mode_cd(dbcursor.getString(dbcursor
                                .getColumnIndexOrThrow(CommonString.KEY_TRAINING_MODE_CD)));
                        list.add(quiz);
                        dbcursor.moveToNext();
                    }
                    dbcursor.close();
                    return list;
                }

            } catch (Exception e) {
                Log.d("Ex fetching Posm!",
                        e.toString());
                return list;
            }

            Log.d("FetcAudit data->Stop<-",
                    "-");
            return list;

        }

        //Update Upload status to U
        public void updateUploadStatus(String key_id, String status) {

            try {
                ContentValues values = new ContentValues();
                values.put(CommonString.KEY_UPLOAD_STATUS, status);

                db.update(CommonString.TABLE_STORE_DATA, values,
                        CommonString.KEY_ID + "=" + key_id, null);
            } catch (Exception e) {

            }
        }

        //Delete specific Posm Data
        public void deleteSpecificPOSMData(String common_id) {

            db.delete(CommonString.TABLE_POSM_DATA, CommonString.KEY_COMMON_ID + "='" + common_id + "'", null);
        }

        //Delete uploaded store data next day
        public void deleteStoreData(String visit_date) {

            db.delete(CommonString.TABLE_STORE_DATA, CommonString.KEY_VISIT_DATE + "!='" + visit_date + "' AND " + CommonString.KEY_UPLOAD_STATUS + "='U'", null);
        }

        public ArrayList<OrderEntrySKUGetterSetter> getOrderEntrySKU() {
            ArrayList<OrderEntrySKUGetterSetter> list = new ArrayList<>();

            Cursor cr = null;
            cr = db.rawQuery("Select * from SKU_MASTER  as s " +
                    "inner join BRAND_MASTER as b " +
                    "where s.BRAND_CD=b.BRAND_CD", null);
            if (cr != null) {
                if (cr.moveToFirst()) {
                    do {
                        OrderEntrySKUGetterSetter skuOrder = new OrderEntrySKUGetterSetter();
                        skuOrder.setSku_cd(cr.getString(0));
                        skuOrder.setSku(cr.getString(1));
                        skuOrder.setSku_brand_cd(cr.getString(2));
                        skuOrder.setSku_sequence(cr.getString(3));
                        skuOrder.setBrand_cd(cr.getString(4));
                        skuOrder.setBrand(cr.getString(5));
                        skuOrder.setCategory_cd(cr.getString(6));
                        skuOrder.setCompany_cd(cr.getString(7));
                        skuOrder.setBrand_sequence(cr.getString(8));

                        list.add(skuOrder);
                    } while (cr.moveToNext());
                }
                cr.close();
            }
            return list;
        }

        public ArrayList<OrderEntrySKUGetterSetter> getOrderEntrySKUPerCategory(String category) {
            ArrayList<OrderEntrySKUGetterSetter> list = new ArrayList<>();

            Cursor cr = null;
            cr = db.rawQuery("Select * from SKU_MASTER  as s " +
                    "inner join (select * from BRAND_MASTER where CATEGORY_CD=?) as b " +
                    "where s.BRAND_CD=b.BRAND_CD", new String[]{category});
            if (cr != null) {
                if (cr.moveToFirst()) {
                    do {
                        OrderEntrySKUGetterSetter skuOrder = new OrderEntrySKUGetterSetter();
                        skuOrder.setSku_cd(cr.getString(0));
                        skuOrder.setSku(cr.getString(1));
                        skuOrder.setSku_brand_cd(cr.getString(2));
                        skuOrder.setSku_sequence(cr.getString(3));
                        skuOrder.setBrand_cd(cr.getString(4));
                        skuOrder.setBrand(cr.getString(5));
                        skuOrder.setCategory_cd(cr.getString(6));
                        skuOrder.setCompany_cd(cr.getString(7));
                        skuOrder.setBrand_sequence(cr.getString(8));

                        list.add(skuOrder);
                    } while (cr.moveToNext());
                }
                cr.close();
            }
            return list;
        }

        // getCoverageData

        public void updateCoverageStoreImage(String StoreId, String VisitDate, String Image) {
            try {
                ContentValues values = new ContentValues();
                values.put(CommonString.KEY_IMAGE, Image);

                db.update(CommonString.TABLE_COVERAGE_DATA, values, CommonString.KEY_STORE_CD + "='" + StoreId + "' AND "
                        + CommonString.KEY_VISIT_DATE + "='" + VisitDate
                        + "'", null);
            } catch (Exception e) {
                e.toString();
            }
        }

        public void updateCoverageStoreStatusValid(String StoreId, String VisitDate, String status) {

            try {
                ContentValues values = new ContentValues();
                //values.put(CommonString.KEY_OUT_TIME, outtime);
                values.put(CommonString.KEY_COVERAGE_STATUS, status);

                db.update(CommonString.TABLE_COVERAGE_DATA, values, CommonString.KEY_STORE_CD + "='" + StoreId + "' AND "
                        + CommonString.KEY_VISIT_DATE + "='" + VisitDate
                        + "'", null);
            } catch (Exception e) {

            }
        }


        public void deleteSpecificStoreData(String store_cd) {
            db.delete(CommonString.TABLE_COVERAGE_DATA, CommonString.KEY_STORE_CD + "='" + store_cd + "'", null);
            /*db.delete(CommonString.TABLE_ANSWERED_DATA, CommonString.KEY_STORE_CD + "='" + store_cd + "'", null);
            db.delete(CommonString.TABLE_CHECKLIST_INSERTED_DATA, CommonString.KEY_STORE_CD + "='" + store_cd + "'", null);
            db.delete(CommonString.TABLE_ADD_NEW_EMPLOYEE, CommonString.KEY_STORE_CD + "='" + store_cd + "'", null);
            db.delete(CommonString.TABLE_NEW_ISD, CommonString.KEY_STORE_CD + "='" + store_cd + "'", null);*/
//
//    }
//
//    //check if previous coverage table is filled
//    public boolean isCoverageDataFilled(String visit_date) {
//        boolean filled = false;
//
//        Cursor dbcursor = null;
//
//        try {
//
//            dbcursor = db
//                    .rawQuery(
//                            "SELECT * FROM COVERAGE_DATA " + "where "
//                                    + CommonString.KEY_VISIT_DATE + "<>'" + visit_date + "'", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                int icount = dbcursor.getInt(0);
//                dbcursor.close();
//                if (icount > 0) {
//                    filled = true;
//                } else {
//                    filled = false;
//                }
//
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception isempty",
//                    e.toString());
//            return filled;
//        }
//
//        return filled;
//    }
//
//
//    //Delete specific store data
//   /* public void deleteStoreData(String visit_date){
//
//        db.delete(CommonString.TABLE_STORE_DATA, CommonString.KEY_VISIT_DATE + "!='" + visit_date + "' AND "+ CommonString.KEY_UPLOAD_STATUS + "='U'", null);
//    }*/
//
//
////Gagan Goel
//
////Insert Data
//
//    //JCP data or Working Data
//    public void insertJCPData(JCPGetterSetter data) {
//        db.delete("JOURNEY_PLAN", null, null);
//        ContentValues values = new ContentValues();
//
//        try {
//            for (int i = 0; i < data.getSTORE_CD().size(); i++) {
//                values.put("STORE_CD", Integer.parseInt(data.getSTORE_CD().get(i)));
//                values.put("EMP_CD", Integer.parseInt(data.getEMP_CD().get(i)));
//                values.put("VISIT_DATE", data.getVISIT_DATE().get(i));
//                values.put("KEYACCOUNT", data.getKEYACCOUNT().get(i));
//                values.put("STORENAME", data.getSTORENAME().get(i));
//                values.put("CITY", data.getCITY().get(i));
//                values.put("STORETYPE", data.getSTORETYPE().get(i));
//                values.put("STATE_CD", data.getSTATE_CD().get(i));
//                values.put("STORETYPE_CD", data.getSTORETYPE_CD().get(i));
//                values.put("UPLOAD_STATUS", data.getUPLOAD_STATUS().get(i));
//                values.put("CHECKOUT_STATUS", data.getCHECKOUT_STATUS().get(i));
//                values.put("GEO_TAG", data.getGEO_TAG().get(i));
//
//                db.insert("JOURNEY_PLAN", null, values);
//            }
//        } catch (Exception ex) {
//            Log.d("DB Excep in JCP Insert", ex.toString());
//        }
//    }
//
//    //Non Working data
//    public void insertNonWorkingReasonData(NonWorkingReasonGetterSetter data) {
//        db.delete("NON_WORKING_REASON", null, null);
//        ContentValues values = new ContentValues();
//
//        try {
//            for (int i = 0; i < data.getReason_cd().size(); i++) {
//                values.put("REASON_CD", Integer.parseInt(data.getReason_cd().get(i)));
//                values.put("REASON", data.getReason().get(i));
//                values.put("ENTRY_ALLOW", data.getEntry_allow().get(i));
//                values.put("IMAGE_ALLOW", data.getImage_allow().get(i));
//
//                db.insert("NON_WORKING_REASON", null, values);
//            }
//        } catch (Exception ex) {
//            Log.d("Exception Non Working", ex.toString());
//        }
//    }
//
//    //Insert SKU_Master Data
//    public void insertSKUData(SkuMasterGetterSetter skuData) {
//        db.delete("SKU_MASTER", null, null);
//        ContentValues values = new ContentValues();
//
//        try {
//            for (int i = 0; i < skuData.getSKU_CD().size(); i++) {
//                values.put("SKU_CD", Integer.parseInt(skuData.getSKU_CD().get(i)));
//                values.put("SKU", skuData.getSKU().get(i));
//                values.put("BRAND_CD", skuData.getBRAND_CD().get(i));
//                values.put("SKU_SEQUENCE", skuData.getBRAND_CD().get(i));
//                /*values.put("ORDER_VALUE", skuData.getORDER_VALUE().get(i));
//                values.put("ORDER_DELIVERY_VALUE", skuData.getORDER_DELIVERY_VALUE().get(i));*/
//
//                db.insert("SKU_MASTER", null, values);
//            }
//        } catch (Exception ex) {
//            Log.d("Exception Sku Master", ex.toString());
//        }
//    }
//
//    //Insert CATEGORY_MASTER Data
//    public void insertCategoryData(CategoryMasterGetterSetter categoryData) {
//        db.delete("CATEGORY_MASTER", null, null);
//        ContentValues values = new ContentValues();
//
//        try {
//            for (int i = 0; i < categoryData.getCATEGORY_CD().size(); i++) {
//                values.put("CATEGORY_CD", Integer.parseInt(categoryData.getCATEGORY_CD().get(i)));
//                values.put("CATEGORY", categoryData.getCATEGORY().get(i));
//                values.put("CATEGORY_SEQUENCE", categoryData.getCATEGORY_SEQUENCE().get(i));
//
//                db.insert("CATEGORY_MASTER", null, values);
//            }
//        } catch (Exception ex) {
//            Log.d("Exception ", "CATEGORY MASTER" + ex.toString());
//        }
//    }
//
//    //Insert Brand_Master Data
//    public void insertBrandData(BrandMasterGetterSetter brandData) {
//        db.delete("BRAND_MASTER", null, null);
//        ContentValues values = new ContentValues();
//
//        try {
//            for (int i = 0; i < brandData.getBRAND_CD().size(); i++) {
//                values.put("BRAND_CD", Integer.parseInt(brandData.getBRAND_CD().get(i)));
//                values.put("BRAND", brandData.getBRAND().get(i));
//                values.put("CATEGORY_CD", brandData.getCATEGORY_CD().get(i));
//                values.put("COMPANY_CD", brandData.getCOMPANY_CD().get(i));
//                values.put("BRAND_SEQUENCE", brandData.getBRAND_SEQUENCE().get(i));
//
//                db.insert("BRAND_MASTER", null, values);
//            }
//        } catch (Exception ex) {
//            Log.d("Exception BRAND MASTER", ex.toString());
//        }
//    }
//
//    //Insert Store List Data into OrderEntry
//    public void InsertOpeningStocklistData(
//            String storeid, String STATE_CD, String STORE_TYPE_CD,
//            HashMap<StockNewGetterSetter, List<StockNewGetterSetter>> data,
//            List<StockNewGetterSetter> save_listDataHeader) {
//
//        ContentValues values = new ContentValues();
//        ContentValues values1 = new ContentValues();
//        try {
//            db.beginTransaction();
//            for (int i = 0; i < save_listDataHeader.size(); i++) {
//                values.put("STORE_CD", storeid);
//                values.put("BRAND_CD", save_listDataHeader.get(i).getBrand_cd());
//                values.put("BRAND", save_listDataHeader.get(i).getBrand());
//                //values.put("STATE_CD", STATE_CD);
//                //values.put("STORE_TYPE_CD", STORE_TYPE_CD);
//
//                long l = db.insert(CommonString.TABLE_INSERT_ORDER_ENTRYHEADER_DATA, null, values);
//
//                for (int j = 0; j < data.get(save_listDataHeader.get(i)).size(); j++) {
//                    values1.put("Common_Id", (int) l);
//                    values1.put("SKU_CD", data.get(save_listDataHeader.get(i)).get(j).getSku_cd());
//                    values1.put("SKU", data.get(save_listDataHeader.get(i)).get(j).getSku());
//                    values1.put("BRAND_CD", save_listDataHeader.get(i).getBrand_cd());
//                    values1.put("BRAND", save_listDataHeader.get(i).getBrand());
//                    values1.put("ORDER_VALUE", data.get(save_listDataHeader.get(i)).get(j).getOrder());
//                    values1.put("ORDER_DELIVERY", data.get(save_listDataHeader.get(i)).get(j).getOrder_delivery());
//                    values1.put("STORE_CD", storeid);
//                    /*values1.put("STORE_CD", storeid);
//                    values1.put("STATE_CD", STATE_CD);
//                    values1.put("STORE_TYPE_CD", STORE_TYPE_CD);
//                    values1.put("OPENING_TOTAL_STOCK", data.get(save_listDataHeader.get(i)).get(j).getOpenning_total_stock());
//                    values1.put("FACING", data.get(save_listDataHeader.get(i)).get(j).getOpening_facing());
//                    values1.put("STOCK_UNDER_DAYS", data.get(save_listDataHeader.get(i)).get(j).getStock_under45days());
//                    values1.put("STOCK_UNDER_12", data.get(save_listDataHeader.get(i)).get(j).getStockunder12());
//                    values1.put("STOCK_GREATER_13", data.get(save_listDataHeader.get(i)).get(j).getStockgreater13());*/
//                    //values1.put("EXPRIY_DATE", data.get(save_listDataHeader.get(i)).get(j).getDate());
//                    db.insert(CommonString.TABLE_ORDERENTRY_STOCK_DATA, null, values1);
//                }
//            }
//            db.setTransactionSuccessful();
//            db.endTransaction();
//        } catch (Exception ex) {
//            Log.d("Database ", "Exception while Insert Posm Master Data" + ex.toString());
//        }
//    }
//
//    //Insert Coverage Data
    public long InsertCoverageData(CoverageBean data) {
        ContentValues values = new ContentValues();
        try {
            values.put(CommonString.KEY_STORE_CD, data.getStoreId());
            values.put(CommonString.KEY_USER_ID, data.getUserId());
            values.put(CommonString.KEY_IN_TIME, data.getInTime());
            values.put(CommonString.KEY_OUT_TIME, data.getOutTime());
            values.put(CommonString.KEY_VISIT_DATE, data.getVisitDate());
            values.put(CommonString.KEY_LATITUDE, data.getLatitude());
            values.put(CommonString.KEY_LONGITUDE, data.getLongitude());
            values.put(CommonString.KEY_REASON_ID, data.getReasonid());
            values.put(CommonString.KEY_REASON, data.getReason());
            values.put(CommonString.KEY_COVERAGE_STATUS, data.getStatus());
            values.put(CommonString.KEY_IMAGE, data.getImage());
            values.put(CommonString.KEY_COVERAGE_REMARK, data.getRemark());
            values.put(CommonString.KEY_REASON_ID, data.getReasonid());
            values.put(CommonString.KEY_REASON, data.getReason());

            return db.insert(CommonString.TABLE_COVERAGE_DATA, null, values);

        } catch (Exception ex) {
            Log.d("DB Exception coverage",
                    ex.toString());
        }
        return 0;
    }

    //Update Data
    public void updateCoverageIDatStoreInfo(long coverageid, long keyid) {
        try {
            ContentValues values = new ContentValues();
            values.put(CommonString.KEY_COVERAGE_ID, coverageid);

            db.update(CommonString.TABLE_ADD_NEW_STORE, values,
                    CommonString.KEY_ID + "=" + keyid, null);
        } catch (Exception e) {

        }
    }

    //Save Add New Store Data
    public int saveAddNewStoreData(AddNewStoreGetterSetter addNewStoreGetterSetter, String visit_date) {
        int store_id = 0;

        try {
            ContentValues values = new ContentValues();

            values.put("VISIT_DATE", visit_date);
            values.put("STORE_CD", "0");
            values.put("STORE_NAME", addNewStoreGetterSetter.getEd_StoreName());
            values.put("RETAILER_NAME", addNewStoreGetterSetter.getEd_RetailerName());
            values.put("ADDRESS", addNewStoreGetterSetter.getEd_Address());
            values.put("LANDMARK", addNewStoreGetterSetter.getEd_Landmark());

            values.put("STATE_CD", addNewStoreGetterSetter.getState_cd());
            values.put("STATE", addNewStoreGetterSetter.getState());

            values.put("CITY_CD", addNewStoreGetterSetter.getCity_CD());
            values.put("CITY", addNewStoreGetterSetter.getCity());
            values.put("TOWN_CD", addNewStoreGetterSetter.getTown_CD());
            values.put("TOWN", addNewStoreGetterSetter.getTown());
            values.put("PHONE", addNewStoreGetterSetter.getEd_Phone());

            values.put("DOB", addNewStoreGetterSetter.getDob());
            values.put("DOA", addNewStoreGetterSetter.getDoa());

            values.put("ANSWER_CD", addNewStoreGetterSetter.getSmartphoneAnswer_cd());
            values.put("ANSWER", addNewStoreGetterSetter.getHasSmartphone());

            values.put("SUBDISTRIBUTER_NAME", addNewStoreGetterSetter.getSubDistributerName());

            values.put("CHANNEL_CD", addNewStoreGetterSetter.getStoreType_CD());
            values.put("CHANNEL", addNewStoreGetterSetter.getStoreType());
            values.put("UPLOAD_STATUS", addNewStoreGetterSetter.getUpload_Status());
            values.put("IMAGE_PATH", addNewStoreGetterSetter.getImg_Camera());

            db.insert(CommonString.TABLE_ADD_NEW_STORE, null, values);
            Cursor cr = db.rawQuery("Select last_insert_rowid()", null);
            if (cr.moveToFirst()) {
                store_id = cr.getInt(0);
            }
            cr.close();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return store_id;
    }

    ////Get List Data
//
//    //get JCP Data without visit date
//    public ArrayList<JCPGetterSetter> getAllJCPData() {
//        Log.d("FetchingStoredata", "-->Start<--------------------");
//        ArrayList<JCPGetterSetter> list = new ArrayList<>();
//        Cursor dbcursor = null;
//
//        try {
//            dbcursor = db.rawQuery("SELECT * from JOURNEY_PLAN ", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    JCPGetterSetter df = new JCPGetterSetter();
//
//                    df.setSTORE_CD(dbcursor.getString(dbcursor.getColumnIndexOrThrow("STORE_CD")));
//                    df.setEMP_CD(dbcursor.getString(dbcursor.getColumnIndexOrThrow("EMP_CD")));
//                    df.setVISIT_DATE(dbcursor.getString(dbcursor.getColumnIndexOrThrow("VISIT_DATE")));
//                    df.setKEYACCOUNT(dbcursor.getString(dbcursor.getColumnIndexOrThrow("KEYACCOUNT")));
//                    df.setSTORENAME(dbcursor.getString(dbcursor.getColumnIndexOrThrow("STORENAME")));
//                    df.setCITY(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CITY")));
//                    df.setSTORETYPE(dbcursor.getString(dbcursor.getColumnIndexOrThrow("STORETYPE")));
//                    df.setSTATE_CD(dbcursor.getString(dbcursor.getColumnIndexOrThrow("STATE_CD")));
//                    df.setSTORETYPE_CD(dbcursor.getString(dbcursor.getColumnIndexOrThrow("STORETYPE_CD")));
//                    df.setUPLOAD_STATUS(dbcursor.getString(dbcursor.getColumnIndexOrThrow("UPLOAD_STATUS")));
//                    df.setCHECKOUT_STATUS(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CHECKOUT_STATUS")));
//                    df.setGEO_TAG(dbcursor.getString(dbcursor.getColumnIndexOrThrow("GEO_TAG")));
//
//                    list.add(df);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//        } catch (Exception e) {
//            Log.d("Exception ", "when fetching JCP!!!!!!!!!!!!!!!!!!!!!" + e.toString());
//            return list;
//        }
//
//        Log.d("FetchingJCP data", "--->Stop<---------------------");
//        return list;
//    }
//
//    //Get Store Data List
//    public ArrayList<JCPGetterSetter> getStoreData(String visit_date) {
//        Log.d("FetchStorelist>Start<--", "----");
//        ArrayList<JCPGetterSetter> list = new ArrayList<>();
//        Cursor dbcursor = null;
//
//        try {
//            dbcursor = db.rawQuery("SELECT * from JOURNEY_PLAN WHERE VISIT_DATE = '" + visit_date + "'", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    JCPGetterSetter df = new JCPGetterSetter();
//
//                    df.setSTORE_CD(dbcursor.getString(dbcursor.getColumnIndexOrThrow("STORE_CD")));
//                    df.setEMP_CD(dbcursor.getString(dbcursor.getColumnIndexOrThrow("EMP_CD")));
//                    df.setVISIT_DATE(dbcursor.getString(dbcursor.getColumnIndexOrThrow("VISIT_DATE")));
//                    df.setKEYACCOUNT(dbcursor.getString(dbcursor.getColumnIndexOrThrow("KEYACCOUNT")));
//                    df.setSTORENAME(dbcursor.getString(dbcursor.getColumnIndexOrThrow("STORENAME")));
//                    df.setCITY(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CITY")));
//                    df.setSTORETYPE(dbcursor.getString(dbcursor.getColumnIndexOrThrow("STORETYPE")));
//                    df.setSTATE_CD(dbcursor.getString(dbcursor.getColumnIndexOrThrow("STATE_CD")));
//                    df.setSTORETYPE_CD(dbcursor.getString(dbcursor.getColumnIndexOrThrow("STORETYPE_CD")));
//                    df.setUPLOAD_STATUS(dbcursor.getString(dbcursor.getColumnIndexOrThrow("UPLOAD_STATUS")));
//                    df.setCHECKOUT_STATUS(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CHECKOUT_STATUS")));
//                    df.setGEO_TAG(dbcursor.getString(dbcursor.getColumnIndexOrThrow("GEO_TAG")));
//
//                    list.add(df);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//        } catch (Exception e) {
//            Log.d("Ex fetching Store!", e.toString());
//            return list;
//        }
//        Log.d("Fetcstore data->Stop<-", "-");
//        return list;
//    }
//
//    //Get Non Working data
//    public ArrayList<NonWorkingReasonGetterSetter> getNonWorkingData() {
//        Log.d("FetcNonWorking->Start<-", "-");
//        ArrayList<NonWorkingReasonGetterSetter> list = new ArrayList<NonWorkingReasonGetterSetter>();
//        Cursor dbcursor = null;
//
//        try {
//            dbcursor = db.rawQuery("SELECT * FROM NON_WORKING_REASON", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    NonWorkingReasonGetterSetter sb = new NonWorkingReasonGetterSetter();
//                    sb.setReason_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("REASON_CD")));
//                    sb.setReason(dbcursor.getString(dbcursor.getColumnIndexOrThrow("REASON")));
//                    sb.setEntry_allow(dbcursor.getString(dbcursor.getColumnIndexOrThrow("ENTRY_ALLOW")));
//                    sb.setImage_allow(dbcursor.getString(dbcursor.getColumnIndexOrThrow("IMAGE_ALLOW")));
//
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//        } catch (Exception e) {
//            Log.d("Exc Non working!!", e.toString());
//            return list;
//        }
//
//        Log.d("Fet non working->Stop<-", "-");
//        return list;
//    }
//
//    //Get SKU_Master Data List
//    public ArrayList<SkuMasterGetterSetter> getSkuMasterData() {
//        Log.d("Fetch Sku Master Data", "->Start<-");
//        ArrayList<SkuMasterGetterSetter> list = new ArrayList<SkuMasterGetterSetter>();
//        Cursor dbcursor = null;
//
//        try {
//            dbcursor = db.rawQuery("SELECT * FROM SKU_MASTER", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    SkuMasterGetterSetter sb = new SkuMasterGetterSetter();
//                    sb.setSKU_CD(dbcursor.getString(dbcursor.getColumnIndexOrThrow("SKU_CD")));
//                    sb.setSKU(dbcursor.getString(dbcursor.getColumnIndexOrThrow("SKU")));
//                    sb.setBRAND_CD(dbcursor.getString(dbcursor.getColumnIndexOrThrow("BRAND_CD")));
//                    sb.setSKU_SEQUENCE(dbcursor.getString(dbcursor.getColumnIndexOrThrow("SKU_SEQUENCE")));
//
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exc SKU Master!!", e.toString());
//            return list;
//        }
//
//        Log.d("Fet SKU Master->Stop<-", "-");
//        return list;
//    }
//
//    //Get CATEGORY_MASTER Data List
//    public ArrayList<CategoryMasterGetterSetter> getCategoryMasterData() {
//        Log.d("Fetch Category Master", "->Start<-");
//        ArrayList<CategoryMasterGetterSetter> list = new ArrayList<CategoryMasterGetterSetter>();
//        Cursor dbcursor = null;
//
//        try {
//            dbcursor = db.rawQuery("SELECT * FROM CATEGORY_MASTER", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    CategoryMasterGetterSetter sb = new CategoryMasterGetterSetter();
//                    sb.setCATEGORY_CD(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CATEGORY_CD")));
//                    sb.setCATEGORY(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CATEGORY")));
//                    sb.setCATEGORY_SEQUENCE(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CATEGORY_SEQUENCE")));
//
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exc CATEGORY MASTER!!", e.toString());
//            return list;
//        }
//
//        Log.d("Fet CATEGORY ", "MASTER ->Stop<--");
//        return list;
//    }
//
//    //Get Brand_Master Data List
//    public ArrayList<BrandMasterGetterSetter> getBrandMasterData() {
//        Log.d("Fetch Brand Master Data", "->Start<-");
//        ArrayList<BrandMasterGetterSetter> list = new ArrayList<BrandMasterGetterSetter>();
//        Cursor dbcursor = null;
//
//        try {
//            dbcursor = db.rawQuery("SELECT * FROM BRAND_MASTER", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    BrandMasterGetterSetter sb = new BrandMasterGetterSetter();
//                    sb.setBRAND_CD(dbcursor.getString(dbcursor.getColumnIndexOrThrow("BRAND_CD")));
//                    sb.setBRAND(dbcursor.getString(dbcursor.getColumnIndexOrThrow("BRAND")));
//                    sb.setCATEGORY_CD(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CATEGORY_CD")));
//                    sb.setCOMPANY_CD(dbcursor.getString(dbcursor.getColumnIndexOrThrow("COMPANY_CD")));
//                    sb.setBRAND_SEQUENCE(dbcursor.getString(dbcursor.getColumnIndexOrThrow("BRAND_SEQUENCE")));
//
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Log.e("Exc BRAND MASTER!!", e.toString());
//            return list;
//        }
//
//        Log.e("Fet BRAND ", "MASTER ->Stop<-");
//        return list;
//    }
//
//    //Header Data download after save the data
//    public ArrayList<StockNewGetterSetter> getStockAvailabilityData(String STATE_CD, String STORE_TYPE_CD) {
//        Log.d("FetchingStoredata", "--------------->Start<------------");
//        ArrayList<StockNewGetterSetter> list = new ArrayList<StockNewGetterSetter>();
//        Cursor dbcursor = null;
//
//        try {
///*            dbcursor = db.rawQuery("SELECT DISTINCT BRAND_CD,BRAND " +
//                    "FROM openingHeader_data  " +
//                    "WHERE STATE_CD ='" + STATE_CD + "' AND " +
//                    "STORE_TYPE_CD ='" + STORE_TYPE_CD + "'", null);*/
//
//            dbcursor = db.rawQuery("SELECT DISTINCT BR.BRAND_CD, BR.BRAND " +
//                    "FROM OrderEnrty_Header_Data as BR", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    StockNewGetterSetter sb = new StockNewGetterSetter();
//
//                    sb.setBrand_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("BRAND_CD")));
//                    sb.setBrand(dbcursor.getString(dbcursor.getColumnIndexOrThrow("BRAND")));
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//        } catch (Exception e) {
//            Log.d("Exception ", "when fetching opening stock!!!!!!!!!!!" + e.toString());
//            return list;
//        }
//
//        Log.d("Fetching opening stock", "---------------------->Stop<-----------ø");
//        return list;
//    }
//
//    //Default Header data download from the server case
//    public ArrayList<StockNewGetterSetter> getHeaderStockImageData(String STATE_CD, String STORE_TYPE_CD) {
//        //Log.d("FetchingStoredata--------------->Start<------------", "------------------");
//        ArrayList<StockNewGetterSetter> list = new ArrayList<StockNewGetterSetter>();
//        Cursor dbcursor = null;
//
//        try {
///*            dbcursor = db.rawQuery("SELECT DISTINCT BR.BRAND_CD, BR.BRAND" +
//                    " FROM MAPPING_AVAILABILITY MA INNER JOIN SKU_MASTER SKM ON MA.SKU_CD = SKM.SKU_CD " +
//                    "INNER JOIN BRAND_MASTER BR ON SKM.BRAND_CD = BR.BRAND_CD WHERE MA.STATE_CD ='" + STATE_CD + "' AND MA.STORETYPE_CD ='" + STORE_TYPE_CD + "'  ", null);*/
//
//            //dbcursor = db.rawQuery("SELECT DISTINCT BR.BRAND_CD, BR.BRAND FROM Brand_Master as BR", null);
//
//            dbcursor = db.rawQuery("SELECT DISTINCT BRAND_CD, BRAND, CATEGORY_CD, COMPANY_CD, BRAND_SEQUENCE " +
//                    "FROM BRAND_MASTER ORDER BY BRAND_SEQUENCE", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    StockNewGetterSetter sb = new StockNewGetterSetter();
//
//                    sb.setBrand_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("BRAND_CD")));
//                    sb.setBrand(dbcursor.getString(dbcursor.getColumnIndexOrThrow("BRAND")));
//                    sb.setCATEGORY_CD(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CATEGORY_CD")));
//                    sb.setCOMPANY_CD(dbcursor.getString(dbcursor.getColumnIndexOrThrow("COMPANY_CD")));
//                    sb.setBRAND_SEQUENCE(dbcursor.getString(dbcursor.getColumnIndexOrThrow("BRAND_SEQUENCE")));
//
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            //Log.d("Exception when fetching Header!!!!!!!!!!!", e.toString());
//            return list;
//        }
//
//        //Log.d("Fetching Header stock---------------------->Stop<-----------", "-------------------");
//        return list;
//    }
//
//    public ArrayList<StockNewGetterSetter> getOpeningStockDataFromDatabase(String store_cd, String brand_cd) {
//        Log.d("FetchingOpening ", "Stock data--------------->Start<------------------------------" + brand_cd);
//        ArrayList<StockNewGetterSetter> list = new ArrayList<StockNewGetterSetter>();
//        Cursor dbcursor = null;
//
//        try {
//  /*            dbcursor = db.rawQuery("SELECT SKU_CD,SKU, OPENING_TOTAL_STOCK, FACING ,STOCK_UNDER_DAYS, " +
//                    "STOCK_UNDER_12, STOCK_GREATER_13" +
//                    "  FROM  STOCK_DATA  WHERE STORE_CD= '" + store_cd + "' AND BRAND_CD = '" + brand_cd + "'", null);*/
//
///*            dbcursor = db.rawQuery("Select s.SKU_CD,s.SKU,s.BRAND_CD from SKU_MASTER  as s " +
//                    "inner join BRAND_MASTER as b " +
//                    "ON s.BRAND_CD=b.BRAND_CD " +
//                    "where s.BRAND_CD='" + brand_cd + "' ", null);*/
//
//            dbcursor = db.rawQuery("Select s.SKU,s.SKU_CD,s.BRAND,s.BRAND_CD,s.ORDER_VALUE " +
//                    "from Order_ENTRY_Stock_Data  as s " +
//                    "WHERE STORE_CD= '" + store_cd +
//                    "' AND BRAND_CD = '" + brand_cd + "'", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    StockNewGetterSetter sb = new StockNewGetterSetter();
//
//                    sb.setSku_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("SKU_CD")));
//                    sb.setSku(dbcursor.getString(dbcursor.getColumnIndexOrThrow("SKU")));
//                    sb.setBrand(dbcursor.getString(dbcursor.getColumnIndexOrThrow("BRAND")));
//                    sb.setBrand_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("BRAND_CD")));
//                    sb.setOrder(dbcursor.getString(dbcursor.getColumnIndexOrThrow("ORDER_VALUE")));
//
//                    /*String s = dbcursor.getString(dbcursor.getColumnIndexOrThrow("ORDER_VALUE"));
//                    if (s != null) {
//                        sb.setOrder(dbcursor.getString(dbcursor.getColumnIndexOrThrow("ORDER_VALUE")));
//                    } else {
//                        sb.setOrder("0");
//                    }*/
//                    //sb.setOpenning_total_stock(dbcursor.getString(dbcursor.getColumnIndexOrThrow("ORDER_VALUE")));
//                    /*sb.setOpenning_total_stock(dbcursor.getString(dbcursor.getColumnIndexOrThrow("OPENING_TOTAL_STOCK")));
//                    sb.setOpening_facing(dbcursor.getString(dbcursor.getColumnIndexOrThrow("FACING")));
//                    sb.setStock_under45days(dbcursor.getString(dbcursor.getColumnIndexOrThrow("STOCK_UNDER_DAYS")));
//                    sb.setStockunder12(dbcursor.getString(dbcursor.getColumnIndexOrThrow("STOCK_UNDER_12")));
//                    sb.setStockgreater13(dbcursor.getString(dbcursor.getColumnIndexOrThrow("STOCK_GREATER_13")));*/
//
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception ", "when fetching Records!!!!!!!!!!!!!!!!!!!!!" +
//                    e.toString());
//            return list;
//        }
//
//        Log.d("FetchingOPening midday", "---------------------->Stop<-----------");
//        return list;
//    }
//
//    public ArrayList<StockNewGetterSetter> getStockSkuData(String brand_cd, String STATE_CD, String STORE_TYPE_CD) {
//        Log.d("FetchingStoredata", "--------------->Start<------------" + brand_cd);
//        ArrayList<StockNewGetterSetter> list = new ArrayList<StockNewGetterSetter>();
//        Cursor dbcursor = null;
//
//        try {
//            //dbcursor = db.rawQuery("SELECT DISTINCT SD.SKU_CD, SD.SKU FROM MAPPING_AVAILABILITY CD INNER JOIN SKU_MASTER SD ON CD.SKU_CD = SD.SKU_CD WHERE CD.STATE_CD= '" +  STATE_CD + "' AND SD.BRAND_CD ='"+brand_cd+"'", null);
//            /*dbcursor = db.rawQuery("SELECT DISTINCT SKM.SKU_CD, SKM.SKU" +
//                    " FROM MAPPING_AVAILABILITY MA " +
//                    "INNER JOIN SKU_MASTER SKM " +
//                    "ON MA.SKU_CD = SKM.SKU_CD " +
//                    "INNER JOIN BRAND_MASTER BR " +
//                    "ON SKM.BRAND_CD = BR.BRAND_CD " +
//                    "WHERE MA.STATE_CD ='" + STATE_CD +
//                    "' AND MA.STORETYPE_CD ='" + STORE_TYPE_CD +
//                    "' AND BR.BRAND_CD ='" + brand_cd +
//                    "'LIMIT 1  ", null);*/
//
//            dbcursor = db.rawQuery("Select s.SKU_CD,s.SKU from SKU_MASTER  as s " +
//                    "inner join BRAND_MASTER as b " +
//                    "ON s.BRAND_CD=b.BRAND_CD " +
//                    "where s.BRAND_CD='" + brand_cd + "' ", null);
//
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    StockNewGetterSetter sb = new StockNewGetterSetter();
//
//                    sb.setSku_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("SKU_CD")));
//                    sb.setSku(dbcursor.getString(dbcursor.getColumnIndexOrThrow("SKU")));
//
//                            /*sb.setCategory_type(dbcursor.getString(dbcursor
//                                    .getColumnIndexOrThrow("CATEGORY_TYPE")));*/
//                    /*sb.setOpenning_total_stock("");
//                    sb.setOpening_facing("");
//                    sb.setStock_under45days("");
//                    sb.setDate("");*/
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception ", "when fetching opening stock!!!!!!!!!!!" + e.toString());
//            return list;
//        }
//
//        Log.d("Fetching opening stock", "---------------------->Stop<-----------");
//        return list;
//    }
//
//    public ArrayList<HeaderGetterSetter> getHeaderStock(String storeId) {
//        Log.d("FetchingOpening ", "Stock data--------------->Start<------------");
//        ArrayList<HeaderGetterSetter> list = new ArrayList<HeaderGetterSetter>();
//        Cursor dbcursor = null;
//
//        try {
//            dbcursor = db.rawQuery("SELECT KEY_ID FROM OrderEnrty_Header_Data " +
//                    "WHERE STORE_CD= '" + storeId + "'", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    HeaderGetterSetter sb = new HeaderGetterSetter();
//
//                    sb.setKeyId(dbcursor.getString(dbcursor.getColumnIndexOrThrow("KEY_ID")));
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//        } catch (Exception e) {
//            Log.d("Exception ", "when fetching Records!!!!!!!!!!!!!!!!!!!!!" + e.toString());
//            return list;
//        }
//
//        Log.d("FetchingOPening", " midday---------------------->Stop<-----------");
//        return list;
//    }
//

//
//    //Coverage Data Image
//    public String getCoverageDataImageValue(String store_id, String visit_date) {
//        String image = null;
//        try {
//            Cursor cr = db.rawQuery("SELECT  IMAGE from " + CommonString.TABLE_COVERAGE_DATA + " where STORE_CD ='"
//                    + store_id + "' AND VISIT_DATE ='" + visit_date + "'", null);
//            if (cr.moveToFirst()) {
//                image = cr.getString(0);
//            }
//            cr.close();
//        } catch (Exception ex) {
//            Log.d("DB Exception coverage", ex.toString());
//        }
//        return image;
//    }
//
//    //Get Coverage Specific Data
//    public ArrayList<CoverageBean> getCoverageSpecificData(String store_id, String visit_date) {
//        ArrayList<CoverageBean> list = new ArrayList<CoverageBean>();
//        Cursor dbcursor = null;
//
//        try {
//            dbcursor = db.rawQuery("SELECT  * from " + CommonString.TABLE_COVERAGE_DATA +
//                    " where " + CommonString.KEY_STORE_CD + "='" + store_id + "'", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    CoverageBean sb = new CoverageBean();
//
//                    sb.setUserId((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_USER_ID))));
//                    sb.setInTime(((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IN_TIME)))));
//                    sb.setOutTime(((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_OUT_TIME)))));
//                    sb.setVisitDate((((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_VISIT_DATE))))));
//                    sb.setLatitude(((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_LATITUDE)))));
//                    sb.setLongitude(((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_LONGITUDE)))));
//                    sb.setStatus((((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_COVERAGE_STATUS))))));
//
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//        } catch (Exception e) {
//            Log.d("Exce fetching Coverage", e.toString());
//        }
//
//        return list;
//    }
//
//    //Get Store List Data for upload
//    public ArrayList<StoreDataUploadGetterSetter> getStoreDataUpload(String store_cd) {
//        Log.e("Fetch", " StoreDataUpload -->Start<--");
//        ArrayList<StoreDataUploadGetterSetter> list = new ArrayList<>();
//        Cursor dbcursor = null;
//
//        try {
//            dbcursor = db.rawQuery("SELECT DISTINCT SKU_CD, ORDER_VALUE" +
//                    " FROM " + CommonString.TABLE_ORDERENTRY_STOCK_DATA +
//                    " WHERE " + CommonString.KEY_STORE_CD + " = '" + store_cd + "'", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    StoreDataUploadGetterSetter sb = new StoreDataUploadGetterSetter();
//
//                    sb.setSku_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("SKU_CD")));
//                    sb.setOrder_value(dbcursor.getString(dbcursor.getColumnIndexOrThrow("ORDER_VALUE")));
//
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//        } catch (Exception e) {
//            Log.e("Ex fetching Posm!", e.toString());
//            return list;
//        }
//
//        Log.e("Fetch", " StoreDataUpload -->Stop<--");
//        return list;
//    }
//
//    /// get store Status
//    public JCPGetterSetter getStoreStatus(String id) {
//        Log.d("FetchingStoredata", "--------------->Start<------------------------------");
//
//        JCPGetterSetter sb = new JCPGetterSetter();
//        Cursor dbcursor = null;
//
//        try {
//            dbcursor = db.rawQuery("SELECT  * from  JOURNEY_PLAN"
//                    + "  WHERE STORE_CD = '" + id + "'", null);
//
//            if (dbcursor != null) {
//                int numrows = dbcursor.getCount();
//
//                dbcursor.moveToFirst();
//                for (int i = 0; i < numrows; i++) {
//                    sb.setSTORE_CD(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_STORE_CD)));
//                    sb.setCHECKOUT_STATUS((dbcursor.getString(dbcursor.getColumnIndexOrThrow("CHECKOUT_STATUS"))));
//                    sb.setUPLOAD_STATUS(dbcursor.getString(dbcursor.getColumnIndexOrThrow("UPLOAD_STATUS")));
//
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//            }
//        } catch (Exception e) {
//            Log.d("Exception ", "when fetching Records!!!!!!!!!!!!!!!!!!!!!" + e.toString());
//        }
//
//        Log.d("FetchingStoredat", "---------------------->Stop<-----------");
//        return sb;
//    }
//
//    //Get Upload Distributor System Full Data
//    public ArrayList<UploadDistributorSystemGetterSetter> getUploadDistributorSystemData() {
//        Log.d("Fetch Data start", "UploadDistributorSystem  ->Start<-");
//        ArrayList<UploadDistributorSystemGetterSetter> list = new ArrayList<UploadDistributorSystemGetterSetter>();
//        Cursor dbcursor = null;
//
//        try {
//            dbcursor = db.rawQuery("SELECT * FROM DISTRIBUTOR_UPLOAD", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    UploadDistributorSystemGetterSetter sb = new UploadDistributorSystemGetterSetter();
//                    sb.setMID(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("MID")));
//                    sb.setSTORE_CD(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("STORE_CD")));
//                    sb.setSTORENAME(dbcursor.getString(dbcursor.getColumnIndexOrThrow("STORENAME")));
//                    sb.setADDRESS(dbcursor.getString(dbcursor.getColumnIndexOrThrow("ADDRESS")));
//                    sb.setCITY(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CITY")));
//                    sb.setVISITDATE(dbcursor.getString(dbcursor.getColumnIndexOrThrow("VISITDATE")));
//                    sb.setBRAND_CD(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("BRAND_CD")));
//                    sb.setBRAND(dbcursor.getString(dbcursor.getColumnIndexOrThrow("BRAND")));
//                    sb.setSKU_CD(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("SKU_CD")));
//                    sb.setSKU(dbcursor.getString(dbcursor.getColumnIndexOrThrow("SKU")));
//                    sb.setORDER_QTY(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("ORDER_QTY")));
//                    sb.setUPLOAD_STATUS(dbcursor.getString(dbcursor.getColumnIndexOrThrow("UPLOAD_STATUS")));
//
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception !!", " UploadDistributorSystem " + e.toString());
//            return list;
//        }
//
//        Log.d("Fetch Data stop ", "UploadDistributorSystem ->Stop<--");
//        return list;
//    }
//
//    //Get UploadDistributorStoreNameData Default
//    public ArrayList<UploadDistributorSystemGetterSetter> getUploadDistributorStoreNameData() {
//        Log.d("Fetch Data start", "UploadDistributorSystem  ->Start<-");
//        ArrayList<UploadDistributorSystemGetterSetter> list = new ArrayList<UploadDistributorSystemGetterSetter>();
//        Cursor dbcursor = null;
//
//        try {
//            dbcursor = db.rawQuery("select DISTINCT MID,STORE_CD,STORENAME,ADDRESS,CITY,VISITDATE " +
//                    "from DISTRIBUTOR_UPLOAD", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    UploadDistributorSystemGetterSetter sb = new UploadDistributorSystemGetterSetter();
//                    sb.setMID(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("MID")));
//                    sb.setSTORE_CD(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("STORE_CD")));
//                    sb.setSTORENAME(dbcursor.getString(dbcursor.getColumnIndexOrThrow("STORENAME")));
//                    sb.setADDRESS(dbcursor.getString(dbcursor.getColumnIndexOrThrow("ADDRESS")));
//                    sb.setCITY(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CITY")));
//                    sb.setVISITDATE(dbcursor.getString(dbcursor.getColumnIndexOrThrow("VISITDATE")));
//                    //sb.setUPLOAD_STATUS(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("UPLOAD_STATUS")));
//                    sb.setNON_ORDER_REASON(-1);
//                    sb.setORDER_STATUS(-1);
//
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception !!", " UploadDistributorSystem " + e.toString());
//            return list;
//        }
//
//        Log.d("Fetch Data stop ", "UploadDistributorSystem ->Stop<--");
//        return list;
//    }
//
//    //Get UploadDistributorStoreNameData After Save Data Available
//    public ArrayList<UploadDistributorSystemGetterSetter> getAvailableUploadDistributorStoreNameData() {
//        Log.d("Fetch Data start", "getAvailableUploadDistributorStoreNameData  ->Start<-");
//        ArrayList<UploadDistributorSystemGetterSetter> list = new ArrayList<UploadDistributorSystemGetterSetter>();
//        Cursor dbcursor = null;
//
//        try {
///*            dbcursor = db.rawQuery("select ORDER_STATUS,NON_ORDER_REASON " +
//                    "from NonOrderUploadReason", null);*/
//            dbcursor = db.rawQuery("Select DISTINCT a.MID,a.STORE_CD,a.STORENAME,a.ADDRESS," +
//                    "a.CITY,a.VISITDATE,n.ORDER_STATUS,n.NON_ORDER_REASON " +
//                    "from DISTRIBUTOR_UPLOAD a " +
//                    "inner join OrderUpload_DistributorSystem n " +
//                    "on a.MID=n.MID", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    UploadDistributorSystemGetterSetter sb = new UploadDistributorSystemGetterSetter();
//                    sb.setMID(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("MID")));
//                    sb.setSTORE_CD(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("STORE_CD")));
//                    sb.setSTORENAME(dbcursor.getString(dbcursor.getColumnIndexOrThrow("STORENAME")));
//                    sb.setADDRESS(dbcursor.getString(dbcursor.getColumnIndexOrThrow("ADDRESS")));
//                    sb.setCITY(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CITY")));
//                    sb.setVISITDATE(dbcursor.getString(dbcursor.getColumnIndexOrThrow("VISITDATE")));
//                    sb.setORDER_STATUS(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("ORDER_STATUS")));
//                    sb.setNON_ORDER_REASON(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("NON_ORDER_REASON")));
//
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception !!", " getAvailableUploadDistributorStoreNameData " + e.toString());
//            return list;
//        }
//
//        Log.d("Fetch Data stop ", "getAvailableUploadDistributorStoreNameData ->Stop<--");
//        return list;
//    }
//
//    public ArrayList<UploadDistributorSystemGetterSetter> getgetUploadDistributorSkuData(int mid) {
//        Log.d("Fetch Data start", "UploadDistributorSystem  ->Start<-");
//        ArrayList<UploadDistributorSystemGetterSetter> list = new ArrayList<UploadDistributorSystemGetterSetter>();
//        Cursor dbcursor = null;
//
//        try {
//            dbcursor = db.rawQuery("select MID,STORE_CD,BRAND_CD,BRAND,SKU_CD,SKU,ORDER_QTY " +
//                    "from DISTRIBUTOR_UPLOAD " +
//                    "where MID='" + mid + "'", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    UploadDistributorSystemGetterSetter sb = new UploadDistributorSystemGetterSetter();
//                    sb.setMID(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("MID")));
//                    sb.setSTORE_CD(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("STORE_CD")));
//                    sb.setBRAND_CD(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("BRAND_CD")));
//                    sb.setBRAND(dbcursor.getString(dbcursor.getColumnIndexOrThrow("BRAND")));
//                    sb.setSKU_CD(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("SKU_CD")));
//                    sb.setSKU(dbcursor.getString(dbcursor.getColumnIndexOrThrow("SKU")));
//                    sb.setORDER_QTY(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("ORDER_QTY")));
//
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception !!", " UploadDistributorSystem " + e.toString());
//            return list;
//        }
//
//        Log.d("Fetch Data stop ", "UploadDistributorSystem ->Stop<--");
//        return list;
//    }
//
//    //Get Non Order Upload Reason Data
//    public ArrayList<NonOrderUploadReasonGetterSetter> getNonOrderUploadReasonData() {
//        Log.d("Fetch Data start", "NonOrderUploadReasonData  ->Start<-");
//        ArrayList<NonOrderUploadReasonGetterSetter> list = new ArrayList<NonOrderUploadReasonGetterSetter>();
//        Cursor dbcursor = null;
//
//        try {
//            dbcursor = db.rawQuery("SELECT * FROM NON_ORDER_UPLOAD_REASON", null);
//
//            NonOrderUploadReasonGetterSetter sb1 = new NonOrderUploadReasonGetterSetter();
//            sb1.setOREASON_CD(0);
//            sb1.setOREASON("Select Reason");
//            list.add(sb1);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    NonOrderUploadReasonGetterSetter sb = new NonOrderUploadReasonGetterSetter();
//                    sb.setOREASON_CD(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("OREASON_CD")));
//                    sb.setOREASON(dbcursor.getString(dbcursor.getColumnIndexOrThrow("OREASON")));
//
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception !!", " NonOrderUploadReasonData " + e.toString());
//            return list;
//        }
//
//        Log.d("Fetch Data stop ", "NonOrderUploadReasonData ->Stop<--");
//        return list;
//    }
//
//    //Get Distributor System Upload Data for upload to server
//    public ArrayList<UploadDistributorSystemGetterSetter> getUploadDistributorFinalData() {
//        Log.d("Fetch Data start", "getUploadDistributorFinalData  ->Start<-");
//        ArrayList<UploadDistributorSystemGetterSetter> list = new ArrayList<UploadDistributorSystemGetterSetter>();
//        Cursor dbcursor = null;
//
//        try {
//            dbcursor = db.rawQuery("select MID,STORE_CD,VISIT_DATE,ORDER_STATUS,NON_ORDER_REASON " +
//                    "from OrderUpload_DistributorSystem", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    UploadDistributorSystemGetterSetter sb = new UploadDistributorSystemGetterSetter();
//                    sb.setMID(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("MID")));
///*                    sb.setSTORE_CD(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("STORE_CD")));
//                    sb.setVISITDATE(dbcursor.getString(dbcursor.getColumnIndexOrThrow("VISIT_DATE")));*/
//                    sb.setORDER_STATUS(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("ORDER_STATUS")));
//                    sb.setNON_ORDER_REASON(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("NON_ORDER_REASON")));
//
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//                return list;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception !!", " getUploadDistributorFinalData " + e.toString());
//            return list;
//        }
//
//        Log.d("Fetch Data stop ", "getUploadDistributorFinalData ->Stop<--");
//        return list;
//    }
//
//    //Check Distributor Upload  System Status
//    public boolean checkDistributorUploadStatus() {
//        Log.d("Fetch Data start", "checkDistributorUploadStatus  ->Start<-");
//        ArrayList<UploadDistributorSystemGetterSetter> list = new ArrayList<UploadDistributorSystemGetterSetter>();
//        Cursor dbcursor = null;
//        UploadDistributorSystemGetterSetter sb;
//        boolean flag = true;
//
//        try {
//            dbcursor = db.rawQuery("Select DISTINCT MID,STORE_CD,UPLOAD_STATUS " +
//                    "from DISTRIBUTOR_UPLOAD", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    sb = new UploadDistributorSystemGetterSetter();
//
//                    sb.setMID(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("MID")));
//                    sb.setSTORE_CD(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("STORE_CD")));
//                    sb.setUPLOAD_STATUS(dbcursor.getString(dbcursor.getColumnIndexOrThrow("UPLOAD_STATUS")));
//
//                    list.add(sb);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//            }
//
//            for (int i = 0; i < list.size(); i++) {
//                sb = list.get(i);
//                if (sb.getUPLOAD_STATUS().equals(CommonString.KEY_U)) {//|| sb.getUPLOAD_STATUS().equals("")) {
//                    flag = false;
//                } else {
//                    flag = true;
//                }
//
//                if (flag == false) {
//                    break;
//                }
//                return flag;
//            }
//
//
//        } catch (Exception e) {
//            Log.d("Exception !!", " UploadDistributorSystem " + e.toString());
//            return false;
//        }
//
//        Log.d("Fetch Data stop ", "UploadDistributorSystem ->Stop<--");
//        return false;
//    }
//
//    //Get Order Distributor Store Name Data Default
//    public ArrayList<OrderDeliveryClassGetterSetter> getOrderDistributorStoreNameData() {
//        Log.d("Fetch Data start", "OrderDistributorStoreName  ->Start<-");
//        ArrayList<OrderDeliveryClassGetterSetter> list = new ArrayList<>();
//        Cursor dbcursor = null;
//
//        try {
//            dbcursor = db.rawQuery("select DISTINCT MID,STORE_CD,STORENAME,ADDRESS,CITY,VISITDATE,UPLOAD_STATUS " +
//                    "from ORDER_DELIVERY", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//                while (!dbcursor.isAfterLast()) {
//                    OrderDeliveryClassGetterSetter od = new OrderDeliveryClassGetterSetter();
//                    od.setMID(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("MID")));
//                    od.setSTORE_CD(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("STORE_CD")));
//                    od.setSTORENAME(dbcursor.getString(dbcursor.getColumnIndexOrThrow("STORENAME")));
//                    od.setADDRESS(dbcursor.getString(dbcursor.getColumnIndexOrThrow("ADDRESS")));
//                    od.setCITY(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CITY")));
//                    od.setVISITDATE(dbcursor.getString(dbcursor.getColumnIndexOrThrow("VISITDATE")));
//                    od.setUPLOAD_STATUS(dbcursor.getString(dbcursor.getColumnIndexOrThrow("UPLOAD_STATUS")));
//
//                    list.add(od);
//                    dbcursor.moveToNext();
//                }
//                dbcursor.close();
//
//                return list;
//            }
//
//        } catch (Exception e) {
//            Log.d("Exception !!", " OrderDistributorStoreName " + e.toString());
//            return list;
//        }
//        Log.d("Fetch Data stop ", "OrderDistributorStoreName ->Stop<--");
//        return list;
//    }

    // Get Coverage Data List
    public ArrayList<CoverageBean> getCoverageData(String visitdate) {
        ArrayList<CoverageBean> list = new ArrayList<>();
        Cursor dbcursor = null;

        try {
            if (visitdate.equalsIgnoreCase("0")) {
                dbcursor = db.rawQuery("SELECT  * from " + CommonString.TABLE_COVERAGE_DATA + " where "
                        + CommonString.KEY_STORE_CD + "='" + visitdate + "'", null);
            } else {
                dbcursor = db.rawQuery("SELECT  * from " + CommonString.TABLE_COVERAGE_DATA + " where "
                        + CommonString.KEY_VISIT_DATE + "='" + visitdate + "' AND " + CommonString.KEY_STORE_CD + "<>'" + 0 + "'", null);
            }


            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    CoverageBean sb = new CoverageBean();

                    sb.setStoreId(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_STORE_CD)));
                    sb.setUserId((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_USER_ID))));
                    sb.setInTime(((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IN_TIME)))));
                    sb.setOutTime(((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_OUT_TIME)))));
                    sb.setVisitDate((((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_VISIT_DATE))))));
                    sb.setLatitude(((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_LATITUDE)))));
                    sb.setLongitude(((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_LONGITUDE)))));
                    sb.setStatus((((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_COVERAGE_STATUS))))));
                    sb.setImage((((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE))))));
                    sb.setReason((((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_REASON))))));
                    sb.setReasonid((((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_REASON_ID))))));
                    sb.setMID(Integer.parseInt(((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_ID))))));

                    if (dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_COVERAGE_REMARK)) == null) {
                        sb.setRemark("");
                    } else {
                        sb.setRemark((((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_COVERAGE_REMARK))))));
                    }

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            Log.d("Excep fetch Coverage", e.toString());
        }
        return list;
    }

    public CoverageBean getCoverageSpecificData(String coverage_id) {
        CoverageBean sb = new CoverageBean();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT  * from " + CommonString.TABLE_COVERAGE_DATA + " where "
                    + CommonString.KEY_ID + "='" + coverage_id + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {

                    sb.setStoreId(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_STORE_CD)));
                    sb.setUserId((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_USER_ID))));
                    sb.setInTime(((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IN_TIME)))));
                    sb.setOutTime(((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_OUT_TIME)))));
                    sb.setVisitDate((((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_VISIT_DATE))))));
                    sb.setLatitude(((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_LATITUDE)))));
                    sb.setLongitude(((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_LONGITUDE)))));
                    sb.setStatus((((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_COVERAGE_STATUS))))));
                    sb.setImage((((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE))))));
                    sb.setReason((((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_REASON))))));
                    sb.setReasonid((((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_REASON_ID))))));
                    sb.setMID(Integer.parseInt(((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_ID))))));

                    if (dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_COVERAGE_REMARK)) == null) {
                        sb.setRemark("");
                    } else {
                        sb.setRemark((((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_COVERAGE_REMARK))))));
                    }

                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return sb;
            }
        } catch (Exception e) {
            Log.d("Excep fetch Coverage", e.toString());
        }
        return sb;
    }


    public CoverageBean getCoverageStatus(int coverage_id) {
        CoverageBean sb = new CoverageBean();
        Cursor dbcursor = null;
        try {
            dbcursor = db.rawQuery("SELECT  Coverage from " + CommonString.TABLE_COVERAGE_DATA + " where "
                    + CommonString.KEY_ID + "='" + coverage_id + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {

                    sb.setStatus((((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_COVERAGE_STATUS))))));

                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return sb;
            }
        } catch (Exception e) {
            Log.d("Excep fetch Coverage", e.toString());
        }
        return sb;
    }


    //Get Add New Store Data
    public ArrayList<AddNewStoreGetterSetter> getAddNewStoreList() {
        ArrayList<AddNewStoreGetterSetter> list = new ArrayList<>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("select * from " + CommonString.TABLE_ADD_NEW_STORE, null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    AddNewStoreGetterSetter od = new AddNewStoreGetterSetter();

                    od.setKey_id(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("KEY_ID")));
                    od.setStore_id(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("STORE_CD")));
                    od.setCoverage_id(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("COVERAGE_ID")));
                    od.setEd_StoreName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("STORE_NAME")));
                    od.setEd_Address(dbcursor.getString(dbcursor.getColumnIndexOrThrow("ADDRESS")));
                    od.setEd_Phone(String.valueOf(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("PHONE"))));
                    od.setCity_CD(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("CITY_CD")));
                    od.setState_cd(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("STATE_CD")));
                    od.setState(dbcursor.getString(dbcursor.getColumnIndexOrThrow("STATE")));
                    od.setCity(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CITY")));
                    od.setStoreType_CD(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("CHANNEL_CD")));
                    od.setStoreType(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CHANNEL")));
                    od.setImg_Camera(dbcursor.getString(dbcursor.getColumnIndexOrThrow("IMAGE_PATH")));
                    od.setUpload_Status(dbcursor.getString(dbcursor.getColumnIndexOrThrow("UPLOAD_STATUS")));

                    list.add(od);
                    dbcursor.moveToNext();
                }
                dbcursor.close();

                return list;
            }

        } catch (Exception e) {
            Log.d("Exception !!", " getAddNewStoreList " + e.toString());
            return list;
        }
        Log.d("Fetch Data stop ", " getAddNewStoreList ->Stop<--");
        return list;
    }

    public ArrayList<AddNewStoreGetterSetter> getAddNewStoreListForUpload(String coverageid) {
        ArrayList<AddNewStoreGetterSetter> list = new ArrayList<>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("select * from " + CommonString.TABLE_ADD_NEW_STORE + " WHERE COVERAGE_ID = '" + coverageid + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    AddNewStoreGetterSetter od = new AddNewStoreGetterSetter();

                    od.setKey_id(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("KEY_ID")));
                    od.setStore_id(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("STORE_CD")));
                    od.setCoverage_id(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("COVERAGE_ID")));
                    od.setEd_StoreName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("STORE_NAME")));
                    od.setEd_Address(dbcursor.getString(dbcursor.getColumnIndexOrThrow("ADDRESS")));
                    od.setEd_Phone(String.valueOf(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("PHONE"))));
                    od.setCity_CD(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("CITY_CD")));
                    od.setTown_CD(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("TOWN_CD")));
                    od.setState_cd(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("STATE_CD")));
                    od.setState(dbcursor.getString(dbcursor.getColumnIndexOrThrow("STATE")));
                    od.setCity(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CITY")));
                    od.setStoreType_CD(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("CHANNEL_CD")));
                    od.setEd_RetailerName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("RETAILER_NAME")));
                    od.setEd_Landmark(dbcursor.getString(dbcursor.getColumnIndexOrThrow("LANDMARK")));
                    od.setDob(dbcursor.getString(dbcursor.getColumnIndexOrThrow("DOB")));
                    od.setDoa(dbcursor.getString(dbcursor.getColumnIndexOrThrow("DOA")));
                    od.setHasSmartphone(dbcursor.getString(dbcursor.getColumnIndexOrThrow("ANSWER_CD")));
                    od.setStoreType(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CHANNEL")));
                    od.setImg_Camera(dbcursor.getString(dbcursor.getColumnIndexOrThrow("IMAGE_PATH")));
                    od.setUpload_Status(dbcursor.getString(dbcursor.getColumnIndexOrThrow("UPLOAD_STATUS")));

                    list.add(od);
                    dbcursor.moveToNext();
                }
                dbcursor.close();

                return list;
            }

        } catch (Exception e) {
            Log.d("Exception !!", " getAddNewStoreListForUpload " + e.toString());
            return list;
        }
        Log.d("Fetch Data stop ", " getAddNewStoreListForUpload ->Stop<--");
        return list;
    }


    //Get Add New Store Data
    public ArrayList<AddNewStoreGetterSetter> getStoreMasterData(String date) {
        ArrayList<AddNewStoreGetterSetter> list = new ArrayList<>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("select * from " + CommonString.TABLE_ADD_NEW_STORE, null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    AddNewStoreGetterSetter od = new AddNewStoreGetterSetter();

                    od.setStore_id(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("STORE_ID")));
                    od.setEd_StoreName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("STORE_NAME")));
                    od.setEd_Address(dbcursor.getString(dbcursor.getColumnIndexOrThrow("ADDRESS")));
                    od.setEd_Phone(String.valueOf(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("PHONE"))));
                    od.setCity_CD(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("CITY_CD")));
                    od.setCity(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CITY")));
                    od.setStoreType_CD(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("CHANNEL_CD")));
                    od.setStoreType(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CHANNEL")));
                    od.setImg_Camera(dbcursor.getString(dbcursor.getColumnIndexOrThrow("IMAGE_PATH")));
                    od.setUpload_Status(dbcursor.getString(dbcursor.getColumnIndexOrThrow("UPLOAD_STATUS")));

                    list.add(od);
                    dbcursor.moveToNext();
                }
                dbcursor.close();

                return list;
            }

        } catch (Exception e) {
            Log.d("Exception !!", " getAddNewStoreList " + e.toString());
            return list;
        }
        Log.d("Fetch Data stop ", " getAddNewStoreList ->Stop<--");
        return list;
    }


    public ArrayList<AddNewStoreGetterSetter> getNewStoreListFromJCP(String date) {
        ArrayList<AddNewStoreGetterSetter> list = new ArrayList<>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("select * from JOURNEY_PLAN where VISIT_DATE = '" + date + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    AddNewStoreGetterSetter od = new AddNewStoreGetterSetter();

                    od.setStore_id(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("STORE_CD")));
                    od.setEd_StoreName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("STORENAME")));

                    od.setCity(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CITY")));
                    od.setState_cd(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("STATE_CD")));
                    od.setStoreType_CD(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("STORETYPE_CD")));
                    od.setStoreType(dbcursor.getString(dbcursor.getColumnIndexOrThrow("STORETYPE")));
                    od.setUpload_Status(dbcursor.getString(dbcursor.getColumnIndexOrThrow("UPLOAD_STATUS")));
                    od.setCHECKOUT_STATUS(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CHECKOUT_STATUS")));

                    list.add(od);
                    dbcursor.moveToNext();
                }
                dbcursor.close();

                return list;
            }

        } catch (Exception e) {
            Log.d("Exception !!", " getAddNewStoreList " + e.toString());
            return list;
        }
        Log.d("Fetch Data stop ", " getAddNewStoreList ->Stop<--");
        return list;
    }


    public ArrayList<LineEntryGetterSetter> getLineEntryData(String store_cd, String coverage_id) {
        ArrayList<LineEntryGetterSetter> list = new ArrayList<LineEntryGetterSetter>();
        LineEntryGetterSetter addLineEntry = null;
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("select * from LINE_ENTRY WHERE STORE_CD = '" + store_cd + "' and COVERAGE_ID = '" + coverage_id + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    addLineEntry = new LineEntryGetterSetter();
                    addLineEntry.setTotalLineAvailable(dbcursor.getString(dbcursor.getColumnIndexOrThrow("TOTAL_LINE_AVAILABLE")));
                    addLineEntry.setNoOfBuildSkuLine(dbcursor.getString(dbcursor.getColumnIndexOrThrow("NO_OF_SKULINE")));
                    addLineEntry.setTotalBillAmount(dbcursor.getString(dbcursor.getColumnIndexOrThrow("TOTAL_BILL_AMOUNT")));
                    list.add(addLineEntry);

                    dbcursor.moveToNext();
                }
                dbcursor.close();

                return list;
            }

        } catch (Exception e) {
            Log.d("Exception !!", "  getLineEntryData" + e.toString());
        }

        Log.d("Fetch Data stop ", " getLineEntryData ->Stop<--");
        return null;
    }


    //Get  City Master List by state_cd
    public ArrayList<CityMasterGetterSetter> getCityMasterList(String state_cd) {
        ArrayList<CityMasterGetterSetter> list = new ArrayList<CityMasterGetterSetter>();
        CityMasterGetterSetter addCity = null;
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("select -1 as CITY_CD,'Select' as CITY union select CITY_CD,CITY  from CITY_MASTER where STATE_CD='" + state_cd + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    addCity = new CityMasterGetterSetter();
                    addCity.setCITY_CD(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CITY_CD")));
                    addCity.setCITY(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CITY")));
                    list.add(addCity);

                    dbcursor.moveToNext();
                }
                dbcursor.close();

                return list;
            }

        } catch (Exception e) {
            Log.d("Exception !!", "  getCityMasterList" + e.toString());
        }

        Log.d("Fetch Data stop ", " getCityMasterList ->Stop<--");
        return null;
    }


    //Get  City Master List by state_cd
    public ArrayList<TownMasterGetterSetter> getTownMasterList(String city_cd) {
        ArrayList<TownMasterGetterSetter> list = new ArrayList<TownMasterGetterSetter>();
        TownMasterGetterSetter addTown = null;
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("select -1 as TOWN_CD,'Select' as TOWN union select TOWN_CD,TOWN  from TOWN_MASTER where CITY_CD='" + city_cd + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    addTown = new TownMasterGetterSetter();
                    addTown.setTOWN_CD(dbcursor.getString(dbcursor.getColumnIndexOrThrow("TOWN_CD")));
                    addTown.setTOWN(dbcursor.getString(dbcursor.getColumnIndexOrThrow("TOWN")));
                    list.add(addTown);

                    dbcursor.moveToNext();
                }
                dbcursor.close();

                return list;
            }

        } catch (Exception e) {
            Log.d("Exception !!", "  getTownMasterList" + e.toString());
        }

        Log.d("Fetch Data stop ", " getTownMasterList ->Stop<--");
        return null;
    }


    //Get  state Master List by state_cd
    public ArrayList<StateMasterGetterSetter> getStateMasterList() {
        ArrayList<StateMasterGetterSetter> list = new ArrayList<StateMasterGetterSetter>();
        StateMasterGetterSetter addState = null;
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("select -1 as STATE_CD,'Select' as STATE union select STATE_CD,STATE  from STATE_MASTER ", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    addState = new StateMasterGetterSetter();
                    addState.setSTATE_CD(dbcursor.getString(dbcursor.getColumnIndexOrThrow("STATE_CD")));
                    addState.setSTATE(dbcursor.getString(dbcursor.getColumnIndexOrThrow("STATE")));
                    list.add(addState);

                    dbcursor.moveToNext();
                }
                dbcursor.close();

                return list;
            }

        } catch (Exception e) {
            Log.d("Exception !!", "  getStateMasterList" + e.toString());
        }

        Log.d("Fetch Data stop ", " getStateMasterList ->Stop<--");
        return null;
    }

    //Get  Brand Master List by state_cd
    public ArrayList<BrandMasterGetterSetter> getBrandMasterList(String state_cd, String storetype_cd) {
        ArrayList<BrandMasterGetterSetter> list = new ArrayList<BrandMasterGetterSetter>();
        BrandMasterGetterSetter addBrand = null;
        Cursor dbcursor = null;

        try {
            //dbcursor = db.rawQuery("select -1 as BRAND_CD,'-Select-' as BRAND union select BRAND_CD,BRAND from BRAND_MASTER", null);

            dbcursor = db.rawQuery("Select -1 as BRAND_CD,'Select' as BRAND union SELECT DISTINCT BR.BRAND_CD, BR.BRAND FROM POSM_MAPPING M INNER JOIN BRAND_MASTER BR ON M.BRAND_CD = BR.BRAND_CD WHERE STATE_CD = '" + state_cd + "' AND STORETYPE_CD = '" + storetype_cd + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    addBrand = new BrandMasterGetterSetter();
                    addBrand.setBRAND_CD(dbcursor.getString(dbcursor.getColumnIndexOrThrow("BRAND_CD")));
                    addBrand.setBRAND(dbcursor.getString(dbcursor.getColumnIndexOrThrow("BRAND")));
                    list.add(addBrand);

                    dbcursor.moveToNext();
                }
                dbcursor.close();

                return list;
            }

        } catch (Exception e) {
            Log.d("Exception !!", "  getBrandMasterList" + e.toString());
        }

        Log.d("Fetch Data stop ", " getBrandMasterList ->Stop<--");
        return null;
    }

    //Get  Brand Master List by state_cd
    public ArrayList<PosmMasterGetterSetter> getPosmMasterList(String state_cd, String storetype_cd, String brand_cd) {
        ArrayList<PosmMasterGetterSetter> list = new ArrayList<PosmMasterGetterSetter>();
        PosmMasterGetterSetter addPosm = null;
        Cursor dbcursor = null;
        try {
            // dbcursor = db.rawQuery("select -1 as POSM_CD,'-Select-' as POSM union select POSM_CD,POSM from POSM_MASTER", null);

            dbcursor = db.rawQuery("Select -1 as POSM_CD,'Select' as POSM union SELECT DISTINCT P.POSM_CD, P.POSM FROM POSM_MAPPING M INNER JOIN POSM_MASTER P ON M.POSM_CD = P.POSM_CD  WHERE M.STATE_CD = '" + state_cd + "' AND M.STORETYPE_CD = '" + storetype_cd + "' AND M.BRAND_CD = '" + brand_cd + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    addPosm = new PosmMasterGetterSetter();
                    addPosm.setPOSM_CD(dbcursor.getString(dbcursor.getColumnIndexOrThrow("POSM_CD")));
                    addPosm.setPOSM(dbcursor.getString(dbcursor.getColumnIndexOrThrow("POSM")));
                    list.add(addPosm);

                    dbcursor.moveToNext();
                }
                dbcursor.close();

                return list;
            }

        } catch (Exception e) {
            Log.d("Exception !!", "  getPosmMasterList" + e.toString());
        }

        Log.d("Fetch Data stop ", " getPosmMasterList ->Stop<--");
        return null;
    }


    //Get  StoreTypeMasterList
    public ArrayList<StoreTypeMasterGetterSetter> getStoreTypeMasterList() {
        ArrayList<StoreTypeMasterGetterSetter> list = new ArrayList<StoreTypeMasterGetterSetter>();
        StoreTypeMasterGetterSetter addstoreType = null;
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("select -1 as STORETYPE_CD, 'Select' as STORETYPE union select STORETYPE_CD,STORETYPE from STORETYPE_MASTER", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    addstoreType = new StoreTypeMasterGetterSetter();
                    addstoreType.setSTORETYPE_CD(dbcursor.getString(dbcursor.getColumnIndexOrThrow("STORETYPE_CD")));
                    addstoreType.setSTORETYPE(dbcursor.getString(dbcursor.getColumnIndexOrThrow("STORETYPE")));
                    list.add(addstoreType);

                    dbcursor.moveToNext();
                }
                dbcursor.close();

                return list;
            }

        } catch (Exception e) {
            Log.d("Exception !!", " getStoreTypeMasterList " + e.toString());
        }

        Log.d("Fetch Data stop ", " getStoreTypeMasterList ->Stop<--");
        return null;
    }


    //Get  AnswerList
    public ArrayList<AnswerMasterGetterSetter> getAnswerMasterList(Boolean fromToolbarSpinner) {
        ArrayList<AnswerMasterGetterSetter> list = new ArrayList<AnswerMasterGetterSetter>();
        AnswerMasterGetterSetter addAnswer = null;

        try {
            addAnswer = new AnswerMasterGetterSetter();
            addAnswer.setANSWER_CD("-1");
            addAnswer.setANSWER("Select");
            list.add(addAnswer);
            addAnswer = new AnswerMasterGetterSetter();
            addAnswer.setANSWER_CD("1");
            if (fromToolbarSpinner) {
                addAnswer.setANSWER("Paid");
            } else {
                addAnswer.setANSWER("Yes");
            }
            list.add(addAnswer);
            addAnswer = new AnswerMasterGetterSetter();
            addAnswer.setANSWER_CD("0");
            if (fromToolbarSpinner) {
                addAnswer.setANSWER("Non Paid");
            } else {
                addAnswer.setANSWER("No");
            }
            list.add(addAnswer);

        } catch (Exception e) {
            Log.d("Exception !!", " getAnswerMasterList " + e.toString());
        }

        Log.d("Fetch Data stop ", " getAnswerMasterList ->Stop<--");
        return list;
    }


    //Get  AnswerList
    public ArrayList<AnswerMasterGetterSetter> getAnswerMasterListForWindow(String checklist_cd) {
        ArrayList<AnswerMasterGetterSetter> list = new ArrayList<AnswerMasterGetterSetter>();
        AnswerMasterGetterSetter addAnswer = null;
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT -1 as ANSWER_CD,'Select' as ANSWER union SELECT ANSWER_CD,ANSWER FROM WINDOW_CHECKLIST_ANSWER WHERE CHECKLIST_CD = '" + checklist_cd + "'", null);
            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    addAnswer = new AnswerMasterGetterSetter();
                    addAnswer.setANSWER_CD(dbcursor.getString(dbcursor.getColumnIndexOrThrow("ANSWER_CD")));
                    addAnswer.setANSWER(dbcursor.getString(dbcursor.getColumnIndexOrThrow("ANSWER")));

                    list.add(addAnswer);

                    dbcursor.moveToNext();
                }
                dbcursor.close();

                return list;
            }

        } catch (Exception e) {
            Log.d("Exception !!", " getAnswerMasterListForWindow " + e.toString());
        }

        Log.d("Fetch Data stop ", " getAnswerMasterListForWindow ->Stop<--");
        return list;
    }

    //Get  AnswerList
    public ArrayList<WindowChecklistGetterSetter> getAnswerListForSavedWindowData(String key_id) {
        ArrayList<WindowChecklistGetterSetter> list = new ArrayList<WindowChecklistGetterSetter>();

        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("select * from SECONDARY_WINDOW_CHILD_DATA where COMMON_ID= '" + key_id + "'", null);
            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    WindowChecklistGetterSetter addAnswer = new WindowChecklistGetterSetter();
                    addAnswer.setWINDOW_CD(dbcursor.getString(dbcursor.getColumnIndexOrThrow("WINDOW_CD")));
                    addAnswer.setCHECKLIST_CD(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CHECKLIST_CD")));
                    addAnswer.setCHECKLIST(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CHECKLIST")));
                    addAnswer.setANSWER_CD(dbcursor.getString(dbcursor.getColumnIndexOrThrow("ANSWER_CD")));
                    addAnswer.setANSWER(dbcursor.getString(dbcursor.getColumnIndexOrThrow("ANSWER")));

                    list.add(addAnswer);

                    dbcursor.moveToNext();
                }
                dbcursor.close();

                return list;
            }

        } catch (Exception e) {
            Log.d("Exception !!", " getAnswerMasterListForWindow " + e.toString());
        }

        Log.d("Fetch Data stop ", " getAnswerMasterListForWindow ->Stop<--");
        return list;
    }


    //Get  Window Master List by state_cd
    public ArrayList<WindowMasterGetterSetter> getWindowMasterList(String state_cd, String storetype_cd) {
        ArrayList<WindowMasterGetterSetter> list = new ArrayList<WindowMasterGetterSetter>();
        WindowMasterGetterSetter addWindow = null;
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT W.WINDOW_CD,W.WINDOW,W.SKU_HOLD,PLANOGRAM_IMAGE FROM  WINDOW_MAPPING WM INNER JOIN WINDOW_MASTER W ON WM.WINDOW_CD = W.WINDOW_CD WHERE WM.STATE_CD = '" + state_cd + "' AND WM.STORETYPE_CD = '" + storetype_cd + "' ", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    addWindow = new WindowMasterGetterSetter();
                    addWindow.setWINDOW_CD(dbcursor.getString(dbcursor.getColumnIndexOrThrow("WINDOW_CD")));
                    addWindow.setWINDOW(dbcursor.getString(dbcursor.getColumnIndexOrThrow("WINDOW")));
                    addWindow.setSKU_HOLD(dbcursor.getString(dbcursor.getColumnIndexOrThrow("SKU_HOLD")));
                    addWindow.setPLANOGRAM_IMAGE(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PLANOGRAM_IMAGE")));
                    addWindow.setCameraIcon(R.drawable.camera_orange);
                    list.add(addWindow);

                    dbcursor.moveToNext();
                }
                dbcursor.close();

                return list;
            }

        } catch (Exception e) {
            Log.d("Exception !!", "  getWindowMasterList" + e.toString());
        }

        Log.d("Fetch Data stop ", " getWindowMasterList ->Stop<--");
        return null;
    }


    //Get  SavedWindowData by state_cd and storetype_cd
    public ArrayList<WindowMasterGetterSetter> getSavedWindowData(String store_cd, String coverage_id) {
        ArrayList<WindowMasterGetterSetter> list = new ArrayList<WindowMasterGetterSetter>();
        WindowMasterGetterSetter addWindow = null;
        Cursor dbcursor = null;
        try {
            dbcursor = db.rawQuery("select * from SECONDARY_WINDOW_HEADER_DATA where store_cd= '" + store_cd + "' AND COVERAGE_ID = '" + coverage_id + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    addWindow = new WindowMasterGetterSetter();
                    addWindow.setWINDOW_CD(dbcursor.getString(dbcursor.getColumnIndexOrThrow("WINDOW_CD")));
                    addWindow.setWINDOW(dbcursor.getString(dbcursor.getColumnIndexOrThrow("WINDOW")));
                    addWindow.setWINDOW_EXIST(CommonFunctions.convertInttoBool(dbcursor.getString(dbcursor.getColumnIndexOrThrow("IS_EXISTS"))));
                    addWindow.setWINDOW_IMAGE(dbcursor.getString(dbcursor.getColumnIndexOrThrow("WINDOW_IMAGE")));
                    addWindow.setKey_id(dbcursor.getString(dbcursor.getColumnIndexOrThrow("KEY_ID")));
                    addWindow.setPaidOrNonpaid(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_PAID_OR_NONPAID)));
                    if (addWindow.isWINDOW_EXIST()) {
                        addWindow.setCameraIcon(R.drawable.camera_green);
                    } else {
                        addWindow.setCameraIcon(R.drawable.camera_orange);
                    }

                    list.add(addWindow);

                    dbcursor.moveToNext();
                }
                dbcursor.close();

                return list;
            }

        } catch (Exception e) {
            Log.d("Exception !!", "  getWindowMasterList" + e.toString());
        }

        Log.d("Fetch Data stop ", " getWindowMasterList ->Stop<--");
        return null;
    }


    //Get  Window Master List by state_cd
    public ArrayList<WindowChecklistGetterSetter> getWindowCheckList(String window_cd) {
        ArrayList<WindowChecklistGetterSetter> list = new ArrayList<WindowChecklistGetterSetter>();
        WindowChecklistGetterSetter addchecklist = null;
        Cursor dbcursor = null;

        try {
//            dbcursor = db.rawQuery("SELECT MWC.WINDOW_CD,C.CHECKLIST_CD,C.CHECKLIST FROM  WINDOW_MAPPING WM" +
//                    " INNER JOIN MAPPING_WINDOW_CHECKLIST MWC ON WM.WINDOW_CD = MWC.WINDOW_CD" +
//                    " INNER JOIN WINDOW_CHECKLIST C ON MWC.CHECKLIST_CD = C.CHECKLIST_CD" +
//                    " WHERE WM.STATE_CD = '"+state_cd+"' AND WM.STORETYPE_CD = '"+storetype_cd+"'", null);

            dbcursor = db.rawQuery("SELECT MWC.WINDOW_CD,C.CHECKLIST_CD,C.CHECKLIST FROM MAPPING_WINDOW_CHECKLIST MWC INNER JOIN WINDOW_CHECKLIST C ON  MWC.CHECKLIST_CD = C.CHECKLIST_CD WHERE MWC.WINDOW_CD = '" + window_cd + "'", null);


            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    addchecklist = new WindowChecklistGetterSetter();
                    addchecklist.setWINDOW_CD(dbcursor.getString(dbcursor.getColumnIndexOrThrow("WINDOW_CD")));
                    addchecklist.setCHECKLIST_CD(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CHECKLIST_CD")));
                    addchecklist.setCHECKLIST(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CHECKLIST")));

                    list.add(addchecklist);

                    dbcursor.moveToNext();
                }
                dbcursor.close();

                return list;
            }

        } catch (Exception e) {
            Log.d("Exception !!", "  getWindowMasterList" + e.toString());
        }

        Log.d("Fetch Data stop ", " getWindowMasterList ->Stop<--");
        return null;
    }


    //Update Data
    public void updateCoverageStatus(int key_id, String status) {
        try {
            ContentValues values = new ContentValues();
            values.put(CommonString.KEY_COVERAGE_STATUS, status);

            db.update(CommonString.TABLE_COVERAGE_DATA, values,
                    CommonString.KEY_ID + "='" + key_id + "'", null);

        } catch (Exception e) {

        }
    }

    //    //Update Coverage Status
//    public void updateCoverageStatus1(String Store_cd, String status) {
//        try {
//            ContentValues values = new ContentValues();
//            values.put(CommonString.KEY_COVERAGE_STATUS, status);
//
//            db.update(CommonString.TABLE_COVERAGE_DATA, values,
//                    CommonString.KEY_STORE_CD + "=" + Store_cd, null);
//        } catch (Exception e) {
//            Log.e("Database", "UpdateCoverageStatus Failed" + e.getMessage());
//        }
//    }
//
    //Coverage Store Out Time
    public void updateCoverageStoreOutTime(String StoreId, String VisitDate, String outtime, String status) {
        try {
            ContentValues values = new ContentValues();
            values.put(CommonString.KEY_OUT_TIME, outtime);
            values.put(CommonString.KEY_COVERAGE_STATUS, status);

            db.update(CommonString.TABLE_COVERAGE_DATA, values, CommonString.KEY_STORE_CD + "='" + StoreId + "' AND "
                    + CommonString.KEY_VISIT_DATE + "='" + VisitDate
                    + "'", null);
        } catch (Exception e) {
            e.toString();
        }
    }


    public void updateCoverageStoreOutTimeOfAddstore(String id, String VisitDate, String outtime, String status) {
        try {
            ContentValues values = new ContentValues();
            values.put(CommonString.KEY_OUT_TIME, outtime);
            values.put(CommonString.KEY_COVERAGE_STATUS, status);

            db.update(CommonString.TABLE_COVERAGE_DATA, values,
                    CommonString.KEY_ID + "='" + id + "'", null);


         /*   db.update(CommonString.TABLE_COVERAGE_DATA, values, CommonString.KEY_ID + "='" + StoreId + "' AND "
                    + CommonString.KEY_VISIT_DATE + "='" + VisitDate
                    + "'", null);*/
        } catch (Exception e) {
            e.toString();
        }
    }


//    //Update checkout status in JCP
//    public void updateStoreStatusOnCheckout(String storeid, String visitdate, String status) {
//        try {
//            ContentValues values = new ContentValues();
//            values.put(CommonString.KEY_CHECKOUT_STATUS, status);
//
//            db.update("JOURNEY_PLAN", values,
//                    CommonString.KEY_STORE_CD + "='" + storeid + "' AND "
//                            + CommonString.KEY_VISIT_DATE + "='" + visitdate + "'", null);
//        } catch (Exception e) {
//            e.toString();
//        }
//    }
//
//    //Update coverage data on leave selected
//    public void updateStoreStatusOnLeave(String storeid, String visitdate, String status) {
//        try {
//            ContentValues values = new ContentValues();
//            values.put("UPLOAD_STATUS", status);
//
//            db.update("JOURNEY_PLAN", values, CommonString.KEY_STORE_CD + "='" + storeid + "' AND "
//                    + CommonString.KEY_VISIT_DATE + "='" + visitdate + "'", null);
//        } catch (Exception e) {
//            Log.e("Database", "updateStoreStatusOnLeave " + e.getMessage());
//        }
//    }
//
//    //Update Add New Store Upload Status
//    public void UpdateAddNewStoreUploadStatus(int store_id, String uploadStatus) {
//        try {
//            ContentValues values = new ContentValues();
//            values.put("UPLOAD_STATUS", uploadStatus);
//
//            db.update(CommonString.TABLE_ADD_NEW_STORE, values, "STORE_ID='" + store_id + "'", null);
//        } catch (NumberFormatException e) {
//            e.printStackTrace();
//        }
//    }
//
//
////Delete
//
//    public void deleteAddNewStoreListByUserId(int store_id) {
//        db.delete(CommonString.TABLE_ADD_NEW_STORE, "STORE_ID=" + store_id, null);
//    }
//
////Other Methods
//
//    //Check Mid
//    public int CheckMid(String currdate, String storeid) {
//        Cursor dbcursor = null;
//        int mid = 0;
//        try {
//            dbcursor = db.rawQuery("SELECT  * from " + CommonString.TABLE_COVERAGE_DATA +
//                    "  WHERE " + CommonString.KEY_VISIT_DATE + " = '" + currdate +
//                    "' AND " + CommonString.KEY_STORE_CD + " ='" + storeid + "'", null);
//
//            if (dbcursor != null) {
//                dbcursor.moveToFirst();
//
//                mid = dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_ID));
//                dbcursor.close();
//            }
//        } catch (Exception e) {
//            Log.d("Exception mid", e.toString());
//        }
//        return mid;
//    }


}
