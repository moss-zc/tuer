package net.ice4c.tuer.activity;

import net.ice4c.tuer.R;
import net.ice4c.tuer.dao.ITokenDao;
import net.ice4c.tuer.dao.impl.TokenDao;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import static net.ice4c.tuer.api.Uri.*;
public class AuthActivity extends Activity {
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.auth); 
		//获取token的连接文件
		tokenDao = new TokenDao(this);
		final WebView wv = (WebView)findViewById(R.id.auth_web_view);
		wv.setWebViewClient(new WebViewClient(){
			
			/**
			 * 在跳转的时候监听
			 */
			public void onLoadResource(WebView view, String url) {
				if(url.startsWith(REDIRECT_URI)){
					String msg =  url.split("=")[1];
					if(!"".equals(msg)){
						wv.destroy();
						Intent it = new Intent(AuthActivity.this, ShowMainList.class);
						startActivity(it);
					}
				}
			}
		});
		//弹出窗口
		wv.loadUrl(AUTH_PAGE);
	}
	
	private ITokenDao tokenDao;
}
