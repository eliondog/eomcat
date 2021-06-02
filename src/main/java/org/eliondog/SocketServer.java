package org.eliondog;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

/**
 * ServiceSocket
 *
 * @author eliondog - 2021-06-02 23:17:46
 */
public class SocketServer {

  public static void main(String[] args) {
    final int port = 8080;
    final int backlog = 1;
    final String host = "127.0.0.1";

    try {
      InetAddress inetAddress = InetAddress.getByName(host);
      ServerSocket serverSocket = new ServerSocket(port, backlog, inetAddress);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
