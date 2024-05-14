package com.dam.tfg.MotoMammiApplicationAGB.Models;

public class PartsDTO {
   

    private String id;
    private String codigoExterno;
    private String internalCod;
    private String descripcion;



    
    public PartsDTO() {
    }




    public PartsDTO(String id, String codigoExterno, String internalCod, String descripcion) {
        this.id = id;
        this.codigoExterno = codigoExterno;
        this.internalCod = internalCod;
        this.descripcion = descripcion;
    }

    
}
