package com.ifinanceweb.db.mybatis.mapper;

import com.ifinanceweb.db.mybatis.entity.EncyclopediaContent;

public interface EncyclopediaContentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EncyclopediaContent record);

    int insertSelective(EncyclopediaContent record);

    EncyclopediaContent selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EncyclopediaContent record);

    int updateByPrimaryKeyWithBLOBs(EncyclopediaContent record);

    int updateByPrimaryKey(EncyclopediaContent record);
}