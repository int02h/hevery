package com.dpforge.hevery.cli.compiler;

import java.io.File;

class FileCollector {

    private final String extension;

    FileCollector(String extension) {
        this.extension = extension.toLowerCase();
    }

    File[] collect(File directory) {
        return directory.listFiles(
                pathname -> !pathname.isDirectory() && pathname.getName().toLowerCase().endsWith(extension)
        );
    }

}
