package com.dam.tfg.MotoMammiApplicationAGB.Controllers;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dam.tfg.MotoMammiApplicationAGB.Services.impl.ProccessServiceImpl;
import com.dam.tfg.MotoMammiApplicationAGB.Utils.Constants;
import com.dam.tfg.MotoMammiApplicationAGB.Utils.Errors;
import com.dam.tfg.MotoMammiApplicationAGB.Utils.Utils;



@RestController
public class MotomamiController{
    @Autowired
    ProccessServiceImpl pService;


    //ENUNCIADO RUTA:/MotoMammiAplicationAGB/v1/readInfoFileAGB/{resource}/{codprov}/{date}
    
    @RequestMapping(value =("/MotoMammiAplicationAGB/v1/readInfoFileAGB/{resource}/{codprovProcess}/{dateProcess}"),
                    method = RequestMethod.GET,
                    produces = "application/json")
    String callProcessReadInfo(@PathVariable String resource,
                               @PathVariable String codprovProcess,
                               @PathVariable String dateProcess
                               ){
        try{
            String lastResource;
            String lastCodProv=null;
            String lastDate=null;
            switch (resource) {
                case "customer":
                    lastResource= Constants.CUSTOMER;
                    break;
                case "vehicle":
                    lastResource= Constants.VEHICLES;
                break;
                case "parts":
                lastResource= Constants.PARTS;
            break;
            default:
                lastResource=null;
                break;
            }
          if(!codprovProcess.equals("null") || codprovProcess.isEmpty()) {lastCodProv=codprovProcess;}
          if(!dateProcess.equals("null") || dateProcess.isEmpty()){lastDate=dateProcess;}
          
            pService.readInfoFile(lastResource,lastCodProv,lastDate);
            
            return Constants.SUCCESS_INFO_FILE;
            

        } catch (Exception e){
            System.err.println(Errors.ERROR_INFO_FILE + e.getMessage());
            return Errors.ERROR_PROCES_FILE_CONTROLLER + e.getMessage();
        }
     }


  @RequestMapping(value =("/MotoMammiAplicationAGB/v1/proccessIntegrateInfoAGB/{resourceProcess}/{codprov}/{date}"),
                    method = RequestMethod.GET,
                    produces = "application/json")
    String callProcessInfo(    @PathVariable String resourceProcess,
                               @PathVariable String codprov,
                               @PathVariable String date
                               ){
            try{
            System.out.println("FUNCIONA?????????????????????????????????????????????");
            String lastResource;
            String lastCodProv=null;
            String lastDate=null;
            switch (resourceProcess) {
                case "customer":
                    lastResource= Constants.CUSTOMER;
                    break;
                case "vehicle":
                    lastResource= Constants.VEHICLES;
                break;
                case "parts":
                lastResource= Constants.PARTS;
            break;
            default:
                lastResource=null;
                break;
            }
          if(!codprov.equals("null") || codprov.isEmpty()) {lastCodProv=codprov;}
          if(!date.equals("null") || date.isEmpty()){lastDate=date;}
          pService.proccessIntegrateInfo(lastResource,lastCodProv,lastDate);
          
          return Constants.SUCCESS_PROCESS_FILE;

        } catch (Exception e){
            System.err.println(Errors.ERROR_INFO_FILE + e.getMessage());
            return Errors.ERROR_PROCES_FILE_CONTROLLER + e.getMessage();
        }
    }






}