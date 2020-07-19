import java.io.*;
import java.net.Socket;

@SuppressWarnings("CanBeFinal")
public class BaseSocketApp {

    protected final String QUIT_MESSAGE = "quit";

    protected Socket socket = null;
    protected BufferedReader consoleReader;
    protected BufferedReader inputReader = null;
    protected BufferedWriter outputWriter = null;

    private String instanceName;

    public BaseSocketApp(String instanceName) {
        this.instanceName = instanceName;
        consoleReader = new BufferedReader(new InputStreamReader(System.in));
    }

    protected void init(Socket socket) throws IOException {
        this.socket = socket;
        inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outputWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    protected Message getInputMessage() throws IOException {
        if (inputReader.ready()) {
            return new Message(instanceName, EMessageType.input, inputReader.readLine());
        } else {
            return null;
        }
    }

    protected Message sendOutputMessage() throws IOException {
        if (consoleReader.ready()) {
            String message = consoleReader.readLine();

            outputWriter.write(message);
            outputWriter.newLine();
            outputWriter.flush();

            return new Message(instanceName, EMessageType.output, message);
        } else {
            return null;
        }
    }

    protected void printMessage(Message message) {
        if (message == null) return;
        System.out.println(message.getText() + " | " +
                message.getInstanceName() + " | " +
                message.getMessageType() + " | " +
                message.getFormattedStringDate());
    }

    boolean itIsQuitMessage(Message message) {
        if (message == null) return false;
        return message.getText().equalsIgnoreCase(QUIT_MESSAGE);
    }

    protected void tryCloseAllSources() {
        tryCloseInputReader();
        tryCloseOutputWriter();
        tryCloseSocket();
    }

    private void tryCloseSocket() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void tryCloseInputReader() {
        try {
            outputWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void tryCloseOutputWriter() {
        try {
            inputReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
