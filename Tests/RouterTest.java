import Framework.Handler;
import Framework.Response;
import Framework.Route;
import Framework.Router;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

class RouterTest {

    @Test
    void getRequestHandler() {
        Router router = new Router();
        Handler handler = r -> new Response();
        String pattern = "/groups/:id/";
        router.addHandler("GET", pattern, handler);
        HashMap<String, Integer> map = new HashMap<>();
        map.put("id", 5);
        Route result = new Route(handler, map);

        Assertions.assertEquals(result, router.getRoute("GET", "/groups/5/"));
    }

    @Test
    void getSpecificParam(){
        Router router = new Router();
        router.addHandler("GET", "/groups/:id/interns/:internId", r->null);
        HashMap<String, Integer> map = new HashMap<>();
        map.put("id", 5);
        map.put("internId", 5);

        Route route = router.getRoute("GET", "/groups/5/interns/7");

        Assertions.assertEquals(new Integer(5), route.getParams().get("id"));
        Assertions.assertEquals(new Integer(7), route.getParams().get("internId"));
    }
}