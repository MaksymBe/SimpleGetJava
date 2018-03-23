import java.util.HashMap;

public class Route {
    Handler handler;
    HashMap<String, Integer> params;

    public Route(Handler handler) {
        this.handler = handler;
        params = new HashMap<>();
    }

    public Route(Handler handler, HashMap<String, Integer> params) {
        this.handler = handler;
        this.params = params;
    }

    public Handler getHandler() {
        return handler;
    }

    public HashMap<String, Integer> getParams() {
        return params;
    }
}
