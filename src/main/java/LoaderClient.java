import java.io.IOException;
import java.nio.ByteBuffer;

public class LoaderClient {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        final Client client = new Client();
        System.out.println("Client started");
        //
        byte[] arguments = new byte[8];
        byte[] numberOne = ByteBuffer.allocate(4).putInt(50).array();
        byte[] numberTwo = ByteBuffer.allocate(4).putInt(55).array();
        //
        System.arraycopy(numberOne,0,arguments,0,numberOne.length);
        System.arraycopy(numberTwo,0,arguments,numberOne.length,numberTwo.length);
        //
        byte[] replyArray = client.doOperation(new RemoteAddress("127.0.0.1", 2555), 0, arguments);
        MessageReply reply = MessageUtil.toReply(replyArray);
        //
        System.out.println(reply.message + " " + reply.result);

    }

}
