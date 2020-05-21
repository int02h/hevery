package com.dpforge.hevery.installer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.dpforge.hevery.installer.FileUtils.checkDirectoryExists;
import static com.dpforge.hevery.installer.FileUtils.ensureDirectoryExists;
import static com.dpforge.hevery.installer.FileUtils.write;

public class InstallerMain {

    public static void main(final String[] args) throws IOException {
        if (args.length == 1) {
            installInto(new File(args[0]));
        } else {
            printHelp();
        }
    }

    private static void installInto(final File gitDir) throws IOException {
        final File targetDir = getHooksDir(checkDirectoryExists(gitDir));

        final InputStream is = InstallerMain.class.getClassLoader().getResourceAsStream("content.zip");
        if (is == null) {
            System.out.println("Installer is broken");
            System.exit(1);
        }

        final List<String> rootEntries = new ArrayList<>();
        try (ZipInputStream zis = new ZipInputStream(is)) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (!entry.getName().contains("/")) {
                    rootEntries.add(entry.getName());
                }
                copyEntry(entry, zis, targetDir);
                zis.closeEntry();
            }
        }

        final CommandExecutor commandExecutor = new CommandExecutor(targetDir);
        for (String entry : rootEntries) {
            commandExecutor.execute("chmod +x " + entry);
        }
    }

    private static void copyEntry(
            final ZipEntry entry,
            final ZipInputStream zis,
            final File targetDir
    ) throws IOException {
        final File targetFile = new File(targetDir, entry.getName());
        final boolean overwrite = !entry.getName().toLowerCase().endsWith(".java"); // do not overwrite user changes
        write(zis, targetFile, overwrite);
    }

    private static File getHooksDir(final File gitDir) throws IOException {
        final ExecutionResult result = new CommandExecutor(gitDir).execute("git config core.hooksPath");
        final String hooksDir;
        if (result.isSuccess()) {
            hooksDir = result.output.get(0).trim();
        } else {
            hooksDir = ".git/hooks"; // the default one
        }
        return ensureDirectoryExists(new File(gitDir, hooksDir));
    }

    private static void printHelp() {
        System.out.println("The only argument is the path to git repository folder");
        System.out.println("Example: java -jar hevery-installer.jar /Users/developer/project");
    }
}
