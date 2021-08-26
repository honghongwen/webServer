package cn.europa.web.second;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;

/**
 * @author fengwen
 * @date 2021-08-26
 */
public class Response {

    private static final String BLANK = " ";
    private static final String RN = "\r\n";
    /**
     * 输出流
     */
    private BufferedWriter bufferedWriter;
    /**
     * 响应头
     */
    private StringBuilder headInfo;
    /**
     * 响应体
     */
    private StringBuilder contentInfo;
    /**
     * 响应长度
     */
    private int len;


    private Response() {
        headInfo = new StringBuilder();
        contentInfo = new StringBuilder();
        len = 0;
    }

    public Response(OutputStream outputStream) {
        this();
        bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
    }

    /**
     * 返回响应头
     *
     * @param code
     */
    private void setHeadInfo(int code) {
        headInfo.append("HTTP/1.1").append(BLANK).append(code).append(BLANK);
        switch (code) {
            case 200:
                headInfo.append("OK");
                break;
            case 404:
                headInfo.append("NOT FOUND");
                break;
            case 500:
                headInfo.append("SEVER ERROR");
                break;
            default:
                headInfo.append("UNKNOWN CODE");
                break;
        }

        headInfo.append(RN);
        headInfo.append("Content-Length:").append(len).append(RN);
        headInfo.append("Content-Type:text/html").append(RN);
        headInfo.append("Date:").append(new Date()).append(RN);
        headInfo.append("Server:nginx/1.12.1").append(RN);
        headInfo.append(RN);
    }

    /**
     * 写入响应体
     *
     * @param content
     * @return
     */
    public Response print(String content) {
        contentInfo.append(content);
        len += content.getBytes().length;
        return this;
    }

    /**
     * 返回浏览器
     *
     * @param code
     * @throws IOException
     */
    public void push(int code) throws IOException {
        setHeadInfo(code);
        bufferedWriter.append(headInfo.toString());
        bufferedWriter.append(contentInfo.toString());
        bufferedWriter.flush();
    }
}
