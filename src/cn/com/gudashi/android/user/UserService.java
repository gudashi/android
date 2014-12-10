package cn.com.gudashi.android.user;

import android.content.Context;
import android.content.SharedPreferences;
import cn.com.gudashi.android.SharedPrefsKey;
import cn.com.gudashi.domain.User;
import cn.com.gudashi.utils.OjmUtils;

public class UserService {
	private static SharedPreferences getSharedPreferences(Context context){
		return context.getSharedPreferences(SharedPrefsKey.USER, Context.MODE_PRIVATE);
	}

	public static User getLoggedInUser(Context context) {
		String json = getSharedPreferences(context)
						.getString("loggedIn", null);
		if(json == null){
			return null;
		}else{
			try{
				return OjmUtils.fromJson(json, User.class);
			}catch(Exception ex){
				return null;
			}
		}
	}

	public static void setLoggedInUser(Context context, User user) {
		try{
			getSharedPreferences(context)
			.edit()
			.putString("loggedIn", user == null ? null : OjmUtils.toJson(user))
			.commit();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
