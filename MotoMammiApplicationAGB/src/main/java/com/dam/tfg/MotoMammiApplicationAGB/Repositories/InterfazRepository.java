package com.dam.tfg.MotoMammiApplicationAGB.Repositories;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.sql.Date;
import java.util.List;
import java.util.UUID;
import com.dam.tfg.MotoMammiApplicationAGB.Utils.Constants;
import org.aspectj.apache.bcel.classfile.Constant;
import org.glassfish.jaxb.runtime.v2.runtime.reflect.opt.Const;
import org.hibernate.Hibernate;
import org.hibernate.HibernateError;
import org.hibernate.Session;
import java.sql.Timestamp;

import com.dam.tfg.MotoMammiApplicationAGB.Models.CustomerDTO;
import com.dam.tfg.MotoMammiApplicationAGB.Models.InterfazDTO;
import com.dam.tfg.MotoMammiApplicationAGB.Models.PartsDTO;
import com.dam.tfg.MotoMammiApplicationAGB.Models.ProviderDTO;
import com.dam.tfg.MotoMammiApplicationAGB.Models.VehicleDTO;
import com.dam.tfg.MotoMammiApplicationAGB.Utils.Constants;
import com.dam.tfg.MotoMammiApplicationAGB.Utils.HibernateUtil;
import com.dam.tfg.MotoMammiApplicationAGB.Utils.PropertiesConfig;
import com.dam.tfg.MotoMammiApplicationAGB.Utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ch.qos.logback.classic.pattern.Util;

public class InterfazRepository {

    /**
     * Comparamos en la tabla interfa
     * @param externalCod recuperamos el elemento de la tabla interfaz a traves de su codigo externo
     * **/
    public InterfazDTO getPersonOfInterfazWithExternalCod(String externalCod,String codProv){
        
        try {
            Session session = HibernateUtil.getSession();
            InterfazDTO interfazPerson= session.createQuery("from mm_interface where codExternal = :EXTERNAL_COD and codProv = :CODPROV",InterfazDTO.class)
            .setParameter("EXTERNAL_COD", externalCod)
            .setParameter("CODPROV", codProv)
            .uniqueResult();
            session.close();
            return interfazPerson;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            /*esto lo hago para que en caso de que estallara le envie un objeto y no devuelva null como si 
              no hubiera encontrado nada en la base de datos por que alomejor en la base
            * de datos si que esta, pero ha habido algun problema entre medias
            */
            return new InterfazDTO();
        }

    }

    @SuppressWarnings("deprecation")
    public void updateInterfaz(InterfazDTO interfazDTO){
        Session session = null;
        try {
            session = HibernateUtil.getSession();
            session.beginTransaction();
            session.update(interfazDTO);;
            session.getTransaction().commit();
            session.close();
            
        } catch (Exception e) {
          e.printStackTrace();
        }finally{
            session.close();
        }
    }





///////////////////////////////////////////////////////////////////     PARTS   ///////////////////////////////////////////////////////////////////////////////////



    /** transforma el customer en un json y lo inserta en la tabla MM_INTERFACE**/
    @SuppressWarnings("deprecation")
    public void insertPartToInterfaz(PartsDTO partsDTO,String codprov,String operation){
        try {
            ProviderRepository providerRepository = new ProviderRepository();
            Session session = HibernateUtil.getSession();
            Gson gson = new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();
           

            ProviderDTO provider = providerRepository.getProviderByCodProv(codprov);
            InterfazDTO interfazDTO = new InterfazDTO();
            partsDTO.setId(Utils.randomID());//le añadimos un id
            //convertir customerDTO en un json
            String objectJson = gson.toJson(partsDTO);
            
            
            interfazDTO.setCodExternal(partsDTO.getCodigoExterno());//CodExternal-DNI
            interfazDTO.setCodProv(codprov);//CodProv-CodProv
            interfazDTO.setcontJson(objectJson);//contJson-objectJson
            interfazDTO.setCreateBy(PropertiesConfig.APP_NAME); //cratedBY -> NOMBRE DE LA APLICACION
            interfazDTO.setStatusProcess(Constants.SP_N);//statusProcess -> N -> NO HA SIDO PROCESADO
            interfazDTO.setResource(Constants.PARTS);//Resource: Customer
            interfazDTO.setOperation(operation); //le ponemos El OPERATION
            interfazDTO.setIdProv(provider.getId());

            //en caso de que sea nuevo lo guardas
            if (operation==Constants.NEW) {

                interfazDTO.setId(Utils.randomID());
                interfazDTO.setCreateDate(Utils.timeNow());

                session.beginTransaction();
                session.save(interfazDTO);
                session.getTransaction().commit();
                session.close();
            }
            //en caso de que este pero el json sea diferente lo actualizas
            if (operation==Constants.UPD) {
                //recuperamos el ID de la base de datos 
                InterfazDTO objectOfDataBase = getPersonOfInterfazWithExternalCod(partsDTO.getCodigoExterno(), codprov);
             
                
            
                interfazDTO.setId(objectOfDataBase.getId());
                interfazDTO.setCreateDate(objectOfDataBase.getCreateDate());
                interfazDTO.setLastUpdate(Utils.timeNow());
                interfazDTO.setUpdateBy(PropertiesConfig.APP_NAME); //UpdateBy -> NOMBRE DE APLICACION
                interfazDTO.setStatusProcess(Constants.SP_N); // -->status process otra vez a N para que se pueda volver actualizar en la tabla maestra
                session.beginTransaction();
                session.update(interfazDTO);;
                session.getTransaction().commit();
                session.close();
            }
           
        } catch (Exception e) {
           e.printStackTrace();
        }
    }

