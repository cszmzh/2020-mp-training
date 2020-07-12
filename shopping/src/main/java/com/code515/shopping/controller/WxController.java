package com.code515.shopping.controller;

import com.code515.shopping.VO.ResultVO;
import com.code515.shopping.service.WxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/wx")
public class WxController {

    @Autowired
    private WxService wxService;

    /**
     * 1.通过code获取用户open_id
     */
    @PostMapping("/getOpenId")
    public ResultVO getOpenId(@RequestParam("code")String code) throws IOException {
        String openId = wxService.getOpenIdByCode(code);
        ResultVO resultVO = new ResultVO(0, openId);
        return resultVO;
    }
}
