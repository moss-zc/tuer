package net.ice4c.tuer;

import net.ice4c.common.TitleBarHandler;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TitleBaseActivity extends Activity implements TitleBarHandler{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	/**
	 * 设置回复（功能）按钮的名称
	 * @param text
	 */
	protected void setRequestBtnText(String text){
		getRequestBtn().setText(text);
	}
	/**
	 * 设置取消按钮显示的文字
	 * @param text
	 */
	protected void setCancleBtnText(String text){
		getCancleBtn().setText(text);
	}

	/**
	 * 获得中部的文字对象
	 * @return
	 */
	protected TextView getTitleView(){
		return (TextView) findViewById(R.id.title_text);
	}
	/**
	 * 获得取消按钮对象
	 * @return
	 */
	protected Button getCancleBtn(){
		return (Button) findViewById(R.id.title_cancel);
	}
	/**
	 * 获得回复按钮对象
	 * @return
	 */
	protected Button getRequestBtn(){
		return (Button) findViewById(R.id.title_request);
	}
	/**
	 * 设置中间区域显示的文字
	 * @param text
	 */
	protected void setTitleText(String text){
		getTitleView().setText(text);
	}

	/**
	 * 设置功能按钮显示方式
	 * @param visibility
	 */
	protected void setRequestBtnVisible(int visibility){
		getRequestBtn().setVisibility(visibility);
	}
	/**
	 * 设置取消按钮显示方式
	 * @param visibility
	 */
	protected void setCancleBtnVisible(int visibility){
		getCancleBtn().setVisibility(visibility);
	}
	@Override
	public void onBackButtonClick(View view) {
		finish();
	}
	@Override
	public void onReplyButtonClick(View view) {
		
	}
}
