package org.ouyushan.cxf.ws;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This class was generated by Apache CXF 3.4.1
 * 2020-11-26T16:56:13.455+08:00
 * Generated source version: 3.4.1
 *
 */
@WebServiceClient(name = "UserService",
                  wsdlLocation = "classpath:wsdl/user.wsdl",
                  targetNamespace = "http://ws.cxf.ouyushan.org/")
public class UserService_Service extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://ws.cxf.ouyushan.org/", "UserService");
    public final static QName UserServiceImplPort = new QName("http://ws.cxf.ouyushan.org/", "UserServiceImplPort");
    static {
        URL url = null;
        try {
            url = new URL("classpath:wsdl/user.wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(UserService_Service.class.getName())
                .log(java.util.logging.Level.INFO,
                     "Can not initialize the default wsdl from {0}", "classpath:wsdl/user.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public UserService_Service(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public UserService_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public UserService_Service() {
        super(WSDL_LOCATION, SERVICE);
    }

    public UserService_Service(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public UserService_Service(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public UserService_Service(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }




    /**
     *
     * @return
     *     returns UserService
     */
    @WebEndpoint(name = "UserServiceImplPort")
    public UserService getUserServiceImplPort() {
        return super.getPort(UserServiceImplPort, UserService.class);
    }

    /**
     *
     * @param features
     *     A list of {@link WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns UserService
     */
    @WebEndpoint(name = "UserServiceImplPort")
    public UserService getUserServiceImplPort(WebServiceFeature... features) {
        return super.getPort(UserServiceImplPort, UserService.class, features);
    }

}
