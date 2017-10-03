package com.example.deepakp.gskgtsubd.GSKGT_subD;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deepakp.gskgtsubd.R;
import com.example.deepakp.gskgtsubd.bean.CoverageBean;
import com.example.deepakp.gskgtsubd.constants.CommonString;
import com.example.deepakp.gskgtsubd.dailyentry.DailyEntryActivity;
import com.example.deepakp.gskgtsubd.dailyentry.MyAttendanceActivity;
import com.example.deepakp.gskgtsubd.database.Database;
import com.example.deepakp.gskgtsubd.download.CompleteDownloadActivity;
import com.example.deepakp.gskgtsubd.fragment.MainFragment;
import com.example.deepakp.gskgtsubd.gettersetter.AddNewStoreGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.NonWorkingReasonGetterSetter;
import com.example.deepakp.gskgtsubd.upload.UploadDataActivity;
import com.example.deepakp.gskgtsubd.utilities.AlertAndMessages;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    private SharedPreferences preferences = null;
    private String user_name, user_type, visit_date;
    private ArrayList<AddNewStoreGetterSetter> list = new ArrayList<AddNewStoreGetterSetter>();
    private ArrayList<CoverageBean> coveragelist_jcpstore = new ArrayList<CoverageBean>();
    private ArrayList<CoverageBean> coveragelist_addstore = new ArrayList<CoverageBean>();
    private ArrayList<NonWorkingReasonGetterSetter> attendanceList = new ArrayList<NonWorkingReasonGetterSetter>();
    private ArrayList<NonWorkingReasonGetterSetter> attendanceList2 = new ArrayList<NonWorkingReasonGetterSetter>();
    private ArrayList<NonWorkingReasonGetterSetter> entryAllowFromReasonList = new ArrayList<NonWorkingReasonGetterSetter>();
    Database db;
    private String date;
    String str;
    FrameLayout frame_layout;
    Context context;
    SharedPreferences.Editor editor;

