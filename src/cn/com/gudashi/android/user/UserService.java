package cn.com.gudashi.android.user;

import android.content.Context;
import android.content.SharedPreferences;
import cn.com.gudashi.android.SharedPrefsKey;

public class UserService {
	private static SharedPreferences getSharedPreferences(Context context){
		return context.getSharedPreferences(SharedPrefsKey.USER, Context.MODE_PRIVATE);
	}

	public static String getLoggedInUser(Context context) {
		return getSharedPreferences(context)
				.getString("loggedIn", null);
	}

	public static void setLoggedInUser(Context context, String username) {
		getSharedPreferences(context)
		.edit()
		.putString("loggedIn", username)
		.commit();
	}
}
