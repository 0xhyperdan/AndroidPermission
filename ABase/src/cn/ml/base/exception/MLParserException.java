/**
 * FlieName:LTParserException.java
 * Destribution:
 * Author:michael
 * 2013-5-20 上午10:49:25
 */
package cn.ml.base.exception;

import cn.ml.base.utils.MLStrUtil;

/**
 * @author michael
 *
 */
public class MLParserException extends Exception{


	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1219595618743635633L;


	public MLParserException(String parserMessage) {
		super(parserMessage);
	}


	@Override
	public String getMessage() {
		if(MLStrUtil.isEmpty(super.getMessage()))
			return "解析异常：未知错误！";
		else
		//	return String.format("解析异常：%s", super.getMessage());
			return String.format("数据解析异常");
	}

}
