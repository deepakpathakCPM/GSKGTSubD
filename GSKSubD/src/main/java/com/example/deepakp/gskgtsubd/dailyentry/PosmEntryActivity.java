package com.example.deepakp.gskgtsubd.dailyentry;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.deepakp.gskgtsubd.R;
import com.example.deepakp.gskgtsubd.constants.CommonString;
import com.example.deepakp.gskgtsubd.database.Database;
import com.example.deepakp.gskgtsubd.gettersetter.BrandMasterGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.PosmEntryGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.PosmMasterGetterSetter;
import com.example.deepakp.gskgtsubd.utilities.AlertAndMessages;

import java.io.File;
import java.util.ArrayList;

import static com.example.deepakp.gskgtsubd.utilities.CommonFunctions.getCurrentTime;
import static com.example.deepakp.gskgtsubd.utilities.CommonFunctions.startCameraActivity;

public class PosmEntryActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    Spinner sp_brand, sp_posm;
    Toolbar toolbar;
    FloatingActionButton fab_save, fab_add;
    RecyclerView recyclerView;
    ImageButton btnimg_camera;
    EditText edt_quantity;
    Database db;
    String brand_cd, posm_cd;
    Context context;
    Activity activity;
    String store_cd, _pathforcheck = "", _path = "", image = "", flag_operation = "save",coverage_id;
    int editPosition;
    private SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String state_cd, username, visit_date,storetype_cd;
    ArrayList<BrandMasterGetterSetter> brandList;
    ArrayList<PosmMasterGetterSetter> posmList;
    ArrayAdapter<String> brandAdapter, posmAdapter;
    PosmEntryGetterSetter posmEntryGetterSetter;
    ArrayList<PosmEntryGetterSetter> recyclerList = new ArrayList<PosmEntryGetterSetter>();
    ValueAdapter adapter;
    Long removedID = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posm_entry);
        setDeclaration();
        fab_add.setTag("save");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("POSM");

        brandList = db.getBrandMasterList(state_cd,storetype_cd);
        //------------for Brand Master List---------------
        brandAdapter = new ArrayAdapter<>(PosmEntryActivity.this, android.R.layout.simple_spinner_item);
        for (int i = 0; i < brandList.size(); i++) {
            brandAdapter.add(brandList.get(i).getBRAND().get(0));
        }
        brandAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_brand.setAdapter(brandAdapter);
        //------------------------------------------------

        //------------for Posm Type List---------------
        posmAdapter = new ArrayAdapter<>(PosmEntryActivity.this, android.R.layout.simple_spinner_item);
        posmAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //------------------------------------------------

        recyclerList = db.getPosmEntryData(store_cd,coverage_id);

        if (recyclerList.size() > 0) {
            adapter = new ValueAdapter(getApplicationContext(), recyclerList);
            adapter.notifyDataSetChanged();
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(adapter);

        }

        sp_brand.setOnItemSelectedListener(this);
        sp_posm.setOnItemSelectedListener(this);

        fab_add.setOnClickListener(this);
        fab_save.setOnClickListener(this);
        btnimg_camera.setOnClickListener(this);

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

    public void setDeclaration() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        sp_brand = (Spinner) findViewById(R.id.sp_brand);
        sp_posm = (Spinner) findViewById(R.id.sp_posm);
        edt_quantity = (EditText) findViewById(R.id.edt_quantity);
        fab_add = (FloatingActionButton) findViewById(R.id.fab_add);
        fab_save = (FloatingActionButton) findViewById(R.id.fab_save);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        btnimg_camera = (ImageButton) findViewById(R.id.btnimg_camera);
        activity = this;
        db = new Database(this);
        db.open();
        context = this;
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        store_cd = getIntent().getStringExtra(CommonString.KEY_STORE_CD);
        coverage_id = getIntent().getStringExtra(CommonString.KEY_COVERAGE_ID);
        state_cd = getIntent().getStringExtra(CommonString.KEY_STATE_CD);
        storetype_cd = getIntent().getStringExtra(CommonString.KEY_STORE_TYPE_CD);
        username = preferences.getString(CommonString.KEY_USERNAME, "");
        visit_date = preferences.getString(CommonString.KEY_DATE, null);
        brandList = new ArrayList<BrandMasterGetterSetter>();
        posmList = new ArrayList<PosmMasterGetterSetter>();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnimg_camera:
                _pathforcheck = store_cd + "POSM"
                        + "Image" + visit_date.replace("/", "") + getCurrentTime().replace(":", "") + ".jpg";

                _path = CommonString.FILE_PATH + _pathforcheck;

                startCameraActivity(activity, _path);

                break;
            case R.id.fab_add:
                if (validate()) {

                    posmEntryGetterSetter = new PosmEntryGetterSetter();

                    posmEntryGetterSetter.setBrand_cd(brand_cd);
                    posmEntryGetterSetter.setBrand(sp_brand.getSelectedItem().toString());
                    posmEntryGetterSetter.setPosm_cd(posm_cd);
                    posmEntryGetterSetter.setPosm(sp_posm.getSelectedItem().toString());
                    posmEntryGetterSetter.setQuantity(edt_quantity.getText().toString());
                    posmEntryGetterSetter.setImage(image);

                    if (flag_operation.equalsIgnoreCase("save") || flag_operation.equalsIgnoreCase("delete")) {
                        recyclerList.add(posmEntryGetterSetter);
                        flag_operation = "save";
                    } else if (flag_operation.equalsIgnoreCase("edit")) {
                        recyclerList.remove(editPosition);
                        recyclerList.add(editPosition, posmEntryGetterSetter);
                        fab_add.setImageResource(R.drawable.fab_add);
                        fab_add.setTag("save");
                    }

                    adapter = new ValueAdapter(context, recyclerList);
                    adapter.notifyDataSetChanged();
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    recyclerView.setAdapter(adapter);
                    clearFields();
                }
                break;
            case R.id.fab_save:
                if (recyclerList.size() > 0) {
                    if (flag_operation.equalsIgnoreCase("edit")) {

                        if (fab_add.getTag().toString().equalsIgnoreCase("edit")) {
                            AlertAndMessages.showToastMessage(context, "Please add the data first");
                        } else {
                            long id = db.insertPosmEntryData(recyclerList, store_cd,coverage_id);
                            if (id > 0) {
                                AlertAndMessages.showToastMessage(context, "Data has been saved");
                                finish();
                            } else {
                                AlertAndMessages.showToastMessage(context, "Data not saved");
                                finish();
                            }
                        }
                    } else if (flag_operation.equalsIgnoreCase("save")) {
                        if (fab_add.getTag().toString().equalsIgnoreCase("save")) {
                            db.insertPosmEntryData(recyclerList, store_cd,coverage_id);
                            AlertAndMessages.showToastMessage(context, "Data has been saved");
                            finish();
                        }
                    } else if (flag_operation.equalsIgnoreCase("delete") && removedID > 0L) {
                        db.deletePosmEntryData(removedID);
                        AlertAndMessages.showToastMessage(context, "Data has been deleted");
                        removedID = 0L;
                        flag_operation = "save";
                        finish();
                    }
                } else {
                    if (flag_operation.equalsIgnoreCase("delete") && recyclerList.size() == 0 && removedID > 0L) {
                        db.deletePosmEntryData(removedID);
                        AlertAndMessages.showToastMessage(context, "Data has been deleted");
                        removedID = 0L;
                        flag_operation = "save";
                        finish();
                    } else {
                        AlertAndMessages.showToastMessage(context, "please add the data first");
                    }

                }
                break;
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (parent.getId()) {
            case R.id.sp_brand:
                brand_cd = brandList.get(position).getBRAND_CD().get(0);
                posmList.clear();
                posmAdapter.clear();
                posmList = db.getPosmMasterList(state_cd,storetype_cd,brand_cd);
                //------------for POSM List---------------
                for (int i = 0; i < posmList.size(); i++) {
                    posmAdapter.add(posmList.get(i).getPOSM().get(0));
                }
                sp_posm.setAdapter(posmAdapter);
                //------------------------------------------------
                break;
            case R.id.sp_posm:
                posm_cd = posmList.get(position).getPOSM_CD().get(0);
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
                    if (new File(CommonString.FILE_PATH + _pathforcheck).exists()) {
                        //cam.setBackgroundResource(R.drawable.camera_list_tick);
                        btnimg_camera.setBackgroundResource(R.drawable.camera_green);
                        image = _pathforcheck;
                    }
                }

                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        if (recyclerList.size() > 0) {
            AlertAndMessages.backpressedAlert(activity);
        } else {
            finish();
            overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
        }
    }

    private boolean validate() {
        boolean isgood = true;
        if (sp_brand.getSelectedItemPosition() == 0) {
            isgood = false;
            AlertAndMessages.showToastMessage(context, "Please select brand");
        } else if (sp_posm.getSelectedItemPosition() == 0) {
            isgood = false;
            AlertAndMessages.showToastMessage(context, "Please select posm");
        } else if (edt_quantity.getText().toString().equalsIgnoreCase("")) {
            isgood = false;
            AlertAndMessages.showToastMessage(context, "Please fill quantity");
        } else if (!(edt_quantity.getText().toString().equalsIgnoreCase(""))
                && (Integer.parseInt(edt_quantity.getText().toString())>0))
        {
            if(image == "")
            {
                isgood = false;
                AlertAndMessages.showToastMessage(context, "Please click image");
            }

        }
        return isgood;
    }


    private class ValueAdapter extends RecyclerView.Adapter<ValueAdapter.MyViewHolder> {
        private LayoutInflater inflator;
        ArrayList<PosmEntryGetterSetter> data = new ArrayList<PosmEntryGetterSetter>();

        public ValueAdapter(Context context, ArrayList<PosmEntryGetterSetter> data) {
            inflator = LayoutInflater.from(context);
            this.data = data;
        }

        @Override
        public ValueAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {
            View view = inflator.inflate(R.layout.itemlist_posm_entry, parent, false);
            ValueAdapter.MyViewHolder holder = new ValueAdapter.MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(ValueAdapter.MyViewHolder viewHolder, final int position) {

            viewHolder.brandtxt.setText(data.get(position).getBrand());
            viewHolder.posmtxt.setText(data.get(position).getPosm());
            viewHolder.quantitytxt.setText(data.get(position).getQuantity());

            viewHolder.edit.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            PopupMenu popup = new PopupMenu(context, v);
                            //Inflating the Popup using xml file
                            popup.getMenuInflater().inflate(R.menu.poupup_menu, popup.getMenu());
                            //registering popup with OnMenuItemClickListener
                            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                public boolean onMenuItemClick(MenuItem item) {
                                    switch (item.getItemId()) {
                                        case R.id.edit:
                                            Runnable editTask = new Runnable() {
                                                @Override
                                                public void run() {

                                                    for (int i = 0; i < brandList.size(); i++) {
                                                        if (brandList.get(i).getBRAND_CD().get(0).equalsIgnoreCase(recyclerList.get(position).getBrand_cd())) {
                                                            sp_brand.setSelection(i);
                                                            break;
                                                        }
                                                    }
                                                    for (int i = 0; i < posmList.size(); i++) {
                                                        if (posmList.get(i).getPOSM_CD().get(0).equalsIgnoreCase(recyclerList.get(position).getPosm_cd())) {
                                                            sp_posm.setSelection(i);
                                                            break;
                                                        }
                                                    }

                                                    if (!recyclerList.get(position).getQuantity().equalsIgnoreCase("")) {
                                                        edt_quantity.setText(recyclerList.get(position).getQuantity());
                                                    }

                                                    if (!recyclerList.get(position).getImage().equalsIgnoreCase("")) {
                                                        btnimg_camera.setBackgroundResource(R.drawable.camera_green);
                                                        image = recyclerList.get(position).getImage();
                                                    }

                                                    fab_add.setImageResource(R.drawable.editimage);
                                                    fab_add.setTag("edit");
                                                    flag_operation = "edit";
                                                    editPosition = position;
                                                }
                                            };
                                            AlertAndMessages.editorDeleteAlert(activity, "Do you want to edit?", editTask);

                                            break;
                                        case R.id.delete:
                                            Runnable deleteTask = new Runnable() {
                                                @Override
                                                public void run() {
                                                    flag_operation = "delete";
                                                    if (recyclerList.get(position).getId() != null) {
                                                        removedID = Long.parseLong(recyclerList.get(position).getId());
                                                    }
                                                    recyclerList.remove(position);
                                                    adapter = new ValueAdapter(getApplicationContext(), recyclerList);
                                                    adapter.notifyDataSetChanged();
                                                    recyclerView.setLayoutManager(new LinearLayoutManager(activity));
                                                    recyclerView.setAdapter(adapter);
                                                }
                                            };
                                            AlertAndMessages.editorDeleteAlert(activity, "Are you sure to delete?", deleteTask);
                                            break;
                                    }
                                    return true;
                                }
                            });

                            popup.show();//showing popup menu

                        }
                    }
            );


        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView brandtxt, posmtxt, quantitytxt;
            ImageView edit;

            public MyViewHolder(View itemView) {
                super(itemView);
                brandtxt = (TextView) itemView.findViewById(R.id.txt_brand);
                posmtxt = (TextView) itemView.findViewById(R.id.txt_posm);
                quantitytxt = (TextView) itemView.findViewById(R.id.txt_quantity);
                edit = (ImageView) itemView.findViewById(R.id.edit);
            }
        }
    }


    private void clearFields() {
        sp_brand.setSelection(0);
        sp_posm.setSelection(0);
        edt_quantity.setText("");
        image = "";
        _pathforcheck = "";
        btnimg_camera.setBackgroundResource(R.drawable.camera_orange);
    }

}
