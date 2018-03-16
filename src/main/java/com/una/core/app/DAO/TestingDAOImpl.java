package com.una.core.app.DAO;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.una.core.app.model.TestingBean;


@Repository
public class TestingDAOImpl implements TestingDAO{
	
	private static final Logger logger = LoggerFactory.getLogger(TestingDAOImpl.class);
	
	 private SessionFactory sessionFactory;

	    public void setSessionFactory(SessionFactory sf) {
	        this.sessionFactory = sf;
	    }

	    @Override
	    public void addUser(TestingBean p) {
	    	Session session = null;
			Transaction tx = null;
			
			try {
				System.out.println("INITIATING PERSISTANCE");
				
				SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
				session = sessionFactory.openSession();
			    tx = session.beginTransaction();
			    
			    System.out.println("LOADING HIBERNATE");
				TestingBean addUser = new TestingBean();
				addUser.setEmployeeName(p.getEmployeeName());
				addUser.setEmployeeRole(p.getEmployeeRole());
				
				session.save(addUser);
				
				
				System.out.println("Succesful");
				
				session.flush(); 
			    
			    tx.commit();
			} catch(Exception e) {
			    tx.rollback();
			}finally {
				if(session != null) {
					session.close();
					
			        logger.info(" saved successfully, for user=" + p.getEmployeeName());
				}
			}
	    }

	    @Override
	    public void updateUser(TestingBean p) {
	        Session session = this.sessionFactory.getCurrentSession();
	        session.update(p);
	        logger.info(" updated successfully,  Details=" + p);
	    }

	    @SuppressWarnings("unchecked")
	    @Override
	    public List<TestingBean> listUser() {
	        Session session = this.sessionFactory.getCurrentSession();
	        List<TestingBean> phonesList = session.createQuery("from ").list();
	        for (TestingBean p : phonesList) {
	            logger.info(" List::" + p);
	        }
	        return phonesList;
	    }

	    @Override
	    public void deleteUser(int id) {
	        Session session = this.sessionFactory.getCurrentSession();
	        TestingBean p = (TestingBean) session.load(TestingBean.class, new Integer(id));
	        if (null != p) {
	            session.delete(p);
	        }
	        logger.info(" deleted successfully,  details=" + p);
	    }
	
}
