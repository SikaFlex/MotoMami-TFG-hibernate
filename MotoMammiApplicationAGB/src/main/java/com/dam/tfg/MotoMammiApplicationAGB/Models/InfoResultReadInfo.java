package com.dam.tfg.MotoMammiApplicationAGB.Models;
//Se encarga de contar los nuevos elementos, los updates,errors y elementos leidos.

import java.util.ArrayList;

public class InfoResultReadInfo {
    private int newElements;
    private int updateElements;
    private int readedElement;
    private ArrayList<String> errors;

    
    public InfoResultReadInfo() {
    }


    public InfoResultReadInfo(int newElements, int updateElements, int readedElement, ArrayList<String> errors) {
        this.newElements = newElements;
        this.updateElements = updateElements;
        this.readedElement = readedElement;
        this.errors = errors;
    }

    //insertar errores en base de datos?
    //public void insertAllErrors(Session session){ to MM_INTERFACE}

   
    
}
