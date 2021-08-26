package cn.europa.web.fifth;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fengwen
 * @date 2021-08-26
 */
public class ServletContext {

    /**
     * {login:LoginServlet,register:RegisterServlet}
     * servletName与servlet的映射
     */
    private Map<String, String> servletMap;

    /**
     * {/login:login}
     * url与servletName的映射
     */
    private Map<String, String> urlMapping;

    public ServletContext() {
        servletMap = new HashMap<>();
        urlMapping = new HashMap<>();
    }

    public Map<String, String> getServletMap() {
        return servletMap;
    }

    public void setServletMap(Map<String, String> servletMap) {
        this.servletMap = servletMap;
    }

    public Map<String, String> getUrlMapping() {
        return urlMapping;
    }

    public void setUrlMapping(Map<String, String> urlMapping) {
        this.urlMapping = urlMapping;
    }
}
