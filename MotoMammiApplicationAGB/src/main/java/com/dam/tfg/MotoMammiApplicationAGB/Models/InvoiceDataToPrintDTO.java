package com.dam.tfg.MotoMammiApplicationAGB.Models;

import java.sql.Date;

public class InvoiceDataToPrintDTO {
private String DNI; 
private String codProv;
private Date fecha_emision; //today
private String nombre;
private String firt_Surname;
private String last_Surname;
private String direccion;
private String tipoDeVehiculo;
private String matricula; // REFERENCIA A LA TABLA VEHICLES
private String nombreEmpresa;
private String cifEmpresa;
private String direccionEmpresa;
private double coste;
private String divisa; //€
private int iva;


public InvoiceDataToPrintDTO() {
}





public InvoiceDataToPrintDTO(String dNI, String codProv, Date fecha_emision, String nombre,
        String firt_Surname, String last_Surname, String direccion, String tipoDeVehiculo, String matricula,
        String nombreEmpresa, String cifEmpresa, String direccionEmpresa, int coste, String divisa, int iva) {

    DNI = dNI;
    this.codProv = codProv;
    this.fecha_emision = fecha_emision;
    this.nombre = nombre;
    this.firt_Surname = firt_Surname;
    this.last_Surname = last_Surname;
    this.direccion = direccion;
    this.tipoDeVehiculo = tipoDeVehiculo;
    this.matricula = matricula;
    this.nombreEmpresa = nombreEmpresa;
    this.cifEmpresa = cifEmpresa;
    this.direccionEmpresa = direccionEmpresa;
    this.coste = coste;
    this.divisa = divisa;
    this.iva = iva;
}



public String firtsLineCSV() {
    return "DNI;codProv;fecha_emision;nombre;firt_Surname;last_Surname;direccion;tipoDeVehiculo;matricula;nombreEmpresa;nombreEmpresa;cifEmpresa;coste;iva";
}





public String printCSV(){
    return this.DNI+","+this.codProv+","+this.fecha_emision+","+this.nombre+","+this.firt_Surname+","+this.last_Surname+","+this.direccion+","+
    this.tipoDeVehiculo+","+this.matricula+","+this.nombreEmpresa+","+this.cifEmpresa+","+this.direccionEmpresa+","+this.coste+this.divisa+","+this.iva;

}

public String getDNI() {
    return DNI;
}

public void setDNI(String dNI) {
    DNI = dNI;
}

public Date getFecha_emision() {
    return fecha_emision;
}

public void setFecha_emision(Date fecha_emision) {
    this.fecha_emision = fecha_emision;
}

public String getNombre() {
    return nombre;
}

public void setNombre(String nombre) {
    this.nombre = nombre;
}

public String getFirt_Surname() {
    return firt_Surname;
}

public void setFirt_Surname(String firt_Surname) {
    this.firt_Surname = firt_Surname;
}

public String getLast_Surname() {
    return last_Surname;
}

public void setLast_Surname(String last_Surname) {
    this.last_Surname = last_Surname;
}

public String getDireccion() {
    return direccion;
}

public void setDireccion(String direccion) {
    this.direccion = direccion;
}

public String getTipoDeVehiculo() {
    return tipoDeVehiculo;
}

public void setTipoDeVehiculo(String tipoDeVehiculo) {
    this.tipoDeVehiculo = tipoDeVehiculo;
}

public String getMatricula() {
    return matricula;
}

public void setMatricula(String matricula) {
    this.matricula = matricula;
}

public String getNombreEmpresa() {
    return nombreEmpresa;
}

public void setNombreEmpresa(String nombreEmpresa) {
    this.nombreEmpresa = nombreEmpresa;
}

public String getCifEmpresa() {
    return cifEmpresa;
}

public void setCifEmpresa(String cifEmpresa) {
    this.cifEmpresa = cifEmpresa;
}

public String getDireccionEmpresa() {
    return direccionEmpresa;
}

public void setDireccionEmpresa(String direccionEmpresa) {
    this.direccionEmpresa = direccionEmpresa;
}

public double getCoste() {
    return coste;
}

public void setCoste(double coste) {
    this.coste = coste;
}

public String getDivisa() {
    return divisa;
}

public void setDivisa(String divisa) {
    this.divisa = divisa;
}

public int getIva() {
    return iva;
}

public void setIva(int iva) {
    this.iva = iva;
}


public String getCodProv() {
    return codProv;
}


public void setCodProv(String codProv) {
    this.codProv = codProv;
}




}
