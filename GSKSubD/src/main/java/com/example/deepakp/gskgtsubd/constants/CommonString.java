package com.example.deepakp.gskgtsubd.constants;

import android.os.Environment;

/**
 * Created by yadavendras on 17-06-2016.
 */
public class CommonString {
    public static final String DATABASE_NAME = "GSKGTSubD_Databases";

    public static final String KEY_USERNAME = "username";
    public static final String KEY__USERNAME = "USERNAME";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_REMEMBER = "remember";

    public static final String KEY_INTENT = "KEY_INTENT";

    public static final String KEY_TRAINING_MODE_CD = "TRAINING_MODE_CD";
    public static final String KEY_MANAGED = "MANAGED";

    public static final String KEY_TOPIC_CD = "TOPIC_CD";

    public static final String KEY_MID = "MID";

    public static final String KEY_ISD_CD = "ISD_CD";

    public static final String KEY_ISD_NAME = "ISD_NAME";
    public static final String KEY_Upload_Store_ChecOut_Status = "Upload_Store_ChecOut_Status";

    public static final String KEY_NAME = "NAME";

    public static final String KEY_PHONE_NO = "PHONE_NO";

    public static final String KEY_IS_ISD = "IS_ISD";
    public static final String KEY_IS_EXISTS = "IS_EXISTS";
    public static final String KEY_STORE_IN_TIME = "Store_in_time";
    public static final String KEY_STATE_CD = "STATE_CD";
    public static final String KEY_STOREVISITED_STATUS = "STOREVISITED_STATUS";


    public static final String KEY_DISTRIBUTOR_UPLOAD_STATUS = "Distributor_Upload_Status";

    //Url etc
    public static final String URL = "http://gsksubd.parinaam.in/GSKsubservice.asmx";
    public static final String URL_Notice_Board = "http://gsksubd.parinaam.in/notice/notice.html";
    public static final String NAMESPACE = "http://tempuri.org/";

    public static final String METHOD_NAME_UNIVERSAL_DOWNLOAD = "Download_Universal";
    public static final String SOAP_ACTION_UNIVERSAL = "http://tempuri.org/"
            + METHOD_NAME_UNIVERSAL_DOWNLOAD;

    public static final String METHOD_UPLOAD_XML = "DrUploadXml";
    public static final String SOAP_ACTION = "http://tempuri.org/";

    public static final String METHOD_UPLOAD_DR_STORE_COVERAGE = "UPLOAD_COVERAGE";
    public static final String MEHTOD_UPLOAD_COVERAGE_STATUS = "UploadCoverage_Status";

    public static final String METHOD_UPLOAD_REASON_ATTENDANCE = "NON_WORKING_REASON_ATTENDANCE";

    public static final String METHOD_UPLOAD_IMAGE = "GetImageWithFolderName";

    public static final String SOAP_ACTION_UPLOAD_IMAGE = "http://tempuri.org/" + METHOD_UPLOAD_IMAGE;

    public static final String FILE_PATH = Environment.getExternalStorageDirectory() + "/GSKGTSubD_Image/";

    public static final String UERNAME_OR_PASSWORD_IS_WRONG = "User id or password not matched with current user";
    public static final String NO_INTERNET_CONNECTION = "No internet connection found";

    public static final String ONBACK_ALERT_MESSAGE = "Unsaved data will be lost - Do you want to continue?";

    public static final String DATA_DELETE_ALERT_MESSAGE = "Saved data will be lost - Do you want to continue?";

    public static final String METHOD_LOGIN = "UserLoginDetail";
    public static final String SOAP_ACTION_LOGIN = "http://tempuri.org/" + METHOD_LOGIN;

    public static final String KEY_SUCCESS = "Success";
    public static final String KEY_FAILURE = "Failure";
    public static final String KEY_FALSE = "False";
    public static final String KEY_CHANGED = "Changed";

