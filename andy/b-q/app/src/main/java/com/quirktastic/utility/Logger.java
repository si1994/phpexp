package com.quirktastic.utility;


import android.util.Log;


public class Logger {


   private static boolean developer_mode = true;

    public static void e(String tag, String message){
        if (developer_mode == true) {
            try {
                Log.e(tag + " --> ", message + "");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static void i(String tag, String message){
        if (developer_mode == true) {
            try {
                Log.i(tag + " --> ", message + "");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static void d(String tag, String message){
        if (developer_mode == true) {
            try {
                Log.d(tag + " --> ", message + "");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static void w(String tag, String message){
        if (developer_mode == true) {
            try {
                Log.w(tag + " --> ", message + "");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
