import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoaderServer {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        final Server server = new Server();
        while(true){
            MessageRequest request = MessageUtil.toMessageRequest(server.getRequest());
            System.out.println(request.toString());
            handle(request,server);
        }
    }

    private static void handle(MessageRequest req, Server server) throws IOException {
        MessageReply reply = new MessageReply();
        if(req.operationId == 0){
            reply.result = req.arguments[0] + req.arguments[1];
            reply.message = "sum-result:";
        }else{
            reply.result = req.arguments[0] - req.arguments[1];
            reply.message = "diff-result:";
        }

        server.sendReply(MessageUtil.replyToByteArray(reply), InetAddress.getByName(req.sourceReference.host),req.sourceReference.port);
    }
}
