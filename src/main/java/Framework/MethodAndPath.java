package Framework;

import java.util.ArrayList;
import java.util.Objects;

public class MethodAndPath {
    private String method;
    private String path;
    private ArrayList<String> params;

    public MethodAndPath(String method, String path) {
        this.method = method;
        this.path = path;
        params = new ArrayList<>();
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String[] getParams() {
        String[] array = params.toArray(new String[]{});
        return array;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public void addParameter(String parameter) {
        params.add(parameter);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MethodAndPath that = (MethodAndPath) o;
        return Objects.equals(method, that.method) &&
                Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, path);
    }
}
