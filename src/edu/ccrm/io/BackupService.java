package edu.ccrm.io;

import java.nio.file.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import edu.ccrm.util.RecursiveUtil;

public class BackupService {
    private final Path base;

    public BackupService(Path base){
        this.base = base;
    }

    public Path backupDirectory(Path sourceDir) throws IOException {
        String ts = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        Path target = base.resolve("backup_" + ts);
        Files.createDirectories(target);
        
        Files.walk(sourceDir).forEach(p -> {
            try {
                Path rel = sourceDir.relativize(p);
                Path dest = target.resolve(rel);
                if(Files.isDirectory(p)) {
                    Files.createDirectories(dest);
                } else {
                    Files.copy(p, dest);
                }
            } catch(Exception e){ 
                throw new RuntimeException(e); 
            }
        });
        
        return target;
    }

    public long computeBackupSize(Path backupDir) throws IOException {
        return RecursiveUtil.computeSize(backupDir);
    }
}
