import com.fasterxml.jackson.databind.ObjectMapper;

public class GroupsHandler {
    private ObjectMapper mapper = new ObjectMapper();
    private Repository<Group> repository;

    GroupsHandler() {
        repository = new SQLRepositoryForGroups("max", "qwerty");
    }

    GroupsHandler(String userName, String pass) {
        try {
            repository = new SQLRepositoryForGroups(userName, pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Response getAllGroups(Request request) {
        try {
            return generateDefaultResponse(repository.getAll(), request, "200 OK");
        } catch (RepositoryException e) {
            return print404Response(request);
        }
    }

    public Response getGroupById(Request request) {
        Integer groupId = getGroupId(request.getPath());
        try {
            return generateDefaultResponse(repository.getById(groupId), request, "200 OK");
        } catch (RepositoryException e) {
            return print404Response(request);
        }
    }

    public Response createGroup(Request request) {
        Group group = new Group();
        try {
            group = mapper.readValue(request.getBody(), Group.class);
            group = repository.create(group);
            return generateDefaultResponse(group, request, "201 Created");
        } catch (RepositoryException e) {
            return print404Response(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return print404Response(request);
    }

    public Response deleteGroup(Request request) {
        Integer groupId = getGroupId(request.getPath());
        try {
            return generateDefaultResponse(repository.delete(groupId), request, "204 No Content");
        } catch (RepositoryException e) {
            return print404Response(request);
        }
    }

    public Response updateGroup(Request request) {
        Integer groupId = getGroupId(request.getPath());
        Group patch = (Group) request.getBodyAsObject(Group.class);
        Group groupToReturn = null;
        try {
            groupToReturn = repository.update(groupId, patch);
            return generateDefaultResponse(groupToReturn, request, "201 Modified");
        } catch (RepositoryException e) {
            return print404Response(request);
        }

    }

    private Integer getGroupId(String route) {
        String[] arrayToGetNumber = route.split("/");
        return Integer.parseInt(arrayToGetNumber[arrayToGetNumber.length - 1]);
    }

    private Response generateDefaultResponse(Object data, Request request, String status) {
        try {
            Response response = new Response(request.getProtocol(), status);
            response.addHeaderParameter("Content-Type: application/json; charset=utf-8");
            response.addHeaderParameter("Access-Control-Allow-Origin: *");
            if (data != null) response.setBody(getStringForBody(data));
            return response;
        } catch (Exception e) {
            return new Response();
        }
    }

    private String getStringForBody(Object data) throws Exception {
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
    }

    private Response print404Response(Request request) {
        try {
            Response response = new Response("HTTP/1.1", "404 Not Found ");
            response.addHeaderParameter("Content-Type: plain/text");
            response.addHeaderParameter("Connection: close");
            response.setBody("Group not found");
            return response;
        } catch (Exception e) {
            return new Response();
        }
    }


}
