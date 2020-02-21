import com.newLand.udf.deal.JDBCGetCodeTableData;
import org.junit.Test;

/**
 * @ClassName: TestUDF
 * @Description: TODO
 * @Author chengfei
 * @Date 2020/1/3 16:07
 * @Version 1.0
 **/
public class TestUDF {
    @Test
    public void test(){
//        System.out.println("dvsd");
        JDBCGetCodeTableData jdbcGetCodeTableData = new JDBCGetCodeTableData();
        jdbcGetCodeTableData.initialize();
        String data = jdbcGetCodeTableData.getData("D", "type_desc", "工薪");
        System.out.println(data);
    }
}
