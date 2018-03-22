import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Router {
    private Map<MethodWithRoute, Handler> handlers;


    public Router(Map<MethodWithRoute, Handler> handlers) {
        this.handlers = handlers;
        GroupsHandler a = new GroupsHandler();
    }

    public Router() {
        handlers = new HashMap<MethodWithRoute, Handler>();
    }

    public void addHandler(String method, String path, Handler handler) {
        handlers.put(new MethodWithRoute(method, path), handler);
    }

    public Handler getRequestHandler(String method, String path) {
        return handlers.get(new MethodWithRoute(method, path));
    }

}
