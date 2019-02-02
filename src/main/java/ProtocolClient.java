import java.io.IOException;
import java.net.InetAddress;

public interface ProtocolClient {
    byte[] doOperation(RemoteAddress ref, int operationId, byte[] arguments) throws IOException;
}
