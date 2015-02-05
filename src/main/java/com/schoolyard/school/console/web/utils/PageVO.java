package com.schoolyard.school.console.web.utils;



import java.util.List;

/**
 * 分页vo
 */
public class PageVO<T> {
	
	String total;			//记录总数
	String currentpage;		//当前页数
	String pagesize;		//当前每页条数
	List<T> data;			//结果列表
	
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getCurrentpage() {
		return currentpage;
	}
	public void setCurrentpage(String currentpage) {
		this.currentpage = currentpage;
	}
	public String getPagesize() {
		return pagesize;
	}
	public void setPagesize(String pagesize) {
		this.pagesize = pagesize;
	}
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}


}
