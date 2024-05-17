package com.dam.tfg.MotoMammiApplicationAGB.Repositories;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.sql.Date;
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


///////////////////////////////////////////////////////////////////     VEHICLE   ///////////////////////////////////////////////////////////////////////////////////

@SuppressWarnings("deprecation")
public void insertVehicleToInterfaz(VehicleDTO vehicleDTO,String codprov,String operation){
    ProviderRepository providerRepository = new ProviderRepository();
    Gson gson = new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();
    Session session = HibernateUtil.getSession();
    ProviderDTO provider = providerRepository.getProviderByCodProv(codprov);
    String vehicleJson= gson.toJson(vehicleDTO);
    InterfazDTO interfazDTO = new InterfazDTO();

    interfazDTO.setCodExternal(vehicleDTO.getMatricula());//CodExternal-DNI
    interfazDTO.setCodProv(codprov);//CodProv-CodProv
    interfazDTO.setcontJson(vehicleJson);//contJson-CustomerJson
    interfazDTO.setCreateBy(PropertiesConfig.APP_NAME); //cratedBY -> NOMBRE DE LA APLICACION
    interfazDTO.setUpdateBy(PropertiesConfig.APP_NAME); //UpdateBy -> NOMBRE DE APLICACION
    interfazDTO.setStatusProcess(Constants.SP_N);//statusProcess -> N -> NO HA SIDO PROCESADO
    interfazDTO.setResource(Constants.VEHICLES);//Resource: Customer
    interfazDTO.setOperation(operation); //le ponemos El OPERATION
    interfazDTO.setIdProv(provider.getId());


    if (operation==Constants.NEW) {
        UUID uuid = UUID.randomUUID();
     

        interfazDTO.setId(uuid.toString());
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

        session.beginTransaction();
        session.update(interfazDTO);;
        session.getTransaction().commit();
        session.close();
    }


}









///////////////////////////////////////////////////////////////////     CUSTOMER   ///////////////////////////////////////////////////////////////////////////////////



    /** transforma el customer en un json y lo inserta en la tabla MM_INTERFACE**/
    @SuppressWarnings("deprecation")
    public void insertCustomerToInterfaz(CustomerDTO customerDTO,String codprov,String operation){
        try {
            ProviderRepository providerRepository = new ProviderRepository();
           
          
           

            Gson gson = new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();
            Session session = HibernateUtil.getSession();
            ProviderDTO provider = providerRepository.getProviderByCodProv(codprov);
            InterfazDTO interfazDTO = new InterfazDTO();
            //convertir customerDTO en un json
            String customerJson = gson.toJson(customerDTO);
            
            
            interfazDTO.setCodExternal(customerDTO.getDNI());//CodExternal-DNI
            interfazDTO.setCodProv(codprov);//CodProv-CodProv
            interfazDTO.setcontJson(customerJson);//contJson-CustomerJson
            interfazDTO.setCreateBy(PropertiesConfig.APP_NAME); //cratedBY -> NOMBRE DE LA APLICACION
            interfazDTO.setUpdateBy(PropertiesConfig.APP_NAME); //UpdateBy -> NOMBRE DE APLICACION
            interfazDTO.setStatusProcess(Constants.SP_N);//statusProcess -> N -> NO HA SIDO PROCESADO
            interfazDTO.setResource(Constants.CUSTOMER);//Resource: Customer
            interfazDTO.setOperation(operation); //le ponemos El OPERATION
            interfazDTO.setIdProv(provider.getId());

            //en caso de que sea nuevo lo guardas
            if (operation==Constants.NEW) {
                UUID uuid = UUID.randomUUID();
             

                interfazDTO.setId(uuid.toString());
                interfazDTO.setCreateDate(Utils.timeNow());

                session.beginTransaction();
                session.save(interfazDTO);
                session.getTransaction().commit();
                session.close();
            }
            //en caso de que este pero el json sea diferente lo actualizas
            if (operation==Constants.UPD) {
                //recuperamos el ID de la base de datos 
                InterfazDTO objectOfDataBase = getPersonOfInterfazWithExternalCod(customerDTO.getDNI(), codprov);
             
                
            
                interfazDTO.setId(objectOfDataBase.getId());
                interfazDTO.setCreateDate(objectOfDataBase.getCreateDate());
                interfazDTO.setLastUpdate(Utils.timeNow());

                session.beginTransaction();
                session.update(interfazDTO);;
                session.getTransaction().commit();
                session.close();
            }
           
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

}
