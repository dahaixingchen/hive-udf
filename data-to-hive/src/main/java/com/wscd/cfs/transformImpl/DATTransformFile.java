package com.wscd.cfs.transformImpl;

import com.wscd.cfs.transform.TransformFile;
import jodd.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: BATTransformFile
 * @Description: TODO
 * @Author chengfei
 * @Date 2020/1/16 20:47
 * @Version 1.0
 **/
public class DATTransformFile implements TransformFile {

    private String inputFloderPath = null;
    private String inputFileName = null;

    public DATTransformFile(String inputFloderPath, String inputFileName) {
        this.inputFloderPath = inputFloderPath;
        this.inputFileName = inputFileName;
    }
    public DATTransformFile(){}

    @Override
    public List<String> transformFile(String[] defaultField) throws Exception {
        ArrayList<String> transTmpFiles = new ArrayList<>();
        File inputFloderFile = new File(inputFloderPath + inputFileName);
        String outPutFileName = StringUtil.split(inputFileName, ".")[0];
        File outputFloderFile = new File(inputFloderPath + outPutFileName + ".txt");
        transTmpFiles.add(inputFloderPath + outPutFileName + ".txt");
//        if (!inputFloderFile.exists()){
//            throw new Exception(inputFloderFile.getPath() + "所指文件不存在");
//        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader
                (new FileInputStream(inputFloderPath + inputFileName),"GB2312"));

        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter
                (new FileOutputStream(outputFloderFile)));

        StringBuffer tmpStrBuff = new StringBuffer();
        String tmpStr = null;
        //遍历
        while ((tmpStr =bufferedReader.readLine()) != null){
//            tmpStr = StringUtils.substringAfter(tmpStr,"^?");
            tmpStr = StringUtils.replace(tmpStr, "^?", "\u0001");
            tmpStrBuff.append(tmpStr + "\u0001"+defaultField[0]+"\u0001"+defaultField[1]+"\n");
//            System.out.println(tmpStr);
            bufferedWriter.write(tmpStrBuff.toString());
            tmpStrBuff.delete(0,tmpStrBuff.length());
        }

        // 关流顺序，先打开的后关闭
        bufferedWriter.flush();
        bufferedWriter.close();
        bufferedReader.close();
        return transTmpFiles;
    }
}
