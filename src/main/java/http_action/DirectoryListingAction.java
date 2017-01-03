package http_action;

import http_request.Request;
import routing.DataTable;
import routing.PathToUrlMapper;

import java.io.File;

public class DirectoryListingAction implements HTTPAction {

    private PathToUrlMapper mapper;

    public DirectoryListingAction(PathToUrlMapper mapper) {
        this.mapper = mapper;
    }

    public void execute(Request request, DataTable dataTable) {
        String body = "<!DOCTYPE html><html><head><title>Directory Listing</title></head><body>";

        for (File file : mapper.filesInPublicDirectory()) {
            String[] filename = file.getAbsolutePath().split("public/");
            body = body + "<p><a href='/" + filename[1] + "'>" + filename[1] + "</a></p>";
        }

        body = body + "</body></html>";

        dataTable.addBody(request.url(), body);
    }

}
