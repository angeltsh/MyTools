package test;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import tools.EmailTool;
import tools.JSONTool;

public class WeiMeng {

	private static String ACCESS_TOKEN;

	private final static String APPID = "03d287ac78a655b76cdd8abf62582c8c";
	private final static String APPSECRET = "3543208276b89b9f016eca56f99ba406";

	private static String http_access_token = "https://open.weimob.com/common/token?grant_type=client_credential&appid="
			+ APPID + "&secret=" + APPSECRET;
	private static String http_orderList = "https://open.weimob.com/api/mname/WE_MALL/cname/orderGet?accesstoken=";

	public static void main(String[] args) {
		// ACCESS_TOKEN = getAccessToken();// 真实环境测试
		ACCESS_TOKEN = "550a34784643b0cad152dd8171d41fdf5acd5f0bdb2a59ff4e388524c15fa070dd96d11576006fc3722609e6a52e8d8597f73afcf1616a61027cce7e1f3d9f22";//

		WeiMeng wm = new WeiMeng();

		WeiMengOrder orders = wm.getReturnOrderJson();

		System.out.println(orders.getData());

		StringBuffer html = wm.getHTMLOrder(orders);

		new EmailTool().sendEmail("674725574@qq.com", "微信商城订单信息",
				html.toString());

	}

	private StringBuffer getHTMLOrder(WeiMengOrder orders) {

		StringBuffer sb = new StringBuffer();
		sb.append("");

		sb.append(orders.getData().getPage_data().get(0).getGoods().get(0)
				.getItemName());

		return sb;
	}

	private WeiMengOrder getReturnOrderJson() {
		JSONObject orderData = new JSONObject();

		orderData.put("order_type", 0);
		orderData.put("search_type", 1);
		// orderData.put("search", "");//查询条件
		orderData.put("order_state", 0);
		orderData.put("order_fields", "*");
		orderData.put("order_detail_fields", "*");
		// orderData.put("create_begin_time", 0);
		// orderData.put("create_end_time", 0);
		// orderData.put("update_begin_time", 0);
		// orderData.put("update_end_time", 0);
		orderData.put("page_size", 1);
		orderData.put("page_no", 1);

		System.out.println(orderData);

		String jsonStr = JSONTool.getJSONStringByPost(http_orderList
				+ ACCESS_TOKEN, orderData.toString());

		System.out.println(jsonStr);

		Gson gson = new Gson();
		return gson.fromJson(jsonStr, WeiMengOrder.class);
	}

	private String getAccessToken() {
		String jsonStr = JSONTool.getJSONStringByGet(http_access_token);

		String access_token = (String) (new JSONObject(jsonStr)
				.getJSONObject("data").get("access_token"));

		System.out.println(access_token);
		return access_token;
	}
}
