package function;

public class Handler implements RequestHandler {
    @Override
    public String Handle(byte[] requestPayload) {
       return String.format("Hello, Vertx. You said: %s", requestPayload);
    }
}
