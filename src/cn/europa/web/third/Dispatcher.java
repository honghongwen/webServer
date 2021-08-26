package cn.europa.web.third;

import java.io.IOException;
import java.net.Socket;

/**
 * @author fengwen
 * @date 2021-08-26
 */
public class Dispatcher implements Runnable {

    private Socket socket;

    private Request request;

    private Response response;

    public Dispatcher(Socket socket) {
        this.socket = socket;
        try {
            request = new Request(socket.getInputStream());
            response = new Response(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
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

        try {
            response.push(200);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