   @SuppressWarnings("deprecation")


    public InterfazDTO haveJsonWithPart(PartsDTO partsDTO,String codprov){
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();//es importante que el json tenga el mismo formato que el que esta en base de datos
            String jsonToTheFile = gson.toJson(partsDTO); //lo convertimos en JSON para guardarlo en la tabla interfaz
    
            String codExt = partsDTO.getCodigoExterno();
            Session session = HibernateUtil.getSession();
            InterfazDTO interfazdDto = session.createQuery("from mm_interface where codExternal = :CODEXT and codProv = :CODPROV",InterfazDTO.class)
            .setParameter("CODEXT", codExt)
            .setParameter("CODPROV", codprov)
            .uniqueResult();
       
            
            if (interfazdDto==null) { return null; }//control de errores por si no lo encuentra
            Boolean isEquals = jsonToTheFile.equals(interfazdDto.getcontJson());
    
            return isEquals ? null : interfazdDto;
            /* Compruebo yo si son iguales ya mysql no se como compara 2 Strings pero se ve que no estrictamente iguales
             * haciendolo asi puedo asegurarme de que el formato es exactamente el mismo. */
    
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null; //en caso de error devolvera null y se asegurara asi que se guarda el registro
        }
    
    }




///////////////////////////////////////////////////////////////////     VEHICLE   ///////////////////////////////////////////////////////////////////////////////////

@SuppressWarnings("deprecation")
public void insertVehicleToInterfaz(VehicleDTO vehicleDTO,String codprov,String operation){
    ProviderRepository providerRepository = new ProviderRepository();
    Gson gson = new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();
    Session session = HibernateUtil.getSession();
    ProviderDTO provider = providerRepository.getProviderByCodProv(codprov);
    String vehicleJson= gson.toJson(vehicleDTO);
    InterfazDTO interfazDTO = new InterfazDTO();

    interfazDTO.setCodExternal(vehicleDTO.getMatricula());//CodExternal
    interfazDTO.setCodProv(codprov);//CodProv-CodProv
    interfazDTO.setcontJson(vehicleJson);//contJson-objectJson
    interfazDTO.setCreateBy(PropertiesConfig.APP_NAME); //cratedBY -> NOMBRE DE LA APLICACION
    interfazDTO.setStatusProcess(Constants.SP_N);//statusProcess -> N -> NO HA SIDO PROCESADO
    interfazDTO.setResource(Constants.VEHICLES);//Resource: Customer
    interfazDTO.setOperation(operation); //le ponemos El OPERATION
    interfazDTO.setIdProv(provider.getId());


    if (operation==Constants.NEW) {
       
     

        interfazDTO.setId(Utils.randomID());
        interfazDTO.setCreateDate(Utils.timeNow());


        session.beginTransaction();
        session.save(interfazDTO);
        session.getTransaction().commit();
        session.close();
    }
    //en caso de que este pero el json sea diferente lo actualizas
    if (operation==Constants.UPD) {
        //recuperamos el ID de la base de datos 
        InterfazDTO objectOfDataBase = getPersonOfInterfazWithExternalCod(vehicleDTO.getMatricula(), codprov);
     
        
    
        interfazDTO.setId(objectOfDataBase.getId());
        interfazDTO.setCreateDate(objectOfDataBase.getCreateDate());
        interfazDTO.setLastUpdate(Utils.timeNow());
        interfazDTO.setUpdateBy(PropertiesConfig.APP_NAME); //UpdateBy -> NOMBRE DE APLICACION


        session.beginTransaction();
        session.update(interfazDTO);;
        session.getTransaction().commit();
        session.close();
    }


}


public InterfazDTO haveJsonWithVehicle(VehicleDTO vehicleDTO,String codprov){
    try {
        Gson gson = new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();//es importante que el json tenga el mismo formato que el que esta en base de datos
        String jsonToTheFile = gson.toJson(vehicleDTO); //lo convertimos en JSON para guardarlo en la tabla interfaz

        String matricula = vehicleDTO.getMatricula();
        Session session = HibernateUtil.getSession();
        InterfazDTO interfazdDto = session.createQuery("from mm_interface where codExternal = :MATRICULA and codProv = :CODPROV",InterfazDTO.class)
        .setParameter("MATRICULA", matricula)
        .setParameter("CODPROV", codprov)
        .uniqueResult();
        

        if (interfazdDto==null) { return null; }//control de errores por si no lo encuentra
        Boolean isEquals = jsonToTheFile.equals(interfazdDto.getcontJson());

        return isEquals ? null : interfazdDto;
        /* Compruebo yo si son iguales ya mysql no se como compara 2 Strings pero se ve que no estrictamente iguales
         * haciendolo asi puedo asegurarme de que el formato es exactamente el mismo. */

    } catch (Exception e) {
        System.err.println(e.getMessage());
        return null; //en caso de error devolvera null y se asegurara asi que se guarda el registro
    }

}






