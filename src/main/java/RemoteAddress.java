import java.io.Serializable;

public class RemoteAddress implements Serializable {
    public String host;
    public int port;

    public RemoteAddress(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public String toString() {
        return "RemoteAddress{" +
                "host='" + host + '\'' +
                ", port='" + port + '\'' +
                '}';
    }
}
