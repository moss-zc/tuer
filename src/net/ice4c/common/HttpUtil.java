package net.ice4c.common;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.WeakHashMap;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.MultihomePlainSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BufferedHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.MemoryFile;
import android.os.Message;
import android.util.Log;

/**
 * 所有http操作的集合，提供对各种get，post方法的同步及异步调用
 * @author ice4c
 *
 */
public class HttpUtil {
	/**
	 * 当异步调用完成，将使用这个类型回调
	 * @author ice4c
	 *
	 */
	public interface OnCallback {
		public void onCallback(Message msg);
	}

	/**
	 *异步的，返回String的调用
	 * @author ice4c
	 *
	 */
	private interface AsycTaskForString {
		public String doMethod() throws Exception;
	}

	/**
	 * 异步的，返回字节数组的回调
	 * @author ice4c
	 *
	 */
	private interface AsycTaskForByteArr {
		public byte[] doMethod() throws Exception;
	}

	/**
	 * 用户生成任务的计时器
	 */
	private static Timer timer = new Timer();
	
	/**
	 * 计时器的holder
	 */
	private static Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			OnCallback callback = callbacks.get(msg.what);
			if (callback != null) {
				callback.onCallback(msg);
				callbacks.remove(msg.what);
			}
		};
	};
	/**
	 * 用于区分task的编号
	 */
	private static int taskid = 0;
	
	/**
	 * 将异步调用后对象存入hashMap，在异步读取数据完成时获取
	 */
	private static Map<Integer, OnCallback> callbacks = new WeakHashMap<Integer, OnCallback>();

	
	/**
	 * 这是一个
	 * @param task
	 * @param callback
	 * @return
	 */
	private static boolean doAsyc(final AsycTaskForString task,
			final OnCallback callback) {
		callbacks.put(taskid, callback);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				String rtn = null;
				try {
					rtn = task.doMethod();
				} catch (Exception e) {
					Log.e("网络问题", "执行异步的网络请求出错", e);
					throw new HttpException("执行异步的网络请求出错",e);
				}
				Bundle bd = new Bundle();
				bd.putString("data", rtn);
				Message msg = new Message();
				msg.what = taskid++;
				// 应该不可能有这么大的value存在,但如果超过这个值，则重置它
				if (taskid == Integer.MAX_VALUE)
					taskid = 0;
				msg.setData(bd);
				handler.sendMessage(msg);
			}

		}, 0);
		return true;
	}

	// 异步执行
	private static boolean doAsyc(final AsycTaskForByteArr task,
			final OnCallback callback) {
		callbacks.put(taskid, callback);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				byte[] rtn = null;
				try {
					rtn = task.doMethod();
				} catch (Exception e) {
					Log.e("网络问题", "执行异步的网络请求出错", e);
				}
				Bundle bd = new Bundle();
				bd.putByteArray("data", rtn);
				Message msg = new Message();
				msg.what = taskid++;
				// 应该不可能有这么大的value存在,但如果超过这个值，则重置它
				if (taskid == Integer.MAX_VALUE)
					taskid = 0;
				msg.setData(bd);
				handler.sendMessage(msg);
			}

		}, 0);
		return true;
	}

	public static byte[] getImage(String path) throws Exception {
		URL url = new URL(path);
		HttpURLConnection urlConnection = (HttpURLConnection) url
				.openConnection();
		urlConnection.setRequestMethod("GET");
		urlConnection.setConnectTimeout(5000);
		InputStream inStream = urlConnection.getInputStream();
		byte[] data = readInputStream(inStream);
		return data;
	}

	/**
	 * 异步的获取image
	 * 
	 * @param path
	 * @param callback
	 * @throws Exception
	 */
	public static void getImage(final String path, OnCallback callback)
			throws Exception {
		doAsyc(new AsycTaskForByteArr() {
			public byte[] doMethod() throws Exception {
				return getImage(path);
			}
		}, callback);
	}

	private static byte[] readInputStream(InputStream inStream)
			throws IOException {
		ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		while ((len = inStream.read(buffer)) != -1) {
			byteOutputStream.write(buffer, 0, len);
		}
		inStream.close();
		return byteOutputStream.toByteArray();
	}

	public static String getHTML(String path) throws Exception {
		URL url = new URL(path);
		HttpURLConnection urlConnection = (HttpURLConnection) url
				.openConnection();
		urlConnection.setRequestMethod("GET");
		urlConnection.setConnectTimeout(5000);
		InputStream inStream = urlConnection.getInputStream();
		byte[] data = readInputStream(inStream);
		return new String(data, "utf-8");
	}

	/**
	 * 异步的使用get方法获取html
	 * 
	 * @param path
	 * @param callback
	 * @throws Exception
	 */
	public static void getHTML(final String path, OnCallback callback)
			throws Exception {
		doAsyc(new AsycTaskForString() {
			public String doMethod() throws Exception {
				return getHTML(path);
			}
		}, callback);
	}

	public static void saveFileSDcard(String filename, String text)
			throws Exception {
		// 获取SDCard所在路径，建议使用此方式
		File file = new File(Environment.getExternalStorageDirectory(),
				filename);
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		fileOutputStream.write(text.getBytes());
		fileOutputStream.close();
	}

	public static DefaultHttpClient client = new DefaultHttpClient();
	public static String cookie_sid;
	public static String cookie_pwd;
	public static String cookie_accounts;

	public static String post(String path, List<NameValuePair> params)
			throws ClientProtocolException, IOException {
		// instantiate HttpPost object from the url address
		HttpEntityEnclosingRequestBase httpRequest = new HttpPost(path);
		// the post name and value must be used as NameValuePair
		if (params != null)
			httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
		httpRequest.setHeader("Cookie", "connect.sid=" + cookie_sid);
		httpRequest.setHeader("Cookie", "pwd=" + cookie_pwd);
		httpRequest.setHeader("Cookie", "accounts=" + cookie_accounts);
		// execute the post and get the response from servers
		HttpResponse httpResponse = client.execute(httpRequest);
		String strResult = null;
		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			strResult = EntityUtils.toString(httpResponse.getEntity());
			for (Cookie cookie : client.getCookieStore().getCookies()) {
				String name = cookie.getName();
				String val = cookie.getValue();
				if (name.equals("connect.sid")) {
					cookie_sid = val;
				} else if (name.equals("pwd")) {
					cookie_pwd = cookie.getValue();
				} else if (name.equals("accounts")) {
					cookie_accounts = val;
				}
			}
			return strResult;
		}
		return strResult;
	}

	public static void post(final String path,
			final List<NameValuePair> params, OnCallback callback)
			throws Exception {
		doAsyc(new AsycTaskForString() {
			public String doMethod() throws Exception {
				return post(path, params);
			}
		}, callback);
	}

