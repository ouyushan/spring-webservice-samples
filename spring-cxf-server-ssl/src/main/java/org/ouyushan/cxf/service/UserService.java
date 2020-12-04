package org.ouyushan.cxf.service;

import org.ouyushan.cxf.entity.User;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * @Description:
 * @Author: ouyushan
 * @Email: ouyushan@hotmail.com
 * @Date: 2020/11/26 14:42
 */
@WebService(targetNamespace = "http://ws.cxf.ouyushan.org/")
public interface UserService {


    @WebMethod
    public String getUserName(
            /* 指定入参名称为userId 默认为arg0*/
            @WebParam(name = "userId")
                    String userId
    );

    @WebMethod
    public User getUser(

            @WebParam(name = "userId")
                    String userId
    );

    @WebMethod
    public java.util.List<User> getUserList(

            @WebParam(name = "userId")
                    String userId
    );
}
