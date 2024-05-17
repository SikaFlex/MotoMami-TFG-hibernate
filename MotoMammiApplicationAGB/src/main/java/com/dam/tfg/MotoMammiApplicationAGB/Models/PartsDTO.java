package com.dam.tfg.MotoMammiApplicationAGB.Models;

import java.sql.Timestamp;

public class PartsDTO {
   

    private String id;
    private String id_invoice;
    private String codigoExterno;
    private String internalCod;
    private String descripcion;
    private Timestamp dateNotification;
    private String matricula;
   



    
    public PartsDTO() {
    }




    public PartsDTO(String id, String codigoExterno, String internalCod, String descripcion) {
        this.id = id;
        this.codigoExterno = codigoExterno;
        this.internalCod = internalCod;
        this.descripcion = descripcion;
    }

    
}
