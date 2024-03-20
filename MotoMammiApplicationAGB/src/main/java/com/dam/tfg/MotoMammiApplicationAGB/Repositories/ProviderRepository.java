package com.dam.tfg.MotoMammiApplicationAGB.Repositories;

import java.util.ArrayList;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.dam.tfg.MotoMammiApplicationAGB.Models.ProviderDTO;

@Repository
public class ProviderRepository implements InterfazProvider{

    @Override
    public ArrayList<ProviderDTO> getAllUsersPovidersActive(Session session) {



        
        return null;
    }


    
    
}
