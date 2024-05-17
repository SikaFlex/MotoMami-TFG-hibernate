package com.dam.tfg.MotoMammiApplicationAGB.Repositories;


import java.util.ArrayList;
import java.util.List;

import org.aspectj.apache.bcel.classfile.Module.Provide;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.dam.tfg.MotoMammiApplicationAGB.Models.CustomerDTO;
import com.dam.tfg.MotoMammiApplicationAGB.Models.InterfazDTO;
import com.dam.tfg.MotoMammiApplicationAGB.Models.ProviderDTO;
import com.dam.tfg.MotoMammiApplicationAGB.Utils.HibernateUtil;

@Repository
public class ProviderRepository implements InterfazProvider{
  
    public List<ProviderDTO> getAllUsersPovidersActive(String p_prov, String p_date) {
    try {
        Session session=  HibernateUtil.getSession();

        List<ProviderDTO> listProviders= session.createQuery("FROM mm_providers where swiact = 1 "+
        "and ifnull(:p_date,current_date()) BETWEEN dateIni AND ifnull(dateEnd,'2099-12-31') "+
        "and codigoProveedor = ifnull(:p_prov, codigoProveedor)",ProviderDTO.class)
        .setParameter("p_prov", null)
        .setParameter("p_date", null)
        .list();
        session.close();
        return listProviders;
    } catch (Exception e) {
       System.err.println("ERROR getAllUserProviderActive() | ProviderRespository: "+e.getMessage());
    }
        return null;
    }

    public static boolean doValidatePersonIsInInterface(String dni){
        try {
            Session session=  HibernateUtil.getSession();

            @SuppressWarnings("deprecation")
            InterfazDTO provider = session.createQuery("FROM MM_INTERFACE where codExternal = :DNI",InterfazDTO.class)
                                        .setParameter("DNI", dni)
                                        .uniqueResult();
            if (provider == null) {return false;}
            return true;
            
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }

    }

     public void insertCustomerToMMInterfaz(CustomerDTO customerDTO){
        try {
            Session session = HibernateUtil.getSession();
            session.persist(customerDTO);
            session.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }


}
