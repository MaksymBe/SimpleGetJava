import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RequestParser {
    String stringToParse = null;

    public RequestParser() {

    }

    public Request parseInputStream(InputStream inputStream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String stringToParse = "";
        try {
            String temp;
            while (( temp = reader.readLine()).length() != 0) {
                stringToParse += temp + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parseString(stringToParse);
    }

    private Request parseString(String stringToParse) {
        return null;
    }
}
