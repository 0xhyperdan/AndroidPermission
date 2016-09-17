package cn.ml.base.http;

import java.util.ArrayList;
import java.util.List;

public class MLRequestParams {
	
	private List<MLHttpParam> postParamList;
	public void addParameter(String paramName,String paramValue){
		MLHttpParam param = new MLHttpParam();
		param.setParamName(paramName);
		if(paramValue==null)
			paramValue = "";
		param.setParamValue(paramValue);
		if(postParamList ==null){
			postParamList = new ArrayList<MLHttpParam>();
		}
		postParamList.add(param);
	}

    public  List<MLHttpParam> getParams(){
    	return postParamList;
    }
    
    
}
