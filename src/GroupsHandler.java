import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class GroupsHandler {
    private ArrayList<Group> groups;
    private ObjectMapper mapper = new ObjectMapper();
    private Integer maxId = 0;

    GroupsHandler() {
        groups = new ArrayList<>();
    }

    GroupsHandler(String filePath) {
        try {
            File file = new File(filePath);
            JsonNode jsonInTree = mapper.readTree(file);
            JsonNode groupsNode = jsonInTree.get("groups");
            groups = new ArrayList<>(Arrays.asList(mapper.treeToValue(groupsNode, Group[].class)));
            groups.forEach((group) -> {
                if (group.getId() > maxId) maxId = group.getId();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Response getAllGroups(Request request) {
        return generateDefaultResponse(groups, request);
    }

    public Response getGroupById(Request request) {
        Integer groupId = getGroupId(request.getPath());
        Group groupToReturn = null;
        for (Group group : groups) {
            if (group.getId().equals(groupId)) {
                groupToReturn = group;
                break;
            }
        }
        return generateDefaultResponse(groupToReturn, request);

    }

    public Response createGroup(Request request) {
        Group group = new Group();
        try {
            group = mapper.readValue(request.getBody(), Group.class);
            group.setId(++maxId);
            groups.add(group);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return generateDefaultResponse(group, request);
    }

    public Response deleteGroup(Request request) {
        Integer groupId = getGroupId(request.getPath());
        Group groupToReturn = null;
        for (Group group : groups) {
            if (group.getId().equals(groupId)) {
                groupToReturn = group;
                groups.remove(group);
                break;
            }
        }
        return generateDefaultResponse(groupToReturn, request);
    }

    public Response optionsProcessing(Request request) {
        Response response = new Response(request.getProtocol(), "204 No Content ");
        try {
            response.addHeaderParameter("Access-Control-Allow-Credentials: true");
            response.addHeaderParameter("Access-Control-Allow-Headers: content-type");
            response.addHeaderParameter("Access-Control-Allow-Methods: GET,HEAD,PUT,PATCH,POST,DELETE");
            response.addHeaderParameter("Access-Control-Allow-Origin: " + request.getHeaders("origin"));
            response.addHeaderParameter("Connection: keep-alive");
            response.addHeaderParameter("Content-Length: 0");
            response.addHeaderParameter("Vary: Origin, Access-Control-Request-Headers");
            return response;
        } catch (Exception e) {
            return new Response();
        }
    }

    public Response updateGroup(Request request){
        Integer groupId = getGroupId(request.getPath());
        Group patch = (Group) request.getBodyAsObject(Group.class);
        Group groupToReturn = null;
        for (Group group : groups) {
            if (group.getId().equals(groupId)) {
                group.patch(patch);
                groupToReturn = group;
                break;
            }
        }
        return generateDefaultResponse(groupToReturn, request);
    }

    private Integer getGroupId(String route) {
        String[] arrayToGetNumber = route.split("/");
        return Integer.parseInt(arrayToGetNumber[arrayToGetNumber.length - 1]);
    }

    private Response generateDefaultResponse(Object data, Request request) {
        try {
            Response response = new Response(request.getProtocol(), "200 OK ");
            response.addHeaderParameter("Content-Type: application/json; charset=utf-8");
            response.addHeaderParameter("Access-Control-Allow-Origin: *");
            response.setBody(getStringForBody(data));
            return response;
        } catch (Exception e) {
            return new Response();
        }
    }

    private String getStringForBody(Object data) throws Exception {
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
    }


}
