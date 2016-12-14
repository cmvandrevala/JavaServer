package http_action;

import http_request.Request;
import routing.DataTable;

public interface HTTPAction {

    void execute(Request request, DataTable dataTable);

}
