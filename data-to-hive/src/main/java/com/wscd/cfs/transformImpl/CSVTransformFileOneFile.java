package com.wscd.cfs.transformImpl;

import com.wscd.cfs.transform.TransformFile;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.stringtemplate.v4.ST;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @ClassName: CSVTransformFileOneFile
 * @Description: TODO
 * @Author chengfei
 * @Date 2020/1/14 18:06
 * @Version 1.0
 **/
public class CSVTransformFileOneFile implements TransformFile {
    private static org.apache.log4j.Logger logger = Logger.getLogger(CSVTransformFileOneFile.class);
    private String inputFloderPath = null;
    private String inputFileName = null;
    private String targetTableName = null;

    private List<Map<String, Integer>> hiveColumns ;
    private ArrayList<Integer> fileIndexList = new ArrayList<>();

    public CSVTransformFileOneFile(String inputFloderPath, String inputFileName, String targetTableName, List<Map<String, Integer>> hiveColumns) {
        this.inputFloderPath = inputFloderPath;
        this.inputFileName = inputFileName;
        this.targetTableName = targetTableName;
        this.hiveColumns = hiveColumns;
    }
    @Override
    public List<String> transformFile(String[] defaultField) throws Exception{
        File srcFile = new File(inputFloderPath+inputFileName);
        // 判断源文件是否存在
        if (!srcFile.exists()) {
            throw new Exception(srcFile.getPath() + "所指文件不存在");
        }
        ArrayList<String> transTmpFiles = new ArrayList<>();
        ZipFile zipFile = new ZipFile(srcFile);
        //开始解压
        Enumeration<?> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = (ZipEntry) entries.nextElement();
            String[] split = entry.getName().split("/|\\\\");
            String fileName = split[split.length-1];
            //只需要转换目标文件
            String s = "ods_nf_yinlian_" + StringUtils.substringBeforeLast(fileName, "_");
            int i1 = fileName.indexOf(".csv");
            if (!s.toLowerCase().equals(targetTableName.toLowerCase()) || fileName.indexOf(".csv") == -1){
                continue;
            }else{
                // 如果是文件夹，就创建个文件夹
                if (entry.isDirectory()) {
                    String dirPath = inputFloderPath + entry.getName();
                    srcFile.mkdirs();
                } else {
                    File targetFile = new File(inputFloderPath  + entry.getName());
                    // 保证这个文件的父文件夹必须要存在

                    transTmpFiles.add(inputFloderPath + entry.getName());
                    if (!targetFile.getParentFile().exists()) {
                        targetFile.getParentFile().mkdirs();
                    }
                    targetFile.createNewFile();
                    InputStream is = zipFile.getInputStream(entry);
                    // 将压缩文件内容写入到这个文件中
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
                    FileOutputStream fos = new FileOutputStream(targetFile);
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fos));
                    StringBuffer tmpStrBuff = new StringBuffer();
                    String tmpStr = null;
                    boolean isFirstRow = true;

                    int ii = 0;

                    while ((tmpStr = bufferedReader.readLine()) != null){
                        int i = 0 ;
                        if(!isFirstRow){

                            ii++;
                            String tmpStr_test = tmpStr;

                            tmpStr =StringUtils.replace(tmpStr,"|+|","\u0001 ");
                            String[] split1 = tmpStr.split("\u0001");

                            for(int j = 0;j <fileIndexList.size(); j++){
                                if (-1 != fileIndexList.get(j)){
                                    tmpStrBuff.append(split1[fileIndexList.get(j)].trim() + "\u0001");
                                }else{
                                    if (i >=2){
                                        logger.warn("文件表和hive表中有字段不一样");
                                    }else{
                                        tmpStrBuff.append(defaultField[i] + "\u0001");
                                    }
                                    i++;
                                }
                            }
                            tmpStrBuff.append("\n");
                            bufferedWriter.write(tmpStrBuff.toString());
                            tmpStrBuff.delete(0,tmpStrBuff.length());
                        }else{
                            String[] split1 = StringUtils.split(tmpStr,"|+|");
                            //hive 表中的字段和文件中的字段个数不一致直接返回
                            if (hiveColumns.size()-3 != split1.length){
                                logger.info("hive表的字段个数"+hiveColumns.size());
                                logger.info("文件的字段个数"+split1.length);
                                logger.error("文件中字段的个数和hive表中的字段的个数不一致");
                                throw new Exception("文件中字段的个数和hive表中的字段的个数不一致");
                            }

                            for (int j = 0;j<hiveColumns.size()-1;j++){
                                int k = 0;
                                for (;k < split1.length; k++){
                                    if (hiveColumns.get(j).containsKey(split1[k].toLowerCase())){
                                        fileIndexList.add(k);
                                        break;
                                    }
                                }
                                if (k == split1.length){
                                    fileIndexList.add(-1);
                                }
                            }
                        }
                        isFirstRow = false;
                    }
                    // 关流顺序，先打开的后关闭
                    bufferedWriter.flush();
                    fos.close();
                    is.close();
                }
            }
        }
        return transTmpFiles;
    }
}
