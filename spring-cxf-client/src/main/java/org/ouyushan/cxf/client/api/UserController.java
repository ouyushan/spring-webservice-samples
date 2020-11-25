package org.ouyushan.cxf.client.api;

import org.ouyushan.cxf.client.service.UserFacadeService;
import org.ouyushan.cxf.ws.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    private UserFacadeService userBizService;

    @GetMapping("/get")
    public String getUsername(String userId){
        System.out.println("client: userId" + userId);
        return userBizService.getUsername(userId);
    }

    @GetMapping("/getUser")
    public User getUser(String userId){
        System.out.println("client: userId" + userId);
        return userBizService.getUser(userId);
    }

    @GetMapping("/getUserList")
    public List<User> getUserList(String userId){
        System.out.println("client: userId" + userId);
        return userBizService.getUserList(userId);
    }

}
