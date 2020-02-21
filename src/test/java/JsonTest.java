import com.newLand.udf.JsonUDF;
import org.junit.Test;

/**
 * @ClassName: JsonTest
 * @Description: TODO
 * @Author chengfei
 * @Date 2019/12/23 11:55
 * @Version 1.0
 **/
public class JsonTest {
    @Test
    public void testJson(){
        System.out.println(new JsonUDF().evaluate("{\"movie\":\"1193\",\"rate\":\"5\",\"timeStamp\":\"978300760\",\"uid\":\"1\"}","movie"));
    }
}
