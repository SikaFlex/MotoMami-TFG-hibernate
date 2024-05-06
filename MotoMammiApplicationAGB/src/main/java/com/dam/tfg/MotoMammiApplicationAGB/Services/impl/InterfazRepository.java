package com.dam.tfg.MotoMammiApplicationAGB.Services.impl;

import org.aspectj.apache.bcel.classfile.Constant;
import org.glassfish.jaxb.runtime.v2.runtime.reflect.opt.Const;
import org.hibernate.Hibernate;
import org.hibernate.Session;

import com.dam.tfg.MotoMammiApplicationAGB.Models.InterfazDTO;
import com.dam.tfg.MotoMammiApplicationAGB.Models.User.CustomerDTO;
import com.dam.tfg.MotoMammiApplicationAGB.Utils.Constants;
import com.dam.tfg.MotoMammiApplicationAGB.Utils.HibernateUtil;

public class InterfazRepository {
    /**
     * Comparamos en la tabla interfa
     * @param customerDTO le pasamos objeto customerDTO que queremos comparar 
     * **/
    public InterfazDTO getPersonOfInterfazWithCustomer(CustomerDTO customerDTO,String codProv){
        
        try {
           
            String dni = customerDTO.getDNI();

            Session session = HibernateUtil.getSession();
            InterfazDTO interfazPerson= session.createQuery("from MM_INTERFACE where codExternal = :DNI and codProv = :CODPROV;",InterfazDTO.class)
            .setParameter("DNI", dni)
            .setParameter("CODPROV", codProv)
            .uniqueResult();
            return interfazPerson;
            
        } catch (Exception e) {

            System.err.println(e.getMessage());
            return null;
        }

    }
}
