package edu.ccrm.util;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class RecursiveUtil {
    public static long computeSize(Path root) throws IOException {
        final long[] total = {0};
        
        Files.walkFileTree(root, new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                total[0] += attrs.size();
                return FileVisitResult.CONTINUE;
            }
        });
        
        return total[0];
    }
}
