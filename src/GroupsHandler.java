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
        return generateDefaultResponse(groups, request, "200 OK");
    }

    public Response getGroupById(Request request) {
        Integer groupId = getGroupId(request.getPath());
        for (Group group : groups) {
            if (group.getId().equals(groupId)) {
                return generateDefaultResponse(group, request, "200 OK");
            }
        }
        return print404Response(request);

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
        return generateDefaultResponse(group, request, "201 Created");
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
        return generateDefaultResponse(groupToReturn, request, "204 No Content");
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
        return generateDefaultResponse(groupToReturn, request, "201 Modified");
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
            response.setBody(getStringForBody(data));
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
