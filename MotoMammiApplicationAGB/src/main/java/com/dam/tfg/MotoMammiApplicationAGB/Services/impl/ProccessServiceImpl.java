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
import org.aspectj.apache.bcel.classfile.Constant;
import org.hibernate.Hibernate;

import com.dam.tfg.MotoMammiApplicationAGB.Models.ProviderDTO;
import com.dam.tfg.MotoMammiApplicationAGB.Repositories.ProviderRepository;
import com.dam.tfg.MotoMammiApplicationAGB.Utils.HibernateUtil;

//proceso que lee el fichero
public class ProccessServiceImpl {

    
    //leer archivo
    //con los 3 parametros buscamos el archivo
     // [source]+[codProv]+[date]//dateNow


    public void readInfoFile(String source,String codProv, String date){
        try {
            ProviderRepository PR = new ProviderRepository();    
        
        //recuperamos los providers activos
        List<ProviderDTO> listaProveedoresActivos = PR.getAllUsersPovidersActive();
        

        
        if (date==null){date=new SimpleDateFormat("yyyyMMdd").format(new Date());}

        String formatFile =".dat";
        String fileName = source+codProv+date+formatFile;
      
        

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
