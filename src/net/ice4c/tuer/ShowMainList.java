package net.ice4c.tuer;

import static net.ice4c.tuer.Params.PATTERN_SINGLE_DIARY_INFO_NOLOG_COMENTS;
import static net.ice4c.tuer.Params.URL_USER_HEAD;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.ice4c.common.HttpUtil;
import net.ice4c.common.HttpUtil.OnCallback;
import net.ice4c.common.ImageUtil;
import net.ice4c.common.TitleBarHandler;

import android.app.Activity;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ShowMainList extends TitleBaseActivity implements OnCallback,OnClickListener {
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 取消标题栏
		setContentView(R.layout.main);
		setCancleBtnText("退出");
		setRequestBtnText("登录");
	}
	@Override
	public void onClick(View view) {
		Intent it = null;
		switch(view.getId()){
			case R.id.main_btn_alldaily: it = new Intent(this,DailyListActivity.class);break;
			case R.id.main_btn_mydaily: it = new Intent(this,MyDailyListActivity.class);break;
			case R.id.main_btn_writing: it = new Intent(this,WriteDailyActivity.class);break;
			case R.id.main_btn_myfrends: it = new Intent(this,DailyListActivity.class);break;
		}
		startActivity(it);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(1, 1, 1, "退出");
		menu.add(1, 2, 2, "关于");
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == 1) finish();
		if(item.getItemId() == 2) startActivity(new Intent(this,AboutMe.class));
		return super.onOptionsItemSelected(item);
	}
	public static String userid;
	public static String name;
	public static String prefix;
	public static List books;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == R.layout.login) {
			String data1 = data.getExtras().getString("data");
			String userid_paten = "<a href=\"/profile/([^/]*)/diaries/1\">1</a>";
			// String name_paten = "<h1>欢迎,(.*)! <small>(.*)</small></h1>";
			String name_paten = "<h1>([^>]*?)<small>";
			String prefix_paten = "<a href=\"/([^\"])\" class=\"\">全部日记</a>";
			Matcher matcher = Pattern.compile(userid_paten).matcher(data1);
			if (matcher.find()) {
				userid = matcher.group(1);
			}
			Button rbtn = (Button) findViewById(R.id.title_request);
			rbtn.setVisibility(View.INVISIBLE);
			Matcher matcher_name = Pattern.compile(name_paten).matcher(data1);
			if (matcher_name.find()) {
				name = matcher_name.group(1);
				setTitleText(name);
			}
			Matcher matcher_prefix = Pattern.compile(prefix_paten).matcher(data1);
			if (matcher_prefix.find()) {
				prefix = matcher_prefix.group(1);
			}
			try {
				HttpUtil.get("http://m.tuer.me/diary/write", this);
			} catch (Exception e) {
				Log.e("错误", "读取日记本列表错误", e);
			}
			super.onActivityResult(requestCode, resultCode, data);
		}
	}

	@Override
	public void onBackButtonClick(View view) {
		finish();
	}

	@Override
	public void onReplyButtonClick(View view) {
		Intent it = new Intent(ShowMainList.this, LoginActivity.class);
		startActivityForResult(it, R.layout.login);
	}

	@Override
	public void onCallback(Message msg) {
		String msgstr = msg.getData().getString("data");
		String pattern = "<option value=\"([^\"]*)\">([^<]*)</option>";
		Matcher mc = Pattern.compile(pattern).matcher(msgstr);
		List<Map<String, String>> ls = new LinkedList<Map<String, String>>();
		while (mc.find()) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", mc.group(1));
			map.put("view", mc.group(2));
			ls.add(map);
		}
		books = ls;
	}
}
