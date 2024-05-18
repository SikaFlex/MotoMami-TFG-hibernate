package com.dam.tfg.MotoMammiApplicationAGB.Services.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Value;
import org.aspectj.apache.bcel.classfile.Constant;
import org.hibernate.Hibernate;

import com.dam.tfg.MotoMammiApplicationAGB.Models.CustomerDTO;
import com.dam.tfg.MotoMammiApplicationAGB.Models.InterfazDTO;
import com.dam.tfg.MotoMammiApplicationAGB.Models.PartsDTO;
import com.dam.tfg.MotoMammiApplicationAGB.Models.ProviderDTO;
import com.dam.tfg.MotoMammiApplicationAGB.Models.VehicleDTO;
import com.dam.tfg.MotoMammiApplicationAGB.Repositories.InterfazRepository;
import com.dam.tfg.MotoMammiApplicationAGB.Repositories.ProviderRepository;
import com.dam.tfg.MotoMammiApplicationAGB.Utils.HibernateUtil;
import com.dam.tfg.MotoMammiApplicationAGB.Utils.PropertiesConfig;
import com.dam.tfg.MotoMammiApplicationAGB.Utils.Utils;
import com.google.gson.Gson;
import com.dam.tfg.MotoMammiApplicationAGB.Utils.Constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;


//proceso que lee el fichero
@Service
public class ProccessServiceImpl implements ProccessService{
 
    public static void main(String[] args) {
        ProccessServiceImpl psi = new ProccessServiceImpl();
       
        psi.readInfoFile("CUS",null,null);
    
    }


    //TERCER PROCESO 1 VEZ AL MES
    @Override
    public void voidGenerateInvoice(String codProv, String date) {
        /*
         * RF4.2 Las facturas contendrán los campos de id, fecha, nombre empresa, cif empresa (41256985632), 
         * dirección de la empresa (C/ Vergel, 5 Madrid, 28080), nombre usuario, apellidos usuario, dirección usuario, 
         * tipo seguro, tipo de vehículo, fecha de registro,
         *  fecha de fin de contrato (un año de duración), coste, iva (21% sobre el precio).
         *   RF4.2-AD. La aplicación tendrá un proceso mensual que se lanzará el primer día de cada mes y genera un fichero con todas
         *  las facturas del mes anterior(nombre fichero “MM_invoices_CODPROV_YYYYMM.csv”). Para que un proveedor firme digitalmente las facturas.
         */
       //GENERA UN ARCHIVO CON TODAS LAS FACTURA DE UN USUARIO
    }

      //SEGUNDO PROCESO
    @Override
    public void proccessIntegrateInfo(String source,String codProv, String date){
        //sacar una lista de la tabla MM_interfaz con los que tengan el status process en N
        //serializar el objeto ya sea cutomer,vehicle,parts 
        //una vez lo tenga serializado tengo que la traduccion EXTERNAL_COD --> MM_TRANSLATION  traducciendo por ejemplo street type
        //insertar en las tablas maestras ya sea mm_customer...
        //y una vez ejecutado la insercion actualizamos el statusprocess en la tabla interfaz con el valor P = procesado

    }

