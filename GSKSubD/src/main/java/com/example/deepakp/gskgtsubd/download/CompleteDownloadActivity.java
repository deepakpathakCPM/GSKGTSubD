package com.example.deepakp.gskgtsubd.download;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.deepakp.gskgtsubd.R;
import com.example.deepakp.gskgtsubd.bean.TableBean;
import com.example.deepakp.gskgtsubd.constants.CommonString;
import com.example.deepakp.gskgtsubd.database.Database;
import com.example.deepakp.gskgtsubd.gettersetter.BrandMasterGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.CityMasterGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.JCPMasterGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.MappingUsrGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.MappingWindowChecklistGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.NonWorkingReasonGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.PosmMappingGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.PosmMasterGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.StateMasterGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.StoreTypeMasterGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.TownMasterGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.WindowChecklistAnswerGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.WindowChecklistGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.WindowMappingGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.WindowMasterGetterSetter;
import com.example.deepakp.gskgtsubd.xmlHandler.XMLHandlers;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;


public class CompleteDownloadActivity extends AppCompatActivity {
    String _UserId;
    private SharedPreferences preferences;

    TableBean tb;
    Database db;

    private Dialog dialog;
    private ProgressBar pb;
    private Data data;
    private TextView percentage, message;

    int eventType;

    SharedPreferences.Editor editor;
    JCPMasterGetterSetter jcpMasterGetterSetter;
    CityMasterGetterSetter cityMasterGetterSetter;
    TownMasterGetterSetter townMasterGetterSetter;
    PosmMappingGetterSetter posmMappingGetterSetter;
    StateMasterGetterSetter stateMasterGetterSetter;
    StoreTypeMasterGetterSetter storeTypeMasterGetterSetter;
    WindowMasterGetterSetter windowMasterGetterSetter;
    BrandMasterGetterSetter brandMasterGetterSetter;
    PosmMasterGetterSetter posmMasterGetterSetter;
    WindowChecklistGetterSetter windowChecklistGetterSetter;
    MappingWindowChecklistGetterSetter mappingWindowChecklistGetterSetter;
    WindowChecklistAnswerGetterSetter windowChecklistAnswerGetterSetter;
    WindowMappingGetterSetter windowMappingGetterSetter;
    NonWorkingReasonGetterSetter nonworkinggettersetter,attendancegettersetter,nonworkingNewgettersetter;
    MappingUsrGetterSetter mappingUsrGetterSetter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        _UserId = preferences.getString(CommonString.KEY_USERNAME, "");

        tb = new TableBean();
        db = new Database(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        new BackgroundTask(this).execute();
    }

    //Download Asynctask
    private class BackgroundTask extends AsyncTask<Void, Data, String> {
        private Context context;

        BackgroundTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.custom);
            //dialog.setTitle("Download Files");
            dialog.setCancelable(false);
            dialog.show();

