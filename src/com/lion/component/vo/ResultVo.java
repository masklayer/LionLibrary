package com.lion.component.vo;

import java.io.Serializable;

/**
 * api-response
 * 
 * @author zhangbp
 * 
 * @param <T>
 */

public class ResultVo<T>  extends BaseVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String version;
	public String functionCode;
	public Boolean isSuccess;
	public String code;
	public String msg;
	public int count;
	public T results;
	public int totalPages = 0;
	public int curPage = 0;
	public int pageSize = 0;
	@Override
	public String toString() {
		return "ResultVo [version=" + version + ", functionCode="
				+ functionCode + ", isSuccess=" + isSuccess + ", code=" + code
				+ ", msg=" + msg + ", count=" + count + ", results=" + results
				+ ", totalPages=" + totalPages + ", curPage=" + curPage
				+ ", pageSize=" + pageSize + "]";
	}
	

}
