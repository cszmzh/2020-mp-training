package com.code515.shopping.service;

import java.io.IOException;

public interface WxService {

    /**
     * 1.通过Code获取用户微信open_id
     * @param code
     * @return
     */
    public String getOpenIdByCode(String code) throws IOException;

}
