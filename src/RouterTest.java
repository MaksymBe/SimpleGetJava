import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RouterTest {

    @Test
    void getRequestHandler() {
        Router router = new Router();
        Handler handler = r -> new Response();
        router.addHandler("GET", "/groups", handler);

        assertEquals(handler, router.getRequestHandler("GET", "/groups"));
    }
}