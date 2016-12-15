package http_action;

import http_request.Request;
import routing.DataTable;

public class PostAction implements HTTPAction {

    public void execute(Request request, DataTable dataTable) {
        if(request.body().contains("=")) {
            String[] parts = request.body().split("=");
            dataTable.addData(request.url(), parts[0], parts[1]);
            dataTable.addData(request.url(), "Body", request.body());
        } else {
            dataTable.addData(request.url(), "Body", request.body());
        }
    }

}
