import java.io.IOException;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Loader {

    final ExecutorService executor = Executors.newFixedThreadPool(1);

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Protocol instance = new ProtocolInstance();

        while(true){
            byte[] request = instance.getRequest();
            MessageRequest messageRequest = MessageUtil.toMessageRequest(request);
            executor.execute();
        }

    }
}
