import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class GroupsHandler {
    private ObjectMapper mapper = new ObjectMapper();
    private DBController dbController;

    GroupsHandler() {
        dbController = new DBController("root", "root");
    }

    GroupsHandler(String userName, String pass) {
        try {
            dbController = new DBController(userName, pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Response getAllGroups(Request request) {
        try {
            return generateDefaultResponse(dbController.getAllGroups(), request, "200 OK");
        } catch (Exception e) {
            return print404Response(request);
        }
    }

    public Response getGroupById(Request request) {
        Integer groupId = getGroupId(request.getPath());
        try{
            return generateDefaultResponse(dbController.getGroupById(groupId), request, "200 OK");
        } catch (Exception e) {
            return print404Response(request);
        }
    }

    public Response createGroup(Request request) {
        Group group = new Group();
        try {
            group = mapper.readValue(request.getBody(), Group.class);
            group = dbController.create(group);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return generateDefaultResponse(group, request, "201 Created");
    }

    public Response deleteGroup(Request request) {
        Integer groupId = getGroupId(request.getPath());
        try{
            return generateDefaultResponse(dbController.delete(groupId), request, "204 No Content");
        } catch (Exception e){
            return print404Response(request);
        }
    }

    public Response updateGroup(Request request) {
        Integer groupId = getGroupId(request.getPath());
        Group patch = (Group) request.getBodyAsObject(Group.class);
        Group groupToReturn = null;
        try{
            groupToReturn = dbController.update(groupId, patch);
        } catch (Exception e){
            e.printStackTrace();
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
