import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Loader {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Protocol protocolInstance = new ProtocolInstance(new RemoteRef("localhost",255));

        while(true){
            byte[] requestBytes = protocolInstance.getRequest();
            if(requestBytes!=null){
                ByteArrayInputStream in = new ByteArrayInputStream(requestBytes);
                ObjectInputStream objectInputStream = new ObjectInputStream(in);
                MessageRequest request = (MessageRequest) objectInputStream.readObject();
                protocolInstance.sendReply();


            }
        }
    }
}







//switch (request.operationId){
//        case 0:
//        result = request.arguments[0] + request.arguments[1];
//        break;
//        case 1:
//        result = request.arguments[0] + request.arguments[1];
//        break;
//        }