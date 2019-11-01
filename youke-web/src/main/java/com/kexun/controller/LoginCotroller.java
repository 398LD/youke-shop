package com.kexun.controller;

import com.kexun.pojo.Category;
import com.kexun.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class LoginCotroller extends BaseController {

    @Autowired
    private CategoryService categoryService;

    /*跳转登录界面*/
    @GetMapping("login")
    public String login() {
        return "login";
    }

    @GetMapping("index")
    public String index(Model model) {
        List<Category> list = categoryService.findTop10CategoryList();
        model.addAttribute("list", list);
        return "index";
    }


}
