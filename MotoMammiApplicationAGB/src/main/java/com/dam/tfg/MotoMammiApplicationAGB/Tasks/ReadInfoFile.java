package com.dam.tfg.MotoMammiApplicationAGB.Tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dam.tfg.MotoMammiApplicationAGB.Services.impl.ProccessServiceImpl;
import com.dam.tfg.MotoMammiApplicationAGB.Utils.Constants;

@Component
public class ReadInfoFile {

  private static final Logger log = LoggerFactory.getLogger(ReadInfoFile.class);


  //LEER CADA DIA ARCHIVO DE CLIENTES
  @Scheduled(cron = "${cron.task.read.customer}")
  public void readCustomerFile() {
    ProccessServiceImpl process = new ProccessServiceImpl();
    process.readInfoFile(Constants.CUSTOMER, null,null);
  }

  //LEER CADA DIA ARCHIVO DE PARTES DE LOS CLIENTES
  @Scheduled(cron = "${cron.task.read.parts}")
  public void readPartFile() {
    ProccessServiceImpl process = new ProccessServiceImpl();
    process.readInfoFile(Constants.PARTS, null,null);
  }

//LEER CADA DIA ARCHIVO DE LOS VEHICULOS DE LOS CLIENTES
  @Scheduled(cron = "${cron.task.read.vehicles}")
  public void readVehiclesFile() {
    ProccessServiceImpl process = new ProccessServiceImpl();
    process.readInfoFile(Constants.VEHICLES, null,null);
  }


}
