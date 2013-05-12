package com.ifinanceweb.db.mybatis.mapper;

import java.util.List;

import com.ifinanceweb.db.mybatis.entity.Category;

public interface CategoryMapper {

  List<Category> getCategoryList();

  Category getCategory(String categoryId);

}
