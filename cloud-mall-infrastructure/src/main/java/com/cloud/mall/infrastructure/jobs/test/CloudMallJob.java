package com.cloud.mall.infrastructure.jobs.test;

import java.util.concurrent.atomic.AtomicInteger;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: 夜痕
 * @Date: 2022-02-04 4:18 下午
 * @Description:
 */
@Slf4j
@Component
public class CloudMallJob {

    private final AtomicInteger cnt = new AtomicInteger();

    @XxlJob(value = "cloud-mall-job-test", init = "init", destroy = "destroy")
    public ReturnT<String> execute(final String param) throws Exception {
        // 打印日志
        CloudMallJob.log.info("[execute][定时第 ({}) 次执行]", this.cnt.incrementAndGet());
        // 返回执行成功
        return new ReturnT(200, "调用:{}" + this.cnt.get());
    }

    public void init() throws Exception {
        CloudMallJob.log.info("cloud-mall-job init");
    }

    public void destroy() throws Exception {
        CloudMallJob.log.info("cloud-mall-job destroy");
    }
}
