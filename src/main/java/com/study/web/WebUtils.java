package com.study.web;

import org.apache.commons.lang3.StringUtils;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class WebUtils {

    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final String METHOD_POST     = "POST";
    private static final String METHOD_GET      = "GET";
    private static final int DEFAULT_CONNECT_TIMEOUT = 60000;
    private static final int DEFAULT_READ_TIMEOUT = 60000;

    private static class DefaultTrustManager implements X509TrustManager {
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }
    }

    private WebUtils() {
    }

    public static String doPost(String url, Map<String,String> params) throws IOException {
        return doPost(url, params, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * POST请求
     * @param url 请求地址
     * @param params 参数
     * @param connectTimeout 连接超时时间
     * @param readTimeout 读取数据超时时间
     * @return 请求结果
     * @throws IOException IOException
     */
    public static String doPost(String url, Map<String, String> params, int connectTimeout,
                                int readTimeout) throws IOException {
        return doPost(url, params, DEFAULT_CHARSET, connectTimeout, readTimeout);
    }

    /**
     * POST请求
     * @param url 请求地址
     * @param params 参数
     * @param charset 编码
     * @param connectTimeout 连接超时时间
     * @param readTimeout 读取数据超时时间
     * @return 请求结果
     * @throws IOException IOException
     */
    public static String doPost(String url, Map<String, String> params, String charset,
                                int connectTimeout, int readTimeout) throws IOException {
        String cType = "application/x-www-form-urlencoded;charset=" + charset;
        String query = buildQuery(params, charset);
        byte[] content = {};
        if (query != null) {
            content = query.getBytes(charset);
        }
        return doPost(url, cType, content, connectTimeout, readTimeout);
    }

    /**
     * POST请求
     * @param url 请求地址
     * @param cType 请求类型
     * @param content 请求内容
     * @param connectTimeout 连接超时时间
     * @param readTimeout 读取数据超时时间
     * @return 请求结果
     * @throws IOException IOException
     */
    public static String doPost(String url, String cType, byte[] content, int connectTimeout,
                                int readTimeout) throws IOException {
        HttpURLConnection conn = null;
        OutputStream out = null;
        String rsp;
        try {
            conn = getConnection(new URL(url), METHOD_POST, cType);
            conn.setConnectTimeout(connectTimeout);
            conn.setReadTimeout(readTimeout);
            out = conn.getOutputStream();
            out.write(content);
            rsp = getResponseAsString(conn);
        } finally {
            if (out != null) {
                out.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rsp;
    }

    /**
     * 文件上传 POST请求
     * @param url 请求地址
     * @param params 文本参数
     * @param fileParams 文件参数
     * @param connectTimeout 连接超时时间
     * @param readTimeout 读取数据超时时间
     * @return 请求结果
     * @throws IOException IOException
     */
    public static String doPost(String url, Map<String, String> params,
                                Map<String, FileItem> fileParams, int connectTimeout,
                                int readTimeout) throws IOException {
        if (fileParams == null || fileParams.isEmpty()) {
            return doPost(url, params, DEFAULT_CHARSET, connectTimeout, readTimeout);
        } else {
            return doPost(url, params, fileParams, DEFAULT_CHARSET, connectTimeout, readTimeout);
        }
    }

    /**
     * 文件上传 POST请求
     * @param url 请求地址
     * @param params 文本参数
     * @param fileParams 文件参数
     * @param charset 编码
     * @param connectTimeout 连接超时时间
     * @param readTimeout 读取数据超时时间
     * @return 请求结果
     * @throws IOException IOException
     */
    public static String doPost(String url, Map<String, String> params,
                                Map<String, FileItem> fileParams, String charset,
                                int connectTimeout, int readTimeout) throws IOException {
        if (fileParams == null || fileParams.isEmpty()) {
            return doPost(url, params, charset, connectTimeout, readTimeout);
        }

        String boundary = System.currentTimeMillis() + ""; // 随机分隔线
        HttpURLConnection conn = null;
        OutputStream out = null;
        String rsp;
        try {
            String cType = "multipart/form-data;boundary=" + boundary + ";charset=" + charset;
            conn = getConnection(new URL(url), METHOD_POST, cType);
            conn.setConnectTimeout(connectTimeout);
            conn.setReadTimeout(readTimeout);
            out = conn.getOutputStream();

            byte[] entryBoundaryBytes = ("\r\n--" + boundary + "\r\n").getBytes(charset);
            // 组装文本请求参数
            Set<Map.Entry<String, String>> textEntrySet = params.entrySet();
            for (Map.Entry<String, String> textEntry : textEntrySet) {
                byte[] textBytes = getTextEntry(textEntry.getKey(), textEntry.getValue(),
                        charset);
                out.write(entryBoundaryBytes);
                out.write(textBytes);
            }
            // 组装文件请求参数
            Set<Map.Entry<String, FileItem>> fileEntrySet = fileParams.entrySet();
            for (Map.Entry<String, FileItem> fileEntry : fileEntrySet) {
                FileItem fileItem = fileEntry.getValue();
                byte[] fileBytes = getFileEntry(fileEntry.getKey(), fileItem.getFileName(),
                        fileItem.getMimeType(), charset);
                out.write(entryBoundaryBytes);
                out.write(fileBytes);
                out.write(fileItem.getContent());
            }
            // 添加请求结束标志
            byte[] endBoundaryBytes = ("\r\n--" + boundary + "--\r\n").getBytes(charset);
            out.write(endBoundaryBytes);
            rsp = getResponseAsString(conn);
        } finally {
            if (out != null) {
                out.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }

        return rsp;
    }

    private static byte[] getTextEntry(String fieldName, String fieldValue, String charset)
            throws IOException {
        StringBuilder entry = new StringBuilder();
        entry.append("Content-Disposition:form-data;name=\"");
        entry.append(fieldName);
        entry.append("\"\r\nContent-Type:text/plain\r\n\r\n");
        entry.append(fieldValue);
        return entry.toString().getBytes(charset);
    }

    private static byte[] getFileEntry(String fieldName, String fileName, String mimeType,
                                       String charset) throws IOException {
        StringBuilder entry = new StringBuilder();
        entry.append("Content-Disposition:form-data;name=\"");
        entry.append(fieldName);
        entry.append("\";filename=\"");
        entry.append(fileName);
        entry.append("\"\r\nContent-Type:");
        entry.append(mimeType);
        entry.append("\r\n\r\n");
        return entry.toString().getBytes(charset);
    }

    /**
     * 执行HTTP GET请求。
     *
     * @param url 请求地址
     * @param params 请求参数
     * @return 响应字符串
     * @throws IOException
     */
    public static String doGet(String url, Map<String, String> params) throws IOException {
        return doGet(url, params, DEFAULT_CHARSET);
    }

    /**
     * 执行HTTP GET请求。
     *
     * @param url 请求地址
     * @param params 请求参数
     * @param charset 字符集，如UTF-8, GBK, GB2312
     * @return 响应字符串
     * @throws IOException IOException
     */
    public static String doGet(String url, Map<String, String> params, String charset)
            throws IOException {
        HttpURLConnection conn = null;
        String rsp;
        try {
            String cType = "application/x-www-form-urlencoded;charset=" + charset;
            String query = buildQuery(params, charset);
            conn = getConnection(buildGetUrl(url, query), METHOD_GET, cType);
            rsp = getResponseAsString(conn);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rsp;
    }

    private static HttpURLConnection getConnection(URL url, String method, String cType)
            throws IOException {
        HttpURLConnection conn = null;
        if ("https".equals(url.getProtocol())) {
            SSLContext ctx;
            try {
                ctx = SSLContext.getInstance("TLS");
                ctx.init(new KeyManager[0], new TrustManager[] { new DefaultTrustManager() },
                        new SecureRandom());
            } catch (Exception e) {
                throw new IOException(e);
            }
            HttpsURLConnection connHttps = (HttpsURLConnection) url.openConnection();
            connHttps.setSSLSocketFactory(ctx.getSocketFactory());
            connHttps.setHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return false;//默认认证不通过，进行证书校验。
                }
            });
            conn = connHttps;
        } else {
            conn = (HttpURLConnection) url.openConnection();
        }

        conn.setRequestMethod(method);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestProperty("Accept", "text/xml,text/javascript,text/html");
        conn.setRequestProperty("User-Agent", "aop-sdk-java");
        conn.setRequestProperty("Content-Type", cType);
        return conn;
    }

    private static URL buildGetUrl(String strUrl, String query) throws IOException {
        URL url = new URL(strUrl);
        if (StringUtils.isEmpty(query)) {
            return url;
        }

        if (StringUtils.isEmpty(url.getQuery())) {
            if (strUrl.endsWith("?")) {
                strUrl = strUrl + query;
            } else {
                strUrl = strUrl + "?" + query;
            }
        } else {
            if (strUrl.endsWith("&")) {
                strUrl = strUrl + query;
            } else {
                strUrl = strUrl + "&" + query;
            }
        }

        return new URL(strUrl);
    }

    public static String buildQuery(Map<String, String> params, String charset) throws IOException {
        return buildQuery(params, true, charset);
    }

    public static String buildQueryWithoutEncode(Map<String, String> params) throws IOException {
        return buildQuery(params, false, null);
    }

    private static String buildQuery(Map<String, String> params, boolean needEncode, String charset)
            throws IOException {
        if (params == null || params.isEmpty()) {
            return null;
        }

        StringBuilder query = new StringBuilder();
        Set<Map.Entry<String, String>> entries = params.entrySet();
        boolean hasParam = false;

        for (Map.Entry<String, String> entry : entries) {
            String name = entry.getKey();
            String value = entry.getValue();
            // 忽略参数名或参数值为空的参数
            if (areNotEmpty(name, value)) {
                if (hasParam) {
                    query.append("&");
                } else {
                    hasParam = true;
                }

                if (needEncode) {
                    value = URLEncoder.encode(value, charset);
                }
                query.append(name).append("=").append(value);
            }
        }

        return query.toString();
    }

    private static boolean areNotEmpty(String... values) {
        boolean result = true;
        if (values == null || values.length == 0) {
            result = false;
        } else {
            for (String value : values) {
                result &= !isEmpty(value);
            }
        }
        return result;
    }

    private static boolean isEmpty(String value) {
        int strLen;
        if (value == null || (strLen = value.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((!Character.isWhitespace(value.charAt(i)))) {
                return false;
            }
        }
        return true;
    }

    public static String getResponseAsString(HttpURLConnection conn) throws IOException {
        String charset = getResponseCharset(conn.getContentType());
        InputStream es = conn.getErrorStream();
        if (es == null) {
            return getStreamAsString(conn.getInputStream(), charset);
        } else {
            String msg = getStreamAsString(es, charset);
            if (StringUtils.isEmpty(msg)) {
                throw new IOException(conn.getResponseCode() + ":" + conn.getResponseMessage());
            } else {
                throw new IOException(msg);
            }
        }
    }

    private static String getStreamAsString(InputStream stream, String charset) throws IOException {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, charset));
            StringWriter writer = new StringWriter();

            char[] chars = new char[256];
            int count = 0;
            while ((count = reader.read(chars)) > 0) {
                writer.write(chars, 0, count);
            }

            return writer.toString();
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }

    private static String getResponseCharset(String cType) {
        String charset = DEFAULT_CHARSET;

        if (!StringUtils.isEmpty(cType)) {
            String[] params = cType.split(";");
            for (String param : params) {
                param = param.trim();
                if (param.startsWith("charset")) {
                    String[] pair = param.split("=", 2);
                    if (pair.length == 2) {
                        if (!StringUtils.isEmpty(pair[1])) {
                            charset = pair[1].trim();
                        }
                    }
                    break;
                }
            }
        }

        return charset;
    }

    public static String decode(String value) {
        return decode(value, DEFAULT_CHARSET);
    }

    public static String encode(String value) {
        return encode(value, DEFAULT_CHARSET);
    }

    public static String decode(String value, String charset) {
        String result = null;
        if (!StringUtils.isEmpty(value)) {
            try {
                result = URLDecoder.decode(value, charset);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }
    public static String encode(String value, String charset) {
        String result = null;
        if (!StringUtils.isEmpty(value)) {
            try {
                result = URLEncoder.encode(value, charset);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    private static Map<String, String> getParamsFromUrl(String url) {
        Map<String, String> map = null;
        if (url != null && url.indexOf('?') != -1) {
            map = splitUrlQuery(url.substring(url.indexOf('?') + 1));
        }
        if (map == null) {
            map = new HashMap<>();
        }
        return map;
    }
    public static Map<String, String> splitUrlQuery(String query) {
        Map<String, String> result = new HashMap<String, String>();

        String[] pairs = query.split("&");
        if (pairs.length > 0) {
            for (String pair : pairs) {
                String[] param = pair.split("=", 2);
                if (param.length == 2) {
                    result.put(param[0], param[1]);
                }
            }
        }

        return result;
    }
}
