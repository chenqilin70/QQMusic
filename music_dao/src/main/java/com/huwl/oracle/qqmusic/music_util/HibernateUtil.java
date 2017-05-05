package com.huwl.oracle.qqmusic.music_util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
@Deprecated
public class HibernateUtil {
	public static void main(String[] args) {
		System.out.println(getSessionFactory().openSession());
	}
	public static SessionFactory sessionFactory;
	static {
		Configuration configuration = new Configuration().configure();
		ServiceRegistryBuilder srb = new ServiceRegistryBuilder();
		srb.applySettings(configuration.getProperties());
		ServiceRegistry serviceRegistry = srb.buildServiceRegistry();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	}
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	
}
