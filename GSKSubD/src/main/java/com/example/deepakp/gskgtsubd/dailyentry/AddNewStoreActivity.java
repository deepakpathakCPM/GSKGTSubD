package com.example.deepakp.gskgtsubd.dailyentry;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.deepakp.gskgtsubd.R;
import com.example.deepakp.gskgtsubd.constants.CommonString;
import com.example.deepakp.gskgtsubd.database.Database;
import com.example.deepakp.gskgtsubd.gettersetter.AddNewStoreGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.AnswerMasterGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.CityMasterGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.MappingUsrGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.StateMasterGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.StoreTypeMasterGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.TownMasterGetterSetter;
import com.example.deepakp.gskgtsubd.utilities.AlertAndMessages;
import com.example.deepakp.gskgtsubd.utilities.CommonFunctions;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * Created by gagang on 21-10-2016.
 */

public class AddNewStoreActivity extends AppCompatActivity {
    Toolbar toolbar;

    EditText ed_StoreName, ed_RetailerName, ed_Landmark, ed_Address, ed_Phone, ed_subdistributername;
    Spinner sp_City, sp_storeFormat, retailerSmartphone, sp_State, sp_town, spn_usr;
    ImageView img_AddNewStoreCamera;
    TextView dob_txt, doa_txt, global_tv;
    Button dob_btn, doa_btn;
    FloatingActionButton fab_save;
    private SharedPreferences preferences;
    Database db;
    int city_cd, storeType_cd, town_cd, usr_cd;
    ArrayAdapter<String> cityAdapter, stateAdapter, storeTypeAdapter, answerAdapter, townAdapter, usrAdapter;

    AddNewStoreGetterSetter addNewStoreGetterSetter;

    String visit_date, username;
    String imageurl, state_cd, image = "";
    String path = "";
    Activity activity;
    int mid = 0, answercd;
    protected boolean _taken;
    Context context;
    private int year;
    private int month;
    private int day;

