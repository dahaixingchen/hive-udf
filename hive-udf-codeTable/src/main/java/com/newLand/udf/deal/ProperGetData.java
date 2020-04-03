package com.newLand.udf.deal;

/**
 * @ClassName: ProperGetData
 * @Description: TODO
 * @Author chengfei
 * @Date 2020/2/24 15:37
 * @Version 1.0
 **/
public class ProperGetData {
    public static String getData(String systemSource, String field, String code) {
        String target_code = "UNKNOW";
        if (systemSource != null && (systemSource.equals("D") || systemSource.equals("Y"))
        && field != null && (field.equals("type_desc") && code != null && code.equals("工薪"))){
            target_code = "APR";
        }
        return target_code;
    }
}
