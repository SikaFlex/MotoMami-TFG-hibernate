package com.dam.tfg.MotoMammiApplicationAGB.Models;

import jakarta.persistence.*;
import java.sql.Date;



@Table
@Entity(name="MM_INTERFACE")
public class InterfazDTO {
    @Id
    private String id;
    private String idProv;
    private String codExternal;
    private String codProv;
    private String constJson;
    private Date createDate;
    private Date lastUpdate;
    private String createBy;
    private String updateBy;
    private String codError;
    private String errorMessage;
    private String statusProcess;
    private String operation;
    private String resource;


    
    public InterfazDTO() {
    }



    public InterfazDTO(String id, String idProv, String codExternal, String codProv, String constJson, Date createDate,
            Date lastUpdate, String createBy, String updateBy, String codError, String errorMessage,
            String statusProcess, String operation, String resource) {
            
        Date dateNow =   new java.sql.Date(System.currentTimeMillis());
        this.id = id;
        this.idProv = idProv;
        this.codExternal = codExternal;
        this.codProv = codProv;
        this.constJson = constJson;
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



    public String getConstJson() {
        return constJson;
    }



    public void setConstJson(String constJson) {
        this.constJson = constJson;
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
    
    


    /**
     *
    FOREIGN KEY (idProv) REFERENCES MM_PROVIDERS(id)
     * 
     * 
     * 
     */


     
}
