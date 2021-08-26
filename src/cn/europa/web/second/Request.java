package cn.europa.web.second;


import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author fengwen
 * @date 2021-08-26
 * 请求内容
 */
public class Request {

    private static final String RN = "\r\n";
    private static final String CHARSET = "UTF-8";
    private final String GET = "GET";
    private final String POST = "POST";
    private final int SIZE = 1024;
    /**
     * 请求方式
     */
    private String method;
    /**
     * 请求路由
     */
    private String url;
    /**
     * 输入流
     */
    private InputStream inputStream;
    /**
     * 请求参数
     */
    private Map<String, String> paramMap;
    /**
     * 请求信息
     */
    private String requestInfo;


    private Request() {
        paramMap = new HashMap<>();
    }

    public Request(InputStream inputStream) {
        this();
        this.inputStream = inputStream;
        int index = SIZE;
        StringBuilder builder = new StringBuilder();
        while (index == SIZE) {
            try {
                byte[] bytes = new byte[SIZE];
                index = inputStream.read(bytes);
                String tempStr = new String(bytes);
                builder.append(tempStr);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        requestInfo = builder.toString();

        // 解析信息
        analyzeRequest();
    }

    private void analyzeRequest() {
        if (requestInfo == null || "".equals(requestInfo.trim())) {
            return;
        }

        // 填充请求方式 GET /login?username=europa HTTP/1.1
        String firstLine = requestInfo.substring(0, requestInfo.indexOf(RN));
        String[] split = firstLine.split(" ");
        method = split[0];
        System.out.println("method is:" + method);
        // /login?username=europa&age=18
        url = split[1];

        if (GET.equals(method)) {
            if (url.contains("?")) {
                String[] splitOfQuestion = url.split("\\?");
                // /login
                url = splitOfQuestion[0];
                // username=europa&age=18
                String param = splitOfQuestion[1];
                analyzeRequestParam(param);
            }
        } else if (POST.equals(method)) {
            String param = requestInfo.substring(requestInfo.lastIndexOf(RN)).trim();
            analyzeRequestParam(param);
        } else {
            throw new IllegalArgumentException(String.format("request method [%s] not  support yet.", method));
        }
    }

    /**
     * 解析请求参数
     *
     * @param param eg. username=europa&age=18
     */
    private void analyzeRequestParam(String param) {
        // [username=europa, age=18]
        String[] splitOfAnd = param.split("&");
        for (int i = 0; i < splitOfAnd.length; i++) {
            // [username, europa]..
            String[] splitOfEquals = splitOfAnd[i].split("=");
            paramMap.put(splitOfEquals[0], splitOfEquals.length > 1 ? decode(splitOfEquals[1], CHARSET) : null);
        }
    }

    /**
     * 解码
     *
     * @param splitOfEqual
     * @param charset
     * @return
     */
    private String decode(String splitOfEqual, String charset) {
        try {
            return URLDecoder.decode(splitOfEqual, charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据参数名获取参数值
     *
     * @param name 参数名
     * @return 参数对应值
     */
    public String getParam(String name) {
        return paramMap.get(name);
    }

    public Map<String, String> getParamMap() {
        return paramMap;
    }

    public String getRequestInfo() {
        return requestInfo;
    }

    public String getMethod() {
        return method;
    }
}
