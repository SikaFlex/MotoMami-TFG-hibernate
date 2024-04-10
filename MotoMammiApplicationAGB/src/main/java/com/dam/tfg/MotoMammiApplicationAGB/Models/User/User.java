package com.dam.tfg.MotoMammiApplicationAGB.Models.User;

import java.sql.Date;

import org.hibernate.Session;

import com.dam.tfg.MotoMammiApplicationAGB.Models.InterfazUsers;
import com.dam.tfg.MotoMammiApplicationAGB.Models.User.User.Direccion;

/*RF 2.3 El usuario puede registrarse en la aplicación con los datos de nombre, apellidos,
 fecha de nacimiento, dirección, teléfono, número de carnet o dni, carnet de conducir, dirección de 
 correo electrónico, tipo de vehículo, matrícula si tiene, marca si tiene y el modelo del vehículo, 
 password y sexo (hombre/mujer).  
*/
public class User implements InterfazUsers{

    private String nombre;
    private String apellidos;
    private Date fechaNacimiento;
    private String idPersona;
    private String carnetConducir;
    private String password;
    private String sexo;
    //una persona tiene muchos vehiculos pero un vehiculo no tiene muchas personas? 1-m? --> Vehiculos[]
    
    private Vehiculo vehiculo;
    private Direccion direccion;
    private Contacto contacto;





    public User() {
    }



    @Override
    public void update(Session session, User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
    @Override
    public void insert(Session session) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insert'");
    }
    @Override
    public void delete(Session session) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }



    
    
}
