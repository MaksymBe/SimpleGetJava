import java.util.HashMap;
import java.util.Map;

public class Router {
    private Map<MethodWithRoute, Handler> handlers;
    private Handler errorHandler = this::print404Response;

    public Router(Map<MethodWithRoute, Handler> handlers) {
        this.handlers = handlers;
    }

    public Router(Handler errorHandler) {
        this.errorHandler = errorHandler;
    }

    public Router() {
        handlers = new HashMap<>();
    }

    public void addHandler(String method, String path, Handler handler) {
        handlers.put(new MethodWithRoute(method, path), handler);
    }

    public Handler getRequestHandler(String method, String path) {
        Handler handler;
        path = path.replaceAll("/\\d+/", "/:id/");
        if ((handler = handlers.get(new MethodWithRoute(method, path))) != null)
            return handler;
        else return this::print404Response;
    }

    private Response print404Response(Request request) {
        try {
            Response response = new Response("HTTP/1.1", "200 OK ");
            response.addHeaderParameter("Content-Type: plain/text");
            response.addHeaderParameter("Connection: close");
            response.setBody("Not found");
            return response;
        } catch (Exception e) {
            return new Response();
        }
    }

}
