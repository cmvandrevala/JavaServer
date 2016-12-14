package http_action;

import http_request.Request;
import routing.DataTable;
import routing.RoutesTable;

public class UrlRetunsCookieAction implements HTTPAction {

    public void execute(Request request, RoutesTable routesTable, DataTable dataTable) {
        dataTable.addData(request.url(), "Returns-Cookie", "true");
    }

}
