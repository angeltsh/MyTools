package weimeng;

import java.util.List;

import org.json.JSONObject;

import tools.EmailTool;
import tools.JSONTool;
import tools.TimeTool;

import com.google.gson.Gson;

public class WeiMeng {

	private static String ACCESS_TOKEN;

	private final static String APPID = "03d287ac78a655b76cdd8abf62582c8c";
	private final static String APPSECRET = "3543208276b89b9f016eca56f99ba406";

	private static String http_access_token = "https://open.weimob.com/common/token?grant_type=client_credential&appid="
			+ APPID + "&secret=" + APPSECRET;
	private static String http_orderList = "https://open.weimob.com/api/mname/WE_MALL/cname/orderGet?accesstoken=";

	public static void main(String[] args) {

		WeiMeng wm = new WeiMeng();

		// ACCESS_TOKEN =
		// "d49310efda8a6101007783f742c5f7671fa9dae3316ed1e6009fc194d85ae258d537ecfe8119bfe7d174d8b1319e4541767fd0155fa6d48af264346217d91947";
		ACCESS_TOKEN = wm.getAccessToken();// 真实环境测试

		WeiMengOrder orders = wm.getReturnOrderJson(1, 1);

		StringBuffer html = wm.getHTMLOrder(orders, wm, "2015-12-01 00:00:00");

		new EmailTool().sendEmail("674725574@qq.com", "微信商城未收货订单信息核对",
				html.toString());

	}

	private StringBuffer getHTMLOrder(WeiMengOrder orders, WeiMeng wm,
			String startTime) {

		StringBuffer sb = new StringBuffer();

		if (orders.getData() == null) {
			sb.append("最近无订单");
		} else {

			int page_no = orders.getData().getPage_no();

			while (orders.getData().getPage_count() > orders.getData()
					.getPage_no()) {

				WeiMengOrder orders2 = wm.getReturnOrderJson(orders.getData()
						.getPage_size(), (++page_no));

				if (orders2.getData() == null) {
					break;
				}
				orders.getData().getPage_data()
						.addAll(orders2.getData().getPage_data());

			}
			// 样式管理
			sb.append("<style>");
			sb.append("table{background-color:#000;text-align:center;}table tr td,th{	background-color:#FFF;padding:4px;letter-spacing:1px;line-height:28px;}");
			sb.append("</style>");

			// 表格
			sb.append("<table border=\"1\" cellpadding=\"0\" cellspacing=\"0\">");
			sb.append("<tr>");
			sb.append("<th>订单编号</th>");
			sb.append("<th>联系方式</th>");
			sb.append("<th>商品名称</th>");
			sb.append("<th>单价</th>");
			sb.append("<th>数量</th>");
			sb.append("<th>付款时间</th>");
			sb.append("<th>付款金额</th>");
			sb.append("</tr>");

			// 订单信息处理
			List<Page_data> pageList = orders.getData().getPage_data();
			for (Page_data p : pageList) {
				// 订单信息
				Order order = p.getOrder();
				// 订单货物信息
				List<Goods> goods = p.getGoods();

				// 判断订单时间
				if (TimeTool.isBefore(order.getPayTime(), startTime)) {
					continue;
				}

				int rowspan = goods.size();

				sb.append("<tr>");
				sb.append("<td rowspan=\"" + rowspan + "\">" + p.getOrder_no()
						+ "</td>");
				sb.append("<td rowspan=\"" + rowspan + "\">"
						+ order.getConsigneeName() + "<br/>"
						+ order.getConsigneeTel() + "<br/>"
						+ order.getConsigneeAddress() + "</td>");

				int i = 1;
				for (Goods g : goods) {

					if (i != 1) {
						sb.append("<tr>");
					}

					sb.append("<td>" + g.getItemName()
							+ "<br/><img style=\"width:50px;\" src=\""
							+ g.getImageUrl() + "\" />" + "</td>");
					sb.append("<td>" + g.getPrice() + "</td>");
					sb.append("<td>" + g.getQty() + "</td>");

					if (i == 1) {
						sb.append("<td rowspan=\"" + rowspan + "\">"
								+ order.getPayTime() + "</td>");
						sb.append("<td rowspan=\"" + rowspan + "\">"
								+ order.getTotalAmount() + "</td>");
						sb.append("</tr>");
					} else {
						sb.append("</tr>");
					}

					i++;
				}

			}

			sb.append("</table>");
		}

		return sb;
	}

	private WeiMengOrder getReturnOrderJson(int page_size, int page_no) {
		JSONObject orderData = new JSONObject();

		orderData.put("order_type", 0);
		orderData.put("search_type", 1);
		// orderData.put("search", "");//查询条件

		/*
		 * 订单状态： 所有=0，已完成=1，已关闭=2，未付款 待发货=5，未付款 已发货=6，已付款 待发货=7，已付款 已发货=8，已删除=9
		 */
		orderData.put("order_state", 7);

		orderData.put("order_fields", "*");
		orderData.put("order_detail_fields", "*");
		// orderData.put("create_begin_time", 0);
		// orderData.put("create_end_time", 0);
		// orderData.put("update_begin_time", update_begin_time);
		// orderData.put("update_end_time", update_end_time);
		orderData.put("page_size", page_size);
		orderData.put("page_no", page_no);

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

		//System.out.println(access_token);
		return access_token;
	}
}
