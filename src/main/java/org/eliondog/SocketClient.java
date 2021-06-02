package org.eliondog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * SocketConnect
 *
 * @author  eliondog - 2021-06-02 22:33:55
 */
public class SocketClient {

  public static void main(String[] args) {
    String message = socketProcess();
    System.out.println(message);
  }

  public static String socketProcess() {
    final String host = "127.0.0.1";
    final int port = 8080;
    final boolean autoFlush = true;
    final int capacity = 8096;
    final int millis = 50;

    Socket socket = null;
    OutputStream outputStream;
    PrintWriter printWriter;
    InputStream inputStream;
    InputStreamReader inputStreamReader;
    BufferedReader bufferedReader;
    try {
      socket = new Socket(host, port);
      // 发送字节流
      outputStream = socket.getOutputStream();
      printWriter = new PrintWriter(outputStream, autoFlush);

      // 接收字节流
      inputStream = socket.getInputStream();
      inputStreamReader = new InputStreamReader(inputStream);
      bufferedReader = new BufferedReader(inputStreamReader);

      // 发送Http请求到服务器
      printWriter.println("GET /index.jsp HTTP/1.1");
      printWriter.println("Host: localhost");
      printWriter.println("Connection: close");
      printWriter.println();

      // 读取服务器响应
      StringBuilder responseText = new StringBuilder(capacity);
      boolean loop = true;
      while (loop) {
        if (bufferedReader.ready()) {
          int i = 0;
          while (i != -1) {
            i = bufferedReader.read();
            responseText.append(i);
          }
          loop = false;
        }
        Thread.sleep(millis);
      }
      return responseText.toString();
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    } finally {
      if (socket != null) {
        try {
          socket.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return null;
  }
}
