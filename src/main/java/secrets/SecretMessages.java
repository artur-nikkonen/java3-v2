package secrets;

import com.google.common.primitives.Bytes;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class SecretMessages {

    static final char ETX = (char) 3; //End-of-Text character

    public static boolean writeSecretMessage(String imageName, String imageNameWithMessage, String message, String key) {
        try {

            //Load image
            BufferedImage image = ImageIO.read(new File(imageName));

            //Encrypt message
            String encryptedMessage = encryptMessage(message, key);

            //Try write bytes to image
            boolean recorded = tryWriteBytesToImage(encryptedMessage.getBytes(), image);
            if (!recorded) return false;

            //Save result image
            File output = new File(imageNameWithMessage);
            ImageIO.write(image, "png", output);
            return true;

        } catch (IOException e) {
            return false;
        }
    }

    private static String encryptMessage(String message, String key) {
        try {
            Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);

            byte[] encrypted = cipher.doFinal(message.getBytes());

            String encodedString = Base64.getEncoder().encodeToString(encrypted);
            encodedString += ETX; //add End of Text char

            return encodedString;

        } catch (Exception e) {
            return "" + ETX;
        }
    }

    private static boolean tryWriteBytesToImage(byte[] bytes, BufferedImage image) {
        int h = image.getHeight();
        int w = image.getWidth();
        int maxBytes = h * w / 8;

        if (maxBytes < bytes.length) return false; //image too small

        //set all bytes by bits to smallest bits of blue color
        for (int i = 0; i < bytes.length * 8; i++) {
            //pixel coordinates
            int x = i % w;
            int y = i / w;
            int pixel = image.getRGB(x, y);

            //Bit coordinates
            int bitNum = i % 8;
            int byteNum = i / 8;

            if (((bytes[byteNum] >> bitNum) & 1) == 1) {
                pixel |= 1; // set 1
            } else {
                pixel &= ~1; // set 0
            }
            image.setRGB(x, y, pixel);
        }

        return true;
    }

    public static String readSecretMessage(String imageNameWithMessage, String key) {
        try {
            //Load image
            BufferedImage image = ImageIO.read(new File(imageNameWithMessage));

            //Get encrypted message
            String encryptedMessage = readStringFromImage(image);

            //Decrypt message
            return decryptMessage(encryptedMessage, key);

        } catch (IOException e) {
            return "";
        }
    }

    private static String decryptMessage(String encryptedMessage, String key) {
        try {
            Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, aesKey);

            byte[] decodedBytes = Base64.getDecoder().decode(encryptedMessage);
            return new String(cipher.doFinal(decodedBytes));

        } catch (Exception e) {
            return "<no message>";
        }
    }

    private static String readStringFromImage(BufferedImage image) {
        int h = image.getHeight();
        int w = image.getWidth();
        int totalBytes = h * w / 8;
        int totalBits = totalBytes * 8;

        List<Byte> bytesList = new ArrayList<>();
        bytesList.add((byte) 0); //Init first byte

        for (int i = 0; i < totalBits; i++) {
            //pixel coordinates
            int x = i % w;
            int y = i / w;
            int pixel = image.getRGB(x, y);

            //Bit coordinates
            int bitNum = i % 8;
            int byteNum = i / 8;

            if (byteNum == bytesList.size()) {
                //If last byte is ETX that message is end
                if (bytesList.get(byteNum - 1) == ETX) {
                    bytesList.remove(bytesList.size() - 1); //delete ETX byte;
                    break;
                }
                bytesList.add((byte) 0); //Init next byte
            }

            if (pixel % 2 != 0) {
                byte b = bytesList.get(byteNum);
                b |= 1 << bitNum; // set 1
                bytesList.set(byteNum, b);
            }
        }

        return new String(Bytes.toArray(bytesList));
    }
}
