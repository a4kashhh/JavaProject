package edu.ccrm.io;

import java.nio.file.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import edu.ccrm.util.RecursiveUtil;

public class BackupService {
    private final Path baseBackupDirectory;

    public BackupService(Path baseBackupDirectory){
        this.baseBackupDirectory = baseBackupDirectory;
    }

    public Path backupDirectory(Path sourceDir) throws IOException {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        Path targetDirectory = baseBackupDirectory.resolve("backup_" + timestamp);
        Files.createDirectories(targetDirectory);
        
        // Copy all files and directories recursively
        Files.walk(sourceDir).forEach(sourcePath -> {
            try {
                Path relativePath = sourceDir.relativize(sourcePath);
                Path destinationPath = targetDirectory.resolve(relativePath);
                
                if(Files.isDirectory(sourcePath)) {
                    Files.createDirectories(destinationPath);
                } else {
                    Files.copy(sourcePath, destinationPath);
                }
            } catch(Exception e){ 
                throw new RuntimeException("Failed to copy: " + sourcePath, e); 
            }
        });
        
        return targetDirectory;
    }

    public long computeBackupSize(Path backupDir) throws IOException {
        return RecursiveUtil.computeSize(backupDir);
    }
}
