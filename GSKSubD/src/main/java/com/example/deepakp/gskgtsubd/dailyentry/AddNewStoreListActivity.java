package com.example.deepakp.gskgtsubd.dailyentry;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.deepakp.gskgtsubd.R;
import com.example.deepakp.gskgtsubd.bean.CoverageBean;
import com.example.deepakp.gskgtsubd.constants.CommonString;
import com.example.deepakp.gskgtsubd.database.Database;
import com.example.deepakp.gskgtsubd.gettersetter.AddNewStoreGetterSetter;
import com.example.deepakp.gskgtsubd.utilities.AlertAndMessages;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by gagang on 21-10-2016.
 */

public class AddNewStoreListActivity extends AppCompatActivity implements LocationListener {
    Toolbar toolbar;
    FloatingActionButton btn_addNewStore;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_store_list);
        declaration();
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Store List");


        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        visit_date = preferences.getString(CommonString.KEY_DATE, null);
        username = preferences.getString(CommonString.KEY_USERNAME, null);
        app_ver = preferences.getString(CommonString.KEY_VERSION, "");


        btn_addNewStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddNewStoreListActivity.this, AddNewStoreActivity.class);
                intent.putExtra("store_id", "0");
                startActivityForResult(intent, 1);
            }
        });

        db = new Database(this);
        db.open();
    }

    @Override
    protected void onResume() {
        super.onResume();

        coverage = db.getCoverageData("0");

        addStoreList = db.getAddNewStoreList();
        btn_addNewStore.setVisibility(View.VISIBLE);


        if (addStoreList.size() > 0) {
            txt_noDataFound.setVisibility(View.GONE);
        } else {
            txt_noDataFound.setVisibility(View.VISIBLE);
        }

        adapter = new AddStoreRecyclerAdapter();
        recyclerview.setAdapter(adapter);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    public void onLocationChanged(Location location) {
        currLatitude = Double.toString(location.getLatitude());
        currLongitude = Double.toString(location.getLongitude());
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {


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

            if (as.getUpload_Status().equalsIgnoreCase("U")) {
                holder.img_storeImage.setImageDrawable(getResources().getDrawable(R.drawable.tick_u));
                holder.img_storeImage.setVisibility(View.VISIBLE);
            } else if (as.getUpload_Status().equalsIgnoreCase(CommonString.KEY_C)) {
                holder.img_storeImage.setImageDrawable(getResources().getDrawable(R.drawable.tick_c));
                holder.img_storeImage.setVisibility(View.VISIBLE);
            } else if (as.getUpload_Status().equalsIgnoreCase("I")) {
                holder.img_storeImage.setImageDrawable(getResources().getDrawable(R.drawable.checkin_ico));
                holder.img_storeImage.setVisibility(View.VISIBLE);
            } else {
                holder.img_storeImage.setVisibility(View.INVISIBLE);
            }


            if (db.getCoverageStatus(as.getCoverage_id()).getStatus().equalsIgnoreCase(CommonString.KEY_VALID)) {
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
                    } else if (as.getUpload_Status().equalsIgnoreCase(CommonString.KEY_C)) {
                        flag = false;
                        msg = "Store data already checked out";
                    } else {
                        for (int i = 0; i < coverage.size(); i++) {
                            if ((coverage.get(i).getStatus().equalsIgnoreCase("I") || coverage.get(i).getStatus().equalsIgnoreCase(CommonString.KEY_VALID))
                                    && !(as.getCoverage_id()==coverage.get(i).getMID())) {
                                flag = false;
                                msg = "Please checkout of current store";
                                break;
                            }
                        }
                    }

                    if (flag) {
                        Class activityClass;
                        if (as.getCoverage_id() == 0) {
                            activityClass = StoreImageActivity.class;
                        } else {
                            activityClass = MenuActivity.class;
                        }

                        Intent in = new Intent(AddNewStoreListActivity.this, activityClass)
                                .putExtra(CommonString.KEY_STORE_CD, String.valueOf(addStoreList.get(position).getStore_id()))
                                .putExtra(CommonString.KEY_STATE_CD, String.valueOf(addStoreList.get(position).getState_cd()))
                                .putExtra(CommonString.KEY_STORE_TYPE_CD, String.valueOf(addStoreList.get(position).getStoreType_CD()))
                                .putExtra(CommonString.KEY_ID, String.valueOf(addStoreList.get(position).getKey_id()))
                                .putExtra(CommonString.KEY_COVERAGE_ID, String.valueOf(as.getCoverage_id()));

                        startActivity(in);


                    } else {
                        AlertAndMessages.showToastMessage(AddNewStoreListActivity.this, msg);
                    }


                }
            });

            holder.checkoutbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Runnable runnable=new Runnable() {
                        @Override
                        public void run() {
                            db.updateCoverageStoreOutTimeOfAddstore(String.valueOf(addStoreList.get(position).getCoverage_id()), visit_date,getCurrentTime(),CommonString.KEY_C);
                            //db.updateCoverageStatus(addStoreList.get(position).getCoverage_id(), CommonString.KEY_C);
                            db.updateStoreStatusOnStoreinfo(addStoreList.get(position).getKey_id(), CommonString.KEY_C);
                            as.setUpload_Status(CommonString.KEY_C);
                            adapter.notifyDataSetChanged();
                        }
                    };
                    AlertAndMessages.editorDeleteAlert(AddNewStoreListActivity.this,"Do you want to checkout the store?",runnable);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == 1) {
//                AddNewStoreGetterSetter addNewStore = db.getAddNewStoreListByUserId(data.getIntExtra("store_id", -1));
//
//                addStoreList.add(addNewStore);
//                adapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    private void declaration() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        btn_addNewStore = (FloatingActionButton) findViewById(R.id.fab_save);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview_addNewStoreList);
        fab_save = (FloatingActionButton) findViewById(R.id.fab_save);
        txt_noDataFound = (TextView) findViewById(R.id.txt_noDataFound);
        key_intent = getIntent().getStringExtra(CommonString.KEY_INTENT);


    }
    public String getCurrentTime() {

        Calendar m_cal = Calendar.getInstance();

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String cdate = formatter.format(m_cal.getTime());

       /* String intime = m_cal.get(Calendar.HOUR_OF_DAY) + ":"
                + m_cal.get(Calendar.MINUTE) + ":" + m_cal.get(Calendar.SECOND);*/

        return cdate;

    }
    //void showMyDialog(final String , final String , final String , final String , final String checkout_status)

}
