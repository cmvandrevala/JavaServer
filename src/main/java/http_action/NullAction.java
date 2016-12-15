package http_action;

import http_request.Request;
import routing.DataTable;

public class NullAction implements HTTPAction {

    public void execute(Request request, DataTable dataTable) {}

}