///////////////////////////////////////////////////////////////////     CUSTOMER   ///////////////////////////////////////////////////////////////////////////////////



    /** transforma el customer en un json y lo inserta en la tabla MM_INTERFACE**/
    @SuppressWarnings("deprecation")
    public void insertCustomerToInterfaz(CustomerDTO customerDTO,String codprov,String operation){
        try {
            ProviderRepository providerRepository = new ProviderRepository();
            Session session = HibernateUtil.getSession();
            Gson gson = new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();
           

            ProviderDTO provider = providerRepository.getProviderByCodProv(codprov);
            InterfazDTO interfazDTO = new InterfazDTO();
            //convertir customerDTO en un json
            String objectJson = gson.toJson(customerDTO);
            
            
            interfazDTO.setCodExternal(customerDTO.getDNI());//CodExternal-DNI
            interfazDTO.setCodProv(codprov);//CodProv-CodProv
            interfazDTO.setcontJson(objectJson);//contJson-objectJson
            interfazDTO.setCreateBy(PropertiesConfig.APP_NAME); //cratedBY -> NOMBRE DE LA APLICACION
            interfazDTO.setStatusProcess(Constants.SP_N);//statusProcess -> N -> NO HA SIDO PROCESADO
            interfazDTO.setResource(Constants.CUSTOMER);//Resource: Customer
            interfazDTO.setOperation(operation); //le ponemos El OPERATION
            interfazDTO.setIdProv(provider.getId());

            //en caso de que sea nuevo lo guardas
            if (operation==Constants.NEW) {
                
             

                interfazDTO.setId(Utils.randomID());
                interfazDTO.setCreateDate(Utils.timeNow());

                session.beginTransaction();
                session.save(interfazDTO);
                session.getTransaction().commit();
            }
            //en caso de que este pero el json sea diferente lo actualizas
            if (operation==Constants.UPD) {
                //recuperamos el ID de la base de datos 
                InterfazDTO objectOfDataBase = getPersonOfInterfazWithExternalCod(customerDTO.getDNI(), codprov);
             
                
            
                interfazDTO.setId(objectOfDataBase.getId());
                interfazDTO.setCreateDate(objectOfDataBase.getCreateDate());
                interfazDTO.setLastUpdate(Utils.timeNow());
                interfazDTO.setUpdateBy(PropertiesConfig.APP_NAME); //UpdateBy -> NOMBRE DE APLICACION

                session.beginTransaction();
                session.update(interfazDTO);;
                session.getTransaction().commit();
            }
            session.close();
           
        } catch (Exception e) {
           e.printStackTrace();
        }
    }

    /**
     * Comprueba si el customer en la tabla interfaz esta  
     * 
     * @return En caso de que nuestro archivo json sea diferente al de la base nos devolvera null ya que no ha encontrado ninguno igual
     * 
     * * */
    public InterfazDTO haveJsonWithCustomer(CustomerDTO customerDTO,String codprov){

        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();//es importante que el json tenga el mismo formato que el que esta en base de datos
            String jsonToTheFile = gson.toJson(customerDTO); //lo convertimos en JSON para guardarlo en la tabla interfaz

            String dni = customerDTO.getDNI();
            Session session = HibernateUtil.getSession();
            InterfazDTO interfazdDto = session.createQuery("from mm_interface where codExternal = :DNI and codProv = :CODPROV",InterfazDTO.class)
            .setParameter("DNI", dni)
            .setParameter("CODPROV", codprov)
            .uniqueResult();
            session.close();

            if (interfazdDto==null) { return null; }//control de errores por si no lo encuentra
            Boolean isEquals = jsonToTheFile.equals(interfazdDto.getcontJson());

            return isEquals ? null : interfazdDto;
            /* Compruebo yo si son iguales ya mysql no se como compara 2 Strings pero se ve que no estrictamente iguales
             * haciendolo asi puedo asegurarme de que el formato es exactamente el mismo. */
       

    

        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null; //en caso de error devolvera null y se asegurara asi que se guarda el registro
        }


    }



    public List<InterfazDTO> getRecordsWithStatusInN(String source){
        Session session = null;
        try {
            session = HibernateUtil.getSession();
            return session.createQuery("from mm_interface where resource = :RESOURCE and statusProcess='N'",InterfazDTO.class)
           .setParameter("RESOURCE", source)
           .list();
          
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }finally{
            session.close();
        }

     
    }
}
