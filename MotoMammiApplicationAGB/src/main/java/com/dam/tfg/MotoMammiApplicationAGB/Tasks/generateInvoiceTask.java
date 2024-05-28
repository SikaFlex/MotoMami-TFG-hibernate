package com.dam.tfg.MotoMammiApplicationAGB.Tasks;

import org.springframework.scheduling.annotation.Scheduled;

import org.springframework.scheduling.annotation.Scheduled;
import com.dam.tfg.MotoMammiApplicationAGB.Services.impl.ProccessServiceImpl;
import com.dam.tfg.MotoMammiApplicationAGB.Utils.Constants;
import org.springframework.stereotype.Component;

@Component
public class generateInvoiceTask {
      //CREAMOS UN CSV CON TODOS LOS DATOS DE LAS FACTURAS DEL MES PASADO
  @Scheduled(cron = "${cron.task.invoice}")
  public void processCustomerFile() {
    ProccessServiceImpl process = new ProccessServiceImpl();
    process.generateInvoice(Constants.CODPROV_EXAMPLE_CAIX, null);
  }
    
}
