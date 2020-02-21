
import com.wscd.cfs.transform.TransformFile;
import jodd.util.StringUtil;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: TransformFile
 * @Description: TODO
 * @Author chengfei
 * @Date 2020/1/9 10:18
 * @Version 1.0
 **/
public class PlaceTest implements TransformFile {
    private static Logger logger = Logger.getLogger(XLSXTransformFileCopy.class);
    private String inputFloderPath = null;
    private String inputFileName = null;

    private List<Map<String, Integer>> hiveColumns ;
    private ArrayList<Integer> fileIndexList = new ArrayList<>();

    public PlaceTest(String inputFloderPath, String inputFileName,List<Map<String, Integer>> hiveColumns) {
        this.inputFloderPath = inputFloderPath;
        this.inputFileName = inputFileName;
        this.hiveColumns = hiveColumns;
    }

    @Override
    public List<String> transformFile(String [] defaultField) throws Exception {
        ArrayList<String> transTmpFiles = new ArrayList<>();
        File inputFloderFile = new File(inputFloderPath + inputFileName);
        if (!inputFloderFile.exists()){
            throw new Exception(inputFloderFile.getPath() + "所指文件不存在");
        }
        String outPutFileName = StringUtil.split(inputFileName, ".")[0];
        File outputFloderFile = new File(inputFloderPath + outPutFileName + ".txt");
        if (!outputFloderFile.exists()){
            outputFloderFile.createNewFile();
        }

        Workbook wb = null;
        FileWriter fileWriter = null;
        fileWriter = new FileWriter(outputFloderFile);
        wb = new XSSFWorkbook(inputFloderFile);
        //开始解析
        Sheet sheet = wb.getSheetAt(0);
        //第一行是列名
        Row firstRow = sheet.getRow(sheet.getFirstRowNum());

        //如果文件中的字段数和hive表中的字段数不一致，直接返回
        int firstCellIndex = firstRow.getFirstCellNum();
        int lastCellIndex = firstRow.getLastCellNum();
        if(lastCellIndex+3 != hiveColumns.size()){
            logger.warn("文件中字段的个数和hive表中的字段的个数不一致");
            return transTmpFiles;
        }
        logger.info("文件转换时输出文件的路径"+inputFloderPath + outPutFileName + ".txt");
        transTmpFiles.add(inputFloderPath + outPutFileName + ".txt");

        getFileColumnOrder(firstRow, firstCellIndex, lastCellIndex, fileIndexList);

        int firstRowIndex = sheet.getFirstRowNum() + 1;
        int lastRowIndex = sheet.getLastRowNum();
        StringBuffer strBuffer = new StringBuffer();
        //遍历行
        for (int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {
            Row row = sheet.getRow(rIndex);
            if (row != null) {
                //遍历列
                for (int cIndex = firstCellIndex; cIndex < lastCellIndex; cIndex++) {
                    Cell cell = row.getCell(cIndex);
                    if (cell != null) {
                        strBuffer.append(cell.toString() + "\u0001" );
                    }
                }
            }
            //调整顺序
            String[] split = strBuffer.toString().split("\u0001");
            strBuffer.delete(0,strBuffer.length());
            int j=0;
            for (int i = 0;i < fileIndexList.size() ;i++){
                if (fileIndexList.get(i) == -1){
                    strBuffer.append(defaultField[j] + "\u0001" );
                    j++;
                }else{
                    strBuffer.append(split[fileIndexList.get(i)] + "\u0001" );
                }
            }

            strBuffer.append("\n");
            String s = strBuffer.toString();

            fileWriter.write(strBuffer.toString());
            strBuffer.delete(0, strBuffer.length());
        }
        fileWriter.flush();
        fileWriter.close();
//        wb.close();
        return transTmpFiles;
    }

    private void getFileColumnOrder(Row firstRow, int firstCellIndex, int lastCellIndex, ArrayList<Integer> fileIndexList) {
        //hive表中最后一个字段是分区字段
        for (int i =0; i < hiveColumns.size()-1;i++){

            int cIndex = firstCellIndex;
            for (; cIndex < lastCellIndex; cIndex++) {
                Cell cell = firstRow.getCell(cIndex);
                if (cell.toString() != null && hiveColumns.get(i).containsKey(cell.toString())){
                    //如果能匹配到，记录文件字段的位置
                    fileIndexList.add(cIndex);
                    break;
                }
            }
            //如果匹配不到，则认为是默认值为-1
            if (cIndex == lastCellIndex){
                fileIndexList.add(-1);
            }
        }
    }
}
