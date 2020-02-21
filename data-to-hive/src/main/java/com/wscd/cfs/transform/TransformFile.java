package com.wscd.cfs.transform;

import java.util.List;

public interface TransformFile {
    public List<String> transformFile(String [] defaultField) throws Exception;
}
