@FunctionalInterface
public interface Handler {
    Response getResponse(Request request);
}
