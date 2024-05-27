package com.dam.tfg.MotoMammiApplicationAGB.Utils;

public class Constants {
     public final static String CUSTOMER = "CUS"; 
     public final static String PARTS = "PART";
     public final static String VEHICLES = "VEHICLES";

     /** 
      * Estados para los inserts de CUSTOMERS
      **/
     public final static String NEW = "NEW"; 
     public final static String UPD = "UDP";
     public final static String ERR = "ERROR";


   
   //SKIPEAR LINEAS
   public final static String SKIP_CUSTOMER = "DNI";
   public final static String SKIP_VEHICLE = "matricula";
   public final static String SKIP_PARTS = "codigoExterno";





    /*Estados de los registros guardados en MM_Interfaz
     statusProcess:
     * N -> No ha sido procesado.
     * E -> ERROR
     * P -> Procesado.
     */
    public final static String SP_N = "N";
    public final static String SP_E = "E";
    public final static String SP_P = "P";


    public final static String UNDERSCORE ="_";

    public final static String path = ".dat";

    public final static String SUCCESS_INFO_FILE = "Se ha realizado la lecutura correctamente";
    public final static String SUCCESS_PROCESS_FILE = "Se ha realizado la inserccion a las tablas maestras correctamente";
    public final static String SUCCESS_INVOICE = "Se ha realizado las facturas del mes pasado correctamente";

}
