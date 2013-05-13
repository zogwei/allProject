package com.ifinanceweb.web.test.structs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ifinanceweb.db.mybatis.mapper.CategoryMapper;

@Service
public class StructService {

	  @Autowired
	  private CategoryMapper accountMapper;
	  
	  @Transactional
	  public void quert() {
		  accountMapper.getCategoryList();
	  }
}
