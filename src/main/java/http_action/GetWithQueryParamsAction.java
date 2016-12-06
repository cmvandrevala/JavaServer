package http_action;

import http_request.Request;
import routing.PathToUrlMapper;
import routing.RoutesTable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class GetWithQueryParamsAction implements HTTPAction {

    private RoutesTable routesTable = RoutesTable.getInstance();

    public void execute(Request request) {
        routesTable.addData(request.url(),"body", request.queryParamsString());
    }

}
