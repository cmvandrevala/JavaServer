package http_action;

import http_request.Request;
import routing.RoutesTable;

public interface HTTPAction {

    void execute(Request request, RoutesTable routesTable);

}
