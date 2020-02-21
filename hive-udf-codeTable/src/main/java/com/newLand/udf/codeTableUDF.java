package com.newLand.udf;

import com.newLand.udf.deal.JDBCGetCodeTableData;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.hive.ql.udf.UDFType;

/**
 * @ClassName: codeTableUDF
 * @Description: TODO
 * @Author chengfei
 * @Date 2020/1/3 17:29
 * @Version 1.0
 **/
@UDFType
@Description(
        name = "transform_codeTable",
        value = "_FUNC_ (systemSource,field,code) - " +
                " from the input systemSource,field,code string " +
                " returns the code of value",
        extended = "Example :" +
                "> SELECT _FUNC_ (systemSource,field,code) FROM src;"
)
public class codeTableUDF extends UDF {
    public String evaluate(String systemSource, String field,String code) {
        JDBCGetCodeTableData jdbcGetCodeTableData = new JDBCGetCodeTableData();
        jdbcGetCodeTableData.initialize();
        return jdbcGetCodeTableData.getData(systemSource, field, code);
    }

}
