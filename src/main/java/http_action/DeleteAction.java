package http_action;

import http_request.Request;
import routing.RoutesTable;

public class DeleteAction implements HTTPAction {

    private RoutesTable routesTable = RoutesTable.getInstance();

    public void execute(Request request) {
        routesTable.removeAllData(request.url());
    }

}
