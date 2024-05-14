package com.dam.tfg.MotoMammiApplicationAGB.Models.User;

public class CustomerDTO {

    private String DNI; //id
    private String name;
    private String first_surname;
    private String last_surname;
    private String email;
    private String birth_date;
    private String postal_code;
    private String street_type;//tradducir
    private String city; // traducir ejemplo londre/londo
    private String number;
    private String phone;
    private String gender;//cambiar h y m / x f / m
    private String licence_type;  // B A 
    private String operatio; 
    
    public CustomerDTO(String dNI, String name, String first_surname, String last_surname, String email,
            String birth_date, String postal_code, String street_type, String city, String number, String phone,
            String gender, String licence_type) {
        DNI = dNI;
        this.name = name;
        this.first_surname = first_surname;
        this.last_surname = last_surname;
        this.email = email;
        this.birth_date = birth_date;
        this.postal_code = postal_code;
        this.street_type = street_type;
        this.city = city;
        this.number = number;
        this.phone = phone;
        this.gender = gender;
        this.licence_type = licence_type;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String dNI) {
        DNI = dNI;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirst_surname() {
        return first_surname;
    }

    public void setFirst_surname(String first_surname) {
        this.first_surname = first_surname;
    }

    public String getLast_surname() {
        return last_surname;
    }

    public void setLast_surname(String last_surname) {
        this.last_surname = last_surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public String getStreet_type() {
        return street_type;
    }

    public void setStreet_type(String street_type) {
        this.street_type = street_type;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLicence_type() {
        return licence_type;
    }

    public void setLicence_type(String licence_type) {
        this.licence_type = licence_type;
    }

    public void setOperatio(String operatio) {
        this.operatio = operatio;
    }

    public String getOperatio() {
        return operatio;
    }

    



}
