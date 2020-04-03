package com.newLand.udf.deal;



import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @ClassName: JDBCGetCodeTableData
 * @Description: TODO
 * @Author chengfei
 * @Date 2020/1/3 15:19
 * @Version 1.0
 **/
public class JDBCGetCodeTableData implements GetCodeTableData {
    private Connection conn = null;

    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    public JDBCGetCodeTableData(){}

    public void initialize() throws IOException, SQLException, ClassNotFoundException {
        Properties properties = new Properties();
        InputStream inputStream = JDBCGetCodeTableData.class.getClassLoader().getResourceAsStream("db.properties");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection(properties.getProperty("url")
                , properties.getProperty("username"), properties.getProperty("password"));
        inputStream.close();
    }

    public String getData(String systemSource, String field, String code) throws SQLException {
        String sql = "select target_code from code_table " +
                "where system_source = ? and type = ? and source_code = ?";

        String target_code = "UNKNOW";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,systemSource);
            preparedStatement.setString(2,field);
            preparedStatement.setString(3,code);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                target_code = resultSet.getString("target_code");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return target_code;
    }

    public void close() throws SQLException {
        if (resultSet != null){
            resultSet.close();
        }
        if (preparedStatement != null){
            preparedStatement.close();
        }
        if (conn != null){
            conn.close();
        }
    }
}
