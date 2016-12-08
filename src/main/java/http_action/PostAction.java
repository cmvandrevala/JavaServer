package http_action;

import http_request.Request;
import routing.RoutesTable;

public class PostAction implements HTTPAction {

    public void execute(Request request, RoutesTable routesTable) {
        if(request.body().contains("=")) {
            String[] parts = request.body().split("=");
            routesTable.addData(request.url(), parts[0], parts[1]);
        } else {
            routesTable.addData(request.url(), "body", request.body());
        }
    }

}
