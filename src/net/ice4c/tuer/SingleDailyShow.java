package net.ice4c.tuer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.ice4c.common.ImageUtil;
import net.ice4c.common.ListAdapterByNetImage;
import net.ice4c.common.HttpUtil;
import net.ice4c.common.HttpUtil.OnCallback;
import net.ice4c.common.TitleBarHandler;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import static net.ice4c.tuer.Params.*;

public class SingleDailyShow extends Activity implements OnCallback,
		TitleBarHandler {
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listlayout);
		ListView lv = getListView();
		bookurl = (String) getIntent().getExtras().get("bookurl");
		if (ShowMainList.userid == null) {
			Button btn = (Button) findViewById(R.id.title_request);
			btn.setVisibility(View.INVISIBLE);
		}
		// Button btn = (Button) findViewById(R.id.singledaily_btn_flow);
		// btn.setOnClickListener(this);
		try {
			HttpUtil.getHTML(URL_MSITE + bookurl, this);
		} catch (Exception e) {
			Log.e("单一日志", "读取网络失败", e);
		}
	}

	private ListView getListView() {
		return (ListView) findViewById(R.id.list);
	}

	private ListAdapter getListAdapter() {
		return getListView().getAdapter();
	}

	private void setListAdapter(ListAdapter adapter) {
		getListView().setAdapter(adapter);
	}

	private String bookurl, bookname;

	public void onCallback(Message msg) {
		try {
			String infoString = (String) msg.getData().get("data");
			List<Map<String, String>> ls = new ArrayList<Map<String, String>>();
			Matcher matcher = Pattern.compile(PATTERN_SINGLE_DIARY_INFO_NOLOG)
					.matcher(infoString);
			if (matcher.find()) {
				String title = matcher.group(1);
				// String time = matcher.group(2);
				String image = matcher.group(3);
				// String imageurl = matcher.group(4);
				String userid = matcher.group(5);
				String username = matcher.group(6);
				bookname = title;
				String content = matcher.group(7);
				Map<String, String> map = new HashMap<String, String>();
				map.put("usernick", userid);
				map.put("username", username);
				map.put("content", content);
				map.put("photo", URL_USER_HEAD + userid);
				map.put("img", Pattern.compile("<p> <img src=\"(.*)\"/></p>")
						.matcher(image).replaceAll("$1"));
				ls.add(map);
			}
			Matcher matcher2 = Pattern.compile(
					PATTERN_SINGLE_DIARY_INFO_NOLOG_COMENTS)
					.matcher(infoString);
			while (matcher2.find()) {
				String usernick = matcher2.group(1);
				String username = matcher2.group(2);
				String content = matcher2.group(3);
				Map<String, String> map = new HashMap<String, String>();
				map.put("usernick", usernick);
				map.put("username", username);
				map.put("content", content);
				map.put("photo", URL_USER_HEAD + usernick);
				ls.add(map);
			}
			setListAdapter(new ListAdapterByNetImage(
					SingleDailyShow.this,
					ls,
					R.layout.dailyitem,
					new String[] { "username", "bookname", "content", "photo",
							"img" },
					new int[] { R.id.item_username, R.id.item_title,
							R.id.item_content, R.id.itm_userface, R.id.item_img }));
		} catch (Exception e) {
			Log.e("html", "err", e);
		}
	}

	@Override
	public void onBackButtonClick(View view) {
		finish();
	}

	@Override
	public void onReplyButtonClick(View view) {
		Intent it = new Intent(this, CommentSaveActivity.class);
		it.putExtra("bookurl", bookurl);
		it.putExtra("bookname", bookname);
		startActivity(it);
	}
}
