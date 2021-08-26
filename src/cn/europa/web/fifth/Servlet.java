package cn.europa.web.fifth;

/**
 * @author fengwen
 * @date 2021-08-26
 */
public abstract class Servlet {

    public void service(Request request, Response response) {
        doGet(request, response);
        doPost(request, response);
    }

    public void doGet(Request request, Response response) {
    }

    public void doPost(Request request, Response response) {
    }

}
