package org.ouyushan.cxf.server.service;

import com.google.common.collect.Lists;
import org.ouyushan.cxf.server.entity.User;
import org.springframework.stereotype.Service;

import javax.jws.WebService;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author: ouyushan
 * @Email: ouyushan@hotmail.com
 * @Date: 2020/10/14 9:32
 */
@WebService(serviceName = "UserService",//对外发布的服务名
        targetNamespace = "http://ws.cxf.ouyushan.org/",
        endpointInterface = "org.ouyushan.cxf.server.service.UserService"
)
@Service
public class UserServiceImpl implements UserService {

    private static User user1;
    private static User user2;
    private static User user3;

    private static List<User> userList;
    private static Map<String, User> userMap;

    static {

        user1 = new User("1", "tom", "tom@gmail.com");
        user2 = new User("2", "jim", "jim@gmail.com");
        user3 = new User("3", "jack", "jack@gmail.com");

        userList = Lists.newArrayList(user1, user2, user3);
        userMap = userList.stream().collect(Collectors.toMap(User::getUserId, Function.identity()));

    }

    @Override
    public String getUserName(String userId) {
        User user = userMap.getOrDefault(userId, new User(userId, "random", "random@gmail.com"));
        return user.getUserName();
    }

    @Override
    public User getUser(String userId) {
        User user = userMap.getOrDefault(userId, new User(userId, "random", "random@gmail.com"));
        return user;
    }

    @Override
    public List<User> getUserList(String userId) {
        return userList;
    }

}
