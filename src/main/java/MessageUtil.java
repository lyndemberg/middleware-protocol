import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class MessageUtil {

    public static byte[] toByteArray(Object request) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = new ObjectOutputStream(bos);
        out.writeObject(request);
        out.flush();
        return bos.toByteArray();
    }

    public static MessageRequest toRequest(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        ObjectInputStream out = new ObjectInputStream(in);
        MessageRequest request = (MessageRequest) out.readObject();
        return request;
    }

    public static MessageReply toReply(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        ObjectInputStream out = new ObjectInputStream(in);
        MessageReply reply = (MessageReply) out.readObject();
        return reply;
    }

    public static byte[] getSlicesOrdered(HashMap<Integer,byte[]> slices) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Map<Integer, byte[]> mapOrdered = new TreeMap<Integer, byte[]>(slices);
        Iterator<Map.Entry<Integer, byte[]>> iterator = mapOrdered.entrySet().iterator();
        while(iterator.hasNext()){
            byte[] value = iterator.next().getValue();
            outputStream.write(value,8,value.length-8);
        }
        byte[] retorno = outputStream.toByteArray();
        return retorno;
    }

}
