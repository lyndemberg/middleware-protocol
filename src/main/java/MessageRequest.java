import java.io.Serializable;

public class MessageRequest implements Serializable {
    public RemoteAddress ref;
    public int operationId;
    public int value1;
    public int value2;

    public MessageRequest(RemoteAddress ref, int operationId, int value1, int value2) {
        this.ref = ref;
        this.operationId = operationId;
        this.value1 = value1;
        this.value2 = value2;
    }

    @Override
    public String toString() {
        return "MessageRequest{" +
                "ref=" + ref +
                ", operationId=" + operationId +
                ", value1=" + value1 +
                ", value2=" + value2 +
                '}';
    }
}
