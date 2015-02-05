package com.schoolyard.school.console.web.utils;



/** 
* @Fields PageTransferVO.java : TODO(用一句话描述这个变量表示什么) 
*/ 


import java.util.List;
import java.util.Map;

import com.schoolyard.common.page.Page;

/**
 * @author XXX
 *
 */
public class OPPageTransferVO<T> {
	
	private String next;//下一页
	private String current;//当前页
	private String previous;//上一页
	private String size;//每页条数
	private List<T> data;//结果集
	/**
	 * @return the next
	 */
	public String getNext() {
		return next;
	}
	/**
	 * @param next the next to set
	 */
	public void setNext(String next) {
		this.next = next;
	}
	/**
	 * @return the current
	 */
	public String getCurrent() {
		return current;
	}
	/**
	 * @param current the current to set
	 */
	public void setCurrent(String current) {
		this.current = current;
	}
	/**
	 * @return the previous
	 */
	public String getPrevious() {
		return previous;
	}
	/**
	 * @param previous the previous to set
	 */
	public void setPrevious(String previous) {
		this.previous = previous;
	}
	/**
	 * @return the size
	 */
	public String getSize() {
		return size;
	}
	/**
	 * @param size the size to set
	 */
	public void setSize(String size) {
		this.size = size;
	}
	/**
	 * @return the data
	 */
	public List<T> getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(List<T> data) {
		this.data = data;
	}
	
	/**
	 * 由Page组装OPPageTransferVO数据
	 * @param page 分页查询返回的数据
	 * @param data vo list数据
	 * @param path api请求路径
	 * @return
	 */
	public OPPageTransferVO<T> wrapResultVO(Page<?> page,List<T> data,String path) {
		OPPageTransferVO<T> resultVO = new OPPageTransferVO<T>();
		resultVO.setData(data);
		
		int currentStart = page.getStart();
		resultVO.setCurrent(currentStart + "");
		int pageSize = page.getPagesize();
		resultVO.setSize(pageSize + "");
		int nextStart = currentStart + pageSize;
		int previousStart = currentStart - pageSize;
		String next = "";
		String privious = "";
		String sortParams = "";

		if (null != page.getParam() && null != page.getParam().getOrder()) {
			for (Map.Entry<String, String> ord : page.getParam().getOrder().entrySet()) {
				String sort = ord.getKey();
				String field = ord.getValue();
				sortParams += "&sortfield=" + field + "&sortorder=" + sort;
				break;
			}
		}

		if (nextStart >= page.getTotal()) {
			resultVO.setNext(null);
		} else {
			next = path + "?start=" + nextStart + "&pagesize=" + pageSize
					+ sortParams;
			resultVO.setNext(next);
		}
		if (previousStart < 0) {
			resultVO.setPrevious(null);
		} else {
			privious = path + "?start=" + previousStart + "&pagesize="
					+ pageSize + sortParams;
			resultVO.setPrevious(privious);
		}
		return resultVO;
	}
}
