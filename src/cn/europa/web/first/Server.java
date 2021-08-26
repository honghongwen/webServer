package cn.europa.web.first;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * @author fengwen
 * @date 2021-08-26
 */
public class Server {

    private ServerSocket serverSocket;

    private static final int BUFFER_SIZE = 100;

    private static final String BLANK = " ";
    private static final String RN = "\r\n";

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
            byte[] bytes = new byte[BUFFER_SIZE];
            StringBuilder stringBuilder = new StringBuilder();

            int index = BUFFER_SIZE;
            while (index == BUFFER_SIZE) {
                index = is.read(bytes);
                String data = new String(bytes, 0, index);
                stringBuilder.append(data);
            }

            System.out.println("received the data succeed. data:\n" + stringBuilder.toString());

            response(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 做出响应
     *
     * @param socket
     */
    private void response(Socket socket) {
        String content = "<!DOCTYPE html>" +
                "<html lang=\"zh\">" +
                "    <head>      " +
                "        <meta charset=\"UTF-8\">" +
                "        <title>测试</title>" +
                "    </head>     " +
                "    <body>      " +
                "        <h3>Hello World</h3>" +
                "    </body>     " +
                "</html>";
        StringBuilder response = new StringBuilder();
        // 响应头信息
        response.append("HTTP/1.1").append(BLANK).append("200").append(BLANK).append("OK").append(RN);
        response.append("Content-Length:").append(content.length()).append(RN);
        response.append("Content-Type:text/html").append(RN);
        response.append("Date:").append(new Date()).append(RN);
        response.append("Server:nginx/1.12.1").append(RN);
        response.append(RN);
        // 添加正文
        response.append(content);

        // 输出到浏览器
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            writer.write(response.toString());
            writer.flush();
            writer.close();
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
