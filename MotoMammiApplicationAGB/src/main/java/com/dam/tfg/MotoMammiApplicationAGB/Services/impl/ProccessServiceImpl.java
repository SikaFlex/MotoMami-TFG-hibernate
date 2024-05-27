package com.dam.tfg.MotoMammiApplicationAGB.Services.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;


import com.dam.tfg.MotoMammiApplicationAGB.Models.CustomerDTO;
import com.dam.tfg.MotoMammiApplicationAGB.Models.InterfazDTO;
import com.dam.tfg.MotoMammiApplicationAGB.Models.InvoiceDTO;
import com.dam.tfg.MotoMammiApplicationAGB.Models.InvoiceDataToPrintDTO;
import com.dam.tfg.MotoMammiApplicationAGB.Models.PartsDTO;
import com.dam.tfg.MotoMammiApplicationAGB.Models.ProviderDTO;
import com.dam.tfg.MotoMammiApplicationAGB.Models.Translation;
import com.dam.tfg.MotoMammiApplicationAGB.Models.VehicleDTO;
import com.dam.tfg.MotoMammiApplicationAGB.Repositories.CustomerRepository;
import com.dam.tfg.MotoMammiApplicationAGB.Repositories.InterfazRepository;
import com.dam.tfg.MotoMammiApplicationAGB.Repositories.InvoiceRepository;
import com.dam.tfg.MotoMammiApplicationAGB.Repositories.PartsRepository;
import com.dam.tfg.MotoMammiApplicationAGB.Repositories.ProviderRepository;
import com.dam.tfg.MotoMammiApplicationAGB.Repositories.TranslationRepository;
import com.dam.tfg.MotoMammiApplicationAGB.Repositories.VehiclesRepository;
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
 
  


    @Override
    public void voidGenerateInvoice(String codProv, String date) {
        try {
            Date dateNow = new Date();                                                                      //en caso de que no mande nada buscara por el mes
            InvoiceRepository invoiceRepository = new InvoiceRepository();
            CustomerRepository customerRepository = new CustomerRepository();
            VehiclesRepository vehiclesRepository = new VehiclesRepository();
            
            java.sql.Date dateFinally = date !=null ? Utils.stringToSqlDate(date) : Utils.getLastMonth(new java.sql.Date(System.currentTimeMillis()));
            date=new SimpleDateFormat("yyyyMM").format(new Date()).toString();
  
            String path= PropertiesConfig.PATH + PropertiesConfig.INVOICE_PATH_FILE + codProv + Constants.UNDERSCORE + date + PropertiesConfig.INVOICE_FORMAT;
    
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File(path)));
            
            List<InvoiceDTO> invoicesList = invoiceRepository.getInvoiceFromUntilToday(codProv,dateFinally);
            CustomerDTO customer;
            VehicleDTO vehicle; 
            InvoiceDataToPrintDTO invoiceDataToPrintDTO = new InvoiceDataToPrintDTO();
            
             
            String dni;
            double iva;
      

            if (invoicesList == null) { return;}
            bw.write(invoiceDataToPrintDTO.firtsLineCSV()+"\n");
            for (InvoiceDTO invoiceDTO : invoicesList) {
                //esto se podria hacer con INNER JOIN pero Hibernate las relaciones 1-M etc... ya tu sabe.
                dni = invoiceDTO.getDni_Cliente();
                customer=customerRepository.getCustomerByDNI(dni);
                vehicle= vehiclesRepository.getVehicleByDNI(dni);
                iva = invoiceDTO.getIva();

                invoiceDataToPrintDTO.setDNI(dni);
                invoiceDataToPrintDTO.setCodProv(codProv);
                invoiceDataToPrintDTO.setFecha_emision(dateFinally);
                invoiceDataToPrintDTO.setNombre(customer.getName());
                invoiceDataToPrintDTO.setFirt_Surname(customer.getFirst_surname());
                invoiceDataToPrintDTO.setLast_Surname(customer.getLast_surname());
                invoiceDataToPrintDTO.setTipoDeVehiculo(vehicle.getTipoVehiculo());
                invoiceDataToPrintDTO.setMatricula(vehicle.getMatricula());
                invoiceDataToPrintDTO.setDireccionEmpresa(invoiceDTO.getDireccionEmpresa());
                invoiceDataToPrintDTO.setNombreEmpresa(invoiceDTO.getNombreEmpresa());
                invoiceDataToPrintDTO.setCifEmpresa(invoiceDTO.getCifEmpresa());
                invoiceDataToPrintDTO.setCoste(invoiceDTO.getCoste()*(iva/100)+invoiceDTO.getCoste());
                invoiceDataToPrintDTO.setDivisa(invoiceDTO.getDivisa());
                invoiceDataToPrintDTO.setIva(invoiceDTO.getIva());

                bw.write(invoiceDataToPrintDTO.printCSV()+"\n");

            }
            bw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
            
    }

      //SEGUNDO PROCESO
    @Override
    public void proccessIntegrateInfo(String source,String codProv, String date){
        if (source==null) {System.err.println(Errors.ERROR_PROCES_FILE); return;}
        String dateFile = date==null ? new SimpleDateFormat("yyyy-MM-dd").format(new Date())
                                     : new SimpleDateFormat("yyyy-MM-dd").format(date);

        InterfazRepository interfazRepository = new InterfazRepository();
        List <InterfazDTO> interfazListWithStatusN=new ArrayList<InterfazDTO>();
        TranslationRepository tr = new TranslationRepository();
        switch (source) {
            case Constants.CUSTOMER:
            processCustomerAndInsertInMainTable(interfazRepository,interfazListWithStatusN,tr,codProv,dateFile);
            break;
            case Constants.PARTS:
            processPartAndInsertInMainTable(interfazRepository,interfazListWithStatusN,tr,codProv,dateFile);
                break;
            case Constants.VEHICLES:
            processVehicleAndInsertInMainTable(interfazRepository, interfazListWithStatusN, tr, codProv, dateFile);
                break;
        }
    }

    @Override
    public void readInfoFile(String source,String codProv, String date){
        
        try {
            if (source==null) {System.err.println(Errors.ERROR_SOURCE); return;}
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
                String underscore ="_";
                List<ProviderDTO> listaProveedoresActivos; 
              
                //devolvera los proveedores activos que tenga el mismo codProv, si es null devolvera todos
                listaProveedoresActivos = PR.getAllUsersPovidersActive(codProv, dateFile);
               if (codProv==null) {
                 //recorremos los proveedores activos
                 for (ProviderDTO proveedor : listaProveedoresActivos) {
                    // Recuperamos el código del proveedor
                    String codProvActivo = proveedor.getCodigoProveedor();
                    
                    //buscar archivo con estos nombres por cada proveedor
                    customerFile= PropertiesConfig.PATH + PropertiesConfig.CUSTOMER_PATH_FILE + codProvActivo +Constants.UNDERSCORE+ dateFile + PropertiesConfig.FORMAT_DAT;
                    vehicleFile= PropertiesConfig.PATH + PropertiesConfig.VEHICLE_PATH_FILE + codProvActivo +Constants.UNDERSCORE+ dateFile + PropertiesConfig.FORMAT_DAT;
                    partFile= PropertiesConfig.PATH + PropertiesConfig.PARTS_PATH_FILE + codProvActivo +Constants.UNDERSCORE+ dateFile + PropertiesConfig.FORMAT_DAT;
                                      
                    switch (source) {
                        case Constants.CUSTOMER:
                        readFileAndInsertToInterfaz(customerFile,Constants.CUSTOMER, codProvActivo);
                        break;

                        case Constants.VEHICLES:
                        readFileAndInsertToInterfaz(vehicleFile,Constants.VEHICLES, codProvActivo);
                        break;

                        case Constants.PARTS:
                        readFileAndInsertToInterfaz(partFile,Constants.PARTS, codProvActivo);
                        break;

                    }
                } 
               }else{
                
                switch (source) {
                    case Constants.CUSTOMER:
                        source = PropertiesConfig.CUSTOMER_PATH_FILE;
                        break;
                    case Constants.VEHICLES:
                        source = PropertiesConfig.VEHICLE_PATH_FILE;
                        break;
                    case Constants.PARTS:
                        source = PropertiesConfig.PARTS_PATH_FILE;
                        break;
                }

                String path= PropertiesConfig.PATH+ source +  codProv + underscore+dateFile + PropertiesConfig.FORMAT_DAT;
                readFileAndInsertToInterfaz(path, source, codProv);
               }
                                        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    







///////////////////////////////////////////////////////////////////     CUSTOMER   ///////////////////////////////////////////////////////////////////////////////////
private void readFileAndInsertToInterfaz(String pathCompost, String source,String codprov ){
    try {
        InterfazRepository interfazRepository = new InterfazRepository();
        BufferedReader br = new BufferedReader(new FileReader(new File(pathCompost)));
        String linea;
        
        while ((linea=br.readLine())!= null) {
            /*
             * SE QUITA ESTE CONTROL DE ERRORES POR DECISION PERSONAL YA QUE ES UNA MINA DE BUGS Y COMO ALFINAL ESTO SE PUEDE ACORDAR CON EL CLIENTE
             * SE DECIDE QUE EL FORMATO SERA SIN LA FILA DE LAS COLUMNAS ARRIBA 
            */
            // if (linea.contains(source.SKIP_VEHICLE) || linea.contains(source.SKIP_CUSTOMER) || linea.contains(source.SKIP_PARTS) || linea == null){linea=br.readLine();}
            String[] splitData =linea.split(","); 
            

            switch (source) {


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



    private void processCustomerAndInsertInMainTable(InterfazRepository interfazRepository,List <InterfazDTO> interfazListWithStatusN,TranslationRepository tr,String codProv,String date ){
         try {
            Gson gson = new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();

             //sacar una lista de la tabla MM_interfaz con los que tengan el status process en N
             interfazListWithStatusN = interfazRepository.getRecordsWithStatusInN(Constants.CUSTOMER);
             CustomerDTO customer= new CustomerDTO();
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
         } catch (Exception e) {
            e.printStackTrace();
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

private void processVehicleAndInsertInMainTable(InterfazRepository interfazRepository,List <InterfazDTO> interfazListWithStatusN,TranslationRepository tr,String codProv,String date ){
    try {
    Gson gson = new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();
    interfazListWithStatusN = interfazRepository.getRecordsWithStatusInN(Constants.VEHICLES);
    VehicleDTO vehicle= new VehicleDTO();
     VehiclesRepository vehicleRepository = new VehiclesRepository();
    if (interfazListWithStatusN!=null) {
        for (InterfazDTO interfazDTO : interfazListWithStatusN) {
            if (codProv==null) { codProv = interfazDTO.getCodProv();}//en caso de que no nos lo mande lo cogemos de interfaz
            if (date==null) {date = Utils.timeNow().toString();} //igual con la fecha

            String colorTranslated,color;
            Map<String, String> vehicleData = gson.fromJson(interfazDTO.getcontJson(), Map.class);

                vehicle.setId(Utils.randomID());
                vehicle.setMatricula(vehicleData.get("matricula"));
                vehicle.setTipoVehiculo(vehicleData.get("tipoVehiculo"));
                vehicle.setMarcaVehiculo(vehicleData.get("marcaVehiculo"));
                vehicle.setModelo(vehicleData.get("modelo"));
                color = vehicleData.get("color");
                //traducimos el color mediante la tabla auxiliar translation
                colorTranslated = tr.getTraductionByProvider(color,codProv);

                color = colorTranslated.equals(Errors.ERROR_TRADUCTION) ? color : colorTranslated;
                vehicle.setColor(color);
                vehicle.setDniUsuario(vehicleData.get("dniUsuario"));

            //guarda vehiculo en tabla maestra
            vehicleRepository.insertVehicleToMainTable(vehicle);
            //actualizar estado interfaz a --> P
            interfazDTO.setStatusProcess(Constants.SP_P);
            //insertamos el nuevo json con los datos actualizados
            interfazDTO.setContJson(gson.toJson(vehicle));  
            //actualizamos el estado con el nuevo json
            interfazRepository.updateInterfaz(interfazDTO);
        }
    }
        
    } catch (Exception e) {
        e.printStackTrace();
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


private void processPartAndInsertInMainTable(InterfazRepository interfazRepository,List <InterfazDTO> interfazListWithStatusN,TranslationRepository tr,String codProv,String date ){
    try {
    Gson gson = new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();
    interfazListWithStatusN = interfazRepository.getRecordsWithStatusInN(Constants.PARTS);
            PartsDTO parts=new PartsDTO();
            PartsRepository partsRepository = new PartsRepository();
            String id,codigoExterno,internalCod,descripcion,matricula,idInvoice,dniVehicle,dateNotification;
            if (interfazListWithStatusN!=null) {
                for (InterfazDTO interfazDTO : interfazListWithStatusN) {
                    if (codProv==null) { codProv = interfazDTO.getCodProv();}//en caso de que no nos lo mande lo cogemos de interfaz
                    if (date==null) {date = Utils.timeNow().toString();} //igual con la fecha
                    parts = new PartsDTO();
    
                    Map<String, String> partsData = gson.fromJson(interfazDTO.getcontJson(), Map.class);
                    //aqui traduciriamos los campos que fueran necesarios pero parts no necesita serlo.
                     parts.setId(Utils.randomID()); //le generamos un id;
                     parts.setCodigoExterno( partsData.get("codigoExterno"));
                     parts.setInternalCod(partsData.get("internalCod"));
                     parts.setDescripcion(partsData.get("descripcion"));
                     parts.setMatricula(partsData.get("matricula"));
                     parts.setIdInvoice(partsData.get("idInvoice"));
                     parts.setDniVehicle(partsData.get("dniVehicle"));
                     parts.setDateNotification(Utils.stringToSqlDate(partsData.get("dateNotification")));
                    
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
            
}catch(Exception e){
        e.printStackTrace();
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
    Session session = null;
    try {
        String errorMessage = e.getMessage()!= null ? e.getMessage() : Errors.UNKNOW_ERROR ; //-> valido que no me meta null
        session = HibernateUtil.getSession();
        InterfazDTO interfazDTO = new InterfazDTO();
        
        interfazDTO.setId(Utils.randomID());
        interfazDTO.setCreateDate(Utils.timeNow());
        interfazDTO.setStatusProcess(Constants.SP_E); //--> StatusProcess: E -> ERROR
        interfazDTO.setCodError(Errors.ERROR_HIBERNATE);//-> dentro del catch de hibernate este es el causante de todos nuestros males
        interfazDTO.setErrorMessage(errorMessage);//--> Mensaje de error
        interfazDTO.setStatusProcess(Constants.SP_E);
        session.beginTransaction();
        session.save(interfazDTO);//Guardamos el objeto
        session.getTransaction().commit();
      
        
    } catch (Exception exception) {
       System.err.println(exception.getMessage());
       System.err.println(Errors.UNKNOW_ERROR);//en caso de que estalle tambien esto pues ERROR DESCONOCIDO
    }finally{
        if (session!=null) { session.close();} 
    }
}
 
}
