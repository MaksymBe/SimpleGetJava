public class Main {
    public static void main(String[] args) {
        Integer port;
        String filePath;
        try {
            port = Integer.parseInt(args[1]);
        } catch (Exception e) {
            port = 3000;
        }
        try {
            filePath = args[0];
        } catch (Exception e) {
            filePath = "../db.json";
        }
        GroupsHandler groupsHandler = new GroupsHandler(filePath);
        Router router = new Router();
        router.addHandler("GET", "/groups/", groupsHandler::getAllGroups);
        router.addHandler("GET", "/groups/:id/", groupsHandler::getGroupById);
        router.addHandler("POST", "/groups/", groupsHandler::createGroup);
        router.addHandler("DELETE", "/groups/:id/", groupsHandler::deleteGroup);
        router.addHandler("OPTIONS", "/groups/", groupsHandler::optionsProcessing);
        router.addHandler("OPTIONS", "/groups/:id/", groupsHandler::optionsProcessing);
        router.addHandler("PATCH", "/groups/:id/", groupsHandler::updateGroup);
        Server server = new Server(port, router);
        server.start();
    }

}
