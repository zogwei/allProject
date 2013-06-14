package com.ifinanceweb.db.mybatis.mapper;

import com.ifinanceweb.db.mybatis.entity.EncyclopediaCatalog;

public interface EncyclopediaCatalogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EncyclopediaCatalog record);

    int insertSelective(EncyclopediaCatalog record);

    EncyclopediaCatalog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EncyclopediaCatalog record);

    int updateByPrimaryKey(EncyclopediaCatalog record);
}