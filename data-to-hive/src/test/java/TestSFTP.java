import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.wscd.cfs.connect.SFTPConnect;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

/**
 * @ClassName: TestSFTP
 * @Description: TODO
 * @Author chengfei
 * @Date 2020/1/16 11:34
 * @Version 1.0
 **/
public class TestSFTP {
    @Test
    public void test() throws IOException, SftpException, JSchException {
        InputStream resourceAsStream = TestSFTP.class.getClassLoader().getResourceAsStream("db.properties");
        Properties properties = new Properties();
        properties.load(resourceAsStream);
        String sftpIp = properties.getProperty("yinlianSftpIp");
        String sftpUserName = properties.getProperty("yinlianSftpUserName");
        String sftpPassword = properties.getProperty("yinlianSftpPassword");
        String sftpPort = properties.getProperty("sftpPort");
        String privateKey = properties.getProperty("privateKey");

        SFTPConnect sftpConnect = new SFTPConnect();
        ChannelSftp sftp = sftpConnect.login(sftpIp, sftpUserName, sftpPassword, sftpPort, privateKey);

        System.out.println(LocalDateTime.now().toString());
        String yestday = LocalDate.now().plusDays(-Integer.parseInt("1")).format(DateTimeFormatter.BASIC_ISO_DATE);
        String savePath = "D:\\study\\"+yestday+File.separator;
        File file = new File(savePath);
        file.mkdir();
        sftpConnect.download("/var/data/core/dw/yinlian/20200115","CCS_20200113.zip",savePath);
    }
}