    public static final String KEY_NO_DATA = "NoData";
    public static final String KEY_ISDATADOWNLOADED = "isdatadownloaded";

    public static final String KEY_PATH = "path";
    public static final String KEY_VERSION = "version";
    public static final String KEY_USER_TYPE = "RIGHTNAME";

    public static final String KEY_DATE = "date";

    public static final String KEY_ = "date";

    //----- Alert Messages
    public static final String MESSAGE_DELETE = "Do You Want To deletecross This Record";
    public static final String MESSAGE_SAVE = "Do You Want To Save The Data ";
    public static final String MESSAGE_FAILURE = "Server Eroor.Please Access After Some Time";
    public static final String MESSAGE_JCP_FALSE = "Data is not found in ";
    public static final String MESSAGE_INVALID_DATA = "Enter Data";
    public static final String MESSAGE_DUPLICATE_DATA = "Data Already Exist";
    public static final String MESSAGE_DOWNLOAD = "Data Downloaded Successfully";
    public static final String MESSAGE_UPLOAD_DATA = "Data Uploaded Successfully";
    public static final String MESSAGE_UPLOAD_IMAGE = "Images Uploaded Successfully";
    public static final String MESSAGE_FALSE = "Invalid User";
    public static final String MESSAGE_CHANGED = "Invalid UserId Or Password / Password Has Been Changed.";
    public static final String MESSAGE_EXIT = "Do You Want To Exit";
    public static final String MESSAGE_BACK = "Use Back Button";
    public static final String MESSAGE_EXCEPTION = "Network Communication Failure. Check Your Network Connection";
    public static final String MESSAGE_SOCKETEXCEPTION = "Network Communication Failure. Check Your Network Connection";
    public static final String MESSAGE_NO_DATA = "No Data For Upload";
    public static final String MESSAGE_NO_IMAGE = "No Image For Upload";
    public static final String MESSAGE_DATA_FIRST = "Upload Data First";
    public static final String MESSAGE_IMAGE_UPLOAD = "Upload Images";
    public static final String MESSAGE_DATA_ALREADY_UPLOADED = "Data Already Uploaded";
    public static final String MESSAGE_SOTORE_ALREADY_CLOSED = "Store Already Closed";

    public static final String MESSAGE_TRAINING_DATA_ALREADY_FILLED = "Training Data Already Filled";
    public static final String MESSAGE_STORE_ALREADY_CHECKED_OUT = "Store Already Checked out";
    public static final String MESSAGE_FIRST_CHECKOUT = "First checkout from previous store";
    public static final String MESSAGE_CHECKOUT = "Checkedout Successfully";

    public static final String MESSAGE_ANSWER_ALL_QUESTION = "Answer all the questions";
    public static final String MESSAGE_SELECT_TRAINING_TOPIC = "Select training topic";
    public static final String MESSAGE_FIRST_ENTER_QUANTITY = "First enter quantity";
    public static final String MESSAGE_DOWNLOAD_DATA_FIRST = "Download data first";
    public static final String MESSAGE_FILL_ALL_DATA = "Fill all the data ";
    public static final String MESSAGE_SELECT_STORE_TYPE = "Select store type";
    public static final String MESSAGE_CLICK_STORE_IMAGE = "Click store image";
    public static final String MESSAGE_ENTER_VALID_EMAIL = "Enter valid email address";
    public static final String MESSAGE_STORE_ALREADY_ADDED = "This store is already added";
    public static final String MESSAGE_CLICK_IMAGE_FOR_QUANTITY = "Plese click image for a filled quantity";
    public static final String MESSAGE_FILL_ALL_DATA_OR_ZERO = "Please fill 0 if Posm not available";
    public static final String MESSAGE_UNSAVED_DATA_LOST = "Unsaved data will be lost - want to continue";

    //Table Store Data
    public static final String TABLE_STORE_DATA = "STORE_DATA";

