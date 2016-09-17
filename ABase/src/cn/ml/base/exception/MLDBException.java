/**
 * FlieName:LTDBException.java
 * Destribution:
 * Author:michael
 * 2013-5-20 上午11:55:55
 */
package cn.ml.base.exception;

import cn.ml.base.utils.MLStrUtil;


/**
 * @author michael
 *
 */
public class MLDBException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6429635947845610312L;


	public MLDBException(String dbMessage) {
		super(dbMessage);
	}


	@Override
	public String getMessage() {
		if(MLStrUtil.isEmpty(super.getMessage()))
			return "访问数据层异常：未知错误！";
		else
			return String.format("访问数据层异常：%s", super.getMessage());
	}

}
