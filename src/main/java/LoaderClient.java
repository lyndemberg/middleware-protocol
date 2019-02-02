import java.io.IOException;
import java.net.InetAddress;

public class LoaderClient {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Client client = new Client();
        byte[] arguments = {2,3};
        byte[] replyArray = client.doOperation(new RemoteAddress("127.0.0.1", 2555), 0, arguments);
        MessageReply messageReply = MessageUtil.toMessageReplay(replyArray);
        System.out.println(messageReply.message+messageReply.result);
    }

}
