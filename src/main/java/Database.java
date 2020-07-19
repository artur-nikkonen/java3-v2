import java.sql.*;
import java.util.UUID;

public class Database {

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    PreparedStatement insertMessage;
    private Connection connection;
    private String sessionId;

    public Database(String filename) throws SQLException {

        connection = DriverManager.getConnection("jdbc:sqlite:" + filename);

        Statement statement = connection.createStatement();
        String sql = "CREATE TABLE IF NOT EXISTS chat (\n" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "sessionId TEXT NOT NULL,\n" +
                "instanceName TEXT NOT NULL,\n" +
                "messageType TEXT NOT NULL,\n" +
                "message TEXT NOT NULL,\n" +
                "date TEXT NOT NULL" +
                ");";
        statement.executeUpdate(sql);

        insertMessage = connection.prepareStatement(
                "INSERT INTO chat (sessionId, instanceName, messageType, message, date) VALUES (?,?,?,?,?)");

        this.sessionId = UUID.randomUUID().toString();
    }

    public void saveMessage(Message message) {
        try {
            insertMessage.setString(1, sessionId);
            insertMessage.setString(2, message.getInstanceName());
            insertMessage.setString(3, message.getMessageType().toString());
            insertMessage.setString(4, message.getText());
            insertMessage.setString(5, message.getFormattedStringDate());
            insertMessage.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printLast10Records() {
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM (SELECT * FROM chat ORDER BY id DESC LIMIT 10) ORDER BY id;";
            ResultSet resultSet = statement.executeQuery(sql);

            System.out.println("Id | Session id | Instance name | Message type | Message | Date |");
            System.out.println("----------------------------------------------------");

            while (resultSet.next()) {
                String line = resultSet.getInt("id") + " | " +
                        resultSet.getString("sessionId") + " | " +
                        resultSet.getString("instanceName") + " | " +
                        resultSet.getString("messageType") + " | " +
                        resultSet.getString("message") + " | " +
                        resultSet.getString("date") + " | ";
                System.out.println(line);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void tryCloseAllSources(){
        tryCloseInsertMessage();
        tryCloseConnection();
    }

    private void tryCloseInsertMessage() {
        try {
            insertMessage.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void tryCloseConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
