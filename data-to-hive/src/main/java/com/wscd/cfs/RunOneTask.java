package com.wscd.cfs;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.wscd.cfs.connect.MysqlJDBC;
import com.wscd.cfs.connect.SFTPConnect;
import com.wscd.cfs.contrast_columns.HiveColumns;
import com.wscd.cfs.entity.Entity;
import com.wscd.cfs.transformImpl.CSVTransformFileOneFile;
import com.wscd.cfs.transformImpl.DATTransformFile;
import com.wscd.cfs.transformImpl.XLSXTransformFile;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;

/**
 * @ClassName: RunOneTask
 * @Description: TODO
 * @Author chengfei
 * @Date 2020/1/14 17:00
 * @Version 1.0
 **/
public class RunOneTask {
    private static Logger logger = Logger.getLogger(RunOneTask.class);

    public static void main(String[] args) throws Exception {
        Entity entity = new MysqlJDBC().getData(args[0]);
        InputStream resourceAsStream = RunOneTask.class.getClassLoader().getResourceAsStream("db.properties");
        Properties properties = new Properties();
        try {
            properties.load(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String yestday = LocalDate.now().plusDays(-Integer.parseInt(entity.getFil_diff_runDate())).format(DateTimeFormatter.BASIC_ISO_DATE);
        if(args.length >= 2 && !"".equals(args[1]) && args[1] != null){
            if(args[1].length() != 8){
                logger.error("请输入8位的年月日，如：20200101");
                return;
            }
            yestday = args[1];
        }
        String[] defaultField = {yestday, "系统抽数"};
        ArrayList<Map<String, Integer>> hiveColumns = new HiveColumns(args[0]).getHiveColumns();

        LoadData2Hive loadData2Hive = new LoadData2Hive(defaultField);
        String inputFloderPath = null;
        inputFloderPath = entity.getSave_dir() + yestday + File.separator;
        //logger.info("存储贴源文件夹的路径：" + inputFloderPath);
        String souDir = entity.getSou_dir() + yestday + "/";
        boolean copyFlag = false;
        if ("D".equals(entity.getSystem_source())) {
            //复制文件
            String souFileName = entity.getSou_file() + yestday + ".csv";
            String sftpIp = properties.getProperty("wolaidaiSftpIp");
            String sftpUserName = properties.getProperty("wolaidaiSftpUserName");
            String sftpPassword = properties.getProperty("wolaidaiSftpPassword");
            String sftpPort = properties.getProperty("sftpPort");
            String privateKey = properties.getProperty("privateKey");
            copyFlag = copyFile(souDir, souFileName, entity.getSave_dir(), yestday
                    , new String[]{sftpIp, sftpUserName, sftpPassword, sftpPort, privateKey});
            if (copyFlag) {

                loadData2Hive.loadData2Hive("D", new XLSXTransformFile(inputFloderPath, souFileName,hiveColumns),yestday);
            }
        } else if ("Y".equals(entity.getSystem_source())) {
            //复制文件
            String sftpIp = properties.getProperty("yinlianSftpIp");
            String sftpUserName = properties.getProperty("yinlianSftpUserName");
            String sftpPassword = properties.getProperty("yinlianSftpPassword");
            String sftpPort = properties.getProperty("sftpPort");
            String privateKey = properties.getProperty("privateKey");

            String souFileName = entity.getSou_file() + yestday + ".zip";
            copyFlag = copyFile(souDir, souFileName, entity.getSave_dir(), yestday
                    , new String[]{sftpIp, sftpUserName, sftpPassword, sftpPort, privateKey});
            if (copyFlag) {
                loadData2Hive.loadData2Hive("Y", new CSVTransformFileOneFile(inputFloderPath, souFileName
                        , entity.getTar_tabName(),hiveColumns),yestday);
            }
        } else if ("W".equals(entity.getSystem_source())) {
            //复制文件
            String sftpIp = properties.getProperty("weidaiSftpIp");
            String sftpUserName = properties.getProperty("weidaiSftpUserName");
            String sftpPassword = properties.getProperty("weidaiSftpPassword");
            String sftpPort = properties.getProperty("sftpPort");
            String privateKey = properties.getProperty("privateKey");
            yestday = Integer.toString(Integer.parseInt(yestday) - 1);
            if(args.length >= 2 && !"".equals(args[1]) && args[1] != null){
                if(args[1].length() != 8){
                    logger.error("请输入8位的年月日，如：20200101");
                    return;
                }
                yestday = args[1];
            }
            souDir = entity.getSou_dir() + yestday + "/";
            inputFloderPath = entity.getSave_dir() + yestday + File.separator;

            String souFileName = entity.getSou_file() + yestday + ".dat";
            copyFlag = copyFile(souDir, souFileName, entity.getSave_dir(), yestday
                    , new String[]{sftpIp, sftpUserName, sftpPassword, sftpPort, privateKey});
            if (copyFlag) {
                loadData2Hive.loadData2Hive("W", entity.getTar_tabName(), new DATTransformFile(inputFloderPath, souFileName),yestday);
            }
        } else {
            logger.warn("System_source来源不对");
        }
    }

    public static boolean copyFile(String souDir, String souFile, String savePath, String fileDate, String[] sftpConfig) throws IOException, SftpException, JSchException {
        logger.info("贴源数据入数仓的IP：" + sftpConfig[0]);
        SFTPConnect sftpConnect = new SFTPConnect();
        ChannelSftp sftp = sftpConnect.login(sftpConfig[0], sftpConfig[1], sftpConfig[2], sftpConfig[3], sftpConfig[4]);
        savePath += fileDate + File.separator;
        File file = new File(savePath);
        boolean flag = false;
        if (!file.exists()) {
            file.mkdir();
        }
        try {

            flag = sftpConnect.download(souDir, souFile, savePath);
        } catch (SftpException e) {
            logger.error("下载文件失败");
            logger.error("复制对应的文件路径：" + souDir + souFile);
            e.printStackTrace();
            throw e;
        } catch (IOException e) {
            logger.error("下载文件失败");
            logger.error("复制对应的文件路径：" + souDir + souFile);
            e.printStackTrace();
            throw e;
        } finally {
            logger.info("退出sftp");
            sftpConnect.logout(sftp);
        }
        sftpConnect.logout(sftp);
        return flag;
    }

}
