import java.io.IOException;
import java.net.Socket;

public class Client extends BaseSocketApp {

    public Client(String instanceName) {
        super(instanceName);
    }

    public static void main(String[] args) {
        new Client("Client").run("localhost", 2021);
    }

    public void run(String serverAddress, int serverPort) {
        try {
            init(new Socket(serverAddress, serverPort));

            while (true) {
                Message inputMessage = getInputMessage();
                if (inputMessage != null) {
                    printMessage(inputMessage);
                    if (itIsQuitMessage(inputMessage)) break;
                }

                Message outputMessage = sendOutputMessage();
                if (outputMessage != null) {
                    if (itIsQuitMessage(outputMessage)) break;
                }
                Thread.sleep(100);
            }

            System.out.println("Сеанс завершен");

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            tryCloseAllSources();
        }
    }
}
