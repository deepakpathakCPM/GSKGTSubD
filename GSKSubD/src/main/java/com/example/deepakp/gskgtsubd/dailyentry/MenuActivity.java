package com.example.deepakp.gskgtsubd.dailyentry;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.deepakp.gskgtsubd.R;
import com.example.deepakp.gskgtsubd.constants.CommonString;
import com.example.deepakp.gskgtsubd.database.Database;
import com.example.deepakp.gskgtsubd.gettersetter.AddNewStoreGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.LineEntryGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.NavMenuGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.PosmEntryGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.WindowMasterGetterSetter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ValueAdapter adapter;
    private SharedPreferences preferences;
    String store_cd;
    AddNewStoreGetterSetter storeinfo;
    String manned;
    Database db;
    String coverage_id, state_cd, storetype_cd;
    ArrayList<PosmEntryGetterSetter> recyclerList;
    Toolbar toolbar;
    ArrayList<LineEntryGetterSetter> lineEntryList;
    ArrayList<WindowMasterGetterSetter> windowList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routetraining);
        declaration();

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Daily Activity Menu");


        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        //Database
        db = new Database(this);
        db.open();
    }

    @Override
    protected void onResume() {
        super.onResume();
        NavMenuGetterSetter recData = new NavMenuGetterSetter();
        List<NavMenuGetterSetter> data = new ArrayList<>();

        lineEntryList = db.getLineEntryData(store_cd, coverage_id);
        if (lineEntryList.size() > 0) {
            recData.setIconImg(R.drawable.total_line_score_done);
        } else {
            recData.setIconImg(R.drawable.total_line_score);
        }

        if(store_cd.equalsIgnoreCase("0"))
        {
            recData.setIconName("Total Line Sold");
        }
        else
        {
            recData.setIconName("Total Line Available");
        }

        data.add(recData);


        recData = new NavMenuGetterSetter();
        recData.setIconName("POSM");
        recyclerList = db.getPosmEntryData(store_cd, coverage_id);
        if (recyclerList.size() > 0) {
            recData.setIconImg(R.drawable.posm_done);
        } else {
            recData.setIconImg(R.drawable.posm);
        }
        data.add(recData);

        recData = new NavMenuGetterSetter();
        recData.setIconName("Window");
        windowList = db.getSavedWindowData(store_cd, coverage_id);
        if (windowList.size() > 0) {
            recData.setIconImg(R.drawable.window_done);
        } else {
            recData.setIconImg(R.drawable.window);
        }
        data.add(recData);


        adapter = new ValueAdapter(getApplicationContext(), data);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));

        validate();
    }

    public void validate() {
        lineEntryList = db.getLineEntryData(store_cd, coverage_id);
        recyclerList = db.getPosmEntryData(store_cd, coverage_id);
        windowList = db.getSavedWindowData(store_cd, coverage_id);

        if (lineEntryList.size() > 0 && recyclerList.size() > 0 && windowList.size() > 0) {
            db.updateCoverageStatus(Integer.parseInt(coverage_id), CommonString.KEY_VALID);
        }

    }

    public class ValueAdapter extends RecyclerView.Adapter<ValueAdapter.MyViewHolder> {
        private LayoutInflater inflator;
        List<NavMenuGetterSetter> data = Collections.emptyList();

        public ValueAdapter(Context context, List<NavMenuGetterSetter> data) {
            inflator = LayoutInflater.from(context);
            this.data = data;
        }

        @Override
        public ValueAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {
            View view = inflator.inflate(R.layout.custom_menu_row, parent, false);
            ValueAdapter.MyViewHolder holder = new ValueAdapter.MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final ValueAdapter.MyViewHolder viewHolder, final int position) {
            final NavMenuGetterSetter current = data.get(position);
            viewHolder.txt.setText(current.getIconName());
            viewHolder.icon.setImageResource(current.getIconImg());

            viewHolder.lay_menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (current.getIconName().equalsIgnoreCase("Total Line Available") || current.getIconName().equalsIgnoreCase("Total Line Sold")) {
                        Intent in = new Intent(getApplicationContext(), LineEntryActivity.class)
                                .putExtra(CommonString.KEY_STORE_CD, store_cd)
                                .putExtra(CommonString.KEY_STATE_CD, state_cd)
                                .putExtra(CommonString.KEY_STORE_TYPE_CD, storetype_cd)
                                .putExtra(CommonString.KEY_COVERAGE_ID, coverage_id);
                        startActivity(in);
                        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                    } else if (current.getIconName().equalsIgnoreCase("POSM")) {
                        Intent delivery = new Intent(MenuActivity.this, PosmEntryActivity.class)
                                .putExtra(CommonString.KEY_STORE_CD, store_cd)
                                .putExtra(CommonString.KEY_COVERAGE_ID, coverage_id)
                                .putExtra(CommonString.KEY_STATE_CD, state_cd)
                                .putExtra(CommonString.KEY_STORE_TYPE_CD, storetype_cd);
                        startActivity(delivery);
                        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                    } else if (current.getIconName().equalsIgnoreCase("Window")) {
                        Intent delivery = new Intent(MenuActivity.this, SecondaryWindowActivity.class)
                                .putExtra(CommonString.KEY_STORE_CD, store_cd)
                                .putExtra(CommonString.KEY_COVERAGE_ID, coverage_id)
                                .putExtra(CommonString.KEY_STATE_CD, state_cd)
                                .putExtra(CommonString.KEY_STORE_TYPE_CD, storetype_cd);
                        startActivity(delivery);
                        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                    }

                }
            });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView txt;
            ImageView icon;
            LinearLayout lay_menu;

            public MyViewHolder(View itemView) {
                super(itemView);
                txt = (TextView) itemView.findViewById(R.id.list_txt);
                icon = (ImageView) itemView.findViewById(R.id.list_icon);
                lay_menu = (LinearLayout) itemView.findViewById(R.id.lay_menu);
            }
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

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
    }

    private void declaration() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.rec_menu);
        coverage_id = getIntent().getStringExtra(CommonString.KEY_COVERAGE_ID);
        store_cd = getIntent().getStringExtra(CommonString.KEY_STORE_CD);
        state_cd = getIntent().getStringExtra(CommonString.KEY_STATE_CD);
        storetype_cd = getIntent().getStringExtra(CommonString.KEY_STORE_TYPE_CD);
    }

}
