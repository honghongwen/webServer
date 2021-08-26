package cn.europa.web.fifth;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fengwen
 * @date 2021-08-26
 */
public class WebHandler extends DefaultHandler {

    private List<ServletXml> servletXmlList;
    private List<ServletMappingXml> mappingXmlList;

    private ServletXml servletXml;
    private ServletMappingXml servletMappingXml;

    private String beginTag;
    private boolean isMapping;

    @Override
    public void startDocument() throws SAXException {
        servletXmlList = new ArrayList<>();
        mappingXmlList = new ArrayList<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName != null) {
            beginTag = qName;

            if ("servlet".equals(qName)) {
                isMapping = false;
                servletXml = new ServletXml();
            } else if ("servlet-mapping".equals(qName)) {
                isMapping = true;
                servletMappingXml = new ServletMappingXml();
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName != null) {
            if ("servlet".equals(qName)) {
                servletXmlList.add(servletXml);
            } else if ("servlet-mapping".equals(qName)) {
                mappingXmlList.add(servletMappingXml);
            }

            beginTag = null;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (beginTag != null) {
            String str = new String(ch, start, length);

            if (isMapping) {
                if ("servlet-name".equals(beginTag)) {
                    servletMappingXml.setServletName(str);
                } else if ("url-pattern".equals(beginTag)) {
                    servletMappingXml.setUrlPattern(str);
                }
            } else {
                if ("servlet-name".equals(beginTag)) {
                    servletXml.setServletName(str);
                } else if ("servlet-class".equals(beginTag)) {
                    servletXml.setServletClazz(str);
                }
            }
        }
    }

    public List<ServletXml> getServletXmlList() {
        return servletXmlList;
    }

    public void setServletXmlList(List<ServletXml> servletXmlList) {
        this.servletXmlList = servletXmlList;
    }

    public List<ServletMappingXml> getMappingXmlList() {
        return mappingXmlList;
    }

    public void setMappingXmlList(List<ServletMappingXml> mappingXmlList) {
        this.mappingXmlList = mappingXmlList;
    }
}
