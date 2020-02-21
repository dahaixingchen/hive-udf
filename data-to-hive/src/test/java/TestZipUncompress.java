import com.wscd.cfs.transformImpl.ZIPCSVTransformFileAllFile;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

/**
 * @ClassName: TestZipUncompress
 * @Description: TODO
 * @Author chengfei
 * @Date 2020/1/10 17:34
 * @Version 1.0
 **/
public class TestZipUncompress {
    @Test
    public void test() throws Exception {
        new ZIPCSVTransformFileAllFile("E:\\WorkData\\CCS_20200109.zip","E:\\WorkData\\ccs")
                .transformFile(new String [] {"2020-01-12","chengfei","D"});
    }

    @Test
    public void path(){
        String separator = "/|\\\\";
        String s =  "C:\\Users\\feifei\\Desktop\\newLandFinancial\\contract_info_20200112.txt";
        s = "2020-01-13^?HT201702201500001852920001^?0^?2017-02-22^?^?^?0^?0^?0^?0^?0^?0^?0^?0^?0^?0^?0^?0^?0^?0^?0^?150000^?0^?.288^?0^?0^?N^?N^?N^?^?^?^?0^?0^?^?^?N^?^?N^?0^?710000^?新大陆(1)^?38948355";
//        s = "/tmp/wo.csv";
        String[] split =s
                .split("/|\\\\");
        String targetTableName = split[split.length-1];
        String s1 = StringUtils.substringBeforeLast(targetTableName, "_");
        s1 = StringUtils.substringAfter(targetTableName,"^?");
        System.out.println(s1);
    }
}
