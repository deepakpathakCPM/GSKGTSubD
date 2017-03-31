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
import com.example.deepakp.gskgtsubd.gettersetter.NavMenuGetterSetter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class DailyEntryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ValueAdapter adapter;
    private SharedPreferences preferences;
    String store_cd, training_mode_cd, manned;
    Database db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routetraining);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        training_mode_cd = getIntent().getStringExtra(CommonString.KEY_TRAINING_MODE_CD);
        manned = getIntent().getStringExtra(CommonString.KEY_MANAGED);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        store_cd = preferences.getString(CommonString.KEY_STORE_CD, null);

        //Database
        db = new Database(this);
        db.open();
    }

    @Override
    protected void onResume() {
        super.onResume();

        recyclerView = (RecyclerView) findViewById(R.id.rec_menu);
        List<NavMenuGetterSetter> data = new ArrayList<>();

        NavMenuGetterSetter recData = new NavMenuGetterSetter();
        recData.setIconImg(R.drawable.pjp);
        recData.setIconName("Bandhan");
        data.add(recData);

        recData = new NavMenuGetterSetter();
        recData.setIconName("Leap Frog");
        recData.setIconImg(R.drawable.add_stores);
        data.add(recData);

        adapter = new ValueAdapter(getApplicationContext(), data);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
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
            MyViewHolder holder = new MyViewHolder(view);
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

                    if (current.getIconName().equalsIgnoreCase("Bandhan")) {
                        Intent in = new Intent(getApplicationContext(), StoreListActivity.class).putExtra(CommonString.KEY_INTENT, "Bandhan");
                        startActivity(in);
                        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                    } else if (current.getIconName().equalsIgnoreCase("Leap Frog")) {
                        Intent delivery = new Intent(DailyEntryActivity.this, AddNewStoreListActivity.class).putExtra(CommonString.KEY_INTENT, "Leap Frog");
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
}