    public static final String TABLE_ANSWERED_DATA = "ANSWERED_DATA";
    public static final String TABLE_CHECKLIST_INSERTED_DATA = "CHECKLIST_INSERTED_DATA";

    public static final String TABLE_NEW_ISD = "NEW_ISD";

    public static final String TABLE_ADD_NEW_EMPLOYEE = "ADD_NEW_EMPLOYEE";

    public static final String TABLE_COVERAGE_DATA = "COVERAGE_DATA";

    public static final String KEY_ID = "KEY_ID";
    public static final String KEY_CITY = "CITY";
    public static final String KEY_EMAIL = "EMAIL";
    public static final String KEY_LOCALITY = "LOCALITY";
    public static final String KEY_MARKET_NAME = "MARKET";
    public static final String KEY_OWNER_CONTACT_NO = "OWNER_CONTACT_NO";
    public static final String KEY_OWNER_NAME = "OWNER_NAME";
    public static final String KEY_SHOP_NO = "SHOP_NO";
    public static final String KEY_STORE_IMAGE = "STORE_IMAGE";
    public static final String KEY_STORE_NAME = "STORE_NAME";
    public static final String KEY_STORE_TYPE = "STORE_TYPE";
    public static final String KEY_STORE_TYPE_CD = "STORE_TYPE_CD";
    public static final String KEY_TELEPHONE_NO_ONE = "TELEPHONE_NO";
    public static final String KEY_TELEPHONE_NO_TWO = "TELEPHONE_NO_TWO";
    public static final String KEY_UPLOAD_STATUS = "UPLOAD_STATUS";
    public static final String KEY_CHECKOUT_STATUS = "CHECKOUT_STATUS";

    public static final String KEY_IN_TIME = "IN_TIME";
    public static final String KEY_OUT_TIME = "OUT_TIME";
    public static final String KEY_VISIT_DATE = "VISIT_DATE";
    public static final String KEY_LATITUDE = "LATITUDE";
    public static final String KEY_LONGITUDE = "LONGITUDE";
    public static final String KEY_COVERAGE_STATUS = "Coverage";
    public static final String KEY_COVERAGE_ID = "COVERAGE_ID";
    public static final String KEY_REASON = "REASON";
    public static final String KEY_REASON_ID = "REASON_ID";
    public static final String KEY_USER_ID = "USER_ID";
    public static final String KEY_IMAGE = "IMAGE";
    public static final String KEY_COVERAGE_REMARK = "COVERAGE_REMARK";
    public static final String MID = "MID";
    public static final String KEY_P = "P";
    public static final String KEY_D = "D";
    public static final String KEY_U = "U";
    public static final String KEY_C = "Y";
    public static final String KEY_CHECK_IN = "I";
    public static final String KEY_L = "Leave";
    public static final String KEY_VALID = "Valid";
    public static final String KEY_INVALID = "InValid";
    public static final String STORE_STATUS_LEAVE = "L";
    public static final String KEY_WINDOW_CD = "WINDOW_CD";
    public static final String KEY_WINDOW = "WINDOW";
    public static final String KEY_WINDOW_IMAGE = "WINDOW_IMAGE";
    public static final String KEY_FROMSTORE = "FROMSTORE";
    public static final String KEY_PAID_OR_NONPAID = "PAID_OR_NONPAID";


    public static final String KEY_QUESTION_CD = "QUESTION_CD";
    public static final String KEY_ANSWER_CD = "ANSWER_CD";
    public static final String KEY_ANSWER = "ANSWER";

    public static final String KEY_STORE_CD = "STORE_CD";

