package com.ims_team4.utils.webSocket;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.List;

public class InsertImageToMySQL {
    public static void main(String[] args) {
        for (int i = 1; i <= 5; i++) {
            String filePath = "src/main/resources/static/images/avatar/avatar" + i + ".jpg";
            System.out.println(i + ": " + hashFileAsNumber(filePath));
        }
    }

    private static int hashFileAsNumber(String filePath) {
        try (InputStream fis = Files.newInputStream(Paths.get(filePath))) {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] buffer = new byte[8192];
            int bytesRead;

            // Đọc file và cập nhật giá trị băm
            while ((bytesRead = fis.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesRead);
            }

            // Lấy kết quả băm và chuyển thành số nguyên
            byte[] hash = digest.digest();
            int hashValue = 0;
            for (byte b : hash) {
                hashValue += (b & 0xFF); // Chuyển đổi byte thành số dương rồi cộng dồn
            }

            return hashValue;
        } catch (Exception e) {
            return -1; // Trả về -1 nếu có lỗi
        }
    }

    public static final List<Integer> avatarValues = Arrays.asList(
            4339, 3668, 4308, 4483, 4583, 3981, 4538, 4654, 3867, 3431,
            3491, 4591, 4384, 4027, 4230, 3274, 3702, 3733, 4509, 4306,
            3892, 3878, 4230, 4570);
}