package com.example.deepakp.gskgtsubd.dailyentry;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.deepakp.gskgtsubd.R;
import com.example.deepakp.gskgtsubd.constants.CommonString;
import com.example.deepakp.gskgtsubd.database.Database;
import com.example.deepakp.gskgtsubd.gettersetter.NonWorkingReasonGetterSetter;
import com.example.deepakp.gskgtsubd.utilities.AlertAndMessages;

import java.util.ArrayList;


public class
MyAttendanceActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner attendance_spn;
    FloatingActionButton fab;
    Database db;
    SharedPreferences preferences;
    Context context;
    ArrayList<NonWorkingReasonGetterSetter> nonWorkingReasonnewList;
    ArrayAdapter<String> spn_adapter;
    ArrayList<String> reasonlist;
    String reason_cd = "0", user_name, visit_date,entry_allow = "0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_attendance);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        declaration();
        prepareList();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {

                    long id = db.saveAttendanceData(user_name, visit_date, reason_cd,entry_allow);
                    if (id > 0) {
                        Intent intent = new Intent(context, CheckoutActivity.class)
                                .putExtra(CommonString.KEY_STORE_CD, reason_cd)
                                .putExtra(CommonString.KEY_FROMSTORE, false);
                        startActivity(intent);
                        finish();
                    } else {
                        AlertAndMessages.showToastMessage(context, "Attendance not Saved");
                    }
                }
            }
        });
    }


    void declaration() {
        attendance_spn = (Spinner) findViewById(R.id.attendance_spn);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        user_name = preferences.getString(CommonString.KEY_USERNAME, null);
        visit_date = preferences.getString(CommonString.KEY_DATE, null);
        context = this;
        db = new Database(context);
        db.open();
    }

    void prepareList() {
        nonWorkingReasonnewList = db.getNonWorkingData(false);
        reasonlist = new ArrayList<>();
        reasonlist.clear();
        if (nonWorkingReasonnewList.size() > 0) {
            reasonlist = new ArrayList<String>();
            for (int i = 0; i < nonWorkingReasonnewList.size(); i++) {
                reasonlist.add(nonWorkingReasonnewList.get(i).getReason().get(0));
            }
        }
        spn_adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, reasonlist);
        attendance_spn.setAdapter(spn_adapter);
        attendance_spn.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (parent.getId()) {
            case R.id.attendance_spn:
                reason_cd = nonWorkingReasonnewList.get(position).getReason_cd().get(0);
                entry_allow = nonWorkingReasonnewList.get(position).getEntry_allow().get(0);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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

    boolean validate() {
        boolean isvalidate = true;
        if (reason_cd.equalsIgnoreCase("0") || reason_cd.equalsIgnoreCase("-1")) {
            AlertAndMessages.showToastMessage(context, "Please select a reason");
            isvalidate = false;
        }
        return isvalidate;
    }
}
