package com.newLand.udf.deal;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.mysql.jdbc.Connection;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @ClassName: JDBCGetCodeTableData
 * @Description: TODO
 * @Author chengfei
 * @Date 2020/1/3 15:19
 * @Version 1.0
 **/
public class JDBCGetCodeTableData implements GetCodeTableData {
    private DruidPooledConnection conn = null;
    public void initialize() {
        Properties properties = new Properties();
        InputStream inputStream = JDBCGetCodeTableData.class.getClassLoader().getResourceAsStream("db.properties");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(properties.getProperty("driverClassName"));
        druidDataSource.setUrl(properties.getProperty("url"));druidDataSource.setUsername(properties.getProperty("username"));
        druidDataSource.setPassword(properties.getProperty("password"));
        try {
            conn = druidDataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public String getData(String systemSource, String field, String code) {
        String sql = "select target_code from code_table " +
                "where system_source = ? and type = ? and source_code = ?";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
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
            return target_code;
        } catch (SQLException e) {
            e.printStackTrace();
            return target_code;
        }
    }
}
