package http_action;

import http_request.Request;
import routing.DataTable;
import routing.PathToUrlMapper;

import java.io.*;

public class ReadFromTextFileAction implements HTTPAction {

    private PathToUrlMapper mapper;

    public ReadFromTextFileAction(PathToUrlMapper mapper) {
        this.mapper = mapper;
    }

    public void execute(Request request, DataTable dataTable) {
        File file = this.mapper.fileCorrespondingToUrl(request.url());
        if(file.exists()) {
            String body = null;
            try {
                body = readFile(file.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            dataTable.addData(request.url(),"Body", body);
        }
    }

    private String readFile(String filename) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        BufferedReader in = new BufferedReader(new FileReader(filename));
        String str;
        while ((str = in.readLine()) != null) {
            contentBuilder.append(str);
        }
        in.close();
        return contentBuilder.toString();
    }

}
