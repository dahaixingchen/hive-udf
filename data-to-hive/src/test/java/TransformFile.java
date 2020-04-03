import org.junit.Test;

import java.io.*;

/**
 * @ClassName: TransformFile
 * @Description: TODO
 * @Author chengfei
 * @Date 2020/3/16 11:01
 * @Version 1.0
 **/
public class TransformFile {
    @Test
    public void test() throws IOException {
        File inFile = new File("D:\\service\\computer\\QQData" +
                "\\1213564251\\FileRecv\\mysql-bin.000046");
        File outFile = new File("D:\\service\\computer\\QQData" +
                "\\1213564251\\FileRecv\\mysql-bin-wp_posts22.txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(inFile));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outFile));
        String tmpStr = null;
        int i = 0;
        while ((tmpStr = bufferedReader.readLine()) != null){
            i++;
            if(tmpStr.contains("DELETE FROM `wp_options`")){
                if (i>1000){
                    break;
                }
                bufferedWriter.write(tmpStr);
            }
        }
        bufferedWriter.flush();
    }
}
