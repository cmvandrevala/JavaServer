package http_action;

import http_request.Request;
import routing.DataTable;

public class UrlAcceptsCookieAction implements HTTPAction {

    public void execute(Request request, DataTable dataTable) {
        if(request.cookie().equals("")) {
            dataTable.addData(request.url(), "Body", "<h1>Hello World!</h1>");
        } else if(request.cookie().contains("type=")) {
            String[] flavor = request.cookie().split("type=");
            dataTable.addData(request.url(), "Body", "mmmm " + flavor[1]);
        } else {
            dataTable.addData(request.url(), "Body", "Your cookie has no type...");
        }
    }

}
