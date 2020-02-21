package com.newland.udf;

import com.newland.udf.encrypt.DESUtil;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.hive.ql.udf.UDFType;

import java.io.InputStream;
import java.util.Properties;

/**
 * @ClassName: EnAndDe
 * @Description: TODO
 * @Author chengfei
 * @Date 2020/2/20 18:04
 * @Version 1.0
 **/
@UDFType
@Description(
        name = "card_des",
        value = "_FUNC_ (cardNo) - " +
                " from the input cardNo string " +
                " returns the encryption of cardNo",
        extended = "Example :" +
                "> SELECT _FUNC_ (cardNo) FROM src;"
)
public class EncryptUdf extends UDF {
    public String evaluate(String cardNo) throws Exception {
        InputStream resourceAsStream = EncryptUdf.class.getClassLoader().getResourceAsStream("key.properties");
        Properties properties = new Properties();
        properties.load(resourceAsStream);
        String key = properties.getProperty("key");

        String encryptCardNo = DESUtil.encrypt(cardNo, key);
        return encryptCardNo.trim();
    }
}
