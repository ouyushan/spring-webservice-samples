package org.ouyushan.cxf.component;

import org.apache.wss4j.common.ext.WSPasswordCallback;
import org.springframework.stereotype.Component;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;


/**
 * @Description: 设置回调密码
 * @Author: ouyushan
 * @Email: ouyushan@hotmail.com
 * @Date: 2020/12/03 16:03
 */
@Component
public class ServerPasswordCallback implements CallbackHandler {

    /**
     * 自定义实现业务逻辑
     *
     * @param callbacks
     */
    @Override
    public void handle(Callback[] callbacks) {
        WSPasswordCallback callback = (WSPasswordCallback) callbacks[0];
        callback.setPassword("ouyushan.pass");
    }
}