package com.cloud.mall.infrastructure.config;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: 夜痕
 * @Date: 2022-02-05 12:03 上午
 * @Description:
 */
@Slf4j
@Configuration
public class OssManagerConfig {

    @Value("${AliYun.endPoint}")
    private String endPoint;

    @Value("${AliYun.accessKeyId}")
    private String accessKeyId;

    @Value("${AliYun.accessKeySecret}")
    private String accessKeySecret;

    @Value("${AliYun.bucketName}")
    private String bucketName;

    @Bean
    public OSSClient getOssClient() {
        final OSSClient ossClient = new OSSClient(this.endPoint, this.accessKeyId, this.accessKeySecret);
        if (ossClient.doesBucketExist(this.bucketName)) {
            OssManagerConfig.log.info(this.bucketName + "存在，文件存在情况下可进行存储");
        } else { // bucket不存在
            OssManagerConfig.log.info(this.bucketName + "not exist, please set a existing bucket");
            final CreateBucketRequest bucketRequest = new CreateBucketRequest("null");
            bucketRequest.setBucketName(this.bucketName);
            bucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
            // 映射原理
            ossClient.createBucket(bucketRequest);
            ossClient.shutdown();
        }
        return ossClient;
    }
}
