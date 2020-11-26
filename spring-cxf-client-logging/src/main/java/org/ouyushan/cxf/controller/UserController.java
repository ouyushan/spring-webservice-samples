package org.ouyushan.cxf.controller;

import org.ouyushan.cxf.service.UserFacadeService;
import org.ouyushan.cxf.ws.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description:
 * @Author: ouyushan
 * @Email: ouyushan@hotmail.com
 * @Date: 2020/11/26 17:01
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserFacadeService userFacadeService;

    @GetMapping("/get")
    public String getUsername(String userId){
        System.out.println("client#userId:" + userId);
        return userFacadeService.getUsername(userId);
    }

    @GetMapping("/getUser")
    public User getUser(String userId){
        System.out.println("client#userId:" + userId);
        return userFacadeService.getUser(userId);
    }

    @GetMapping("/getUserList")
    public List<User> getUserList(String userId){
        System.out.println("client#userId:" + userId);
        return userFacadeService.getUserList(userId);
    }
}
