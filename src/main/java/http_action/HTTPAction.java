package http_action;

import http_request.Request;
import routing.DataTable;
import routing.RoutesTable;

public interface HTTPAction {

    void execute(Request request, RoutesTable routesTable, DataTable dataTable);

}
