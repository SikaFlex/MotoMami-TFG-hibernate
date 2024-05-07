package com.dam.tfg.MotoMammiApplicationAGB.Repositories;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;

import com.dam.tfg.MotoMammiApplicationAGB.Models.ProviderDTO;

public interface InterfazProvider {
    List<ProviderDTO> getAllUsersPovidersActive(String p_prov, String p_date);
}
