package http_action;

import http_request.Request;
import http_response.Response;
import routing.DataTable;

public interface HTTPAction {

    void execute(Request request, DataTable dataTable);

}
