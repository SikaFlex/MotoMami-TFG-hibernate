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

    public InterfazDTO getPersonOfInterfazWithCustomer(CustomerDTO customerDTO){
        
        try {
           
            String dni = customerDTO.getDNI();

            Session session = HibernateUtil.getSession();
            InterfazDTO interfazPerson= session.createQuery("from MM_INTERFACE where codExternal = :DNI and codProv = :CODPROV;",InterfazDTO.class)
            .setParameter("DNI", dni)
            .uniqueResult();
            return interfazPerson;
            
        } catch (Exception e) {

            System.err.println(e.getMessage());
        }
        
        
        
        return null;

    }
}
