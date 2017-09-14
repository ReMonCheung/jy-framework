package com.jy.framework.core.base.model;

import java.io.Serializable;

/**
 * 接口层输出实体
 * @author zhanglj
 *
 * @param <T>
 */
public class DataResult<T> implements Serializable {
	
	private static final long serialVersionUID = 3661585377348141926L;

	/**
	 * 返回代码
	 */
	private String code;
	
	/**
	 * 响应信息
	 */
	private String msg = "";
	
	/**
	 * 返回数据
	 */
	private T data;
	/**
     * 处理耗时(毫秒)
     */
    private long elapsedMilliseconds;

	public DataResult(String code, String msg, T data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
	
	public DataResult(String code, String msg, T data, long elapsedMilliseconds) {
		this.code = code;
		this.msg = msg;
		this.data = data;
		this.elapsedMilliseconds = elapsedMilliseconds;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
	public long getElapsedMilliseconds() {
		return elapsedMilliseconds;
	}

	public void setElapsedMilliseconds(long elapsedMilliseconds) {
		this.elapsedMilliseconds = elapsedMilliseconds;
	}

	@Override
	public String toString() {
		return "DataResult [code=" + code + ", msg=" + msg + ", data=" + data
				+ ", elapsedMilliseconds=" + elapsedMilliseconds + "]";
	}
	
}
