package com.dam.tfg.MotoMammiApplicationAGB.Repositories;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.dam.tfg.MotoMammiApplicationAGB.Models.ProviderDTO;
import com.dam.tfg.MotoMammiApplicationAGB.Utils.HibernateUtil;

@Repository
public class ProviderRepository implements InterfazProvider{

    @Override
    public void getAllUsersPovidersActive() {
    try {
        Session session=  HibernateUtil.getSession();

        List<ProviderDTO> listProviders= session.createQuery("FROM mm_providers where swiact = 1 "+
        "and ifnull(:p_date,current_date()) BETWEEN dateIni AND ifnull(dateEnd,'2099-12-31') "+
        "and codigoProveedor = ifnull(:p_prov, codigoProveedor)",ProviderDTO.class)
        .setParameter("p_prov", null)
        .setParameter("p_date", null)
        .list();
        
   
        for (ProviderDTO providerDTO : listProviders) {
            System.out.println(providerDTO.getName());
        }
        
    } catch (Exception e) {
       System.err.println("ERROR: "+e.getMessage());
    }
      
    }


    public static void main(String[] args) {
        ProviderRepository pr = new ProviderRepository();
        pr.getAllUsersPovidersActive();
    }
    
}
