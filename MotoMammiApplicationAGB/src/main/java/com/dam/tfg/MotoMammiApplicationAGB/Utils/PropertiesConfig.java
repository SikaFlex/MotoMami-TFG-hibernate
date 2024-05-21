package com.dam.tfg.MotoMammiApplicationAGB.Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesConfig {

    public static Properties properties = new Properties();
    static {
        try {
            properties.load(new FileInputStream("MotoMammiApplicationAGB\\src\\main\\resources\\application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("No se encontro el archivo properties");
        }
    }
	/* VARIABE STATICAS RECOGIDAS DE aplication.propertis las cuales podemos acceder desde cualquier parte del proyecto 
	 * facilitando el poder cambiar cualquier valor desde solo un archivo y siempre pudiendo cambiar el archivo donde se almacenan el valor
	 * de estas propertis sin modificar todo el codigo
	*/

    // cron.task.customer=*/15 * * * *
    // cron.task.parts=*/15 * * * *
    // cron.task.vehicles=*/15 * * * *

   
   
    //TIMER PATA SCHEDULER 
    public static final String CRON_TASK_CUSTOMER = properties.getProperty("cron.task.customer");
    public static final String CRON_TASK_PARTS = properties.getProperty("cron.task.parts");
    public static final String CRON_TASK_VEHICLES = properties.getProperty("cron.task.vehicles");





    //BUSQUEDA DE ARCHIVOS
    public static final String CUSTOMER_PATH_FILE = properties.getProperty("customer.path.file");
    public static final String VEHICLE_PATH_FILE = properties.getProperty("vehicle.path.file");
    public static final String PARTS_PATH_FILE = properties.getProperty("parts.path.file");
    public static final String INVOICE_PATH_FILE = properties.getProperty("invoice.path.file");
    
	public static final String PATH = properties.getProperty("file.path");
    public static final String FORMAT_DAT = properties.getProperty("file.format.dat");
    public static final String INVOICE_FORMAT=properties.getProperty("file.format.csv");




    public final static String APP_NAME = properties.getProperty("spring.application.name");
   
    
}
