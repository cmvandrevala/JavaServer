package http_action;

import http_request.Request;
import routing.DataTable;

public class UrlReturnsCookieAction implements HTTPAction {

    public void execute(Request request, DataTable dataTable) {
        if(request.queryParamsString().equals("")) {
            dataTable.addBody(request.url(), "<h1>Hello World!</h1>");
        } else {
            dataTable.addSetCookie(request.url(), request.queryParamsString());
            dataTable.addBody(request.url(), "Eat");
        }
    }

}
