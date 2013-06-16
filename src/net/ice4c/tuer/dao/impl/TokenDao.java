package net.ice4c.tuer.dao.impl;

import net.ice4c.tuer.activity.AuthActivity;
import net.ice4c.tuer.dao.ITokenDao;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import static net.ice4c.tuer.api.App.*;
public class TokenDao implements ITokenDao {
	public static final String TOKEN_KEY = "token";//
	public static final String USER_KEY = "user";//
	public static final String DEFAULT_KEY = null;
	private Activity context;
	public TokenDao(Activity context){
		this.context = context;
	}
	@Override
	public String getToken(Long userid) {
		SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_NAME, context.MODE_PRIVATE);
		String token = preferences.getString(TOKEN_KEY, DEFAULT_KEY);
		if(token == DEFAULT_KEY){
			//如果没获取到token，则启动token获取工具
			Intent it = new Intent(context, AuthActivity.class);
			context.startActivity(it);
		}
		return token;
	}
	@Override
	public String saveToken(Long userid, String token) {
		// Use MODE_WORLD_READABLE and/or MODE_WORLD_WRITEABLE to grant access to other applications
		SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_NAME, context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putLong(USER_KEY, userid);
		editor.putString(TOKEN_KEY, token);
		editor.commit();
		return token;
	}

}
