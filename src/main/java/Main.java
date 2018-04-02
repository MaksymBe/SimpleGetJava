import Core.GroupsHandler;
import Framework.Router;
import Framework.Server;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {

        SpringApplication.run(Main.class, args);

    }
}




/*              Integer port;
                try {
                port = Integer.parseInt(args[0]);
                } catch (Exception e) {
                port = 3000;
                }
                GroupsHandler groupsHandler = new GroupsHandler();
                Router router = new Router();
                router.addHandler("GET", "/groups/", groupsHandler::getAllGroups);
                router.addHandler("GET", "/groups/:id/", groupsHandler::getGroupById);
                router.addHandler("POST", "/groups/", groupsHandler::createGroup);
                router.addHandler("DELETE", "/groups/:id/", groupsHandler::deleteGroup);
                router.addHandler("PATCH", "/groups/:id/", groupsHandler::updateGroup);
                Server server = new Server(port, router);
                server.start();*/
