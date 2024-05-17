package com.dam.tfg.MotoMammiApplicationAGB;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.dam.tfg.MotoMammiApplicationAGB.Utils.HibernateUtil;

import java.util.*;

@SpringBootApplication
@EnableScheduling
public class MotoMammiApplicationAgbApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(MotoMammiApplicationAgbApplication.class);
	}

}
