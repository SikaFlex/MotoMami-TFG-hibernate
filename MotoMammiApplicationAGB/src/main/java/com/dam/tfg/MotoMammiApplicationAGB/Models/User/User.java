package com.dam.tfg.MotoMammiApplicationAGB.Models.User;

import java.sql.Date;

import org.hibernate.Session;


/*RF 2.3 El usuario puede registrarse en la aplicación con los datos de nombre, apellidos,
 fecha de nacimiento, dirección, teléfono, número de carnet o dni, carnet de conducir, dirección de 
 correo electrónico, tipo de vehículo, matrícula si tiene, marca si tiene y el modelo del vehículo, 
 password y sexo (hombre/mujer).  
*/
public class User{

    private String nombre;
    private String apellidos;
    private Date fechaNacimiento;
    private String idPersona;
    private String carnetConducir;
    private String password;
    private String sexo;
    //una persona tiene muchos vehiculos pero un vehiculo no tiene muchas personas? 1-m? --> Vehiculos[]
    
    private VehicleDTO vehiculo;
    private Direccion direccion;
    private Contacto contacto;





    public User() {
    }



   







    
    
}
