package org.noryar.timer.common;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtil.class);
    // 设置连接超时时间(单位毫秒)
    private static final int CONNECTION_TIMEOUT = 7 * 1000;
    // 设置读数据超时时间(单位毫秒)
    private static final int SOCKET_TIMEOUT = 30 * 1000;
    // 设置从连接池获取连接超时时间(单位毫秒)
    private static final int CONNECTION_REQUEST_TIMEOUT = 60 * 1000;
    // 链接池缓存时间
    private static final int TIME_TO_LIVE = 10 * 60 * 1000;

    private static final HttpClientBuilder httpClientBuilder;
    private static RequestConfig config;
    static {
        // 连接配置
        config = RequestConfig.custom()
                .setConnectTimeout(CONNECTION_TIMEOUT) // 与服务器连接超时时间
                .setSocketTimeout(SOCKET_TIMEOUT) // 服务器响应数据超时时间
                .setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT) // 从连接池获取连接超时时间
                .build();
        // 连接池配置
        PoolingHttpClientConnectionManager poolingHttpClientConnectionManager =
                new PoolingHttpClientConnectionManager(TIME_TO_LIVE, TimeUnit.MILLISECONDS);
        poolingHttpClientConnectionManager.setMaxTotal(50);
        poolingHttpClientConnectionManager.setDefaultMaxPerRoute(10);
        // 创建客户端
        httpClientBuilder = HttpClientBuilder.create();
        httpClientBuilder.setDefaultRequestConfig(config);
        httpClientBuilder.setConnectionManager(poolingHttpClientConnectionManager);
    }
    private static HttpClient getHttpClient() {
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
        return closeableHttpClient;
    }

    public static String get(String url,
                             Map<String, String> params,
                             int socketTimeout) {
        return getUrlForDefinedTimeout(url, HTTP.UTF_8, params, socketTimeout);
    }

    public static String get(String url,
                             Map<String, String> params) {
        return get(url, HTTP.UTF_8, params);
    }

    public static String get(String url,
                             String charset,
                             Map<String, String> params) {
        return getUrlForDefinedTimeout(url, charset, params, CONNECTION_TIMEOUT);
    }

    public static String getUrlForDefinedTimeout(final String url,
                                                 String charset,
                                                 Map<String, String> params,
                                                 int socketTimeout) {
        String returnContent = StringUtils.EMPTY;
        HttpClient client = getHttpClient();
        String urlWithParams = appendParamsToUrl(url, params);
        HttpResponse response = null;
        HttpEntity entity = null;
        try {
            LOGGER.info("[HTTP GET]:{} start.", urlWithParams);
            HttpGet httpGet = getHttpGet(urlWithParams, socketTimeout);
            response = client.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                entity = response.getEntity();
                if (entity != null) {
                    returnContent = EntityUtils.toString(entity, charset);
                }
            } else {
                LOGGER.error("[HTTP GET]:{} error, response: {}",
                        urlWithParams, response.getStatusLine().getReasonPhrase());
            }
            LOGGER.info("[HTTP GET]:{} end.", urlWithParams);
        } catch (Exception e) {
            LOGGER.error("[HTTP GET]:{}. exception:", urlWithParams, e);
        } finally {
            if (entity != null) {
                try {
                    // 释放连接
                    EntityUtils.consume(entity);
                } catch (Exception ex) {
                    LOGGER.error("Close client exception:", ex);
                }
            }
        }
        return returnContent;
    }

    private static HttpGet getHttpGet(String url, int socketTimeout) {
        HttpGet httpGet = new HttpGet(url);
        if (socketTimeout < SOCKET_TIMEOUT) {
            RequestConfig subConfig = RequestConfig.copy(HttpClientUtil.config)
                    .setSocketTimeout(socketTimeout).build();
            httpGet.setConfig(subConfig);
        }
        return httpGet;
    }

    private static String appendParamsToUrl(String url,
                                            Map<String, String> params) {
        StringBuilder sb = new StringBuilder(url);
        if (params != null && params.size() > 0) {
            sb.append("?");
            for (Entry<String, String> entry : params.entrySet()) {
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        int cnt = 0;
        while (true) {
            String response = get("http://www.acssor.org:8081/monitor/ok", null);
            System.out.println(++cnt + "\t" + response);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
