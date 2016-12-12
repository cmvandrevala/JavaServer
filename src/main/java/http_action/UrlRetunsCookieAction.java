package http_action;

import http_request.Request;
import routing.RoutesTable;

public class UrlRetunsCookieAction implements HTTPAction {

    public void execute(Request request, RoutesTable routesTable) {
        routesTable.addData(request.url(), "Returns-Cookie","chocolate chip");
    }

}
