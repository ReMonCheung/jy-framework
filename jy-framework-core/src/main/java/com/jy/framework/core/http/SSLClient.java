package com.jy.framework.core.http;

import org.apache.commons.codec.Charsets;
import org.apache.http.Consts;
import org.apache.http.client.HttpClient;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.util.StringUtils;

import javax.net.ssl.SSLContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * HttpClient连接池管理
 * @author zhanglj
 *
 */
public class SSLClient {
	
	private static SSLClient instance = null;
	private static SSLContext sslContext = null;
	private static PoolingHttpClientConnectionManager connManager = null;

	private static ConnectionConfig connConfig = ConnectionConfig.custom().setCharset(Charsets.toCharset(Consts.UTF_8)).build();
	private static SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(100000).build();
	private static RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.<ConnectionSocketFactory> create();
	private static ConnectionSocketFactory plainSF = new PlainConnectionSocketFactory();
	
	private SSLClient() {
    }

	
	@SuppressWarnings("deprecation")
	public static SSLClient getInstance(String keyStorePath, String keyStorepass) throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException, IOException, CertificateException {
		if (instance == null) {
			synchronized (SSLClient.class) {
				if (instance == null) {
					if(StringUtils.isEmpty(keyStorePath) || StringUtils.isEmpty(keyStorePath)){
						KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
						sslContext = SSLContexts.custom().useTLS()
								.loadTrustMaterial(trustStore, new AnyTrustStrategy())
								.build();
						LayeredConnectionSocketFactory sslSF = new SSLConnectionSocketFactory(
								sslContext,
								SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

						registryBuilder.register("http", plainSF);
						registryBuilder.register("https", sslSF);
					}else {
						KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
						String path = SSLClient.class.getResource(keyStorePath).getFile();
						FileInputStream instream = new FileInputStream(new File(path));
						trustStore.load(instream, keyStorepass.toCharArray());

						SSLContext sslContext = SSLContexts.custom().useTLS()
								.loadTrustMaterial(trustStore, new TrustSelfSignedStrategy())
								.build();
						LayeredConnectionSocketFactory sslSF = new SSLConnectionSocketFactory(
								sslContext,
								SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
						registryBuilder.register("http", plainSF);
						registryBuilder.register("https", sslSF);
					}

					Registry<ConnectionSocketFactory> registry = registryBuilder.build();
					 connManager = new PoolingHttpClientConnectionManager(registry);
					//设置连接管理器
					connManager.setDefaultConnectionConfig(connConfig);
					connManager.setDefaultSocketConfig(socketConfig);
					connManager.setMaxTotal(3000);
					instance = new SSLClient();
				}
			}
		}
		
		return instance;
	}
	
	
    /**
     * 
     * 信任所有证书
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @return
     */
    public HttpClient getSSLInsecureClient(){
		return HttpClientBuilder.create().setConnectionManager(connManager).build();
    }
    

    static class AnyTrustStrategy implements TrustStrategy{
    	@Override
    	public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
    		return true;
    	}
    	
    }
    
}

