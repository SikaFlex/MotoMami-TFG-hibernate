package com.dam.tfg.MotoMammiApplicationAGB.Services;

import com.dam.tfg.MotoMammiApplicationAGB.Models.User.CustomerDTO;

public interface ProccessService {
    public void readInfoFile(String source,String codProv, String date);
    public void proccessIntegrateInfo(String source,String codProv, String date);
    public void voidGenerateInvoice(String codProv, String date);


}
