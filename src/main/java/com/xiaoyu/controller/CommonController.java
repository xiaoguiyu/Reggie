package com.xiaoyu.controller;

import com.xiaoyu.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

/**
 * @author 小鱼
 * @version 1.0
 * @date 2022/10/9 20:59
 * 公共的请求处理
 */

@Slf4j
@RestController
@RequestMapping("/common")
@SuppressWarnings({"all"})
public class CommonController {

    @Value("${reggie.path}")  // 从yaml 配置中读取
    private String filePath;   // 上传的文件路径

    /**
     * 文件上传
     * @param file 上传的文件
     * @return 返回上传的原文件名
     */
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) {  // 这里的file是一个临时文件, 处理完此次请求就会被清理

        // 获取源文件名
        String originalFilename = file.getOriginalFilename();
        // 获取文件的后缀(.jpg)
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        // 使用UUID来生成文件名, 防止文件重名来覆盖文件
        String fileName = UUID.randomUUID() + suffix;

        File dir = new File(filePath);
        // 判断文件的路径是否存在, 如果不存在,则创建这个文件夹
        if (!dir.exists()) {
            dir.mkdir();
        }

        try {
            // 将临时文件转存到服务端的磁盘中, 路径为application.yaml中的配置, 文件名为uuid所生成
            file.transferTo(new File(filePath + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 返回原文件名
        return Result.success(fileName);
    }


    /**
     * 文件在浏览器的显示(文件下载)
     * @param name 回显的文件名
     * @param response 需要使用输出流, 因此需要response
     */
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) {

        try {
            // 获取带缓冲的输入流
            BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(filePath + name));
            // 获取输出流
            BufferedOutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            // 设置响应类型为图片格式
            response.setContentType("/image/jpeg");

            int len;
            byte[] bytes = new byte[1024];
            // 边读边写, 将文件写入到浏览器中
            while ((len = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes,0, len);
            }
            outputStream.flush();

            // 关闭资源
            inputStream.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
