package com.wscd.cfs.contrast_columns;

import com.wscd.cfs.connect.HiveJDBC;
import org.apache.hive.jdbc.HiveQueryResultSet;
import org.apache.hive.jdbc.HiveResultSetMetaData;
import org.jcodings.util.Hash;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.Map;

/**
 * @ClassName: HiveColumns
 * @Description: TODO
 * @Author chengfei
 * @Date 2020/2/4 15:41
 * @Version 1.0
 **/
public class HiveColumns {
    private String tableName;
    public HiveColumns(String tableName){
        this.tableName = tableName;
    }
    public ArrayList<Map<String, Integer>> getHiveColumns() throws SQLException, IOException, ClassNotFoundException {
        HiveJDBC hiveJDBC = new HiveJDBC();
        ArrayList<Map<String, Integer>> columnsList = new ArrayList<Map<String,Integer>>();
        String column = "";
        String sql = "select * from "+ tableName +" limit 0";
        HiveQueryResultSet resultSet = (HiveQueryResultSet) hiveJDBC.executeQuery2Hive(sql);
        HiveResultSetMetaData metaData = (HiveResultSetMetaData) resultSet.getMetaData();

        for (int i =1;i <= metaData.getColumnCount();i++){
            Map<String, Integer> columnPlaceMap = new HashMap<>();
            column = metaData.getColumnName(i);
            String[] split = column.split("[.]");
            column = split[1];
            columnPlaceMap.put(column,i);
            columnsList.add(columnPlaceMap);
        }
        return columnsList;
    }
//    public static ArrayList<Integer> getFileColumnOrder(List<Map<String, Integer>> hiveColumns,){
//        ArrayList<Integer> fileIndexList = new ArrayList<>();
//        return fileIndexList;
//    }
}
