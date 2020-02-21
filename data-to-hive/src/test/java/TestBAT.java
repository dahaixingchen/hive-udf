import com.wscd.cfs.LoadData2Hive;
import com.wscd.cfs.transformImpl.DATTransformFile;

import javax.swing.text.DateFormatter;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @ClassName: TestBAT
 * @Description: TODO
 * @Author chengfei
 * @Date 2020/1/16 21:19
 * @Version 1.0
 **/
public class TestBAT {
    public static void main(String[] args) throws Exception {
//        System.out.println(LocalDate.now().plusDays(-1).toString());
        String str1="\n" +"dsfasd\n";
        StringBuilder stringBuilder = new StringBuilder(str1);
        stringBuilder.insert(4, "-").insert(7,"-");
        System.out.println(stringBuilder.toString());
        File file = new File("E:\\WorkData\\test.txt");
        if (!file.exists()){
            file.createNewFile();
        }
    }
}
