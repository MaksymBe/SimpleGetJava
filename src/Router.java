import java.util.HashMap;
import java.util.Map;

public class Router {
    private Map<MethodWithRoute, Handler> handlers;


    public Router(Map<MethodWithRoute, Handler> handlers) {
        this.handlers = handlers;
    }

    Router() {
        handlers = new HashMap<MethodWithRoute, Handler>();
    }

    public void addHandler(String method, String path, Handler handler) {
        handlers.put(new MethodWithRoute(method, path), handler);
    }

    public Handler getRequestHandler(String method, String path) {
        Handler handler;
        if((handler = handlers.get(new MethodWithRoute(method, path)))!=null)
        return handler;
        else return this::print404Response;
    }

    private Response print404Response(Request request) {
        try {
            Response response = new Response("HTTP/1.1", "200 OK ");
            response.addHeaderParameter("Content-Type: application/json; charset=utf-8");
            response.addHeaderParameter("CConnection: close");
            response.addHeaderParameter("Access-Control-Allow-Origin: *");
            response.setBody("Not found");
            return response;
        } catch (Exception e) {
            return new Response();
        }
    }

}
