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
public class XLSXTransformFileCopy implements TransformFile {
    private static Logger logger = Logger.getLogger(XLSXTransformFileCopy.class);
    private String inputFloderPath = null;
    private String inputFileName = null;

    public XLSXTransformFileCopy(String inputFloderPath, String inputFileName, ArrayList<Map<String, Integer>> hiveColumns) {
        this.inputFloderPath = inputFloderPath;
        this.inputFileName = inputFileName;
    }

    @Override
    public List<String> transformFile(String [] defaultField) throws Exception {
        ArrayList<String> transTmpFiles = new ArrayList<>();
        File inputFloderFile = new File(inputFloderPath + inputFileName);
        logger.info("文件转换时需要转换文件的路径"+inputFloderPath + inputFileName);
        String outPutFileName = StringUtil.split(inputFileName, ".")[0];
        File outputFloderFile = new File(inputFloderPath + outPutFileName + ".txt");
        if (!outputFloderFile.exists()){
            outputFloderFile.createNewFile();
        }
        logger.info("文件转换时输出文件的路径"+inputFloderPath + outPutFileName + ".txt");
        transTmpFiles.add(inputFloderPath + outPutFileName + ".txt");
        if (!inputFloderFile.exists()){
            throw new Exception(inputFloderFile.getPath() + "所指文件不存在");
        }
        Workbook wb = null;
        FileWriter fileWriter = null;
        fileWriter = new FileWriter(outputFloderFile);
        wb = new XSSFWorkbook(inputFloderFile);
        //开始解析
        Sheet sheet = wb.getSheetAt(0);
        //第一行是列名
        int firstRowIndex = sheet.getFirstRowNum() + 1;
        int lastRowIndex = sheet.getLastRowNum();
        StringBuffer strBuffer = new StringBuffer();
        //遍历行

        for (int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {
            Row row = sheet.getRow(rIndex);
            if (row != null) {
                int firstCellIndex = row.getFirstCellNum();
                int lastCellIndex = row.getLastCellNum();
                //遍历列
                for (int cIndex = firstCellIndex; cIndex < lastCellIndex; cIndex++) {
                    Cell cell = row.getCell(cIndex);
                    if (cell != null) {
                        strBuffer.append(cell.toString() + "\u0001" );
                    }
                }
            }
            strBuffer.append(defaultField[0]+"\u0001" +defaultField[1] + "\n");
            String s = strBuffer.toString();
            fileWriter.write(strBuffer.toString());
            strBuffer.delete(0, strBuffer.length());
        }
        fileWriter.flush();
        fileWriter.close();
//        wb.close();
        return transTmpFiles;
    }
}