    ArrayList<CityMasterGetterSetter> cityList = new ArrayList<>();
    ArrayList<TownMasterGetterSetter> townList = new ArrayList<>();
    ArrayList<MappingUsrGetterSetter> usrList = new ArrayList<>();
    ArrayList<StateMasterGetterSetter> stateList;
    ArrayList<StoreTypeMasterGetterSetter> storeTypeList;
    ArrayList<AnswerMasterGetterSetter> answerList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_store);
        context = this;
        activity = this;
        _taken = false;
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Store List");

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        declaration();

        addNewStoreGetterSetter = new AddNewStoreGetterSetter();

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        //store_cd = preferences.getString(CommonString.KEY_STORE_CD, null);
        username = preferences.getString(CommonString.KEY_USERNAME, "");
        visit_date = preferences.getString(CommonString.KEY_DATE, null);

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        //app_ver = preferences.getString(CommonString.KEY_VERSION, "");

        db = new Database(this);
        db.open();

        stateList = db.getStateMasterList();
        storeTypeList = db.getStoreTypeMasterList();
        answerList = db.getAnswerMasterList(false);
        usrList = db.getMappingUsrList();

        //------------for state Master List---------------
        stateAdapter = new ArrayAdapter<>(AddNewStoreActivity.this, android.R.layout.simple_spinner_item);
        for (int i = 0; i < stateList.size(); i++) {
            stateAdapter.add(stateList.get(i).getSTATE().get(0));
        }
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_State.setAdapter(stateAdapter);
        //------------------------------------------------

        //------------for city Master List---------------
        cityAdapter = new ArrayAdapter<>(AddNewStoreActivity.this, android.R.layout.simple_spinner_item);
      /*  for (int i = 0; i < cityList.size(); i++) {
            cityAdapter.add(cityList.get(i).getCITY().get(0));
        }*/
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //sp_City.setAdapter(cityAdapter);
        //------------------------------------------------

        //------------for city Master List---------------
        townAdapter = new ArrayAdapter<>(AddNewStoreActivity.this, android.R.layout.simple_spinner_item);
      /*  for (int i = 0; i < cityList.size(); i++) {
            cityAdapter.add(cityList.get(i).getCITY().get(0));
        }*/
        townAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //sp_City.setAdapter(cityAdapter);
        //------------------------------------------------

        //------------for Store Type List---------------
        storeTypeAdapter = new ArrayAdapter<>(AddNewStoreActivity.this, android.R.layout.simple_spinner_item);
        for (int i = 0; i < storeTypeList.size(); i++) {
            storeTypeAdapter.add(storeTypeList.get(i).getSTORETYPE().get(0));
        }
        storeTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_storeFormat.setAdapter(storeTypeAdapter);
        //------------------------------------------------

        //------------for Answer List---------------
        answerAdapter = new ArrayAdapter<>(AddNewStoreActivity.this, android.R.layout.simple_spinner_item);
        for (int i = 0; i < answerList.size(); i++) {
            answerAdapter.add(answerList.get(i).getANSWER().get(0));
        }
        answerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        retailerSmartphone.setAdapter(answerAdapter);
        //------------------------------------------------

        //------------for USR List---------------
        usrAdapter = new ArrayAdapter<>(AddNewStoreActivity.this, android.R.layout.simple_spinner_item);
        for (int i = 0; i < usrList.size(); i++) {
            usrAdapter.add(usrList.get(i).getUSR().get(0));
        }
        usrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_usr.setAdapter(usrAdapter);
        //------------------------------------------------


        sp_City.setOnItemSelectedListener(onItemSelectedListener);
        sp_storeFormat.setOnItemSelectedListener(onItemSelectedListener);
        sp_State.setOnItemSelectedListener(onItemSelectedListener);
        sp_town.setOnItemSelectedListener(onItemSelectedListener);
        spn_usr.setOnItemSelectedListener(onItemSelectedListener);

        img_AddNewStoreCamera.setOnClickListener(onClickListener);
        dob_btn.setOnClickListener(onClickListener);
        doa_btn.setOnClickListener(onClickListener);


       /* if (!addNewStoreGetterSetter.getImg_Camera().equals("")) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4;

            Bitmap bitmap = BitmapFactory.decodeFile(addNewStoreGetterSetter.getImg_Camera().toString(), options);
            img_AddNewStoreCamera.setImageBitmap(bitmap);
        } else {
            img_AddNewStoreCamera.setImageDrawable(getResources().getDrawable(R.drawable.camera_icon));
        }*/
        //Save data into database
        fab_save.setOnClickListener(onClickListener);
        //Update data into database
        // fab_update.setOnClickListener(onClickListener);
    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.fab_save:
                    try {
                        if (validate(addNewStoreGetterSetter)) {

                            addNewStoreGetterSetter.setEd_StoreName(ed_StoreName.getText().toString().replaceAll("[&^<>{}'$]"," "));
                            addNewStoreGetterSetter.setEd_RetailerName(ed_RetailerName.getText().toString().replaceAll("[&^<>{}'$]"," "));
                            addNewStoreGetterSetter.setUsr_cd(usr_cd);
                            addNewStoreGetterSetter.setUsr(spn_usr.getSelectedItem().toString());
                            addNewStoreGetterSetter.setEd_Address(ed_Address.getText().toString().replaceAll("[&^<>{}'$]"," "));
                            addNewStoreGetterSetter.setEd_Landmark(ed_Landmark.getText().toString().replaceAll("[&^<>{}'$]"," "));
                            addNewStoreGetterSetter.setCity_CD(city_cd);
                            addNewStoreGetterSetter.setCity(sp_City.getSelectedItem().toString());
                            addNewStoreGetterSetter.setTown_CD((town_cd));
                            addNewStoreGetterSetter.setTown(sp_town.getSelectedItem().toString());
                            addNewStoreGetterSetter.setState_cd(Integer.parseInt(state_cd));
                            addNewStoreGetterSetter.setState(sp_State.getSelectedItem().toString());
                            addNewStoreGetterSetter.setEd_Phone(ed_Phone.getText().toString());

                            addNewStoreGetterSetter.setDob(dob_txt.getText().toString());
                            addNewStoreGetterSetter.setDoa(doa_txt.getText().toString());
                            addNewStoreGetterSetter.setSmartphoneAnswer_cd(answercd);
                            addNewStoreGetterSetter.setHasSmartphone(retailerSmartphone.getSelectedItem().toString());
                            addNewStoreGetterSetter.setSubDistributerName(ed_subdistributername.getText().toString());

                            addNewStoreGetterSetter.setStoreType_CD(storeType_cd);
                            addNewStoreGetterSetter.setStoreType(sp_storeFormat.getSelectedItem().toString());
                            addNewStoreGetterSetter.setUpload_Status("N");

                            int store_id = db.saveAddNewStoreData(addNewStoreGetterSetter, visit_date);

                            if (store_id > 0) {
                                setResult(1, new Intent(AddNewStoreActivity.this, AddNewStoreListActivity.class).putExtra("store_id", store_id));
                                AlertAndMessages.showToastMessage(context, "Store Details Saved Successfully");
                            }


                            finish();
                            overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    break;

                case R.id.img_AddNewStoreCamera:
                    String TempDate = visit_date.replace("/", "");
                    imageurl = mid + TempDate + ".jpg";
                    path = Environment.getExternalStorageDirectory() + "/GSKGTSUBD_Image/" + "DealerBoard" + imageurl;
                    CommonFunctions.startCameraActivity(activity, path);
                    break;
                case R.id.btn_dob:
                    showDatePickerDialog(dob_txt);
                    break;
                case R.id.btn_doa:
                    showDatePickerDialog(doa_txt);
                    break;
            }

        }
    };


    AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            switch (parent.getId()) {
                case R.id.sp_City:
                    city_cd = Integer.parseInt(cityList.get(position).getCITY_CD().get(0));
                    townList.clear();
                    townAdapter.clear();
                    townList = db.getTownMasterList(String.valueOf(city_cd));
                    //------------for city Master List---------------
                    for (int i = 0; i < townList.size(); i++) {
                        townAdapter.add(townList.get(i).getTOWN().get(0));
                    }

                    sp_town.setAdapter(townAdapter);
                    //------------------------------------------------

                    break;
                case R.id.sp_State:
                    state_cd = stateList.get(position).getSTATE_CD().get(0);
                    cityList.clear();
                    cityAdapter.clear();
                    cityList = db.getCityMasterList(state_cd);
                    //------------for city Master List---------------
                    for (int i = 0; i < cityList.size(); i++) {
                        cityAdapter.add(cityList.get(i).getCITY().get(0));
                    }

                    sp_City.setAdapter(cityAdapter);
                    //------------------------------------------------
                    break;
                case R.id.storeformat_spinner:
                    storeType_cd = Integer.parseInt(storeTypeList.get(position).getSTORETYPE_CD().get(0));
                    break;
                case R.id.sp_town:
                    town_cd = Integer.parseInt(townList.get(position).getTOWN_CD().get(0));
                    break;
                case R.id.answer_spinner:
                    answercd = Integer.parseInt(answerList.get(position).getANSWER_CD().get(0));
                    break;
                case R.id.spn_usr:
                    usr_cd = Integer.parseInt(usrList.get(position).getUSR_CD().get(0));
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };


    boolean validate(AddNewStoreGetterSetter addNewStoreGetterSetter) {
        boolean flag = false;

        if (ed_StoreName.getText().toString().equalsIgnoreCase("")
                || ed_StoreName.getText() == null) {
            flag = false;
            AlertAndMessages.showAlertMessage(context, "Please fill store name");
        }
        /*else if (ed_RetailerName.getText().toString().equalsIgnoreCase("")
                || ed_RetailerName.getText() == null) {
            flag = false;
            AlertAndMessages.showAlertMessage(context, "Please fill retailer name");
        }*/
       /* else if (spn_usr.getSelectedItemPosition() == 0 || usr_cd == -1) {
            flag = false;
            AlertAndMessages.showAlertMessage(context, "Please select USR");
        } */
       /* else if (ed_Address.getText().toString().equalsIgnoreCase("")
                || ed_Address.getText() == null) {
            flag = false;
            AlertAndMessages.showAlertMessage(context, "Please fill Address");
        } */
        /*else if (ed_Landmark.getText().toString().equalsIgnoreCase("")
                || ed_Landmark.getText() == null) {
            flag = false;
            AlertAndMessages.showAlertMessage(context, "Please fill Landmark");
        } */
        else if (sp_State.getSelectedItemPosition() == 0) {
            AlertAndMessages.showToastMessage(context, "Please Select state");
            flag = false;
        }
        else if (sp_City.getSelectedItemPosition() == 0) {
            AlertAndMessages.showToastMessage(context, "Please Select Super Town");
            flag = false;
        }
        else if (sp_town.getSelectedItemPosition() == 0) {
            AlertAndMessages.showToastMessage(context, "Please Select Sub Town");
            flag = false;
        } else if (ed_Phone.getText().toString().equalsIgnoreCase("") || ed_Phone.getText().toString().length() !=10) {
            flag = false;
            AlertAndMessages.showAlertMessage(context, "Please fill the 10 digit phone number...");
        }
      /*  else if (dob_txt.getText().toString().equalsIgnoreCase("")) {
            flag = false;
            AlertAndMessages.showAlertMessage(context, "Please select date of birth");
        } else if (doa_txt.getText().toString().equalsIgnoreCase("")) {
            flag = false;
            AlertAndMessages.showAlertMessage(context, "Please select date of anniversary");
        } else if (retailerSmartphone.getSelectedItemPosition() == 0 || answercd == -1) {
            flag = false;
            AlertAndMessages.showAlertMessage(context, "Please select Yes or No in retailer has smartphone");
        } else if (ed_subdistributername.getText().toString().equalsIgnoreCase("")) {
            flag = false;
            AlertAndMessages.showAlertMessage(context, "Please select Sub-distributer name");
        }
        else if (_taken == false) {

            AlertAndMessages.showToastMessage(getApplicationContext(), "Please Click Store Image");
            flag = false;
        } */
        else if (sp_storeFormat.getSelectedItemPosition() == 0) {
            AlertAndMessages.showToastMessage(context, "Please Select Channel");
            flag = false;
        }
        else {
            flag = true;
        }

        return flag;
    }

    private void declaration() {
        ed_StoreName = (EditText) findViewById(R.id.ed_storeName);
        ed_RetailerName = (EditText) findViewById(R.id.ed_RetailerName);
        ed_Address = (EditText) findViewById(R.id.ed_Address);
        ed_Landmark = (EditText) findViewById(R.id.ed_Landmark);
        sp_City = (Spinner) findViewById(R.id.sp_City);
        sp_State = (Spinner) findViewById(R.id.sp_State);
        sp_town = (Spinner) findViewById(R.id.sp_town);
        ed_Phone = (EditText) findViewById(R.id.ed_Phone);

        dob_txt = (TextView) findViewById(R.id.txt_DOB);
        doa_txt = (TextView) findViewById(R.id.txt_DOA);
        dob_btn = (Button) findViewById(R.id.btn_dob);
        doa_btn = (Button) findViewById(R.id.btn_doa);
        retailerSmartphone = (Spinner) findViewById(R.id.spinner_smartphone);
        spn_usr = (Spinner) findViewById(R.id.spn_usr);
        ed_subdistributername = (EditText) findViewById(R.id.ed_subdistributername);
        sp_storeFormat = (Spinner) findViewById(R.id.storeformat_spinner);
        img_AddNewStoreCamera = (ImageView) findViewById(R.id.img_AddNewStoreCamera);
        fab_save = (FloatingActionButton) findViewById(R.id.fab_save);
        dob_txt.setText("0-0");
        doa_txt.setText("0-0");
    }

    public class Status {
        int id;
        String name;

        public Status(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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
        if (requestCode == 1) {
            switch (resultCode) {
                case 0:
                    _taken = false;
                    Log.e("OpenCameraActivity", "User cancelled");
                    break;
                case -1:
                    onPhotoTaken();
                    break;
            }
        }
    }

    protected void showDatePickerDialog(TextView textView) {
        global_tv = textView;
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, pickerListener, year, month, day);
        // ((ViewGroup) datePickerDialog.getDatePicker()).findViewById(Resources.getSystem().getIdentifier("year", "id", "android")).setVisibility(View.GONE);
        //datePickerDialog.findViewById(Resources.getSystem().getIdentifier("year", "id", "android")).setVisibility(View.GONE);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.setTitle("");
        datePickerDialog.show();
    }

    protected void onPhotoTaken() {
        Log.e("OpenCameraActivity", "onPhotoTaken");
        _taken = true;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;
        image = imageurl;
        addNewStoreGetterSetter.setImg_Camera(image);

        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        img_AddNewStoreCamera.setImageBitmap(bitmap);
        image = "";
    }

    private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            year = selectedYear;
            month = selectedMonth + 1;
            day = selectedDay;

            String day_str = String.valueOf(day);
            day_str = "00" + day_str;
            day_str = day_str.substring(day_str.length() - 2, day_str.length());


            String month_str = String.valueOf(month);
            month_str = "00" + month_str;
            month_str = month_str.substring(month_str.length() - 2, month_str.length());


         /*   global_tv.setText(new StringBuilder().append(month + 1)
                    .append("-").append(day).append("-").append(year)
                    .append(" "));*/

            global_tv.setText(new StringBuilder().append(day_str).append("-").append(month_str)
            );


        }
    };
}
