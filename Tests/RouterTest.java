import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RouterTest {

    @Test
    void getRequestHandler() {
        Router router = new Router();
        Handler handler = r -> new Response();
        String pattern = "/groups/:id/";
        router.addHandler("GET", pattern, handler);

        Assertions.assertEquals(handler, router.getRequestHandler("GET", "/groups/5/"));
    }
}