    public static final String CREATE_TABLE_COVERAGE_DATA = "CREATE TABLE  IF NOT EXISTS "
            + TABLE_COVERAGE_DATA + " ("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
            + KEY_STORE_CD + " INTEGER,"
            + KEY_USER_ID + " VARCHAR,"
            + KEY_IN_TIME + " VARCHAR,"
            + KEY_OUT_TIME + " VARCHAR,"
            + KEY_VISIT_DATE + " VARCHAR,"
            + KEY_LATITUDE + " VARCHAR,"
            + KEY_LONGITUDE + " VARCHAR,"
            + KEY_COVERAGE_STATUS + " VARCHAR,"
            + KEY_IMAGE + " VARCHAR,"
            + KEY_REASON_ID + " VARCHAR,"
            + KEY_COVERAGE_REMARK + " VARCHAR,"
            + KEY_REASON + " VARCHAR)";


    public static final String CREATE_TABLE_ANSWERED_DATA = "CREATE TABLE "
            + TABLE_ANSWERED_DATA
            + " ("
            + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
            + KEY_STORE_CD + " INTEGER,"
            + KEY_TOPIC_CD + " INTEGER,"
            + KEY_QUESTION_CD + " INTEGER,"
            + KEY_ANSWER + " VARCHAR,"
            + KEY_ANSWER_CD + " INTEGER,"
            + KEY_TRAINING_MODE_CD + " INTEGER,"
            + KEY_MID + " INTEGER,"
            + KEY_ISD_CD + " INTEGER)";

    public static final String KEY_CHECKLIST_CD = "CHECKLIST_CD";
    public static final String KEY_CHECKLIST = "CHECKLIST";
    public static final String KEY_AVAILABILITY = "AVAILABILITY";


    public static final String CREATE_TABLE_NEW_ISD_DATA = "CREATE TABLE "
            + TABLE_NEW_ISD
            + " ("
            + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
            + KEY_STORE_CD + " INTEGER,"
            + KEY_ISD_CD + " INTEGER,"
            + KEY_ISD_NAME + " VARCHAR )";

    public static final String CREATE_TABLE_CHECKLIST_INSERTED_DATA = "CREATE TABLE "
            + TABLE_CHECKLIST_INSERTED_DATA
            + " ("
            + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
            + KEY_STORE_CD + " INTEGER,"
            + KEY_ISD_CD + " INTEGER,"
            + KEY_CHECKLIST_CD + " INTEGER,"
            + KEY_CHECKLIST + " VARCHAR,"
            + KEY_MID + " INTEGER,"
            + KEY_AVAILABILITY + " INTEGER)";

    public static final String CREATE_TABLE_ADD_NEW_EMPLOYEE_DATA = "CREATE TABLE "
            + TABLE_ADD_NEW_EMPLOYEE
            + " ("
            + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
            + KEY_STORE_CD + " INTEGER,"
            + KEY_NAME + " VARCHAR,"
            + KEY_EMAIL + " VARCHAR,"
            + KEY_IS_ISD + " VARCHAR,"
            + KEY_PHONE_NO + " VARCHAR)";


    public static final String CREATE_TABLE_STORE_DATA = "CREATE TABLE "
            + TABLE_STORE_DATA
            + " ("
            + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
            + KEY_CITY + " VARCHAR,"
            + KEY_EMAIL + " VARCHAR,"
            + KEY_LOCALITY + " VARCHAR,"
            + KEY_OWNER_NAME + " VARCHAR,"
            + KEY_SHOP_NO + " VARCHAR,"
            + KEY_MARKET_NAME + " VARCHAR,"
            + KEY_STORE_IMAGE + " VARCHAR,"
            + KEY_STORE_NAME + " VARCHAR,"
            + KEY_STORE_TYPE + " VARCHAR,"
            + KEY_STORE_TYPE_CD + " INTEGER,"
            + KEY_TELEPHONE_NO_ONE + " VARCHAR,"
            + KEY_TELEPHONE_NO_TWO + " VARCHAR,"
            + KEY_LATITUDE + " REAL,"
            + KEY_LONGITUDE + " REAL,"
            + KEY_VISIT_DATE + " VARCHAR,"
            + KEY_UPLOAD_STATUS + " VARCHAR,"
            + KEY_OWNER_CONTACT_NO + " VARCHAR)";


