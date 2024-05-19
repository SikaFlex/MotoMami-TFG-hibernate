package com.dam.tfg.MotoMammiApplicationAGB.Models;

import jakarta.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.UUID;



@Entity(name="mm_interface")
@Table
public class InterfazDTO {
    @Id
    private String id;
    private String idProv;
    private String codExternal; //HACE REFERENCIA AL ID DE LAS OTRAS TABLAS
    private String codProv;
    private String contJson;
    private Timestamp createDate;
    private Timestamp lastUpdate;
    private String createBy;
    private String updateBy;
    private String codError;
    private String errorMessage;
    private String statusProcess;
    private String operation;
    private String resource;


    //Le configuramos un ID dinamico al mismo objeto y que recoja la fecha de creacion directamente del sistema
    public InterfazDTO() {
     
    }



  






    public InterfazDTO(String id, String idProv, String codExternal, String codProv, String contJson,
            Timestamp createDate, Timestamp lastUpdate, String createBy, String updateBy, String codError,
            String errorMessage, String statusProcess, String operation, String resource) {
        this.id = id;
        this.idProv = idProv;
        this.codExternal = codExternal;
        this.codProv = codProv;
        this.contJson = contJson;
        this.createDate = createDate;
        this.lastUpdate = lastUpdate;
        this.createBy = createBy;
        this.updateBy = updateBy;
        this.codError = codError;
        this.errorMessage = errorMessage;
        this.statusProcess = statusProcess;
        this.operation = operation;
        this.resource = resource;
    }










    public String getId() {
        return id;
    }



    public void setId(String id) {
        this.id = id;
    }



    public String getIdProv() {
        return idProv;
    }



    public void setIdProv(String idProv) {
        this.idProv = idProv;
    }



    public String getCodExternal() {
        return codExternal;
    }



    public void setCodExternal(String codExternal) {
        this.codExternal = codExternal;
    }



    public String getCodProv() {
        return codProv;
    }



    public void setCodProv(String codProv) {
        this.codProv = codProv;
    }



    public String getcontJson() {
        return contJson;
    }



    public void setcontJson(String constJson) {
        this.contJson = constJson;
    }



   


    public String getContJson() {
        return contJson;
    }










    public Timestamp getCreateDate() {
        return createDate;
    }










    public Timestamp getLastUpdate() {
        return lastUpdate;
    }










    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }










    public void setCreateDate(Timestamp timeNow) {
        this.createDate =   timeNow;
    }



  


   


    public String getCreateBy() {
        return createBy;
    }



    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }



    public String getUpdateBy() {
        return updateBy;
    }



    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }



    public String getCodError() {
        return codError;
    }



    public void setCodError(String codError) {
        this.codError = codError;
    }



    public String getErrorMessage() {
        return errorMessage;
    }



    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }



    public String getStatusProcess() {
        return statusProcess;
    }



    public void setStatusProcess(String statusProcess) {
        this.statusProcess = statusProcess;
    }



    public String getOperation() {
        return operation;
    }



    public void setOperation(String operation) {
        this.operation = operation;
    }



    public String getResource() {
        return resource;
    }



    public void setResource(String resource) {
        this.resource = resource;
    }



    public void setContJson(String contJson) {
        this.contJson = contJson;
    }










    @Override
    public String toString() {
        return "InterfazDTO [id=" + id + ", idProv=" + idProv + ", codExternal=" + codExternal + ", codProv=" + codProv
                + ", contJson=" + contJson + ", createDate=" + createDate + ", lastUpdate=" + lastUpdate + ", createBy="
                + createBy + ", updateBy=" + updateBy + ", codError=" + codError + ", errorMessage=" + errorMessage
                + ", statusProcess=" + statusProcess + ", operation=" + operation + ", resource=" + resource + "]";
    }
    
    



     
}
