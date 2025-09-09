package edu.ccrm.util;

import java.io.File;

public class RecursionUtils {
    public static long getDirSize(File dir) {
        if (dir.isFile()) return dir.length();
        long size = 0;
        File[] files = dir.listFiles();
        if (files != null) {
            for (File f : files) {
                size += getDirSize(f);
            }
        }
        return size;
    }
}