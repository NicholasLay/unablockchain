package com.una.core.app.service;

import java.util.List;

import com.una.core.app.model.TestingBean;


public interface TestingService {
	 
		public void addUser(TestingBean test);
		public void updateUser(TestingBean test);
		public List<TestingBean> getList();
}
