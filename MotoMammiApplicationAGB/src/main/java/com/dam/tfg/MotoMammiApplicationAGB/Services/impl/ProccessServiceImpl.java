package com.dam.tfg.MotoMammiApplicationAGB.Services.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
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
import com.dam.tfg.MotoMammiApplicationAGB.Models.User.VehicleDTO;
import com.dam.tfg.MotoMammiApplicationAGB.Repositories.InterfazRepository;
import com.dam.tfg.MotoMammiApplicationAGB.Repositories.ProviderRepository;
import com.dam.tfg.MotoMammiApplicationAGB.Utils.HibernateUtil;
import com.dam.tfg.MotoMammiApplicationAGB.Utils.PropertiesConfig;
import com.google.gson.Gson;
import com.dam.tfg.MotoMammiApplicationAGB.Utils.Constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;


//proceso que lee el fichero
@Service
public class ProccessServiceImpl implements ProccessService{
 
    // public static void main(String[] args) {
    //     ProccessServiceImpl psi = new ProccessServiceImpl();
       

    //     psi.readInfoFile("CUS",null,null);
    
        



    // }


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
                // readFile(vehicleFile,Constants.VEHICLES, codProvActivo);
                // readFile(partFile,Constants.PARTS, codProvActivo);

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
                // if (linea.contains("DNI")/*|| linea.contains("ID_VEHICLE") || linea.contains("ID_PARTS") */) {linea=br.readLine();}//si contiene DNI Skipeala
                String[] splitData =linea.split(",");//spliteamos la linea
                switch (constants) {
                    case "VEHICLES":
                    VehicleDTO vehicleDTO = new VehicleDTO(splitData[0],splitData[1],splitData[2],splitData[3],splitData[4]);
                        
                    //VehicleDTO vehicleDTO = new vehicleDTO(tipoVehiculo, matricula, marcaVehiculo, modelo, color);

                    //vehiculo id -- dni
                    //MODIFICAR STATUS PROCESS DE LA TABLA INTERFAZ


                    //TODO: validamos que no haya un json en la tabla interfaz filtrando por matricula -> dni y codProv
                    //de ser asi lo insertamos como new si no hubiese mas y upd si tenemos que actualizarlo
                    break;
                    //TODO: tratar archivo csv con los campos de customer    
                    // vehiclesList

                    case "PART":
                    PartsDTO partsDTO = new PartsDTO(splitData[0],splitData[1],splitData[2],splitData[3]);
             
                    //external code id

                    //splitear y insertar en la la tabla interfaces
                    //lo mismo que ocn el resto
                    break;
                        
                    case "CUS":
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





@SuppressWarnings("deprecation")
private void insertCustomerToInterfaceTable(String[] splitData,InterfazRepository interfazRepository,String codprov){
    try {
        CustomerDTO customerDTO = new CustomerDTO(splitData[0],splitData[1],splitData[2],splitData[3],splitData[4],splitData[5],splitData[6],
                                                  splitData[7],splitData[8],splitData[9],splitData[10],splitData[11],splitData[12]);
 
     if (notIsPersonInInterface(customerDTO,codprov)) { //Si no esta la persona en la tabla MM_interfaz
         interfazRepository.insertCustomerToMMInterfaz(customerDTO,codprov,Constants.NEW); //Insertarlo con la operacion como :new es un campo que solo rellena la primera vez) -> OPERATION: "NEW"

     }else if(existJsonAndIsDiferent(customerDTO,codprov)){//en caso de ya exista un registo, mirara que el json no sea igual lo ingreso con OPERATION = "UPD" ->update
         interfazRepository.insertCustomerToMMInterfaz(customerDTO,codprov,Constants.UPD);//Cambiamos el operation a UPD
     }
        //En caso de que el json ya este y sea igual no se hace nada
     
    } catch (Exception e) {
     /* En caso de error durante el proceso se guardara el error en la tabla interfaz */
     try {
         String errorMessage = e.getMessage()!= null ? e.getMessage() : "NO SE PUDO RECUPERAR EL MENSAJE DE ERROR" ; //-> valido que no me meta null
         Session session = HibernateUtil.getSession();
         InterfazDTO interfazDTO = new InterfazDTO();

         interfazDTO.setStatusProcess(Constants.SP_E); //--> StatusProcess: E -> ERROR
         interfazDTO.setCodError(Constants.ERROR_HIBERNATE);//-> dentro del catch de hibernate este es el causante
         interfazDTO.setErrorMessage(errorMessage);//--> Mensaje de error

         session.beginTransaction();
         session.save(interfazDTO);//Guardamos el objeto
         session.getTransaction().commit();
         session.close();

         System.err.println(e.getMessage());
         
     } catch (Exception exception) {
        System.err.println(exception.getMessage());
        System.err.println(Constants.UNKNOW_ERROR);//en caso de que estalle tambien esto pues ERROR DESCONOCIDO
     }
 }
}


    /**
     * @param customerDTO el objeto que va a comprobar si contiene un json en la tabla Interfaz
     * @param jsonToTheFile el json que se va usar para comprar con la base de datos
     * Esta funcion va a comprobar si existe un json y si es difente o igual al que ya esta almacenado en base de datos
     * @return  true si no hay el contJson del la tabla interfaz no es igual a nuestro archivo
     * 
     * **/
private boolean existJsonAndIsDiferent (CustomerDTO customerDTO,String codProv){
        InterfazRepository interfazRepository = new InterfazRepository();
        InterfazDTO jsonInterfaz = interfazRepository.haveJsonWithCustomer(customerDTO,codProv);
        return jsonInterfaz != null ? true : false; 
       //Si es null significa el json de base de datos no es igual entonces devolvera true para que pueda entrar en el condicional
       //y guardar el nuevo record en la base de datos. En caso de que encuentre uno igual devolvera false para que no haga nada.
    }


/**
 * @param cutomerDTO objeto customer que vamos a mirar en la base de datos si esta
 * @param  codProv Codigo de proveedor que vanmos a usar en la query
 * 
 */
private boolean notIsPersonInInterface(CustomerDTO customerDTO,String codProv) {
        InterfazRepository IR = new InterfazRepository();
        InterfazDTO interfazDTO = IR.getPersonOfInterfazWithCustomer(customerDTO,codProv);
        return interfazDTO == null ?  true : false ; 
        //si no encuentra algo sigunifica que esta en la base datos entonces devolvera true

    }
 
}
