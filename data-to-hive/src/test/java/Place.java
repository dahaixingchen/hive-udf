import com.wscd.cfs.contrast_columns.HiveColumns;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;

/**
 * @ClassName: HiveColumns
 * @Description: TODO
 * @Author chengfei
 * @Date 2020/2/4 12:01
 * @Version 1.0
 **/
public class Place {
    public static void main(String[] args) throws Exception {
        ArrayList<Map<String, Integer>> hiveColumns = new HiveColumns("ods.ods_nf_yinlian_ccs_ORDER").getHiveColumns();

        String yestday = LocalDate.now().plusDays(-1).format(DateTimeFormatter.BASIC_ISO_DATE);
        String inputFloderPath = "E:\\WorkData\\testData\\";
        String inputFileName = "CCS_20200301.zip";
        String [] defaultField = {yestday,"系统抽数"};
        PlaceTestYinLian placeTestYinLian = new PlaceTestYinLian(inputFloderPath, inputFileName,"ods_nf_yinlian_ccs_ORDER",hiveColumns);
        placeTestYinLian.transformFile(defaultField);
    }
}
