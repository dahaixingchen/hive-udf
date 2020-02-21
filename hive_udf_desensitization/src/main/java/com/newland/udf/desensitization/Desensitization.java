package com.newland.udf.desensitization;

import org.apache.hadoop.hive.ql.exec.UDF;

import javax.swing.*;

/**
 * @ClassName: Desensitization
 * @Description: TODO
 * @Author chengfei
 * @Date 2020/2/21 13:01
 * @Version 1.0
 **/
public class Desensitization extends UDF {
    public String evaluate(String cardNo) throws Exception {
        String desenData = DesenUtil.desen(cardNo);
        return desenData.trim();
    }
}
