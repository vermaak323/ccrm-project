package edu.ccrm.io;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BackupService {

    // Create a backup copy of export files into "backup-<timestamp>" folder
    public static Path backupExportDir(Path exportDir) throws IOException {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));
        Path backupDir = Paths.get("backup-" + timestamp);
        Files.createDirectories(backupDir);

        try (var files = Files.list(exportDir)) {
            files.forEach(p -> {
                try {
                    if (Files.isRegularFile(p)) {
                        Files.copy(p, backupDir.resolve(p.getFileName()), StandardCopyOption.REPLACE_EXISTING);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        return backupDir;
    }

    // Recursively calculate total size of the directory
    public static long getDirSize(Path dir) throws IOException {
        return Files.walk(dir)
                .filter(Files::isRegularFile)
                .mapToLong(p -> p.toFile().length())
                .sum();
    }
}