package com.dam.tfg.MotoMammiApplicationAGB.Services.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.Hibernate;

import com.dam.tfg.MotoMammiApplicationAGB.Models.ProviderDTO;
import com.dam.tfg.MotoMammiApplicationAGB.Utils.HibernateUtil;

//proceso que lee el fichero
public class ProccessServiceImpl {

    
    //leer archivo
    //con los 3 parametros buscamos el archivo
     // [source]+[codProv]+[date]//dateNow


    public void readInfoFile(String source,String codProv, String date){
        try {


        //validar que el proveedor existe
     

        if (date==null){date=new SimpleDateFormat("yyyyMMdd").format(new Date());}

        String formatFile =".dat";
        String fileName = source+codProv+date+formatFile;
      
        BufferedReader bf = new BufferedReader(new FileReader(fileName));
        String linea;
        while ((linea=bf.readLine())!=null) {
            //comprobar que todas las fechas esta bien / En caso de que no escribir en la tabla MM_Interface el error
            
        }
        

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