    //Table POSM DATA

    public static final String TABLE_ADDSTORE_POSM_DATA = "ADDSTORE_POSM_DATA";

    public static final String KEY_COMMON_ID = "COMMON_ID";
    public static final String KEY_POSM = "POSM";
    public static final String KEY_BRAND = "BRAND";
    public static final String KEY_BRAND_CD = "BRAND_CD";
    public static final String KEY_POSM_CD = "POSM_CD";
    public static final String KEY_QUANTITY = "QUANTITY";
    public static final String KEY_POSM_IMAGE = "POSM_IMAGE";

    public static final String TABLE_POSM_DATA = "POSM_DATA";
    public static final String CREATE_TABLE_POSM_DATA = "CREATE TABLE "
            + TABLE_POSM_DATA
            + " ("
            + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
            + KEY_STORE_CD + " INTEGER,"
            + KEY_COVERAGE_ID + " INTEGER,"
            + KEY_BRAND_CD + " INTEGER,"
            + KEY_BRAND + " VARCHAR,"
            + KEY_POSM_CD + " INTEGER,"
            + KEY_POSM + " VARCHAR,"
            + KEY_QUANTITY + " VARCHAR,"
            + KEY_IMAGE + " VARCHAR)";


    public static final String CREATE_TABLE_ADDSTORE_POSM_DATA = "CREATE TABLE "
            + TABLE_ADDSTORE_POSM_DATA
            + " ("
            + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
            + KEY_STORE_CD + " INTEGER,"
            + KEY_BRAND_CD + " INTEGER,"
            + KEY_BRAND + " VARCHAR,"
            + KEY_POSM_CD + " INTEGER,"
            + KEY_POSM + " VARCHAR,"
            + KEY_QUANTITY + " VARCHAR,"
            + KEY_IMAGE + " VARCHAR)";



    public static final String CALL_HELPDESK = "tel:01149894989";


    //Gagan Goel

    public static final String TABLE_INSERT_ORDER_ENTRYHEADER_DATA = "OrderEnrty_Header_Data";

    public static final String CREATE_TABLE_INSERT_ORDERENTRYHEADER_DATA = "CREATE TABLE IF NOT EXISTS "
            + TABLE_INSERT_ORDER_ENTRYHEADER_DATA
            + " ("
            + "KEY_ID"
            + " INTEGER PRIMARY KEY AUTOINCREMENT ,"

            + "STORE_CD"
            + " VARCHAR,"

/*            + "STATE_CD"
            + " VARCHAR,"

            + "STORE_TYPE_CD"
            + " VARCHAR,"*/

            + "BRAND_CD"
            + " VARCHAR,"

            + "BRAND"
            + " VARCHAR)";

    public static final String TABLE_ORDERENTRY_STOCK_DATA = "Order_ENTRY_Stock_Data";

    public static final String CREATE_TABLE_ORDER_STOCKENTRY_DATA = "CREATE TABLE IF NOT EXISTS "
            + TABLE_ORDERENTRY_STOCK_DATA +
            " (" +
            "Common_Id" +
            " INTEGER," +

            "SKU_CD" +
            " VARCHAR," +

            "SKU" +
            " VARCHAR," +

            "BRAND_CD" +
            " VARCHAR," +

            "BRAND" +
            " VARCHAR," +

            "ORDER_VALUE" +
            " INTEGER," +

            "ORDER_DELIVERY" +
            " INTEGER," +

            "STORE_CD" +
            " INTEGER)";

    public static final String TABLE_SECONDARY_WINDOW_CHILD_DATA = "SECONDARY_WINDOW_CHILD_DATA";
    public static final String TABLE_ADDSTORE_SECONDARY_WINDOW_CHILD_DATA = "ADDSTORE_SECONDARY_WINDOW_CHILD_DATA";


