package com.dam.tfg.MotoMammiApplicationAGB.Models;

public class VehicleDTO {
    
    private String matricula; //ID
    private String tipoVehiculo;
    private String marcaVehiculo;
    private String modelo;
    private String color;//traducir color
    private String dniUsuario; //FK-> Customer

    
    public VehicleDTO() {
    }


    public VehicleDTO(String tipoVehiculo, String matricula, String marcaVehiculo, String modelo,String color) {
        this.tipoVehiculo = tipoVehiculo;
        this.matricula = matricula;
        this.marcaVehiculo = marcaVehiculo;
        this.modelo = modelo;
        this.color = color;
    }


    public String getMatricula() {
        return matricula;
    }


    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }


    public String getTipoVehiculo() {
        return tipoVehiculo;
    }


    public void setTipoVehiculo(String tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }


    public String getMarcaVehiculo() {
        return marcaVehiculo;
    }


    public void setMarcaVehiculo(String marcaVehiculo) {
        this.marcaVehiculo = marcaVehiculo;
    }


    public String getModelo() {
        return modelo;
    }


    public void setModelo(String modelo) {
        this.modelo = modelo;
    }


    public String getColor() {
        return color;
    }


    public void setColor(String color) {
        this.color = color;
    }


    public String getDniUsuario() {
        return dniUsuario;
    }


    public void setDniUsuario(String dniUsuario) {
        this.dniUsuario = dniUsuario;
    }

    
}
