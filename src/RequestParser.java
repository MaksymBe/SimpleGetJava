import java.io.*;
import java.util.Hashtable;
import java.util.stream.Collectors;

public class RequestParser {
    private StringBuffer body;
    private Hashtable<String, String> headers;
    private String method;
    private String path;
    private String protocol;

    public RequestParser() {
        headers = new Hashtable<String, String>();
        body = new StringBuffer();
    }

    private void ClearFields() {
        headers = new Hashtable<String, String>();
        body = new StringBuffer();
        method = "";
        path = "";
        protocol = "";
    }

    public Request parseString(String stringToParse) throws Exception {
        return parseRequest(new StringReader(stringToParse));
    }

    public Request parseRequest(Reader request) {
        try {
            BufferedReader reader = new BufferedReader(request);
            setRequestLine(reader.readLine());

            if (parseRequestHeaders(reader)) {
                parseRequestBody(reader);
                /*Integer bodyLine;
                for (int i = 0; i < Integer.parseInt(headers.get("Content-Length")); i++) {
                    bodyLine = reader.read();
                    appendMessageBody(new String(bodyLine));
                }*/
                /*String bodyLine;
                while (body.toString().getBytes().length
                        <= Integer.parseInt(headers.get("Content-Length"))+2) {
                    bodyLine = reader.readLine();
                    appendMessageBody(bodyLine);
                }*/
            }
            reader.close();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        Request req = new Request(method, path, protocol, headers, body);
        String asd = body.toString();
        ClearFields();
        return req;
    }

    private Boolean parseRequestHeaders(BufferedReader reader){
        try {
            String header = reader.readLine();
            while (header.length() > 0) {
                addHeaderParameter(header);
                header = reader.readLine();
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private void parseRequestBody(BufferedReader reader) throws Exception {
        char[] body = new char[getContentLength()];

        reader.read(body, 0, body.length);

        appendMessageBody(new String(body));
    }

    private int getContentLength() {
        return Integer.parseInt(headers.get("Content-Length"));
    }

    private void setRequestLine(String query) throws Exception {
        if (query == null || query.length() == 0) {
            throw new Exception("Invalid Request Query: " + query);
        }
        String[] queryArray = query.split(" ");
        this.method = queryArray[0];
        this.path = (queryArray[1].charAt(queryArray[1].length() - 1) == '/') ? queryArray[1] :queryArray[1]+"/";
        this.protocol = queryArray[2];
    }

    private void addHeaderParameter(String header) throws Exception {
        int idx = header.indexOf(":");
        if (idx == -1) {
            throw new Exception("Invalid Header Parameter: " + header);
        }
        headers.put(header.substring(0, idx), header.substring(idx + 2, header.length()));
    }

    private void appendMessageBody(String bodyLine) {
        body.append(bodyLine).append("\r\n");
    }

}