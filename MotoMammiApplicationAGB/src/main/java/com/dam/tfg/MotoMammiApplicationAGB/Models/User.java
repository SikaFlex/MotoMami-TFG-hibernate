package com.dam.tfg.MotoMammiApplicationAGB.Models;

import java.sql.Date;

import org.hibernate.Session;

/*RF 2.3 El usuario puede registrarse en la aplicación con los datos de nombre, apellidos,
 fecha de nacimiento, dirección, teléfono, número de carnet o dni, carnet de conducir, dirección de 
 correo electrónico, tipo de vehículo, matrícula si tiene, marca si tiene y el modelo del vehículo, 
 password y sexo (hombre/mujer).  
*/
public class User implements InterfazUsers{

    private String nombre;
    private String apellidos;
    private Date fechaNacimiento;
    private String direccion;
    private int telefono;
    private String idPersona;
    private String carnetConducir;
    private String email;
    private String password;
    private String sexo;
    //una persona tiene muchos vehiculos pero un vehiculo no tiene muchas personas? 1-m? --> Vehiculos[]
    private String tipoVehiculo;
    private String matricula;
    private String marcaVehiculo;
    private String modelo;






    public User() {
    }




    
    
    public User(String nombre, String apellidos, Date fechaNacimiento, String direccion, int telefono, String idPersona,
            String carnetConducir, String email, String password, String sexo, String tipoVehiculo, String matricula,
            String marcaVehiculo, String modelo) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.direccion = direccion;
        this.telefono = telefono;
        this.idPersona = idPersona;
        this.carnetConducir = carnetConducir;
        this.email = email;
        this.password = password;
        this.sexo = sexo;
        this.tipoVehiculo = tipoVehiculo;
        this.matricula = matricula;
        this.marcaVehiculo = marcaVehiculo;
        this.modelo = modelo;
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
    @Override
    public String getNombre() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getNombre'");
    }
    @Override
    public String getApellido() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getApellido'");
    }
    @Override
    public java.util.Date getFechaNacimiento() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFechaNacimiento'");
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getApellidos() {
        return apellidos;
    }


    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }


    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }


    public String getDireccion() {
        return direccion;
    }


    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }


    public int getTelefono() {
        return telefono;
    }


    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }


    public String getIdPersona() {
        return idPersona;
    }


    public void setIdPersona(String idPersona) {
        this.idPersona = idPersona;
    }


    public String getCarnetConducir() {
        return carnetConducir;
    }


    public void setCarnetConducir(String carnetConducir) {
        this.carnetConducir = carnetConducir;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public String getSexo() {
        return sexo;
    }


    public void setSexo(String sexo) {
        this.sexo = sexo;
    }


    public String getTipoVehiculo() {
        return tipoVehiculo;
    }


    public void setTipoVehiculo(String tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }


    public String getMatricula() {
        return matricula;
    }


    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }


    public String getMarcaVehiculo() {
        return marcaVehiculo;
    }


    public void setMarcaVehiculo(String marcaVehiculo) {
        this.marcaVehiculo = marcaVehiculo;
    }


    public String getModelo() {
        return modelo;
    }


    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    
}
