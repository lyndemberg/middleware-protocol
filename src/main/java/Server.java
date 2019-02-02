import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.*;

public class Server implements ProtocolServer {
    private DatagramSocket socket;
    private int SIZE_SLICE = 104;
    private int PORT = 2555;

    public Server() throws SocketException, UnknownHostException {
        this.socket = new DatagramSocket(PORT);
    }

    public Server(int port) throws SocketException, UnknownHostException {
        this.PORT = port;
        this.socket = new DatagramSocket(PORT);
    }

    public void sendReply(byte[] reply, InetAddress clientHost, int clientPort) throws IOException {
        RemoteAddress sourceReference = new RemoteAddress(socket.getLocalAddress().getHostAddress(),PORT);

        // preparing byte array final
        final int messageSlices = Math.incrementExact((int) Math.ceil(reply.length / (SIZE_SLICE-8)));
        final int TAMANHO_GERAL = reply.length+(messageSlices*8);
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
                int rest = reply.length - offset;
                System.arraycopy(reply, offset, slice,(sequence.length+length.length),rest);
            }else{
                System.arraycopy(reply, offset, slice,(sequence.length+length.length),SIZE_SLICE-8);
            }
            offset = offset + (SIZE_SLICE-8);

            DatagramPacket packet = new DatagramPacket(slice, 0, SIZE_SLICE, clientHost, clientPort);

            socket.send(packet);
        }
    }

    public byte[] getRequest() throws IOException {
        HashMap<Integer, byte[]> slices = new HashMap<Integer, byte[]>();
        int forReceive = 0;
        int receivers = 0;
        do{
            byte[] slice = new byte[SIZE_SLICE];
            DatagramPacket packet = new DatagramPacket(slice, slice.length);
            socket.receive(packet);
            int sequence = ByteBuffer.wrap(packet.getData(), 0, 4).getInt();
            int length = ByteBuffer.wrap(packet.getData(), 4,4).getInt();
            forReceive = Math.incrementExact((int) Math.ceil(length / SIZE_SLICE));
            slices.put(sequence,slice);
            receivers++;
        }while(receivers != forReceive);
        return MessageUtil.getSlicesOrdered(slices);
    }
}
