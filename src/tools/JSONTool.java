package tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class JSONTool {

	private enum Method {
		POST, GET;
	}

	public static String getJSONStringByGet(String requestUrl) {
		return getHttpRequest(requestUrl, Method.GET, null);
	}

	public static String getJSONStringByPost(String requestUrl, String jsonStr) {
		return getHttpRequest(requestUrl, Method.POST, jsonStr);
	}

	private static String getHttpRequest(String requestUrl,
			Method requestMethod, String jsonStr) {
		StringBuffer sb = new StringBuffer();

		InputStream ips = null;

		if (Method.GET == requestMethod) {
			ips = getInputStream(requestUrl);
		} else if (Method.POST == requestMethod) {
			ips = getOutputStream(requestUrl, jsonStr);
		}

		InputStreamReader isreader = null;
		try {
			isreader = new InputStreamReader(ips, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		BufferedReader bufferedReader = new BufferedReader(isreader);
		String temp = null;
		try {
			while ((temp = bufferedReader.readLine()) != null) {
				sb.append(temp);
			}
			bufferedReader.close();
			isreader.close();
			ips.close();
			ips = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * 发送GET请求
	 * 
	 * @param requestUrl
	 * @param requestMethod
	 * @return
	 */
	private static InputStream getInputStream(String requestUrl) {
		URL url = null;
		HttpURLConnection conn = null;
		InputStream in = null;
		try {
			url = new URL(requestUrl);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		try {
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setRequestMethod("GET");
			conn.connect();
			in = conn.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return in;
	}

	/**
	 * 发送POST请求
	 * 
	 * @param requestUrl
	 * @param requestMethod
	 * @return
	 */
	private static InputStream getOutputStream(String requestUrl, String jsonStr) {
		URL url = null;
		HttpURLConnection conn = null;
		InputStream in = null;
		try {
			url = new URL(requestUrl);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		try {
			conn = (HttpURLConnection) url.openConnection();

			// 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在
			// http正文内，因此需要设为true, 默认情况下是false;
			conn.setDoOutput(true);
			// 设置是否从httpUrlConnection读入，默认情况下是true;
			conn.setDoInput(true);
			// Post 请求不能使用缓存
			conn.setUseCaches(false);

			conn.setRequestProperty("Content-type",
					"application/x-java-serialized-object");

			conn.setRequestMethod("POST");
			conn.connect();

			// 获取URLConnection对象对应的输出流
			PrintWriter out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(jsonStr);
			// flush输出流的缓冲
			out.flush();

			in = conn.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return in;
	}
}
