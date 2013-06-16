package net.ice4c.tuer.activity;

import static net.ice4c.tuer.activity.Params.PATTERN_ALL_DIARYS_NOLOG;
import static net.ice4c.tuer.activity.Params.URL_ALL_DIARYS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.ice4c.common.HttpUtil;
import net.ice4c.common.HttpUtil.OnCallback;
import net.ice4c.common.ListAdapterByNetImage;
import net.ice4c.common.TitleBarHandler;
import net.ice4c.tuer.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DailyListActivity extends TitlePagerBaseActivity implements OnCallback,
		TitleBarHandler, OnItemClickListener {
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.listlayout);
		super.onCreate(savedInstanceState);
		ListView lv = getListView();
		Button btn = (Button) findViewById(R.id.title_request);
		btn.setVisibility(View.INVISIBLE);
		lv.setOnItemClickListener(this);
		getListView().setAdapter(
				new ListAdapterByNetImage(DailyListActivity.this, itemList,
						R.layout.dailyitem, new String[] { "username",
								"bookname", "content", "photo", "img" },
						new int[] { R.id.item_username, R.id.item_title,
								R.id.item_content, R.id.itm_userface,
								R.id.item_img }));
	}
	

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Map<String, String> map = (Map) arg0.getAdapter().getItem(arg2);
		Intent intent = new Intent(DailyListActivity.this,
				SingleDailyShow.class);
		intent.putExtra("bookurl", map.get("bookurl"));
		startActivity(intent);
	}

	@Override
	public void onCallback(String diarysStr) {
		// 保存获取到的信息的list
		List<Map<String, String>> ls = new ArrayList<Map<String, String>>();
			Pattern pattern = Pattern.compile(PATTERN_ALL_DIARYS_NOLOG);
			Matcher matcher = pattern.matcher(diarysStr);
			while (matcher.find()) {
				String bookurl = matcher.group(1);
				String bookname = matcher.group(2);
				String userid = matcher.group(3);
				String username = matcher.group(4);
				String img = matcher.group(5);
				String content = matcher.group(6);
				Map<String, String> map = new HashMap<String, String>();
				map.put("bookurl", bookurl);
				map.put("bookname", bookname);
				map.put("userid", userid);
				map.put("username", username);
				map.put("img",
						Pattern.compile("<img src=\"(.*)\"/>").matcher(img)
								.replaceAll("$1"));
				map.put("photo", "http://www.tuer.me/user/avatar/" + userid);
				map.put("content", content);
				itemList.add(map);
			}
	}

	@Override
	public void onBackButtonClick(View view) {
		finish();
	}

	@Override
	public void onReplyButtonClick(View view) {
	}

	@Override
	protected String getPageUrl(int page) {
		return URL_ALL_DIARYS+'/' + page;
	}

	@Override
	protected ListView getListView() {
		return (ListView) findViewById(R.id.list);
	}

	@Override
	public int getDataPages(String data){
		Matcher matcher = Pattern.compile(
				"<a href=\"/diaries/([^\"]*)\">尾页</a>").matcher(
				data);
		if (matcher.find()) {
			return Integer.parseInt(matcher.group(1));
		}
		return 0;
	}

}
