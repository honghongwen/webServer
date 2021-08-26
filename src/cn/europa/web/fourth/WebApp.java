package cn.europa.web.fourth;


import java.util.Map;

/**
 * @author fengwen
 * @date 2021-08-26
 */
public class WebApp {

    private static ServletContext servletContext;

    /**
     * 初始化servlet
     */
    static {
        servletContext = new ServletContext();
        Map<String, Servlet> servletMap = servletContext.getServletMap();
        Map<String, String> urlMapping = servletContext.getUrlMapping();

        servletMap.put("login", new LoginServlet());
        urlMapping.put("/login", "login");
    }

    public static Servlet getServlet(String url) {
        if (url == null || "".equals(url)) {
            return null;
        }

        String servletName = servletContext.getUrlMapping().get(url);
        return servletContext.getServletMap().get(servletName);
    }

    public static ServletContext getServletContext() {
        return servletContext;
    }

    public static void setServletContext(ServletContext servletContext) {
        WebApp.servletContext = servletContext;
    }
}
