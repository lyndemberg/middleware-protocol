import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.*;

public class Server implements ProtocolServer {
    private DatagramSocket socket;
    private int SIZE_SLICE = 104;
    private int PORT = 2555;

    public Server() throws SocketException {
        this.socket = new DatagramSocket(PORT);
    }

    public Server(int port) throws SocketException {
        this.PORT = port;
        this.socket = new DatagramSocket(port);
    }

    public void sendReply(byte[] reply, InetAddress clientHost, int clientPort) throws IOException {
        byte[] header = ByteBuffer.allocate(4).putInt(reply.length).array();
        byte[] arrayJoin = new byte[header.length + reply.length];
        System.arraycopy(header,0,arrayJoin,0,header.length);
        System.arraycopy(reply,0,arrayJoin,header.length,reply.length);

        int countSlices = Math.round(arrayJoin.length / SIZE_SLICE);
        int countSends = 0;
        while(countSends != countSlices){
            DatagramPacket replyPacket = new DatagramPacket(arrayJoin, countSends * SIZE_SLICE,
                    SIZE_SLICE, clientHost, clientPort);
            socket.send(replyPacket);
            countSends++;
        }
    }

    public byte[] getRequest() throws IOException {
        HashMap<Integer, byte[]> slices = new HashMap<Integer, byte[]>();
        int forReceive = 0;
        int receivers = 0;
        do{
            byte[] slice = new byte[SIZE_SLICE];
            DatagramPacket packet = new DatagramPacket(slice, slice.length);
            System.out.println("antes do receive");
            socket.receive(packet);
            System.out.println("recebeu");
            int sequence = ByteBuffer.wrap(packet.getData(), 0, 4).getInt();
            System.out.println("sequence->"+sequence);
            int length = ByteBuffer.wrap(packet.getData(), 4,4).getInt();
            System.out.println("length->"+length);
            forReceive = Math.incrementExact((int) Math.ceil(length / SIZE_SLICE));
//            forReceive = (int) Math.ceil(length / (SIZE_SLICE));
            System.out.println("forReceive->"+forReceive);
            slices.put(sequence,slice);
            System.out.println("inseriu");
            receivers++;
        }while(receivers != forReceive);
        System.out.println("saiu do while");
        return getRequestOrdered(slices);
    }

    private byte[] getRequestOrdered(HashMap<Integer,byte[]> slices) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Map<Integer, byte[]> mapOrdered = new TreeMap<Integer, byte[]>(slices);
        Iterator<Map.Entry<Integer, byte[]>> iterator = mapOrdered.entrySet().iterator();
        int pos = 0;
        while(iterator.hasNext()){
            System.out.println("pos:"+pos);
            byte[] value = iterator.next().getValue();
            outputStream.write(value,8,value.length-8);
        }
        byte[] retorno = outputStream.toByteArray();
        System.out.println("saindo");
        System.out.println("length final:"+retorno.length);
        return retorno;
    }
}
