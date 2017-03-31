package com.example.deepakp.gskgtsubd.dailyentry;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.deepakp.gskgtsubd.R;
import com.example.deepakp.gskgtsubd.bean.CoverageBean;
import com.example.deepakp.gskgtsubd.constants.CommonString;
import com.example.deepakp.gskgtsubd.database.Database;
import com.example.deepakp.gskgtsubd.gettersetter.FailureGetterSetter;
import com.example.deepakp.gskgtsubd.utilities.AlertAndMessages;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CheckoutActivity extends AppCompatActivity implements LocationListener {


    private Dialog dialog;
    private ProgressBar pb;
    private TextView percentage, message;
    private String username, visit_date, store_id, store_intime,store_outtime="0";;
    private Data data;
    int eventType;
    private Database db;
    CoverageBean coverageBean;

    private SharedPreferences preferences = null;
    private FailureGetterSetter failureGetterSetter = null;
    static int counter = 1;
    private double latitude = 0.0, longitude = 0.0;
    public static String currLatitude = "0.0";
    public static String currLongitude = "0.0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        username = preferences.getString(CommonString.KEY_USERNAME, "");
        visit_date = preferences.getString(CommonString.KEY_DATE, null);
        store_id = getIntent().getStringExtra(CommonString.KEY_STORE_CD);
        //store_intime = preferences.getString(CommonString.KEY_STORE_IN_TIME, "");
        currLatitude = preferences.getString(CommonString.KEY_LATITUDE, "0.0");
        currLongitude = preferences
                .getString(CommonString.KEY_LONGITUDE, "0.0");
        Boolean fromStore = getIntent().getBooleanExtra(CommonString.KEY_FROMSTORE,false);

        db = new Database(this);
        db.open();

        coverageBean = db.getCoverageSpecificData(store_id);
        store_intime = coverageBean.getInTime();
        store_outtime = getCurrentTime();

        if(fromStore)
        {
            new BackgroundTask(this).execute();
        }
        else
        {
            new BackgroundTaskForAttendence(this).execute();
        }

    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        db.open();

    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        db.close();
    }

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
            dialog.setContentView(R.layout.custom);
            dialog.setTitle("Sending Checkout Data");
            dialog.setCancelable(false);
            dialog.show();
            pb = (ProgressBar) dialog.findViewById(R.id.progressBar1);
            percentage = (TextView) dialog.findViewById(R.id.percentage);
            message = (TextView) dialog.findViewById(R.id.message);

        }

        @SuppressWarnings("deprecation")
        @Override
        protected String doInBackground(Void... params) {
            // TODO Auto-generated method stub

            try {

                //String result = "";
                data = new Data();

                data.value = 20;
                data.name = "Checked out Data Uploading";
                publishProgress(data);


                String onXML = "[STORE_CHECK_OUT_STATUS][USER_ID]"
                        + username
                        + "[/USER_ID]" + "[STORE_ID]"
                        + store_id
                        + "[/STORE_ID][LATITUDE]"
                        + latitude
                        + "[/LATITUDE][LOGITUDE]"
                        + longitude
                        + "[/LOGITUDE][CHECKOUT_DATE]"
                        + visit_date
                        + "[/CHECKOUT_DATE][CHECK_OUTTIME]"
                        + store_outtime
                        + "[/CHECK_OUTTIME][CHECK_INTIME]"
                        + store_intime
                        + "[/CHECK_INTIME][CREATED_BY]"
                        + username
                        + "[/CREATED_BY][/STORE_CHECK_OUT_STATUS]";


                final String sos_xml = "[DATA]" + onXML
                        + "[/DATA]";

                SoapObject request = new SoapObject(
                        CommonString.NAMESPACE,
                        "Upload_Store_ChecOut_Status");
                request.addProperty("onXML", sos_xml);
				/*request.addProperty("KEYS", "CHECKOUT_STATUS");
				request.addProperty("USERNAME", username);*/
                //request.addProperty("MID", mid);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                        SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                HttpTransportSE androidHttpTransport = new HttpTransportSE(
                        CommonString.URL);

                androidHttpTransport.call(
                        CommonString.SOAP_ACTION+"Upload_Store_ChecOut_Status",
                        envelope);
                Object result = (Object) envelope.getResponse();


                if (result.toString().equalsIgnoreCase(
                        CommonString.KEY_NO_DATA)) {
                    return "Upload_Store_ChecOut_Status";
                }

                if (result.toString().equalsIgnoreCase(
                        CommonString.KEY_FAILURE)) {
                    return "Upload_Store_ChecOut_Status";
                }

                // for failure



                data.value = 100;
                data.name = "Checkout Done";
                publishProgress(data);

                if (result.toString()
                        .equalsIgnoreCase(CommonString.KEY_SUCCESS)) {

                    db.updateCoverageStoreOutTime(store_id, visit_date,store_outtime, CommonString.KEY_C);

                    /*SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(CommonString.KEY_STOREVISITED_STATUS, "");
                    editor.putString(CommonString.KEY_STORE_IN_TIME, "");
                    editor.putString(CommonString.KEY_LATITUDE, "");
                    editor.putString(CommonString.KEY_LONGITUDE, "");
                    editor.commit();*/

                    db.updateStoreStatusOnCheckout(store_id, visit_date,
                            CommonString.KEY_C);

                } else {
                    if (result.toString().equalsIgnoreCase(
                            CommonString.KEY_FALSE)) {
                        return CommonString.KEY_Upload_Store_ChecOut_Status;
                    }

                    // for failure

                }
                return CommonString.KEY_SUCCESS;

            } catch (MalformedURLException e) {

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub

                        AlertAndMessages.showAlertMessage(CheckoutActivity.this,AlertAndMessages.MESSAGE_EXCEPTION);
                    }
                });

            } catch (IOException e) {
                // counter++;
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        AlertAndMessages.showAlertMessage(CheckoutActivity.this,AlertAndMessages.MESSAGE_SOCKETEXCEPTION);
                        // TODO Auto-generated method stub
						/*
						 * if (counter < 10) { new
						 * BackgroundTask(CheckOutUploadActivity
						 * .this).execute(); } else { message.showMessage();
						 * counter =1; }
						 */
                    }
                });
            } catch (Exception e) {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        AlertAndMessages.showAlertMessage(CheckoutActivity.this,AlertAndMessages.MESSAGE_EXCEPTION);

                    }
                });
            }

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

            if (result.equals(CommonString.KEY_SUCCESS)) {

                AlertAndMessages.showAlertMessage(CheckoutActivity.this,"Successfully Checked out");
                finish();
            } else if (!result.equals("")) {
                AlertAndMessages.showToastMessage(CheckoutActivity.this,"Network Error Try Again");
                finish();
            }

        }

    }


    private class BackgroundTaskForAttendence extends AsyncTask<Void, Data, String> {
        private Context context;

        BackgroundTaskForAttendence(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            dialog = new Dialog(context);
            dialog.setContentView(R.layout.custom);
            dialog.setTitle("Sending Attendance Data");
            dialog.setCancelable(false);
            dialog.show();
            pb = (ProgressBar) dialog.findViewById(R.id.progressBar1);
            percentage = (TextView) dialog.findViewById(R.id.percentage);
            message = (TextView) dialog.findViewById(R.id.message);

        }

        @SuppressWarnings("deprecation")
        @Override
        protected String doInBackground(Void... params) {
            // TODO Auto-generated method stub

            try {

                //String result = "";
                data = new Data();

                data.value = 20;
                data.name = "Attendance Data Uploading";
                publishProgress(data);


                String onXML = "[ATTENDANCE_DATA]"
                        + "[USER_NAME]" + username + "[/USER_NAME]"
                        + "[REASON_CD]" + store_id + "[/REASON_CD]"
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
                        CommonString.SOAP_ACTION+CommonString.METHOD_UPLOAD_XML,
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
                    db.updateAttendanceStatus(username, visit_date, CommonString.KEY_U);
                } else {
                    if (result.toString().equalsIgnoreCase(
                            CommonString.KEY_FALSE)) {
                        return CommonString.KEY_Upload_Store_ChecOut_Status;
                    }

                    // for failure

                }
                return CommonString.KEY_SUCCESS;

            } catch (MalformedURLException e) {

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub

                        AlertAndMessages.showAlertMessage(CheckoutActivity.this,AlertAndMessages.MESSAGE_EXCEPTION);
                    }
                });

            } catch (IOException e) {
                // counter++;
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        AlertAndMessages.showAlertMessage(CheckoutActivity.this,AlertAndMessages.MESSAGE_SOCKETEXCEPTION);
                        // TODO Auto-generated method stub
                    }
                });
            } catch (Exception e) {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        AlertAndMessages.showAlertMessage(CheckoutActivity.this,AlertAndMessages.MESSAGE_EXCEPTION);
                    }
                });
            }

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

            if (result.equals(CommonString.KEY_SUCCESS)) {
                AlertAndMessages.showToastMessage(CheckoutActivity.this,"Successfully Attendance marked");
                finish();
            } else if (!result.equals("")) {
                AlertAndMessages.showToastMessage(CheckoutActivity.this,"Network Error Try Again");
                finish();
            }

        }

    }


    class Data {
        int value;
        String name;
    }





    public String getCurrentTime() {

        Calendar m_cal = Calendar.getInstance();

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String cdate = formatter.format(m_cal.getTime());

       /* String intime = m_cal.get(Calendar.HOUR_OF_DAY) + ":"
                + m_cal.get(Calendar.MINUTE) + ":" + m_cal.get(Calendar.SECOND);*/

        return cdate;

    }

    @Override
    public void onLocationChanged(Location location) {
        // TODO Auto-generated method stub
        latitude = location.getLatitude();
        longitude = location.getLongitude();
    }

    @Override
    public void onProviderDisabled(String arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String arg0) {
        // TODO Auto-generated method stub

    }



    @Override
    public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
        // TODO Auto-generated method stub

    }




}
