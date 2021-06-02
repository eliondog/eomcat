package org.eliondog.ex01;

import java.io.IOException;
import java.io.InputStream;

/**
 * Request
 *
 * @author eliondog - 2021-06-03 00:20:57
 */
public class Request {
  private static final int BUFFER_SIZE = 2048;

  private String uri;

  private InputStream inputStream;

  public Request(InputStream inputStream) {
    this.inputStream = inputStream;
  }

  public void parse() {
    StringBuffer request = new StringBuffer(BUFFER_SIZE);

    int i;
    byte[] buffer = new byte[BUFFER_SIZE];
    try {
      i = inputStream.read(buffer);
    } catch (IOException e) {
      e.printStackTrace();
      i = HttpServer.END;
    }

    for (int j = 0; j < i; j++) {
      request.append((char) buffer[j]);
    }

    System.out.println(request);

    uri = parseUri(request.toString());
  }

  private String parseUri(String request) {
    final char space_char = ' ';

    int firstSpace, secondSpace;
    firstSpace = request.indexOf(space_char);
    if (firstSpace != HttpServer.END) {
      secondSpace = request.indexOf(space_char, firstSpace + 1);
      if (secondSpace > firstSpace) {
        return request.substring(firstSpace + 1, secondSpace);
      }
    }
    return null;
  }

  public String getUri() {
    return uri;
  }
}
