package com.klhd.psi.config;

import com.klhd.psi.annotation.ControllerPermission;
import com.klhd.psi.annotation.Permission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@Component
public class AnnotationScanner implements ApplicationListener<ApplicationReadyEvent> {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${spring.profiles}")
    private String profiles;

    /**
     * 初始化方法
     *  if(event.getApplicationContext().getParent() == null){//root application context 没有parent，他就是老大.
     * @param event
     */
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Map<String, Object> beans = event.getApplicationContext().getBeansWithAnnotation(ControllerPermission.class);
        Set<Map.Entry<String, Object>> entries = beans.entrySet();//遍历Bean
        Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
        while(iterator.hasNext()){
            Map.Entry<String, Object> item = iterator.next();
            Class<?> aClass = item.getValue().getClass();
            Method[] methods = aClass.getMethods();

            ControllerPermission annotation = AnnotationUtils.findAnnotation(aClass, ControllerPermission.class);
            System.out.println(annotation.code() + "\t" + annotation.desc());

            for (Method method : methods) {
                Permission permission = AnnotationUtils.findAnnotation(method, Permission.class);
                if (permission != null) {
                    System.out.println("\t" + permission.code() + "\t" + permission.desc());
                }
            }
        }
        logger.info("==== 服务启动完毕，运行环境：{} ====", profiles);
    }

}