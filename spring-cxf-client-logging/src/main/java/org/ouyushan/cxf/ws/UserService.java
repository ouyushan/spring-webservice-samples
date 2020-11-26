package org.ouyushan.cxf.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.4.1
 * 2020-11-26T16:56:13.393+08:00
 * Generated source version: 3.4.1
 *
 */
@WebService(targetNamespace = "http://ws.cxf.ouyushan.org/", name = "UserService")
@XmlSeeAlso({ObjectFactory.class})
public interface UserService {

    @WebMethod
    @RequestWrapper(localName = "getUserList", targetNamespace = "http://ws.cxf.ouyushan.org/", className = "org.ouyushan.cxf.ws.GetUserList")
    @ResponseWrapper(localName = "getUserListResponse", targetNamespace = "http://ws.cxf.ouyushan.org/", className = "org.ouyushan.cxf.ws.GetUserListResponse")
    @WebResult(name = "return", targetNamespace = "")
    public java.util.List<User> getUserList(

        @WebParam(name = "userId", targetNamespace = "")
        String userId
    );

    @WebMethod
    @RequestWrapper(localName = "getUserName", targetNamespace = "http://ws.cxf.ouyushan.org/", className = "org.ouyushan.cxf.ws.GetUserName")
    @ResponseWrapper(localName = "getUserNameResponse", targetNamespace = "http://ws.cxf.ouyushan.org/", className = "org.ouyushan.cxf.ws.GetUserNameResponse")
    @WebResult(name = "return", targetNamespace = "")
    public String getUserName(

        @WebParam(name = "userId", targetNamespace = "")
        String userId
    );

    @WebMethod
    @RequestWrapper(localName = "getUser", targetNamespace = "http://ws.cxf.ouyushan.org/", className = "org.ouyushan.cxf.ws.GetUser")
    @ResponseWrapper(localName = "getUserResponse", targetNamespace = "http://ws.cxf.ouyushan.org/", className = "org.ouyushan.cxf.ws.GetUserResponse")
    @WebResult(name = "return", targetNamespace = "")
    public User getUser(

        @WebParam(name = "userId", targetNamespace = "")
        String userId
    );
}
