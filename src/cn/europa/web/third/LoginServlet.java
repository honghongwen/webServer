package cn.europa.web.third;

import cn.europa.web.fourth.Request;
import cn.europa.web.fourth.Response;
import cn.europa.web.fourth.Servlet;

/**
 * @author fengwen
 * @date 2021-08-26
 *
 */
public class LoginServlet extends Servlet {

    @Override
    public void doPost(Request request, Response response) {
        response.print("<!DOCTYPE html>")
                .print("<html lang=\"zh\">")
                .print("    <head>      ")
                .print("        <meta charset=\"UTF-8\">")
                .print("        <title>测试</title>")
                .print("    </head>     ")
                .print("    <body>      ")
                .print("        <h3>Hello " + request.getParam("username") + "</h3>")// 获取登陆名
                .print("    </body>     ")
                .print("</html>");
    }
}
