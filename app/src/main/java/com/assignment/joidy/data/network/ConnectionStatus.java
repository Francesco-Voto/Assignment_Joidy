package com.assignment.joidy.data.network;


import android.content.Context;

public class ConnectionStatus {
    public static boolean isNetworkAvailable(Context context) {
        android.net.ConnectivityManager connectivityManager = (android.net.ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null;
    }
}
