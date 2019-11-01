package com.kexun.service;

import com.kexun.mapper.CategoryMapper;
import com.kexun.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*分类服务*/
@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    public List<Category> findTop10CategoryList() {
        return categoryMapper.findTop10CategoryList();
    }


}
