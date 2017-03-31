package com.example.deepakp.gskgtsubd.dailyentry;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.deepakp.gskgtsubd.R;
import com.example.deepakp.gskgtsubd.bean.CoverageBean;
import com.example.deepakp.gskgtsubd.constants.CommonString;
import com.example.deepakp.gskgtsubd.database.Database;
import com.example.deepakp.gskgtsubd.gettersetter.AddNewStoreGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.NonWorkingReasonGetterSetter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class NonWorkingReason extends AppCompatActivity implements
        OnItemSelectedListener, OnClickListener {

    ArrayList<NonWorkingReasonGetterSetter> reasondata = new ArrayList<NonWorkingReasonGetterSetter>();
   // ArrayList<NonWorkingSubReasonGetterSetter> subreasondata = new ArrayList<NonWorkingSubReasonGetterSetter>();
    private Spinner reasonspinner, merNotAllowedSpinner;
    private Database database;
    String reasonname, reasonid, subreasonname, subreasonid = "0", entry_allow,image_allow, image, entry, reason_reamrk, intime;
    Button save;
    private ArrayAdapter<CharSequence> reason_adapter, subreason_adapter;
    protected String _path, str;
    protected String _pathforcheck = "";
    private String image1 = "";
    private ArrayList<CoverageBean> cdata = new ArrayList<CoverageBean>();
    protected boolean _taken;
    protected static final String PHOTO_TAKEN = "photo_taken";
    private SharedPreferences preferences;
    String _UserId, visit_date, store_id;
    protected boolean status = true;
    EditText text, informTo;
    AlertDialog alert;
    ImageButton camera;
    RelativeLayout reason_lay, rel_cam;

    boolean leave_flag = false;

    ArrayList<AddNewStoreGetterSetter> jcp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nonworking);

        reasonspinner = (Spinner) findViewById(R.id.spinner2);
        merNotAllowedSpinner = (Spinner) findViewById(R.id.merNotallowdspinner);
        camera = (ImageButton) findViewById(R.id.imgcam);
        save = (Button) findViewById(R.id.save);
        text = (EditText) findViewById(R.id.reasontxt);
        informTo = (EditText) findViewById(R.id.edit_informto);
        reason_lay = (RelativeLayout) findViewById(R.id.layout_reason);
        rel_cam = (RelativeLayout) findViewById(R.id.relimgcam);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        _UserId = preferences.getString(CommonString.KEY_USERNAME, "");
        visit_date = preferences.getString(CommonString.KEY_DATE, null);
        store_id =getIntent().getStringExtra(CommonString.KEY_STORE_CD);

        database = new Database(this);
        database.open();
        str = CommonString.FILE_PATH;

        reasondata = database.getNonWorkingData(true);
      //  subreasondata = database.getNonWorkingSubReasonData();
        /*cdata = database.getCoverageData(visit_date, null);
        storedata = database.getStoreData(visit_date);*/
        intime = getCurrentTime();

        camera.setOnClickListener(this);
        save.setOnClickListener(this);

        reason_adapter = new ArrayAdapter<CharSequence>(this,
                android.R.layout.simple_spinner_item);
        reason_adapter.add("Select Reason");
        for (int i = 0; i < reasondata.size(); i++) {
            reason_adapter.add(reasondata.get(i).getReason().get(0));
        }
        reasonspinner.setAdapter(reason_adapter);
        reason_adapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        reasonspinner.setOnItemSelectedListener(this);

        subreason_adapter = new ArrayAdapter<CharSequence>(this,
                android.R.layout.simple_spinner_item);
        subreason_adapter.add("Select Sub Reason");
       /* for (int i = 0; i < subreasondata.size(); i++) {
            subreason_adapter.add(subreasondata.get(i).getSUB_REASON().get(0));
        }*/
        merNotAllowedSpinner.setAdapter(subreason_adapter);
        subreason_adapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        merNotAllowedSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub

		/*Intent i = new Intent(this, StorelistActivity.class);
        startActivity(i);*/
        finish();

        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
                               long arg3) {
        // TODO Auto-generated method stub

        switch (arg0.getId()) {
            case R.id.spinner2:
                if (position != 0) {
                    reasonname = reasondata.get(position - 1).getReason().get(0);
                    reasonid = reasondata.get(position - 1).getReason_cd().get(0);
                    entry_allow = reasondata.get(position - 1).getEntry_allow().get(0);
                    image_allow = reasondata.get(position - 1).getIMAGE_ALLOW().get(0);
                    //image = reasondata.get(position - 1).getImage();
                    //	entry = reasondata.get(position - 1).getEntry();
                    //reason_reamrk = reasondata.get(position - 1).getREASON_REMARK();

                    if (image_allow.equalsIgnoreCase("1")) {
                        rel_cam.setVisibility(View.VISIBLE);
                        image = "true";
                    } else {
                        rel_cam.setVisibility(View.GONE);
                        image = "false";
                    }
                  /*  if (reasonid.equalsIgnoreCase("7")) {
                        merNotAllowedSpinner.setVisibility(View.VISIBLE);
                    } else {
                        merNotAllowedSpinner.setVisibility(View.GONE);
                    }*/

                    reason_reamrk = "true";
                    if (reason_reamrk.equalsIgnoreCase("true")) {
                        reason_lay.setVisibility(View.VISIBLE);
                    } else {
                        reason_lay.setVisibility(View.GONE);
                    }

                } else {
                    reasonname = "";
                    reasonid = "";
                    //image = "";
                    //entry = "";
                    //reason_reamrk = "";
                }
                break;
            case R.id.merNotallowdspinner:
                if (position != 0) {
                  //  subreasonname = subreasondata.get(position - 1).getSUB_REASON().get(0);
                   // subreasonid = subreasondata.get(position - 1).getSUB_REASON_CD().get(0);
                } else {
                    subreasonname = "";
                    subreasonid = "";
                }
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

    protected void startCameraActivity() {

        try {
            Log.i("MakeMachine", "startCameraActivity()");
            File file = new File(_path);
            Uri outputFileUri = Uri.fromFile(file);

            Intent intent = new Intent(
                    MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

            startActivityForResult(intent, 0);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("MakeMachine", "resultCode: " + resultCode);
        switch (resultCode) {
            case 0:
                Log.i("MakeMachine", "User cancelled");
                break;

            case -1:

                if (_pathforcheck != null && !_pathforcheck.equals("")) {
                    if (new File(str + _pathforcheck).exists()) {

                        camera.setImageDrawable(getResources().getDrawable(R.drawable.camera_green));

                        //camera.setBackgroundResource(R.drawable.camera_list_tick);
                        image1 = _pathforcheck;

                    }
                }

                break;
        }

    }

    public boolean imageAllowed() {
        boolean result = true;

        if (image.equalsIgnoreCase("true")) {

            if (image1.equalsIgnoreCase("")) {

                result = false;

            }
        }

        return result;

    }

    public boolean textAllowed() {
        boolean result = true;

        //if (reason_reamrk.equalsIgnoreCase("true")) {

      /*  if (text.getText().toString().trim().equals("")) {

            result = false;

        }*/
        //}

        return result;
    }

    public boolean isDataUploaded() {

        jcp = database.getNewStoreListFromJCP(visit_date);
        boolean flag = true;

        if (entry_allow.equals("0")) {

            if (jcp.size() > 0) {
                for (int i = 0; i < jcp.size(); i++) {
                    if (jcp.get(i).getUpload_Status().equalsIgnoreCase(CommonString.KEY_U)) {
                        flag = false;
                        break;
                    }
                }
            }
        }

        return flag;
    }




    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v.getId() == R.id.imgcam) {
            _pathforcheck = store_id + "NonWorking" + _UserId +"_"+visit_date.replace("/","")+"_"+getCurrentTime().replace(":","")+".jpg";

            _path = CommonString.FILE_PATH + _pathforcheck;

            startCameraActivity();
        }
        if (v.getId() == R.id.save) {

            if (validatedata()) {

                if (imageAllowed()) {

                    if (textAllowed()) {

                        if (isDataUploaded()) {

                            AlertDialog.Builder builder = new AlertDialog.Builder(
                                    NonWorkingReason.this);
                            builder.setMessage("Do you want to save the data ")
                                    .setCancelable(false)
                                    .setPositiveButton("OK",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(
                                                        DialogInterface dialog,
                                                        int id) {

                                                    alert.getButton(
                                                            AlertDialog.BUTTON_POSITIVE)
                                                            .setEnabled(false);


                                                    if (entry_allow.equals("0")) {

                                                            database.deleteAllTables();

                                                            for (int i = 0; i < jcp.size(); i++) {

                                                                String stoteid = String.valueOf(jcp.get(i).getStore_id());

                                                                CoverageBean cdata = new CoverageBean();
                                                                cdata.setStoreId(stoteid);
                                                                cdata.setVisitDate(visit_date);
                                                                cdata.setUserId(_UserId);
                                                                cdata.setInTime(intime);
                                                                cdata.setOutTime(getCurrentTime());
                                                                cdata.setReason(reasonname);
                                                                cdata.setReasonid(reasonid);
                                                                cdata.setSub_reasonId(subreasonid);
                                                                cdata.setInformto(informTo.getText().toString());
                                                                cdata.setLatitude("0.0");
                                                                cdata.setLongitude("0.0");
                                                                cdata.setImage(image1);

                                                                cdata.setRemark(text
                                                                        .getText()
                                                                        .toString()
                                                                        .replaceAll(
                                                                                "[&^<>{}'$]",
                                                                                " "));
                                                                cdata.setStatus(CommonString.STORE_STATUS_LEAVE);

                                                                database.InsertCoverageData(cdata);

                                                                database.updateStoreStatus(
                                                                        stoteid,
                                                                        visit_date,
                                                                        CommonString.STORE_STATUS_LEAVE);

                                                                SharedPreferences.Editor editor = preferences
                                                                        .edit();

                                                                editor.putString(CommonString.KEY_STOREVISITED_STATUS + stoteid, "No");
                                                                editor.putString(
                                                                        CommonString.KEY_STOREVISITED_STATUS,
                                                                        "");
                                                                editor.putString(
                                                                        CommonString.KEY_STORE_IN_TIME,
                                                                        "");
                                                                editor.putString(
                                                                        CommonString.KEY_LATITUDE,
                                                                        "");
                                                                editor.putString(
                                                                        CommonString.KEY_LONGITUDE,
                                                                        "");
                                                                editor.commit();

                                                                finish();

                                                            }

                                                    } else {

                                                        CoverageBean cdata = new CoverageBean();
                                                        cdata.setStoreId(store_id);
                                                        cdata.setVisitDate(visit_date);
                                                        cdata.setUserId(_UserId);
                                                        cdata.setInTime(intime);
                                                        cdata.setOutTime(getCurrentTime());
                                                        cdata.setReason(reasonname);
                                                        cdata.setReasonid(reasonid);
                                                        cdata.setSub_reasonId(subreasonid);
                                                        cdata.setInformto(informTo.getText().toString());
                                                        cdata.setLatitude("0.0");
                                                        cdata.setLongitude("0.0");
                                                        cdata.setImage(image1);

                                                        cdata.setRemark(text
                                                                .getText()
                                                                .toString()
                                                                .replaceAll(
                                                                        "[&^<>{}'$]",
                                                                        " "));
                                                        cdata.setStatus(CommonString.STORE_STATUS_LEAVE);

                                                        database.InsertCoverageData(cdata);

                                                        database.updateStoreStatus(
                                                                store_id,
                                                                visit_date,
                                                                CommonString.STORE_STATUS_LEAVE);

                                                        SharedPreferences.Editor editor = preferences
                                                                .edit();

                                                        editor.putString(CommonString.KEY_STOREVISITED_STATUS + store_id, "No");
                                                        editor.putString(
                                                                CommonString.KEY_STOREVISITED_STATUS,
                                                                "");
                                                        editor.putString(
                                                                CommonString.KEY_STORE_IN_TIME,
                                                                "");
                                                        editor.putString(
                                                                CommonString.KEY_LATITUDE,
                                                                "");
                                                        editor.putString(
                                                                CommonString.KEY_LONGITUDE,
                                                                "");
                                                        editor.commit();

                                                        finish();

                                                    }


//												database.updateStoreStatusOnCheckout(
//														store_id, visit_date,
//														CommonString1.KEY_L);

												/*Intent intent = new Intent(
                                                        getApplicationContext(),
														DailyEntryScreen.class);
												startActivity(intent);*/
                                                    // finish();
                                                }
                                            })
                                    .setNegativeButton("Cancel",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(
                                                        DialogInterface dialog,
                                                        int id) {
                                                    dialog.cancel();
                                                }
                                            });

                            alert = builder.create();
                            alert.show();
                        } else {
                            Toast.makeText(NonWorkingReason.this, "Data has been uploaded for some" +
                                    " stores, please select another reason", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Please enter required remark reason",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please Capture Image", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(),
                        "Please Select a Reason and Fill Inform To", Toast.LENGTH_SHORT).show();

            }
        }

    }

    public boolean validatedata() {
        boolean result = false;
        if (reasonid != null && !reasonid.equalsIgnoreCase("")) {
            result = true;
        }
        if (informTo.getText().toString().equalsIgnoreCase("") || informTo.getText().toString() == null) {
            result = false;
        }

       /* if (reasonid.equalsIgnoreCase("7") && merNotAllowedSpinner.getSelectedItemPosition() == 0) {
            result = false;
        }*/

        return result;

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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            // NavUtils.navigateUpFromSameTask(this);
            finish();
            overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
        }
        return super.onOptionsItemSelected(item);
    }
}
