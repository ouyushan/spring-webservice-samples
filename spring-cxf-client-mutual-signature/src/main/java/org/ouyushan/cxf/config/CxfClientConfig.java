package org.ouyushan.cxf.config;

import org.apache.cxf.binding.soap.saaj.SAAJInInterceptor;
import org.apache.cxf.binding.soap.saaj.SAAJOutInterceptor;
import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.configuration.security.FiltersType;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.ext.logging.LoggingInInterceptor;
import org.apache.cxf.ext.logging.LoggingOutInterceptor;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.wss4j.dom.handler.WSHandlerConstants;
import org.ouyushan.cxf.component.ClientPasswordCallback;
import org.ouyushan.cxf.ws.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: ouyushan
 * @Email: ouyushan@hotmail.com
 * @Date: 2020/11/26 16:52
 */
@Configuration
public class CxfClientConfig {

    /**
     * SSL配置参数
     */
    @Value("${client.key-store}")
    private Resource keyStoreResource;

    @Value("${client.trust-store}")
    private Resource trustStoreResource;

    @Value("${client.store-password}")
    private String storePassword;

    @Value("${client.keystore-alias}")
    private String keystoreAlias;


    @Value("${server.serviceUrl}")
    private String serviceUrl;

    @Bean
    public UserService userService() throws Exception {

        JaxWsProxyFactoryBean jaxWsProxyFactoryBean =
                new JaxWsProxyFactoryBean();
        jaxWsProxyFactoryBean.setServiceClass(UserService.class);
        jaxWsProxyFactoryBean.setAddress(serviceUrl);

        // log the request and response messages
        jaxWsProxyFactoryBean.getInInterceptors()
                .add(loggingInInterceptor());
        jaxWsProxyFactoryBean.getOutInterceptors()
                .add(loggingOutInterceptor());

        // add the WSS4J OUT interceptor to sign the request message
        jaxWsProxyFactoryBean.getOutInterceptors().add(clientWssOut());
        jaxWsProxyFactoryBean.getOutInterceptors().add(new SAAJOutInterceptor());
        // add the WSS4J IN interceptor to verify the signature on the response message
        jaxWsProxyFactoryBean.getInInterceptors().add(clientWssIn());
        jaxWsProxyFactoryBean.getInInterceptors().add(new SAAJInInterceptor());

        UserService userService = (UserService) jaxWsProxyFactoryBean.create();

        return userService;
    }

    @Bean
    public LoggingInInterceptor loggingInInterceptor() {
        return new LoggingInInterceptor();
    }

    @Bean
    public LoggingOutInterceptor loggingOutInterceptor() {
        return new LoggingOutInterceptor();
    }

    /**
     * SSL 相关配置
     *
     * @return
     * @throws Exception
     */
    @Bean
    public HTTPConduit httpConduit()
            throws Exception {
        Client client = ClientProxy.getClient(userService());

        HTTPConduit httpConduit = (HTTPConduit) client.getConduit();
        httpConduit.setTlsClientParameters(tlsClientParameters());

        return httpConduit;
    }


    @Bean
    public TLSClientParameters tlsClientParameters()
            throws Exception {
        TLSClientParameters tlsClientParameters =
                new TLSClientParameters();
        // 指定协议
        tlsClientParameters.setSecureSocketProtocol("TLS");
        // should NOT be used in production
        tlsClientParameters.setDisableCNCheck(true);
        // 设置信任证书
        tlsClientParameters.setTrustManagers(trustManagers());
        // 设置keystore
        tlsClientParameters.setKeyManagers(keyManagers());
        tlsClientParameters.setCipherSuitesFilter(cipherSuitesFilter());

        // 会覆盖setDisableCNCheck 或自定义HostnameVerifier
        //tlsClientParameters.setHostnameVerifier(new DefaultHostnameVerifier());

        // 根据证书信息获取指定的域名信息，等效于setHostnameVerifier(new DefaultHostnameVerifier());
        //tlsClientParameters.setUseHttpsURLConnectionDefaultHostnameVerifier(true);

        // 当证书信息包含服务域名时可使用此配置
        //tlsClientParameters.setUseHttpsURLConnectionDefaultSslSocketFactory(true);

        return tlsClientParameters;
    }

