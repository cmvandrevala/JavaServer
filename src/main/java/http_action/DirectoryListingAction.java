package http_action;

import http_request.Request;
import routing.DataTable;

import java.io.File;

public class DirectoryListingAction implements HTTPAction {

    public void execute(Request request, DataTable dataTable) {
        String body = "";
        File folder = new File(System.getProperty("user.dir") + "/public");
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            String[] f = file.getAbsolutePath().split("public");
            body = body + "<p><a href='" + f[1] + "'>" + f[1] + "</a></p>";
        }

        dataTable.addData(request.url(), "Body", body);
    }


}
