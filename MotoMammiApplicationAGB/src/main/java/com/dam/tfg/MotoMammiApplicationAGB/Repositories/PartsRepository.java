package com.dam.tfg.MotoMammiApplicationAGB.Repositories;

import org.hibernate.Session;

import com.dam.tfg.MotoMammiApplicationAGB.Models.CustomerDTO;
import com.dam.tfg.MotoMammiApplicationAGB.Models.PartsDTO;
import com.dam.tfg.MotoMammiApplicationAGB.Utils.HibernateUtil;

public class PartsRepository {

    @SuppressWarnings("deprecation")
    public void insertPartsToMainTable(PartsDTO parts){
        Session session = null;
      try {
        session = HibernateUtil.getSession();

       if (session.get(PartsDTO.class, parts.getId())!=null) { return;} //si existe ya en la base skipea
        session.beginTransaction();
        session.save(parts);
        session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();           
        }finally{
            session.close();
        }
    }
  
    
}
