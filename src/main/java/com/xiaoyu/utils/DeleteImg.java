package com.xiaoyu.utils;

import lombok.extern.slf4j.Slf4j;


import java.io.File;
import java.util.List;

/**
 * @author 小鱼
 * @version 1.0
 * @date 2022/10/15 14:28
 * 删除照片的工具类
 */

@Slf4j
public class DeleteImg {


    private static final String filePath = "D:\\reggie\\";   // 上传的文件路径

    public static void deleteImg(List<String> imgList) {

        imgList.forEach(img -> {
            File file =  new File(filePath + img );
            log.info(filePath + img);
            file.delete();
        });
    }

}
