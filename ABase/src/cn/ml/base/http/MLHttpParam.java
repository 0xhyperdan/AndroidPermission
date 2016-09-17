/**
 * FlieName:LTHttpParam.java
 * Destribution: http request param module
 * Author:michael
 * 2013-5-17 下午4:00:08
 */
package cn.ml.base.http;

/**
 * @author michael
 * 
 */
public class MLHttpParam {

	/**
	 * 参数名字
	 */
	private String paramName;

	/**
	 * 参数值
	 */
	private String paramValue;
	
	private long longParamValue;
	
	public boolean isLongType = false;
	
	

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
		isLongType = false;
	}

	/**
	 * @return the longParamValue
	 */
	public long getLongParamValue() {
		return longParamValue;
	}

	/**
	 * @param longParamValue the longParamValue to set
	 */
	public void setLongParamValue(long longParamValue) {
		this.longParamValue = longParamValue;
		isLongType = true;
	}
	
	

}
