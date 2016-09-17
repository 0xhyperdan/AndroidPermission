package cn.ml.base.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.ml.base.exception.MLParserException;

public class MLJsonParser {

    public static String getStringFromInputStream(InputStream is) throws MLParserException {
        if (is == null) {
            return null;
        }
        BufferedReader br
                = new BufferedReader(
                new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
        } catch (IOException e1) {
            throw new MLParserException("从流进行解析失败！");
        }

        String str = sb.toString();
        return str;

    }


    public static <T> T fromJsonStream(Class<T> cls, InputStream is) throws MLParserException {
        String jsonString = getStringFromInputStream(is);
        return fromJsonString(cls, jsonString);
    }


    public static <T> T fromJsonString(Class<T> cls, String jsonString) throws MLParserException {
        try {
            //data 直接 为String的时候 直接返回
            if (!MLStrUtil.isEmpty(jsonString) && !jsonString.startsWith("{") && !jsonString.startsWith("[")) {
                return (T) jsonString;
            }

            T entity = null;
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            entity = gson.fromJson(jsonString, cls);
            return entity;
        } catch (Exception e) {
            MLLogUtil.d("ERROR", e == null ? "" : e.getMessage() + "");
            throw new MLParserException(String.format("%s(%s:%s)", e.getMessage(),
                    "解析的字符串为"
                    , jsonString));
        }
    }

    /**
     * JSON 转 List
     *
     * @param cls
     * @param jsonString
     * @return
     * @throws MLParserException
     */

    public static <T> List<T> jsonToList(Class<T> cls, String jsonString) throws MLParserException {
        try {
            List<T> entitys = new ArrayList<T>();
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();


            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0, length = jsonArray.length(); i < length; i++) {
                //	 JSONObject jsonOb = (JSONObject) jsonArray.opt(i);
                //     String str = jsonOb.toString();
                String str = jsonArray.getString(i);
                T entity = gson.fromJson(str, cls);
                entitys.add(entity);
            }

    		 
    	 /*    Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
             entity = gson.fromJson(jsonString, new TypeToken<ArrayList<T>>() {}.getType());*/
            return entitys;
        } catch (Exception e) {
            MLLogUtil.d("ERROR", e == null ? "" : e.getMessage() + "");
            throw new MLParserException(String.format("%s(%s:%s)", e.getMessage(),
                    "解析的字符串为"
                    , jsonString));
        }
    }

    public static String[] getKey(String jsonString, String type) {
        String string = jsonString.toString();
        string = string.replace("}", "");
        string = string.replace("{", "");
        string = string.replace("\"", "");
        String[] strings = string.split(",");

        if (type.equals("key")) {
            String[] stringsNum = new String[strings.length];
            for (int i = 0; i < strings.length; i++) {
                stringsNum[i] = strings[i].split(":")[0];
            }
            return stringsNum;
        } else if (type.equals("value")) {
            String[] stringsName = new String[strings.length];
            for (int i = 0; i < strings.length; i++) {
                stringsName[i] = strings[i].split(":")[1];
            }
            return stringsName;
        } else {
            return null;
        }
    }
}
