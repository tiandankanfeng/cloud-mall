package com.cloud.mall.domain.workbench.file;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: 夜痕
 * @Date: 2022-02-04 11:05 下午
 * @Description: 文件上传相关服务
 */
public interface FileUploadService {
    /**
     * 图片上传
     * @param file
     * @return
     * @throws IOException
     */
    String uploadImg(MultipartFile file) throws IOException;

    /**
     * 视频上传
     * @param file
     * @return
     * @throws IOException
     */
    String uploadVideo(MultipartFile file) throws IOException;

}
