package com.newland.udf;

import com.newland.udf.decrypt.DESUtil;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.hive.ql.udf.UDFType;

import java.io.InputStream;
import java.util.Properties;

/**
 * @ClassName: DecryptUdf
 * @Description: TODO
 * @Author chengfei
 * @Date 2020/2/21 12:40
 * @Version 1.0
 **/
@UDFType
@Description(
        name = "card_des",
        value = "_FUNC_ (cardNo) - " +
                " from the input encryptData string " +
                " returns the cardNo",
        extended = "Example :" +
                "> SELECT _FUNC_ (encryptData) FROM src;"
)
public class DecryptUdf extends UDF {
    public String evaluate(String encryptData) throws Exception {
        InputStream resourceAsStream = DecryptUdf.class.getClassLoader()
                .getResourceAsStream("key.properties");
        Properties properties = new Properties();
        properties.load(resourceAsStream);
        String key = properties.getProperty("key");

        String cardNo = DESUtil.decrypt(encryptData,key);
        return cardNo.trim();
    }
}
