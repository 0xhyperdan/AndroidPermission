package cn.bc.base;

import com.google.gson.annotations.Expose;

public class BaseResponseServer {
	@Expose
	public String msg;
	@Expose
	public String state;
	@Expose
	public String time;
	@Expose
	public BaseResponseRes res;
	
	
}
