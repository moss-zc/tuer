package net.ice4c.tuer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.ice4c.common.HttpUtil;
import net.ice4c.common.HttpUtil.OnCallback;
import net.ice4c.common.TitleBarHandler;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class WriteDailyActivity extends Activity implements TitleBarHandler,
		OnCallback, OnClickListener {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.writediary);
		setContentView(R.layout.writediary_onlycontent);
		List<Map<String, String>> ls = ShowMainList.books;
		Spinner spinner_book = (Spinner) findViewById(R.id.write_field_book);
		spinner_book.setAdapter(new SimpleAdapter(this, ls,
				android.R.layout.simple_spinner_dropdown_item,
				new String[] { "view" }, new int[] { android.R.id.text1 }));
		// Spinner spinner_mind = (Spinner) findViewById(R.id.write_field_mind);
		// spinner_mind.setAdapter(ArrayAdapter.createFromResource(this,R.array.write_sniper_mind,android.R.layout.simple_spinner_dropdown_item));
		// Spinner spinner_weather = (Spinner)
		// findViewById(R.id.write_field_weather);
		// spinner_weather.setAdapter(ArrayAdapter.createFromResource(this,R.array.write_sniper_weather,android.R.layout.simple_spinner_dropdown_item));
		// Button btn = (Button) findViewById(R.id.write_field_submit);
		// btn.setOnClickListener(this);
		Button btn = (Button) findViewById(R.id.title_request);
		btn.setText("保存");
	}

	public void onClick(View arg) {
		Spinner spinner_book = (Spinner) findViewById(R.id.write_field_book);
		// Spinner spinner_mind = (Spinner) findViewById(R.id.write_field_mind);
		// Spinner spinner_weather = (Spinner)
		// findViewById(R.id.write_field_weather);
		EditText ta_content = (EditText) findViewById(R.id.write_field_content);
		// EditText et_location =(EditText)
		// findViewById(R.id.write_field_location);
		String bookId = (String) ((Map<String, String>) spinner_book
				.getSelectedItem()).get("id");
		// int mind = spinner_mind.getSelectedItemPosition();
		// int weather = spinner_weather.getSelectedItemPosition();
		String content = ta_content.getEditableText().toString();
		// String location = et_location.getEditableText().toString();
		List<NameValuePair> ls = new ArrayList();
		ls.add(new BasicNameValuePair("bookid", bookId) );
		ls.add(new BasicNameValuePair("content", content) );
		try {
			HttpUtil.post("http://m.tuer.me/diary/save", ls, this);
		} catch (Exception e) {
			Log.e("write", "编写日志时出现错误！", e);
		}
	}

	@Override
	public void onBackButtonClick(View view) {
		finish();
	}

	@Override
	public void onReplyButtonClick(View view) {
		onClick(view);
	}

	@Override
	public void onCallback(Message msg) {
		String str = msg.getData().getString("data");
		Toast.makeText(this, str, 100000);
		finish();
	}
}
