package com.newLand.udf;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * @ClassName: JsonUDF
 * @Description: TODO
 * @Author chengfei
 * @Date 2019/12/23 11:38
 * @Version 1.0
 **/
public class JsonUDF extends UDF {
    /**
     * 解析json并返回对应的值。例如
     * @param jsonStr
     * @param objName
     * @return
     */
    public String evaluate(String jsonStr,String objName) throws JSONException {
        if(StringUtils.isBlank(jsonStr)|| StringUtils.isBlank(objName)){
            return null;
        }
        JSONObject jsonObject = new JSONObject(new JSONTokener(jsonStr));
        Object objValue = jsonObject.get(objName);
        if(objValue==null){
            return null;
        }
        return objValue.toString();
    }
}
