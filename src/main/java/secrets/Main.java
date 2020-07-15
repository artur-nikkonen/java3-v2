package secrets;

import static secrets.SecretMessages.readSecretMessage;
import static secrets.SecretMessages.writeSecretMessage;

public class Main {
    public static void main(String[] args) {

        String imageName = "files/ice-cream.png";
        String imageNameWithMessage = "files/ice-cream-secret.png";
        String key = "my-super-key-128"; // 128 bit key. Length of key is important for encrypt algorithm!!!!

        //Create long message
        StringBuilder message = new StringBuilder("Super secret message 123!!!\nSecret code:");
        for (int i = 0; i <= 1000; i++) {
            message.append(" ").append(i);
        }

        //Save message to image
        boolean messageSaved = writeSecretMessage(imageName, imageNameWithMessage, message.toString(), key);
        System.out.println("Message saved: " + messageSaved);

        if (messageSaved) {
            //Read message from image
            String str = readSecretMessage(imageNameWithMessage, key);
            System.out.println("Message:\n" + str);
        }
    }
}
