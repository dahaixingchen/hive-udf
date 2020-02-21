package com.newLand.udf.deal;

import java.sql.Connection;

public interface GetCodeTableData {
    public void initialize();
    public String getData(String systemSource,String field,String code);
}
