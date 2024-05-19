package com.dam.tfg.MotoMammiApplicationAGB.Models;
import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Timestamp;

import com.dam.tfg.MotoMammiApplicationAGB.Utils.Utils;

@Entity(name="mm_parts")
@Table
public class PartsDTO {
   
    @Id
    private String id;
    private String codigoExterno;
    private String internalCod;
    private String descripcion;
    private String matricula;
    private String idInvoice;
    private String dniVehicle;
    private Date dateNotification;

    
    public PartsDTO() {
    }




    public PartsDTO( String codigoExterno, String internalCod, String descripcion, String matricula,
            String idInvoice, String dniVehicle, String dateNotification) {
        this.codigoExterno = codigoExterno;
        this.internalCod = internalCod;
        this.descripcion = descripcion;
        this.matricula = matricula;
        this.idInvoice = idInvoice;
        this.dniVehicle = dniVehicle;
        this.dateNotification = Utils.stringToSqlDate(dateNotification);
    }

    public String getId() {
        return id;
    }





    public void setId(String id) {
        this.id = id;
    }





 



    public String getIdInvoice() {
        return idInvoice;
    }









    public void setIdInvoice(String idInvoice) {
        this.idInvoice = idInvoice;
    }









    public String getDniVehicle() {
        return dniVehicle;
    }









    public void setDniVehicle(String dniVehicle) {
        this.dniVehicle = dniVehicle;
    }









    public String getCodigoExterno() {
        return codigoExterno;
    }





    public void setCodigoExterno(String codigoExterno) {
        this.codigoExterno = codigoExterno;
    }





    public String getInternalCod() {
        return internalCod;
    }





    public void setInternalCod(String internalCod) {
        this.internalCod = internalCod;
    }





    public String getDescripcion() {
        return descripcion;
    }





    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }









    public String getMatricula() {
        return matricula;
    }





    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }




    public Date getDateNotification() {
        return dateNotification;
    }




    public void setDateNotification(Date dateNotification) {
        this.dateNotification = dateNotification;
    }




    
}
