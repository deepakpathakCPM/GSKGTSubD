package com.example.deepakp.gskgtsubd.dailyentry;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.deepakp.gskgtsubd.R;
import com.example.deepakp.gskgtsubd.constants.CommonString;
import com.example.deepakp.gskgtsubd.database.Database;
import com.example.deepakp.gskgtsubd.gettersetter.LineEntryGetterSetter;
import com.example.deepakp.gskgtsubd.utilities.AlertAndMessages;

import java.util.ArrayList;

//Total Line Score
public class LineEntryActivity extends AppCompatActivity implements View.OnClickListener {

    FloatingActionButton fab_save;
    EditText totalLine_edt, buildSkuLine_edt, billAmount_edt;
    Toolbar toolbar;
    Context context;
    Database db;
    TextView totalline_txt;
    LineEntryGetterSetter lineEntryGetterSetter;
    String store_id,coverage_id;
    ArrayList<LineEntryGetterSetter> lineEntryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_entry);
        declaration();
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(store_id.equalsIgnoreCase("0"))
        {
            getSupportActionBar().setTitle("Total Line Sold");
            totalline_txt.setText("Total Line Sold");
        }
        else {
            getSupportActionBar().setTitle("Total Line Available");
            totalline_txt.setText("Total Line Available");
        }

        lineEntryList = db.getLineEntryData(store_id,coverage_id);

        if(lineEntryList.size()>0)
        {
            setSavedData();
        }

        fab_save.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_save:
                if (validate()) {

                    lineEntryGetterSetter = new LineEntryGetterSetter();
                    lineEntryGetterSetter.setTotalLineAvailable(totalLine_edt.getText().toString());
                    lineEntryGetterSetter.setNoOfBuildSkuLine("0");
                    lineEntryGetterSetter.setTotalBillAmount(billAmount_edt.getText().toString());

                    long id = db.insertLineEntryData(lineEntryGetterSetter,store_id,coverage_id);

                    if(id>0)
                    {
                        AlertAndMessages.showToastMessage(context,"Data has been saved");
                        finish();
                    }
                    else
                    {
                        AlertAndMessages.showToastMessage(context,"Data not saved");
                    }

                }
                break;

        }
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



    private boolean validate() {
        boolean isGood = true;

        if (totalLine_edt.getText().toString().equalsIgnoreCase("")) {
            isGood = false;
            AlertAndMessages.showToastMessage(context, "Please type total line available");
        }

        if(store_id.equalsIgnoreCase("0")) {
           /* if (buildSkuLine_edt.getText().toString().equalsIgnoreCase("")) {
                isGood = false;
                AlertAndMessages.showToastMessage(context, "Please type no of build sku line");
            } */
            if (billAmount_edt.getText().toString().equalsIgnoreCase("")) {
                isGood = false;
                AlertAndMessages.showToastMessage(context, "Please type total bill amount");
            }
        }
        return isGood;
    }

    @Override
    public void onBackPressed() {
        AlertAndMessages.backpressedAlert(LineEntryActivity.this);
    }

    private void declaration() {
        totalLine_edt = (EditText) findViewById(R.id.edt_totallineAvailable);
        buildSkuLine_edt = (EditText) findViewById(R.id.edt_noofskuline);
        billAmount_edt = (EditText) findViewById(R.id.edt_totalbillamount);
        totalline_txt = (TextView) findViewById(R.id.totalline_txt);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab_save = (FloatingActionButton) findViewById(R.id.fab_save);
        context = this;
        db = new Database(context);
        db.open();
        store_id = getIntent().getStringExtra(CommonString.KEY_STORE_CD);
        coverage_id = getIntent().getStringExtra(CommonString.KEY_COVERAGE_ID);
    }

    private void setSavedData() {

        for(int i=0;i<lineEntryList.size();i++)
        {
            totalLine_edt.setText(lineEntryList.get(i).getTotalLineAvailable());
            //buildSkuLine_edt.setText(lineEntryList.get(i).getNoOfBuildSkuLine());
            billAmount_edt.setText(lineEntryList.get(i).getTotalBillAmount());
        }

    }

}
