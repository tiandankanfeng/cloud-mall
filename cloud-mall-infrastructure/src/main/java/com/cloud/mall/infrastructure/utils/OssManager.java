package com.cloud.mall.infrastructure.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import cn.hutool.core.util.StrUtil;
import com.aliyun.oss.OSSClient;
import com.google.common.base.Stopwatch;
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
    public synchronized String uploadDocuments(final MultipartFile multipartFile, final String fileType) throws IOException {
        final Stopwatch stopwatch = Stopwatch.createStarted();

        // 获取文件后缀(不重复)
        final String ext = "." + fileType;

        /**
         * 需处理好默认单次最大文件上传大小限制.
         */
        final String uploadFileName = this.getFileName(fileType, ext);

        String url = null;
        this.ossClient.putObject(this.bucketName, uploadFileName, new ByteArrayInputStream(multipartFile.getBytes()));
        url = this.sufferUrl + uploadFileName;

        OssManager.log.info("此次文件上传耗费:" +  stopwatch.elapsed(TimeUnit.MILLISECONDS) + "ms");
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
