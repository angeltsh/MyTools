package test;

import java.util.Map;

import org.json.JSONObject;

import tools.JSONTool;

public class WeiMeng {

	private static String ACCESS_TOKEN;

	private final static String APPID = "03d287ac78a655b76cdd8abf62582c8c";
	private final static String APPSECRET = "3543208276b89b9f016eca56f99ba406";

	private static String http_access_token = "https://open.weimob.com/common/token?grant_type=client_credential&appid="
			+ APPID + "&secret=" + APPSECRET;
	private static String http_orderList = "https://openosd.weimob.com/api/mname/WE_MALL/cname/orderGet?accesstoken=";

	public static void main(String[] args) {

		String jsonStr = JSONTool.getJSONStringByGet(http_access_token);

		System.out.println(jsonStr);

		System.out.println((new JSONObject(jsonStr).get("data")));

	}
}
