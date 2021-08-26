package cn.europa.web.fifth;

/**
 * @author fengwen
 * @date 2021-08-26
 *
 */
public class ServletMappingXml {

    private String servletName;

    private String urlPattern;

    public String getServletName() {
        return servletName;
    }

    public void setServletName(String servletName) {
        this.servletName = servletName;
    }

    public String getUrlPattern() {
        return urlPattern;
    }

    public void setUrlPattern(String urlPattern) {
        this.urlPattern = urlPattern;
    }
}
