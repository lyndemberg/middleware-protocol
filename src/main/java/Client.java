import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.HashMap;

public class Client implements ProtocolClient {
    private int SIZE_SLICE = 104;
    private DatagramSocket socket;
    private int PORT = 2556;

    public Client() throws SocketException, UnknownHostException {
        this.socket = new DatagramSocket(PORT);
    }

    public Client(int port) throws SocketException, UnknownHostException {
        this.PORT = port;
        this.socket = new DatagramSocket(PORT);
    }

    public byte[] doOperation(RemoteAddress ref, int operationId, byte[] arguments) throws IOException {
        RemoteAddress source = new RemoteAddress(socket.getLocalAddress().getHostAddress(),PORT);
        MessageRequest message = new MessageRequest(source, operationId, arguments);

        // preparing byte array final
        byte[] messageBytes = MessageUtil.toByteArray(message);
        final int messageSlices = Math.incrementExact((int) Math.ceil(messageBytes.length / (SIZE_SLICE-8)));
        final int TAMANHO_GERAL = messageBytes.length+(messageSlices*8);
        final int QUANTIDADE_FATIAS_GERAL = Math.incrementExact((int) Math.ceil(TAMANHO_GERAL / SIZE_SLICE));
        byte[] slice = null;
        int offset = 0;
        int pos = 0;
        byte[] length = ByteBuffer.allocate(4).putInt(TAMANHO_GERAL).array();
        for(int i=0;i<QUANTIDADE_FATIAS_GERAL;i++){
            slice = new byte[SIZE_SLICE];
            byte[] sequence = ByteBuffer.allocate(4).putInt(i).array();
            System.arraycopy(sequence,0,slice,0,4);
            System.arraycopy(length,0,slice,4,4);

            if((i+1) == QUANTIDADE_FATIAS_GERAL){
                int rest = messageBytes.length - offset;
                System.arraycopy(messageBytes, offset, slice,(sequence.length+length.length),rest);
            }else{
                System.arraycopy(messageBytes, offset, slice,(sequence.length+length.length),SIZE_SLICE-8);
            }
            offset = offset + (SIZE_SLICE-8);

            DatagramPacket packet = new DatagramPacket(slice, 0, SIZE_SLICE, InetAddress.getByName(ref.host), ref.port);

            socket.send(packet);
        }

        HashMap<Integer, byte[]> slices = new HashMap<Integer, byte[]>();
        int forReceive = 0;
        int receivers = 0;
        do{
            byte[] sliceReply = new byte[SIZE_SLICE];
            DatagramPacket packet = new DatagramPacket(sliceReply, sliceReply.length);
            socket.receive(packet);
            int sequence = ByteBuffer.wrap(packet.getData(), 0, 4).getInt();
            int lengthReply = ByteBuffer.wrap(packet.getData(), 4,4).getInt();
            forReceive = Math.incrementExact((int) Math.ceil(lengthReply / SIZE_SLICE));
            slices.put(sequence,sliceReply);
            receivers++;
        }while(receivers != forReceive);

        return MessageUtil.getSlicesOrdered(slices);
    }

}