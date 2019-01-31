import java.io.Serializable;

public class RemoteRef implements Serializable {
    public String host;
    public int port;

    public RemoteRef(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public String toString() {
        return "RemoteRef{" +
                "host='" + host + '\'' +
                ", port='" + port + '\'' +
                '}';
    }
}
