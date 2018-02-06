package vertx.function;

public class Handler {

    public String Handle(byte[] request) {
        return String.format("Hello, Vertx. You said: %s", new String(request));
    }
}