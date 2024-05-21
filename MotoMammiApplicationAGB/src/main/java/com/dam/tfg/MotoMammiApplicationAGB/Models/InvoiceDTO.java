package com.dam.tfg.MotoMammiApplicationAGB.Models;


import java.sql.Date;

import jakarta.persistence.*;


@Entity(name="mm_invoices")
@Table
public class InvoiceDTO {

@Id
private String id;
private Date fecha_emision;  //mes anterior a este
private String dni_Cliente;
private String codProv;
private String nombreEmpresa;
private String cifEmpresa;
private String direccionEmpresa;
private double coste;
private String divisa; //€
private int    iva;


public InvoiceDTO() {
}






public InvoiceDTO(String id, Date fecha_emision, String dni_Cliente, String codProv, String nombreEmpresa,
        String cifEmpresa, String direccionEmpresa, double coste, String divisa, int iva) {
    this.id = id;
    this.fecha_emision = fecha_emision;
    this.dni_Cliente = dni_Cliente;
    this.codProv = codProv;
    this.nombreEmpresa = nombreEmpresa;
    this.cifEmpresa = cifEmpresa;
    this.direccionEmpresa = direccionEmpresa;
    this.coste = coste;
    this.divisa = divisa;
    this.iva = iva;
}






public String getId() {
    return id;
}

public void setId(String id) {
    this.id = id;
}

public Date getFecha_emision() {
    return fecha_emision;
}

public void setFecha_emision(Date fecha_emision) {
    this.fecha_emision = fecha_emision;
}

public String getDni_Cliente() {
    return dni_Cliente;
}

public void setDni_Cliente(String dni_Cliente) {
    this.dni_Cliente = dni_Cliente;
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
