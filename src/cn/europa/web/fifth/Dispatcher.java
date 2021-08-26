package cn.europa.web.fifth;

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
        try {
            String servletClazz = WebApp.getServletClazz(request.getUrl());
            Servlet servlet = (Servlet) Class.forName(servletClazz).newInstance();
            servlet.service(request, response);
            response.push(200);
        } catch (IOException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }
}
