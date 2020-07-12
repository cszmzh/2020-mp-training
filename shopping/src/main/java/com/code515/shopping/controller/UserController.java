package com.code515.shopping.controller;

import com.code515.shopping.VO.ResultVO;
import com.code515.shopping.entity.User;
import com.code515.shopping.service.UserService;
import com.code515.shopping.service.WxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private WxService wxService;

    @PostMapping("create")
    public ResultVO createUser(@RequestParam("nickName")String nickName,@RequestParam("avatarUrl")String avatarUrl,
                               @RequestParam("gender")String gender, @RequestParam("country")String country,@RequestParam("province")String province,
                               @RequestParam("city")String city,@RequestParam("code")String code) throws IOException {

        String openId = wxService.getOpenIdByCode(code);
        //检验用户是否存在
        if(userService.getByUserOpenId(openId)!=null){
            return new ResultVO(0,"success");
        }

        //创建用户
        User user = new User();
        user.setNickname(nickName);
        user.setAvatarUrl(avatarUrl);
        user.setGender(gender);
        user.setCountry(country);
        user.setProvince(province);
        user.setCity(city);
        user.setOpenId(openId);

        userService.createUser(user);
        return new ResultVO(0,"success");
    }

    @PostMapping("/getUser")
    public ResultVO getUser(@RequestParam("code")String code) throws IOException {
        String openId = wxService.getOpenIdByCode(code);
        User user = userService.getByUserOpenId(openId);

        ResultVO resultVO = new ResultVO(0,"success");
        resultVO.setData(user);
        return resultVO;
    }
}
