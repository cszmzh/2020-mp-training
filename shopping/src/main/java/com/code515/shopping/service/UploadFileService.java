package com.code515.shopping.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface UploadFileService {

    /**上传商品图片*/
    String uploadProductImage(MultipartFile upload) throws IOException;

    /**删除指定图片文件*/
    public boolean deleteImage(String imageURL);
}
