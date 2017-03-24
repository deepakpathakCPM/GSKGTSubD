package com.example.deepakp.gskgtsubd.dailyentry;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.deepakp.gskgtsubd.R;
import com.example.deepakp.gskgtsubd.bean.CoverageBean;
import com.example.deepakp.gskgtsubd.constants.CommonString;
import com.example.deepakp.gskgtsubd.database.Database;
import com.example.deepakp.gskgtsubd.gettersetter.AddNewStoreGetterSetter;
import com.example.deepakp.gskgtsubd.utilities.AlertAndMessages;

import java.util.ArrayList;

public class StoreListActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerview;
    FloatingActionButton fab_save;
    TextView txt_noDataFound;
    private Dialog dialog;
    private SharedPreferences.Editor editor = null;
    public static String currLatitude = "0.0";
    public static String currLongitude = "0.0";
    ArrayList<AddNewStoreGetterSetter> addStoreList = new ArrayList<>();
    String store_intime = "", store_cd = "";
    Database db;
    AddStoreRecyclerAdapter adapter;
    ArrayList<CoverageBean> coverage;

    private SharedPreferences preferences;

    private String visit_date, username, app_ver, key_intent = "";

    int coverage_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_list);

        declaration();
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Store List");


        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        visit_date = preferences.getString(CommonString.KEY_DATE, null);
        username = preferences.getString(CommonString.KEY_USERNAME, null);
        app_ver = preferences.getString(CommonString.KEY_VERSION, "");


        db = new Database(this);
        db.open();

    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
    }

    @Override
    protected void onResume() {
        super.onResume();
        coverage = db.getCoverageData(visit_date);


        addStoreList = db.getNewStoreListFromJCP(visit_date);


        if (addStoreList.size() > 0) {
            txt_noDataFound.setVisibility(View.GONE);
        } else {
            txt_noDataFound.setVisibility(View.VISIBLE);
        }

        adapter = new AddStoreRecyclerAdapter();
        recyclerview.setAdapter(adapter);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));

    }

    public class AddStoreRecyclerAdapter extends RecyclerView.Adapter<AddStoreRecyclerAdapter.MyViewHolder> {
        private LayoutInflater inflator = LayoutInflater.from(getApplicationContext());

        @Override
        public AddStoreRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflator.inflate(R.layout.item_add_store_list_item_view, parent, false);
            AddStoreRecyclerAdapter.MyViewHolder holder = new AddStoreRecyclerAdapter.MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final AddStoreRecyclerAdapter.MyViewHolder holder, final int position) {
            final AddNewStoreGetterSetter as = addStoreList.get(position);
            coverage_id = 0;

            if (coverage.size() > 0) {

                for (int i = 0; i < coverage.size(); i++) {

                    if (as.getStore_id() == Integer.parseInt(coverage.get(i).getStoreId())) {
                        coverage_id = coverage.get(i).getMID();
                        break;
                    }
                }
            }

            if (as.getUpload_Status().equalsIgnoreCase("U")) {
                holder.img_storeImage.setImageDrawable(getResources().getDrawable(R.drawable.tick_u));
                holder.img_storeImage.setVisibility(View.VISIBLE);
            } else if (as.getCHECKOUT_STATUS().equalsIgnoreCase(CommonString.KEY_C)) {
                holder.img_storeImage.setImageDrawable(getResources().getDrawable(R.drawable.tick_c));
                holder.img_storeImage.setVisibility(View.VISIBLE);
            } else if (as.getUpload_Status().equalsIgnoreCase(CommonString.STORE_STATUS_LEAVE)) {
                holder.img_storeImage.setImageDrawable(getResources().getDrawable(R.drawable.leave_tick));
                holder.img_storeImage.setVisibility(View.VISIBLE);
            } else if (db.getCoverageStatus(coverage_id).getStatus().equalsIgnoreCase("I")) {
                holder.img_storeImage.setImageDrawable(getResources().getDrawable(R.drawable.checkin_ico));
                holder.img_storeImage.setVisibility(View.VISIBLE);
            } else {
                holder.img_storeImage.setVisibility(View.INVISIBLE);
            }


            if (db.getCoverageStatus(coverage_id).getStatus().equalsIgnoreCase(CommonString.KEY_VALID)) {
                holder.checkoutbtn.setVisibility(View.VISIBLE);
                holder.img_storeImage.setVisibility(View.INVISIBLE);
            } else {
                holder.checkoutbtn.setVisibility(View.GONE);
            }


            holder.txt_storeName.setText(as.getEd_StoreName() +
                    " , " + as.getCity());

            holder.storelist_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean flag = true;
                    String msg = "";
                    if (as.getUpload_Status().equalsIgnoreCase("U")) {
                        flag = false;
                        msg = "Store data already uploaded";
                    } else if (as.getCHECKOUT_STATUS().equalsIgnoreCase(CommonString.KEY_C)) {
                        flag = false;
                        msg = "Store data already checked out";
                    } else if (as.getUpload_Status().equalsIgnoreCase(CommonString.STORE_STATUS_LEAVE)) {
                        flag = false;
                        msg = "Store already closed";
                    } else {
                        for (int i = 0; i < coverage.size(); i++) {
                            if ((coverage.get(i).getStatus().equalsIgnoreCase("I") || coverage.get(i).getStatus().equalsIgnoreCase(CommonString.KEY_VALID))
                                    && !(as.getStore_id() == Integer.parseInt(coverage.get(i).getStoreId()))) {
                                flag = false;
                                msg = "Please checkout of current store";
                                break;
                            }
                        }
                    }

                    if (flag) {
                        showMyDialog(as);

                    } else {
                        AlertAndMessages.showToastMessage(StoreListActivity.this, msg);
                    }


                }
            });

            holder.checkoutbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            Intent in = new Intent(StoreListActivity.this, CheckoutActivity.class)
                                    .putExtra(CommonString.KEY_STORE_CD, String.valueOf(as.getStore_id()));
                            startActivity(in);
                        }
                    };
                    AlertAndMessages.editorDeleteAlert(StoreListActivity.this, "Do you want to checkout the store?", runnable);

                }
            });


        }

        @Override
        public int getItemCount() {
            return addStoreList.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView txt_storeName;
            ImageView img_storeImage;
            LinearLayout storelist_ll;
            Button checkoutbtn;

            public MyViewHolder(View itemView) {
                super(itemView);

                txt_storeName = (TextView) itemView.findViewById(R.id.txt_storeName);
                checkoutbtn = (Button) itemView.findViewById(R.id.checkoutbtn);
                img_storeImage = (ImageView) itemView.findViewById(R.id.img_storeImage);
                storelist_ll = (LinearLayout) itemView.findViewById(R.id.storelist_ll);
            }
        }
    }

    void showMyDialog(final AddNewStoreGetterSetter as) {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialogbox);
        RadioGroup radioGroup = (RadioGroup) dialog.findViewById(R.id.radiogrpvisit);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if (checkedId == R.id.yes) {

                    store_cd = String.valueOf(as.getStore_id());
                    dialog.cancel();

                    boolean flag = true;
                    int coverage_id = 0;

                    if (coverage.size() > 0) {

                        for (int i = 0; i < coverage.size(); i++) {

                            if (store_cd.equals(coverage.get(i).getStoreId())) {
                                coverage_id = coverage.get(i).getMID();
                                flag = false;
                                break;
                            }
                        }
                    }
                    Class activityClass;
                    if (flag) {

                        activityClass = StoreImageActivity.class;
                    } else {
                        activityClass = MenuActivity.class;
                    }


                    Intent in = new Intent(getApplicationContext(), activityClass)
                            .putExtra(CommonString.KEY_STORE_CD, String.valueOf(as.getStore_id()))
                            .putExtra(CommonString.KEY_STATE_CD, String.valueOf(as.getState_cd()))
                            .putExtra(CommonString.KEY_STORE_TYPE_CD, String.valueOf(as.getStoreType_CD()))
                            .putExtra(CommonString.KEY_ID, String.valueOf(as.getKey_id()))
                            .putExtra(CommonString.KEY_COVERAGE_ID, String.valueOf(coverage_id));

                    startActivity(in);
                    overridePendingTransition(R.anim.activity_in, R.anim.activity_out);

                } else if (checkedId == R.id.no) {
                    dialog.cancel();
                    boolean flag = true;
                    int coverage_id = 0;
                    String checkout_status = "";
                    store_cd = String.valueOf(as.getStore_id());
                    if (coverage.size() > 0) {

                        for (int i = 0; i < coverage.size(); i++) {

                            if (store_cd.equals(coverage.get(i).getStoreId())) {
                                coverage_id = coverage.get(i).getMID();
                                checkout_status = coverage.get(i).getStatus();
                                flag = false;
                                break;
                            }
                        }
                    }
                    final int finalCoverage_id = coverage_id;
                    if (checkout_status.equals(CommonString.KEY_INVALID) || checkout_status.equals(CommonString.KEY_VALID)) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(StoreListActivity.this);

                        builder.setMessage(CommonString.DATA_DELETE_ALERT_MESSAGE)
                                .setCancelable(false)
                                .setPositiveButton("Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,
                                                                int id) {

                                                db.deleteSpecificTables(String.valueOf(finalCoverage_id));

                                                Intent in = new Intent(StoreListActivity.this, NonWorkingReason.class);
                                                startActivity(in);

                                            }
                                        })
                                .setNegativeButton("No",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,
                                                                int id) {


                                                dialog.cancel();
                                            }
                                        });
                        AlertDialog alert = builder.create();

                        alert.show();
                    } else {
                        db.open();
                        db.deleteSpecificTables(String.valueOf(finalCoverage_id));


                        Intent in = new Intent(StoreListActivity.this, NonWorkingReason.class)
                                .putExtra(CommonString.KEY_STORE_CD, String.valueOf(store_cd))
                                .putExtra(CommonString.KEY_STATE_CD, String.valueOf(as.getState_cd()))
                                .putExtra(CommonString.KEY_STORE_TYPE_CD, String.valueOf(as.getStoreType_CD()))
                                .putExtra(CommonString.KEY_ID, String.valueOf(as.getKey_id()))
                                .putExtra(CommonString.KEY_COVERAGE_ID, String.valueOf(coverage_id));
                        startActivity(in);
                        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
                    }
                    //finish();
                }
            }

        });

        dialog.show();
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


    private void declaration() {
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview_addNewStoreList);
        fab_save = (FloatingActionButton) findViewById(R.id.fab_save);
        txt_noDataFound = (TextView) findViewById(R.id.txt_noDataFound);
        key_intent = getIntent().getStringExtra(CommonString.KEY_INTENT);
    }


}
