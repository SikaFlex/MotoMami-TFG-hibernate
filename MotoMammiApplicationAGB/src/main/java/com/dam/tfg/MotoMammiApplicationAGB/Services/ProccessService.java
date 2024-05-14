package com.dam.tfg.MotoMammiApplicationAGB.Services;

public interface ProccessService {
    public void readInfoFile(String source,String codProv, String date);
    public void proccessIntegrateInfo(String source,String codProv, String date);
    public void voidGenerateInvoice(String codProv, String date);
}
