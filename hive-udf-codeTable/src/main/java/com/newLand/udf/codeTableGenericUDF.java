package com.newLand.udf;

import com.newLand.udf.deal.JDBCGetCodeTableData;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.UDFType;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDFUtils;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorUtils;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.DateObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.StringObjectInspector;

/**
 * @ClassName: codeTableUDF
 * @Description: TODO
 * @Author chengfei
 * @Date 2020/1/3 11:06
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
public class codeTableGenericUDF extends GenericUDF {

    private StringObjectInspector systemSourceString = null;
    private StringObjectInspector fieldString = null;
    private StringObjectInspector codeString = null;

    private GenericUDFUtils.ReturnObjectInspectorResolver returnObjectInspectorResolver;

    @Override
    public ObjectInspector initialize(ObjectInspector[] objectInspectors) throws UDFArgumentException {
        Integer len = objectInspectors.length;
        if (!len.equals(3)) {
            throw new UDFArgumentException("Not Invalid num of arguments");
        }else {
            ObjectInspector tmpDataString1 = objectInspectors[0];
            ObjectInspector tmpDataString2 = objectInspectors[1];
            ObjectInspector tmpDataString3 = objectInspectors[2];
            if (!(tmpDataString1 instanceof StringObjectInspector) || !(tmpDataString2 instanceof StringObjectInspector) || !(tmpDataString3 instanceof StringObjectInspector)){
                throw new UDFArgumentException("Not Invalid type of month or day , please use int type");
            }
            this.systemSourceString = (StringObjectInspector)tmpDataString1;
            this.fieldString = (StringObjectInspector) tmpDataString2;
            this.codeString = (StringObjectInspector) tmpDataString3;
        }
        //初始化jdbc连接池
        new JDBCGetCodeTableData().initialize();

        return PrimitiveObjectInspectorFactory.javaStringObjectInspector;
    }

    @Override
    public Object evaluate(DeferredObject[] deferredObjects) throws HiveException {
        Integer length = deferredObjects.length;

        String systemSource = null;
        String field = null;
        String code = null;

        if (length.equals(3)) {
            systemSource = this.systemSourceString.getPrimitiveJavaObject(deferredObjects[0].get());
            field = this.fieldString.getPrimitiveJavaObject(deferredObjects[1].get());
            code = this.codeString.getPrimitiveJavaObject(deferredObjects[2].get());
        }
        String dataCode = new JDBCGetCodeTableData().getData(systemSource, field, code);
        return dataCode;
    }

    @Override
    public String getDisplayString(String[] children) {
        return "Please check your code, unknown Exception!!!";
    }
}