    public static final String CREATE_TABLE_SECONDARY_WINDOW_CHILD_DATA = "CREATE TABLE IF NOT EXISTS "
            + TABLE_SECONDARY_WINDOW_CHILD_DATA +
            " ("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
            + KEY_COVERAGE_ID + " INTEGER,"
            + KEY_COMMON_ID + " INTEGER,"
            + KEY_STORE_CD + " INTEGER,"
            + KEY_WINDOW_CD + " INTEGER,"
            + KEY_CHECKLIST_CD + " INTEGER,"
            + KEY_CHECKLIST + " VARCHAR,"
            + KEY_ANSWER_CD + " INTEGER,"
            + KEY_ANSWER + " VARCHAR" +
            ")";



    public static final String CREATE_TABLE_ADDSTORE_SECONDARY_WINDOW_CHILD_DATA = "CREATE TABLE IF NOT EXISTS "
            + TABLE_ADDSTORE_SECONDARY_WINDOW_CHILD_DATA +
            " ("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
            + KEY_COVERAGE_ID + " INTEGER,"
            + KEY_COMMON_ID + " INTEGER,"
            + KEY_STORE_CD + " INTEGER,"
            + KEY_WINDOW_CD + " INTEGER,"
            + KEY_CHECKLIST_CD + " INTEGER,"
            + KEY_CHECKLIST + " VARCHAR,"
            + KEY_ANSWER_CD + " INTEGER,"
            + KEY_ANSWER + " VARCHAR" +
            ")";

    public static final String TABLE_SECONDARY_WINDOW_HEADER_DATA = "SECONDARY_WINDOW_HEADER_DATA";
    public static final String TABLE_ADDSTORE_SECONDARY_WINDOW_HEADER_DATA = "ADDSTORE_SECONDARY_WINDOW_HEADER_DATA";

    public static final String CREATE_TABLE_SECONDARY_WINDOW_HEADER_DATA = "CREATE TABLE IF NOT EXISTS "
            + TABLE_SECONDARY_WINDOW_HEADER_DATA +
            " ("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
            + KEY_STORE_CD + " INTEGER,"
            + KEY_COVERAGE_ID + " INTEGER,"
            + KEY_WINDOW_CD + " INTEGER,"
            + KEY_WINDOW + " VARCHAR,"
            + KEY_IS_EXISTS + " BOOLEAN,"
            + KEY_WINDOW_IMAGE + " VARCHAR,"
            + KEY_PAID_OR_NONPAID + " VARCHAR"
            +")";


    public static final String CREATE_TABLE_ADDSTORE_SECONDARY_WINDOW_HEADER_DATA = "CREATE TABLE IF NOT EXISTS "
            + TABLE_ADDSTORE_SECONDARY_WINDOW_HEADER_DATA +
            " ("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
            + KEY_STORE_CD + " INTEGER,"
            + KEY_COVERAGE_ID + " INTEGER,"
            + KEY_WINDOW_CD + " INTEGER,"
            + KEY_WINDOW + " VARCHAR,"
            + KEY_IS_EXISTS + " BOOLEAN,"
            + KEY_WINDOW_IMAGE + " VARCHAR,"
            + KEY_PAID_OR_NONPAID + " VARCHAR"
            +")";



    public static final String TABLE_ORDER_UPLOAD_DISTRIBUTOR_SYSTEM = "OrderUpload_DistributorSystem";

    public static final String CREATE_TABLE_ORDER_UPLOAD_DISTRIBUTOR_SYSTEM = "CREATE TABLE IF NOT EXISTS "
            + TABLE_ORDER_UPLOAD_DISTRIBUTOR_SYSTEM +
            " (" +
            "MID" +
            " INTEGER," +

            "STORE_CD" +
            " INTEGER," +

            "VISIT_DATE" +
            " VARCHAR," +

            "ORDER_STATUS" +
            " VARCHAR," +

            "NON_ORDER_REASON" +
            " VARCHAR" +
            ")";

