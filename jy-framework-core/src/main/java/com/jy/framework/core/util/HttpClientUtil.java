package com.jy.framework.core.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jy.framework.core.http.SSLClient;

/**
 * Http 请求工具类
 * @author zhanglj
 *
 */
public class HttpClientUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtil.class);
	private static final String CONTENT_TYPE_TEXT_JSON = "text/json";
	 private RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(10000).setSocketTimeout(10000)
	            .setConnectionRequestTimeout(10000).build();
    private volatile static HttpClientUtil instance = null;
    
    private HttpClientUtil() {
    }

    public static HttpClientUtil getInstance() {
        if (instance == null) {
            synchronized (HttpClientUtil.class) {
                if (instance == null) {
                    instance = new HttpClientUtil();
                }
            }
        }
        return instance;
    }
    
    /***
     * HTTP或HTTPS Post请求
     * @param httpsFlag     是否支持https
     * @param keyStorePath  证书路径
     * @param params        请求参数
     * @param traceId       流水号ID
     * @param cmdIdType     操作类型
     * @param url           请求地址
     * @return
     */
    public String doPost(boolean httpsFlag, String keyStorePath, String keyStorePwd, Map<String, String> params, String traceId, String url){
    	if (httpsFlag) {
    			return doPostBySingleSSL(keyStorePath, keyStorePwd, params, traceId,url);
    	} else {
    		return doHttpPost(params,traceId,url);
    	}
    }
    
    /**
     * HTTPS模拟请求
     * @param keyStorePath
     * @param keyStorepass
     * @param params
     * @param traceId
     * @param url
     * @return
     */
    public String doPostBySingleSSL(String keyStorePath, String keyStorepass, Map<String, String> params, String traceId, String url){
		HttpClient httpClient = null;
		String result = null;
		String exception = "请求远程URL地址网络异常";
		HttpResponse response = null;
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Content-Type", "application/json;charset=UTF-8;Content-Encoding=UTF-8");
        httpPost.setConfig(requestConfig);

        try {
            String js = JSON.toJSONString(params);
            LOGGER.info("http request traceId:{}  ------ 参数:{}", traceId, js);
        	StringEntity se = new StringEntity(js,"UTF-8");
            se.setContentEncoding("UTF-8");
            se.setContentType(CONTENT_TYPE_TEXT_JSON);
            httpPost.setEntity(se);
            httpClient = SSLClient.getInstance(keyStorePath,keyStorepass).getSSLInsecureClient();
            response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            	LOGGER.error("网络响应异常, http状态响应码:{}" + response.getStatusLine().getStatusCode());
                exception = "网络响应异常, http状态响应码:" + response.getStatusLine().getStatusCode();
        		throw new RuntimeException(exception);
            }
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "UTF-8");
            EntityUtils.consume(entity);
            LOGGER.info("http response traceId:{} ------ 结果:{}", traceId, result);
        } catch (Exception e) {
            LOGGER.error("http exception traceId:{} ------异常:{}", traceId, JSON.toJSONString(params), e);
            throw new RuntimeException(exception, e);
        } finally {
            try {
                // 关闭连接,释放资源
                if (response != null) {
                    EntityUtils.consume(response.getEntity());
                }
            } catch (IOException e) {
                LOGGER.error("释放链接异常", e);
            }
        }
        return result;
	}
    
    /***
     * HTTP 模拟请求
     * @param params
     * @param traceId
     * @param url
     * @return
     */
    public String doHttpPost(Map<String, String> params, String traceId, String url) {
        String result = null;
        String exception = "请求远程URL地址网络异常";
        CloseableHttpClient httpClient = createClient();
        HttpPost httpPost = new HttpPost(url);
        //流方式- begin
        JSONObject jsonObject = new JSONObject();
        jsonObject.putAll(params);
        BasicHttpEntity request = new BasicHttpEntity();
        InputStream inputStream = new ByteArrayInputStream(jsonObject.toJSONString().getBytes());
        request.setContent(inputStream);
        httpPost.setEntity(request);
        //流方式- end
        CloseableHttpResponse response = null;
        LOGGER.info("http request traceId:{}  ------ 参数:{}", traceId, JSON.toJSONString(params));
        try {
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                LOGGER.error("网络响应异常, http状态响应码:{}" + response.getStatusLine().getStatusCode());
                exception = "网络响应异常, http状态响应码:" + response.getStatusLine().getStatusCode();
        		throw new RuntimeException(exception);
            }

            result = EntityUtils.toString(entity, "UTF-8");
            EntityUtils.consume(entity);
            LOGGER.info("http response traceId:{}  ------ 结果:{}", traceId, response);
        } catch (Exception e) {
            LOGGER.error("http exception traceId:{}  ------ 异常:{}", traceId, e);
            throw new RuntimeException(exception, e);
        } finally {
            try {
                // 关闭连接,释放资源
                if (response != null) {
                    EntityUtils.consume(response.getEntity());
                    response.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                LOGGER.error("释放链接异常", e);
            }
        }
        return result;
    }
    
    private CloseableHttpClient createClient() {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        // 将最大连接数增加到100
        cm.setMaxTotal(100);
        // 目前只有一个路由，因此让他等于最大值
        cm.setDefaultMaxPerRoute(cm.getMaxTotal());
        return HttpClients.custom().setConnectionManager(cm).build();
    }
    
}
