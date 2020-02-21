import com.wscd.cfs.connect.HiveJDBC;
import org.apache.hive.jdbc.HiveQueryResultSet;
import org.apache.hive.jdbc.HiveResultSetMetaData;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @ClassName: TestHiveJDBC
 * @Description: TODO
 * @Author chengfei
 * @Date 2020/1/9 15:10
 * @Version 1.0
 **/
public class TestHiveJDBC {
    @Test
    public void getHiveFields() throws SQLException, IOException, ClassNotFoundException {
        HiveJDBC hiveJDBC = new HiveJDBC();
        ArrayList<String> columnsList = new ArrayList<>();
        String column = "";
        String sql = "select * from ods.ods_nf_wolaidai_contract_info limit 0";
        HiveQueryResultSet resultSet = (HiveQueryResultSet) hiveJDBC.executeQuery2Hive(sql);
        HiveResultSetMetaData metaData = (HiveResultSetMetaData) resultSet.getMetaData();

        for (int i =1;i <= metaData.getColumnCount();i++){
            column = metaData.getColumnName(i);
            String[] split = column.split("[.]");

            column = split[1];
            System.out.println(column);
            columnsList.add(column);
        }
    }
}
