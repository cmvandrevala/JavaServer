package http_action;

import http_request.Request;
import routing.DataTable;

public class UrlReturnsCookieAction implements HTTPAction {

    public void execute(Request request, DataTable dataTable) {
        if(request.queryParamsString().equals("")) {
            dataTable.addData(request.url(), "Body", "<h1>Hello World!</h1>");
        } else {
            dataTable.addData(request.url(), "Set-Cookie", "type=chocolate");
            dataTable.addData(request.url(), "Body", "Eat");
        }
    }

}
