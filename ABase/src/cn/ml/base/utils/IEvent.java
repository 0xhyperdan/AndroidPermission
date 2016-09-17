/**
 * FlieName:IEvent.java
 * Destribution:
 * Author:michael
 * 2013-5-27 上午11:33:50
 */
package cn.ml.base.utils;

public interface IEvent<T> {

	void onEvent(Object source, T eventArg);

}
