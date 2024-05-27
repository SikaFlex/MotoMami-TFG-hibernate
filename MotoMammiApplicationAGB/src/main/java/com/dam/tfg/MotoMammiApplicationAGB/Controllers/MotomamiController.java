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



    @RequestMapping(value =("/readInfoFileAGB/{resource}/{codprov}/{date}"),
                    method = RequestMethod.GET,
                    produces = "application/json")
    String callProcessReadInfo(  @PathVariable String resource,
                               @PathVariable String codprov,
                               @PathVariable String date
                               ){
        try{
            String lastResource;
            String lastCodProv=null;
            String lastDate;
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
          if(!codprov.equals("null") || codprov.isEmpty()) {lastCodProv=codprov;}
          if(!date.equals("null") || date.isEmpty()){lastDate=date;}
          
            pService.readInfoFile(lastResource,lastCodProv,lastCodProv);
            
            return Constants.SUCCESS_INFO_FILE;
            

        } catch (Exception e){
            System.err.println(Errors.ERROR_INFO_FILE + e.getMessage());
            return Errors.ERROR_PROCES_FILE_CONTROLLER + e.getMessage();
        }
    }


  @RequestMapping(value =("/proccessIntegrateInfoAGB/{resource}/{codprov}/{date}"),
                    method = RequestMethod.GET,
                    produces = "application/json")
    String callProcessInfo(@PathVariable String resource,
                               @PathVariable String codprov,
                               @PathVariable String date,//"20240423"
                               @PathVariable Integer id_interface
                               ){
        try{
            System.out.println("Resource: " + resource + "\n" +
                               "Provider code: " + codprov + "\n" +
                               "Date: " + date + "\n" + 
                               "id_interface" + id_interface);

            // pService.proccessIntegrateInfo(resource, codprov, date);
            return "Done";
        } catch (Exception e){
            System.err.println("heey pero me estoy poniendo peluche yo 😏😏");
            return "Buenos dias"; // Return an empty map or handle error accordingly
        }
    }






}