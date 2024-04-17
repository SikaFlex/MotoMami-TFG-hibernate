package com.dam.tfg.MotoMammiApplicationAGB.Models;
import jakarta.persistence.*;
import java.sql.Date;

@Entity(name="mm_providers")
@Table
public class ProviderDTO {
    @Id
    private String id;
    private String codigoProveedor;
    private String name;
    private Date dateIni;
    private Date dateEnd;
    private int swiact;


    public ProviderDTO() {
    }


    public ProviderDTO(String id, String codigoProveedor, String name, Date dateIni, Date dateEnd, int swiact) {
        this.id = id;
        this.codigoProveedor = codigoProveedor;
        this.name = name;
        this.dateIni = dateIni;
        this.dateEnd = dateEnd;
        this.swiact = swiact;
    }


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public String getCodigoProveedor() {
        return codigoProveedor;
    }


    public void setCodigoProveedor(String codigoProveedor) {
        this.codigoProveedor = codigoProveedor;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public Date getDateIni() {
        return dateIni;
    }


    public void setDateIni(Date dateIni) {
        this.dateIni = dateIni;
    }


    public Date getDateEnd() {
        return dateEnd;
    }


    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }


    public int getSwiact() {
        return swiact;
    }


    public void setSwiact(int swiact) {
        this.swiact = swiact;
    }

    

}
