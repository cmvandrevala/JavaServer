package http_action;

import http_request.Request;
import routing.DataTable;

public class UrlAcceptsCookieAction implements HTTPAction {

    public void execute(Request request, DataTable dataTable) {
        if(request.cookie().equals("")) {
            dataTable.addBody(request.url(), "<h1>Hello World!</h1>");
        } else if(request.cookie().contains("type=")) {
            String[] flavor = request.cookie().split("type=");
            dataTable.addBody(request.url(), "mmmm " + flavor[1]);
        } else {
            dataTable.addBody(request.url(), "Your cookie has no type...");
        }
    }

}
