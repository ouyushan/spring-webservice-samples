package org.ouyushan.cxf.service;

import org.ouyushan.cxf.ws.User;
import org.ouyushan.cxf.ws.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description:
 * @Author: ouyushan
 * @Email: ouyushan@hotmail.com
 * @Date: 2020/11/26 17:00
 */
@Service
public class UserFacadeService {

    /**
     * 数据接入服务
     */
    @Resource
    private UserService userService;

    public String getUsername(String userId) {
        String userName = userService.getUserName(userId);
        return userName;
    }

    public User getUser(String userId) {

        User user = userService.getUser(userId);

        return user;
    }

    public List<User> getUserList(String userId) {

        List<User> userList = userService.getUserList(userId);

        return userList;
    }
}
