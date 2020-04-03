import org.junit.Test;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName: FileSearch
 * @Description: TODO
 * @Author chengfei
 * @Date 2020/2/27 15:07
 * @Version 1.0
 **/

public class FileSearch {

    public static String[] getFileName(String path) {

        File file = new File(path);

        String[] fileName = file.list();

        return fileName;

    }

    public static void getAllFileName(String path, ArrayList<String> fileName) {

        File file = new File(path);

        File[] files = file.listFiles();

        String[] names = file.list();

        if (names != null)

            fileName.addAll(Arrays.asList(names));

        for (File a : files) {

            if (a.isDirectory()) {

                getAllFileName(a.getAbsolutePath(), fileName);

            }

        }

    }

    public static void main(String[] args) {

        System.out.println("--------------------------------");

        ArrayList<String> listFileName = new ArrayList<String>();

        String[] name = {
                "AbsDataUpdateTask.kjb"
                ,"AppNoCorrespondenceMreportTask.kjb"
                ,"AutomaticYinlianParseOdsToDwCommonTask.kjb"
                ,"AutomaticYinlianParseOdsToDwPbocTask.kjb"
                ,"AutomaticYinlianParseOdsToDwTask.kjb"
                ,"BlackGreyListTask.kjb"
                ,"BlacklistDelayedDataTask.kjb"
                ,"CreditDataToDwTask.kjb"
                ,"CuishouNewSecondUpLoad12.kjb"
                ,"CuishouNewSecondUpLoad8.kjb"
                ,"CXOdsToDwCommonTask.kjb"
                ,"CXOdsToDwPbocTask.kjb"
                ,"CxToOdsTask.kjb"
                ,"DuizhangFile.kjb"
                ,"DvmwebDataDictionaryTask.kjb"
                ,"dwCoreToBhbsdb.kjb"
                ,"DwToKpiTask.kjb"
                ,"FenDanHuaAnTask.kjb"
                ,"FenDanShouChuang.kjb"
                ,"FineReportParseOdsToPerformanceTask.kjb"
                ,"GroupBiReportTask.kjb"
                ,"HaierDataTask.kjb"
                ,"HaierLoanDataSelectTask.kjb"
                ,"HuaAnRtSmsTask.kjb"
                ,"loanoverdueIndexLegalPersonTask.kjb"
                ,"loan_statusTablesTask.kjb"
                ,"ManualDataTask.kjb"
                ,"MarketToDvmwebTask.kjb"
                ,"MarketToFactoringrptTask.kjb"
                ,"MarketToPerformanceTask.kjb"
                ,"MktReportToPerfromanceTask.kjb"
                ,"NewDataCheckTask.kjb"
                ,"NwUploadTask.kjb"
                ,"OdsBaoliCCBToDwTask.kjb"
                ,"OdsCxToDwTask.kjb"
                ,"OdsNufsToGDXTDuizhangTask.kjb"
                ,"OdsNufsToXWDuizhangTask.kjb"
                ,"OdsTablesToMktOnlineRiskTask.kjb"
                ,"OdsTablesToMktTask.kjb"
                ,"OdsToPerformanceTask.kjb"
                ,"OdsWscfsToDwCommonTask.kjb"
                ,"OdsWscfsToDwPbocTask.kjb"
                ,"OdsYinlianSyncCompensationTask.kjb"
                ,"OdsYinLianToMktTask.kjb"
                ,"OfflineFeeTask.kjb"
                ,"OfflineGuotongBussnessDataFtpReportTask.kjb"
                ,"OfflineRmpsdbToDw.kjb"
                ,"OncopToOdsIncrementTask.kjb"
                ,"OnlineRmpsdToDw.kjb"
                ,"OverdueIndexGenerateIncrement.kjb"
                ,"OverdueIndexTask.kjb"
                ,"OverdueIndexTwoPhaseTask.kjb"
                ,"ParseTblRiskToAppreportingTask.kjb"
                ,"PaymentVoucherFuMingTask.kjb"
                ,"PbocMkt.kjb"
                ,"ProcessMasterDataPatchTask.kjb"
                ,"ReportFinancialTask.kjb"
                ,"RiskJobTask.kjb"
                ,"StartPostUploadTask.kjb"
                ,"SupportIndexProcessingTask.kjb"
                ,"WolaidaiDuiZhangSFTP.kjb"
                ,"WscfsTask.kjb"
                ,"WsFactoringGuotongFtpTask.kjb"
                ,"WssbSecondVersionDataTask.kjb"
                ,"YDSubmitDataTask.kjb"
                ,"ZrbFileFtpUploadTask.kjb"
        };
        getAllFileName("E:\\WorkData\\依赖任务改造\\程飞\\task", listFileName);
        ArrayList<String> deleteList = new ArrayList<>();
        for (String a:listFileName){
            int i = 0;
            for (String b : name){
                i++;
                if(a.equals(b)){
                    break;
                }
                if (name.length == i){
                    deleteList.add(a);
                    System.out.println(a);
                }
            }
        }

        //删除文件
        File file = null;
        for (String c:deleteList){
            file = new File("E:\\WorkData\\依赖任务改造\\程飞\\task\\" + c);
            file.delete();
        }

    }


    @Test
    public void test(){
        String[] fileName = getFileName("E:\\WorkData\\依赖任务改造\\程飞\\task");
        for (String s:fileName){
            System.out.println(s);
        }
    }

    @Test
    public void test1(){

        String yestday = LocalDate.now().plusDays(0).format(DateTimeFormatter.BASIC_ISO_DATE);
        System.out.println(yestday);

    }

}