import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class GroupsHandler {
    private ArrayList<Group> groups;
    private ObjectMapper mapper = new ObjectMapper();
    GroupsHandler() {
        groups = new ArrayList<>();
    }

    GroupsHandler(String filePath) {
        try {

            File file = new File(filePath);
            JsonNode jsonInTree = mapper.readTree(file);
            JsonNode groupsNode = jsonInTree.get("groups");
            groups = new ArrayList<>(Arrays.asList(mapper.treeToValue(groupsNode, Group[].class)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Response getAllGroups(Request request) {
        return generateDefaultResponse(groups, request);
    }

    public Response getGroupById(Request request){
        String[] arrayToGetNumber = request.getPath().split("/");
        Integer groupId = Integer.parseInt(arrayToGetNumber[arrayToGetNumber.length - 1]);
        Group groupToReturn = null;
        for (Group group : groups) {
            if (group.getId().equals(groupId)) {
                groupToReturn = group;
                break;
            }
        }
        return generateDefaultResponse(groupToReturn, request);

    }

    private Response generateDefaultResponse(Object data, Request request){
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

    private String getStringForBody(Object data) throws Exception{
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
    }


}
