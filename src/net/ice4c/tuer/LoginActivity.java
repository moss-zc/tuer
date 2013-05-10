package net.ice4c.tuer;

import static net.ice4c.tuer.Params.PATTERN_MY_DIARYS_LOGIN;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HandshakeCompletedListener;

import net.ice4c.common.HttpUtil;
import net.ice4c.common.HttpUtil.OnCallback;
import net.ice4c.common.TitleBarHandler;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources.Theme;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener,
		TitleBarHandler, OnCallback {
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		Button login_btn = (Button) findViewById(R.id.login_btn_login);
		login_btn.setOnClickListener(this);
		Button login = (Button) findViewById(R.id.title_request);
		login.setText("登录");
		login_btn.setOnClickListener(this);
		di = new Dialog(this, android.R.style.Theme_NoTitleBar);
		di.setTitle("请稍候");
		di.getWindow().setLayout(200, 200);
		di.setContentView(R.layout.loading);
	}

	Dialog di = null;

	public void onClick(View arg0) {
		EditText usernamefield = (EditText) findViewById(R.id.login_field_username);
		EditText passwordfield = (EditText) findViewById(R.id.login_field_password);
		String username = usernamefield.getText().toString();
		String password = passwordfield.getText().toString();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("email", username));
		params.add(new BasicNameValuePair("pwd", password));
		try {
			di.show();
			HttpUtil.post(Params.URL_LOGIN, params, this);
			Toast.makeText(LoginActivity.this, "正在提交数据", 1000).show();
		} catch (Exception e) {
			Log.e("login", "读取post网络数据错误", e);
			Toast.makeText(LoginActivity.this, "网络不通，请重试", 1000).show();
			di.dismiss();
		}
	}

	public void onCallback(Message msg) {
		String data = msg.getData().getString("data");
		if (data == null) {
			Toast.makeText(LoginActivity.this, "网络错误", 3000).show();
			di.dismiss();
			return;
		}
		Matcher matcher = Pattern.compile(PATTERN_MY_DIARYS_LOGIN)
				.matcher(data);
		if (!matcher.find()) {
			Toast.makeText(LoginActivity.this, "用户名或密码错误", 3000).show();
			di.dismiss();
			return;
		}
		// 新建一个跳转，跳转到实际页面
		getIntent().putExtra("data", data);
		setResult(R.layout.login, getIntent());
		di.dismiss();
		finish();
	}

	@Override
	public void onBackButtonClick(View view) {
		setResult(RESULT_CANCELED);
		finish();
	}

	@Override
	public void onReplyButtonClick(View view) {
		onClick(view);
	}
}
