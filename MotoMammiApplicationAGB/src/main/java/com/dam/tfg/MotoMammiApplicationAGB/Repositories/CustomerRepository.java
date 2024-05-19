package com.dam.tfg.MotoMammiApplicationAGB.Repositories;

import org.hibernate.Session;

import com.dam.tfg.MotoMammiApplicationAGB.Models.CustomerDTO;
import com.dam.tfg.MotoMammiApplicationAGB.Models.Translation;
import com.dam.tfg.MotoMammiApplicationAGB.Utils.Errors;
import com.dam.tfg.MotoMammiApplicationAGB.Utils.HibernateUtil;

public class CustomerRepository {

    @SuppressWarnings("deprecation")
    public void insertCustomerToMainTable(CustomerDTO customer){
        Session session = null;
      try {
        session = HibernateUtil.getSession();
       if (session.get(CustomerDTO.class, customer.getDNI())!=null) { return;} //si existe ya en la base skipea
        session.beginTransaction();
        session.save(customer);
        session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println(e.getMessage());
           
        }finally{
            session.close();
        }
    }
    //TODO: comprobar si no esta ya en la base de datos

   
}
