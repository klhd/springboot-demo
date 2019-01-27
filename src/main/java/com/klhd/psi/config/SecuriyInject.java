package com.klhd.psi.config;

import com.google.common.io.Files;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

@Component
public class SecuriyInject implements ApplicationListener<ApplicationReadyEvent> {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private HikariDataSource dataSource;

    /**
     * 初始化方法
     *  if(event.getApplicationContext().getParent() == null){//root application context 没有parent，他就是老大.
     * @param event
     */
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        try {
            List<String> list = Files.readLines(new File("D://mysql.key"), Charset.forName("UTF-8"));
            dataSource.setJdbcUrl(list.get(0));
            dataSource.setUsername(list.get(1));
            dataSource.setPassword(list.get(2));
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("数据库配置失败");
        }

    }

}