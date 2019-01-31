import java.io.IOException;
import java.net.InetAddress;

public interface Protocol {
    byte[] doOperation(RemoteRef ref, int operationId, byte[] arguments) throws IOException;
    void sendReply(byte[] reply, InetAddress clientHost, int clientPort) throws IOException, ClassNotFoundException;
    byte[] getRequest() throws IOException;
}
