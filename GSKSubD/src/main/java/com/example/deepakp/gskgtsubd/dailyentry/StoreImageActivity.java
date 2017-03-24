package com.example.deepakp.gskgtsubd.dailyentry;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.deepakp.gskgtsubd.R;
import com.example.deepakp.gskgtsubd.bean.CoverageBean;
import com.example.deepakp.gskgtsubd.constants.CommonString;
import com.example.deepakp.gskgtsubd.database.Database;
import com.example.deepakp.gskgtsubd.gettersetter.AddNewStoreGetterSetter;
import com.example.deepakp.gskgtsubd.utilities.CommonFunctions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.File;
import java.util.ArrayList;

import static com.example.deepakp.gskgtsubd.utilities.CommonFunctions.getCurrentTime;

public class StoreImageActivity extends AppCompatActivity implements View.OnClickListener,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    ImageView img_cam,img_clicked;
    Button btn_save;
    Toolbar toolbar;
    SharedPreferences preferences;
    Database database;
    String store_cd,KEY_ID,date,username,intime,str,_pathforcheck,_path,img_str,state_cd,storetype_cd;
    ArrayList<CoverageBean> coverage_list;
    AlertDialog alert;
    Activity activity;
    GoogleApiClient mGoogleApiClient;
    String lat ="0.0",lon = "0.0";
    AddNewStoreGetterSetter addNewStoreGetterSetter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_image);
        declaration();

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addNewStoreGetterSetter=(AddNewStoreGetterSetter)getIntent().getSerializableExtra(CommonString.KEY_INTENT);

        coverage_list =  database.getCoverageData(date);

        img_cam.setOnClickListener(this);
        img_clicked.setOnClickListener(this);
        btn_save.setOnClickListener(this);

        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return  ;
        }
    }

    private void declaration()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        img_cam = (ImageView) findViewById(R.id.img_selfie);
        img_clicked = (ImageView) findViewById(R.id.img_cam_selfie);
        btn_save = (Button) findViewById(R.id.btn_save_selfie);
        database = new Database(this);
        database.open();
        activity = this;
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        store_cd = getIntent().getStringExtra(CommonString.KEY_STORE_CD);
        KEY_ID = getIntent().getStringExtra(CommonString.KEY_ID);
        state_cd = getIntent().getStringExtra(CommonString.KEY_STATE_CD);
        storetype_cd = getIntent().getStringExtra(CommonString.KEY_STORE_TYPE_CD);

        date = preferences.getString(CommonString.KEY_DATE, null);
        username = preferences.getString(CommonString.KEY_USERNAME, null);
        intime = preferences.getString(CommonString.KEY_STORE_IN_TIME, "");
        str = CommonString.FILE_PATH;

    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id){

            case R.id.img_cam_selfie:

                _pathforcheck = store_cd + "Store" + "Image" + date.replace("/","") + getCurrentTime().replace(":","")+".jpg";

                _path = CommonString.FILE_PATH + _pathforcheck;
                intime = getCurrentTime();
                CommonFunctions.startCameraActivity(activity, _path);

                break;

            case R.id.btn_save_selfie:

                if (img_str !=null) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(
                            StoreImageActivity.this);
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

                                            CoverageBean cdata = new CoverageBean();
                                            cdata.setStoreId(store_cd);
                                            cdata.setVisitDate(date);
                                            cdata.setUserId(username);
                                            cdata.setInTime(intime);
                                            cdata.setOutTime("0");
                                            cdata.setReason("");
                                            cdata.setReasonid("0");
                                            cdata.setLatitude(lat);
                                            cdata.setLongitude(lon);
                                            cdata.setImage(img_str);
                                            cdata.setRemark("");
                                            cdata.setStatus(CommonString.KEY_CHECK_IN);

                                            long coverage_id= database.InsertCoverageData(cdata);

                                            if(store_cd.equalsIgnoreCase("0"))
                                            {
                                                database.updateCoverageIDatStoreInfo(coverage_id, Long.parseLong(KEY_ID));
                                            }

                                            /*SharedPreferences.Editor editor = preferences
                                                    .edit();
                                            editor.putString(CommonString.KEY_STOREVISITED_STATUS, "");
                                            editor.putString(CommonString.KEY_STORE_IN_TIME, "");
                                            editor.putString(CommonString.KEY_LATITUDE, "");
                                            editor.putString(CommonString.KEY_LONGITUDE, "");
                                            editor.commit();*/

                                            Intent in=new Intent(StoreImageActivity.this,MenuActivity.class)
                                                    .putExtra(CommonString.KEY_STORE_CD,store_cd)
                                                    .putExtra(CommonString.KEY_COVERAGE_ID,String.valueOf(coverage_id))
                                                    .putExtra(CommonString.KEY_STATE_CD,state_cd)
                                                    .putExtra(CommonString.KEY_STORE_TYPE_CD,storetype_cd);
                                            startActivity(in);

                                            finish();
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

                }
                else {
                    Toast.makeText(getApplicationContext(),
                            "Please click the image", Toast.LENGTH_SHORT).show();

                }

                break;

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
        }
        return super.onOptionsItemSelected(item);
    }

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

                        Bitmap bmp = BitmapFactory.decodeFile(str + _pathforcheck);

                        img_cam.setImageBitmap(bmp);
                        img_clicked.setVisibility(View.GONE);
                        img_cam.setVisibility(View.VISIBLE);

                        img_str = _pathforcheck;
                        _pathforcheck = "";
                    }
                }

                break;
        }


        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            lat = String.valueOf(mLastLocation.getLatitude());
            lon = String.valueOf(mLastLocation.getLongitude());
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }
}
