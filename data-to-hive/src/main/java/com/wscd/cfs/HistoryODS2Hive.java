package com.wscd.cfs;

import org.apache.commons.lang3.StringUtils;

import java.io.*;

/**
 * @ClassName: ODS2Hive
 * @Description: TODO
 * @Author chengfei
 * @Date 2020/1/19 19:40
 * @Version 1.0
 **/
public class HistoryODS2Hive {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(
                new File("E:\\WorkData\\新大陆入仓脚本" +
                        "\\贴源历史数据（新）\\ods_nf_wd_t_xj_repay_plan.txt"))));
        int count = 0;
        String tmpStr = null;
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
                new File("E:\\aaa.txt")), "UTF-8"));
        StringBuffer strBuf = new StringBuffer();
        String ss= null;
        String[] split = null;
        String[] split1 = null;
        while ((tmpStr = bufferedReader.readLine()) != null){
            String s = StringUtils.substringBefore(tmpStr, "\u0001");
//            split1 = s.split("/");
//            ss = split1[2]+"-"+split1[1]+"-"+split1[0];
//            tmpStr = ss +"\u0001" +StringUtils.substringAfter(tmpStr,"\u0001") + "\r\n";
//            bufferedWriter.write(tmpStr);
            count++;
        }
        System.out.println(count);
//        "\u0001  \u0001"
        bufferedWriter.flush();
        bufferedWriter.close();
        bufferedReader.close();
    }
}
