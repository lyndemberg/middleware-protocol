import java.io.*;
import java.net.*;

public class ProtocolInstance implements Protocol{
    private DatagramSocket socket;

    public ProtocolInstance(RemoteRef ref) throws IOException {
    }

    public byte[] doOperation(RemoteRef ref, int operationId, byte[] arguments) throws IOException {
        MessageRequest message = new MessageRequest(ref, operationId, arguments);
        //SERIALIZE MESSAGE
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = new ObjectOutputStream(bos);
        out.writeObject(message);
        out.flush();
        byte[] requestBytes = bos.toByteArray();
        //
        DatagramPacket request = new DatagramPacket(requestBytes,
                requestBytes.length,InetAddress.getByAddress(ref.host.getBytes()),ref.port);
        socket.send(request);

        // HANDLE REPLY
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
        byte[] bufferReply = new byte[1];
        DatagramPacket reply = new DatagramPacket(bufferReply, bufferReply.length);
        socket.receive(reply);
    }
}
