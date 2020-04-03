package com.newLand.udf.deal;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public interface GetCodeTableData {
    public void initialize() throws IOException, SQLException, ClassNotFoundException;
    public String getData(String systemSource,String field,String code) throws SQLException;
}
