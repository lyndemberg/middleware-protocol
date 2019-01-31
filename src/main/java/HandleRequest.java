public class HandleRequest implements Runnable {
    private MessageRequest request;
    private Protocol instanceProtocol;

    public HandleRequest(MessageRequest request, Protocol instanceProtocol) {
        this.request = request;
        this.instanceProtocol = instanceProtocol;
    }

    public void run() {
        new MessageReply(request);
        request.
        if(request.operationId == 0){

        }else if(request.operationId == 1){

        }
    }
}
