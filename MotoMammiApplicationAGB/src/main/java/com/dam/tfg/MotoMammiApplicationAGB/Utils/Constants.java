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


     /*
     Nombre de la aplicacion a la hora de insertar en la tabla Interfaz
     */ 
    public final static String APP_NAME = "MOTOMAMI_AGB";




    /*Estados de los registros guardados en MM_Interfaz
     statusProcess:
     * N -> No ha sido procesado.
     * E -> ERROR
     * P -> Procesado.
     */
    public final static String SP_N = "N";
    public final static String SP_E = "E";
    public final static String SP_P = "P";





    /*
     * ERRORS:
     */
    public final static String UNKNOW_ERROR = "OCURRIO UN FALLO INSESPERADO";
    public final static String ERROR_HIBERNATE = "HIBERNATE";
}
