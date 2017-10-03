package com.example.deepakp.gskgtsubd.upload;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.deepakp.gskgtsubd.R;
import com.example.deepakp.gskgtsubd.bean.CoverageBean;
import com.example.deepakp.gskgtsubd.constants.CommonString;
import com.example.deepakp.gskgtsubd.dailyentry.MenuActivity;
import com.example.deepakp.gskgtsubd.database.Database;
import com.example.deepakp.gskgtsubd.gettersetter.AddNewStoreGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.FailureGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.LineEntryGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.NonWorkingReasonGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.PosmEntryGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.WindowChecklistGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.WindowMasterGetterSetter;
import com.example.deepakp.gskgtsubd.upload.Retrofit_method.UploadImageWithRetrofit;
import com.example.deepakp.gskgtsubd.utilities.AlertAndMessages;
import com.example.deepakp.gskgtsubd.xmlHandler.FailureXMLHandler;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class UploadDataActivity extends AppCompatActivity {

    private Dialog dialog;
    private ProgressBar pb;
    private TextView percentage, message;
    String app_ver;
    private String visit_date, username;
    private SharedPreferences preferences;
    private Database database;
    private String reasonid, faceup, stock, length;
    private int factor, k;
    String datacheck = "";
    String[] words;
    String validity, storename;
    int mid;
    String sod = "";
    String total_sku = "";
    String sku = "";
    String sos_data = "";
    String category_data = "";
    Data data;

    private ArrayList<CoverageBean> coverageBeanlist = new ArrayList<CoverageBean>();
    private ArrayList<CoverageBean> coverageBeanlist_addstore = new ArrayList<CoverageBean>();
    private ArrayList<LineEntryGetterSetter> lineEntry = new ArrayList<>();
    private ArrayList<AddNewStoreGetterSetter> addNewStoreList = new ArrayList<>();
    private ArrayList<PosmEntryGetterSetter> PosmEntryData = new ArrayList<>();
    private ArrayList<WindowMasterGetterSetter> windowData = new ArrayList<>();
    private ArrayList<WindowChecklistGetterSetter> window_Child_Data = new ArrayList<>();
    private ArrayList<NonWorkingReasonGetterSetter> attendanceList = new ArrayList<>();

    private FailureGetterSetter failureGetterSetter = null;

    static int counter = 1;

    boolean upload_status;
    String result;
    String Path;
    boolean image_valid;

    String errormsg = "", status;
    boolean up_success_flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_data);


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        visit_date = preferences.getString(CommonString.KEY_DATE, null);
        username = preferences.getString(CommonString.KEY_USERNAME, null);
        app_ver = preferences.getString(CommonString.KEY_VERSION, "");
        database = new Database(this);
        database.open();

        Path = CommonString.FILE_PATH;

        new UploadTask(UploadDataActivity.this).execute();

    }

    @Override
    protected void onStop() {
        super.onStop();
        database.close();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, MenuActivity.class);
        startActivity(i);
        UploadDataActivity.this.finish();
    }

    private class UploadTask extends AsyncTask<Void, Data, String> {
        private Context context;

        UploadTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            dialog = new Dialog(context);
            dialog.setContentView(R.layout.custom_upload);
            dialog.setTitle("Uploading Data");
            dialog.setCancelable(false);
            dialog.show();
            pb = (ProgressBar) dialog.findViewById(R.id.progressBar1);
            percentage = (TextView) dialog.findViewById(R.id.percentage);
            message = (TextView) dialog.findViewById(R.id.message);
        }

        @Override
        protected String doInBackground(Void... params) {
            // TODO Auto-generated method stub

            try {
                UploadImageWithRetrofit uploadRetro = new UploadImageWithRetrofit(context);
                data = new Data();

                coverageBeanlist = database.getCoverageData(visit_date);
                coverageBeanlist_addstore = database.getCoverageData("0");

                if (coverageBeanlist.size() > 0) {

                    if (coverageBeanlist.size() == 1) {
                        factor = 50;
                    } else {
                        factor = 100 / (coverageBeanlist.size());
                    }
                }

                for (int i = 0; i < coverageBeanlist.size(); i++) {

                    if (!coverageBeanlist.get(i).getStatus()
                            .equalsIgnoreCase(CommonString.KEY_U)) {

                        String onXML = "", final_xml = "";
                        if (!coverageBeanlist.get(i).getStatus().equalsIgnoreCase(CommonString.KEY_D)) {

                            onXML = "[DATA][USER_DATA][STORE_CD]"
                                    + coverageBeanlist.get(i).getStoreId()
                                    + "[/STORE_CD]" + "[VISIT_DATE]"
                                    + coverageBeanlist.get(i).getVisitDate()
                                    + "[/VISIT_DATE][LATITUDE]"
                                    + coverageBeanlist.get(i).getLatitude()
                                    + "[/LATITUDE][APP_VERSION]"
                                    + app_ver
                                    + "[/APP_VERSION][LONGITUDE]"
                                    + coverageBeanlist.get(i).getLongitude()
                                    + "[/LONGITUDE][IN_TIME]"
                                    + coverageBeanlist.get(i).getInTime()
                                    + "[/IN_TIME][OUT_TIME]"
                                    + coverageBeanlist.get(i).getOutTime()
                                    + "[/OUT_TIME][UPLOAD_STATUS]"
                                    + "N"
                                    + "[/UPLOAD_STATUS][USER_ID]" + username
                                    + "[/USER_ID][IMAGE_URL]" + coverageBeanlist.get(i).getImage()
                                    + "[/IMAGE_URL][REASON_ID]"
                                    + coverageBeanlist.get(i).getReasonid()
                                    + "[/REASON_ID]"
                                    + "[REASON_REMARK]"
                                    + coverageBeanlist.get(i).getRemark()
                                    + "[/REASON_REMARK][/USER_DATA][/DATA]";


                            SoapObject request = new SoapObject(
                                    CommonString.NAMESPACE,
                                    CommonString.METHOD_UPLOAD_DR_STORE_COVERAGE);
                            request.addProperty("onXML", onXML);

                            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                                    SoapEnvelope.VER11);
                            envelope.dotNet = true;
                            envelope.setOutputSoapObject(request);
                            HttpTransportSE androidHttpTransport = new HttpTransportSE(CommonString.URL);

                            androidHttpTransport.call(
                                    CommonString.SOAP_ACTION + CommonString.METHOD_UPLOAD_DR_STORE_COVERAGE,
                                    envelope);
                            Object result = (Object) envelope.getResponse();

                            datacheck = result.toString();
                            datacheck = datacheck.replace("\"", "");
                            words = datacheck.split("\\;");
                            validity = (words[0]);

                            if (validity
                                    .equalsIgnoreCase(CommonString.KEY_SUCCESS)) {
                          /*  database.updateCoverageStatus(coverageBeanlist
                                    .get(i).getMID(), CommonString.KEY_P);
*/
                                database.updateStoreStatus(
                                        coverageBeanlist.get(i).getStoreId(),
                                        coverageBeanlist.get(i).getVisitDate(),
                                        CommonString.KEY_P);
                            } else {


                                if (!result.toString().equalsIgnoreCase(
                                        CommonString.KEY_SUCCESS)) {

                                    return CommonString.METHOD_UPLOAD_DR_STORE_COVERAGE;
                                }
                                if (result.toString().equalsIgnoreCase(
                                        CommonString.KEY_FALSE)) {
                                    return CommonString.METHOD_UPLOAD_DR_STORE_COVERAGE;
                                }
                                if (result.toString().equalsIgnoreCase(
                                        CommonString.KEY_FAILURE)) {
                                    return CommonString.METHOD_UPLOAD_DR_STORE_COVERAGE;
                                }

                            }

                            mid = Integer.parseInt((words[1]));

                            data.value = 80;
                            data.name = "Uploading";

                            publishProgress(data);


///////window1 DATA
                            final_xml = "";
                            onXML = "";
                            lineEntry = database.getLineEntryData(coverageBeanlist.get(i).getStoreId(), String.valueOf(coverageBeanlist.get(i).getMID()));

                            if (lineEntry.size() > 0) {

                                for (int j = 0; j < lineEntry.size(); j++) {

                                    onXML = "[LINE_ENTRY_DATA][MID]"
                                            + mid
                                            + "[/MID]"
                                            + "[CREATED_BY]"
                                            + username
                                            + "[/CREATED_BY]"
                                            + "[TOTAL_LINE_AVAILABLE]"
                                            + lineEntry.get(j).getTotalLineAvailable()
                                            + "[/TOTAL_LINE_AVAILABLE]"
                                            + "[NO_OF_SKULINE]"
                                            + lineEntry.get(j).getNoOfBuildSkuLine()
                                            + "[/NO_OF_SKULINE]"
                                            + "[TOTAL_BILL_AMOUNT]"
                                            + lineEntry.get(j).getTotalBillAmount()
                                            + "[/TOTAL_BILL_AMOUNT]"
                                            + "[/LINE_ENTRY_DATA]";


                                    final_xml = final_xml + onXML;

                                }

                                final String sos_xml = "[DATA]" + final_xml
                                        + "[/DATA]";

                                request = new SoapObject(
                                        CommonString.NAMESPACE,
                                        CommonString.METHOD_UPLOAD_XML);
                                request.addProperty("XMLDATA", sos_xml);
                                request.addProperty("KEYS", "LINE_ENTRY_DATA");
                                request.addProperty("USERNAME", username);
                                request.addProperty("MID", mid);

                                envelope = new SoapSerializationEnvelope(
                                        SoapEnvelope.VER11);
                                envelope.dotNet = true;
                                envelope.setOutputSoapObject(request);

                                androidHttpTransport = new HttpTransportSE(
                                        CommonString.URL);

                                androidHttpTransport.call(
                                        CommonString.SOAP_ACTION + CommonString.METHOD_UPLOAD_XML,
                                        envelope);
                                result = (Object) envelope.getResponse();


                                if (!result.toString().equalsIgnoreCase(
                                        CommonString.KEY_SUCCESS)) {

                                    return "LINE_ENTRY_DATA";
                                }


                                if (result.toString().equalsIgnoreCase(
                                        CommonString.KEY_NO_DATA)) {
                                    return CommonString.METHOD_UPLOAD_XML;
                                }

                                if (result.toString().equalsIgnoreCase(
                                        CommonString.KEY_FAILURE)) {
                                    return CommonString.METHOD_UPLOAD_XML;
                                }

                            }


                            data.value = 80;
                            data.name = "LINE_ENTRY_DATA";

                            publishProgress(data);


///////PosmEntry DATA
                            final_xml = "";
                            onXML = "";
                            PosmEntryData = database.getPosmEntryData(coverageBeanlist.get(i).getStoreId(), String.valueOf(coverageBeanlist.get(i).getMID()));

                            if (PosmEntryData.size() > 0) {

                                for (int j = 0; j < PosmEntryData.size(); j++) {

                                    onXML = "[POSM_ENTRY_DATA][MID]"
                                            + mid
                                            + "[/MID]"
                                            + "[CREATED_BY]"
                                            + username
                                            + "[/CREATED_BY]"
                                            + "[BRAND_CD]"
                                            + PosmEntryData.get(j).getBrand_cd()
                                            + "[/BRAND_CD]"
                                            + "[POSM_CD]"
                                            + PosmEntryData.get(j).getPosm_cd()
                                            + "[/POSM_CD]"
                                            + "[QUANTITY]"
                                            + PosmEntryData.get(j).getQuantity()
                                            + "[/QUANTITY]"
                                            + "[IMAGE]"
                                            + PosmEntryData.get(j).getImage()
                                            + "[/IMAGE]"
                                            + "[/POSM_ENTRY_DATA]";

                                    final_xml = final_xml + onXML;

                                }

                                final String sos_xml = "[DATA]" + final_xml
                                        + "[/DATA]";

                                request = new SoapObject(
                                        CommonString.NAMESPACE,
                                        CommonString.METHOD_UPLOAD_XML);
                                request.addProperty("XMLDATA", sos_xml);
                                request.addProperty("KEYS", "POSM_ENTRY_DATA");
                                request.addProperty("USERNAME", username);
                                request.addProperty("MID", mid);

                                envelope = new SoapSerializationEnvelope(
                                        SoapEnvelope.VER11);
                                envelope.dotNet = true;
                                envelope.setOutputSoapObject(request);

                                androidHttpTransport = new HttpTransportSE(
                                        CommonString.URL);

                                androidHttpTransport.call(
                                        CommonString.SOAP_ACTION + CommonString.METHOD_UPLOAD_XML,
                                        envelope);
                                result = (Object) envelope.getResponse();

                                if (!result.toString().equalsIgnoreCase(
                                        CommonString.KEY_SUCCESS)) {

                                    return "POSM_ENTRY_DATA";
                                }

                                if (result.toString().equalsIgnoreCase(
                                        CommonString.KEY_NO_DATA)) {
                                    return CommonString.METHOD_UPLOAD_XML;
                                }

                                if (result.toString().equalsIgnoreCase(
                                        CommonString.KEY_FAILURE)) {
                                    return CommonString.METHOD_UPLOAD_XML;
                                }
                            }

                            data.value = 80;
                            data.name = "POSM_ENTRY_DATA";

                            publishProgress(data);

// window data

                            final_xml = "";
                            onXML = "";
                            windowData = database.getSavedWindowData(coverageBeanlist.get(i).getStoreId(), String.valueOf(coverageBeanlist.get(i).getMID()));

                            if (windowData.size() > 0) {

                                for (int j = 0; j < windowData.size(); j++) {

                                    String isExist = "";
                                    if (windowData.get(j).isWINDOW_EXIST()) {
                                        isExist = "1";
                                    } else {
                                        isExist = "0";
                                    }

                                    onXML = "[WINDOW_DATA][MID]"
                                            + mid
                                            + "[/MID]"
                                            + "[CREATED_BY]"
                                            + username
                                            + "[/CREATED_BY]"
                                            + "[WINDOW_CD]"
                                            + windowData.get(j).getWINDOW_CD().get(0)
                                            + "[/WINDOW_CD]"
                                            + "[WINDOW_EXISTS]"
                                            + isExist
                                            + "[/WINDOW_EXISTS]"
                                            + "[WINDOW_IMAGE]"
                                            + windowData.get(j).getWINDOW_IMAGE()
                                            + "[/WINDOW_IMAGE]"
                                            + "[/WINDOW_DATA]";

                                    final_xml = final_xml + onXML;

                                }

                                final String sos_xml = "[DATA]" + final_xml
                                        + "[/DATA]";

                                request = new SoapObject(
                                        CommonString.NAMESPACE,
                                        CommonString.METHOD_UPLOAD_XML);
                                request.addProperty("XMLDATA", sos_xml);
                                request.addProperty("KEYS", "WINDOW_DATA");
                                request.addProperty("USERNAME", username);
                                request.addProperty("MID", mid);

                                envelope = new SoapSerializationEnvelope(
                                        SoapEnvelope.VER11);
                                envelope.dotNet = true;
                                envelope.setOutputSoapObject(request);

                                androidHttpTransport = new HttpTransportSE(
                                        CommonString.URL);

                                androidHttpTransport.call(
                                        CommonString.SOAP_ACTION + CommonString.METHOD_UPLOAD_XML,
                                        envelope);
                                result = (Object) envelope.getResponse();


                                if (!result.toString().equalsIgnoreCase(
                                        CommonString.KEY_SUCCESS)) {

                                    return "WINDOW_DATA";
                                }


                                if (result.toString().equalsIgnoreCase(
                                        CommonString.KEY_NO_DATA)) {
                                    return CommonString.METHOD_UPLOAD_XML;
                                }

                                if (result.toString().equalsIgnoreCase(
                                        CommonString.KEY_FAILURE)) {
                                    return CommonString.METHOD_UPLOAD_XML;
                                }

                            }
                            data.value = 80;
                            data.name = "WINDOW_DATA";

                            publishProgress(data);

                            final_xml = "";
                            onXML = "";
                            //SET CHILD DATA
                            if (windowData.size() > 0) {
                                for (int j = 0; j < windowData.size(); j++) {
                                    window_Child_Data = database.getAnswerListForSavedWindowData(windowData.get(j).getKey_id());

                                    if (window_Child_Data.size() > 0) {
                                        for (int k = 0; k < window_Child_Data.size(); k++) {

                                            onXML = "[WINDOW_CHILD_DATA][MID]"
                                                    + mid
                                                    + "[/MID]"
                                                    + "[CREATED_BY]"
                                                    + username
                                                    + "[/CREATED_BY]"
                                                    + "[COMMON_ID]"
                                                    + windowData.get(j).getKey_id()
                                                    + "[/COMMON_ID]"
                                                    + "[WINDOW_CD]"
                                                    + window_Child_Data.get(k).getWINDOW_CD().get(0)
                                                    + "[/WINDOW_CD]"
                                                    + "[CHECKLIST_CD]"
                                                    + window_Child_Data.get(k).getCHECKLIST_CD().get(0)
                                                    + "[/CHECKLIST_CD]"
                                                    + "[ANSWER_CD]"
                                                    + window_Child_Data.get(k).getANSWER_CD()
                                                    + "[/ANSWER_CD]"
                                                    + "[/WINDOW_CHILD_DATA]";

                                            final_xml = final_xml + onXML;
                                        }


                                        final String sos_xml = "[DATA]" + final_xml
                                                + "[/DATA]";

                                        request = new SoapObject(
                                                CommonString.NAMESPACE,
                                                CommonString.METHOD_UPLOAD_XML);
                                        request.addProperty("XMLDATA", sos_xml);
                                        request.addProperty("KEYS", "WINDOW_CHILD_DATA");
                                        request.addProperty("USERNAME", username);
                                        request.addProperty("MID", mid);

                                        envelope = new SoapSerializationEnvelope(
                                                SoapEnvelope.VER11);
                                        envelope.dotNet = true;
                                        envelope.setOutputSoapObject(request);

                                        androidHttpTransport = new HttpTransportSE(
                                                CommonString.URL);

                                        androidHttpTransport.call(
                                                CommonString.SOAP_ACTION + CommonString.METHOD_UPLOAD_XML,
                                                envelope);

                                        result = (Object) envelope.getResponse();


                                        if (!result.toString().equalsIgnoreCase(
                                                CommonString.KEY_SUCCESS)) {

                                            return "WINDOW_CHILD_DATA";
                                        }


                                        if (result.toString().equalsIgnoreCase(
                                                CommonString.KEY_NO_DATA)) {
                                            return CommonString.METHOD_UPLOAD_XML;
                                        }

                                        if (result.toString().equalsIgnoreCase(
                                                CommonString.KEY_FAILURE)) {
                                            return CommonString.METHOD_UPLOAD_XML;
                                        }

                                    }
                                    publishProgress(data);

                                }
                            }
                        }

//////////////////////////////////////

                        if (coverageBeanlist.get(i).getImage() != null && !coverageBeanlist.get(i).getImage().equals("")) {

                            if (new File(CommonString.FILE_PATH + coverageBeanlist.get(i).getImage()).exists()) {
                                //  result = UploadImage(coverageBeanlist.get(i).getImage(), "StoreImages");
                                result = uploadRetro.UploadImage2(coverageBeanlist.get(i).getImage(), "StoreImages", CommonString.FILE_PATH);

                                if (!result.toString().equalsIgnoreCase(CommonString.KEY_SUCCESS)) {
                                    if (result.toString().equalsIgnoreCase(CommonString.KEY_FALSE)) {
                                        return "StoreImages";
                                    } else if (result.toString().equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                                        return "StoreImages" + "," + errormsg;
                                    } else if (result.toString().equalsIgnoreCase(CommonString.MESSAGE_SOCKETEXCEPTION)) {
                                        throw new IOException();
                                    }
                                }

                                runOnUiThread(new Runnable() {
                                    public void run() {

                                        message.setText("Store Image Uploaded");
                                    }
                                });

                            }
                        }

                        data.value = 85;
                        data.name = "StoreImages";

                        publishProgress(data);

                        if (PosmEntryData.size() > 0) {
                            for (int j = 0; j < PosmEntryData.size(); j++) {

                                if (PosmEntryData.get(j).getImage() != null && !PosmEntryData.get(j).getImage().equals("")) {

                                    if (new File(CommonString.FILE_PATH + PosmEntryData.get(j).getImage()).exists()) {

                                        // result = UploadImage(PosmEntryData.get(j).getImage(), "POSMImages");
                                        result = uploadRetro.UploadImage2(PosmEntryData.get(j).getImage(), "POSMImages", CommonString.FILE_PATH);
                                        if (!result.toString().equalsIgnoreCase(CommonString.KEY_SUCCESS)) {
                                            if (result.toString().equalsIgnoreCase(CommonString.KEY_FALSE)) {
                                                return "POSMImages";
                                            } else if (result.toString().equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                                                return "POSMImages" + "," + errormsg;
                                            } else if (result.toString().equalsIgnoreCase(CommonString.MESSAGE_SOCKETEXCEPTION)) {
                                                throw new IOException();
                                            }
                                        }


                                        runOnUiThread(new Runnable() {

                                            public void run() {
                                                message.setText("POSMImages Uploaded");
                                            }
                                        });

                                    }
                                }
                            }
                        }

                        data.value = 70;
                        data.name = "POSMImages";

                        publishProgress(data);


                        //////////////////////////

                        if (windowData.size() > 0) {

                            for (int j = 0; j < windowData.size(); j++) {

                                if (windowData.get(j).getWINDOW_IMAGE() != null && !windowData.get(j).getWINDOW_IMAGE().equals("")) {

                                    if (new File(CommonString.FILE_PATH + windowData.get(j).getWINDOW_IMAGE()).exists()) {

                                        // result = UploadImage(windowData.get(j).getWINDOW_IMAGE(), "WindowImages");
                                        result = uploadRetro.UploadImage2(windowData.get(j).getWINDOW_IMAGE(), "WindowImages", CommonString.FILE_PATH);

                                        if (!result.toString().equalsIgnoreCase(CommonString.KEY_SUCCESS)) {
                                            if (result.toString().equalsIgnoreCase(CommonString.KEY_FALSE)) {
                                                return "WindowImages";
                                            } else if (result.toString().equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                                                return "WindowImages" + "," + errormsg;
                                            } else if (result.toString().equalsIgnoreCase(CommonString.MESSAGE_SOCKETEXCEPTION)) {
                                                throw new IOException();
                                            }
                                        }

                                        runOnUiThread(new Runnable() {

                                            public void run() {

                                                message.setText("WindowImages Uploaded");
                                            }
                                        });

                                    }
                                }
                            }
                        }

                        data.value = 80;
                        data.name = "WindowImages";

                        publishProgress(data);


                        // SET COVERAGE STATUS

                        final_xml = "";
                        onXML = "";
                        onXML = "[COVERAGE_STATUS][STORE_ID]"
                                + coverageBeanlist.get(i).getStoreId()
                                + "[/STORE_ID]"
                                + "[VISIT_DATE]"
                                + coverageBeanlist.get(i).getVisitDate()
                                + "[/VISIT_DATE]"
                                + "[USER_ID]"
                                + coverageBeanlist.get(i).getUserId()
                                + "[/USER_ID]"
                                + "[STATUS]"
                                + CommonString.KEY_U
                                + "[/STATUS]"
                                + "[/COVERAGE_STATUS]";

                        final_xml = final_xml + onXML;

                        final String sos_xml = "[DATA]" + final_xml
                                + "[/DATA]";

                        SoapObject request1 = new SoapObject(
                                CommonString.NAMESPACE,
                                CommonString.MEHTOD_UPLOAD_COVERAGE_STATUS);
                        request1.addProperty("onXML", sos_xml);


                        SoapSerializationEnvelope envelope1 = new SoapSerializationEnvelope(
                                SoapEnvelope.VER11);
                        envelope1.dotNet = true;
                        envelope1.setOutputSoapObject(request1);

                        HttpTransportSE androidHttpTransport1 = new HttpTransportSE(
                                CommonString.URL);

                        androidHttpTransport1.call(
                                CommonString.SOAP_ACTION + CommonString.MEHTOD_UPLOAD_COVERAGE_STATUS,
                                envelope1);
                        Object result1 = (Object) envelope1.getResponse();

//                        if (result1.toString().equalsIgnoreCase(
//                                CommonString.KEY_SUCCESS)) {

                        database.open();
                        database.updateCoverageStatus(coverageBeanlist.get(i)
                                .getMID(), CommonString.KEY_U);
                        database.updateStoreStatus(coverageBeanlist.get(i)
                                .getStoreId(), coverageBeanlist.get(i)
                                .getVisitDate(), CommonString.KEY_U);
                        //  }


                        data.value = 80;
                        data.name = "COVERAGE_STATUS";

                        publishProgress(data);

                    }

                }


///////////////////////////////////////////////------------for leap frog----------------------------------------------------------------------
                for (int i = 0; i < coverageBeanlist_addstore.size(); i++) {
                    String Final_large_xml = "";


                    if (!coverageBeanlist_addstore.get(i).getStatus()
                            .equalsIgnoreCase(CommonString.KEY_U)) {

                        if (!coverageBeanlist_addstore.get(i).getStatus().equalsIgnoreCase(CommonString.KEY_D)) {
                            String onXML; /*= "[USER_DATA][STORE_CD]"
                                + coverageBeanlist_addstore.get(i).getStoreId()
                                + "[/STORE_CD]" + "[VISIT_DATE]"
                                + coverageBeanlist_addstore.get(i).getVisitDate()
                                + "[/VISIT_DATE][LATITUDE]"
                                + coverageBeanlist_addstore.get(i).getLatitude()
                                + "[/LATITUDE][APP_VERSION]"
                                + app_ver
                                + "[/APP_VERSION][LONGITUDE]"
                                + coverageBeanlist_addstore.get(i).getLongitude()
                                + "[/LONGITUDE][IN_TIME]"
                                + coverageBeanlist_addstore.get(i).getInTime()
                                + "[/IN_TIME][OUT_TIME]"
                                + coverageBeanlist_addstore.get(i).getOutTime()
                                + "[/OUT_TIME][UPLOAD_STATUS]"
                                + "N"
                                + "[/UPLOAD_STATUS][USER_ID]" + username
                                + "[/USER_ID][IMAGE_URL]" + coverageBeanlist_addstore.get(i).getImage()
                                + "[/IMAGE_URL][REASON_ID]"
                                + coverageBeanlist_addstore.get(i).getReasonid()
                                + "[/REASON_ID]"
                                + "[REASON_REMARK]"
                                + coverageBeanlist_addstore.get(i).getRemark()
                                + "[/REASON_REMARK][/USER_DATA]";

                        Final_large_xml = onXML;*/


                            //  mid = Integer.parseInt((words[1]));
                            mid = 0;
                            data.value = 10;
                            data.name = "Uploading";

                            publishProgress(data);

                            ///////ADD NEW STORE DATA
                            String final_xml = "";
                            onXML = "";
                            addNewStoreList = database.getAddNewStoreListForUpload(String.valueOf(coverageBeanlist_addstore.get(i).getMID()));

                            if (addNewStoreList.size() > 0) {
                                for (int j = 0; j < addNewStoreList.size(); j++) {


                                    String DOB[] = addNewStoreList.get(j).getDob().split("-");
                                    String DOA[] = addNewStoreList.get(j).getDoa().split("-");

                                    onXML = "[ADDNEW_STORE_DATA][MID]"
                                            + mid
                                            + "[/MID]"
                                            + "[CREATED_BY]"
                                            + username
                                            + "[/CREATED_BY]"
                                            + "[COVERAGE_ID]"
                                            + coverageBeanlist_addstore.get(i).getMID()
                                            + "[/COVERAGE_ID]"
                                            + "[STORE_CD]"
                                            + addNewStoreList.get(j).getStore_id()
                                            + "[/STORE_CD]" +

                                            "[VISIT_DATE]"
                                            + coverageBeanlist_addstore.get(i).getVisitDate()
                                            + "[/VISIT_DATE][LATITUDE]"
                                            + coverageBeanlist_addstore.get(i).getLatitude()
                                            + "[/LATITUDE][APP_VERSION]"
                                            + app_ver
                                            + "[/APP_VERSION][LONGITUDE]"
                                            + coverageBeanlist_addstore.get(i).getLongitude()
                                            + "[/LONGITUDE][IN_TIME]"
                                            + coverageBeanlist_addstore.get(i).getInTime()
                                            + "[/IN_TIME][OUT_TIME]"
                                            + coverageBeanlist_addstore.get(i).getOutTime()
                                            + "[/OUT_TIME][UPLOAD_STATUS]"
                                            + "N"
                                            + "[/UPLOAD_STATUS][USER_ID]" + username
                                            + "[/USER_ID][IMAGE_URL]" + coverageBeanlist_addstore.get(i).getImage()
                                            + "[/IMAGE_URL][STORE_NAME]"
                                            + addNewStoreList.get(j).getEd_StoreName()
                                            + "[/STORE_NAME]"

                                            + "[ADDRESS]"
                                            + addNewStoreList.get(j).getEd_Address()
                                            + "[/ADDRESS]"

                                            + "[PHONE]"
                                            + addNewStoreList.get(j).getEd_Phone()
                                            + "[/PHONE]"

                                            + "[CITY_CD]"
                                            + addNewStoreList.get(j).getCity_CD()
                                            + "[/CITY_CD]"

                                            + "[USR_CD]"
                                            + addNewStoreList.get(j).getUsr_cd()
                                            + "[/USR_CD]"

                                            + "[TOWN_CD]"
                                            + addNewStoreList.get(j).getTown_CD()
                                            + "[/TOWN_CD]"

                                            + "[STATE_CD]"
                                            + addNewStoreList.get(j).getState_cd()
                                            + "[/STATE_CD]"

                                            + "[RETAILER_NAME]"
                                            + addNewStoreList.get(j).getEd_RetailerName()
                                            + "[/RETAILER_NAME]"

                                            + "[LANDMARK]"
                                            + addNewStoreList.get(j).getEd_Landmark()
                                            + "[/LANDMARK]"

                                      /*  + "[DOB]"
                                        + addNewStoreList.get(j).getDob()
                                        + "[/DOB]"

                                        + "[DOA]"
                                        + addNewStoreList.get(j).getDoa()
                                        + "[/DOA]"
*/

                                            + "[DOB_DATE]"
                                            + DOB[0]
                                            + "[/DOB_DATE]"

                                            + "[DOB_MONTH]"
                                            + DOB[1]
                                            + "[/DOB_MONTH]"

                                            + "[DOA_DATE]"
                                            + DOA[0]
                                            + "[/DOA_DATE]"

                                            + "[DOA_MONTH]"
                                            + DOA[1]
                                            + "[/DOA_MONTH]"


                                            + "[RETAILER_HAS_SMARTPHONE]"
                                            + addNewStoreList.get(j).getHasSmartphone()
                                            + "[/RETAILER_HAS_SMARTPHONE]"

                                            + "[CHANNEL_CD]"
                                            + addNewStoreList.get(j).getStoreType_CD()
                                            + "[/CHANNEL_CD]"

                                            + "[IMAGE]"
                                            + addNewStoreList.get(j).getImg_Camera()
                                            + "[/IMAGE]"

                                            + "[/ADDNEW_STORE_DATA]";

                                    final_xml = final_xml + onXML;

                                }

                                Final_large_xml = Final_large_xml + final_xml;

                            }


///////window1 DATA
                            final_xml = "";
                            onXML = "";
                            lineEntry = database.getLineEntryData(coverageBeanlist_addstore.get(i).getStoreId(), String.valueOf(coverageBeanlist_addstore.get(i).getMID()));

                            if (lineEntry.size() > 0) {

                                for (int j = 0; j < lineEntry.size(); j++) {

                                    onXML = "[LINE_ENTRY_DATA][MID]"
                                            + mid
                                            + "[/MID]"
                                            + "[CREATED_BY]"
                                            + username
                                            + "[/CREATED_BY]"
                                            + "[COVERAGE_ID]"
                                            + coverageBeanlist_addstore.get(i).getMID()
                                            + "[/COVERAGE_ID]"
                                            + "[TOTAL_LINE_AVAILABLE]"
                                            + lineEntry.get(j).getTotalLineAvailable()
                                            + "[/TOTAL_LINE_AVAILABLE]"
                                            + "[NO_OF_SKULINE]"
                                            + lineEntry.get(j).getNoOfBuildSkuLine()
                                            + "[/NO_OF_SKULINE]"
                                            + "[TOTAL_BILL_AMOUNT]"
                                            + lineEntry.get(j).getTotalBillAmount()
                                            + "[/TOTAL_BILL_AMOUNT]"
                                            + "[/LINE_ENTRY_DATA]";


                                    final_xml = final_xml + onXML;

                                }

                                Final_large_xml = Final_large_xml + final_xml;

                            }


                            data.value = 80;
                            data.name = "LINE_ENTRY_DATA_LEAP_FROG";

                            publishProgress(data);


///////PosmEntry DATA
                            final_xml = "";
                            onXML = "";
                            PosmEntryData = database.getPosmEntryData(coverageBeanlist_addstore.get(i).getStoreId(), String.valueOf(coverageBeanlist_addstore.get(i).getMID()));

                            if (PosmEntryData.size() > 0) {

                                for (int j = 0; j < PosmEntryData.size(); j++) {

                                    onXML = "[POSM_ENTRY_DATA][MID]"
                                            + mid
                                            + "[/MID]"
                                            + "[CREATED_BY]"
                                            + username
                                            + "[/CREATED_BY]"
                                            + "[COVERAGE_ID]"
                                            + coverageBeanlist_addstore.get(i).getMID()
                                            + "[/COVERAGE_ID]"
                                            + "[BRAND_CD]"
                                            + PosmEntryData.get(j).getBrand_cd()
                                            + "[/BRAND_CD]"
                                            + "[POSM_CD]"
                                            + PosmEntryData.get(j).getPosm_cd()
                                            + "[/POSM_CD]"
                                            + "[QUANTITY]"
                                            + PosmEntryData.get(j).getQuantity()
                                            + "[/QUANTITY]"
                                            + "[IMAGE]"
                                            + PosmEntryData.get(j).getImage()
                                            + "[/IMAGE]"
                                            + "[/POSM_ENTRY_DATA]";

                                    final_xml = final_xml + onXML;

                                }
                                final_xml = "[POSM]" + final_xml + "[/POSM]";

                                Final_large_xml = Final_large_xml + final_xml;
                            }

                            data.value = 80;
                            data.name = "POSM_ENTRY_DATA_LEAP_FROG";

                            publishProgress(data);

// window data

                            final_xml = "";
                            onXML = "";
                            windowData = database.getSavedWindowData(coverageBeanlist_addstore.get(i).getStoreId(), String.valueOf(coverageBeanlist_addstore.get(i).getMID()));

                            if (windowData.size() > 0) {

                                for (int j = 0; j < windowData.size(); j++) {

                                    String isExist = "";
                                    if (windowData.get(j).isWINDOW_EXIST()) {
                                        isExist = "1";
                                    } else {
                                        isExist = "0";
                                    }
                                    if (windowData.get(j).isWINDOW_EXIST()) {
                                        onXML = "[WINDOW_DATA][MID]"
                                                + mid
                                                + "[/MID]"
                                                + "[CREATED_BY]"
                                                + username
                                                + "[/CREATED_BY]"
                                                + "[COVERAGE_ID]"
                                                + coverageBeanlist_addstore.get(i).getMID()
                                                + "[/COVERAGE_ID]"
                                                + "[KEY_ID]"
                                                + windowData.get(j).getKey_id()
                                                + "[/KEY_ID]"
                                                + "[WINDOW_CD]"
                                                + windowData.get(j).getWINDOW_CD().get(0)
                                                + "[/WINDOW_CD]"
                                                + "[WINDOW_EXISTS]"
                                                + isExist
                                                + "[/WINDOW_EXISTS]"
                                                + "[WINDOW_IMAGE]"
                                                + windowData.get(j).getWINDOW_IMAGE()
                                                + "[/WINDOW_IMAGE]"
                                                + "[/WINDOW_DATA]";

                                        final_xml = final_xml + onXML;
                                    }

                                }

                                final String sos_xml = "[WINDOW]" + final_xml
                                        + "[/WINDOW]";
                                Final_large_xml = Final_large_xml + sos_xml;

                            }
                            data.value = 80;
                            data.name = "WINDOW_DATA_LEAP_FROG";

                            publishProgress(data);

                        /*final_xml = "";
                        onXML = "";
                        //SET CHILD DATA
                        if (windowData.size() > 0) {

                            for (int j = 0; j < windowData.size(); j++) {
                                window_Child_Data = database.getAnswerListForSavedWindowData(windowData.get(j).getKey_id());
                                String sos_xml = "";

                                if (window_Child_Data.size() > 0) {
                                    for (int k = 0; k < window_Child_Data.size(); k++) {

                                        onXML = "[WINDOW_CHILD_DATA][MID]"
                                                + mid
                                                + "[/MID]"
                                                + "[CREATED_BY]"
                                                + username
                                                + "[/CREATED_BY]"
                                                + "[COMMON_ID]"
                                                + windowData.get(j).getKey_id()
                                                + "[/COMMON_ID]"
                                                + "[WINDOW_CD]"
                                                + window_Child_Data.get(k).getWINDOW_CD().get(0)
                                                + "[/WINDOW_CD]"
                                                + "[CHECKLIST_CD]"
                                                + window_Child_Data.get(k).getCHECKLIST_CD().get(0)
                                                + "[/CHECKLIST_CD]"
                                                + "[ANSWER_CD]"
                                                + window_Child_Data.get(k).getANSWER_CD()
                                                + "[/ANSWER_CD]"
                                                + "[/WINDOW_CHILD_DATA]";

                                        final_xml = final_xml + onXML;
                                    }


                                    sos_xml = "[WINDOW_CHILD]" + final_xml + "[/WINDOW_CHILD]";
                                    window_Child_Data.clear();
                                    *//*Final_large_xml = Final_large_xml + sos_xml;
                                    final_xml ="";*//*
                                }

                                final_xml = final_xml + sos_xml;
                                sos_xml="";
                            }
                            Final_large_xml = Final_large_xml + final_xml;
                        }*/


                            final_xml = "";
                            onXML = "";
                            //SET CHILD DATA
                            if (windowData.size() > 0) {

                                String windowXml = "";
                                for (int j = 0; j < windowData.size(); j++) {

                                    if (windowData.get(j).isWINDOW_EXIST()) {

                                        window_Child_Data = database.getAnswerListForSavedWindowData(windowData.get(j).getKey_id());
                                        String sos_xml = "";

                                        if (window_Child_Data.size() > 0) {
                                            for (int k = 0; k < window_Child_Data.size(); k++) {

                                                String onXML1 = "[WINDOW_CHILD_DATA][MID]" + mid + "[/MID]" +
                                                        "[CREATED_BY]" + username + "[/CREATED_BY]"
                                                        + "[COVERAGE_ID]"
                                                        + coverageBeanlist_addstore.get(i).getMID()
                                                        + "[/COVERAGE_ID]"
                                                        + "[COMMON_ID]"
                                                        + windowData.get(j).getKey_id()
                                                        + "[/COMMON_ID]"
                                                        + "[WINDOW_CD]"
                                                        + window_Child_Data.get(k).getWINDOW_CD().get(0)
                                                        + "[/WINDOW_CD]"
                                                        + "[CHECKLIST_CD]"
                                                        + window_Child_Data.get(k).getCHECKLIST_CD().get(0)
                                                        + "[/CHECKLIST_CD]"
                                                        + "[ANSWER_CD]"
                                                        + window_Child_Data.get(k).getANSWER_CD()
                                                        + "[/ANSWER_CD]"
                                                        + "[/WINDOW_CHILD_DATA]";

                                                sos_xml = sos_xml + onXML1;
                                            }

                                        }
                                        windowXml = windowXml + sos_xml;

                                    }
                                }

                                final_xml = "[WINDOW_CHILD]" + windowXml + "[/WINDOW_CHILD]";
                            }
                            Final_large_xml = Final_large_xml + final_xml;

                            String LEAP_FROG_DATA = "[LEAP_FROG_DATA]" + Final_large_xml + "[/LEAP_FROG_DATA]";
                            SoapObject request = new SoapObject(
                                    CommonString.NAMESPACE,
                                    CommonString.METHOD_UPLOAD_XML);
                            request.addProperty("XMLDATA", LEAP_FROG_DATA);
                            request.addProperty("KEYS", "LEAP_FROG_DATA");
                            request.addProperty("USERNAME", username);
                            request.addProperty("MID", mid);

                            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                                    SoapEnvelope.VER11);
                            envelope.dotNet = true;
                            envelope.setOutputSoapObject(request);

                            HttpTransportSE androidHttpTransport = new HttpTransportSE(
                                    CommonString.URL);

                            androidHttpTransport.call(
                                    CommonString.SOAP_ACTION + CommonString.METHOD_UPLOAD_XML,
                                    envelope);
                            Object result = (Object) envelope.getResponse();

                            if (!result.toString().equalsIgnoreCase(CommonString.KEY_SUCCESS)) {
                                return "LEAP_FROG_DATA";
                            }
                            if (result.toString().equalsIgnoreCase(CommonString.KEY_NO_DATA)) {
                                return CommonString.METHOD_UPLOAD_XML;
                            }
                            if (result.toString().equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                                return CommonString.METHOD_UPLOAD_XML;
                            }

                            if (result.toString().equalsIgnoreCase(CommonString.KEY_SUCCESS)) {
                                database.open();
                                database.updateCoverageStatus(coverageBeanlist_addstore.get(i)
                                        .getMID(), CommonString.KEY_D);
                                database.updateStoreStatusOnStoreinfo(coverageBeanlist_addstore.get(i)
                                        .getMID(), CommonString.KEY_D);
                            }


                            publishProgress(data);
                        }


//////////////////////////////////////

                        if (coverageBeanlist_addstore.get(i).getImage() != null && !coverageBeanlist_addstore.get(i).getImage().equals("")) {

                            if (new File(CommonString.FILE_PATH + coverageBeanlist_addstore.get(i).getImage()).exists()) {
                                //result = UploadImage(coverageBeanlist_addstore.get(i).getImage(), "StoreImages");
                                result = uploadRetro.UploadImage2(coverageBeanlist_addstore.get(i).getImage(), "StoreImages", CommonString.FILE_PATH);

                                if (!result.toString().equalsIgnoreCase(CommonString.KEY_SUCCESS)) {
                                    if (result.toString().equalsIgnoreCase(CommonString.KEY_FALSE)) {
                                        return "StoreImages_LEAP_FROG";
                                    } else if (result.toString().equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                                        return "StoreImages_LEAP_FROG" + "," + errormsg;
                                    } else if (result.toString().equalsIgnoreCase(CommonString.MESSAGE_SOCKETEXCEPTION)) {
                                        throw new IOException();
                                    }
                                }

                                runOnUiThread(new Runnable() {
                                    public void run() {

                                        message.setText("Store Image leap frog Uploaded");
                                    }
                                });

                            }
                        }

                        data.value = 85;
                        data.name = "StoreImages_leapFrog";

                        publishProgress(data);


                        if (addNewStoreList.size() > 0) {
                            for (int j = 0; j < addNewStoreList.size(); j++) {

                                if (addNewStoreList.get(j).getImg_Camera() != null && !addNewStoreList.get(j).getImg_Camera().equals("")) {

                                    if (new File(CommonString.FILE_PATH + addNewStoreList.get(j).getImg_Camera()).exists()) {
                                        //result = UploadImage(addNewStoreList.get(j).getImg_Camera(), "StoreImages");
                                        result = uploadRetro.UploadImage2(addNewStoreList.get(j).getImg_Camera(), "StoreImages", CommonString.FILE_PATH);
                                        if (!result.toString().equalsIgnoreCase(CommonString.KEY_SUCCESS)) {
                                            if (result.toString().equalsIgnoreCase(CommonString.KEY_FALSE)) {
                                                return "NewStoreImages_leapFrog";
                                            } else if (result.toString().equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                                                return "NewStoreImages_leapFrog" + "," + errormsg;
                                            } else if (result.toString().equalsIgnoreCase(CommonString.MESSAGE_SOCKETEXCEPTION)) {
                                                throw new IOException();
                                            }
                                        }

                                        runOnUiThread(new Runnable() {

                                            public void run() {
                                                message.setText("NewStoreImages_leapFrog Uploaded");
                                            }
                                        });

                                    }
                                }
                            }
                        }

                        data.value = 70;
                        data.name = "NewStoreImages_leapFrog";

                        publishProgress(data);


                        if (PosmEntryData.size() > 0)
                        {
                            for (int j = 0; j < PosmEntryData.size(); j++) {

                                if (PosmEntryData.get(j).getImage() != null && !PosmEntryData.get(j).getImage().equals("")) {

                                    if (new File(CommonString.FILE_PATH + PosmEntryData.get(j).getImage()).exists()) {
                                        //result = UploadImage(PosmEntryData.get(j).getImage(), "POSMImages");
                                        result = uploadRetro.UploadImage2(PosmEntryData.get(j).getImage(), "POSMImages", CommonString.FILE_PATH);
                                        if (!result.toString().equalsIgnoreCase(CommonString.KEY_SUCCESS)) {
                                            if (result.toString().equalsIgnoreCase(CommonString.KEY_FALSE)) {
                                                return "POSMImages_leapFrog";
                                            } else if (result.toString().equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                                                return "POSMImages_leapFrog" + "," + errormsg;
                                            } else if (result.toString().equalsIgnoreCase(CommonString.MESSAGE_SOCKETEXCEPTION)) {
                                                throw new IOException();
                                            }

                                        }

                                        runOnUiThread(new Runnable() {

                                            public void run() {
                                                message.setText("POSMImages_leapFrog Uploaded");
                                            }
                                        });

                                    }
                                }
                            }
                        }

                        data.value = 70;
                        data.name = "POSMImages_leapFrog";

                        publishProgress(data);


                        //////////////////////////

                        if (windowData.size() > 0) {

                            for (int j = 0; j < windowData.size(); j++) {

                                if (windowData.get(j).getWINDOW_IMAGE() != null && !windowData.get(j).getWINDOW_IMAGE().equals("")) {

                                    if (new File(CommonString.FILE_PATH + windowData.get(j).getWINDOW_IMAGE()).exists()) {
                                        //result = UploadImage(windowData.get(j).getWINDOW_IMAGE(), "WindowImages");
                                        result = uploadRetro.UploadImage2(windowData.get(j).getWINDOW_IMAGE(), "WindowImages", CommonString.FILE_PATH);

                                        if (!result.toString().equalsIgnoreCase(CommonString.KEY_SUCCESS)) {
                                            if (result.toString().equalsIgnoreCase(CommonString.KEY_FALSE)) {
                                                return "WindowImages_leapFrog";
                                            } else if (result.toString().equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                                                return "WindowImages_leapFrog" + "," + errormsg;
                                            } else if (result.toString().equalsIgnoreCase(CommonString.MESSAGE_SOCKETEXCEPTION)) {
                                                throw new IOException();
                                            }
                                        }

                                        runOnUiThread(new Runnable() {

                                            public void run() {

                                                message.setText("WindowImages_leapFrog Uploaded");
                                            }
                                        });

                                    }
                                }
                            }
                        }

                        data.value = 80;
                        data.name = "WindowImages";

                        publishProgress(data);


                        // SET COVERAGE STATUS

               /*         final_xml = "";
                        onXML = "";
                        onXML = "[COVERAGE_STATUS][STORE_ID]"
                                + coverageBeanlist_addstore.get(i).getStoreId()
                                + "[/STORE_ID]"
                                + "[VISIT_DATE]"
                                + coverageBeanlist_addstore.get(i).getVisitDate()
                                + "[/VISIT_DATE]"
                                + "[USER_ID]"
                                + coverageBeanlist_addstore.get(i).getUserId()
                                + "[/USER_ID]"
                                + "[STATUS]"
                                + CommonString.KEY_U
                                + "[/STATUS]"
                                + "[/COVERAGE_STATUS]";

                        final_xml = final_xml + onXML;

                        final String sos_xml = "[DATA]" + final_xml
                                + "[/DATA]";

                        SoapObject request1 = new SoapObject(
                                CommonString.NAMESPACE,
                                CommonString.MEHTOD_UPLOAD_COVERAGE_STATUS);
                        request1.addProperty("onXML", sos_xml);


                        SoapSerializationEnvelope envelope1 = new SoapSerializationEnvelope(
                                SoapEnvelope.VER11);
                        envelope1.dotNet = true;
                        envelope1.setOutputSoapObject(request1);

                        HttpTransportSE androidHttpTransport1 = new HttpTransportSE(
                                CommonString.URL);

                        androidHttpTransport1.call(
                                CommonString.SOAP_ACTION + CommonString.MEHTOD_UPLOAD_COVERAGE_STATUS,
                                envelope1);
                        Object result1 = (Object) envelope1.getResponse();
*/
//                        if (result1.toString().equalsIgnoreCase(
//                                CommonString.KEY_SUCCESS)) {

                        database.open();
                        database.updateCoverageStatus(coverageBeanlist_addstore.get(i)
                                .getMID(), CommonString.KEY_U);
                        database.updateStoreStatusOnStoreinfo(coverageBeanlist_addstore.get(i)
                                .getMID(), CommonString.KEY_U);
                        //  }

                        data.value = 80;
                        data.name = "COVERAGE_STATUS_LEAP_FROG";

                        publishProgress(data);

                    }

                }


                attendanceList = database.getAttendanceList(username, visit_date);

                if (attendanceList.size() > 0) {
                    if (attendanceList.get(0).getATTENDANCE_STATUS().get(0).equalsIgnoreCase(CommonString.KEY_C)) {
                        String onXML = "[ATTENDANCE_DATA]"
                                + "[USER_NAME]" + username + "[/USER_NAME]"
                                + "[REASON_CD]" + attendanceList.get(0).getReason_cd().get(0) + "[/REASON_CD]"
                                + "[VISIT_DATE]" + visit_date + "[/VISIT_DATE]"
                                + "[/ATTENDANCE_DATA]";

                        final String sos_xml = "[DATA]" + onXML
                                + "[/DATA]";
                        SoapObject request = new SoapObject(
                                CommonString.NAMESPACE, CommonString.METHOD_UPLOAD_XML);
                        request.addProperty("XMLDATA", sos_xml);
                        request.addProperty("KEYS", "ATTENDANCE_DATA");
                        request.addProperty("USERNAME", username);
                        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                                SoapEnvelope.VER11);
                        envelope.dotNet = true;
                        envelope.setOutputSoapObject(request);

                        HttpTransportSE androidHttpTransport = new HttpTransportSE(
                                CommonString.URL);

                        androidHttpTransport.call(
                                CommonString.SOAP_ACTION + CommonString.METHOD_UPLOAD_XML,
                                envelope);
                        Object result = (Object) envelope.getResponse();


                        if (result.toString().equalsIgnoreCase(
                                CommonString.KEY_NO_DATA)) {
                            return "Upload_Attendance_Status";
                        }

                        if (result.toString().equalsIgnoreCase(
                                CommonString.KEY_FAILURE)) {
                            return "Upload_Attendance_Status";
                        }

                        // for failure
                        data.value = 100;
                        data.name = "Attendance Done";
                        publishProgress(data);

                        if (result.toString()
                                .equalsIgnoreCase(CommonString.KEY_SUCCESS)) {
                            database.updateAttendanceStatus(username, visit_date, CommonString.KEY_U);
                        } else {
                            if (result.toString().equalsIgnoreCase(
                                    CommonString.KEY_FALSE)) {
                                return CommonString.KEY_Upload_Store_ChecOut_Status;
                            }
                        }
                    }
                }


            } catch (MalformedURLException e) {

                up_success_flag = false;
                return AlertAndMessages.MESSAGE_EXCEPTION;

            } catch (IOException e) {

                up_success_flag = false;
                return AlertAndMessages.MESSAGE_SOCKETEXCEPTION;

            } catch (Exception e) {

                up_success_flag = false;
                return AlertAndMessages.MESSAGE_EXCEPTION;

            }
            if (up_success_flag == true) {
                return CommonString.KEY_SUCCESS;
            }/* else {
                return CommonString.KEY_FAILURE;
            }*/

            return "";


        }

        @Override
        protected void onProgressUpdate(Data... values) {
            // TODO Auto-generated method stub

            pb.setProgress(values[0].value);
            percentage.setText(values[0].value + "%");
            message.setText(values[0].name);

        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            dialog.dismiss();

            if (result.contains(CommonString.KEY_SUCCESS)) {

                AlertAndMessages.ShowAlert1(UploadDataActivity.this, "Data uploaded successfully");

                database.deleteAllTables();

            } else {
                AlertAndMessages.ShowAlert1(UploadDataActivity.this, "Error:" + result);
            }


        }
    }

    class Data {
        int value;
        String name;
    }

    public String UploadImage(String path, String folder_path) throws Exception {

        errormsg = "";
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(Path + path, o);

        // The new size we want to scale to
        final int REQUIRED_SIZE = 1024;

        // Find the correct scale value. It should be the power of 2.
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;

        while (true) {
            if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE)
                break;
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        // Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        Bitmap bitmap = BitmapFactory.decodeFile(
                Path + path, o2);

        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bao);
        byte[] ba = bao.toByteArray();
        String ba1 = Base64.encodeBytes(ba);

        SoapObject request = new SoapObject(CommonString.NAMESPACE,
                CommonString.METHOD_UPLOAD_IMAGE);

        String[] split = path.split("/");
        String path1 = split[split.length - 1];

        request.addProperty("img", ba1);
        request.addProperty("name", path1);
        request.addProperty("FolderName", folder_path);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(
                CommonString.URL);

        androidHttpTransport
                .call(CommonString.SOAP_ACTION_UPLOAD_IMAGE,
                        envelope);
        Object result = (Object) envelope.getResponse();

        if (!result.toString().equalsIgnoreCase(CommonString.KEY_SUCCESS)) {

            if (result.toString().equalsIgnoreCase(CommonString.KEY_FALSE)) {
                return CommonString.KEY_FALSE;
            }

            SAXParserFactory saxPF = SAXParserFactory.newInstance();
            SAXParser saxP = saxPF.newSAXParser();
            XMLReader xmlR = saxP.getXMLReader();

            // for failure
            FailureXMLHandler failureXMLHandler = new FailureXMLHandler();
            xmlR.setContentHandler(failureXMLHandler);

            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(result.toString()));
            xmlR.parse(is);

            failureGetterSetter = failureXMLHandler.getFailureGetterSetter();

            if (failureGetterSetter.getStatus().equalsIgnoreCase(
                    CommonString.KEY_FAILURE)) {
                errormsg = failureGetterSetter.getErrorMsg();
                return CommonString.KEY_FAILURE;
            }
        } else {
            new File(Path + path).delete();
            SharedPreferences.Editor editor = preferences
                    .edit();
            editor.putString(CommonString.KEY_STOREVISITED_STATUS, "");
            editor.commit();
        }

        return result.toString();
    }

}
