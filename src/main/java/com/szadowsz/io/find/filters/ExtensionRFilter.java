package com.szadowsz.io.find.filters;

import java.io.File;

public class ExtensionRFilter extends ExtensionFilter {

    public ExtensionRFilter(String extension) {
        super(extension);
    }

    @Override
    public boolean accept(File directory, String fileName) {
        return super.accept(directory, fileName) || (new File(directory, fileName)).isDirectory();
    }
}
