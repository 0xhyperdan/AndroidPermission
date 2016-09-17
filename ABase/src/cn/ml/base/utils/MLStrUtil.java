/*
 * Copyright (C) 2012 www.amsoft.cn
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.ml.base.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MLStrUtil {

	/**
	 * 描述：将null转化为“”.
	 *
	 * @param str
	 *            指定的字符串
	 * @return 字符串的String类型
	 */
	public static String parseEmpty(String str) {
		if (str == null || "null".equals(str.trim())) {
			str = "";
		}
		return str.trim();
	}

	/**
	 * 描述：判断一个字符串是否为null或空值.
	 *
	 * @param str
	 *            指定的字符串
	 * @return true or false
	 */
	public static boolean isEmpty(String str) {
		if (str != null) {
			str.replace(" ", "");
		}
		return str == null || str.trim().length() == 0;
	}

	/**
	 * 对比两个字符串
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean compare(String str1, String str2) {
		if (isEmpty(str1)||isEmpty(str2)) {
			return false;
		}

		return str1.equalsIgnoreCase(str2);
	}

	/**
	 * 获取字符串中文字符的长度（每个中文算2个字符）.
	 *
	 * @param str
	 *            指定的字符串
	 * @return 中文字符的长度
	 */
	public static int chineseLength(String str) {
		int valueLength = 0;
		String chinese = "[\u0391-\uFFE5]";
		/* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
		if (!isEmpty(str)) {
			for (int i = 0; i < str.length(); i++) {
				/* 获取一个字符 */
				String temp = str.substring(i, i + 1);
				/* 判断是否为中文字符 */
				if (temp.matches(chinese)) {
					valueLength += 2;
				}
			}
		}
		return valueLength;
	}
	/**
	 * 去掉 /n 等字符
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str) {
		String dest = "";
		if (str!=null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	/**
	 * 描述：获取字符串的长度.
	 *
	 * @param str
	 *            指定的字符串
	 * @return 字符串的长度（中文字符计2个）
	 */
	public static int strLength(String str) {
		int valueLength = 0;
		String chinese = "[\u0391-\uFFE5]";
		if (!isEmpty(str)) {
			// 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
			for (int i = 0; i < str.length(); i++) {
				// 获取一个字符
				String temp = str.substring(i, i + 1);
				// 判断是否为中文字符
				if (temp.matches(chinese)) {
					// 中文字符长度为2
					valueLength += 2;
				} else {
					// 其他字符长度为1
					valueLength += 1;
				}
			}
		}
		return valueLength;
	}

	/**
	 * 描述：获取指定长度的字符所在位置.
	 *
	 * @param str
	 *            指定的字符串
	 * @param maxL
	 *            要取到的长度（字符长度，中文字符计2个）
	 * @return 字符的所在位置
	 */
	public static int subStringLength(String str, int maxL) {
		int currentIndex = 0;
		int valueLength = 0;
		String chinese = "[\u0391-\uFFE5]";
		// 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
		for (int i = 0; i < str.length(); i++) {
			// 获取一个字符
			String temp = str.substring(i, i + 1);
			// 判断是否为中文字符
			if (temp.matches(chinese)) {
				// 中文字符长度为2
				valueLength += 2;
			} else {
				// 其他字符长度为1
				valueLength += 1;
			}
			if (valueLength >= maxL) {
				currentIndex = i;
				break;
			}
		}
		return currentIndex;
	}

	/**
	 * 描述：手机号格式验证.
	 *
	 * @param str
	 *            指定的手机号码字符串
	 * @return 是否为手机号码格式:是为true，否则false
	 */
	public static Boolean isMobile(String str) {
		Boolean isMobileNo = false;
		try {
			Pattern p = Pattern
					.compile("^((13[0-9])|(147)|(15[^4,\\D])|(17[6-8])|(18[0-9]))\\d{8}$");
			Matcher m = p.matcher(str);
			isMobileNo = m.matches();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isMobileNo;
	}

	/**
	 * 验证身份证号码
	 * 
	 * @param idCard
	 *            居民身份证号码15位或18位，最后一位可能是数字或字母
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean isIdCard(String idCard) {
		String regex = "[1-9]\\d{13,16}[a-zA-Z0-9]{1}";
		return Pattern.matches(regex, idCard);
	}

	/**
	 * 描述：是否只是字母和数字.
	 *
	 * @param str
	 *            指定的字符串
	 * @return 是否只是字母和数字:是为true，否则false
	 */
	public static Boolean isNumberLetter(String str) {
		Boolean isNoLetter = false;
		String expr = "^[A-Za-z0-9]+$";
		if (str.matches(expr)) {
			isNoLetter = true;
		}
		return isNoLetter;
	}

	/**
	 * 验证固定电话号码
	 * 
	 * @param phone
	 *            电话号码，格式：国家（地区）电话代码 + 区号（城市代码） + 电话号码，如：+8602085588447
	 *            <p>
	 *            <b>国家（地区） 代码 ：</b>标识电话号码的国家（地区）的标准国家（地区）代码。它包含从 0 到 9
	 *            的一位或多位数字， 数字之后是空格分隔的国家（地区）代码。
	 *            </p>
	 *            <p>
	 *            <b>区号（城市代码）：</b>这可能包含一个或多个从 0 到 9 的数字，地区或城市代码放在圆括号——
	 *            对不使用地区或城市代码的国家（地区），则省略该组件。
	 *            </p>
	 *            <p>
	 *            <b>电话号码：</b>这包含从 0 到 9 的一个或多个数字
	 *            </p>
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean isPhone(String phone) {
		String regex = "(\\+\\d+)?(\\d{3,4}\\-?)?\\d{7,8}$";
		return Pattern.matches(regex, phone);
	}

	/**
	 * 匹配中国邮政编码
	 * 
	 * @param postcode
	 *            邮政编码
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean isPostcode(String postcode) {
		String regex = "[1-9]\\d{5}";
		return Pattern.matches(regex, postcode);
	}

	/**
	 * 描述：是否只是数字.
	 *
	 * @param str
	 *            指定的字符串
	 * @return 是否只是数字:是为true，否则false
	 */
	public static Boolean isNumber(String str) {
		Boolean isNumber = false;
		String expr = "^[0-9]+$";
		if (str.matches(expr)) {
			isNumber = true;
		}
		return isNumber;
	}

	/**
	 * 描述：是否是邮箱.
	 *
	 * @param str
	 *            指定的字符串
	 * @return 是否是邮箱:是为true，否则false
	 */
	public static Boolean isEmail(String str) {
		Boolean isEmail = false;
		String expr = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		if (str.matches(expr)) {
			isEmail = true;
		}
		return isEmail;
	}

	/**
	 * 描述：是否是中文.
	 *
	 * @param str
	 *            指定的字符串
	 * @return 是否是中文:是为true，否则false
	 */
	public static Boolean isChinese(String str) {
		Boolean isChinese = true;
		String chinese = "[\u0391-\uFFE5]";
		if (!isEmpty(str)) {
			// 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
			for (int i = 0; i < str.length(); i++) {
				// 获取一个字符
				String temp = str.substring(i, i + 1);
				// 判断是否为中文字符
				if (temp.matches(chinese)) {
				} else {
					isChinese = false;
				}
			}
		}
		return isChinese;
	}

	/**
	 * 描述：是否包含中文.
	 *
	 * @param str
	 *            指定的字符串
	 * @return 是否包含中文:是为true，否则false
	 */
	public static Boolean isContainChinese(String str) {
		Boolean isChinese = false;
		String chinese = "[\u0391-\uFFE5]";
		if (!isEmpty(str)) {
			// 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
			for (int i = 0; i < str.length(); i++) {
				// 获取一个字符
				String temp = str.substring(i, i + 1);
				// 判断是否为中文字符
				if (temp.matches(chinese)) {
					isChinese = true;
				} else {

				}
			}
		}
		return isChinese;
	}

	/**
	 * 描述：从输入流中获得String.
	 *
	 * @param is
	 *            输入流
	 * @return 获得的String
	 */
	public static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}

			// 最后一个\n删除
			if (sb.indexOf("\n") != -1
					&& sb.lastIndexOf("\n") == sb.length() - 1) {
				sb.delete(sb.lastIndexOf("\n"), sb.lastIndexOf("\n") + 1);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	/**
	 * 描述：标准化日期时间类型的数据，不足两位的补0.
	 *
	 * @param dateTime
	 *            预格式的时间字符串，如:2012-3-2 12:2:20
	 * @return String 格式化好的时间字符串，如:2012-03-20 12:02:20
	 */
	public static String dateTimeFormat(String dateTime) {
		StringBuilder sb = new StringBuilder();
		try {
			if (isEmpty(dateTime)) {
				return null;
			}
			String[] dateAndTime = dateTime.split(" ");
			if (dateAndTime.length > 0) {
				for (String str : dateAndTime) {
					if (str.indexOf("-") != -1) {
						String[] date = str.split("-");
						for (int i = 0; i < date.length; i++) {
							String str1 = date[i];
							sb.append(strFormat2(str1));
							if (i < date.length - 1) {
								sb.append("-");
							}
						}
					} else if (str.indexOf(":") != -1) {
						sb.append(" ");
						String[] date = str.split(":");
						for (int i = 0; i < date.length; i++) {
							String str1 = date[i];
							sb.append(strFormat2(str1));
							if (i < date.length - 1) {
								sb.append(":");
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return sb.toString();
	}

	/**
	 * 描述：不足2个字符的在前面补“0”.
	 *
	 * @param str
	 *            指定的字符串
	 * @return 至少2个字符的字符串
	 */
	public static String strFormat2(String str) {
		try {
			if (str.length() <= 1) {
				str = "0" + str;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 描述：截取字符串到指定字节长度.
	 *
	 * @param str
	 *            the str
	 * @param length
	 *            指定字节长度
	 * @return 截取后的字符串
	 */
	public static String cutString(String str, int length) {
		return cutString(str, length, "");
	}

	/**
	 * 描述：截取字符串到指定字节长度.
	 *
	 * @param str
	 *            文本
	 * @param length
	 *            字节长度
	 * @param dot
	 *            省略符号
	 * @return 截取后的字符串
	 */
	public static String cutString(String str, int length, String dot) {
		int strBLen = strlen(str, "GBK");
		if (strBLen <= length) {
			return str;
		}
		int temp = 0;
		StringBuffer sb = new StringBuffer(length);
		char[] ch = str.toCharArray();
		for (char c : ch) {
			sb.append(c);
			if (c > 256) {
				temp += 2;
			} else {
				temp += 1;
			}
			if (temp >= length) {
				if (dot != null) {
					sb.append(dot);
				}
				break;
			}
		}
		return sb.toString();
	}

	/**
	 * 描述：截取字符串从第一个指定字符.
	 *
	 * @param str1
	 *            原文本
	 * @param str2
	 *            指定字符
	 * @param offset
	 *            偏移的索引
	 * @return 截取后的字符串
	 */
	public static String cutStringFromChar(String str1, String str2, int offset) {
		if (isEmpty(str1)) {
			return "";
		}
		int start = str1.indexOf(str2);
		if (start != -1) {
			if (str1.length() > start + offset) {
				return str1.substring(start + offset);
			}
		}
		return "";
	}

	/**
	 * 描述：获取字节长度.
	 *
	 * @param str
	 *            文本
	 * @param charset
	 *            字符集（GBK）
	 * @return the int
	 */
	public static int strlen(String str, String charset) {
		if (str == null || str.length() == 0) {
			return 0;
		}
		int length = 0;
		try {
			length = str.getBytes(charset).length;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return length;
	}

	/**
	 * 获取大小的描述.
	 *
	 * @param size
	 *            字节个数
	 * @return 大小的描述
	 */
	public static String getSizeDesc(long size) {
		String suffix = "B";
		if (size >= 1024) {
			suffix = "K";
			size = size >> 10;
			if (size >= 1024) {
				suffix = "M";
				// size /= 1024;
				size = size >> 10;
				if (size >= 1024) {
					suffix = "G";
					size = size >> 10;
					// size /= 1024;
				}
			}
		}
		return size + suffix;
	}

	/**
	 * 描述：ip地址转换为10进制数.
	 *
	 * @param ip
	 *            the ip
	 * @return the long
	 */
	public static long ip2int(String ip) {
		ip = ip.replace(".", ",");
		String[] items = ip.split(",");
		return Long.valueOf(items[0]) << 24 | Long.valueOf(items[1]) << 16
				| Long.valueOf(items[2]) << 8 | Long.valueOf(items[3]);
	}

	/**
	 * MD5 加密
	 * 
	 * @param original
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String MD5(String original) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(original.getBytes("UTF-8"));
		} catch (Exception e) {
			return "";
		}
		byte[] byteArray = messageDigest.digest();
		StringBuffer md5StrBuff = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		return md5StrBuff.toString().toLowerCase();
	}
	
	
	
	//================================================================
	/**
	 * 验证身份证
	 * @param IDStr
	 * @return
	 * @throws ParseException
	 */
	public static boolean IDCardValidate(String IDStr) {  
	        String errorInfo = "";// 记录错误信息  
	        String[] ValCodeArr = { "1", "0", "x", "9", "8", "7", "6", "5", "4",  
	                "3", "2" };  
	        String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",  
	                "9", "10", "5", "8", "4", "2" };  
	        String Ai = "";  
	        // ================ 号码的长度 15位或18位 ================  
	        if (IDStr.length() != 15 && IDStr.length() != 18) {  
	          /*  errorInfo = "身份证号码长度应该为15位或18位。";  
	            return errorInfo;  */
	        	return false;
	        }  
	        // =======================(end)========================  
	 
	        // ================ 数字 除最后以为都为数字 ================  
	        if (IDStr.length() == 18) {  
	            Ai = IDStr.substring(0, 17);  
	        } else if (IDStr.length() == 15) {  
	            Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);  
	        }  
	        if (isNumeric(Ai) == false) {  
	            /*errorInfo = "身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。";  
	            return errorInfo;  */
	          	return false;
	        }  
	        // =======================(end)========================  
	 
	        // ================ 出生年月是否有效 ================  
	        String strYear = Ai.substring(6, 10);// 年份  
	        String strMonth = Ai.substring(10, 12);// 月份  
	        String strDay = Ai.substring(12, 14);// 月份  
	        if (isDataFormat(strYear + "-" + strMonth + "-" + strDay) == false) {  
	           /* errorInfo = "身份证生日无效。";  
	            return errorInfo;  */
	          	return false;
	        }  
	        GregorianCalendar gc = new GregorianCalendar();  
	        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");  
	        try {
				if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150 
				        || (gc.getTime().getTime() - s.parse(  
				                strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {  
				  /*  errorInfo = "身份证生日不在有效范围。";  
				    return errorInfo;  */
				  	return false;
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
			 	return false;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
			 	return false;
			}  
	        if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {  
	          /*  errorInfo = "身份证月份无效";  
	            return errorInfo;  */
	          	return false;
	        }  
	        if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {  
	           /* errorInfo = "身份证日期无效";  
	            return errorInfo;  */
	          	return false;
	        }  
	        // =====================(end)=====================  
	 
	        // ================ 地区码时候有效 ================  
	        Hashtable h = GetAreaCode();  
	        if (h.get(Ai.substring(0, 2)) == null) {  
	          /*  errorInfo = "身份证地区编码错误。";  
	            return errorInfo;  */
	          	return false;
	        }  
	        // ==============================================  
	 
	        // ================ 判断最后一位的值 ================  
	        int TotalmulAiWi = 0;  
	        for (int i = 0; i < 17; i++) {  
	            TotalmulAiWi = TotalmulAiWi  
	                    + Integer.parseInt(String.valueOf(Ai.charAt(i)))  
	                    * Integer.parseInt(Wi[i]);  
	        }  
	        int modValue = TotalmulAiWi % 11;  
	        String strVerifyCode = ValCodeArr[modValue];  
	        Ai = Ai + strVerifyCode;  
	 
	        if (IDStr.length() == 18) {  
	             if (Ai.equalsIgnoreCase(IDStr) == false) {  
	               /*  errorInfo = "身份证无效，不是合法的身份证号码";  
	                 return errorInfo;  */
	            	  	return false;
	             }  
	         } else {  
	        	  	return true;
	         }  
	         // =====================(end)=====================  
	        return true;  
	     }
	   
	    /** 
	      * 功能：判断字符串是否为数字 
	      * @param str 
	      * @return 
	      */ 
	     private static boolean isNumeric(String str) {  
	         Pattern pattern = Pattern.compile("[0-9]*");  
	         Matcher isNum = pattern.matcher(str);
			 return isNum.matches();
	     }
	    
	     /** 
	      * 功能：设置地区编码 
	      * @return Hashtable 对象 
	      */ 
	     private static Hashtable GetAreaCode() {  
	         Hashtable hashtable = new Hashtable();  
	         hashtable.put("11", "北京");  
	         hashtable.put("12", "天津");  
	         hashtable.put("13", "河北");  
	         hashtable.put("14", "山西");  
	         hashtable.put("15", "内蒙古");  
	         hashtable.put("21", "辽宁");  
	         hashtable.put("22", "吉林");  
	         hashtable.put("23", "黑龙江");  
	         hashtable.put("31", "上海");  
	         hashtable.put("32", "江苏");  
	         hashtable.put("33", "浙江");  
	         hashtable.put("34", "安徽");  
	         hashtable.put("35", "福建");  
	         hashtable.put("36", "江西");  
	         hashtable.put("37", "山东");  
	         hashtable.put("41", "河南");  
	         hashtable.put("42", "湖北");  
	         hashtable.put("43", "湖南");  
	         hashtable.put("44", "广东");  
	         hashtable.put("45", "广西");  
	         hashtable.put("46", "海南");  
	         hashtable.put("50", "重庆");  
	         hashtable.put("51", "四川");  
	         hashtable.put("52", "贵州");  
	         hashtable.put("53", "云南");  
	         hashtable.put("54", "西藏");  
	         hashtable.put("61", "陕西");  
	         hashtable.put("62", "甘肃");  
	         hashtable.put("63", "青海");  
	         hashtable.put("64", "宁夏");  
	         hashtable.put("65", "新疆");  
	         hashtable.put("71", "台湾");  
	         hashtable.put("81", "香港");  
	         hashtable.put("82", "澳门");  
	         hashtable.put("91", "国外");  
	         return hashtable;  
	     } 

	    /**验证日期字符串是否是YYYY-MM-DD格式
	      * @param str
	      * @return
	      */
	    public static boolean isDataFormat(String str){
	      boolean flag=false;
	       //String regxStr="[1-9][0-9]{3}-[0-1][0-2]-((0[1-9])|([12][0-9])|(3[01]))";
	      String regxStr="^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
	      Pattern pattern1=Pattern.compile(regxStr);
	      Matcher isNo=pattern1.matcher(str);
	      if(isNo.matches()){
	        flag=true;
	      }
	      return flag;
	   }


}
