import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class Client implements ProtocolClient {
    private int SIZE_SLICE = 104;
    private DatagramSocket socket;
    private int PORT = 2556;

    public Client() throws SocketException {
        this.socket = new DatagramSocket(PORT);
    }

    public Client(int port) throws SocketException {
        this.PORT = port;
        this.socket = new DatagramSocket(port);
    }

    public byte[] doOperation(RemoteAddress ref, int operationId, byte[] arguments) throws IOException {
        RemoteAddress sourceReference = new RemoteAddress(ref.host, ref.port);
        MessageRequest message = new MessageRequest(sourceReference, operationId, arguments);

        // preparing byte array final
        byte[] messageBytes = MessageUtil.requestToByteArray(message);
        final int messageSlices = Math.incrementExact((int) Math.ceil(messageBytes.length / (SIZE_SLICE-8)));
        final int TAMANHO_GERAL = messageBytes.length+(messageSlices*8);
        final int QUANTIDADE_FATIAS_GERAL = Math.incrementExact((int) Math.ceil(TAMANHO_GERAL / SIZE_SLICE));
        System.out.println("Message bytes=>"+messageBytes.length);
        System.out.println("messageSlices=>"+messageSlices);
        System.out.println("TAMANHO_GERAL=>"+TAMANHO_GERAL);
        System.out.println("QUANTIDADE_FATIAS_GERAL=>"+QUANTIDADE_FATIAS_GERAL);
        byte[] slice = null;
        int offset = 0;
        int pos = 0;
        byte[] length = ByteBuffer.allocate(4).putInt(TAMANHO_GERAL).array();
        for(int i=0;i<QUANTIDADE_FATIAS_GERAL;i++){
            System.out.println("iteracao:"+i);
            slice = new byte[SIZE_SLICE];
            byte[] sequence = ByteBuffer.allocate(4).putInt(i).array();
            System.arraycopy(sequence,0,slice,0,4);
            System.arraycopy(length,0,slice,4,4);

            if((i+1) == QUANTIDADE_FATIAS_GERAL){
                int restante = messageBytes.length - offset;
                System.arraycopy(messageBytes, offset, slice,(sequence.length+length.length),restante);
            }else{
                System.arraycopy(messageBytes, offset, slice,(sequence.length+length.length),SIZE_SLICE-8);
            }
            offset = offset + (SIZE_SLICE-8);

            DatagramPacket packet = new DatagramPacket(slice, 0, SIZE_SLICE, InetAddress.getByName(ref.host), ref.port);

            socket.send(packet);
        }
//        int pos = 0;
//        byte[] slice = null;
//        for(int i=0; i<countSlices; i++){
//            slice = new byte[SIZE_SLICE];
//            byte[] sequence = ByteBuffer.allocate(4).putInt(i).array();
//            System.arraycopy(sequence,0,slice,0,4);
//            System.arraycopy(length,0,slice,4,4);
//            System.arraycopy(messageBytes, pos, slice,(sequence.length+length.length),SIZE_SLICE-8);
//            DatagramPacket packet = new DatagramPacket(slice, 0, SIZE_SLICE, InetAddress.getByName(ref.host), ref.port);
//            pos = pos + SIZE_SLICE-8;
//            System.out.println("pos actual:"+pos);
//            System.out.println("send packet with ->"+packet.getData().length);
//            socket.send(packet);
//        }

        // handle reply from remote address that solve the operation
//        byte[] bufferGeneral = null;
//        byte[] bufferSlice = new byte[SIZE_SLICE];
//        int countReceiver = 0;
//        int countSlicesForReceive = 0;
//        Map<Integer, byte[]> mapOrder = new HashMap<Integer, byte[]>();
//        do{
//            DatagramPacket slice = new DatagramPacket(bufferSlice, bufferSlice.length);
//            socket.receive(slice);
//            if(countReceiver == 0){
//                int headerValue = ByteBuffer.wrap(slice.getData(), 0, 4).getInt();
//                //recover length message
//                bufferGeneral = new byte[headerValue];
//                countSlicesForReceive = Math.round(headerValue/SIZE_SLICE);
//            }
//            System.arraycopy(bufferSlice,0,bufferGeneral,countReceiver * SIZE_SLICE, bufferSlice.length);
//            countReceiver++;
//        }while (countReceiver != countSlicesForReceive);

        //send result of bufferGeneral
        return null;
    }

}