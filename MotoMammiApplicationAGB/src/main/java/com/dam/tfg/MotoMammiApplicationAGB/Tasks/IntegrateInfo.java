package com.dam.tfg.MotoMammiApplicationAGB.Tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import com.dam.tfg.MotoMammiApplicationAGB.Services.impl.ProccessServiceImpl;
import com.dam.tfg.MotoMammiApplicationAGB.Utils.Constants;
import org.springframework.stereotype.Component;



@Component
public class IntegrateInfo {

      private static final Logger log = LoggerFactory.getLogger(ReadInfoFile.class);


  //PROCESA CADA DIA ARCHIVO DE CLIENTES
  @Scheduled(cron = "${cron.task.proccess.customer}")
  public void processCustomerFile() {
    ProccessServiceImpl process = new ProccessServiceImpl();
    process.proccessIntegrateInfo(Constants.CUSTOMER, null,null);
  }

  //PROCESA CADA DIA ARCHIVO DE PARTES DE LOS CLIENTES
  @Scheduled(cron = "${cron.task.process.parts}")
  public void processPartFile() {
    ProccessServiceImpl process = new ProccessServiceImpl();
    process.proccessIntegrateInfo(Constants.PARTS, null,null);
  }

//PROCESA CADA DIA ARCHIVO DE LOS VEHICULOS DE LOS CLIENTES
  @Scheduled(cron = "${cron.task.process.vehicles}")
  public void processVehiclesFile() {
    ProccessServiceImpl process = new ProccessServiceImpl();
    process.proccessIntegrateInfo(Constants.VEHICLES, null,null);
  }

    
}
