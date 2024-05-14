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

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Value;
import org.aspectj.apache.bcel.classfile.Constant;
import org.hibernate.Hibernate;

import com.dam.tfg.MotoMammiApplicationAGB.Models.InterfazDTO;
import com.dam.tfg.MotoMammiApplicationAGB.Models.PartsDTO;
import com.dam.tfg.MotoMammiApplicationAGB.Models.ProviderDTO;
import com.dam.tfg.MotoMammiApplicationAGB.Models.User.CustomerDTO;
import com.dam.tfg.MotoMammiApplicationAGB.Models.User.VehicleDTO;
import com.dam.tfg.MotoMammiApplicationAGB.Services.ProccessService;
import com.dam.tfg.MotoMammiApplicationAGB.Utils.HibernateUtil;
import com.google.gson.Gson;
import com.dam.tfg.MotoMammiApplicationAGB.Utils.Constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;


//proceso que lee el fichero
@Service
public class ProccessServiceImpl implements ProccessService{
 
    // @Value("${path.in}")
    
    public String path="C:\\Users\\Usuario\\Desktop\\MotoMami-TFG-hibernate\\MotoMammiApplicationAGB\\src\\main\\resources\\in\\";// /resources/in/

    @Value("${file.format.dat}")
    public String format=".dat";//.dat

    @Value("${vehicle.path.file}")
    String vehiclesPath="MM_insurance_vehicle";//MM_insurance_vehicle

    @Value("${customer.path.file}")
    String customerPath="MM_insurance_customers";//MM_insurance_customers

    @Value("${parts.path.file}")
    String partsPath="MM_insurance_parts";//MM_insurance_parts
    
    //leer archivo
    //con los 3 parametros buscamos el archivo
    //[source]+[codProv]+[date]

    public static void main(String[] args) {
        
        ProccessServiceImpl psi = new ProccessServiceImpl();
        psi.readInfoFile("CUS",null,null);
    
        



    }


    //TERCER PROCESO 1 VEZ AL MES
    @Override
    public void voidGenerateInvoice(String codProv, String date) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'voidGenerateInvoice'");
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
                                        :new SimpleDateFormat("yyyy-MM-dd").format(date);
                      
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
                vehicleFile= path + vehiclesPath + codProvActivo + dateFile + format;
                partFile= path + partsPath +  codProvActivo + dateFile + format;
                customerFile= path + customerPath + codProvActivo + dateFile + format;
                //tienes que leer los 3 archivos
                readFile(customerFile,Constants.CUSTOMER, codProvActivo);
                
      

            }

   

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readFile(String path, String constants,String codprov ){
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(path)));
            String linea;
            ArrayList<CustomerDTO>customerList = new ArrayList<>();
            ArrayList<VehicleDTO>vehiclesList = new ArrayList<>();
            ArrayList<PartsDTO>partsList = new ArrayList<>();
            InterfazRepository interfazRepository = null;

            while ((linea=br.readLine())!= null) {
                // if (linea.contains("DNI")/*|| linea.contains("ID_VEHICLE") || linea.contains("ID_PARTS") */) {linea=br.readLine();}//si contiene DNI Skipeala
                String[] splitData =linea.split(",");//spliteamos la linea
                switch (constants) {
                    case "VEHICLES":
                //    VehicleDTO vehicleDTO = new vehicleDTO(tipoVehiculo, matricula, marcaVehiculo, modelo );
                    // VehicleDTO vehicleDTO = new VehicleDTO(
                    //     splitData[0],splitData[1],splitData[2],splitData[3]
                    // );
                    // vehiclesList.add(vehicleDTO);

                    //vehiculo id -- dni


                    //TODO: que tengo que validar de vehicles?
                    break;
                    //TODO: tratar archivo csv con los campos de customer    
                    // vehiclesList

                    case "PART":
                        //external code id

                    //splitear y insertar en la la tabla interfaces

                    PartsDTO partsDTO = new PartsDTO(splitData[0],splitData[1],splitData[2],splitData[3]);
                    partsList.add(partsDTO);

                    // TODO: que tengo que validar de PARTS?
                
                    partsList.add(partsDTO);
                        break;

                    case "CUS":
                          CustomerDTO customerDTO = new CustomerDTO(
                            splitData[0],splitData[1],splitData[2],splitData[3],
                            splitData[4],splitData[5],splitData[6],splitData[7],
                            splitData[8],splitData[9],splitData[10],splitData[11],
                            splitData[12]);
                        customerList.add(customerDTO);
 
                        if (!doValidatePersonIsInInterface(customerDTO,codprov)) { //Si no esta la persona en la tabla MM_interfaz
                            customerDTO.setOperatio(Constants.NEW); //Insertarlo con la operacion como :new es un campo que solo rellena la primera vez) -> OPERATION: "NEW"
                            interfazRepository.insertCustomerToMMInterfaz(customerDTO,codprov); //procedemos a hacer el insert en base de datos

                        }else if(existJsonAndIsDiferent(customerDTO)){//en caso de ya exista un registo, mirara que el json no sea igual lo ingreso en update
                            customerDTO.setOperatio(Constants.UPD);//Cambiamos el operation a UPD
                            interfazRepository.insertCustomerToMMInterfaz(customerDTO,codprov);
                        }
                        //En caso de que el json ya este y sea igual no se hace nada
                        }
                        break;
              
                }
                
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param customerDTO el objeto que va a comprobar si contiene un json en la tabla Interfaz
     * @param jsonToTheFile el json que se va usar para comprar con la base de datos
     * Esta funcion va a comprobar si existe un json y si es difente o igual al que ya esta almacenado en base de datos
     * @return false: en caso de que exista pero sea igual
     * @return true: en caso de que el json que este en base de datos sea diferente al que tenemos
     * **/
private boolean existJsonAndIsDiferent (CustomerDTO customerDTO){
        InterfazRepository interfazRepository = null;
        String jsonInterfaz = interfazRepository.haveJsonWithCustomer(customerDTO);
        return jsonInterfaz.isEmpty() ? false : true; 
        //en caso de que sean iguales devolver un string vacio de ser asi se cumplira la condicion y sera false
        //por lo tanto no entrara en el condicional y no hara nada 

    }


/**
 * @param cutomerDTO objeto customer que vamos a mirar en la base de datos si esta
 * @param  codProv Codigo de proveedor que vanmos a usar en la query
 * 
 */
private boolean doValidatePersonIsInInterface(CustomerDTO customerDTO,String codProv) {
        InterfazRepository IR = new InterfazRepository();
        InterfazDTO interfazDTO = IR.getPersonOfInterfazWithCustomer(customerDTO,codProv);
        return interfazDTO == null ?  false : true ; 
        //en caso de que sea null devolvera false y si encuentra algo no sera null entonce es true

    }




 
}
