package net.ice4c.common;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.WeakHashMap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

public class ImageUtil {
	public static byte[] getImage(String path) throws Exception {
		URL url = new URL(path);
		HttpURLConnection httpURLconnection = (HttpURLConnection) url
				.openConnection();
		httpURLconnection.setRequestMethod("GET");
		httpURLconnection.setReadTimeout(6 * 1000);
		InputStream in = null;
		if (httpURLconnection.getResponseCode() == 200) {
			in = httpURLconnection.getInputStream();
			byte[] result = readStream(in);
			in.close();
			return result;

		}
		return null;
	}

	public static byte[] readStream(InputStream in) throws Exception {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		while ((len = in.read(buffer)) != -1) {
			outputStream.write(buffer, 0, len);
		}
		outputStream.close();
		in.close();
		return outputStream.toByteArray();
	}

	/**
	 * 使用一个比较弱的map，这个map有比较高的GC回收率，用于放置一些弱引用的image更合适。
	 */
	private static Map<String, Bitmap> imageMap = new WeakHashMap<String, Bitmap>();

	/**
	 * 从本地数据库读取图片
	 * 
	 * @param path
	 * @return
	 */
	public static Bitmap getBitMapFromDb(String path) {
		return null;
	}

	public static Bitmap getBitMap(String path) {
		return imageMap.get(path);
	}

	/**
	 * 从网络读取图片并保存到内存和数据库，下次如果这个图片的地址再次出现，先从内存读取，再读取数据库，最后从网络获取，这样可以避免获取过于频繁，
	 * 从而节约资源。
	 * 
	 * @param path
	 * @return
	 */
	private static Timer timer = new Timer();

	public static void drawTheImageViewFromNet(final ImageView iv,
			final String path) {
		if (path == null || "".equals(path))
			return;
		Bitmap bitmap = imageMap.get(path);
		if (bitmap == null)
			bitmap = ImageUtil.getBitMapFromDb(path);
		if (bitmap != null) {
			iv.setImageBitmap(bitmap);
			return;
		}

		final String fv = path;
		final ImageView fvv = iv;
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				byte[] imagebytes = (byte[]) msg.getData().get("imagebytes");
				Options op = new Options();
				op.outWidth = 200;
				op.outHeight = 200;
				Bitmap map = BitmapFactory.decodeByteArray(imagebytes, 0,
						imagebytes.length, op);
				map.setDensity(150);
				imageMap.put(path, map);
				fvv.setImageBitmap(map);
				fvv.setMaxHeight(100);
			}
		};
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				Message msg = new Message();
				Bundle bd = new Bundle();
				try {
					bd.putSerializable("imagebytes", ImageUtil.getImage(fv));
					msg.setData(bd);
					handler.sendMessage(msg);
				} catch (Exception e) {
					Log.e("tuer", "无法获取网络图片", e);
				}
			}
		}, 0);
	}
}
