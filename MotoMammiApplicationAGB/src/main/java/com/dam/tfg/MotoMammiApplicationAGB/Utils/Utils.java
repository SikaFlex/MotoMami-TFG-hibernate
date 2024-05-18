package com.dam.tfg.MotoMammiApplicationAGB.Utils;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.UUID;

public class Utils {
  public static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
  
  public static Timestamp timeNow(){
       LocalDateTime localDateTime = LocalDateTime.now();
       return Timestamp.valueOf(localDateTime);
  }

  public static String randomID(){
     UUID uuid = UUID.randomUUID();
     return uuid.toString();
    
  }

  public static Date stringToSqlDate(String date){
    try {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    java.util.Date parsedDate;
      parsedDate = dateFormat.parse(date);
      return new Date(parsedDate.getTime());
    } catch (ParseException e) {
      e.printStackTrace();
      return null;
    }
  }

}
