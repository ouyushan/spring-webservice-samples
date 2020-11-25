package org.ouyushan.cxf.server.component;

import org.springframework.stereotype.Component;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;


/**
 * @Description: 设置回调密码
 * @Author: ouyushan
 * @Email: ouyushan@hotmail.com
 * @Date: 2020/10/19 16:03
 */
@Component
public class ServerPasswordCallback implements CallbackHandler {

    @Override
    public void handle(Callback[] callbacks) {
/*        WSPasswordCallback callback = (WSPasswordCallback) callbacks[0];
        callback.setPassword("Huabei.zdh2019");*/
    }
}