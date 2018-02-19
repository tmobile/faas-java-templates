package function;

public class Handler implements RequestHandler {
    @Override
    public String handle(byte[] requestPayload) {
        return String.format("Hello, SpringBoot, You said: %s",  new String(requestPayload));
    }
}
