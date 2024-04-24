package com.dam.tfg.MotoMammiApplicationAGB.Utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import com.dam.tfg.MotoMammiApplicationAGB.Models.InterfazDTO;
import com.dam.tfg.MotoMammiApplicationAGB.Models.ProviderDTO;

public class HibernateUtil {
    
    public static Session getSession(){
		SessionFactory sessionFactory;
		Configuration configuration = new Configuration();
		configuration.configure("hibernate.cfg.xml");
		configuration.addAnnotatedClass(ProviderDTO.class);
		configuration.addAnnotatedClass(InterfazDTO.class);
		
		// configuration.addAnnotatedClass(Users_cide.class); InterfazDTO

		StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);

		return sessionFactory.openSession();

	}
}
