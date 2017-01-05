package http_action;

import http_request.Request;
import routing.DataTable;
import routing.PathToUrlMapper;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static sun.security.pkcs11.wrapper.Functions.toHexString;

public class PatchWithETagAction implements HTTPAction {

    private PathToUrlMapper mapper;

    public PatchWithETagAction(PathToUrlMapper mapper) {
        this.mapper = mapper;
    }

    public void execute(Request request, DataTable dataTable) {
        synchronizedExecute(request, dataTable);
    }

    String encode(String str) {
        return computeSha1OfString(str);
    }

    private synchronized void synchronizedExecute(Request request, DataTable dataTable) {
        String eTagInDataTable = dataTable.retrieveETag(request.url());
        if(!request.ifMatch().equals(eTagInDataTable)) {
            dataTable.addETag(request.url(), encode(request.body()));
            File file = this.mapper.fileCorrespondingToUrl(request.url());
            writeToFile(file, request);
        }
    }

    private synchronized void writeToFile(File file, Request request) {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file, false)))) {
            out.write(request.body());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String computeSha1OfString(final String message)
            throws UnsupportedOperationException, NullPointerException {
        try {
            return computeSha1OfByteArray(message.getBytes(("UTF-8")));
        } catch (UnsupportedEncodingException ex) {
            throw new UnsupportedOperationException(ex);
        }
    }

    private static String computeSha1OfByteArray(final byte[] message)
            throws UnsupportedOperationException {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(message);
            byte[] res = md.digest();
            return toHexString(res);
        } catch (NoSuchAlgorithmException ex) {
            throw new UnsupportedOperationException(ex);
        }
    }
}
