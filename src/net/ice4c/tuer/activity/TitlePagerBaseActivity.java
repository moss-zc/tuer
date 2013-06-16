package net.ice4c.tuer.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.ice4c.common.HttpUtil;
import net.ice4c.common.ListAdapterByNetImage;

import org.apache.http.client.ClientProtocolException;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

public abstract class TitlePagerBaseActivity extends TitleBaseActivity implements OnScrollListener, OnItemClickListener,HttpUtil.OnCallback, OnClickListener  {
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ListView lv = getListView();
		footer = new Button(this);
		footer.setText("下页");
		footer.setOnClickListener(this);
		lv.addFooterView(footer);
		lv.setOnScrollListener(this);
	}
	@Override
	public void onClick(View v) {
		loadData();
	}
	protected abstract String getPageUrl(int page);
	protected abstract ListView getListView() ;

	protected ListAdapter getListAdapter() {
		return getListView().getAdapter();
	}

	protected void setListAdapter(ListAdapter adapter) {
		getListView().setAdapter(adapter);
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		if (firstVisibleItem + visibleItemCount == totalItemCount)
			loadData();
	}
	protected List<Map<String, String>> itemList = new ArrayList<Map<String, String>>();
	private int page = 0, totalpage = 1;
	private boolean loading = false;
	private Button footer ;
	protected void loadData() {
		++page;
		footer.setText("载入第"+page+"页");
		footer.setEnabled(false);
		try {
			if (!loading && page < totalpage) {
				HttpUtil.get(getPageUrl(page), this);
				loading = !loading;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	protected abstract int getDataPages(String data);
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
	}
	@Override
	public void onCallback(Message msg) {
		loading = !loading;
		footer.setEnabled(true);
		footer.setText("当前第"+page+"页，共"+totalpage+"页");
		String data = msg.getData().getString("data");
		onCallback(data);
		((ListAdapterByNetImage)((HeaderViewListAdapter) getListAdapter()).getWrappedAdapter()).notifyDataSetChanged();
		totalpage = getDataPages(data);
	}
	public abstract void onCallback(String data) ;

}
