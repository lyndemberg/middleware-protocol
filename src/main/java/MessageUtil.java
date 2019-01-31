import java.io.*;

public class MessageUtil {

    public static byte[] toByteArray(Object object) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = new ObjectOutputStream(bos);
        out.writeObject(object);
        out.flush();
        return bos.toByteArray();
    }

    public static MessageRequest toMessageRequest(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        ObjectInputStream out = new ObjectInputStream(in);
        MessageRequest request = (MessageRequest) out.readObject();
        return request;
    }

    public static MessageReply toMessageReplay(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        ObjectInputStream out = new ObjectInputStream(in);
        MessageReply reply = (MessageReply) out.readObject();
        return reply;
    }

}
