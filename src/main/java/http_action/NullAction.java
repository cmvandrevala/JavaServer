package http_action;

import http_request.Request;
import routing.DataTable;
import routing.RoutesTable;

public class NullAction implements HTTPAction {

    public void execute(Request request, RoutesTable routesTable, DataTable dataTable) {}

}
