import java.io.IOException;
import java.net.InetAddress;

public interface ProtocolServer {
    void sendReply(byte[] reply, InetAddress clientHost, int clientPort) throws IOException, ClassNotFoundException;
    byte[] getRequest() throws IOException;
}
