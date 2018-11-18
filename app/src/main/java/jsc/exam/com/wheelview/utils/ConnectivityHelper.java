package jsc.exam.com.wheelview.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.Objects;

/**
 * 
 * ConnectivityHelper 网络工具
 *
 *
 */
public class ConnectivityHelper {
	
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		Objects.requireNonNull(cm);
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		return networkInfo != null && networkInfo.isAvailable();
	}
	
	/**
	 * 判断网络是否可用
	 * 
	 * @param context context
	 * @return boolean
	 */
	public static boolean isConnectivityAvailable(Context context) {
		// 判断网络是否可用
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		Objects.requireNonNull(cm);
        NetworkInfo info = cm.getActiveNetworkInfo();
		if (info == null || !info.isConnected()) {
			return false;
		}
		return info.isAvailable() || info.isRoaming();
	}


}
