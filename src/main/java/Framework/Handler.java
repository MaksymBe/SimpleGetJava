package Framework;

import Framework.Response;
import Framework.Request;

@FunctionalInterface
public interface Handler {
    Response getResponse(Request request);
}
