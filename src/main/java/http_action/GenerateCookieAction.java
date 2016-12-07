package http_action;

import http_request.Request;
import routing.RoutesTable;

public class GenerateCookieAction implements HTTPAction {

    private RoutesTable routesTable = RoutesTable.getInstance();

    public void execute(Request request) {
        routesTable.addData(request.url(), "cookie", request.queryParamsString());
    }
}
