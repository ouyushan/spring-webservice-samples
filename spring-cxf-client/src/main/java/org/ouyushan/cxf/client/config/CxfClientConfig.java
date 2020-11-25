package org.ouyushan.cxf.client.config;

import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.configuration.security.FiltersType;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.ext.logging.LoggingInInterceptor;
import org.apache.cxf.ext.logging.LoggingOutInterceptor;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.wss4j.dom.handler.WSHandlerConstants;
import org.ouyushan.cxf.client.component.ClientPasswordCallback;
import org.ouyushan.cxf.ws.UserService;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import javax.net.ssl.*;
import java.io.IOException;
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
 * @Date: 2020/10/14 9:33
 */
@Configuration
public class CxfClientConfig {

    @Value("${server.service.address}")
    private String address;


    /**
     * SSL配置参数
     */
    @Value("${server.service.trust-store}")
    private Resource trustStoreResource;

    @Value("${server.service.trust-store-password}")
    private String trustStorePassword;
    @Bean
    public UserService userService() throws Exception {

        JaxWsProxyFactoryBean jaxWsProxyFactoryBean =
                new JaxWsProxyFactoryBean();
        jaxWsProxyFactoryBean.setServiceClass(UserService.class);
        jaxWsProxyFactoryBean.setAddress(address);

/*        // add the WSS4J OUT interceptor to sign the request message
        jaxWsProxyFactoryBean.getOutInterceptors().add(clientWssOut());
        // add the WSS4J IN interceptor to verify the signature on the response message
        jaxWsProxyFactoryBean.getInInterceptors().add(clientWssIn());
*/
        // log the request and response messages
        jaxWsProxyFactoryBean.getInInterceptors()
                .add(loggingInInterceptor());
        jaxWsProxyFactoryBean.getOutInterceptors()
                .add(loggingOutInterceptor());

        UserService userService = (UserService) jaxWsProxyFactoryBean.create();

        return userService;
    }

    /*
        @Bean
        public Map<String, Object> clientOutProps() {
            Map<String, Object> clientOutProps = new HashMap<>();
            clientOutProps.put(WSHandlerConstants.ACTION,
                    WSHandlerConstants.TIMESTAMP + " "
                            + WSHandlerConstants.SIGNATURE);
            clientOutProps.put(WSHandlerConstants.USER, keystoreAlias);
            clientOutProps.put(WSHandlerConstants.PW_CALLBACK_CLASS,
                    ClientPasswordCallback.class.getName());
            clientOutProps.put(WSHandlerConstants.SIG_PROP_FILE,
                    "client_key.properties");

            return clientOutProps;
        }

        @Bean
        public WSS4JOutInterceptor clientWssOut() {
            WSS4JOutInterceptor clientWssOut = new WSS4JOutInterceptor();
            clientWssOut.setProperties(clientOutProps());

            return clientWssOut;
        }

        @Bean
        public Map<String, Object> clientInProps() {
            Map<String, Object> clientInProps = new HashMap<>();
            clientInProps.put(WSHandlerConstants.ACTION,
                    WSHandlerConstants.TIMESTAMP + " "
                            + WSHandlerConstants.SIGNATURE);
            clientInProps.put(WSHandlerConstants.SIG_PROP_FILE,
                    "client_trust.properties");

            return clientInProps;
        }

        @Bean
        public WSS4JInInterceptor clientWssIn() {
            WSS4JInInterceptor clientWssIn = new WSS4JInInterceptor();
            clientWssIn.setProperties(clientInProps());

            return clientWssIn;
        }

    **/
    @Bean
    public LoggingInInterceptor loggingInInterceptor() {
        return new LoggingInInterceptor();
    }

    @Bean
    public LoggingOutInterceptor loggingOutInterceptor() {
        return new LoggingOutInterceptor();
    }


    @Bean
    public HTTPConduit userConduit()
            throws Exception {
        Client client = ClientProxy.getClient(userService());

        HTTPConduit userConduit = (HTTPConduit) client.getConduit();
        userConduit.setTlsClientParameters(tlsClientParameters());

        return userConduit;
    }

    @Bean
    public TLSClientParameters tlsClientParameters()
            throws NoSuchAlgorithmException, KeyStoreException,
            CertificateException, IOException {
        TLSClientParameters tlsClientParameters =
                new TLSClientParameters();
        tlsClientParameters.setSecureSocketProtocol("TLS");
        // should NOT be used in production 客户端/服务端部署在同一台服务器上时屏蔽CN检查，或自定义实现setHostnameVerifier，不然会报错
        tlsClientParameters.setDisableCNCheck(true);
        //tlsClientParameters.setHostnameVerifier(new CustomHostnameVerifier());

        // SSL证书认证  重要
        tlsClientParameters.setTrustManagers(trustManagers());

        tlsClientParameters.setCipherSuitesFilter(cipherSuitesFilter());

        return tlsClientParameters;
    }


    @Bean
    public TrustManager[] trustManagers()
            throws NoSuchAlgorithmException, KeyStoreException,
            CertificateException, IOException {
        TrustManagerFactory trustManagerFactory = TrustManagerFactory
                .getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(trustStore());

        return trustManagerFactory.getTrustManagers();
    }

    @Bean
    public KeyStore trustStore() throws KeyStoreException,
            NoSuchAlgorithmException, CertificateException, IOException {
        KeyStore trustStore = KeyStore.getInstance("PKCS12");
        trustStore.load(trustStoreResource.getInputStream(),
                trustStorePassword.toCharArray());

        return trustStore;
    }

    @Bean
    public FiltersType cipherSuitesFilter() {
        FiltersType filter = new FiltersType();
        filter.getInclude().add("TLS_ECDHE_RSA_.*");
        filter.getInclude().add("TLS_DHE_RSA_.*");

        return filter;
    }


}