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
    


    /**
     *
    FOREIGN KEY (idProv) REFERENCES MM_PROVIDERS(id)
     * 
     * 
     * 
     */
}
