import com.newland.udf.encrypt.DesenUtil;
import org.junit.Test;

/**
 * @ClassName: DesenTest
 * @Description: TODO
 * @Author chengfei
 * @Date 2020/2/20 16:47
 * @Version 1.0
 **/
public class DesenTest {
    @Test
    public void test(){
        String phone = "18771632488";
        String desenStr = phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");

        String data = "36232319930325101x";
        desenStr = data.replaceAll("(\\d{3})\\d{13}(\\w{2})", "$1*************$2");

        data = "18771632488";
        desenStr = DesenUtil.desen(data);
        System.out.println(desenStr);
    }
}
