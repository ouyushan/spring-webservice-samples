package org.ouyushan.cxf.config;

import org.apache.cxf.ext.logging.LoggingInInterceptor;
import org.apache.cxf.ext.logging.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.ouyushan.cxf.ws.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @Author: ouyushan
 * @Email: ouyushan@hotmail.com
 * @Date: 2020/11/26 16:52
 */
@Configuration
public class CxfClientConfig {


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

}
