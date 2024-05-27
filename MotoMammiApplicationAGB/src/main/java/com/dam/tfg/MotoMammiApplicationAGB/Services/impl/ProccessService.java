package com.dam.tfg.MotoMammiApplicationAGB.Services.impl;

import com.dam.tfg.MotoMammiApplicationAGB.Models.CustomerDTO;

public interface ProccessService {
    public void readInfoFile(String source,String codProv, String date);
    public void proccessIntegrateInfo(String source,String codProv, String date);
    public void voidGenerateInvoice(String source,String codProv, String date);


}
