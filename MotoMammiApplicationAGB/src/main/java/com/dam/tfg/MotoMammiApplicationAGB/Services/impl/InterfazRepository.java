package com.dam.tfg.MotoMammiApplicationAGB.Services.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.aspectj.apache.bcel.classfile.Constant;
import org.glassfish.jaxb.runtime.v2.runtime.reflect.opt.Const;
import org.hibernate.Hibernate;
import org.hibernate.HibernateError;
import org.hibernate.Session;

import com.dam.tfg.MotoMammiApplicationAGB.Models.InterfazDTO;
import com.dam.tfg.MotoMammiApplicationAGB.Models.User.CustomerDTO;
import com.dam.tfg.MotoMammiApplicationAGB.Utils.Constants;
import com.dam.tfg.MotoMammiApplicationAGB.Utils.HibernateUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ch.qos.logback.classic.pattern.Util;

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
    public void insertCustomerToMMInterfaz(CustomerDTO customerDTO,String codprov){
        try {

            Gson gson = new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();
            Session session = HibernateUtil.getSession();
            InterfazDTO interfazDTO = new InterfazDTO();
            //convertir customerDTO en un json
            String customerJson = gson.toJson(customerDTO);

            interfazDTO.setCodExternal(customerDTO.getDNI());//CodExternal-DNI
            interfazDTO.setCodProv(codprov);//CodProv-CodProv
            interfazDTO.setConstJson(customerJson);//contJson-CustomerJson
            interfazDTO.setCreateBy(Constants.APP_NAME); //cratedBY-CONST NOMBRE DE LA APLICACION
            interfazDTO.setUpdateBy(Constants.APP_NAME); //UpdateBy- CONST NOMBRE DE APLICACION
            interfazDTO.setStatusProcess(Constants.SP_N);//statusProcess -> N -> NO HA SIDO PROCESADO
            interfazDTO.setResource(Constants.CUSTOMER);//Resource: Customer


           
            

            //insertarlo en la tabla MM_INTERFAZ
            session.persist(interfazDTO);
            session.close();
        } catch (HibernateError e) {
            try {
                String errorMessage = e.getMessage()!=null ? e.getMessage() : "NULL"; //-> valido que no me meta null
                Session session = HibernateUtil.getSession();
                InterfazDTO interfazDTO = new InterfazDTO();
                interfazDTO.setStatusProcess(Constants.SP_E); //--> StatusProcess: E -> ERROR
                interfazDTO.setCodError(Constants.ERROR_HIBERNATE);//-> dentro del catch de hibernate este es el causante
                interfazDTO.setErrorMessage(errorMessage);//--> Mensaje de error
                session.persist(interfazDTO);//Guardamos el objeto
                session.close();
                System.err.println(e.getMessage());
                
            } catch (Exception exception) {
               System.err.println(exception.getMessage());
               System.err.println(Constants.UNKNOW_ERROR);//en caso de que estalle tambien esto pues ERROR DESCONOCIDO
            }
           
        }
    }

    /**
     * Comprueba si el customer en la tabla interfaz esta  
     * 
     * @return En caso de que los dos Json sean iguales devolvera un String vacio "".
     * En caso de que nuestro archivo json sea diferente al de la base de datos nos devolvera este
     * 
     * * */
    public String haveJsonWithCustomer(CustomerDTO customerDTO){

        try {
            Gson gson = new Gson();
            String jsonToTheFile = gson.toJson(customerDTO); //lo convertimos en JSON para guardarlo en la tabla interfaz

            String dni = customerDTO.getDNI();
            Session session = HibernateUtil.getSession();
            InterfazDTO interfazdDto = session.createQuery("from MM_INTERFACE where codExternal = :DNI and codProv = :CODPROV and contJson=:JSON",InterfazDTO.class)
            .setParameter("DNI", dni)
            .setParameter("JSON", jsonToTheFile)
            .uniqueResult();

           
           String jsonToTheDataBase = interfazdDto.getConstJson();
           if (jsonToTheDataBase!=null) {//si lo encuentra
            return jsonToTheDataBase.equals(jsonToTheFile) ? "" : jsonToTheFile; 
            // si son iguales devuelve un string vacio de no serlo devuelves el Json del archivo
            
           }

           return ""; //en caso de que no encuentres nada devuelve un string vacio
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }


    }

}
