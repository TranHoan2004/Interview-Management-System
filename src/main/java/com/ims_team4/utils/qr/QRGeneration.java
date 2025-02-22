package com.ims_team4.utils.qr;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.ims_team4.config.Constants;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QRGeneration implements Constants.QRProperties {
    @NotNull
    @Bean
    public String generateQRCodeImage(@NotNull String data, int width, int height) throws IOException, WriterException {
        StringBuilder result = new StringBuilder();
        if (!data.isEmpty()) {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            try {
                QRCodeWriter writer = new QRCodeWriter();
                // encrypting information into the matrix
                BitMatrix bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, width, height);
                BufferedImage image = toBufferedImage(bitMatrix);
                ImageIO.write(image, "png", os);
                result.append(new String(Base64.getEncoder().encode(os.toByteArray())));
            } catch (Exception e) {
                Logger.getLogger(QRGeneration.class.getName()).log(Level.ALL, e.getMessage(), e);
                throw e;
            }
        }
        return result.toString();
    }

    @NotNull
    private static BufferedImage toBufferedImage(@NotNull BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        image.createGraphics();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // black for bit 1, white for bit 0
                image.setRGB(x, y, matrix.get(x, y) ? 0x000000 : 0xFFFFFF);
            }
        }
        return image;
    }

    // test
//    public static void main(String[] args) {
//        String input = "Lap trinh vien";
//        try {
//            System.out.println(generateQRCodeImage(input, 300, 300));
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
}
