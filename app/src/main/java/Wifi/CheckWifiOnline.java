package Wifi;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by jasmine on 6/26/2016.
 */
public  class CheckWifiOnline {
    public static   boolean isOnline(Context context){
        try {
            ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.WIFI_SERVICE);

            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                return true;
            } else {
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }


    }


}
