package com.wscd.cfs.transformImpl;

import com.wscd.cfs.transform.TransformFile;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @ClassName: CSVTransformFile
 * @Description: TODO
 * @Author chengfei
 * @Date 2020/1/10 17:28
 * @Version 1.0
 **/
public class ZIPCSVTransformFileAllFile implements TransformFile {
    private static Logger logger = Logger.getLogger(ZIPCSVTransformFileAllFile.class);

    private String inputFloderPath = null;
    private String inputFileName = null;

    public ZIPCSVTransformFileAllFile(String inputFloderPath, String inputFileName) {
        this.inputFloderPath = inputFloderPath;
        this.inputFileName = inputFileName;
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
            String fileName = entry.getName();
            if (fileName.indexOf(".csv") == -1){
                continue;
            }
            // 如果是文件夹，就创建个文件夹
            if (entry.isDirectory()) {
                String dirPath = inputFloderPath + entry.getName();
                srcFile.mkdirs();
            } else {
                File targetFile = new File(inputFloderPath + File.separator + entry.getName());
                logger.info("zip压缩文件中的目标文件targetFile路径："+inputFloderPath + File.separator + entry.getName());
                transTmpFiles.add(inputFloderPath + entry.getName());
                logger.info("zip压缩文件用来load的文件路径"+inputFloderPath + entry.getName());
                // 保证这个文件的父文件夹必须要存在
                if (!targetFile.getParentFile().exists()) {
                    targetFile.getParentFile().mkdirs();
                }
                targetFile.createNewFile();
                // 将压缩文件内容写入到这个文件中
                InputStream is = zipFile.getInputStream(entry);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
                FileOutputStream fos = new FileOutputStream(targetFile);
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fos));
                StringBuffer tmpStrBuff = new StringBuffer();
                String tmpStr = null;
                boolean isFirstRow = true;
                int i = 1 ;
                while ((tmpStr = bufferedReader.readLine()) != null){
                    if(!isFirstRow){
                        tmpStr = StringUtils.replace(tmpStr, "|+|", "\u0001");
                        tmpStrBuff.append(tmpStr + "\u0001"+defaultField[0]+"\u0001"+defaultField[1]+"\n");
                        bufferedWriter.write(tmpStrBuff.toString());
                        tmpStrBuff.delete(0,tmpStrBuff.length());

                    }
                    isFirstRow = false;
                    i ++;
                }
                // 关流顺序，先打开的后关闭
                bufferedWriter.flush();
                fos.close();
                is.close();
            }
        }
        return transTmpFiles;
    }

}
