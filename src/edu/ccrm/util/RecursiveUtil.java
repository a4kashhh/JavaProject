package edu.ccrm.util;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class RecursiveUtil {
    /**
     * Calculates the total size of a directory and all its contents recursively.
     * Uses Java NIO's file tree walking capabilities for efficient traversal.
     * 
     * @param rootDirectory the directory to calculate size for
     * @return total size in bytes
     * @throws IOException if there are issues accessing files
     */
    public static long computeSize(Path rootDirectory) throws IOException {
        final long[] totalSizeBytes = {0};
        
        Files.walkFileTree(rootDirectory, new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) throws IOException {
                totalSizeBytes[0] += attributes.size();
                return FileVisitResult.CONTINUE;
            }
        });
        
        return totalSizeBytes[0];
    }
}
