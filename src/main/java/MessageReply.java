import java.io.Serializable;

public class MessageReply implements Serializable {
    private String message;
    private int result;

    public MessageReply(String message, int result) {
        this.message = message;
        this.result = result;
    }

    @Override
    public String toString() {
        return "MessageReply{" +
                "message='" + message + '\'' +
                ", result=" + result +
                '}';
    }
}

