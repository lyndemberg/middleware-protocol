import java.io.Serializable;

public class MessageReply implements Serializable {
    public String message;
    public int result;

    public MessageReply(String message, int result) {
        this.message = message;
        this.result = result;
    }

    public MessageReply() {
    }

    @Override
    public String toString() {
        return "MessageReply{" +
                "message='" + message + '\'' +
                ", result=" + result +
                '}';
    }
}

