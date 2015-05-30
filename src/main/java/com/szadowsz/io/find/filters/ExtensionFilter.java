package com.szadowsz.io.find.filters;

import java.io.FilenameFilter;
import java.io.File;

public class ExtensionFilter implements FilenameFilter {

    protected String _ext;

    public ExtensionFilter(String extension) {
        _ext = extension.substring(extension.lastIndexOf('.') + 1);
    }

    @Override
    public boolean accept(File directory, String fileName) {
        return fileName.endsWith(_ext);
    }
}