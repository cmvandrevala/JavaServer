package http_action;

import http_request.Request;
import routing.DataTable;
import routing.RoutesTable;

public class DeleteAction implements HTTPAction {

    public void execute(Request request, DataTable dataTable) {
        dataTable.removeAllData(request.url());
    }

}
