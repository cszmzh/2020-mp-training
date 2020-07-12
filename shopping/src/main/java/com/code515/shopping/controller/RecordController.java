package com.code515.shopping.controller;

import com.code515.shopping.VO.ResultVO;
import com.code515.shopping.entity.Record;
import com.code515.shopping.entity.User;
import com.code515.shopping.service.RecordService;
import com.code515.shopping.service.UserService;
import com.code515.shopping.service.WxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/record")
public class RecordController {

    @Autowired
    private RecordService recordService;

    @Autowired
    private WxService wxService;

    @Autowired
    private UserService userService;

    /**
     * 1.获取所有历史记录
     */
    @PostMapping("/getAll")
    public ResultVO getAll(@RequestParam("code")String code, @RequestParam("prodId")Integer productId) throws IOException {

        //获取用户信息
        String openId = wxService.getOpenIdByCode(code);
        User user = userService.getByUserOpenId(openId);

        List<Record> recordList = recordService.getAllByUserIdAndProductId(user.getUserId(), productId);

        ResultVO resultVO = new ResultVO(0,"success");
        resultVO.setData(recordList);
        return resultVO;
    }

}