    @Bean
    public TrustManager[] trustManagers()
            throws NoSuchAlgorithmException, KeyStoreException,
            IOException {
        TrustManagerFactory trustManagerFactory = TrustManagerFactory
                .getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(trustStore());

        return trustManagerFactory.getTrustManagers();
    }

    /**
     * 测试
     *
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws CertificateException
     * @throws IOException
     * @throws UnrecoverableKeyException
     */
    @Bean
    public KeyManager[] keyManagers()
            throws NoSuchAlgorithmException, KeyStoreException,
            IOException, UnrecoverableKeyException {
        KeyManagerFactory keyManagerFactory = KeyManagerFactory
                .getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore(), storePassword.toCharArray());

        return keyManagerFactory.getKeyManagers();
    }

    @Bean
    public KeyStore trustStore() throws KeyStoreException, IOException {
        KeyStore trustStore = KeyStore.getInstance("JKS");

        InputStream inputStream = trustStoreResource.getInputStream();

        try {
            trustStore.load(inputStream,
                    storePassword.toCharArray());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        return trustStore;
    }

    @Bean
    public KeyStore keyStore() throws KeyStoreException, IOException {
        KeyStore trustStore = KeyStore.getInstance("PKCS12");

        InputStream inputStream = keyStoreResource.getInputStream();

        try {
            trustStore.load(inputStream,
                    storePassword.toCharArray());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        return trustStore;
    }

    @Bean
    public FiltersType cipherSuitesFilter() {
        FiltersType filter = new FiltersType();
        filter.getInclude().add("TLS_ECDHE_RSA_.*");
        filter.getInclude().add("TLS_DHE_RSA_.*");

        return filter;
    }

    @Bean
    public Map<String, Object> clientOutProps() {
        Map<String, Object> clientOutProps = new HashMap<>();
        clientOutProps.put(WSHandlerConstants.ACTION,
                WSHandlerConstants.TIMESTAMP + " "
                        + WSHandlerConstants.SIGNATURE);
        clientOutProps.put(WSHandlerConstants.USER, keystoreAlias);
        clientOutProps.put(WSHandlerConstants.MUST_UNDERSTAND, "false");
        clientOutProps.put(WSHandlerConstants.PW_CALLBACK_CLASS,
                ClientPasswordCallback.class.getName());
        clientOutProps.put(WSHandlerConstants.SIG_PROP_FILE,
                "server_sign.properties");
        clientOutProps.put(WSHandlerConstants.SIG_VER_PROP_FILE,
                "server_sign.properties");

        return clientOutProps;
    }

    @Bean
    public Map<String, Object> clientInProps() {
        Map<String, Object> clientInProps = new HashMap<>();
        clientInProps.put(WSHandlerConstants.ACTION,
                WSHandlerConstants.TIMESTAMP + " "
                        + WSHandlerConstants.SIGNATURE);
        clientInProps.put(WSHandlerConstants.USER, keystoreAlias);
        clientInProps.put(WSHandlerConstants.MUST_UNDERSTAND, "false");
        clientInProps.put(WSHandlerConstants.PW_CALLBACK_CLASS,
                ClientPasswordCallback.class.getName());
        clientInProps.put(WSHandlerConstants.SIG_PROP_FILE,
                "server_sign.properties");
        clientInProps.put(WSHandlerConstants.SIG_VER_PROP_FILE,
                "server_sign.properties");

        return clientInProps;
    }

    @Bean
    public WSS4JOutInterceptor clientWssOut() {
        WSS4JOutInterceptor clientWssOut = new WSS4JOutInterceptor();
        clientWssOut.setProperties(clientOutProps());

        return clientWssOut;
    }

    @Bean
    public WSS4JOutInterceptor clientWssIn() {
        WSS4JOutInterceptor clientWssIn = new WSS4JOutInterceptor();
        clientWssIn.setProperties(clientInProps());

        return clientWssIn;
    }

}
