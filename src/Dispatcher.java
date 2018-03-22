import java.lang.reflect.Method;

public class Dispatcher {
    private Request request = null;
    private Response response = null;
    private Router router = null;

    public Dispatcher(Router router){
        this.router = router;
    }

    public void getRequestHandler(Request request){

    }
}
