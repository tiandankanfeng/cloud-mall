package com.cloud.mall.infrastructure.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import cn.hutool.core.util.StrUtil;
import com.aliyun.oss.OSSClient;
import com.cloud.mall.domain.workbench.file.model.Img;
import com.cloud.mall.domain.workbench.result.exp.BizException;
import com.cloud.mall.domain.workbench.result.exp.BizExceptionProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: 夜痕
 * @Date: 2022-02-05 12:19 上午
 * @Description:
 */
@Slf4j
@Component
public class OssManager {

    @Value("${AliYun.sufferUrl}")
    private String sufferUrl;
    @Value("${AliYun.bucketName}")
    private String bucketName;

    @Autowired
    private OSSClient ossClient;

    /**
     * 格式化时间
     */
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    /**
     * 在此处理头像及其.lsx文件的上传问题
     * @param multipartFile
     * @return
     * @throws IOException
     */
    public synchronized String uploadDocuments(final MultipartFile multipartFile) throws IOException {
        final long start = System.currentTimeMillis();

        final String fileName = multipartFile.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
        /**
         * 稍微来统一下结构
         */
        String fileType = "cloud-mall/";
        /**
         * 图片上传仅支持 .jpg 和 .png 格式
         * 这里暂时只来处理文件上传的问题.
         * 需处理好默认单次最大文件上传大小限制.
         */
        if (Img.jpg.toString().equals(ext) || Img.png.toString().equals(ext)) {
            fileType += "img/";
            fileType += "jpg".equals(ext) ? "jpg" : "png";
        } else {
            throw new BizException(BizExceptionProperties.FILE_NOT_MEET_REQUIREMENT.getMsg());
        }

        // 获取文件后缀(不重复)
        ext = "." + ext;
        final String uploadFileName = this.getFileName(fileType, ext);

        String url = null;
        this.ossClient.putObject(this.bucketName, uploadFileName, new ByteArrayInputStream(multipartFile.getBytes()));
        url = this.sufferUrl + uploadFileName;
        final long end = System.currentTimeMillis();
        OssManager.log.info("此次文件上传耗费:" + (end - start) / 1000 + "秒");
        OssManager.log.info("--------->>文件上传成功" + url);
        this.ossClient.shutdown();
        return url;
    }

    private String getFileName(String fileType, final String ext) {
        // 按照指定规则进行上传，避免紊乱
        final String date;
        synchronized (OssManager.class) {
            date = OssManager.sdf.format(new Date());
        }
        if (StrUtil.isBlank(fileType)) {
            fileType = "default";
        }
        // 使用UUID
        final String uuid = UUID.randomUUID().toString().replace("-", "");
        final String fileName = fileType + "/" + date + "/" + uuid + ext;
        return fileName;
    }
}
