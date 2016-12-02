package http_action;

import http_request.Request;
import routing.RoutesTable;

public class PutAction implements HTTPAction {

    private RoutesTable routesTable = RoutesTable.getInstance();

    public void execute(Request request) {
        if(request.body().contains("=")) {
            String[] parts = request.body().split("=");
            routesTable.addData(request.url(), parts[0], parts[1]);
        } else {
            routesTable.addData(request.url(), "body", request.body());
        }
    }

}
