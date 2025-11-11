package body.tickets;

import org.json.JSONObject;

public class CreateTicketsBody {
    public JSONObject getBodyCreateTickets(String title, String description, String attachment, Boolean isPublic) {
        JSONObject body = new JSONObject();
        body.put("title", title);
        body.put("description", description);
        body.put("attachment", attachment);
        body.put("isPublic", isPublic);
        return body;
    }
}
