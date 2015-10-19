package tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class JSONTool {

	private enum Method {
		POST, GET;
	}

	public static String getJSONStringByGet(String requestUrl) {
		return getHttpRequest(requestUrl, Method.GET);
	}

	public static String getJSONStringByPost(String requestUrl) {
		return getHttpRequest(requestUrl, Method.POST);
	}

	private static String getHttpRequest(String requestUrl, Method requestMethod) {
		StringBuffer sb = new StringBuffer();
		InputStream ips = getInputStream(requestUrl, requestMethod);
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

	private static InputStream getInputStream(String requestUrl,
			Method requestMethod) {
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
			if (requestMethod == Method.POST) {
				conn.setRequestMethod("POST");
			} else {
				conn.setRequestMethod("GET");
			}
			conn.connect();
			in = conn.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return in;
	}
}
