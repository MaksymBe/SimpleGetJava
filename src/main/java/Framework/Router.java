package Framework;

import java.util.HashMap;
import java.util.Map;

public class Router {
    private Map<MethodAndPath, Handler> handlers;
    private Handler errorHandler = this::print404Response;

    public Router(Map<MethodAndPath, Handler> handlers) {
        this.handlers = handlers;
    }

    public Router(Handler errorHandler) {
        this.errorHandler = errorHandler;
    }

    public Router() {
        handlers = new HashMap<>();
    }

    public void addHandler(String method, String path, Handler handler) {
        String[] pathWords = path.split("/");
        MethodAndPath methodAndPath = new MethodAndPath(method, path);
        for (String word : pathWords) {
            if (word.matches(":.+(/|$)")) {
                methodAndPath.addParameter(word.substring(1, word.length()));
            }
        }
        String pathToHandler = path.replaceAll("/:\\w+(/|$)", "/:id/");
        methodAndPath.setPath(pathToHandler);
        handlers.put(methodAndPath, handler);
    }

    public Route getRoute(String method, String path) {
        Handler handler;
        String[] pathWords = path.split("/");
        path = path.replaceAll("/\\d+(/|$)", "/:id/");
        if ((handler = handlers.get(new MethodAndPath(method, path))) == null) return new Route(this::print404Response);
        HashMap<String, Integer> params = new HashMap<>();
        MethodAndPath[] keys = handlers.keySet().toArray(new MethodAndPath[]{});

        MethodAndPath key = null;
        for (MethodAndPath methodAndPath : keys) {
            if (methodAndPath.equals(new MethodAndPath(method, path))) {
                key = methodAndPath;
            }
        }
        int counter = 0;
        String[] keyParams = key.getParams();
        for (int i = 0; i < pathWords.length; i++) {
            if (pathWords[i].matches("\\d+")) {
                params.put(keyParams[counter++], Integer.parseInt(pathWords[i]));
            }
        }
        return new Route(handler, params);
    }

    private Response print404Response(Request request) {
        try {
            Response response = new Response("HTTP/1.1", "404 Not Found ");
            response.addHeaderParameter("Content-Type: plain/text");
            response.addHeaderParameter("Connection: close");
            response.setBody("Not found");
            return response;
        } catch (Exception e) {
            return new Response();
        }
    }

}
