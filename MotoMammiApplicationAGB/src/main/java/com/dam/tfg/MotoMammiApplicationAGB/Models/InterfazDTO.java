package com.dam.tfg.MotoMammiApplicationAGB.Models;

import jakarta.persistence.*;
import java.sql.Date;
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
    private Date createDate;
    private Date lastUpdate;
    private String createBy;
    private String updateBy;
    private String codError;
    private String errorMessage;
    private String statusProcess;
    private String operation;
    private String resource;


    //Le configuramos un ID dinamico al mismo objeto y que recoja la fecha de creacion directamente del sistema
    public InterfazDTO() {
        Date dateNow =  new java.sql.Date(System.currentTimeMillis());
        UUID uuid = UUID.randomUUID();
        this.id = uuid.toString();
        this.createDate = dateNow;
    }



  



    public InterfazDTO(String id, String idProv, String codExternal, String codProv, String contJson, Date createDate,
        Date lastUpdate, String createBy, String updateBy, String codError, String errorMessage,
        String statusProcess, String operation, String resource) {

        Date dateNow =  new java.sql.Date(System.currentTimeMillis());
        UUID uuid = UUID.randomUUID();
        this.id = uuid.toString();
        this.idProv = idProv;
        this.codExternal = codExternal;
        this.codProv = codProv;
        this.contJson = contJson;
        this.createDate = dateNow;
        this.lastUpdate = dateNow;
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



    public Date getCreateDate() {
        return createDate;
    }



    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }



    public Date getLastUpdate() {
        return lastUpdate;
    }



    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
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
    
    



     
}
