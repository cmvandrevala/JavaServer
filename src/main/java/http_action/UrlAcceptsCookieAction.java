package http_action;

import http_request.Request;
import routing.RoutesTable;

public class UrlAcceptsCookieAction implements HTTPAction {

    public void execute(Request request, RoutesTable routesTable) {
        routesTable.addData(request.url(), "Accepts-Cookie","true");
    }

}
