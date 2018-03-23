public class Dispatcher {
    private Router router;

    Dispatcher(Router router) {
        this.router = router;
    }

    private Route getRequestHandler(Request request) {
        return router.getRoute(request.getMethod(), request.getPath());
    }

    public Response processRequest(Request request) {
        if(request.getMethod().equals("OPTIONS")) return optionsProcessing(request);
        return getRequestHandler(request).getHandler().getResponse(request);
    }

    private Response optionsProcessing(Request request) {
        Response response = new Response(request.getProtocol(), "204 No Content ");
        try {
            response.addHeaderParameter("Access-Control-Allow-Credentials: true");
            response.addHeaderParameter("Access-Control-Allow-Headers: content-type");
            response.addHeaderParameter("Access-Control-Allow-Methods: GET,HEAD,PUT,PATCH,POST,DELETE");
            response.addHeaderParameter("Access-Control-Allow-Origin: " + request.getHeaders("origin"));
            response.addHeaderParameter("Connection: keep-alive");
            response.addHeaderParameter("Content-Length: 0");
            response.addHeaderParameter("Vary: Origin, Access-Control-Request-Headers");
            return response;
        } catch (Exception e) {
            return new Response();
        }
    }
}
