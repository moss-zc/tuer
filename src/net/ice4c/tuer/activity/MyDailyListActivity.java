package net.ice4c.tuer.activity;

import static net.ice4c.tuer.activity.Params.PATTERN_MY_DIARYS_LOGIN;
import static net.ice4c.tuer.activity.Params.URL_USER_HEAD;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;

import net.ice4c.common.ListAdapterByNetImage;
import net.ice4c.common.HttpUtil;
import net.ice4c.common.TitleBarHandler;
import net.ice4c.tuer.R;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.HeaderViewListAdapter;
import android.widget.LinearLayout;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;

public class MyDailyListActivity extends TitlePagerBaseActivity implements
		HttpUtil.OnCallback, OnScrollListener, OnItemClickListener,
		TitleBarHandler {
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.listlayout);
		super.onCreate(savedInstanceState);
		setListAdapter(new ListAdapterByNetImage(MyDailyListActivity.this,
				itemList, R.layout.dailyitem, new String[] { "username",
						"bookname", "content", "photo", "img" }, new int[] {
						R.id.item_username, R.id.item_title, R.id.item_content,
						R.id.itm_userface, R.id.item_img }));
		getListView().setOnItemClickListener(this);
		loadData();
	}

	public ListView getListView() {
		return (ListView) findViewById(R.id.list);
	}

	protected String getPageUrl(int page){
		return Params.URL_MSITE + "/profile/"
				+ ShowMainList.userid + "/diaries/" + page;
	}
	@Override
	public void onCallback(String data) {
		Matcher matcher2 = Pattern.compile(PATTERN_MY_DIARYS_LOGIN).matcher(
				data);
		while (matcher2.find()) {
			String bookurl = matcher2.group(1);
			String bookname = matcher2.group(2);
			String img = matcher2.group(3);
			String content = matcher2.group(4);
			Map<String, String> map = new HashMap<String, String>();
			map.put("bookurl", bookurl);
			map.put("bookname", bookname);
			map.put("content", content);
			map.put("username", ShowMainList.name);
			map.put("photo", Params.URL_USER_HEAD + "/" + ShowMainList.userid);
			map.put("img", Pattern.compile("<img src=\"(.*)\"/>").matcher(img)
					.replaceAll("$1"));
			itemList.add(map);
		}

	}
	public int getDataPages(String data){
		Matcher matcher = Pattern.compile(
				"<a href=\"/profile/[^/]*/diaries/([^\"]*)\">尾页</a>").matcher(
				data);
		if (matcher.find()) {
			return Integer.parseInt(matcher.group(1));
		}
		return 0;
	}
	

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Map<String, String> item = (Map<String, String>) arg0.getAdapter()
				.getItem(arg2);
		Intent it = new Intent(this, SingleDailyShow.class);
		it.putExtra("bookurl", item.get("bookurl"));
		startActivity(it);
	}

	@Override
	public void onBackButtonClick(View view) {
		finish();
	}

	@Override
	public void onReplyButtonClick(View view) {

	}
}
