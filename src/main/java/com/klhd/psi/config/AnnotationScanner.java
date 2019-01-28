package com.klhd.psi.config;

import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.klhd.psi.annotation.ControllerPermission;
import com.klhd.psi.annotation.Permission;
import com.klhd.psi.dao.PrivilegeDao;
import com.klhd.psi.vo.privilege.PrivilegeVO;
import com.klhd.psi.vo.privilege.PrivilegeVOQuery;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class AnnotationScanner implements ApplicationListener<ApplicationReadyEvent> {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${spring.profiles}")
    private String profiles;
    @Autowired
    private HikariDataSource dataSource;
    @Autowired
    private PrivilegeDao privilegeDao;

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
            logger.error("数据库配置失败");
        }

        List<PrivilegeVO> list = privilegeDao.selectByExample(new PrivilegeVOQuery());
        Map<String, PrivilegeVO> map = Maps.newHashMap();
        for (int i = 0; i < list.size(); i++) {
            PrivilegeVO privilegeVO = list.get(i);
            map.put(privilegeVO.getPrivilegeCode(), privilegeVO);
        }
        int update=0,add=0,del=0;

        Map<String, Object> beans = event.getApplicationContext().getBeansWithAnnotation(ControllerPermission.class);
        Set<Map.Entry<String, Object>> entries = beans.entrySet();//遍历Bean
        Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
        while(iterator.hasNext()){
            Map.Entry<String, Object> item = iterator.next();
            Class<?> aClass = item.getValue().getClass();
            Method[] methods = aClass.getMethods();

            ControllerPermission annotation = AnnotationUtils.findAnnotation(aClass, ControllerPermission.class);
            for (Method method : methods) {
                Permission permission = AnnotationUtils.findAnnotation(method, Permission.class);
                if (permission != null) {
                    String pcode = annotation.code() + "$" + permission.code();
                    PrivilegeVO pri = map.get(pcode);
                    if(pri != null){
                        pri.setTypeDesc(annotation.desc());
                        pri.setMethodDesc(permission.desc());
                        privilegeDao.updateByPrimaryKey(pri);
                        map.remove(pcode);
                        update ++;
                    }else{
                        PrivilegeVO p = new PrivilegeVO();
                        p.setTypeDesc(annotation.desc());
                        p.setTypeCode(annotation.code());
                        p.setMethodDesc(permission.desc());
                        p.setMethodCode(permission.code());
                        p.setPrivilegeCode(pcode);
                        privilegeDao.insert(p);
                        add ++;
                    }
                }
            }
        }
        Iterator<Map.Entry<String, PrivilegeVO>> iterator2 = map.entrySet().iterator();
        while(iterator2.hasNext()){
            Map.Entry<String, PrivilegeVO> next = iterator2.next();
            privilegeDao.deleteByPrimaryKey(next.getValue().getId());
            del ++;
        }
        logger.info("added:{}, updated: {}, deleted: {} ", add, update, del);
        logger.info("==== 服务启动完毕，运行环境：{} ====", profiles);
    }

}