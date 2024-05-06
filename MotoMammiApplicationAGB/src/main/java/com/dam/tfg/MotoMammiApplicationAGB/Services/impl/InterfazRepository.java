package com.dam.tfg.MotoMammiApplicationAGB.Services.impl;

import org.aspectj.apache.bcel.classfile.Constant;
import org.glassfish.jaxb.runtime.v2.runtime.reflect.opt.Const;
import org.hibernate.Hibernate;
import org.hibernate.Session;

import com.dam.tfg.MotoMammiApplicationAGB.Models.InterfazDTO;
import com.dam.tfg.MotoMammiApplicationAGB.Models.User.CustomerDTO;
import com.dam.tfg.MotoMammiApplicationAGB.Utils.Constants;
import com.dam.tfg.MotoMammiApplicationAGB.Utils.HibernateUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
            session.close();
            return interfazPerson;
        } catch (Exception e) {

            System.err.println(e.getMessage());
            return null;
        }

    }

    /** transforma el customer en un json y lo inserta en la tabla MM_INTERFACE**/
    public void insertCustomerToMMInterfaz(CustomerDTO customerDTO){
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Session session = HibernateUtil.getSession();
            InterfazDTO interfazDTO = new InterfazDTO();
            //convertir customerDTO en un json
            String customerJson = gson.toJson(customerDTO);
            interfazDTO.setConstJson(customerJson);


            //insertarlo en la tabla MM_INTERFAZ
            session.persist(interfazDTO);
            session.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Comprueba si el customer en la tabla interfaz esta  
     * 
     * @return En caso de que los dos Json sean iguales devolvera un String vacio "".
     * En caso de que nuestro archivo json sea diferente al de la base de datos nos devolvera este
     * 
     * * */
    public String haveJsonWithCustomer(CustomerDTO customerDTO, String JsonToTheFile){
        try {
            String dni = customerDTO.getDNI();
            Session session = HibernateUtil.getSession();
            InterfazDTO interfazdDto = session.createQuery("from MM_INTERFACE where codExternal = :DNI and codProv = :CODPROV;",InterfazDTO.class)
            .setParameter("DNI", dni)
            .uniqueResult();

            //no validamos si 
           String jsonToTheDataBase = interfazdDto.getConstJson();
           if (jsonToTheDataBase!=null) {//si lo encuentra
            return jsonToTheDataBase.equals(JsonToTheFile) ? "" : JsonToTheFile; // e ser iguales devuelve un string vacio de no serlo devuelves el Json del archivo
            
           }

           return ""; //en caso de que no encuentres nada devuelve un string vacio
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }


    }

}
