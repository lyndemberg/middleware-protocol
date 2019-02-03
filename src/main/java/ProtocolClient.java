import java.io.IOException;

public interface ProtocolClient {
    byte[] doOperation(RemoteAddress ref, int operationId, byte[] arguments) throws IOException;
}
