package org.eliondog.ex01;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * Response
 *
 * @author eliondog - 2021-06-03 00:23:14
 */
public class Response {

  private static final int BEGIN = 0;

  private static final int BUFFER_SIZE = 1024;

  private Request request;

  private OutputStream outputStream;

  public Response(OutputStream outputStream) {
    this.outputStream = outputStream;
  }

  public void sendStaticResource() throws IOException {
    byte[] bytes = new byte[BUFFER_SIZE];
    FileInputStream fileInputStream = null;
    try {
      File file = new File(HttpServer.WEB_ROOT, request.getUri());
      if (file.exists()) {
        String responseHeader =
            new StringBuffer("HTTP/1.1 200 OK\\r\\n").append("Content-Type: text/html\r\n")
                .append("Content-Length: 148\r\n").append("\r\n").toString();
        outputStream.write(responseHeader.getBytes(StandardCharsets.UTF_8));

        fileInputStream = new FileInputStream(file);
        int ch = fileInputStream.read(bytes, BEGIN, BUFFER_SIZE);
        while (ch != HttpServer.END) {
          System.out.println(new String(bytes));
          outputStream.write(bytes, BEGIN, BUFFER_SIZE);
          ch = fileInputStream.read(bytes, BEGIN, BUFFER_SIZE);
        }
      } else {
        String errMsg = new StringBuilder("HTTP/1.1 404 File Not Found\\r\\n")
            .append("Content-Type: text/html\r\n").append("Content-Length: 23\r\n").append("\r\n")
            .append("<h1>File Not Found</h1>").toString();
        outputStream.write(errMsg.getBytes(StandardCharsets.UTF_8));
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (fileInputStream != null) {
        fileInputStream.close();
      }
    }
  }

  public void setRequest(Request request) {
    this.request = request;
  }
}
