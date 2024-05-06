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
import com.dam.tfg.MotoMammiApplicationAGB.Models.ProviderDTO;
import com.dam.tfg.MotoMammiApplicationAGB.Models.User.CustomerDTO;
import com.dam.tfg.MotoMammiApplicationAGB.Models.User.VehicleDTO;
import com.dam.tfg.MotoMammiApplicationAGB.Utils.HibernateUtil;
import com.dam.tfg.MotoMammiApplicationAGB.Utils.Constants;



//proceso que lee el fichero
public class ProccessServiceImpl {
 
    @Value("${path.in}")
    String path;// /resources/in/

    @Value("${file.format.dat}")
    String format;//.dat

    @Value("${vehicle.path.file}")
    String vehiclesPath;//MM_insurance_vehicle

    @Value("${customer.path.file}")
    String customerPath;//MM_insurance_customers

    @Value("${parts.path.file}")
    String partsPath;//MM_insurance_parts
    
    //leer archivo
    //con los 3 parametros buscamos el archivo
    //[source]+[codProv]+[date]

    public static void main(String[] args) {
        
        ProccessServiceImpl psi = new ProccessServiceImpl();
        psi.readInfoFile("CUS",null,null);

    }
    public void readInfoFile(String source,String codProv, String date){
        try {
       
            //en caso de que este vacio le pondra el dia de hoy
           String dateFile = date==null ? new SimpleDateFormat("yyyy-MM-dd").format(new Date())
                                        :new SimpleDateFormat("yyyy-MM-dd").format(date);
                      

            ProviderRepository PR = new ProviderRepository();

            List<ProviderDTO> listaProveedoresActivos = PR.getAllUsersPovidersActive();
         
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


            }


        String formatFile =".dat";
   
      
        

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void readFile(String path, String constants,String codprov ){
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(path)));
            String linea;
            while ((linea=br.readLine())!= null) {
                if (linea.contains("DNI")/*|| linea.contains("ID_VEHICLE") || linea.contains("ID_PARTS") */) {linea=br.readLine();}//si contiene DNI Skipeala

                switch (constants) {
                    case "VEHICLES":
                        ArrayList<VehicleDTO>vehiclesList = new ArrayList<>();
                        //TODO: tratar archivo csv con los campos de customer    


                        break;

                    case "PART":
                        
                        break;

                    case "CUS":
                        ArrayList<CustomerDTO>customerList = new ArrayList<>();
                        String[] cAtt =linea.split(",");//le pongo este nombre tan corto para que en el nuevo objeto no mida 3 campos de futbol
                        CustomerDTO customerDTO = new CustomerDTO(
                            cAtt[0],cAtt[1],cAtt[2],cAtt[3],
                            cAtt[4],cAtt[5],cAtt[6],cAtt[7],
                            cAtt[8],cAtt[9],cAtt[10],cAtt[11],
                            cAtt[12]);
                        customerList.add(customerDTO);//13 campos jiji
                        if (!doValidatePersonIsInInterface(customerDTO,codprov) {
                            //Si no existe 
                            //Insertarlo con la operacion como :new es un campo que solo rellena la primera vez) -> OPERATION: "NEW"
                            

                        }else if(!existJson(customerDTO)){//en caso de que no exista el mismo json lo ingreso en update


                            //En caso de si este OPERATION: "UPD" Y SEA DIFERENTE EN CASO DE QUE SEA IGUAL NADA
                            //compare con el que ya esta insertado en base de datos
                            //en caso de que sea diferente hacemos un inster con update OPERATION= "UPD"
                            //PREMOMINA EL JSON

                            
                        }

                        }
                        
                        //Query en sql
                        //CUSTOMER: DNI -- CODEXTERNAL 

                        break;
              
                }
                
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean existJson (CustomerDTO customerDTO){

        return false;

    }

    private boolean doValidatePersonIsInInterface(CustomerDTO customerDTO,String codProv) {
        InterfazRepository IR = new InterfazRepository();
        

        InterfazDTO interfazDTO = IR.getPersonOfInterfazWithCustomer(customerDTO,codProv);
        
        return true;
    }
}
