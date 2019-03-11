package com.quirktastic.utility;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.shashank.sony.fancytoastlib.FancyToast;

public class Toast {

    public static final boolean isToast = true;

    public static void show(Context context, String message) {

        if (isToast) {
             android.widget.Toast.makeText(context, message, android.widget.Toast.LENGTH_SHORT).show();
           // FancyToast.makeText(context, message, FancyToast.LENGTH_LONG, FancyToast.DEFAULT, false).show();

//            showSnackBar(context, message);
        }

    }

    public static void show(Context context, int message) {

        if (isToast) {
            android.widget.Toast.makeText(context, "" + message, android.widget.Toast.LENGTH_SHORT).show();
//            showSnackBar(context, "" + message);
        }

    }

    public static void showLongToast(Context context, String message) {

        if (isToast) {
            android.widget.Toast.makeText(context, message, android.widget.Toast.LENGTH_LONG).show();
//            showSnackBarLong(context, message);
        }

    }

    public static void showLongToast(Context context, int message) {

        if (isToast) {
            android.widget.Toast.makeText(context, "" + message, android.widget.Toast.LENGTH_LONG).show();
//            showSnackBarLong(context, "" + message);
        }

    }

    private static void showSnackBar(Context context, String message) {
        try {
            View parentLayout = ((Activity) context).findViewById(android.R.id.content);
            Snackbar.make(parentLayout, message, Snackbar.LENGTH_SHORT)
                    .setAction("CLOSE", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    })
                    .setActionTextColor(((Activity) context).getResources().getColor(android.R.color.white))
                    .show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private static void showSnackBarLong(Context context, String message) {
        try {
            View parentLayout = ((Activity) context).findViewById(android.R.id.content);
            Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG)
                    .setAction("CLOSE", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    })
                    .setActionTextColor(((Activity) context).getResources().getColor(android.R.color.white))
                    .show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
