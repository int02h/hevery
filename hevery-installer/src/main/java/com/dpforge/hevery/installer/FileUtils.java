package com.dpforge.hevery.installer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

class FileUtils {
    private FileUtils() {
    }

    static File ensureDirectoryExists(final File directory) {
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                throw new IllegalStateException("Fail to create directory or one of its parent");
            }
        }
        return directory;
    }

    static File checkDirectoryExists(final File directory) {
        if (!directory.exists()) {
            throw new IllegalArgumentException("Directory '" + directory.getAbsolutePath() + "' does not exist");
        }
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException("Directory '" + directory.getAbsolutePath() + "' is not a directory");
        }
        return directory;
    }

    static void write(final InputStream inputStream, File file, boolean overwrite) throws IOException {
        ensureDirectoryExists(file.getParentFile());
        if (overwrite) {
            Files.copy(inputStream, file.toPath(), REPLACE_EXISTING);
        } else if (!file.exists()) {
            Files.copy(inputStream, file.toPath());
        }
    }

}
