package com.whereisthat.helper;

import android.content.Context;
import android.net.ConnectivityManager;

public abstract class ConnHelper {

	public static boolean isValid(Context context) {
        try
        {
            ConnectivityManager cm = (ConnectivityManager)
            context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable() && cm.getActiveNetworkInfo().isConnected()) {
                return true;
            } else {
                return false;
            }
        }catch (Exception e) {
            return false;
        }
    }	
}
