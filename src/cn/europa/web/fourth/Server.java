package cn.europa.web.fourth;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * @author fengwen
 * @date 2021-08-26
 */
public class Server {

    private ServerSocket serverSocket;

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
        server.handler();
    }

    /**
     * 处理请求
     */
    private void handler() {
        int i = 0;
        while (true) {
            try {
                Dispatcher dispatcher = new Dispatcher(serverSocket.accept());
                Thread thread = new Thread(dispatcher, "handleThread-" + i++);
                thread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 初始化
     */
    private void start() {
        try {
            serverSocket = new ServerSocket(8080);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
