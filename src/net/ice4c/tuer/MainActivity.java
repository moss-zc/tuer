package net.ice4c.tuer;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

/**
 * 本界面为主窗口，主要用于展示logo，及logo播放完成之后的跳转活动。
 * 
 * @author ice4cp-
 * 
 */
public class MainActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 取消标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.logo);
		// 开始播放动画
		ImageView iv = (ImageView) findViewById(R.id.img_danceing);
		AnimationDrawable danceing = (AnimationDrawable) iv.getDrawable();
		danceing.start();
		// 在另外一个线程执行任务，当任务准备好之后启动下一主界面
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				Intent it = new Intent(MainActivity.this, ShowMainList.class);
				startActivity(it);
				finish();
			}
		}, 3000);

	}
}