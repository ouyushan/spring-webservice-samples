
package org.ouyushan.ws.client.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.ouyushan.cxf package. 
 * &lt;p&gt;An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetAllUserData_QNAME = new QName("http://cxf.ouyushan.org/", "getAllUserData");
    private final static QName _GetAllUserDataResponse_QNAME = new QName("http://cxf.ouyushan.org/", "getAllUserDataResponse");
    private final static QName _GetUser_QNAME = new QName("http://cxf.ouyushan.org/", "getUser");
    private final static QName _GetUserName_QNAME = new QName("http://cxf.ouyushan.org/", "getUserName");
    private final static QName _GetUserNameResponse_QNAME = new QName("http://cxf.ouyushan.org/", "getUserNameResponse");
    private final static QName _GetUserResponse_QNAME = new QName("http://cxf.ouyushan.org/", "getUserResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.ouyushan.cxf
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetAllUserDataResponse }
     * 
     */
    public GetAllUserDataResponse createGetAllUserDataResponse() {
        return new GetAllUserDataResponse();
    }

    /**
     * Create an instance of {@link GetAllUserDataResponse.Map }
     * 
     */
    public GetAllUserDataResponse.Map createGetAllUserDataResponseMap() {
        return new GetAllUserDataResponse.Map();
    }

    /**
     * Create an instance of {@link GetAllUserData }
     * 
     */
    public GetAllUserData createGetAllUserData() {
        return new GetAllUserData();
    }

    /**
     * Create an instance of {@link GetUser }
     * 
     */
    public GetUser createGetUser() {
        return new GetUser();
    }

    /**
     * Create an instance of {@link GetUserName }
     * 
     */
    public GetUserName createGetUserName() {
        return new GetUserName();
    }

    /**
     * Create an instance of {@link GetUserNameResponse }
     * 
     */
    public GetUserNameResponse createGetUserNameResponse() {
        return new GetUserNameResponse();
    }

    /**
     * Create an instance of {@link GetUserResponse }
     * 
     */
    public GetUserResponse createGetUserResponse() {
        return new GetUserResponse();
    }

    /**
     * Create an instance of {@link User }
     * 
     */
    public User createUser() {
        return new User();
    }

    /**
     * Create an instance of {@link GetAllUserDataResponse.Map.Entry }
     * 
     */
    public GetAllUserDataResponse.Map.Entry createGetAllUserDataResponseMapEntry() {
        return new GetAllUserDataResponse.Map.Entry();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllUserData }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetAllUserData }{@code >}
     */
    @XmlElementDecl(namespace = "http://cxf.ouyushan.org/", name = "getAllUserData")
    public JAXBElement<GetAllUserData> createGetAllUserData(GetAllUserData value) {
        return new JAXBElement<GetAllUserData>(_GetAllUserData_QNAME, GetAllUserData.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllUserDataResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetAllUserDataResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://cxf.ouyushan.org/", name = "getAllUserDataResponse")
    public JAXBElement<GetAllUserDataResponse> createGetAllUserDataResponse(GetAllUserDataResponse value) {
        return new JAXBElement<GetAllUserDataResponse>(_GetAllUserDataResponse_QNAME, GetAllUserDataResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUser }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetUser }{@code >}
     */
    @XmlElementDecl(namespace = "http://cxf.ouyushan.org/", name = "getUser")
    public JAXBElement<GetUser> createGetUser(GetUser value) {
        return new JAXBElement<GetUser>(_GetUser_QNAME, GetUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserName }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetUserName }{@code >}
     */
    @XmlElementDecl(namespace = "http://cxf.ouyushan.org/", name = "getUserName")
    public JAXBElement<GetUserName> createGetUserName(GetUserName value) {
        return new JAXBElement<GetUserName>(_GetUserName_QNAME, GetUserName.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserNameResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetUserNameResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://cxf.ouyushan.org/", name = "getUserNameResponse")
    public JAXBElement<GetUserNameResponse> createGetUserNameResponse(GetUserNameResponse value) {
        return new JAXBElement<GetUserNameResponse>(_GetUserNameResponse_QNAME, GetUserNameResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetUserResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://cxf.ouyushan.org/", name = "getUserResponse")
    public JAXBElement<GetUserResponse> createGetUserResponse(GetUserResponse value) {
        return new JAXBElement<GetUserResponse>(_GetUserResponse_QNAME, GetUserResponse.class, null, value);
    }

}
