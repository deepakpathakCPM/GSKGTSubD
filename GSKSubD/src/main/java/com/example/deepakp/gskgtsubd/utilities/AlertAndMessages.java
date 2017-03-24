package com.example.deepakp.gskgtsubd.utilities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.deepakp.gskgtsubd.GSKGT_subD.MainActivity;
import com.example.deepakp.gskgtsubd.R;

/**
 * Created by deepakp on 2/9/2017.
 */

public class AlertAndMessages {

    public static final String MESSAGE_DELETE = "Do You Want To deletecross This Record";
    public static final String MESSAGE_SAVE = "Do You Want To Save The Data ";
    public static final String MESSAGE_FAILURE = "Server Eroor.Please Access After Some Time";
    public static final String MESSAGE_JCP_FALSE = "Data is not found in ";
    public static final String MESSAGE_INVALID_DATA = "Enter Data";
    public static final String MESSAGE_DUPLICATE_DATA = "Data Already Exist";
    public static final String MESSAGE_DOWNLOAD = "Data Downloaded Successfully";
    public static final String MESSAGE_UPLOAD_DATA = "Data Uploaded Successfully";
    public static final String MESSAGE_UPLOAD_IMAGE = "Images Uploaded Successfully";
    public static final String MESSAGE_FALSE = "Invalid User";
    public static final String MESSAGE_CHANGED = "Invalid UserId Or Password / Password Has Been Changed.";
    public static final String MESSAGE_EXIT = "Do You Want To Exit";
    public static final String MESSAGE_BACK = "Use Back Button";
    public static final String MESSAGE_EXCEPTION = "Problem Occured : Report The Problem To Parinaam";
    public static final String MESSAGE_SOCKETEXCEPTION = "Network Communication Failure. Check Your Network Connection";
    public static final String MESSAGE_NO_DATA = "No Data For Upload";
    public static final String MESSAGE_NO_IMAGE = "No Image For Upload";
    public static final String MESSAGE_DATA_FIRST = "Upload Data First";
    public static final String MESSAGE_IMAGE_UPLOAD = "Upload Images";
    public static final String MESSAGE_PARTIAL_UPLOAD = "Data Partially Uploaded";
    public static final String MESSAGE_DATA_UPLOAD = "Data Uploaded";
    public static final String MESSAGE_CHECKOUT_UPLOAD = "Store Already Checkedout";
    public static final String MESSAGE_UPLOAD = "All Data Uploaded";
    public static final String MESSAGE_LEAVE_UPLOAD = "Leave Data Uploaded";
    public static final String MESSAGE_ERROR = "Network Error , ";
    public static final String MESSAGE_NO_UPDATE = "No Update Available";
    public static final String MESSAGE_LEAVE = "On Leave";
    public static final String MESSAGE_CHECKOUT = "Store Successfully Checkedout";

    public static void showToastMessage(Context context,String message)
    {
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();

    }

    public static void showAlertMessage(Context context,String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

    public static void showMessageAtUpdate(String str, final Activity activity) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Parinaam");

        builder.setMessage(str)
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {


                                Intent i = new Intent(activity,MainActivity.class);
                                activity.startActivity(i);

                                activity.finish();
                            }
                        })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Intent i = new Intent(activity, MainActivity.class);
                        activity.startActivity(i);
                        activity.finish();

                    }
                });

        AlertDialog alert = builder.create();
        alert.show();

    }



    public static void ShowAlert1(final Activity activity, String str) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Parinaam");
        builder.setMessage(str).setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        //	Intent i = new Intent(activity, MainMenuActivity.class);
                        Intent i = new Intent(activity, MainActivity.class);
                        activity.startActivity(i);
                        activity.finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }


    public static void showRefImage(Context context,String imagePath)
    {
        android.support.v7.app.AlertDialog.Builder alertadd = new android.support.v7.app.AlertDialog.Builder(context);
        LayoutInflater factory = LayoutInflater.from(context);
        final View view = factory.inflate(R.layout.showimageinalert, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.dialog_imageview);
        String imageurl = imagePath;
        new DownloadImageTask(imageView)
                .execute(imageurl);
        alertadd.setView(view);
        alertadd.show();

    }

    public static void editorDeleteAlert(Activity activity, String str, final Runnable task) {

        final AlertDialog builder = new AlertDialog.Builder(activity).create();
        builder.setTitle("Alert");
        builder.setMessage(str);
        builder.setCancelable(false);
        builder.setButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                task.run();

            }
        });
        builder.setButton2("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                builder.dismiss();
            }
        });
        builder.show();
    }



    public static void backpressedAlert(final Activity activity) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Alert");
        builder.setMessage("Do you want to exit? Filled data will be lost");
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                activity.finish();
                activity.overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
