package test;

import java.util.List;

public class Page_data {
	private List<Goods> goods;

	private Order order;

	private String order_no;

	private int order_state;

	public void setGoods(List<Goods> goods) {
		this.goods = goods;
	}

	public List<Goods> getGoods() {
		return this.goods;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Order getOrder() {
		return this.order;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public String getOrder_no() {
		return this.order_no;
	}

	public void setOrder_state(int order_state) {
		this.order_state = order_state;
	}

	public int getOrder_state() {
		return this.order_state;
	}

}