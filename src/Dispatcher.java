public class Dispatcher {
    private Router router;

    Dispatcher(Router router){
        this.router = router;
    }

    private Handler getRequestHandler(Request request){

        String path = request.getPath();
        String[] pathArr = path.split("/");
        path = isById(path)? "/"+ pathArr[1] + "/:id" : path;
        return router.getRequestHandler(request.getMethod(), path);
    }

    public Response processRequest(Request request) {
        return getRequestHandler(request).getResponse(request);
    }

    private  Boolean isById(String s) {
        return s.matches("^/[a-zA-z]+/\\d+(/$|$)");
    }
}
