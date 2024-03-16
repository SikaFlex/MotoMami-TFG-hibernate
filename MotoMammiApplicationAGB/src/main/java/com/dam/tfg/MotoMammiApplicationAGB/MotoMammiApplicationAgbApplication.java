package com.dam.tfg.MotoMammiApplicationAGB;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;

@SpringBootApplication
public class MotoMammiApplicationAgbApplication {

	public static void main(String[] args) {
		Session session=null;
		try{
			session = getSession();














		}catch(Exception e){
			System.err.println(e.getMessage());
		}finally{
			session.close();
		}
	}


	public static Session getSession(){
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
