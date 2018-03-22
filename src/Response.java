import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Hashtable;

class Response {
    private String protocol;
    private String status;
    private Hashtable<String, String> headers;
    private StringBuffer body;


    Response() {
        headers = new Hashtable<String, String>();
        body = new StringBuffer();


    }

    public Response(String protocol, String status, Hashtable<String, String> headers, StringBuffer body) {
        this.protocol = protocol;
        this.status = status;
        this.headers = headers;
        this.body = body;
    }

    Response(String protocol, String status) {
        this.protocol = protocol;
        this.status = status;
    }

    void setBody(String bodyLine) {
        body.append(bodyLine).append("\r\n");
    }

    public void addHeaderParameter(String header) throws Exception {
        int idx = header.indexOf(":");
        if (idx == -1) {
            throw new Exception("Invalid Header Parameter: " + header);
        }
        headers.put(header.substring(0, idx), header.substring(idx + 2, header.length()));
    }

    public String getProtocol() {
        return protocol;
    }

    public String getStatus() {
        return status;
    }

    private String getStatusLine() {
        return protocol + " " + status + "\r\n";
    }

    private String getHeadersAsString() {
        String[] headersAsString = new String[]{""};
        this.headers.forEach((headerName, headerContent) -> headersAsString[0] += headerName + ": " + headerContent + "\r\n");
        return headersAsString[0];
    }

    StringBuffer getBody() {
        return body;
    }

    public void sendResponse(OutputStream clientStream) {
        PrintWriter printWriter = new PrintWriter(clientStream);
        printWriter.print(getStatusLine());
        printWriter.print(getHeadersAsString() + "\r\n");
        printWriter.print(getBody());
/*        printWriter.print("Content-Type: application/json; charset=utf-8\r\n");
        printWriter.print("Access-Control-Allow-Origin: *\r\n");
        printWriter.print("\r\n");
        // printWriter.print(data);*/
    }
}
