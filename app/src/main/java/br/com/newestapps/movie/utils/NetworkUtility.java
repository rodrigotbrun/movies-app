package br.com.newestapps.movie.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;

import org.apache.commons.lang3.ArrayUtils;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;

// requires android.permission.ACCESS_NETWORK_STATE
public final class NetworkUtility {
    private NetworkUtility() {
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected());
    }

    public static int getType(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null) {
            // returns ConnectivityManager.TYPE_WIFI, ConnectivityManager.TYPE_MOBILE etc.
            return networkInfo.getType();
        } else {
            return -1;
        }
    }

    public static String getTypeName(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null) {
            return networkInfo.getTypeName();
        } else {
            return null;
        }
    }

    public static String getIpAddressFromWifiInfo(WifiInfo wifiInfo) {
        try {
            byte[] myIPAddress = BigInteger.valueOf(wifiInfo.getIpAddress()).toByteArray();
            ArrayUtils.reverse(myIPAddress);
            InetAddress myInetIP = null;
            myInetIP = InetAddress.getByAddress(myIPAddress);
            String ip = myInetIP.getHostAddress();
            return ip;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return null;
    }
}