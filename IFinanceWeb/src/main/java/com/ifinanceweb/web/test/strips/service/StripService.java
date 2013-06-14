package com.ifinanceweb.web.test.strips.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class StripService {

	  @Autowired
//	  private CategoryMapper accountMapper;
	  
	  @Transactional
	  public void quert() {
//		  accountMapper.getCategoryList();
	  }
}
