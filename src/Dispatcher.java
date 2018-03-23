public class Dispatcher {
    private Router router;

    Dispatcher(Router router) {
        this.router = router;
    }

    private Handler getRequestHandler(Request request) {
        return router.getRequestHandler(request.getMethod(), request.getPath());
    }

    public Response processRequest(Request request) {
        return getRequestHandler(request).getResponse(request);
    }
}
