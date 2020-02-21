package com.wscd.cfs;

import java.lang.String;

import org.apache.log4j.Logger;

/**
 * @ClassName: MainProcess
 * @Description: TODO
 * @Author chengfei
 * @Date 2020/1/12 16:26
 * @Version 1.0
 **/
public class RunAllTask {
    private static Logger logger = Logger.getLogger(RunAllTask.class);
    public static void main(String[] args) {
//        List<Entity> sourceData = new MysqlJDBC().getData();
//        String[] defaultField = {LocalDateTime.now().toString(),"chengfei"};
//        LoadData2Hive loadData2Hive = new LoadData2Hive(defaultField);
//        LocalDate today = LocalDate.now();
//        String inputFloderPath = null;
//        String inputFileName = null;
//        String targetNames = null;
//        int i = 1;
//        for (Entity entity:sourceData){
//            logger.info("***************************************");
//            logger.info("***************************************");
//            logger.info("第"+ i +"个抽数任务开始");
//            String fileDate = today.plusDays(-Integer.parseInt(entity.getFil_diff_runDate())).format(DateTimeFormatter.BASIC_ISO_DATE);
//            if (entity.getSou_dirPar() != null){
//                inputFloderPath = entity.getSou_dir() + File.separator + entity.getSou_dirPar() + fileDate +File.separator;
//                logger.info("输入文件夹的路径：" + inputFloderPath);
//            }else{
//                inputFloderPath = entity.getSou_dir() + File.separator;
//                logger.info("输入文件夹的路径：" + inputFloderPath);
//            }
//
//            if ("D".equals(entity.getSystem_source())){
//                try {
//                    inputFileName = entity.getSou_filPar() + fileDate + ".csv";
//                    logger.info("输入文件名：" + inputFileName);
//                    loadData2Hive.loadData2Hive("D",new XLSXTransformFile(inputFloderPath,inputFileName));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }else if("Y".equals(entity.getSystem_source())){
//                try {
//                    inputFileName = entity.getSou_filPar() + fileDate + ".zip";
//                    logger.info("输入文件名：" + inputFileName);
//                    loadData2Hive.loadData2Hive("Y",new CSVTransformFileAllFile(inputFloderPath,inputFileName));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }else{
//                logger.warn("System_source来源不对");
//            }
//            logger.info("第"+ i +"个抽数任务结束");
//            i++;
//        }
    }
}
