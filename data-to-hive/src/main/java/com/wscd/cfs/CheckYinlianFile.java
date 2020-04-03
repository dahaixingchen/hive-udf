package com.wscd.cfs;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.wscd.cfs.connect.SFTPConnect;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Properties;


/**
 * @ClassName: CheckYinlianFile
 * @Description: TODO
 * @Author chengfei
 * @Date 2020/2/20 14:26
 * @Version 1.0
 **/
public class CheckYinlianFile {
    private static org.apache.log4j.Logger logger = Logger.getLogger(CheckYinlianFile.class);

    public static void main(String[] args) throws IOException, JSchException, SftpException {
        InputStream resourceAsStream = CheckYinlianFile.class.getClassLoader().getResourceAsStream("db.properties");
        Properties properties = new Properties();
        properties.load(resourceAsStream);
        String sftpIp = properties.getProperty("yinlianSftpIp");
        String sftpUserName = properties.getProperty("yinlianSftpUserName");
        String sftpPassword = properties.getProperty("yinlianSftpPassword");
        String sftpPort = properties.getProperty("sftpPort");
        String privateKey = properties.getProperty("privateKey");

        SFTPConnect sftpConnect = new SFTPConnect();
        ChannelSftp sftp = sftpConnect.login(sftpIp, sftpUserName, sftpPassword, sftpPort, privateKey);
        String yestday = LocalDate.now().plusDays(-1).format(DateTimeFormatter.BASIC_ISO_DATE);

        String dir = "/var/data/core/dw/yinlian/" + yestday;
//        try {
        sftp.cd(dir);
        logger.info("登入sftp服务器成功");
//        } catch (SftpException e) {
//            logger.error("贴源文件夹不存在：" + dir);
//            sftpConnect.logout(sftp);
//            resourceAsStream.close();
//            throw e;
//        }
        String file = "CCS_" + yestday + ".zip";
        File tarFile = new File("/home/chengfei/data/tmp/work/" + yestday + "/");
        tarFile.mkdir();
//        try {
        sftp.get(file, new FileOutputStream("/home/chengfei/data/tmp/work/" + yestday + "/" + file));
        logger.info("银数贴源文件拉取成功");
//        } catch (SftpException e) {
//            logger.error("贴源文件不存在：" + dir + file,e);
//            sftpConnect.logout(sftp);
//            resourceAsStream.close();
//            System.exit(1);
//        }
        sftpConnect.logout(sftp);
        resourceAsStream.close();
    }
}
