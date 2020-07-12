package com.code515.shopping.service.Impl;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.code515.shopping.constant.WxConfig;
import com.code515.shopping.entity.OpenIdJson;
import com.code515.shopping.service.WxService;
import com.code515.shopping.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class WxServiceImpl implements WxService {

    private String appID = WxConfig.getAPPID();
    private String appSecret = WxConfig.getSECRET();

    @Override
    public String getOpenIdByCode(String code) throws IOException {
        String result="";
        try{
            result = HttpUtil.doGet("https://api.weixin.qq.com/sns/jscode2session?appid="
                    + this.appID + "&secret="
                    + this.appSecret + "&js_code="
                    + code
                    + "&grant_type=authorization_code", null);
        }catch (Exception e){
            e.printStackTrace();
        }
        ObjectMapper mapper = new ObjectMapper();
        OpenIdJson openIdJson = mapper.readValue(result,OpenIdJson.class);
        log.info("【获取微信openId】code={}, openId={} ",code,openIdJson.getOpenid());
        return openIdJson.getOpenid();
    }
}
