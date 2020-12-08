package org.ouyushan.cxf.config;

import org.apache.cxf.Bus;
import org.apache.cxf.binding.soap.saaj.SAAJInInterceptor;
import org.apache.cxf.binding.soap.saaj.SAAJOutInterceptor;
import org.apache.cxf.ext.logging.LoggingInInterceptor;
import org.apache.cxf.ext.logging.LoggingOutInterceptor;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.wss4j.dom.handler.WSHandlerConstants;
import org.ouyushan.cxf.component.ServerPasswordCallback;
import org.ouyushan.cxf.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.xml.ws.Endpoint;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 服务端配置类
 * @Author: ouyushan
 * @Email: ouyushan@hotmail.com
 * @Date: 2020/12/03 14:38
 */

@Configuration
public class CxfServerConfig {

    @Value("${server.key-alias}")
    private String keystoreAlias;

    @Resource
    private Bus bus;

    @Resource
    private UserService userService;

    @Bean
    public ServletRegistrationBean cxfServlet() {
        return new ServletRegistrationBean(new CXFServlet(), "/ws/*");
    }

    @Bean
    public Endpoint userEndpoint() {
        EndpointImpl endpoint =
                new EndpointImpl(bus, userService);
        endpoint.publish("/user");
        // log the request and response messages
        endpoint.getInInterceptors().add(loggingInInterceptor());
        endpoint.getOutInterceptors().add(loggingOutInterceptor());

        // add the WSS4J IN interceptor to verify the signature on the response message
        endpoint.getInInterceptors().add(serverWssIn());
        // add the WSS4J OUT interceptor to sign the request message
        endpoint.getOutInterceptors().add(serverWssOut());
        return endpoint;
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
     * 以下为服务REQ_IN、REQ_OUT的加解密和验签配置
     *
     * @return
     */
    @Bean
    public Map<String, Object> serverInProps() {
        Map<String, Object> serverInProps = new HashMap<>();
        serverInProps.put(WSHandlerConstants.ACTION,
                WSHandlerConstants.TIMESTAMP + " "
                        + WSHandlerConstants.SIGNATURE);
        serverInProps.put(WSHandlerConstants.SIGNATURE_USER, "webclient");
        serverInProps.put(WSHandlerConstants.PW_CALLBACK_CLASS,
                ServerPasswordCallback.class.getName());
        // 验签
        serverInProps.put(WSHandlerConstants.SIG_PROP_FILE,
                "server_trust.properties");

        return serverInProps;
    }

    @Bean
    public Map<String, Object> serverOutProps() {
        Map<String, Object> serverOutProps = new HashMap<>();
        serverOutProps.put(WSHandlerConstants.ACTION,
                WSHandlerConstants.TIMESTAMP + " "
                        + WSHandlerConstants.SIGNATURE);
        serverOutProps.put(WSHandlerConstants.SIGNATURE_USER, "webserver");
        serverOutProps.put(WSHandlerConstants.PW_CALLBACK_CLASS,
                ServerPasswordCallback.class.getName());
        // 签名
        serverOutProps.put(WSHandlerConstants.SIG_PROP_FILE,
                "server_key.properties");
        return serverOutProps;
    }

    @Bean
    public WSS4JInInterceptor serverWssIn() {
        WSS4JInInterceptor serverWssIn = new WSS4JInInterceptor();
        serverWssIn.setProperties(serverInProps());

        return serverWssIn;
    }

    @Bean
    public WSS4JOutInterceptor serverWssOut() {
        WSS4JOutInterceptor serverWssOut = new WSS4JOutInterceptor();
        serverWssOut.setProperties(serverOutProps());

        return serverWssOut;
    }

}
