package http_action;

import http_request.Request;
import routing.DataTable;

public class RedirectAction implements HTTPAction {

    private String redirectUrl;

    public RedirectAction(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public void execute(Request request, DataTable dataTable) {
        dataTable.addData(request.url(), "Location", this.redirectUrl);
    }

}
