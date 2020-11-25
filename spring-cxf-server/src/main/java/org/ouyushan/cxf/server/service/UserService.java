package org.ouyushan.cxf.server.service;


import org.ouyushan.cxf.server.entity.User;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * @Description:
 * @Author: ouyushan
 * @Email: ouyushan@hotmail.com
 * @Date: 2020/10/14 9:30
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
