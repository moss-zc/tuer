package net.ice4c.tuer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.ice4c.common.HttpUtil;
import net.ice4c.common.HttpUtil.OnCallback;
import net.ice4c.common.TitleBarHandler;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class CommentSaveActivity extends Activity implements TitleBarHandler {
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.flowdaily);
		Intent it = getIntent();
		bookid = it.getExtras().getString("bookurl").replace("/diary/", "");
		bookname = it.getExtras().getString("bookname");
		TextView tv = (TextView) findViewById(R.id.flow_name);
		tv.setText(bookname);
	}

	private String bookid;
	private String bookname;

	@Override
	public void onBackButtonClick(View view) {
		finish();
	}

	@Override
	public void onReplyButtonClick(View view) {
		EditText et = (EditText) findViewById(R.id.flow_content);
		String content = et.getEditableText().toString();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("content", content));
		params.add(new BasicNameValuePair("diaryid", bookid));
		try {
			HttpUtil.post(Params.URL_COMMENT_SAVE, params, new OnCallback() {
				public void onCallback(Message msg) {
					finish();
				}
			});
		} catch (ClientProtocolException e) {
			Log.e("保存", "保存回复失败", e);
		} catch (IOException e) {
			Log.e("保存", "保存回复失败", e);
		} catch (Exception e) {
			Log.e("保存", "保存回复失败", e);
		}
	}
}
