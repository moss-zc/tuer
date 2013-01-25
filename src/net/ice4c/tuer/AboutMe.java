package net.ice4c.tuer;

import net.ice4c.common.TitleBarHandler;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AboutMe extends Activity implements TitleBarHandler{
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aboutme);
		Button cancel = (Button)findViewById(R.id.title_cancel);
		Button request = (Button)findViewById(R.id.title_request);
		request.setVisibility(View.GONE);
		cancel.setText("真可怜");
		TextView text = (TextView)findViewById(R.id.title_text);
		text.setText("关于我们");
	}

	@Override
	public void onBackButtonClick(View view) {
		finish();
	}

	@Override
	public void onReplyButtonClick(View view) {
		// TODO Auto-generated method stub
		
	}
}
