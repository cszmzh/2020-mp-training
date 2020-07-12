package com.code515.shopping.controller;

import com.code515.shopping.VO.ResultVO;
import com.code515.shopping.service.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/upload")
public class FileController {

    @Autowired
    private UploadFileService uploadFileService;

    /**
     * 1.上传用户头像接口
     */
    @PostMapping("/productImage")
    public ResultVO uploadProductImage(@RequestParam("productImage") MultipartFile file) throws IOException {

        String productImgURL = uploadFileService.uploadProductImage(file);

        ResultVO resultVO = new ResultVO(0,"success");

        resultVO.setData(productImgURL);

        return resultVO;
    }
}
