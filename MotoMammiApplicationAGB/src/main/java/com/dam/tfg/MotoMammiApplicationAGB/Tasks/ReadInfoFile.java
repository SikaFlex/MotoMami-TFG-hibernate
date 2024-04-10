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

import com.dam.tfg.MotoMammiApplicationAGB.Services.ProccessService;
import com.dam.tfg.MotoMammiApplicationAGB.Services.impl.ProccessServiceImpl;

@Component
public class ReadInfoFile {
  @Value("${customer.path.file}")
  String customerPath;
  private static final Logger log = LoggerFactory.getLogger(ReadInfoFile.class);


  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
  @Autowired
  private ProccessService p_service;

  @Scheduled(cron = "${cron.task.customer}")
  // haz que se repita cada x tiempo automatico con el scheduled
  public void readCurrentFile() {
    

    //ejecutar le ProcessServiceImplement
    ProccessServiceImpl process = new ProccessServiceImpl();
    process.readInfoFile(customerPath, customerPath, customerPath);
  }


}
