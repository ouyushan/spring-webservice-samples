package org.ouyushan.cxf.server.config;

import org.apache.cxf.Bus;
import org.apache.cxf.ext.logging.LoggingInInterceptor;
import org.apache.cxf.ext.logging.LoggingOutInterceptor;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.wss4j.dom.handler.WSHandlerConstants;
import org.ouyushan.cxf.server.component.ServerPasswordCallback;
import org.ouyushan.cxf.server.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: ouyushan
 * @Email: ouyushan@hotmail.com
 * @Date: 2020/10/14 9:33
 */
@Configuration
public class CxfServerConfig {

    @Value("${server.keystore-alias}")
    private String keystoreAlias;

    @Autowired
    private Bus bus;

    @Autowired
    private UserServiceImpl userService;


    @Bean
    public Endpoint userEndpoint() {
        EndpointImpl endpoint =
                new EndpointImpl(bus, userService);
        endpoint.publish("/user");

        // add the WSS4J IN interceptor to verify the signature on the request message
        endpoint.getInInterceptors().add(serverWssIn());
        // add the WSS4J OUT interceptor to sign the response message
        endpoint.getOutInterceptors().add(serverWssOut());

        // log the request and response messages
        endpoint.getInInterceptors()
                .add(loggingInInterceptor());
        endpoint.getOutInterceptors()
                .add(loggingOutInterceptor());

        return endpoint;
    }

    @Bean
    public Map<String, Object> serverInProps() {
        Map<String, Object> serverInProps = new HashMap<>();
        serverInProps.put(WSHandlerConstants.ACTION,
                WSHandlerConstants.TIMESTAMP + " "
                        + WSHandlerConstants.SIGNATURE);
        serverInProps.put(WSHandlerConstants.SIG_PROP_FILE,
                "server_trust.properties");

        return serverInProps;
    }

    @Bean
    public WSS4JInInterceptor serverWssIn() {
        WSS4JInInterceptor serverWssIn = new WSS4JInInterceptor();
        serverWssIn.setProperties(serverInProps());

        return serverWssIn;
    }

    @Bean
    public Map<String, Object> serverOutProps() {
        Map<String, Object> serverOutProps = new HashMap<>();
        serverOutProps.put(WSHandlerConstants.ACTION,
                WSHandlerConstants.TIMESTAMP + " "
                        + WSHandlerConstants.SIGNATURE);
        serverOutProps.put(WSHandlerConstants.USER, keystoreAlias);
        serverOutProps.put(WSHandlerConstants.PW_CALLBACK_CLASS,
                ServerPasswordCallback.class.getName());
        serverOutProps.put(WSHandlerConstants.SIG_PROP_FILE,
                "server_key.properties");

        return serverOutProps;
    }

    @Bean
    public WSS4JOutInterceptor serverWssOut() {
        WSS4JOutInterceptor serverWssOut = new WSS4JOutInterceptor();
        serverWssOut.setProperties(serverOutProps());

        return serverWssOut;
    }


    @Bean
    public LoggingInInterceptor loggingInInterceptor() {
        return new LoggingInInterceptor();
    }

    @Bean
    public LoggingOutInterceptor loggingOutInterceptor() {
        return new LoggingOutInterceptor();
    }


}