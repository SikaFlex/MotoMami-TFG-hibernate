package com.dam.tfg.MotoMammiApplicationAGB.Utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

public class Utils {
  public static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
  
  public static Timestamp timeNow(){
       LocalDateTime localDateTime = LocalDateTime.now();
       return Timestamp.valueOf(localDateTime);
  }

}
