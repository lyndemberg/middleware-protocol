import java.io.Serializable;
import java.util.Arrays;

public class MessageRequest implements Serializable {
    public RemoteAddress sourceReference;
    public int operationId;
    public byte[] arguments;

    public MessageRequest(){}

    public MessageRequest(RemoteAddress sourceReference, int operationId, byte[] arguments) {
        this.sourceReference = sourceReference;
        this.operationId = operationId;
        this.arguments = arguments;
    }

    public RemoteAddress getSourceReference() {
        return sourceReference;
    }

    public void setSourceReference(RemoteAddress sourceReference) {
        this.sourceReference = sourceReference;
    }

    public int getOperationId() {
        return operationId;
    }

    public void setOperationId(int operationId) {
        this.operationId = operationId;
    }

    public byte[] getArguments() {
        return arguments;
    }

    public void setArguments(byte[] arguments) {
        this.arguments = arguments;
    }

    @Override
    public String toString() {
        return "MessageRequest{" +
                "sourceReference=" + sourceReference +
                ", operationId=" + operationId +
                ", arguments=" + Arrays.toString(arguments) +
                '}';
    }
}
