package com.dam.tfg.MotoMammiApplicationAGB.Services.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;


import com.dam.tfg.MotoMammiApplicationAGB.Models.CustomerDTO;
import com.dam.tfg.MotoMammiApplicationAGB.Models.InterfazDTO;
import com.dam.tfg.MotoMammiApplicationAGB.Models.PartsDTO;
import com.dam.tfg.MotoMammiApplicationAGB.Models.ProviderDTO;
import com.dam.tfg.MotoMammiApplicationAGB.Models.Translation;
import com.dam.tfg.MotoMammiApplicationAGB.Models.VehicleDTO;
import com.dam.tfg.MotoMammiApplicationAGB.Repositories.CustomerRepository;
import com.dam.tfg.MotoMammiApplicationAGB.Repositories.InterfazRepository;
import com.dam.tfg.MotoMammiApplicationAGB.Repositories.PartsRepository;
import com.dam.tfg.MotoMammiApplicationAGB.Repositories.ProviderRepository;
import com.dam.tfg.MotoMammiApplicationAGB.Repositories.TranslationRepository;
import com.dam.tfg.MotoMammiApplicationAGB.Utils.HibernateUtil;
import com.dam.tfg.MotoMammiApplicationAGB.Utils.PropertiesConfig;
import com.dam.tfg.MotoMammiApplicationAGB.Utils.Utils;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers.SqlDateDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.dam.tfg.MotoMammiApplicationAGB.Utils.Constants;
import com.dam.tfg.MotoMammiApplicationAGB.Utils.Errors;

import org.springframework.stereotype.Service;


//proceso que lee el fichero
@Service
public class ProccessServiceImpl implements ProccessService{
 
    public static void main(String[] args) {
        ProccessServiceImpl psi = new ProccessServiceImpl();
       
        psi.readInfoFile(Constants.VEHICLES,null,null);
        psi.proccessIntegrateInfo(Constants.PARTS,null,null);
    
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
        InterfazRepository interfazRepository = new InterfazRepository();
        List <InterfazDTO> interfazListWithStatusN;
        Gson gson = new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();
        TranslationRepository tr = new TranslationRepository();
        switch (source) {

            case Constants.CUSTOMER:
             //sacar una lista de la tabla MM_interfaz con los que tengan el status process en N
            interfazListWithStatusN = interfazRepository.getRecordsWithStatusInN(Constants.CUSTOMER);
            CustomerDTO customer;
            CustomerRepository customerRepository = new CustomerRepository();
            String DNI,name,firstSurname,lastSurname,email,birthDate,postalCode,streetType,city,number,phone,gender,licenceType;
            if (interfazListWithStatusN!=null) {
            //serializar el objeto ya sea cutomer
            for (InterfazDTO interfazDTO : interfazListWithStatusN) {
                if (codProv==null) { codProv = interfazDTO.getCodProv();}//en caso de que no nos lo mande lo cogemos de interfaz
                if (date==null) {date = Utils.timeNow().toString();} //igual con la fecha

                Map<String, String> customerData = gson.fromJson(interfazDTO.getcontJson(), Map.class);
                 DNI = customerData.get("DNI");
                 name = customerData.get("name");
                 firstSurname = customerData.get("first_surname");
                 lastSurname = customerData.get("last_surname");
                 email = customerData.get("email");
                 birthDate = customerData.get("birth_date");
                 postalCode = customerData.get("postal_code");
                 streetType = customerData.get("street_type");
                 city = customerData.get("city");
                 number = customerData.get("number");
                 phone = customerData.get("phone");
                 gender = customerData.get("gender");
                 licenceType = customerData.get("licence_type");

                //traducimos a traves de la tabla translation dependiendo del proveedor
                if (!streetType.isEmpty()) { streetType = tr.getTraductionByProvider(streetType, codProv);}  
                if (!city.isEmpty()) { city = tr.getTraductionByProvider(city, codProv);}  
                if (!gender.isEmpty()) { gender = tr.getTraductionByProvider(gender, codProv);} 
                if (!licenceType.isEmpty()) { licenceType = tr.getTraductionByProvider(licenceType, codProv);} 

                customer = new CustomerDTO(DNI,name,firstSurname,lastSurname,email,birthDate,postalCode,streetType,city,number,phone,gender,licenceType); 
                customerRepository.insertCustomerToMainTable(customer);

                //actualizar estado interfaz a --> P
                interfazDTO.setStatusProcess(Constants.SP_P);
                
                //insertamos el nuevo json con los datos actualizados
                interfazDTO.setContJson(gson.toJson(customer));  
                //actualizamos el estado con el nuevo json
                interfazRepository.updateInterfaz(interfazDTO);


            }
              
        }
        
            //una vez lo tenga serializado tengo que la traduccion EXTERNAL_COD --> MM_TRANSLATION  traducciendo por ejemplo street type
            //insertar en las tablas maestras ya sea mm_customer...
            //y una vez ejecutado la insercion actualizamos el statusprocess en la tabla interfaz con el valor P = procesado
                break;

            case Constants.PARTS:
            interfazListWithStatusN = interfazRepository.getRecordsWithStatusInN(Constants.PARTS);
            PartsDTO parts;
            PartsRepository partsRepository = new PartsRepository();
            String id,codigoExterno,internalCod,descripcion,matricula,idInvoice,dniVehicle,dateNotification;
            if (interfazListWithStatusN!=null) {
                for (InterfazDTO interfazDTO : interfazListWithStatusN) {
                    if (codProv==null) { codProv = interfazDTO.getCodProv();}//en caso de que no nos lo mande lo cogemos de interfaz
                    if (date==null) {date = Utils.timeNow().toString();} //igual con la fecha
    
                    Map<String, String> partsData = gson.fromJson(interfazDTO.getcontJson(), Map.class);
                     codigoExterno = partsData.get("codigoExterno");
                     internalCod = partsData.get("internalCod");
                     descripcion = partsData.get("descripcion");
                     matricula = partsData.get("dateNotification");
                     idInvoice = partsData.get("idInvoice");
                     dniVehicle = partsData.get("dniVehicle");
                     dateNotification = partsData.get("dateNotification");
                    //aqui traduciriamos los campos que fueran necesarios pero parts no necesita serlo.
                    parts = new PartsDTO(codigoExterno, internalCod, descripcion, matricula, idInvoice, dniVehicle, dateNotification);
                    parts.setId(Utils.randomID()); //le generamos un id;
                    
                    //insertamos en la tabla maestra
                    partsRepository.insertPartsToMainTable(parts);
    
                    //actualizar estado interfaz a --> P
                    interfazDTO.setStatusProcess(Constants.SP_P);
                    //insertamos el nuevo json con los datos actualizados
                    interfazDTO.setContJson(gson.toJson(parts));  
                    //actualizamos el estado con el nuevo json
                    interfazRepository.updateInterfaz(interfazDTO);
    
    
                }
    
    
            }
            




            
                break;
            case Constants.VEHICLES:
            interfazListWithStatusN = interfazRepository.getRecordsWithStatusInN(Constants.CUSTOMER);
                break;

            default:
                break;
        }




    }

