package com.kexun.controller;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;


public class BaseController extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;


    private static Logger log = Logger.getLogger(BaseController.class);


    @ModelAttribute
    public void init(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        this.request = request;
        this.response = response;
        this.session = request.getSession();
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
    }

    protected boolean strIsNull(String str) {
        if (null == str || str.trim().equals("")) return true;
        else if (isDangerStr(str)) return true;
        return false;
    }

    protected boolean isDangerStr(String str) {
        if (str.toLowerCase().indexOf("delete") > -1 || str.toLowerCase().indexOf("select") > -1 ||
                str.toLowerCase().indexOf("from") > -1 || str.toLowerCase().indexOf("drop") > -1
                || str.toLowerCase().indexOf("update") > -1 || str.toLowerCase().indexOf("table") > -1
                || str.toLowerCase().indexOf("where") > -1) return true;
        return false;
    }

}
