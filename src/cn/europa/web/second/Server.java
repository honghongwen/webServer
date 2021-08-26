package cn.europa.web.second;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author fengwen
 * @date 2021-08-26
 */
public class Server {

    private ServerSocket serverSocket;

    public static void main(String[] args) {
        Server server = new Server();
        server.init();
        server.handleRequest();
    }

    /**
     * 处理请求
     */
    private void handleRequest() {
        if (serverSocket == null) {
            throw new IllegalArgumentException("服务器初始化失败");
        }

        try {
            // 阻塞等待请求
            Socket socket = serverSocket.accept();
            InputStream is = socket.getInputStream();
            Request request = new Request(is);

            System.out.println("received the data succeed. data:\n" + request.getRequestInfo());

            Response response = new Response(socket.getOutputStream());
            String content = "<!DOCTYPE html>" +
                    "<html lang=\"zh\">" +
                    "    <head>      " +
                    "        <meta charset=\"UTF-8\">" +
                    "        <title>测试</title>" +
                    "    </head>     " +
                    "    <body>      " +
                    "        <h3>Hello " + request.getParam("username") + "</h3>" +
                    "    </body>     " +
                    "</html>";
            response.print(content);
            response.push(200);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化
     */
    private void init() {
        try {
            serverSocket = new ServerSocket(8080);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
