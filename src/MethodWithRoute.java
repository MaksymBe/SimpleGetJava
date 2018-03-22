import java.util.Objects;
import java.util.regex.Pattern;

public class MethodWithRoute {
    private String method;
    private String route;

    public MethodWithRoute(String method, String route) {
        this.method = method;
        this.route = route;
    }

    public String getMethod() {
        return method;
    }

    public String getRoute() {
        return route;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MethodWithRoute that = (MethodWithRoute) o;
        return Objects.equals(method, that.method) &&
                Objects.equals(route, that.route);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, route);
    }
}
