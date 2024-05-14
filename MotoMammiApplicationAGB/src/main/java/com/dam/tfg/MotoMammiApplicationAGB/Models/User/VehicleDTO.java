package com.dam.tfg.MotoMammiApplicationAGB.Models.User;

public class VehicleDTO {
    private String tipoVehiculo;
    private String matricula; //ID
    private String marcaVehiculo;
    private String modelo;
    private String color;//traducir color

    
    public VehicleDTO() {
    }


    public VehicleDTO(String tipoVehiculo, String matricula, String marcaVehiculo, String modelo,String color) {
        this.tipoVehiculo = tipoVehiculo;
        this.matricula = matricula;
        this.marcaVehiculo = marcaVehiculo;
        this.modelo = modelo;
        this.color = color;
    }

    
}
