import java.io.IOException;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoaderServer {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        final Server server = new Server();
        System.out.println("Server started");

        while(true){
            MessageRequest request = MessageUtil.toRequest(server.getRequest());
            System.out.println(request.toString());
            handle(request,server);
        }
    }

    private static void handle(MessageRequest req, Server server) throws IOException {
        MessageReply reply = new MessageReply();
        int numberOne = ByteBuffer.wrap(req.arguments, 0, 4).getInt();
        int numberTwo = ByteBuffer.wrap(req.arguments, 4, 4).getInt();
        if(req.operationId == 0){
            reply.result = numberOne + numberTwo;
            reply.message = "sum-result:";
        }else{
            reply.result = numberOne - numberTwo;
            reply.message = "diff-result:";
        }

        server.sendReply(MessageUtil.toByteArray(reply), InetAddress.getByName(req.sourceReference.host),req.sourceReference.port);
    }
}
