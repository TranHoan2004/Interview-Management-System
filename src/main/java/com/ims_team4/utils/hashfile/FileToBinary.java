package com.ims_team4.utils.hashfile;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.file.*;

public class FileToBinary {
    // Hàm chuyển bytes sang dạng hex để dễ đọc
    @NotNull
    private static String bytesToHex(@NotNull byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X ", b));
        }
        return sb.toString();
    }
}
