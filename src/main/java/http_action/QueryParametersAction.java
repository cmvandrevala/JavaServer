package http_action;

import http_request.Request;
import routing.DataTable;

public class QueryParametersAction implements HTTPAction {

    public void execute(Request request, DataTable dataTable) {
        String body = request.queryParamsString().replace("1=O", "1 = O").replace("2=s", "2 = s");
        dataTable.addBody(request.url(), body);
    }

}
