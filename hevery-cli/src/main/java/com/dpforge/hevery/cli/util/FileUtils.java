package com.dpforge.hevery.cli.util;

import java.io.File;

public class FileUtils {
    private FileUtils() {
    }

    public static File ensureDirectoryExists(final File directory) {
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                throw new IllegalStateException("Fail to create directory or one of its parent");
            }
        }
        return directory;
    }

    public static String getNameWithoutExtension(final File file) {
        if (file.isDirectory()) {
            throw new IllegalArgumentException("File '" + file.getAbsolutePath() + "' is directory");
        }
        final String name = file.getName();
        final int dotPos = name.lastIndexOf('.');
        return (dotPos == -1) ? name : name.substring(0, dotPos);
    }

}
