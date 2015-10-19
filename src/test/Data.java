package test;

import java.util.List;

public class Data {
	private int page_count;

	private List<Page_data> page_data;

	private int page_no;

	private int page_size;

	private int row_count;

	public void setPage_count(int page_count) {
		this.page_count = page_count;
	}

	public int getPage_count() {
		return this.page_count;
	}

	public void setPage_data(List<Page_data> page_data) {
		this.page_data = page_data;
	}

	public List<Page_data> getPage_data() {
		return this.page_data;
	}

	public void setPage_no(int page_no) {
		this.page_no = page_no;
	}

	public int getPage_no() {
		return this.page_no;
	}

	public void setPage_size(int page_size) {
		this.page_size = page_size;
	}

	public int getPage_size() {
		return this.page_size;
	}

	public void setRow_count(int row_count) {
		this.row_count = row_count;
	}

	public int getRow_count() {
		return this.row_count;
	}

}