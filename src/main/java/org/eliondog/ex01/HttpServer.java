package org.eliondog.ex01;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * HttpServer
 *
 * @author eliondog - 2021-06-02 23:57:38
 */
public class HttpServer {
  public static final int SUCCESS = 0;

  public static final int FAILED = -1;

  public static final int END = -1;

  public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "webroot";

  private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";

  private boolean shutdown = false;

  public static void main(String[] args) {
    HttpServer httpServer = new HttpServer();
    httpServer.await();
  }

  public void await() {
    final String host = "127.0.0.1";
    final int port = 8080;
    final int backlog = 1;

    ServerSocket serverSocket = null;
    try {
      serverSocket = new ServerSocket(port, backlog, InetAddress.getByName(host));
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(FAILED);
    }

    while (!shutdown) {
      Socket socket = null;
      InputStream inputStream;
      OutputStream outputStream;

      try {
        socket = serverSocket.accept();
        inputStream = socket.getInputStream();
        outputStream = socket.getOutputStream();

        Request request = new Request(inputStream);
        request.parse();

        Response response = new Response(outputStream);
        response.setRequest(request);
        response.sendStaticResource();

        socket.close();

        shutdown = request.getUri().equals(SHUTDOWN_COMMAND);
      } catch (IOException e) {
        e.printStackTrace();
        continue;
      }


    }
  }

}
