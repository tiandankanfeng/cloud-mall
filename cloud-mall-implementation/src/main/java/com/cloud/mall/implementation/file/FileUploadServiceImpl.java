package com.cloud.mall.implementation.file;

import java.io.IOException;

import com.cloud.mall.domain.workbench.file.FileUploadService;
import com.cloud.mall.infrastructure.dataObject.workbench.file.Img;
import com.cloud.mall.infrastructure.dataObject.workbench.file.Video;
import com.cloud.mall.infrastructure.result.exp.BizException;
import com.cloud.mall.infrastructure.result.exp.BizExceptionProperties;
import com.cloud.mall.infrastructure.utils.OssManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: 夜痕
 * @Date: 2022-02-04 11:54 下午
 * @Description:
 */
@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Autowired
    private OssManager ossManager;

    @Override
    public String uploadImg(final MultipartFile file) throws IOException {
        final String fileName = file.getOriginalFilename();
        final String ext = fileName.substring(fileName.lastIndexOf(".") + 1);

        /**
         * 稍微来统一下结构
         */
        final StringBuilder fileType = new StringBuilder("cloud-mall/");

        fileType.append("img/");

        // 限制文件上传格式
        Img.getImgEnumByType(ext)
            .ifPresentOrElse(img -> {
                fileType.append(img.desc);
            }, () -> {
                throw new BizException(BizExceptionProperties.FILE_NOT_MEET_REQUIREMENT.getMsg());
            });

        return this.ossManager.uploadDocuments(file, fileType.toString());
    }

    @Override
    public String uploadVideo(final MultipartFile file) throws IOException {
        final String fileName = file.getOriginalFilename();
        final String ext = fileName.substring(fileName.lastIndexOf(".") + 1);

        /**
         * 稍微来统一下结构
         */
        final StringBuilder fileType = new StringBuilder("cloud-mall/");

        fileType.append("video/");

        // 限制文件上传格式
        Video.getImgEnumByType(ext)
            .ifPresentOrElse(video -> {
                fileType.append(video.desc);
            }, () -> {
                throw new BizException(BizExceptionProperties.FILE_NOT_MEET_REQUIREMENT.getMsg());
            });

        return this.ossManager.uploadDocuments(file, fileType.toString());
    }
}
