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
}