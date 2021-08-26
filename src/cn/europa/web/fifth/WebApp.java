package cn.europa.web.fifth;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.List;
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
        Map<String, String> servletMap = servletContext.getServletMap();
        Map<String, String> urlMapping = servletContext.getUrlMapping();

        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            WebHandler webHandler = new WebHandler();
            saxParser.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("web.xml"),
                    webHandler);

            List<ServletXml> servletXmlList = webHandler.getServletXmlList();
            for (ServletXml servletXml : servletXmlList) {
                servletMap.put(servletXml.getServletName(), servletXml.getServletClazz());
            }

            List<ServletMappingXml> mappingXmlList = webHandler.getMappingXmlList();
            for (ServletMappingXml mappingXml : mappingXmlList) {
                urlMapping.put(mappingXml.getUrlPattern(), mappingXml.getServletName());
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    public static String getServletClazz(String url) {
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