    @Override
    public void readInfoFile(String source,String codProv, String date){
        
        try {
            //en caso de que este vacio le pondra el dia de hoy
           String dateFile = date==null ? new SimpleDateFormat("yyyy-MM-dd").format(new Date())
                                        : new SimpleDateFormat("yyyy-MM-dd").format(date);
                      
            ProviderRepository PR = new ProviderRepository();
            List<ProviderDTO> listaProveedoresActivos = PR.getAllUsersPovidersActive(codProv, dateFile);
         
            String vehicleFile;
            String partFile;
            String customerFile;

                //recorremos los proveedores activos
            for (ProviderDTO proveedor : listaProveedoresActivos) {
                // Recuperamos el código del proveedor
                String codProvActivo = proveedor.getCodigoProveedor();
                
                //buscar archivo con estos nombres por cada proveedor
                customerFile= PropertiesConfig.PATH + PropertiesConfig.CUSTOMER_PATH_FILE + codProvActivo + dateFile + PropertiesConfig.FORMAT;
                vehicleFile= PropertiesConfig.PATH + PropertiesConfig.VEHICLE_PATH_FILE + codProvActivo + dateFile + PropertiesConfig.FORMAT;
                partFile= PropertiesConfig.PATH + PropertiesConfig.PARTS_PATH_FILE +  codProvActivo + dateFile + PropertiesConfig.FORMAT;
                
              
                //tienes que leer los 3 archivos
                readFile(customerFile,Constants.CUSTOMER, codProvActivo);
                readFile(vehicleFile,Constants.VEHICLES, codProvActivo);
                readFile(partFile,Constants.PARTS, codProvActivo);

            }

   

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings("deprecation")
    private void readFile(String pathCompost, String constants,String codprov ){
        try {
            InterfazRepository interfazRepository = new InterfazRepository();
            BufferedReader br = new BufferedReader(new FileReader(new File(pathCompost)));
            String linea;
            
            while ((linea=br.readLine())!= null) {
                /*
                 * SE QUITA ESTE CONTROL DE ERRORES POR DECISION PERSONAL YA QUE ES UNA MINA DE BUGS Y COMO ALFINAL ESTO SE PUEDE ACORDAR CON EL CLIENTE
                 * SE DECIDE QUE EL FORMATO SERA SIN LA FILA DE LAS COLUMNAS ARRIBA
                */
                // if (linea.contains(Constants.SKIP_VEHICLE) || linea.contains(Constants.SKIP_CUSTOMER) || linea.contains(Constants.SKIP_PARTS) || linea == null){linea=br.readLine();}
                String[] splitData =linea.split(","); 
                
                
               
                
                switch (constants) {


                    case Constants.VEHICLES:
                    insertVehicleToInterfaceTable(splitData,interfazRepository,codprov);
                    break;
               
                    case Constants.PARTS:
                    insertPartsToInterfaceTable(splitData,interfazRepository,codprov);
                    break;
                        
                    case Constants.CUSTOMER:
                    insertCustomerToInterfaceTable(splitData,interfazRepository,codprov);
                    break;
                   
                }
            }
        
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }







///////////////////////////////////////////////////////////////////     CUSTOMER   ///////////////////////////////////////////////////////////////////////////////////



private void insertCustomerToInterfaceTable(String[] splitData,InterfazRepository interfazRepository,String codprov){
    try {
        CustomerDTO customerDTO = new CustomerDTO(splitData[0],splitData[1],splitData[2],splitData[3],splitData[4],splitData[5],splitData[6],
                                                  splitData[7],splitData[8],splitData[9],splitData[10],splitData[11],splitData[12]);
 
     if (!isInInterface(customerDTO.getDNI(),codprov)) { //Si no esta la persona en la tabla MM_interfaz
         interfazRepository.insertCustomerToInterfaz(customerDTO,codprov,Constants.NEW); //Insertarlo con la operacion como :new es un campo que solo rellena la primera vez) -> OPERATION: "NEW"

     }else if(existJsonAndIsDifferentCustomer(customerDTO,codprov)){//en caso de ya exista un registo, mirara que el json no sea igual lo actualizara con OPERATION = "UPD" ->update
         interfazRepository.insertCustomerToInterfaz(customerDTO,codprov,Constants.UPD);//Cambiamos el operation a UPD
     }
        //En caso de que el json ya este y sea igual no se hace nada
     
    } catch (Exception error) {
        insertErrorMessage(error);
        System.err.println(error.getMessage());
     /* En caso de error durante el proceso se guardara el error en la tabla interfaz */
 
    }
}


    /**
     * Comprueba si existe un json y si es diferente o igual al que ya esta almacenado en base de datos
     * @param customerDTO el objeto que va a comprobar si contiene un json en la tabla Interfaz
     * @param jsonToTheFile el json que se va usar para comprar con la base de datos
     * @return  true si no hay el contJson del la tabla interfaz no es igual a nuestro archivo
     * 
     * **/
    private boolean existJsonAndIsDifferentCustomer (CustomerDTO customerDTO,String codProv){
    try {
        InterfazRepository interfazRepository = new InterfazRepository();
        InterfazDTO jsonInterfaz = interfazRepository.haveJsonWithCustomer(customerDTO,codProv);
        return jsonInterfaz != null ? true : false; 
       //Si es null significa el json de base de datos no es igual entonces devolvera true para que pueda entrar en el condicional
       //y guardar el nuevo record en la base de datos. En caso de que encuentre uno igual devolvera false para que no haga nada.
    } catch (Exception e) {
      System.err.println(e.getMessage());
      return false;
    }

    }





///////////////////////////////////////////////////////////////////     VEHICLE   ///////////////////////////////////////////////////////////////////////////////////

private void insertVehicleToInterfaceTable(String[] splitData,InterfazRepository interfazRepository,String codprov){
    try {
        VehicleDTO vehicleDTO = new VehicleDTO(splitData[0],splitData[1],splitData[2],splitData[3],splitData[4],splitData[5]);
        
         if (!isInInterface(vehicleDTO.getMatricula(),codprov)) {
         interfazRepository.insertVehicleToInterfaz(vehicleDTO,codprov,Constants.NEW);
         }else if(existJsonAndIsDifferentVehicle(vehicleDTO,codprov)){//en caso de ya exista un registo, mirara que el json no sea igual lo actualizara con OPERATION = "UPD" ->update
         interfazRepository.insertVehicleToInterfaz(vehicleDTO,codprov,Constants.UPD);//Cambiamos el operation a UPD
         }

    }catch (Exception error) {
        insertErrorMessage(error);
        System.err.println(error.getMessage());
     /* En caso de error durante el proceso se guardara el error en la tabla interfaz */
    }
}

private boolean existJsonAndIsDifferentVehicle(VehicleDTO vehicleDTO,String codProv){
    try {
        InterfazRepository interfazRepository = new InterfazRepository();
        InterfazDTO jsonInterfaz = interfazRepository.haveJsonWithVehicle(vehicleDTO,codProv);
        return jsonInterfaz != null ? true : false; 

    } catch (Exception e) {
       System.err.println(e.getMessage());
       return false;
    }

}



///////////////////////////////////////////////////////////////////     PARTS   ///////////////////////////////////////////////////////////////////////////////////
//TODO MIRAR POR QUE DUPLICA
private void insertPartsToInterfaceTable(String[] splitData,InterfazRepository interfazRepository,String codprov){
    try {
        java.sql.Date dateNotification= Utils.stringToSqlDate(splitData[6]); //parseamos la fecha que nos viene en el archivo

        PartsDTO partsDTO = new PartsDTO(splitData[0],splitData[1],splitData[2],splitData[3],splitData[4],splitData[5],dateNotification);
        
         if (!isInInterface(partsDTO.getCodigoExterno(),codprov)) {
         interfazRepository.insertPartToInterfaz(partsDTO,codprov,Constants.NEW);
         }else if(existJsonAndIsDifferentParts(partsDTO,codprov)){//en caso de ya exista un registo, mirara que el json no sea igual lo actualizara con OPERATION = "UPD" ->update
         interfazRepository.insertPartToInterfaz(partsDTO,codprov,Constants.UPD);//Cambiamos el operation a UPD
         }

    }catch (Exception error) {
        insertErrorMessage(error);
        System.err.println(error.getMessage());
     /* En caso de error durante el proceso se guardara el error en la tabla interfaz */
    }
}

private boolean existJsonAndIsDifferentParts(PartsDTO partsDTO,String codProv){
    try {
        InterfazRepository interfazRepository = new InterfazRepository();
        InterfazDTO jsonInterfaz = interfazRepository.haveJsonWithPart(partsDTO,codProv);
        return jsonInterfaz != null ? true : false; 

    } catch (Exception e) {
       System.err.println(e.getMessage());
       return false;
    }

}

///////////////////////////////////////////////////////////////////     GENERIC   ///////////////////////////////////////////////////////////////////////////////////


 
private boolean isInInterface(String externalCod,String codProv) {
    try {
    InterfazRepository IR = new InterfazRepository();
    InterfazDTO interfazDTO = IR.getPersonOfInterfazWithExternalCod(externalCod,codProv);
    return interfazDTO != null ?  true : false ; 
    //si no encuentra algo significa que esta en la base datos entonces devolvera true

    } catch (Exception e) {
        System.err.println(e.getMessage());
        return false;
    }
   
}


/**
 * Guardara el mensaje de error en la base de datos
 * @param e Excepcion para poder recuperar su mensaje
 */
@SuppressWarnings("deprecation")
private void insertErrorMessage(Exception e){
    try {
        String errorMessage = e.getMessage()!= null ? e.getMessage() : Constants.UNKNOW_ERROR ; //-> valido que no me meta null
        Session session = HibernateUtil.getSession();
        InterfazDTO interfazDTO = new InterfazDTO();

        interfazDTO.setCreateDate(Utils.timeNow());
        interfazDTO.setStatusProcess(Constants.SP_E); //--> StatusProcess: E -> ERROR
        interfazDTO.setCodError(Constants.ERROR_HIBERNATE);//-> dentro del catch de hibernate este es el causante de todos nuestros males
        interfazDTO.setErrorMessage(errorMessage);//--> Mensaje de error

        session.beginTransaction();
        session.save(interfazDTO);//Guardamos el objeto
        session.getTransaction().commit();
        session.close();
        
    } catch (Exception exception) {
       System.err.println(exception.getMessage());
       System.err.println(Constants.UNKNOW_ERROR);//en caso de que estalle tambien esto pues ERROR DESCONOCIDO
    }
}
 
}
