import java.util.Hashtable;

public class Request {
    private String method;
    private String path;
    private String protocol;
    private Hashtable<String, String> headers;
    private StringBuffer body;


    public Request() {
        headers = new Hashtable<String, String>();
        body = new StringBuffer();
    }

    public Request(String method, String path, String protocol, Hashtable<String, String> headers, StringBuffer body) {
        this.method = method;
        this.protocol = protocol;
        this.path = path;
        this.headers = headers;
        this.body = body;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getBody() {
        return body.toString();
    }

    public String getHeaders(String headerName) {
        return headers.get(headerName);
    }

    public String getHeadersAsString() {
        return headers.toString();
    }
}
