package http_action;

import http_request.Request;
import routing.DataTable;
import routing.RoutesTable;

public class RedirectAction implements HTTPAction {

    String redirectUrl;

    public RedirectAction(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public void execute(Request request, DataTable dataTable) {
        dataTable.addData(request.url(), "Redirects", redirectUrl);
    }

}
