package org.ouyushan.ws.client.api;

import org.ouyushan.ws.client.service.UserClient;
import org.ouyushan.ws.client.ws.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: ouyushan
 * @Email: ouyushan@hotmail.com
 * @Date: 2020/11/4 12:49
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserClient userClient;

    @GetMapping("/get")
    public User getUser(String userId) {
        return userClient.getUser(userId);
    }
}
