import com.newLand.udf.deal.JDBCGetCodeTableData;
import com.newLand.udf.deal.ProperGetData;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @ClassName: TestUDF
 * @Description: TODO
 * @Author chengfei
 * @Date 2020/1/3 16:07
 * @Version 1.0
 **/
public class TestUDF {

    public String eval(String systemSource, String field,String code) throws IOException, SQLException, ClassNotFoundException {
        String data = ProperGetData.getData(systemSource, field, code);
        return data;
    }

    @Test
    public void test1() throws IOException, SQLException, ClassNotFoundException {
        String eval = null;
        for (int i = 0;i<1000000;i++){
            eval = eval("D", "type_desc", "工");
        }
        System.out.println(eval);
    }

    @Test
    public void test() throws IOException, SQLException, ClassNotFoundException {
       JDBCGetCodeTableData jdbcGetCodeTableData = new JDBCGetCodeTableData();
        jdbcGetCodeTableData.initialize();
        String data = null;
        for(int i = 0;i<500;i++){

            data = jdbcGetCodeTableData.getData("D", "type_desc", "工薪");
        }
        jdbcGetCodeTableData.close();
        System.out.println(data);
    }
}
