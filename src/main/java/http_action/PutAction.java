package http_action;

import http_request.Request;
import routing.DataTable;

public class PutAction implements HTTPAction {

    public void execute(Request request, DataTable dataTable) {
        if(request.body().contains("=")) {
            String[] parts = request.body().split("=");
            dataTable.addCustomData(request.url(), parts[0], parts[1]);
            dataTable.addBody(request.url(), request.body());
        } else {
            dataTable.addBody(request.url(), request.body());
        }
    }

}