    public static final String TABLE_ORDER_DELIVERY = "OrderDelivery";

    public static final String CREATE_TABLE_ORDER_DELIVERY = "CREATE TABLE IF NOT EXISTS "
            + TABLE_ORDER_DELIVERY +
            " (" +
            "MID" +
            " INTEGER," +

            "STORE_CD" +
            " INTEGER," +

            "SKU_CD" +
            " INTEGER," +

            "ORDER_DELIVERY_QTY" +
            " INTEGER" +
            ")";

    public static final String TABLE_ADD_NEW_STORE = "Add_NewStore";
    public static final String TABLE_ADD_LINE_ENTRY = "LINE_ENTRY";
    public static final String TABLE_ADDSTORE_LINE_ENTRY = "ADDSTORE_LINE_ENTRY";
    public static final String TABLE_ATTENDANCE = "ATTENDANCE";
    public static final String KEY_ATTENDANCE_STATUS = "ATTENDANCE_STATUS";

    public static final String CREATE_TABLE_ADD_NEW_STORE = "CREATE TABLE IF NOT EXISTS "
            + TABLE_ADD_NEW_STORE +
            " (" +
            "KEY_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "STORE_CD INTEGER," +
            "COVERAGE_ID INTEGER," +
            "VISIT_DATE VARCHAR," +
            "STORE_NAME VARCHAR," +
            "RETAILER_NAME VARCHAR," +
            "USR_CD INTEGER," +
            "USR VARCHAR," +
            "ADDRESS VARCHAR," +
            "LANDMARK VARCHAR," +
            "PHONE INTEGER," +
            "CITY_CD INTEGER," +
            "CITY VARCHAR," +
            "TOWN_CD INTEGER," +
            "TOWN VARCHAR," +
            "STATE_CD INTEGER," +
            "STATE VARCHAR," +
            "DOB VARCHAR," +
            "DOA VARCHAR," +
            "ANSWER_CD INTEGER," +
            "ANSWER VARCHAR," +
            "SUBDISTRIBUTER_NAME VARCHAR," +
            "CHANNEL_CD INTEGER," +
            "CHANNEL VARCHAR," +
            "UPLOAD_STATUS VARCHAR," +
            "IMAGE_PATH VARCHAR" +
            ")";

    public static final String CREATE_TABLE_LINE_ENTRY = "CREATE TABLE IF NOT EXISTS "
            + TABLE_ADD_LINE_ENTRY +
            " (" +
            "ID INTEGER PRIMARY KEY AUTOINCREMENT ," +
            "STORE_CD INTEGER," +
            "COVERAGE_ID INTEGER," +
            "TOTAL_LINE_AVAILABLE INTEGER," +
            "NO_OF_SKULINE INTEGER," +
            "TOTAL_BILL_AMOUNT INTEGER" +
            ")";

    public static final String CREATE_TABLE_ADDSTORE_LINE_ENTRY = "CREATE TABLE IF NOT EXISTS "
            + TABLE_ADDSTORE_LINE_ENTRY +
            " (" +
            "ID INTEGER PRIMARY KEY AUTOINCREMENT ," +
            "STORE_CD INTEGER," +
            "COVERAGE_ID INTEGER," +
            "TOTAL_LINE_AVAILABLE INTEGER," +
            "NO_OF_SKULINE INTEGER," +
            "TOTAL_BILL_AMOUNT INTEGER" +
            ")";

    public static final String CREATE_TABLE_ATTENDANCE = "CREATE TABLE IF NOT EXISTS "
            + TABLE_ATTENDANCE +
            " (" +
            "ID INTEGER PRIMARY KEY AUTOINCREMENT ," +
            "USERNAME VARCHAR," +
            "VISITDATE VARCHAR," +
            "REASON_CD INTEGER," +
            "ENTRY_ALLOW INTEGER," +
            "ATTENDANCE_STATUS VARCHAR" +
            ")";



}
