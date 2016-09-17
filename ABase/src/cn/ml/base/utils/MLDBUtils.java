package cn.ml.base.utils;

import java.util.List;

import cn.ml.base.MLApplication;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
public class MLDBUtils {
		
	protected static DbUtils db;
	
	private static void initDB() {
		db = MLApplication.db;
	}
	
	// 增,改
	public static <T> void saveOrUpdate(T entity){
		initDB();
		try {
			db.saveOrUpdate(entity);
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// 删
	public  static <T> void delete(T entity){
		initDB();
		try {
			db.delete(entity);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}
	// 查
	public  static <T> T getFirst(Class<T> entity){
		initDB();
		T result;
		try {
			result = db.findFirst(entity);
		} catch (DbException e) {
			return null;
		}
		return result;
	}
	
	public  static <T> T getFirst(Selector selector){
		initDB();
		T result;
		try {
			result = db.findFirst(selector);
		} catch (DbException e) {
			return null;
		}
		return result;
	}
	
	public  static <T> List<T> getAll(Class<T> entity){
		initDB();
		 List<T> result;
		try {
			result = db.findAll(entity);
		} catch (DbException e) {
			return null;
		}
		 return result;
	}
	
	public  static <T> List<T> getAll(Selector select){
		initDB();
		 List<T> result;
		try {
			result = db.findAll(select);
		} catch (DbException e) {
			return null;
		}
		 return result;
	}
	
	
	
	
	
}
