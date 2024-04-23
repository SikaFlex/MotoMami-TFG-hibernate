package com.dam.tfg.MotoMammiApplicationAGB.Services.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Value;
import org.aspectj.apache.bcel.classfile.Constant;
import org.hibernate.Hibernate;

import com.dam.tfg.MotoMammiApplicationAGB.Models.ProviderDTO;
import com.dam.tfg.MotoMammiApplicationAGB.Repositories.ProviderRepository;
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
     // [source]+[codProv]+[date]

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
         
                //recorremos los proveedores activos
            for (ProviderDTO proveedor : listaProveedoresActivos) {
                // Recuperamos el código del proveedor
                String codProvActivo = proveedor.getCodigoProveedor();
                
                //buscar archivo con este nombre
                String vehicleFile= path + vehiclesPath + codProvActivo + dateFile + format;
                String partFile= path + partsPath +  codProvActivo + dateFile + format;
                String customerFile= path + customerPath + codProvActivo + dateFile + format;



                // DEPENDIENDO EL SOURCE QUE LLEGUE EJECUTAREMOS UNA COSA U OTRA
                switch (source) {
                    case Constants.CUSTOMER:
                    // customerFile leer recoger todos los datos del fichero DTO CustomerDTO
                    //TODO: funcion para leer los archivos recibiendo por parametros el nombre del archivo y que depende lo que reciba usara un objeto u otro?
                    //query
                    //CUSTOMER: DNI -- CODEXTERNAL
                        
                        break;

                    case Constants.PARTS:
                    // partFile-> leer recoger todos los datos del fichero DTO PartsDTO

                    //Query
                    //PART: ID_PROVEEDOR -- CODEXTERNAL    

                    break;


                    case Constants.VEHICLES:
                    //Query
                    //VEHICLE: MATRICULA -- CODEXTERNAL
                
                    break;

                    default:

                        break;
                }
                //leemos los ficheros

                //otra query a interface con el dni que recupere


                //guardamos los datos de este ficher en la tabla MM_Interface



            }


        String formatFile =".dat";
   
      
        

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
