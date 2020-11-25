package org.ouyushan.cxf.client.config;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * @Description:
 * @Author: ouyushan
 * @Email: ouyushan@hotmail.com
 * @Date: 2020/11/10 15:16
 */
public class CustomHostnameVerifier implements HostnameVerifier {
    @Override
    public boolean verify(String hostname, SSLSession sslSession) {
/*        if (hostname.equals("127.0.0.1")){
            return true;
        }*/
        return true;
    }
}