            pb = (ProgressBar) dialog.findViewById(R.id.progressBar1);
            percentage = (TextView) dialog.findViewById(R.id.percentage);
            message = (TextView) dialog.findViewById(R.id.message);
        }

        @Override
        protected String doInBackground(Void... params) {
            String resultHttp = "";
            try {
                data = new Data();

                // data
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser xpp = factory.newPullParser();
                SoapObject request;
                SoapSerializationEnvelope envelope;
                HttpTransportSE androidHttpTransport;

                // JOURNEY_PLAN data
                request = new SoapObject(CommonString.NAMESPACE, CommonString.METHOD_NAME_UNIVERSAL_DOWNLOAD);
                request.addProperty("UserName", _UserId);
                request.addProperty("Type", "JOURNEY_PLAN");

                envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                androidHttpTransport = new HttpTransportSE(CommonString.URL);
                androidHttpTransport.call(CommonString.SOAP_ACTION_UNIVERSAL, envelope);

                Object resultJcp = (Object) envelope.getResponse();

                if (resultJcp.toString() != null) {
                    xpp.setInput(new StringReader(resultJcp.toString()));
                    xpp.next();
                    eventType = xpp.getEventType();

                    jcpMasterGetterSetter = XMLHandlers.JCPMasterXMLHandler(xpp, eventType);

                    String jcpMasterTable = jcpMasterGetterSetter.getTable_JOURNEY_PLAN();
                    TableBean.setTable_JCPMaster(jcpMasterTable);

                    if (jcpMasterGetterSetter.getSTORE_CD().size() > 0) {
                        resultHttp = CommonString.KEY_SUCCESS;
                      /*  String jcpMasterTable = jcpMasterGetterSetter.getTable_JOURNEY_PLAN();
                        TableBean.setTable_JCPMaster(jcpMasterTable);*/
                    } else {

                        //  return "JOURNEY_PLAN";
                    }

                    data.value = 5;
                    data.name = "JOURNEY_PLAN Downloading";
                }
                publishProgress(data);


                // CITY_MASTER data
                request = new SoapObject(CommonString.NAMESPACE, CommonString.METHOD_NAME_UNIVERSAL_DOWNLOAD);
                request.addProperty("UserName", _UserId);
                request.addProperty("Type", "CITY_MASTER");

                envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                androidHttpTransport = new HttpTransportSE(CommonString.URL);
                androidHttpTransport.call(CommonString.SOAP_ACTION_UNIVERSAL, envelope);

                Object result = (Object) envelope.getResponse();

                if (result.toString() != null) {
                    xpp.setInput(new StringReader(result.toString()));
                    xpp.next();
                    eventType = xpp.getEventType();

                    cityMasterGetterSetter = XMLHandlers.CityMasterXMLHandler(xpp, eventType);

                    if (cityMasterGetterSetter.getCITY_CD().size() > 0) {
                        resultHttp = CommonString.KEY_SUCCESS;
                        String cityMasterTable = cityMasterGetterSetter.getTable_CityMaster();
                        TableBean.setTable_CityMaster(cityMasterTable);
                    } else {
                        return "CITY MASTER";
                    }

                    data.value = 10;
                    data.name = "CITY MASTER Downloading";
                }
                publishProgress(data);


                // TOWN_MASTER data
                request = new SoapObject(CommonString.NAMESPACE, CommonString.METHOD_NAME_UNIVERSAL_DOWNLOAD);
                request.addProperty("UserName", _UserId);
                request.addProperty("Type", "TOWN_MASTER");

                envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                androidHttpTransport = new HttpTransportSE(CommonString.URL);
                androidHttpTransport.call(CommonString.SOAP_ACTION_UNIVERSAL, envelope);

                result = (Object) envelope.getResponse();

                if (result.toString() != null) {
                    xpp.setInput(new StringReader(result.toString()));
                    xpp.next();
                    eventType = xpp.getEventType();

                    townMasterGetterSetter = XMLHandlers.TownMasterXMLHandler(xpp, eventType);

                    if (townMasterGetterSetter.getTOWN_CD().size() > 0) {
                        resultHttp = CommonString.KEY_SUCCESS;
                        String townMasterTable = townMasterGetterSetter.getTable_TOWN_MASTER();
                        TableBean.setTable_TownMaster(townMasterTable);
                    } else {
                        return "TOWN_MASTER";
                    }

                    data.value = 10;
                    data.name = "TOWN_MASTER Downloading";
                }
                publishProgress(data);


                // POSM_MAPPING data
                request = new SoapObject(CommonString.NAMESPACE, CommonString.METHOD_NAME_UNIVERSAL_DOWNLOAD);
                request.addProperty("UserName", _UserId);
                request.addProperty("Type", "POSM_MAPPING");

                envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                androidHttpTransport = new HttpTransportSE(CommonString.URL);
                androidHttpTransport.call(CommonString.SOAP_ACTION_UNIVERSAL, envelope);

                result = (Object) envelope.getResponse();

                if (result.toString() != null) {
                    xpp.setInput(new StringReader(result.toString()));
                    xpp.next();
                    eventType = xpp.getEventType();

                    posmMappingGetterSetter = XMLHandlers.POSMMAPPINGXMLHandler(xpp, eventType);

                    if (posmMappingGetterSetter.getSTATE_CD().size() > 0) {
                        resultHttp = CommonString.KEY_SUCCESS;
                        String posmMappingTable = posmMappingGetterSetter.getTable_POSMMAPPING();
                        TableBean.setTable_posmMapping(posmMappingTable);
                    } else {
                        return "POSM_MAPPING";
                    }

                    data.value = 10;
                    data.name = "POSM_MAPPING Downloading";
                }
                publishProgress(data);


                //STATE_MASTER Reason data
                request = new SoapObject(CommonString.NAMESPACE, CommonString.METHOD_NAME_UNIVERSAL_DOWNLOAD);
                request.addProperty("UserName", _UserId);
                request.addProperty("Type", "STATE_MASTER");

                envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                androidHttpTransport = new HttpTransportSE(CommonString.URL);
                androidHttpTransport.call(CommonString.SOAP_ACTION_UNIVERSAL, envelope);

                Object resultStateMaster = (Object) envelope.getResponse();

                if (resultStateMaster.toString() != null) {
                    xpp.setInput(new StringReader(resultStateMaster.toString()));
                    xpp.next();
                    eventType = xpp.getEventType();


                    stateMasterGetterSetter = XMLHandlers.stateMasterXML(xpp, eventType);

                    if (stateMasterGetterSetter.getSTATE_CD().size() > 0) {
                        resultHttp = CommonString.KEY_SUCCESS;
                        String statemastertable = stateMasterGetterSetter.getTable_STATE_MASTER();
                        TableBean.setTable_StateMaster(statemastertable);
                    } else {
                        return "STATE_MASTER";
                    }

                    data.value = 30;
                    data.name = "STATE_MASTER Downloading";
                }
                publishProgress(data);


                //STORETYPE_MASTER Data
                request = new SoapObject(CommonString.NAMESPACE, CommonString.METHOD_NAME_UNIVERSAL_DOWNLOAD);
                request.addProperty("UserName", _UserId);
                request.addProperty("Type", "STORETYPE_MASTER");

                envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                androidHttpTransport = new HttpTransportSE(CommonString.URL);
                androidHttpTransport.call(CommonString.SOAP_ACTION_UNIVERSAL, envelope);

                Object storeTypeResult = (Object) envelope.getResponse();

                if (storeTypeResult.toString() != null) {
                    xpp.setInput(new StringReader(storeTypeResult.toString()));
                    xpp.next();
                    eventType = xpp.getEventType();

                    storeTypeMasterGetterSetter = XMLHandlers.storeTypeXmlHandler(xpp, eventType);

                    if (storeTypeMasterGetterSetter.getSTORETYPE_CD().size() > 0) {
                        resultHttp = CommonString.KEY_SUCCESS;
                        String storetypeMastertable = storeTypeMasterGetterSetter.getTable_STORETYPE_MASTER();
                        TableBean.setTable_StoreTypeMaster(storetypeMastertable);
                    } else {
                        return "STORETYPE_MASTER";
                    }

                    data.value = 45;
                    data.name = "STORETYPE_MASTER Downloading";
                }
                publishProgress(data);


                //WINDOW_MASTER Data
                request = new SoapObject(CommonString.NAMESPACE, CommonString.METHOD_NAME_UNIVERSAL_DOWNLOAD);
                request.addProperty("UserName", _UserId);
                request.addProperty("Type", "WINDOW_MASTER");

                envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                androidHttpTransport = new HttpTransportSE(CommonString.URL);
                androidHttpTransport.call(CommonString.SOAP_ACTION_UNIVERSAL, envelope);

                Object windowMasterResult = (Object) envelope.getResponse();

                if (windowMasterResult.toString() != null) {
                    xpp.setInput(new StringReader(windowMasterResult.toString()));
                    xpp.next();
                    eventType = xpp.getEventType();

                    windowMasterGetterSetter = XMLHandlers.windowMasterXmlHandler(xpp, eventType);

                    if (windowMasterGetterSetter.getWINDOW_CD().size() > 0) {
                        resultHttp = CommonString.KEY_SUCCESS;
                        String window_masterTable = windowMasterGetterSetter.getTable_WINDOW_MASTER();
                        TableBean.setTable_WindowMaster(window_masterTable);
                    } else {
                        return "WINDOW_MASTER";
                    }

                    data.value = 55;
                    data.name = "WINDOW_MASTER Downloading";
                }
                publishProgress(data);

                //BRAND_MASTER Data
                request = new SoapObject(CommonString.NAMESPACE, CommonString.METHOD_NAME_UNIVERSAL_DOWNLOAD);
                request.addProperty("UserName", _UserId);
                request.addProperty("Type", "BRAND_MASTER");

                envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                androidHttpTransport = new HttpTransportSE(CommonString.URL);
                androidHttpTransport.call(CommonString.SOAP_ACTION_UNIVERSAL, envelope);

                Object brandMasterResult = (Object) envelope.getResponse();

                if (brandMasterResult.toString() != null) {
                    xpp.setInput(new StringReader(brandMasterResult.toString()));
                    xpp.next();
                    eventType = xpp.getEventType();

                    brandMasterGetterSetter = XMLHandlers.brandMasterXmlHandler(xpp, eventType);

                    if (brandMasterGetterSetter.getBRAND_CD().size() > 0) {
                        resultHttp = CommonString.KEY_SUCCESS;
                        String brand_masterTable = brandMasterGetterSetter.getTable_BrandMaster();
                        TableBean.setTable_BrandMaster(brand_masterTable);
                    } else {
                        return "BRAND_MASTER";
                    }

                    data.value = 58;
                    data.name = "BRAND_MASTER Downloading";
                }
                publishProgress(data);


                //POSM_MASTER Data
                request = new SoapObject(CommonString.NAMESPACE, CommonString.METHOD_NAME_UNIVERSAL_DOWNLOAD);
                request.addProperty("UserName", _UserId);
                request.addProperty("Type", "POSM_MASTER");

                envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                androidHttpTransport = new HttpTransportSE(CommonString.URL);
                androidHttpTransport.call(CommonString.SOAP_ACTION_UNIVERSAL, envelope);

                Object posmMasterResult = (Object) envelope.getResponse();

                if (posmMasterResult.toString() != null) {
                    xpp.setInput(new StringReader(posmMasterResult.toString()));
                    xpp.next();
                    eventType = xpp.getEventType();

                    posmMasterGetterSetter = XMLHandlers.posmMasterXmlHandler(xpp, eventType);

                    if (posmMasterGetterSetter.getPOSM_CD().size() > 0) {
                        resultHttp = CommonString.KEY_SUCCESS;
                        String posm_masterTable = posmMasterGetterSetter.getTable_POSMMaster();
                        TableBean.setTable_PosmMaster(posm_masterTable);
                    } else {
                        return "POSM_MASTER";
                    }

                    data.value = 63;
                    data.name = "POSM_MASTER Downloading";
                }
                publishProgress(data);


                //WINDOW_CHECKLIST
                request = new SoapObject(CommonString.NAMESPACE, CommonString.METHOD_NAME_UNIVERSAL_DOWNLOAD);
                request.addProperty("UserName", _UserId);
                request.addProperty("Type", "WINDOW_CHECKLIST");

                envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                androidHttpTransport = new HttpTransportSE(CommonString.URL);
                androidHttpTransport.call(CommonString.SOAP_ACTION_UNIVERSAL, envelope);

                Object windowChecklistResult = (Object) envelope.getResponse();

                if (windowChecklistResult.toString() != null) {
                    xpp.setInput(new StringReader(windowChecklistResult.toString()));
                    xpp.next();
                    eventType = xpp.getEventType();

                    windowChecklistGetterSetter = XMLHandlers.windowChecklistXmlHandler(xpp, eventType);

                    if (windowChecklistGetterSetter.getCHECKLIST_CD().size() > 0) {
                        resultHttp = CommonString.KEY_SUCCESS;
                        String windowChecklistTable = windowChecklistGetterSetter.getTable_WINDOW_CHECKLIST();
                        TableBean.setTable_windowChecklist(windowChecklistTable);
                    } else {
                        return "WINDOW_CHECKLIST";
                    }
                    data.value = 65;
                    data.name = "WINDOW_CHECKLIST Downloading";
                }
                publishProgress(data);


                //MAPPING_WINDOW_CHECKLIST data
                request = new SoapObject(CommonString.NAMESPACE, CommonString.METHOD_NAME_UNIVERSAL_DOWNLOAD);
                request.addProperty("UserName", _UserId);
                request.addProperty("Type", "MAPPING_WINDOW_CHECKLIST");

                envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                androidHttpTransport = new HttpTransportSE(CommonString.URL);
                androidHttpTransport.call(CommonString.SOAP_ACTION_UNIVERSAL, envelope);

                Object mappingWindowChecklistResult = (Object) envelope.getResponse();

                if (mappingWindowChecklistResult.toString() != null) {
                    xpp.setInput(new StringReader(mappingWindowChecklistResult.toString()));
                    xpp.next();
                    eventType = xpp.getEventType();

                    mappingWindowChecklistGetterSetter = XMLHandlers.mappingWindowChecklistXmlHandler(xpp, eventType);

                    if (mappingWindowChecklistGetterSetter.getCHECKLIST_CD().size() > 0) {
                        resultHttp = CommonString.KEY_SUCCESS;
                        String mappingWindowChecklisttable = mappingWindowChecklistGetterSetter.getTable_MAPPING_WINDOW_CHECKLIST();
                        TableBean.setTable_mappingWindowChecklist(mappingWindowChecklisttable);
                    } else {
                        return "MAPPING_WINDOW_CHECKLIST";
                    }

                    data.value = 75;
                    data.name = "MAPPING_WINDOW_CHECKLIST DATA DOWNLOADING";
                }
                publishProgress(data);


                //WINDOW_CHECKLIST_ANSWER
                request = new SoapObject(CommonString.NAMESPACE, CommonString.METHOD_NAME_UNIVERSAL_DOWNLOAD);
                request.addProperty("UserName", _UserId);
                request.addProperty("Type", "WINDOW_CHECKLIST_ANSWER");

                envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                androidHttpTransport = new HttpTransportSE(CommonString.URL);
                androidHttpTransport.call(CommonString.SOAP_ACTION_UNIVERSAL, envelope);

                Object windowChecklistAnswerResult = (Object) envelope.getResponse();

                if (windowChecklistAnswerResult.toString() != null) {
                    xpp.setInput(new StringReader(windowChecklistAnswerResult.toString()));
                    xpp.next();
                    eventType = xpp.getEventType();

                    windowChecklistAnswerGetterSetter = XMLHandlers.windowChecklistAnswerXmlHandler(xpp, eventType);

                    if (windowChecklistAnswerGetterSetter.getANSWER_CD().size() > 0) {
                        resultHttp = CommonString.KEY_SUCCESS;
                        String windowChecklistAnswerTable = windowChecklistAnswerGetterSetter.getTable_WINDOW_CHECKLIST_ANSWER();
                        TableBean.setTable_windowChecklistAnswer(windowChecklistAnswerTable);
                    } else {
                        return "WINDOW_CHECKLIST_ANSWER";
                    }

                    data.value = 90;
                    data.name = "WINDOW_CHECKLIST_ANSWER DATA DOWNLOADING";
                }
                publishProgress(data);


                //WINDOW_MAPPING
                request = new SoapObject(CommonString.NAMESPACE, CommonString.METHOD_NAME_UNIVERSAL_DOWNLOAD);
                request.addProperty("UserName", _UserId);
                request.addProperty("Type", "WINDOW_MAPPING");

                envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                androidHttpTransport = new HttpTransportSE(CommonString.URL);
                androidHttpTransport.call(CommonString.SOAP_ACTION_UNIVERSAL, envelope);

                Object windowMappingResult = (Object) envelope.getResponse();

                if (windowMappingResult.toString() != null) {
                    xpp.setInput(new StringReader(windowMappingResult.toString()));
                    xpp.next();
                    eventType = xpp.getEventType();

                    windowMappingGetterSetter = XMLHandlers.windowMappingXmlHandler(xpp, eventType);

                    if (windowMappingGetterSetter.getWINDOW_CD().size() > 0) {
                        resultHttp = CommonString.KEY_SUCCESS;
                        String windowMappingTable = windowMappingGetterSetter.getTable_WINDOW_MAPPING();
                        TableBean.setTable_WindowMapping(windowMappingTable);
                    } else {
                        return "WINDOW_MAPPING";
                    }

                    data.value = 100;
                    data.name = "WINDOW_MAPPING DATA DOWNLOADING";
                }
                publishProgress(data);


                //Non Working Reason data
                request = new SoapObject(CommonString.NAMESPACE,
                        CommonString.METHOD_NAME_UNIVERSAL_DOWNLOAD);

                request.addProperty("UserName", _UserId);
                request.addProperty("Type", "NON_WORKING_REASON");

                envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                androidHttpTransport = new HttpTransportSE(CommonString.URL);

                androidHttpTransport.call(
                        CommonString.SOAP_ACTION_UNIVERSAL, envelope);
                Object resultnonworking = (Object) envelope.getResponse();

                if (resultnonworking.toString() != null) {

                    xpp.setInput(new StringReader(resultnonworking.toString()));
                    xpp.next();
                    eventType = xpp.getEventType();

                    nonworkinggettersetter = XMLHandlers.nonWorkinReasonXML(xpp, eventType);

                    if (nonworkinggettersetter.getReason_cd().size() > 0) {
                        resultHttp = CommonString.KEY_SUCCESS;
                        String nonworkingtable = nonworkinggettersetter.getNonworking_table();
                        TableBean.setNonworkingtable(nonworkingtable);

                    } else {
                        return "NON_WORKING_REASON";
                    }

                    data.value = 90;
                    data.name = "Non Working Reason Downloading";

                }

                publishProgress(data);

                //Non Working Reason data
                request = new SoapObject(CommonString.NAMESPACE,
                        CommonString.METHOD_NAME_UNIVERSAL_DOWNLOAD);

                request.addProperty("UserName", _UserId);
                request.addProperty("Type", "NON_WORKING_REASON_NEW");

                envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                androidHttpTransport = new HttpTransportSE(CommonString.URL);

                androidHttpTransport.call(
                        CommonString.SOAP_ACTION_UNIVERSAL, envelope);
                Object resultnonworkingnew = (Object) envelope.getResponse();

                if (resultnonworkingnew.toString() != null) {

                    xpp.setInput(new StringReader(resultnonworkingnew.toString()));
                    xpp.next();
                    eventType = xpp.getEventType();

                    nonworkingNewgettersetter = XMLHandlers.nonWorkinReasonNewXML(xpp, eventType);

                    if (nonworkingNewgettersetter.getReason_cd().size() > 0) {
                        resultHttp = CommonString.KEY_SUCCESS;
                        String nonworkingtable = nonworkingNewgettersetter.getNonworking_table();
                        TableBean.setNonworkingNewtable(nonworkingtable);

                    } else {
                        return "NON_WORKING_REASON_NEW";
                    }

                    data.value = 95;
                    data.name = "NON_WORKING_REASON_NEW Downloading";
                }

                publishProgress(data);


                //MAPPING_USR data
                request = new SoapObject(CommonString.NAMESPACE,
                        CommonString.METHOD_NAME_UNIVERSAL_DOWNLOAD);

                request.addProperty("UserName", _UserId);
                request.addProperty("Type", "MAPPING_USR");

                envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                androidHttpTransport = new HttpTransportSE(CommonString.URL);

                androidHttpTransport.call(
                        CommonString.SOAP_ACTION_UNIVERSAL, envelope);
                Object resultmappingusr = (Object) envelope.getResponse();

                if (resultmappingusr.toString() != null) {

                    xpp.setInput(new StringReader(resultmappingusr.toString()));
                    xpp.next();
                    eventType = xpp.getEventType();

                    mappingUsrGetterSetter = XMLHandlers.mappingUsrXML(xpp, eventType);

                    String nonworkingtable = mappingUsrGetterSetter.getTable_mappingUsr();
                    TableBean.setMappingUsrTable(nonworkingtable);

                    if (mappingUsrGetterSetter.getUSR_CD().size() > 0) {
                        resultHttp = CommonString.KEY_SUCCESS;
                    }
                  /*  else {
                        return "MAPPING_USR";
                    }
*/
                    data.value = 95;
                    data.name = "MAPPING_USR Downloading";
                }

                publishProgress(data);


                //MAPPING_USR data
                request = new SoapObject(CommonString.NAMESPACE,
                        CommonString.METHOD_NAME_UNIVERSAL_DOWNLOAD);

                request.addProperty("UserName", _UserId);
                request.addProperty("Type", "ATTENDANCE_STATUS");

                envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                androidHttpTransport = new HttpTransportSE(CommonString.URL);

                androidHttpTransport.call(
                        CommonString.SOAP_ACTION_UNIVERSAL, envelope);
                Object resultattendance = (Object) envelope.getResponse();

                if (resultattendance.toString() != null) {

                    xpp.setInput(new StringReader(resultattendance.toString()));
                    xpp.next();
                    eventType = xpp.getEventType();

                    attendancegettersetter = XMLHandlers.attendanceXML(xpp, eventType);

                    String nonworkingtable = attendancegettersetter.getNonworking_table();
                    TableBean.setAttendanceTable(nonworkingtable);

                    if (attendancegettersetter.getReason_cd().size() > 0) {
                        resultHttp = CommonString.KEY_SUCCESS;
                    }
                  /*  else {
                        return "MAPPING_USR";
                    }
*/
                    data.value = 95;
                    data.name = "ATTENDANCE_STATUS Downloading";
                }

                publishProgress(data);


                db.open();

                if(jcpMasterGetterSetter.getSTORE_CD().size()>0)
                {
                    db.insertJCPMasterData(jcpMasterGetterSetter);
                }

                db.insertCityMasterData(cityMasterGetterSetter);
                db.insertTownMasterData(townMasterGetterSetter);
                db.insertPosmMappingData(posmMappingGetterSetter);
                db.insertStateMasterData(stateMasterGetterSetter);
                db.insertStoreTypeData(storeTypeMasterGetterSetter);
                db.insertWindowMasterData(windowMasterGetterSetter);
                db.insertBrandMasterData(brandMasterGetterSetter);
                db.insertPosmMasterData(posmMasterGetterSetter);
                db.insertWindowChecklistData(windowChecklistGetterSetter);
                db.insertmappingWindowChecklistData(mappingWindowChecklistGetterSetter);
                db.insertWindowChecklistAnswerData(windowChecklistAnswerGetterSetter);
                db.insertWindowMappingData(windowMappingGetterSetter);
                db.insertNonWorkingReasonData(nonworkinggettersetter);
                db.insertNonWorkingNewReasonData(nonworkingNewgettersetter);

                if(mappingUsrGetterSetter.getUSR_CD().size()>0)
                {
                    db.insertMappingUsrData(mappingUsrGetterSetter);
                }
                db.insertAttendanceData(attendancegettersetter);


                editor = preferences.edit();
                editor.putBoolean(CommonString.KEY_ISDATADOWNLOADED,true);
                editor.commit();

                data.value = 100;
                data.name = "Finishing";
                publishProgress(data);

                return resultHttp;
            } catch (MalformedURLException e) {
               /* runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showMessage(CommonString.MESSAGE_EXCEPTION);
                    }
                });*/
                return CommonString.MESSAGE_EXCEPTION;

            } catch (IOException e) {
               /* runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showMessage(CommonString.MESSAGE_SOCKETEXCEPTION);

                    }
                });*/

                return CommonString.MESSAGE_SOCKETEXCEPTION;
            } catch (Exception e) {
               /* runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showMessage(CommonString.MESSAGE_EXCEPTION);
                    }
                });*/

                return CommonString.MESSAGE_EXCEPTION;
            }
        }

        @Override
        protected void onProgressUpdate(Data... values) {
            pb.setProgress(values[0].value);
            percentage.setText(values[0].value + "%");
            message.setText(values[0].name);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dialog.dismiss();

            if (result.equals(CommonString.KEY_SUCCESS)) {
                showMessage(CommonString.MESSAGE_DOWNLOAD);
            } else {
                showMessage(CommonString.MESSAGE_JCP_FALSE + " " + result);
            }
        }
    }

    class Data {
        int value;
        String name;
    }

    public void showMessage(String msg) {
        new AlertDialog.Builder(CompleteDownloadActivity.this)
                .setTitle("Alert Dialog")
                .setMessage(msg)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        finish();
                    }
                })
                // .setIcon(R.drawable.parinaam_logo_ico)
                .show();
    }
}
