package http_action;

import http_request.Request;
import routing.DataTable;
import routing.PathToUrlMapper;

import java.io.*;
import java.math.BigInteger;
import java.security.SecureRandom;

public class PatchWithETagAction implements HTTPAction {

    private PathToUrlMapper mapper;
    private SecureRandom random = new SecureRandom();

    public PatchWithETagAction(PathToUrlMapper mapper) {
        this.mapper = mapper;
    }

    public void execute(Request request, DataTable dataTable) {
        String eTagInDataTable = dataTable.retrieveData(request.url(), "ETag");
        if(request.ifMatch().equals(eTagInDataTable)) {
            File file = this.mapper.fileCorrespondingToUrl(request.url());
            PrintWriter out;
            try {
                out = new PrintWriter(new BufferedWriter(new FileWriter(file.getAbsoluteFile(), false)));
                out.println(request.body());
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            dataTable.addData(request.url(),"ETag", generateETag());
        }
    }

    private String generateETag() {
        return new BigInteger(130, random).toString(32);
    }

}