    @Override
    public void readInfoFile(String source,String codProv, String date){
        
        try {
            //en caso de que este vacio le pondra el dia de hoy
           String dateFile = date==null ? new SimpleDateFormat("yyyy-MM-dd").format(new Date())
                                        : new SimpleDateFormat("yyyy-MM-dd").format(date);
                      
        // *{RESOURCE}--> indica que la fuente de información que se quiere leer(OBLIGATORIO).
        // *{CODPROV}--> indica el código del proveedor que queremos leer la información(OPCIONAL), en caso de vacío se obtienen de todos los proveedores activos.
        // *{DATE}--> indica la fecha que queremos leer la información(OPCIONAL), en caso de vacío se obtiene la fecha actual de sistema.
                                                                   
          
                ProviderRepository PR = new ProviderRepository();
                String vehicleFile;
                String partFile;
                String customerFile;
                List<ProviderDTO> listaProveedoresActivos; 
              
                //devolvera los proveedores activos que tenga el mismo codProv, si es null devolvera todos
                listaProveedoresActivos = PR.getAllUsersPovidersActive(codProv, dateFile);
               
                //recorremos los proveedores activos
                for (ProviderDTO proveedor : listaProveedoresActivos) {
                    // Recuperamos el código del proveedor
                    String codProvActivo = proveedor.getCodigoProveedor();
                    
                    //buscar archivo con estos nombres por cada proveedor
                    customerFile= PropertiesConfig.PATH + PropertiesConfig.CUSTOMER_PATH_FILE + codProvActivo + dateFile + PropertiesConfig.FORMAT;
                    vehicleFile= PropertiesConfig.PATH + PropertiesConfig.VEHICLE_PATH_FILE + codProvActivo + dateFile + PropertiesConfig.FORMAT;
                    partFile= PropertiesConfig.PATH + PropertiesConfig.PARTS_PATH_FILE +  codProvActivo + dateFile + PropertiesConfig.FORMAT;
                    
                  
                    //tienes que leer los 3 archivos
                    switch (source) {
                        case Constants.CUSTOMER:
                        readFile(customerFile,Constants.CUSTOMER, codProvActivo);
                        break;

                        case Constants.VEHICLES:
                        readFile(vehicleFile,Constants.VEHICLES, codProvActivo);
                        break;

                        case Constants.PARTS:
                        readFile(partFile,Constants.PARTS, codProvActivo);
                        break;

                    }
                  
              }                          
           
            

   

        } catch (Exception e) {
           
            e.printStackTrace();
        }
    }
    
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
            br.close();
        
        } catch (FileNotFoundException e) {
            System.err.println(Errors.FILE_DONT_EXIST_ERROR + e.getMessage());
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
        
        // java.sql.Date dateSql = Utils.stringToSqlDateSimpleFormat(splitData[5]);
        
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

private void insertPartsToInterfaceTable(String[] splitData,InterfazRepository interfazRepository,String codprov){
    try {
        PartsDTO partsDTO = new PartsDTO(splitData[0],splitData[1],splitData[2],splitData[3],splitData[4],splitData[5],splitData[6]);
        
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
        String errorMessage = e.getMessage()!= null ? e.getMessage() : Errors.UNKNOW_ERROR ; //-> valido que no me meta null
        Session session = HibernateUtil.getSession();
        InterfazDTO interfazDTO = new InterfazDTO();

        interfazDTO.setCreateDate(Utils.timeNow());
        interfazDTO.setStatusProcess(Constants.SP_E); //--> StatusProcess: E -> ERROR
        interfazDTO.setCodError(Errors.ERROR_HIBERNATE);//-> dentro del catch de hibernate este es el causante de todos nuestros males
        interfazDTO.setErrorMessage(errorMessage);//--> Mensaje de error

        session.beginTransaction();
        session.save(interfazDTO);//Guardamos el objeto
        session.getTransaction().commit();
        session.close();
        
    } catch (Exception exception) {
       System.err.println(exception.getMessage());
       System.err.println(Errors.UNKNOW_ERROR);//en caso de que estalle tambien esto pues ERROR DESCONOCIDO
    }
}
 
}
