package com.dam.tfg.MotoMammiApplicationAGB.Models;
import jakarta.persistence.*;


@Entity(name="mm_translation_aux")
@Table
public class Translation {
@Id
private String id;
private String idProv;
private String cod_ext;
private String cod_int;
private String date_ini;
private String date_end;

public Translation() {
}

public Translation(String id, String idProv, String cod_ext, String cod_int, String date_ini, String date_end) {
    this.id = id;
    this.idProv = idProv;
    this.cod_ext = cod_ext;
    this.cod_int = cod_int;
    this.date_ini = date_ini;
    this.date_end = date_end;
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

public String getCod_ext() {
    return cod_ext;
}

public void setCod_ext(String cod_ext) {
    this.cod_ext = cod_ext;
}

public String getCod_int() {
    return cod_int;
}

public void setCod_int(String cod_int) {
    this.cod_int = cod_int;
}

public String getDate_ini() {
    return date_ini;
}

public void setDate_ini(String date_ini) {
    this.date_ini = date_ini;
}

public String getDate_end() {
    return date_end;
}

public void setDate_end(String date_end) {
    this.date_end = date_end;
}


   
}
