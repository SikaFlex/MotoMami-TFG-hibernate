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

   
                                 //READ INFO FILE
    @RequestMapping(value =("/MotoMammiAplicationAGB/v1/readInfoFileAGB/{resource}/{codprov}/{date}"),
                    method = RequestMethod.GET,
                    produces = "application/json")
    String callProcessReadInfo(@PathVariable String resource,
                               @PathVariable String codprov,
                               @PathVariable String date
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
          if(!codprov.equals("null") || codprov.isEmpty()) {lastCodProv=codprov;}
          if(!date.equals("null") || date.isEmpty()){lastDate=date;}
          
          pService.readInfoFile(lastResource,lastCodProv,lastDate);
          return Constants.SUCCESS_INFO_FILE;


        } catch (Exception e){
            System.err.println(Errors.ERROR_INFO_FILE + e.getMessage());
            return Errors.ERROR_PROCES_FILE_CONTROLLER + e.getMessage();
        }
     }

    // PROCESS INTEGRATE INFO 
  @RequestMapping(value =("/MotoMammiAplicationAGB/v1/proccessIntegrateInfoAGB/{resource}/{codprov}/{date}"),
                    method = RequestMethod.GET,
                    produces = "application/json")
    String callProcessInfo(    @PathVariable String resource,
                               @PathVariable String codprov,
                               @PathVariable String date
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
          if(!codprov.equals("null") || codprov.isEmpty()) {lastCodProv=codprov;}
          if(!date.equals("null") || date.isEmpty()){lastDate=date;}
          
          if (lastResource.equals(null)) {return Errors.ERROR_PROCES_FILE_CONTROLLER;}
          
          pService.proccessIntegrateInfo(lastResource,lastCodProv,lastDate);
          
          return Constants.SUCCESS_PROCESS_FILE;

        } catch (Exception e){
            System.err.println(Errors.ERROR_INFO_FILE + e.getMessage());
            return Errors.ERROR_PROCES_FILE_CONTROLLER;
        }
    }

                                //INVOICE
@RequestMapping(value = ("/MotoMammiAplicationAGB/v1/genInvoiceFileAGB/{resource}/{codprov}/{date}"), 
                                        method = RequestMethod.GET, 
                                        produces = "application/json")
String callInvoiceGenerate(@PathVariable String resource,
        @PathVariable String codprov,
        @PathVariable String date) {
    try {
        String lastResource;
        String lastCodProv = null;
        String lastDate = null;
        switch (resource) {
            case "customer":
                lastResource = Constants.CUSTOMER;
                break;
            case "vehicle":
                lastResource = Constants.VEHICLES;
                break;
            case "parts":
                lastResource = Constants.PARTS;
                break;
            default:
                lastResource = null;
                break;
        }
        if (!codprov.equals("null") || codprov.isEmpty()) {
            lastCodProv = codprov;
        }
        if (!date.equals("null") || date.isEmpty()) {
            lastDate = date;
        }
        if (lastCodProv==null) {
            return Errors.ERROR_INVOICE_WITHOUT_CODPROV;
        }
        pService.generateInvoice(lastCodProv, lastDate);

        return Constants.SUCCESS_INVOICE;

    } catch (Exception e) {
        System.err.println(Errors.ERROR_INFO_FILE + e.getMessage());
        return Errors.ERROR_PROCES_FILE_CONTROLLER + e.getMessage();
    }
}




}