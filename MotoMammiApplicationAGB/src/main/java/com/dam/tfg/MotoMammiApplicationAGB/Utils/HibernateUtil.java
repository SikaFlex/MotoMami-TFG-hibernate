package com.dam.tfg.MotoMammiApplicationAGB.Utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    
    public Session getSession(){
		SessionFactory sessionFactory;
		Configuration configuration = new Configuration();
		configuration.configure("hibernate.cfg.xml");
		// configuration.addAnnotatedClass(Covid_Register_User.class);
		// configuration.addAnnotatedClass(Users_cide.class);

		StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);

		return sessionFactory.openSession();

	}
}
