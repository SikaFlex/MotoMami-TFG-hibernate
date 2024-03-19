package com.dam.tfg.MotoMammiApplicationAGB;
import org.hibernate.Session;

import java.util.Date;


public interface InterfazUsers {
    
    void update(Session session, User user);
    void insert(Session session);
    void delete(Session session);
    String getNombre();
    String getApellido();
    Date getFechaNacimiento();
}
