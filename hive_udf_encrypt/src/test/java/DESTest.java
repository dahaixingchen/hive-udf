import com.newland.udf.encrypt.DESUtil;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @ClassName: DESTest
 * @Description: TODO
 * @Author chengfei
 * @Date 2020/2/19 9:50
 * @Version 1.0
 **/
public class DESTest {
    @Test
    public void test1() throws Exception {
        String source = "15558590366";
        System.out.println("原文: " + source);
        String key = "newland$11";
//               key = "A1B2C3D4E5F60708";
        String encryptData = DESUtil.encrypt(source, key);
        System.out.println("加密后密文的长度" + encryptData.length());
        System.out.println("加密后: " + encryptData);

        String decryptData = DESUtil.decrypt(encryptData, key);
        System.out.println("解密后: " + decryptData);
    }

    @Test
    public void test2() throws Exception {
        String source = "362323199303251014";
        System.out.println("原文: " + source);
        String key = "newland$11";
        String encrypt = DESUtil.encrypt(source, key);
        System.out.println(encrypt);
    }

    @Test
    public void test22(){
        String s = "dfsd\r\n";
        System.out.println(s.trim());
    }

    @Test
    public  void test3() throws Exception {
        String encryptData = "Q3p2a8HAcV83UtwOFdwGbw==";
        String decryptData = DESUtil.decrypt(encryptData, "newland$11");
        System.out.println(decryptData);
    }

    @Test
    public void test4() throws IOException {
        InputStream resourceAsStream = DESTest.class.getClassLoader()
                .getResourceAsStream("key.properties");
        Properties properties = new Properties();
        properties.load(resourceAsStream);
        String key = properties.getProperty("key");
        System.out.println(key);
    }

    public static void main(String[] args) throws IOException {
        InputStream resourceAsStream = DESTest.class.getClassLoader().getResourceAsStream("key.properties");
//        JDBCGetCodeTableData.class.getClassLoader().getResourceAsStream("db.properties");
        Properties properties = new Properties();
        properties.load(resourceAsStream);
        String key = properties.getProperty("key");
        System.out.println(key);
    }

}
