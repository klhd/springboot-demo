package com.klhd.psi.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component
public class ScheduledTask {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 每间隔10秒输出时间
     */
//    @Scheduled(fixedRate = 10000)
    public void logTime() {
        logger.info("定时任务，现在时间：" + new SimpleDateFormat("YYYY-MM-DD HH:mm:ss").format(System.currentTimeMillis()));
    }

    /**
     * 每隔5秒执行一次
     */
//    @Scheduled(cron="*/5 * * * * *")
    public void test1(){
        logger.info("cron任务，现在时间：" + new SimpleDateFormat("YYYY-MM-DD HH:mm:ss").format(System.currentTimeMillis()));
    }

}
