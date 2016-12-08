package http_action;

import http_request.Request;
import routing.RoutesTable;

public class DeleteAction implements HTTPAction {

    public void execute(Request request, RoutesTable routesTable) {
        routesTable.removeAllData(request.url());
    }

}
