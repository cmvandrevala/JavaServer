package http_action;

import http_request.Request;
import routing.DataTable;
import routing.RoutesTable;

public class PutAction implements HTTPAction {

    public void execute(Request request, DataTable dataTable) {
        if(request.body().contains("=")) {
            String[] parts = request.body().split("=");
            dataTable.addData(request.url(), parts[0], parts[1]);
        } else {
            dataTable.addData(request.url(), "body", request.body());
        }
    }

}
