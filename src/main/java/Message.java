import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@SuppressWarnings("CanBeFinal")
public class Message {

    private String instanceName;
    private EMessageType messageType;
    private String text;
    private Date date;

    public Message(String instanceName, EMessageType messageType, String message) {
        this.instanceName = instanceName;
        this.messageType = messageType;
        this.text = message;
        this.date = new Date();
    }

    public String getInstanceName() {
        return instanceName;
    }

    public EMessageType getMessageType() {
        return messageType;
    }

    public String getText() {
        return text;
    }

    public String getFormattedStringDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
        return sdf.format(date);
    }
}