//    ArrayList<JCPGetterSetter> jcplist;
//    ArrayList<CoverageBean> coveragedata;
//    JCPGetterSetter storestatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;
        frame_layout = (FrameLayout) findViewById(R.id.frame_layout);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        user_name = preferences.getString(CommonString.KEY_USERNAME, null);
        user_type = preferences.getString(CommonString.KEY_USER_TYPE, null);
        visit_date = preferences.getString(CommonString.KEY_DATE, null);
        date = preferences.getString(CommonString.KEY_DATE, null);

   /*     editor =preferences.edit();
        editor.putBoolean(CommonString.KEY_ISDATADOWNLOADED,false);
        editor.commit();*/

      /*  db = new Database(this);
        db.open();*/

        //fab = (FloatingActionButton) findViewById(R.id.fab);
        str = CommonString.FILE_PATH;

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = LayoutInflater.from(this).inflate(R.layout.nav_header_layout, navigationView, false);
        navigationView.addHeaderView(headerView);

        TextView tv_username = (TextView) headerView.findViewById(R.id.nav_user_name);
        TextView tv_usertype = (TextView) headerView.findViewById(R.id.nav_user_type);

        tv_username.setText(user_name);
        tv_usertype.setText(user_type);

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        db = new Database(MainActivity.this);
        db.open();

        if (preferences.getBoolean(CommonString.KEY_ISDATADOWNLOADED, false)) {
            attendanceList = db.getAttendanceList(null, visit_date);
            if (attendanceList.size() > 0) {
                db.deleteAttendanceData(user_name, visit_date);
            }
        }

        if (db.isAddStoreDataUploaded(visit_date, CommonString.KEY_U)) {
            db.deleteAddStoreData(visit_date, CommonString.KEY_U);
        }


        FragmentManager fragmentManager = getSupportFragmentManager();
        MainFragment cartfrag = new MainFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_layout, cartfrag)
                .commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_daily_entry) {

            // list = db.getStateMasterList();
            if (!preferences.getBoolean(CommonString.KEY_ISDATADOWNLOADED, false)) {
                Toast.makeText(getBaseContext(), "Please Download Data First",
                        Toast.LENGTH_LONG).show();
            } else {
                attendanceList = db.getAttendanceList(user_name, visit_date);
                attendanceList2 = db.getAttendanceListFromDownload(user_name, visit_date);
                if (attendanceList.size() > 0 || attendanceList2.size() > 0) {

                    if (attendanceList.size() > 0) {
                        if (attendanceList.get(0).getEntry_allow().get(0).equalsIgnoreCase("1")) {
                            Intent in = new Intent(getApplicationContext(), DailyEntryActivity.class);
                            startActivity(in);
                            overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                        } else {
                            AlertAndMessages.showToastMessage(context, "Entry is not allowed.");
                        }
                    } else if (attendanceList2.size() > 0) {
                        entryAllowFromReasonList = db.getEntryAllowFromBrand(attendanceList2.get(0).getReason_cd().get(0));
                        if (entryAllowFromReasonList.size() > 0) {
                            if (entryAllowFromReasonList.get(0).getEntry_allow().get(0).equalsIgnoreCase("1")) {
                                Intent in = new Intent(getApplicationContext(), DailyEntryActivity.class);
                                startActivity(in);
                                overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                            } else {
                                AlertAndMessages.showToastMessage(context, "Entry is not allowed.");
                            }
                        }

                    }


                } else {
                    AlertAndMessages.showToastMessage(context, "Please mark the attendance first.");
                }
            }
        } else if (id == R.id.nav_download) {
            // Download data
            Handler h = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what != 1) { // code if not connected
                        Snackbar.make(frame_layout, CommonString.NO_INTERNET_CONNECTION, Snackbar.LENGTH_SHORT).show();
                    } else { // code if connected
                        // showProgress(true);
                     /*   if (db.getCoverageData(visit_date).size()>0)
                        {
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setTitle("Parinaam");
                            builder.setMessage("Please Upload Previous Data First")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                           // Intent startUpload = new Intent(MainActivity.this, CheckoutNUpload.class);
                                          //  startActivity(startUpload);
                                          //  finish();
                                        }
                                    });
                            AlertDialog alert = builder.create();
                            alert.show();
                        } else {*/
                        Intent startDownload = new Intent(getApplicationContext(), CompleteDownloadActivity.class);
                        startActivity(startDownload);
                        // }
                    }
                }
            };

            isNetworkAvailable(h, 3000);
            //finish();
        } else if (id == R.id.nav_attendance) {

            if (!preferences.getBoolean(CommonString.KEY_ISDATADOWNLOADED, false)) {
                Toast.makeText(getBaseContext(), "Please Download Data First",
                        Toast.LENGTH_LONG).show();
            } else {

                attendanceList2 = db.getAttendanceListFromDownload(user_name, visit_date);
                attendanceList = db.getAttendanceList(user_name, visit_date);
                if (attendanceList.size() == 0 && attendanceList2.size() == 0) {
                    Intent in = new Intent(getApplicationContext(), MyAttendanceActivity.class);
                    startActivity(in);
                    overridePendingTransition(R.anim.activity_in, R.anim.activity_out);

                } else {
                    AlertAndMessages.showToastMessage(context, "Attendence has been marked already");
                }
            }

        } else if (id == R.id.nav_upload) {
            //Upload data
            boolean isvalid = true;
            list = db.getNewStoreListFromJCP(visit_date);
            coveragelist_jcpstore = db.getCoverageData(visit_date);
            coveragelist_addstore = db.getCoverageData("0");

            if (coveragelist_jcpstore.size() > 0) {
                for (int i = 0; i < coveragelist_jcpstore.size(); i++) {
                    if (!coveragelist_jcpstore.get(i).getStoreId().equalsIgnoreCase("0")) {
                        if (coveragelist_jcpstore.get(i).getStatus().equalsIgnoreCase("I") || coveragelist_jcpstore.get(i).getStatus().equalsIgnoreCase(CommonString.KEY_VALID)) {
                            AlertAndMessages.showToastMessage(context, "Pleasecheck out the store in Bandhan");
                            isvalid = false;
                            break;
                        }
                    }
                }
            }

            if (coveragelist_addstore.size() > 0) {
                for (int i = 0; i < coveragelist_addstore.size(); i++) {
                    if (coveragelist_addstore.get(i).getStoreId().equalsIgnoreCase("0")) {
                        if (coveragelist_addstore.get(i).getStatus().equalsIgnoreCase(CommonString.KEY_CHECK_IN) || coveragelist_addstore.get(i).getStatus().equalsIgnoreCase(CommonString.KEY_VALID)) {
                            AlertAndMessages.showToastMessage(context, "Please checkout the store in Leap Frog");
                            isvalid = false;
                            break;
                        }
                    }
                }
            }


            if (coveragelist_jcpstore.size() == 0 && coveragelist_addstore.size() == 0) {
                Snackbar.make(frame_layout, CommonString.MESSAGE_NO_DATA, Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            } else {
                if (isvalid) {

                    Handler h = new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            if (msg.what != 1) {
                                // code if not connected
                                Snackbar.make(frame_layout, CommonString.NO_INTERNET_CONNECTION,
                                        Snackbar.LENGTH_SHORT).show();
                            } else {
                                // code if connected
                                Intent i = new Intent(getBaseContext(),
                                        UploadDataActivity.class);
                                i.putExtra("UploadAll", false);
                                startActivity(i);
                                // finish();
                            }
                        }
                    };
                    isNetworkAvailable(h, 5000);
                }

            }


        } else if (id == R.id.nav_exit) {
            //Exit to login
            Intent startDownload = new Intent(this, LoginActivity.class);
            startActivity(startDownload);
            overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
            finish();

        } else if (id == R.id.nav_help) {
            //Open Help Fragment

        } else if (id == R.id.nav_export_database) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
            builder1.setMessage("Are you sure you want to take the backup of your data")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @SuppressWarnings("resource")
                        public void onClick(DialogInterface dialog, int id) {
                            try {
                                /*File file = new File(Environment.getExternalStorageDirectory(),"capital_backup");
                                if (!file.isDirectory()) {
                                    file.mkdir();
                                }*/

                                File sd = Environment.getExternalStorageDirectory();
                                File data = Environment.getDataDirectory();

                                if (sd.canWrite()) {
                                    long date = System.currentTimeMillis();

                                    SimpleDateFormat sdf = new SimpleDateFormat("MMM/dd/yy");
                                    String dateString = sdf.format(date);

                                    String currentDBPath = "//data//cpm.com.GSKGTSubD//databases//" + Database.DATABASE_NAME;
                                    String backupDBPath = "GSKGTSubD_backup" + dateString.replace('/', '-');

                                    String path = Environment.getExternalStorageDirectory().getPath();

                                    File currentDB = new File(data, currentDBPath);
                                    File backupDB = new File(path, backupDBPath);

                                    Snackbar.make(frame_layout, "Database Exported Successfully", Snackbar.LENGTH_SHORT).show();

                                    if (currentDB.exists()) {
                                        @SuppressWarnings("resource")
                                        FileChannel src = new FileInputStream(currentDB).getChannel();
                                        FileChannel dst = new FileOutputStream(backupDB).getChannel();
                                        dst.transferFrom(src, 0, src.size());
                                        src.close();
                                        dst.close();
                                    }
                                }
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert1 = builder1.create();
            alert1.show();
        } else if (id == R.id.nav_add_store) {
            //startActivity(new Intent(this, AddNewStoreListActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static void isNetworkAvailable(final Handler handler, final int timeout) {
        // ask fo message '0' (not connected) or '1' (connected) on 'handler'
        // the answer must be send before before within the 'timeout' (in milliseconds)

        new Thread() {
            private boolean responded = false;

            @Override
            public void run() {
                // set 'responded' to TRUE if is able to connect with google mobile (responds fast)
                new Thread() {
                    @Override
                    public void run() {
                        HttpGet requestForTest = new HttpGet("http://m.google.com");
                        try {
                            new DefaultHttpClient().execute(requestForTest); // can last...
                            responded = true;
                        } catch (Exception e) {
                            Log.e("MainActivity", "isNetworkAvailable " + e.getMessage());
                        }
                    }
                }.start();

                try {
                    int waited = 0;
                    while (!responded && (waited < timeout)) {
                        sleep(100);
                        if (!responded) {
                            waited += 100;
                        }
                    }
                } catch (InterruptedException e) {
                } // do nothing
                finally {
                    if (!responded) {
                        handler.sendEmptyMessage(0);
                    } else {
                        handler.sendEmptyMessage(1);
                    }
                }
            }
        }.start();
    }

}
