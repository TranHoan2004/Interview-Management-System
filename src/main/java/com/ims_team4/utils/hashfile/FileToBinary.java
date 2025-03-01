package com.ims_team4.utils.hashfile;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileToBinary {
    public static void main(String[] args) {
        String filePath = "D:\\OJT_PĐL\\PhamDucLong_CV.docx";

        try {
            byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));
            System.out.println("Binary Data: " + bytesToHex(fileBytes));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
