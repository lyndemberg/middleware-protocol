import java.io.*;
import java.net.*;

public class ProtocolInstance implements Protocol{
    private DatagramSocket socket;
    private final static int PORT_DEFAULT = 2555;

    public ProtocolInstance() throws SocketException {
        this.socket = new DatagramSocket(PORT_DEFAULT);
    }

    public ProtocolInstance(int port) throws SocketException {
        this.socket = new DatagramSocket(port);
    }

    public byte[] doOperation(RemoteAddress ref, int operationId, int value1, int value2) throws IOException {
        MessageRequest message = new MessageRequest(ref, operationId, value1,value2);
        byte[] messageBytes = MessageUtil.toByteArray(message);
        // sending packet to realize operation
        DatagramPacket request = new DatagramPacket(messageBytes,
                messageBytes.length,InetAddress.getByAddress(ref.host.getBytes()),ref.port);
        socket.send(request);
        //

        // handle reply from remote address that solve the operation
        byte[] bufferReply = new byte[1];
        DatagramPacket reply = new DatagramPacket(bufferReply, bufferReply.length);
        socket.receive(reply);
        return bufferReply;
    }

    public void sendReply(byte[] reply, InetAddress clientHost, int clientPort) throws IOException {
        DatagramPacket replyObject = new DatagramPacket(reply,
                reply.length,clientHost,clientPort);
        socket.send(replyObject);
    }

    public byte[] getRequest() throws IOException {
        byte[] buff = new byte[179];
        DatagramPacket request = new DatagramPacket(buff, buff.length);
        socket.receive(request);
        return request.getData();
    }
}
