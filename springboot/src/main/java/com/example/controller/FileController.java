package com.example.controller;

import com.example.common.Result;
import cn.hutool.core.io.FileUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

@RestController
@RequestMapping("/files")
public class FileController {

    private static final String ROOT_PATH = System.getProperty("user.dir") + "/files";

    // 文件上传
    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();  // 文件名
        long flag = System.currentTimeMillis();  // 时间戳
        String fileName = flag + "_" + originalFilename; // 上传到服务器的文件名

        File finalFile = new File(ROOT_PATH + "/" + fileName); // 文件在存盘存储的对象
        if (!finalFile.getParentFile().exists()) { // 如果文件夹不存在，则创建
            finalFile.getParentFile().mkdirs(); // 创建文件夹
        }
        file.transferTo(finalFile);

        // 返回文件的url
        String url = "http://localhost:8888/files/download?fileName=" + fileName;
        return Result.success(url);
    }

    // 文件下载
    @GetMapping("/download")
    public void download(String fileName, HttpServletResponse response) throws IOException {
        File file = new File(ROOT_PATH + "/" + fileName);  // 文件在存盘存储的对象
        ServletOutputStream os = response.getOutputStream();
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        response.setContentType("application/octet-stream");
        FileUtil.writeToStream(file, os);
        os.flush();
        os.close();
    }
}
