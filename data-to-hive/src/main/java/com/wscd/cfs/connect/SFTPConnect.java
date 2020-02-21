package com.wscd.cfs.connect;

import com.jcraft.jsch.*;
import com.wscd.cfs.RunAllTask;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.DatagramSocket;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

/**
 * @ClassName: SFTPConnect
 * @Description: TODO
 * @Author chengfei
 * @Date 2020/1/16 11:02
 * @Version 1.0
 **/
public class SFTPConnect {
    private static org.apache.log4j.Logger logger = Logger.getLogger(SFTPConnect.class);

    private  ChannelSftp sftp;

    private  Session session;

    public  ChannelSftp login(String sftpIp,String sftpUserName,String sftpPassword,String sftpPort,String privateKey) throws JSchException {
        try {
            JSch jsch = new JSch();
            if (!"".equals(privateKey)) {
                // 设置私钥
                jsch.addIdentity(privateKey);
            }
            session = jsch.getSession(sftpUserName, sftpIp, Integer.parseInt(sftpPort));

            if (sftpPassword != null) {
                session.setPassword(sftpPassword);
            }
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");

            session.setConfig(config);
            session.connect();

            Channel channel = session.openChannel("sftp");
            channel.connect();

            sftp = (ChannelSftp) channel;
        } catch (JSchException e) {
            logger.error("连接sftp失败.");
            throw e;
        }
        return sftp;
    }

    /**
     * 下载文件。
     * @param directory 下载目录
     * @param downloadFile 下载的文件
     * @param saveDirectory 存在本地的路径
     */
    public boolean download(String directory, String downloadFile, String saveDirectory) throws FileNotFoundException, SftpException {
        boolean flag = true;
        if (directory != null && !"".equals(directory)) {
            try {
                sftp.cd(directory);
            } catch (SftpException e) {
                logger.error("贴源文件不存在：" + directory);
                flag = false;
                throw e;
            }
        }else{
            logger.error("贴源文件夹路径有问题，对应的文件：" + directory);
            throw new FileNotFoundException();
        }

        File file = new File(saveDirectory+downloadFile);
        if (file.exists()){
            logger.info("文件"+saveDirectory+downloadFile+"已经复制过");
        }else {
            sftp.get(downloadFile, new FileOutputStream(file));
        }
        return flag;
    }
    /**
     * 关闭连接 server
     */
    public void logout(ChannelSftp sftp){
        if (sftp != null) {
            if (sftp.isConnected()) {
                sftp.disconnect();
            }
        }
        if (session != null) {
            if (session.isConnected()) {
                session.disconnect();
            }
        }
    }

}
