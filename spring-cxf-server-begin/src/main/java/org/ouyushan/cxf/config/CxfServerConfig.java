package org.ouyushan.cxf.config;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.ouyushan.cxf.service.UserService;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.xml.ws.Endpoint;

/**
 * @Description: 服务端配置类
 * @Author: ouyushan
 * @Email: ouyushan@hotmail.com
 * @Date: 2020/11/26 14:38
 */

@Configuration
public class CxfServerConfig {

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

        return endpoint;
    }
}
