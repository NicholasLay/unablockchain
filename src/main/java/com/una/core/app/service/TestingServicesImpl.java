package com.una.core.app.service;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.una.core.app.DAO.TestingDAO;
import com.una.core.app.DAO.TestingDAOImpl;
import com.una.core.app.model.TestingBean;


@Service
public class TestingServicesImpl implements TestingService{

	private static final Logger logger = LoggerFactory.getLogger(TestingServicesImpl.class);
	
	@Autowired
    private TestingDAO phoneDAO;

    public void setPhoneDAO(TestingDAO phoneDAO) {
        this.phoneDAO = phoneDAO;
    }

	@Override
	@Transactional
	public void addUser(TestingBean testBean) {
		this.phoneDAO.addUser(testBean);	
	}

	@Override
	public void updateUser(TestingBean test) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<TestingBean> getList() {
		// TODO Auto-generated method stub
		return null;
	}
}
