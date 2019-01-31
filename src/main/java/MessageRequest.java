import java.io.Serializable;
import java.util.Arrays;

public class MessageRequest implements Serializable {
    public RemoteRef ref;
    int operationId;
    byte[] arguments;

    public MessageRequest(RemoteRef ref, int operationId, byte[] arguments) {
        this.ref = ref;
        this.operationId = operationId;
        this.arguments = arguments;
    }

    @Override
    public String toString() {
        return "MessageRequest{" +
                "ref=" + ref +
                ", operationId=" + operationId +
                ", arguments=" + Arrays.toString(arguments) +
                '}';
    }
}
