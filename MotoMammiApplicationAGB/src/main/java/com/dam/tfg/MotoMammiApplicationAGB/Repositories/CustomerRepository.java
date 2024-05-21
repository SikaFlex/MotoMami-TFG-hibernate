package com.dam.tfg.MotoMammiApplicationAGB.Repositories;

import org.hibernate.Session;

import com.dam.tfg.MotoMammiApplicationAGB.Models.CustomerDTO;
import com.dam.tfg.MotoMammiApplicationAGB.Models.InvoiceDTO;
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

    public CustomerDTO getCustomerByDNI(String DNI){
             Session session=null;
      try {
            session=  HibernateUtil.getSession();
            return session.createQuery("FROM mm_customer where DNI = :DNI",CustomerDTO.class)
            .setParameter("DNI",DNI)
            .uniqueResult();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }finally{
            if (session != null ) {session.close();}
        }
     
    }
}

   

