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

    public GroupsHandler(String filePath) {
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
        try {
            Response response = new Response(request.getProtocol(), "200 OK ");
            response.addHeaderParameter("Content-Type: application/json; charset=utf-8");
            response.addHeaderParameter("Access-Control-Allow-Origin: *");
            response.setBody(getStringForBody(groups));
            return response;
        } catch (Exception e) {
            return new Response();
        }

    }

    private String getStringForBody(Object data) throws Exception{
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
    }


}
