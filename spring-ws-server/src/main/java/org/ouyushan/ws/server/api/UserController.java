package org.ouyushan.ws.server.api;

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

/*    @Autowired
    private UserBizService userBizService;

    @GetMapping("/get")
    public String getUser(){
        return userBizService.getUser("1");
    }*/
}
