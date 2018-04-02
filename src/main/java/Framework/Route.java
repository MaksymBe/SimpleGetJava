package Framework;

import java.util.HashMap;
import java.util.Objects;

public class Route {
    private Handler handler;
    private HashMap<String, Integer> params;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return Objects.equals(handler, route.handler) &&
                Objects.equals(params, route.params);
    }

    @Override
    public int hashCode() {

        return Objects.hash(handler, params);
    }
}
