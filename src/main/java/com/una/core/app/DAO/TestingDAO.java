package com.una.core.app.DAO;

import java.util.List;

import com.una.core.app.model.TestingBean;

public interface TestingDAO {

	public void addUser(TestingBean bean);
    public void updateUser(TestingBean p);
    public List<TestingBean> listUser();
    public void deleteUser(int id);
	
}
