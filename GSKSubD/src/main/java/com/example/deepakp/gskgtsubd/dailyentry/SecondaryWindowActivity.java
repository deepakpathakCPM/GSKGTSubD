package com.example.deepakp.gskgtsubd.dailyentry;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.deepakp.gskgtsubd.R;
import com.example.deepakp.gskgtsubd.constants.CommonString;
import com.example.deepakp.gskgtsubd.database.Database;
import com.example.deepakp.gskgtsubd.gettersetter.AnswerMasterGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.WindowChecklistGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.WindowMasterGetterSetter;
import com.example.deepakp.gskgtsubd.utilities.AlertAndMessages;
import com.example.deepakp.gskgtsubd.utilities.CommonFunctions;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class SecondaryWindowActivity extends AppCompatActivity implements View.OnClickListener {

    ExpandableListView expandableListView;
    Database db;
    Context context;
    ArrayList<WindowMasterGetterSetter> windowList, savedWindowList;
    ArrayList<WindowChecklistGetterSetter> checkList;
    HashMap<String, ArrayList<WindowChecklistGetterSetter>> map;
    String intime, store_cd, coverage_id, visit_date, _pathforcheck = "", path, username, image = "", cameraTag, state_cd, storetype_cd, answer = "", answer_cd = "-1";
    Activity activity;
    Toolbar toolbar;
    private SharedPreferences preferences;
    FloatingActionButton fab_save;
    ArrayAdapter answerAdapterforPaid, answerAdapter;
    Spinner paidYesOrNo_spinner;
    ArrayList<AnswerMasterGetterSetter> answerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary_window);
        declaration();

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Window");

        windowList = db.getSavedWindowData(store_cd, coverage_id);

        if (windowList.size() == 0) {
            windowList = db.getWindowMasterList(state_cd, storetype_cd);
            for (int i = 0; i < windowList.size(); i++) {
                map.put(windowList.get(i).getWINDOW().get(0), db.getWindowCheckList(windowList.get(i).getWINDOW_CD().get(0)));
            }
        } else {
            for (int i = 0; i < windowList.size(); i++) {
                map.put(windowList.get(i).getWINDOW().get(0), db.getAnswerListForSavedWindowData(windowList.get(i).getKey_id()));
            }
        }

        if (store_cd.equalsIgnoreCase("0")) {
            answerList = db.getAnswerMasterList(true);
            answerAdapterforPaid = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item);
            for (int i = 0; i < answerList.size(); i++) {
                answerAdapterforPaid.add(answerList.get(i).getANSWER().get(0));
            }
            paidYesOrNo_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    answer_cd = answerList.get(position).getANSWER_CD().get(0);
                    answer = answerList.get(position).getANSWER().get(0);

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            answerAdapterforPaid.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            paidYesOrNo_spinner.setAdapter(answerAdapterforPaid);

            if (windowList.size()>0) {

                if (windowList.get(0).getPaidOrNonpaid().equalsIgnoreCase("paid")) {
                    paidYesOrNo_spinner.setSelection(1);
                } else if (windowList.get(0).getPaidOrNonpaid().equalsIgnoreCase("non paid")) {
                    paidYesOrNo_spinner.setSelection(2);
                } else {
                    paidYesOrNo_spinner.setSelection(0);
                }
            }
            paidYesOrNo_spinner.setVisibility(View.VISIBLE);
        } else {
            paidYesOrNo_spinner.setVisibility(View.GONE);
        }


        ExpandableAdapter expandableAdapter = new ExpandableAdapter(this, windowList, map);
        expandableListView.setAdapter(expandableAdapter);

        for (int i = 0; i < expandableListView.getExpandableListAdapter().getGroupCount(); i++) {
            expandableListView.expandGroup(i);
        }

        expandableListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                int i = scrollState;
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
              /*  int lastItem = firstVisibleItem + visibleItemCount;
                if (lastItem == totalItemCount) {
                    fab_save.setVisibility(View.INVISIBLE);
                } else {
                    fab_save.setVisibility(View.VISIBLE);
                }*/
            }
        });

        fab_save.setOnClickListener(this);

    }


    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
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
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ref_btn:
                AlertAndMessages.showRefImage(context, windowList.get(Integer.parseInt(v.getTag().toString())).getPLANOGRAM_IMAGE().get(0));
                break;
            case R.id.camera_btn:

                intime = CommonFunctions.getCurrentTime();
                cameraTag = v.getTag().toString();
                for (int i = 0; i < windowList.size(); i++) {
                    if (!(windowList.get(i).getCameraIcon() == R.drawable.camera_grey)) {
                        if (v.getTag().toString().equalsIgnoreCase("WindowImage_" + (i))) {
                            _pathforcheck = store_cd + "_" + "WindowImage" + i + "_" + visit_date.replace("/", "") + intime.replace(":", "") + ".jpg";
                            path = CommonString.FILE_PATH + _pathforcheck;
                            CommonFunctions.startCameraActivity(activity, path);
                            break;
                        }
                    }
                }

                break;
            case R.id.fab_save:
                if (validate()) {

                    // AlertAndMessages.showToastMessage(context, "Data saved");

                  /*  for(int i=0;i<windowList.size();i++)
                    {
                        ArrayList<WindowChecklistGetterSetter> list = map.get(windowList.get(i).getWINDOW().get(0));
                    }*/

                    long common_id[] = db.insertWindowData(windowList, store_cd, coverage_id, answer);
                    if (common_id.length>0 && common_id[0] > 0) {
                        long id = db.insertWindowChildData(common_id, store_cd, coverage_id, windowList, map);
                        if (id > 0) {
                            AlertAndMessages.showToastMessage(context, "Data saved");
                        } else {
                            //AlertAndMessages.showToastMessage(context, "window and child data not saved");
                        }

                    } else {
                        //  AlertAndMessages.showToastMessage(context, "window not saved");
                    }
                    clearFields();
                    finish();

                }

                break;


        }

    }


    private boolean validate() {
        boolean isGood = true;

        if (store_cd.equalsIgnoreCase("0")) {
            if (answer_cd.equalsIgnoreCase("-1")) {
                AlertAndMessages.showToastMessage(context, "Please select Window Type");
                isGood = false;
            }
        }
        if (isGood) {
            for (int i = 0; i < windowList.size(); i++) {
                if (windowList.get(i).isWINDOW_EXIST()) {
                    if (windowList.get(i).getWINDOW_IMAGE().equalsIgnoreCase("")) {
                        isGood = false;
                        AlertAndMessages.showToastMessage(context, "please click image at window '" + i + "'");
                        break;
                    } else {
                        ArrayList<WindowChecklistGetterSetter> list = map.get(windowList.get(i).getWINDOW().get(0));
                        for (int j = 0; j < list.size(); j++) {
                            if (list.get(j).getANSWER_CD().equalsIgnoreCase("-1")) {
                                isGood = false;
                                AlertAndMessages.showToastMessage(context, "please select answer at window '" + (i + 1) + "' and item '" + (j + 1) + "'");
                                break;
                            }
                        }
                    }

                }
                if (isGood == false) {
                    break;
                }
            }
        }

        return isGood;
    }


    class ExpandableAdapter extends BaseExpandableListAdapter {
        LayoutInflater inflater;
        ArrayList<WindowMasterGetterSetter> windowList;
        HashMap<String, ArrayList<WindowChecklistGetterSetter>> checkList;

        public ExpandableAdapter(Context context, ArrayList<WindowMasterGetterSetter> windowList, HashMap<String, ArrayList<WindowChecklistGetterSetter>> map) {
            inflater = LayoutInflater.from(context);
            this.windowList = windowList;
            this.checkList = map;
        }


        @Override
        public void registerDataSetObserver(DataSetObserver observer) {

        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver observer) {

        }

        @Override
        public int getGroupCount() {
            return windowList.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {

            return checkList.get(windowList.get(groupPosition).getWINDOW().get(0)).size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return null;
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return null;
        }

        @Override
        public long getGroupId(int groupPosition) {
            return 0;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return 0;
        }


        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_expandable_groupview, null);
            }
            MyHolder myHolder = new MyHolder(convertView);
            myHolder.window_txt.setText(windowList.get(groupPosition).getWINDOW().get(0));
            boolean isexist = windowList.get(groupPosition).isWINDOW_EXIST();
            myHolder.exists_checkbox.setChecked(isexist);
            if (isexist) {
                if (windowList.get(groupPosition).getWINDOW_IMAGE().equalsIgnoreCase("")) {
                    myHolder.camera_btn.setBackgroundResource(R.drawable.camera_orange);
                } else {
                    myHolder.camera_btn.setBackgroundResource(R.drawable.camera_green);
                }
            } else {

                myHolder.camera_btn.setBackgroundResource(R.drawable.camera_grey);

            }



         /*   if (savedWindowList.size() > 0) {

                myHolder.camera_btn.setBackgroundResource(savedWindowList.get(groupPosition).getCameraIcon());
            }
            else*/


            myHolder.ref_image.setTag(groupPosition);
            myHolder.ref_image.setOnClickListener(SecondaryWindowActivity.this);
            myHolder.camera_btn.setOnClickListener(SecondaryWindowActivity.this);
            myHolder.camera_btn.setTag("WindowImage_" + (groupPosition));
            final ExpandableListView mExpandableListView = (ExpandableListView) parent;

            myHolder.exists_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        windowList.get(groupPosition).setWINDOW_EXIST(isChecked);
                        //windowList.get(groupPosition).setWINDOW_IMAGE(image);
                        //windowList.get(groupPosition).setCameraIcon(R.drawable.camera_orange);
                        expandableListView.invalidateViews();
                        mExpandableListView.expandGroup(groupPosition);

                    } else {
                        windowList.get(groupPosition).setWINDOW_EXIST(isChecked);
                        windowList.get(groupPosition).setWINDOW_IMAGE("");
                        //windowList.get(groupPosition).setCameraIcon(R.drawable.camera_grey);
                        expandableListView.invalidateViews();
                        mExpandableListView.collapseGroup(groupPosition);
                    }
                }
            });

            return convertView;
        }


        @Override
        public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_expandable_childview, null);
            }
            MyHolder myHolder = new MyHolder(convertView);

            final WindowChecklistGetterSetter winCheckgetset;
            winCheckgetset = checkList.get(windowList.get(groupPosition).getWINDOW().get(0)).get(childPosition);

            myHolder.window_childtxt.setText(winCheckgetset.getCHECKLIST().get(0));

            final ArrayList<AnswerMasterGetterSetter> answerList = db.getAnswerMasterListForWindow(checkList.get(windowList.get(groupPosition).getWINDOW().get(0)).get(childPosition).getCHECKLIST_CD().get(0));
            //------------for Answer List---------------
            answerAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item);
            for (int i = 0; i < answerList.size(); i++) {
                answerAdapter.add(answerList.get(i).getANSWER().get(0));
            }
            answerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            myHolder.answer_spinner.setAdapter(answerAdapter);
            //------------------------------------------------

            myHolder.answer_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    winCheckgetset.setANSWER_CD(answerList.get(position).getANSWER_CD().get(0));
                    winCheckgetset.setANSWER(answerList.get(position).getANSWER().get(0));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            if (windowList.get(groupPosition).isWINDOW_EXIST() == false) {
                myHolder.answer_spinner.setSelection(0);
            } else {
                int position = 0;
                for (int i = 0; i < answerList.size(); i++) {
                    if (winCheckgetset.getANSWER_CD().equalsIgnoreCase(answerList.get(i).getANSWER_CD().get(0))) {
                        position = i;
                        break;
                    }
                }
                myHolder.answer_spinner.setSelection(position);

            }


            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }

        @Override
        public boolean areAllItemsEnabled() {
            return false;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public void onGroupExpanded(int groupPosition) {

        }

        @Override
        public void onGroupCollapsed(int groupPosition) {

        }

        @Override
        public long getCombinedChildId(long groupId, long childId) {
            return 0;
        }

        @Override
        public long getCombinedGroupId(long groupId) {
            return 0;
        }
    }

    class MyHolder {
        TextView window_txt, window_childtxt;
        CheckBox exists_checkbox;
        ImageButton ref_image, camera_btn;
        Spinner answer_spinner;

        public MyHolder(View view) {
            window_txt = (TextView) view.findViewById(R.id.window_txt);
            exists_checkbox = (CheckBox) view.findViewById(R.id.exists_check);
            ref_image = (ImageButton) view.findViewById(R.id.ref_btn);
            camera_btn = (ImageButton) view.findViewById(R.id.camera_btn);

            window_childtxt = (TextView) view.findViewById(R.id.window_txt_child);
            answer_spinner = (Spinner) view.findViewById(R.id.answer_spinner);
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {

            switch (resultCode) {
                case 0:
                    Log.i("MakeMachine", "User cancelled");
                    break;

                case -1:

                    if (_pathforcheck != null && !_pathforcheck.equals("")) {
                        if (new File(CommonString.FILE_PATH + _pathforcheck).exists()) {

                            image = _pathforcheck;
                            for (int i = 0; i < windowList.size(); i++) {
                                if (cameraTag.equalsIgnoreCase("WindowImage_" + i)) {
                                    windowList.get(i).setWINDOW_IMAGE(image);
                                    windowList.get(i).setCameraIcon(R.drawable.camera_green);
                                    expandableListView.invalidateViews();
                                    break;
                                }
                            }

                            _pathforcheck = "";
                            cameraTag = "";
                            image = "";
                        }
                    }

                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }


    public void clearFields() {

      /*  brand_spinner.setSelection(0);
        display_spinner.setSelection(0);
        location_spinner.setSelection(0);
        quantity_edittext.setText("");
        totalStock_edittext.setText("");*/

        //rightcam.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.camera, 0, 0);

    }

    private void declaration() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        paidYesOrNo_spinner = (Spinner) findViewById(R.id.paidYesOrNo_spinner);
        context = this;
        activity = this;
        expandableListView = (ExpandableListView) findViewById(R.id.sec_win_expandablelist);
        map = new HashMap<String, ArrayList<WindowChecklistGetterSetter>>();
        db = new Database(context);
        db.open();
        fab_save = (FloatingActionButton) findViewById(R.id.fab_save);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        store_cd = getIntent().getStringExtra(CommonString.KEY_STORE_CD);
        coverage_id = getIntent().getStringExtra(CommonString.KEY_COVERAGE_ID);
        visit_date = preferences.getString(CommonString.KEY_DATE, null);
        username = preferences.getString(CommonString.KEY_USERNAME, null);
        intime = preferences.getString(CommonString.KEY_STORE_IN_TIME, "");
        visit_date = preferences.getString(CommonString.KEY_DATE, null);
        state_cd = getIntent().getStringExtra(CommonString.KEY_STATE_CD);
        storetype_cd = getIntent().getStringExtra(CommonString.KEY_STORE_TYPE_CD);

    }

}
