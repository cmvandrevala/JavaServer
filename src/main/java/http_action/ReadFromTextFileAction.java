package http_action;

import http_request.Request;
import routing.DataTable;
import routing.PathToUrlMapper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class ReadFromTextFileAction implements HTTPAction {

    private PathToUrlMapper mapper;

    public ReadFromTextFileAction(PathToUrlMapper mapper) {
        this.mapper = mapper;
    }

    public void execute(Request request, DataTable dataTable) {
        File file = this.mapper.fileCorrespondingToUrl(request.url());
        String body = null;

        if(file.exists()) {
            if(isImageFile(file)) {
                body = readImageFile(file, request);
            } else {
                try {
                    body = readTextFile(file.getAbsolutePath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            dataTable.addData(request.url(),"Body", body);
        }
    }

    private boolean isImageFile(File file) {
        String path = file.getAbsolutePath();
        return path.contains(".jpeg") || path.contains(".png") || path.contains(".jpeg");
    }

    private String readImageFile(File file, Request request) {
        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            if(request.url().contains(".png")) {
                ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
            } else if(request.url().contains(".jpeg")) {
                ImageIO.write(bufferedImage, "jpg", byteArrayOutputStream);
            } else {
                ImageIO.write(bufferedImage, "gif", byteArrayOutputStream);
            }
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            return new String(byteArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private String readTextFile(String filename) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new FileReader(filename));
            String str;
            while ((str = in.readLine()) != null) {
                contentBuilder.append(str);
            }
            in.close();
        } catch(Exception ignored) {}
        return contentBuilder.toString();
    }

}
