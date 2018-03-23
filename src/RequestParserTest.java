import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RequestParserTest {

    @Test
    void methodInRequestTest() throws Exception {
        RequestParser requestParser = new RequestParser();
        String get = "GET / HTTP/1.1\n" +
                "Host: localhost:3000\n" +
                "Connection: keep-alive\n" +
                "Cache-Control: max-age=0\n" +
                "Upgrade-Insecure-Requests: 1\n" +
                "User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.162 Safari/537.36\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8\n" +
                "Accept-Encoding: gzip, deflate, br\n" +
                "Accept-Language: en-US,en;q=0.9\n" +
                "Cookie: Webstorm-1d8981e1=d201260b-715a-4fca-b54b-5b3bcb06a673\n";
        String post = "POST / HTTP/1.1\n";
        String delete = "DELETE / HTTP/1.1\n" +
                "Host: localhost:3000\n";
        assertEquals("GET", requestParser.parseString(get).getMethod());
        assertEquals("POST", requestParser.parseString(post).getMethod());
        assertEquals("DELETE", requestParser.parseString(delete).getMethod());
    }

    @Test
    void headerInRequestTest() throws Exception {
        RequestParser requestParser = new RequestParser();
        String get = "GET / HTTP/1.1\n" +
                "Host: localhost:3000\n" +
                "Connection: keep-alive\n" +
                "Cache-Control: max-age=0\n" +
                "Upgrade-Insecure-Requests: 1\n" +
                "User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.162 Safari/537.36\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8\n" +
                "Accept-Encoding: gzip, deflate, br\n" +
                "Accept-Language: en-US,en;q=0.9\n" +
                "Cookie: Webstorm-1d8981e1=d201260b-715a-4fca-b54b-5b3bcb06a673\n";
        assertEquals("localhost:3000", requestParser.parseString(get).getHeaders("host"));
    }

    @Test
    void bodyInRequestTest() throws Exception {
        RequestParser requestParser = new RequestParser();
        String get = "POST /dfsfs HTTP/1.1\n" +
                "Host: localhost:3000\n" +
                "Content-Type: application/json\n" +
                "Content-Length: 64\n" +
                "Cache-Control: no-cache\n" +
                "Postman-Token: 4cdd19e8-2a44-fe1e-72cb-508016cc2b5d\n" +
                "\n" +
                "{\n" +
                "    \"name\": \"inCamp8\",\n" +
                "    \"period\": \"05.03.2018-05.06.2018\"\n" +
                "}";
        assertEquals("{\n" +
                "    \"name\": \"inCamp8\",\n" +
                "    \"period\": \"05.03.2018-05.06.2018\"\n" +
                "}\r\n", requestParser.parseString(get).getBody());
    }

}
