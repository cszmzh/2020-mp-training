package com.code515.shopping.service.Impl;

import com.code515.shopping.constant.URLConfig;
import com.code515.shopping.service.UploadFileService;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class UploadFileServiceImpl implements UploadFileService {

    private String productImgPath = URLConfig.getRealURL() + "image/";

    @Override
    public String uploadProductImage(MultipartFile upload) throws IOException {

        //判断文件是否存在，不存在则创建
        File file = new File(productImgPath);

        if(!file.exists()){
            file.mkdirs();
        }

        //生成唯一标识符
        UUID uuid = UUID.randomUUID();

        String newCompressName = uuid + "_compress.png";
        String newCompressPath =  productImgPath + newCompressName;

        //保存原图
        upload.transferTo(new File( newCompressPath));

        //压缩图片
        Thumbnails.of(newCompressPath).scale(0.6f).toFile(newCompressPath);

        //返回原图与压缩图相对服务器链接集合
        String compressURL = newCompressPath.substring(newCompressPath.indexOf("static"),newCompressPath.length());

        return compressURL;
    }

    @Override
    public boolean deleteImage(String imageURL) {
        return false;
    }
}