//	public static String postWithPic(String path, Map<String, String> params,
//			FilePart file) throws HttpException, IOException {
//		PostMethod post = new PostMethod(path);
//		post.setRequestHeader("charset", "utf-8");
//		int i = params.keySet().size(), j = 0;
//		if (file != null)
//			i++;
//		Part[] parts = new Part[i];
//		for (String key : params.keySet()) {
//			parts[j++] = new StringPart(key, params.get(key));
//		}
//		if (file != null)
//			parts[i - 1] = file;
//		MultipartRequestEntity entity = new MultipartRequestEntity(parts,
//				post.getParams());
//		post.setRequestEntity(entity);
//		post.addRequestHeader("Cookie", "connect.sid=" + cookie_sid);
//		post.addRequestHeader("Cookie", "pwd=" + cookie_pwd);
//		post.addRequestHeader("Cookie", "accounts=" + cookie_accounts);
//		org.apache.commons.httpclient.HttpClient cli = new org.apache.commons.httpclient.HttpClient();
//		int stat = cli.executeMethod(post);
//		if (stat == 200) {
//			return post.getResponseBodyAsString();
//		} else {
//			Log.d("tuer", "错误的代码:" + post.getResponseBodyAsString());
//			return post.getResponseBodyAsString();
//		}
//	}
//
//	/**
//	 * 异步的提交一个form（可以包含一个文件）
//	 * 
//	 * @param path
//	 * @param params
//	 * @param file
//	 * @param callback
//	 * @throws HttpException
//	 * @throws IOException
//	 */
//	public static void postWithPic(final String path,
//			final Map<String, String> params, final FilePart file,
//			OnCallback callback) throws HttpException, IOException {
//		doAsyc(new AsycTaskForString() {
//			public String doMethod() throws Exception {
//				return postWithPic(path, params, file);
//			}
//		}, callback);
//	}

	public static String get(String path) throws ClientProtocolException,
			IOException {
		// instantiate HttpPost object from the url address
		HttpGet httpRequest = new HttpGet(path);
		// the post name and value must be used as NameValuePair
		httpRequest.setHeader("Cookie", "connect.sid=" + cookie_sid);
		// execute the post and get the response from servers
		HttpResponse httpResponse = client.execute(httpRequest);
		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			String strResult = EntityUtils.toString(httpResponse.getEntity());
			for (Cookie cookie : client.getCookieStore().getCookies()) {
				if (cookie.getName().equals("connect.sid")) {
					cookie_sid = cookie.getValue();
				}
			}
			return strResult;
		}
		return null;
	}

	/**
	 * 异步的提交一个form（可以包含一个文件）
	 * 
	 * @param path
	 * @param params
	 * @param file
	 * @param callback
	 * @throws HttpException
	 * @throws IOException
	 */
	public static void get(final String path, OnCallback callback)
			throws ClientProtocolException, IOException {
		doAsyc(new AsycTaskForString() {
			public String doMethod() throws Exception {
				return get(path);
			}
		}, callback);
	}
}