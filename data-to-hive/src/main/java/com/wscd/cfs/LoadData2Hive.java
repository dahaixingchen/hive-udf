package com.wscd.cfs;

import com.wscd.cfs.connect.HiveJDBC;
import com.wscd.cfs.transform.TransformFile;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @ClassName: LoadData2Hive
 * @Description: TODO
 * @Author chengfei
 * @Date 2020/1/8 15:51
 * @Version 1.0
 **/
public class LoadData2Hive {
    private static Logger logger = Logger.getLogger(LoadData2Hive.class);
    private String[] defaultField;

    public LoadData2Hive(String[] defaultField) throws SQLException, IOException, ClassNotFoundException {
        this.defaultField = defaultField;
    }

    HiveJDBC hiveJDBC = new HiveJDBC();

    public void loadData2Hive(String systemSource, TransformFile transformFile, String runDate) throws Exception {
        StringBuilder strBuilder = new StringBuilder(runDate);
        runDate = strBuilder.insert(4, "-").insert(7, "-").toString();
        List<String> transTmpPaths = transformFile.transformFile(defaultField);
        String sql = null;
        for (String transTmpPath : transTmpPaths) {
            String targetName = null;
            if (!new File(transTmpPath).exists()){
                logger.error("转换的临时文件路径不存在：" + transTmpPath);
            }
            String[] split = transTmpPath.split("/|\\\\");
            String targetTableName = split[split.length - 1];
            if ("D".equals(systemSource)) {

                targetName = "ods_nf_wolaidai_" + StringUtils.substringBeforeLast(targetTableName, "_");
            } else if ("Y".equals(systemSource)) {
                targetName = "ods_nf_yinlian_" + StringUtils.substringBeforeLast(targetTableName, "_");
            }
            sql = "load data local inpath '" + transTmpPath + "' overwrite into table ods." + targetName +
                    " partition(data_date = '" + runDate + "')";
            logger.info("hiveSQL语句：" + sql);
            hiveJDBC.executeSQL(sql);
            //删除临时文件
            new File(transTmpPath).delete();
        }
    }

    public void loadData2Hive(String systemSource,String targetTableName, TransformFile transformFile, String runDate) throws Exception {
        StringBuilder strBuilder = new StringBuilder(runDate);
        runDate = strBuilder.insert(4, "-").insert(7, "-").toString();
        List<String> transTmpPaths = null;
        transTmpPaths = transformFile.transformFile(defaultField);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        dateTimeFormatter.parse(runDate);
        String sql = null;
        for (String transTmpPath : transTmpPaths) {
            if (!new File(transTmpPath).exists()) {
                logger.error("转换的临时文件路径不存在：" + transTmpPath);
            }
            sql = "load data local inpath '" + transTmpPath + "' overwrite into table ods." + targetTableName +
                    " partition(data_date = '" + runDate + "')";
            logger.info("hiveSQL语句：" + sql);
            hiveJDBC.executeSQL(sql);
            //删除临时文件

            new File(transTmpPath).delete();
        }
    }

}
