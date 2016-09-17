package cn.bc.base;

import com.google.gson.annotations.Expose;

public class BaseResponseRes<T> {
	@Expose
	public String msg;
	@Expose
	public String code;
	@Expose
	public T data;
	
	
}
