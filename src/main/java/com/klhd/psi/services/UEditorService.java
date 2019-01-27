package com.klhd.psi.services;

import com.klhd.psi.config.MyProps;
import com.klhd.psi.common.ueditor.ActionEnter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by cheng on 2017/9/26.
 */
@RestController
@RequestMapping("/ueditor")
public class UEditorService {
    @Autowired
    protected ServletContext servletContext;
    @Autowired
    private MyProps myProps;

    @ResponseBody
    @RequestMapping("/controller")
    public void controller(){
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
            request.setCharacterEncoding("utf-8");
            response.setHeader("Content-Type" , "text/html");
            String rootPath = servletContext.getRealPath( "/" );
            rootPath = myProps.getUploadFilePath();
            response.getWriter().write( new ActionEnter( request, rootPath ).exec() );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
