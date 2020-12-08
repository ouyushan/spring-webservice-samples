package org.ouyushan.cxf.config;

import org.apache.cxf.ext.logging.LoggingInInterceptor;
import org.apache.cxf.ext.logging.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.wss4j.dom.handler.WSHandlerConstants;
import org.ouyushan.cxf.component.ClientPasswordCallback;
import org.ouyushan.cxf.ws.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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


    @Value("${client.keyAlias}")
    private String keyAlias;

    @Value("${client.trustAlias}")
    private String trustAlias;

    @Value("${server.serviceUrl}")
    private String serviceUrl;

    @Bean
    public UserService userService() {

        JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
        jaxWsProxyFactoryBean.setServiceClass(UserService.class);
        jaxWsProxyFactoryBean.setAddress(serviceUrl);

        // log the request and response messages
        jaxWsProxyFactoryBean.getInInterceptors().add(loggingInInterceptor());
        jaxWsProxyFactoryBean.getOutInterceptors().add(loggingOutInterceptor());

        // add the WSS4J OUT interceptor to sign the request message
        jaxWsProxyFactoryBean.getOutInterceptors().add(clientWssOut());
        //jaxWsProxyFactoryBean.getOutInterceptors().add(new SAAJOutInterceptor());
        // add the WSS4J IN interceptor to verify the signature on the response message
        jaxWsProxyFactoryBean.getInInterceptors().add(clientWssIn());
        //jaxWsProxyFactoryBean.getInInterceptors().add(new SAAJInInterceptor());

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


    @Bean
    public Map<String, Object> clientOutProps() {

        Map<String, Object> clientOutProps = new HashMap<>();
        clientOutProps.put(WSHandlerConstants.ACTION, /*WSHandlerConstants.TIMESTAMP + " " +*/ WSHandlerConstants.SIGNATURE);
        clientOutProps.put(WSHandlerConstants.SIGNATURE_USER, keyAlias);
        clientOutProps.put(WSHandlerConstants.PW_CALLBACK_CLASS, ClientPasswordCallback.class.getName());
        // 签名
        clientOutProps.put(WSHandlerConstants.SIG_PROP_FILE, "client_key.properties");

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
        clientInProps.put(WSHandlerConstants.ACTION, /*WSHandlerConstants.TIMESTAMP + " " +*/ WSHandlerConstants.SIGNATURE);
        clientInProps.put(WSHandlerConstants.SIGNATURE_USER, trustAlias);
        clientInProps.put(WSHandlerConstants.PW_CALLBACK_CLASS, ClientPasswordCallback.class.getName());

        // 验签
        clientInProps.put(WSHandlerConstants.SIG_PROP_FILE, "client_trust.properties");

        return clientInProps;
    }

    @Bean
    public WSS4JOutInterceptor clientWssIn() {

        WSS4JOutInterceptor clientWssIn = new WSS4JOutInterceptor();
        clientWssIn.setProperties(clientInProps());

        return clientWssIn;
    }


}
