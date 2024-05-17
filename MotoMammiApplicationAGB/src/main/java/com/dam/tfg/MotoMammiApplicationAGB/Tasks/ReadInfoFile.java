package com.dam.tfg.MotoMammiApplicationAGB.Tasks;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
//proceso que se ejecuta una dependiendo de lo que pongas en propertis sera el tiempo de ejecucion
//scheduler 
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dam.tfg.MotoMammiApplicationAGB.Services.impl.ProccessService;
import com.dam.tfg.MotoMammiApplicationAGB.Services.impl.ProccessServiceImpl;
import com.dam.tfg.MotoMammiApplicationAGB.Utils.Constants;

@Component
public class ReadInfoFile {

  private static final Logger log = LoggerFactory.getLogger(ReadInfoFile.class);


  //LEER CADA DIA ARCHIVO DE CLIENTES
  @Scheduled(cron = "${cron.task.customer}")
  public void readCustomerFile() {
    ProccessServiceImpl process = new ProccessServiceImpl();
    process.readInfoFile(Constants.CUSTOMER, null,null);
  }

  //LEER CADA DIA ARCHIVO DE PARTES DE LOS CLIENTES
  @Scheduled(cron = "${cron.task.parts}")
  public void readPartFile() {
    ProccessServiceImpl process = new ProccessServiceImpl();
    process.readInfoFile(Constants.PARTS, null,null);
  }

//LEER CADA DIA ARCHIVO DE LOS VEHICULOS DE LOS CLIENTES
  @Scheduled(cron = "${cron.task.vehicles}")
  public void readVehiclesFile() {
    ProccessServiceImpl process = new ProccessServiceImpl();
    process.readInfoFile(Constants.VEHICLES, null,null);
  }


}
