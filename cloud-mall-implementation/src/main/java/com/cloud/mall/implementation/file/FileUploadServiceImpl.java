package com.cloud.mall.implementation.file;

import java.io.IOException;

import com.cloud.mall.domain.workbench.file.FileUploadService;
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
        return this.ossManager.uploadDocuments(file);
    }
}
