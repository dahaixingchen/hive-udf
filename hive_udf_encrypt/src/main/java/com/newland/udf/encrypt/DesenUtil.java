package com.newland.udf.encrypt;

/**
 * @ClassName: DesenUtil
 * @Description: TODO
 * @Author chengfei
 * @Date 2020/2/20 16:55
 * @Version 1.0
 **/
public class DesenUtil {
    public static String desen(String cardNo){
        String desenStr = null;
        int length = cardNo.length();
        if (length == 11){  //手机号
            desenStr = cardNo.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        }else if(length == 18){ // 身份证号
            desenStr = cardNo.replaceAll("(\\d{3})\\d{13}(\\w{2})", "$1*************$2");
        }else{ //银行卡号
            desenStr = cardNo.replaceAll("(\\d{6})\\d+(\\d{4}$)","$1*********$2");
        }
        return desenStr;
    }
}
