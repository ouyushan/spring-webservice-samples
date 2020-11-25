package org.ouyushan.ws.client.service;

import org.ouyushan.ws.client.ws.GetUser;
import org.ouyushan.ws.client.ws.GetUserResponse;
import org.ouyushan.ws.client.ws.ObjectFactory;
import org.ouyushan.ws.client.ws.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

import javax.xml.bind.JAXBElement;

/**
 * @Description:
 * @Author: ouyushan
 * @Email: ouyushan@hotmail.com
 * @Date: 2020/11/4 20:17
 */
@Service
public class UserClient {

    @Autowired
    private WebServiceTemplate webServiceTemplate;

    public User getUser(String userId){
        ObjectFactory factory = new ObjectFactory();
        GetUser getUser = factory.createGetUser();
        getUser.setUserId(userId);
        JAXBElement<GetUser> request = factory.createGetUser(getUser);

        JAXBElement<GetUserResponse> response =
                (JAXBElement<GetUserResponse>) webServiceTemplate.marshalSendAndReceive(request);

        return response.getValue().getReturn();
    }
}
