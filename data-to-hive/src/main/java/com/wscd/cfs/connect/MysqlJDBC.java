package com.wscd.cfs.connect;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.wscd.cfs.entity.Entity;

import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @ClassName: MysqlJDBC
 * @Description: TODO
 * @Author chengfei
 * @Date 2020/1/12 15:01
 * @Version 1.0
 **/
public class MysqlJDBC {
    private DruidPooledConnection conn = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    public DruidPooledConnection getConn() {
        initialize();
        return conn;
    }

    public void initialize() {
        Properties properties = new Properties();
        InputStream inputStream = MysqlJDBC.class.getClassLoader().getResourceAsStream("db.properties");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(properties.getProperty("mysqlDriver"));
        druidDataSource.setUrl(properties.getProperty("mysqlUrl"));
        druidDataSource.setUsername(properties.getProperty("mysqlUserName"));
        druidDataSource.setPassword(properties.getProperty("mysqlPassword"));
        try {
            conn = druidDataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<Entity> getData() throws SQLException {
        initialize();
        String sql = "select sou_dir,sou_file,save_dir,tar_tabName,fil_diff_runDate,system_source " +
                "from load2Hive";

        List<Entity> fieldsList = new ArrayList<>();

        try {
            preparedStatement = conn.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String sou_dir = resultSet.getString("sou_dir");
                String sou_file = resultSet.getString("sou_file");
                String save_dir = resultSet.getString("save_dir");
                String tar_tabName = resultSet.getString("tar_tabName");
                String fil_diff_runDate = resultSet.getString("fil_diff_runDate");
                String system_source = resultSet.getString("system_source");

                Entity entity = new Entity();

                entity.setSou_dir(sou_dir);
                entity.setSou_dir(sou_file);
                entity.setSave_dir(save_dir);
                entity.setTar_tabName(tar_tabName);
                entity.setFil_diff_runDate(fil_diff_runDate);
                entity.setSystem_source(system_source);
                fieldsList.add(entity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }finally {
            closeAll();
        }
        return fieldsList;
    }
    public Entity getData(String targetName) throws SQLException {
        initialize();
        String sql = "select sou_dir,sou_file,save_dir,tar_tabName,fil_diff_runDate,system_source" +
                " from load2Hive where tar_tabName = ?";
        Entity entity = new Entity();
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,targetName);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String sou_dir = resultSet.getString("sou_dir");
                String sou_file = resultSet.getString("sou_file");
                String save_dir = resultSet.getString("save_dir");
                String tar_tabName = resultSet.getString("tar_tabName");
                String fil_diff_runDate = resultSet.getString("fil_diff_runDate");
                String system_source = resultSet.getString("system_source");

                entity.setSou_dir(sou_dir);
                entity.setSou_file(sou_file);
                entity.setSave_dir(save_dir);
                entity.setTar_tabName(tar_tabName);
                entity.setFil_diff_runDate(fil_diff_runDate);
                entity.setSystem_source(system_source);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }finally {
            closeAll();
        }
        return entity;
    }
    public void closeAll() throws SQLException {
        try {
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }


    }
}
