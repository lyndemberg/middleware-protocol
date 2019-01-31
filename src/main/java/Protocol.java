import java.io.IOException;
import java.net.InetAddress;

public interface Protocol {
    byte[] doOperation(RemoteAddress ref, int operationId, int value1, int value2) throws IOException;
    void sendReply(byte[] reply, InetAddress clientHost, int clientPort) throws IOException, ClassNotFoundException;
    byte[] getRequest() throws IOException;
}
