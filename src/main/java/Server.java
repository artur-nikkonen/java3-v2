import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;

public class Server extends BaseSocketApp {

    public Server(String instanceName) {
        super(instanceName);
    }

    public static void main(String[] args) throws SQLException {
        new Server("Server").run(2021);
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public void run(int serverPort) throws SQLException {
        while (true) {
            try (ServerSocket serverSocket = new ServerSocket(serverPort)) {
                System.out.println("Ожидаем подключения...");
                init(serverSocket.accept());
                System.out.println("Клиент подключился");

                Database db = new Database("chat.db");

                while (true) {
                    Message inputMessage = getInputMessage();
                    if (inputMessage != null) {
                        printMessage(inputMessage);
                        db.saveMessage(inputMessage);
                        if (itIsQuitMessage(inputMessage)) break;
                    }

                    Message outputMessage = sendOutputMessage();
                    if (outputMessage != null) {
                        db.saveMessage(outputMessage);
                        if (itIsQuitMessage(outputMessage)) break;
                    }
                    Thread.sleep(100);
                }

                System.out.println("Сеанс завершен");
                System.out.println("Последние 10 записей в базе:");
                db.printLast10Records();

                db.tryCloseAllSources();

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            } finally {
                tryCloseAllSources();
            }
        }
    }
}
