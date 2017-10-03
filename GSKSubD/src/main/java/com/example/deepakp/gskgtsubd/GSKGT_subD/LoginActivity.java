package com.example.deepakp.gskgtsubd.GSKGT_subD;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deepakp.gskgtsubd.R;
import com.example.deepakp.gskgtsubd.autoUpdate.AutoUpdateActivity;
import com.example.deepakp.gskgtsubd.constants.CommonString;
import com.example.deepakp.gskgtsubd.gettersetter.FailureGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.LoginGetterSetter;
import com.example.deepakp.gskgtsubd.xmlHandler.XMLHandlers;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    // Location updates intervals in sec
    private static int UPDATE_INTERVAL = 500; // 5 sec
    private static int FATEST_INTERVAL = 100; // 1 sec
    private static int DISPLACEMENT = 5; // 10 meters

    Location mLastLocation;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;

    // LogCat tag
    private static final String TAG = LoginActivity.class.getSimpleName();

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mLoginFormView;
    private LocationRequest mLocationRequest;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };

    TextView tv_version;
    String app_ver;

    GoogleApiClient mGoogleApiClient;
    String lat = "0.0";
    String lon = "0.0";

    private SharedPreferences preferences = null;
    private SharedPreferences.Editor editor = null;

    private String p_username, p_password, user_id, password;

    private boolean isChecked;

    private LocationManager locmanager = null;

    private Intent intent = null;

    private int versionCode;

    int eventType;

    LoginGetterSetter lgs = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Parinaam - Sub D");
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        tv_version = (TextView) findViewById(R.id.tv_version_code);
        //populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);

      /*  mEmailView.setText("testmer");
        mPasswordView.setText("cpm123");*/

        mLoginFormView = findViewById(R.id.login_form);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        p_username = preferences.getString(CommonString.KEY_USERNAME, null);
        p_password = preferences.getString(CommonString.KEY_PASSWORD, null);
        isChecked = preferences.getBoolean(CommonString.KEY_REMEMBER, false);

        try {
            app_ver = String.valueOf(getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
            tv_version.setText("Version " + app_ver);
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        locmanager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean enabled = locmanager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        // Check if enabled and if not send user to the GSP settings
        // Better solution would be to display a dialog and suggesting to
        // go to the settings
        if (!enabled) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginActivity.this);

            // Setting Dialog Title
            alertDialog.setTitle("GPS IS DISABLED...");

            // Setting Dialog Message
            alertDialog.setMessage("Click ok to enable GPS.");

            // Setting Positive "Yes" Button
            alertDialog.setPositiveButton("YES",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                        }
                    });

            // Setting Negative "NO" Button
            alertDialog.setNegativeButton("NO",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to invoke NO event
                            dialog.cancel();
                        }
                    });
            // Showing Alert Message
            alertDialog.show();

        }

        // First we need to check availability of play services
        if (checkPlayServices()) {
            // Building the GoogleApi client
            buildGoogleApiClient();
            createLocationRequest();
        }

        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        // Create a Folder for Images
        File file = new File(Environment.getExternalStorageDirectory(), "GSKGTSubD_Image");
        if (!file.isDirectory()) {
            file.mkdir();
        }

        //Login
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mEmailView.getWindowToken(), 0);
                attemptLogin();
            }
        });
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        user_id = mEmailView.getText().toString();
        password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(user_id)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            p_username = preferences.getString(CommonString.KEY_USERNAME, null);
            p_password = preferences.getString(CommonString.KEY_PASSWORD, null);

            boolean previous_user_flag = false;
            if (p_username != null && p_password != null) {
                if (user_id.equals(p_username) && password.equals(p_password)) {
                    previous_user_flag = true;
                }
            } else {
                previous_user_flag = true;
            }

            if (previous_user_flag) {
                // Show a progress spinner, and kick off a background task to
                // perform the user login attempt.

/*
                Handler h = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        if (msg.what != 1) { // code if not connected
                            Snackbar.make(mEmailView, CommonString.NO_INTERNET_CONNECTION,
                                    Snackbar.LENGTH_SHORT).show();
                        } else { // code if connected
                            // showProgress(true);
                            new AuthenticateTask().execute();
                        }
                    }
                };
*/

                if(CheckNetAvailability())
                {
                    new AuthenticateTask().execute();
                }
                else
                {
                    Snackbar.make(mEmailView, CommonString.NO_INTERNET_CONNECTION,
                            Snackbar.LENGTH_SHORT).show();
                }

               // isNetworkAvailable(h, 1000);
            } else {
                Snackbar.make(mEmailView, CommonString.UERNAME_OR_PASSWORD_IS_WRONG, Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 0;
    }

    /* Shows the progress UI and hides the login form.*/
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(show ? 0 : 1)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                        }
                    });
        } else {

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (mLastLocation != null) {
                lat = String.valueOf(mLastLocation.getLatitude());
                lon = String.valueOf(mLastLocation.getLongitude());
            }
        }
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = "
                + connectionResult.getErrorCode());
    }

    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onLocationChanged(Location location) {

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

    /* Represents an asynchronous login/registration task used to authenticate the user.*/
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);
            if (success) {
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    public boolean CheckNetAvailability() {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                .getState() == NetworkInfo.State.CONNECTED
                || connectivityManager.getNetworkInfo(
                ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            // we are connected to a network
            connected = true;
        }
        return connected;
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

    private class AuthenticateTask extends AsyncTask<Void, Void, String> {
        private ProgressDialog dialog = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                dialog = new ProgressDialog(LoginActivity.this);
                dialog.setTitle("Login");
                dialog.setMessage("Authenticating....");
                dialog.setCancelable(false);
                dialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                String resultHttp = "";
                versionCode = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;

                if (lat == null || lat.equals("")) {
                    lat = "0.0";
                }

                if (lon == null || lon.equals("")) {
                    lon = "0.0";
                }

                String userauth_xml = "[DATA]" + "[USER_DATA][USER_ID]"
                        + user_id + "[/USER_ID]" + "[Password]" + password
                        + "[/Password]" + "[IN_TIME]" + getCurrentTime()
                        + "[/IN_TIME]" + "[LATITUDE]" + lat
                        + "[/LATITUDE]" + "[LONGITUDE]" + lon
                        + "[/LONGITUDE]" + "[APP_VERSION]" + app_ver
                        + "[/APP_VERSION]" + "[ATT_MODE]OnLine[/ATT_MODE]"
                        + "[/USER_DATA][/DATA]";

                SoapObject request = new SoapObject(CommonString.NAMESPACE, CommonString.METHOD_LOGIN);
                request.addProperty("onXML", userauth_xml);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                HttpTransportSE androidHttpTransport = new HttpTransportSE(CommonString.URL);
                androidHttpTransport.call(CommonString.SOAP_ACTION_LOGIN, envelope);

                Object result = (Object) envelope.getResponse();

                if (result.toString().equalsIgnoreCase(CommonString.KEY_FAILURE)) {

                    return CommonString.KEY_FAILURE;

                } else if (result.toString().equalsIgnoreCase(CommonString.KEY_FALSE)) {
                    return CommonString.KEY_FALSE;

                } else if (result.toString().equalsIgnoreCase(CommonString.KEY_CHANGED)) {
                    return CommonString.KEY_CHANGED;

                } else {
                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                    factory.setNamespaceAware(true);
                    XmlPullParser xpp = factory.newPullParser();

                    xpp.setInput(new StringReader(result.toString()));
                    xpp.next();
                    eventType = xpp.getEventType();
                    FailureGetterSetter failureGetterSetter = XMLHandlers.failureXMLHandler(xpp, eventType);

                    if (failureGetterSetter.getStatus().equalsIgnoreCase(CommonString.KEY_FAILURE)) {

                        return CommonString.KEY_FAILURE;

                    } else {
                        try {
                            // For String source
                            xpp.setInput(new StringReader(result.toString()));
                            xpp.next();
                            eventType = xpp.getEventType();
                            lgs = XMLHandlers.loginXMLHandler(xpp, eventType);
                        } catch (XmlPullParserException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        // PUT IN PREFERENCES
                        editor.putString(CommonString.KEY_USERNAME, user_id);
                        editor.putString(CommonString.KEY_PASSWORD, password);
                        editor.putString(CommonString.KEY_VERSION, lgs.getVERSION());
                        editor.putString(CommonString.KEY_PATH, lgs.getPATH());
                        editor.putString(CommonString.KEY_DATE, lgs.getDATE());
                        editor.putString(CommonString.KEY_USER_TYPE, lgs.getRIGHTNAME());
                        editor.commit();

                        return CommonString.KEY_SUCCESS;
                    }
                }

            } catch (MalformedURLException e) {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        showMessage(CommonString.MESSAGE_EXCEPTION);
                    }
                });
            } catch (IOException e) {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        showMessage(CommonString.MESSAGE_SOCKETEXCEPTION);
                    }
                });
            } catch (Exception e) {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        showMessage(CommonString.MESSAGE_EXCEPTION);
                    }
                });
            }
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                dialog.cancel();
                if (result.equals(CommonString.KEY_SUCCESS))
                {
                    				//database.open();
                    if (preferences.getString(CommonString.KEY_VERSION, "").equals(Integer.toString(versionCode)))
                    {
                        intent = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                        finish();
                    } else {
                        intent = new Intent(getBaseContext(), AutoUpdateActivity.class);
                        intent.putExtra(CommonString.KEY_PATH, preferences.getString(CommonString.KEY_PATH, ""));
                        startActivity(intent);

                        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                        finish();
                    }

                } else if (result.equals(CommonString.KEY_FAILURE)) {
                    showMessage(CommonString.METHOD_LOGIN + "Failure");
                }

                if (result.equals(CommonString.KEY_CHANGED)) {
                    showMessage(CommonString.MESSAGE_CHANGED);
                } else if (result.equals(CommonString.KEY_FALSE)) {
                    showMessage(CommonString.MESSAGE_FALSE);
                }

                dialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getCurrentTime() {
        Calendar m_cal = Calendar.getInstance();

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String intime = formatter.format(m_cal.getTime());

        return intime;
    }

    public void showMessage(String msg) {
        new AlertDialog.Builder(LoginActivity.this)
                .setTitle("Login Dialog")
                .setMessage(msg)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        showProgress(false);
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    @Override
    protected void onResume() {
        super.onResume();

        checkPlayServices();
        // Resuming the periodic location updates
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }


    /* Method to verify google play services on the device*/
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(), "This device is not supported.", Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        }
        return true;
    }

    /*Starting the location updates*/
    protected void startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

           // LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }


    /* Creating google api client object*/
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }

    /* Creating location request object*/
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FATEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
    }
}

