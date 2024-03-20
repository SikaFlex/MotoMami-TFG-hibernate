package com.dam.tfg.MotoMammiApplicationAGB.Repositories;

import java.util.ArrayList;

import org.hibernate.Session;

import com.dam.tfg.MotoMammiApplicationAGB.Models.ProviderDTO;
import com.itextpdf.text.List;

public interface InterfazProvider {
    ArrayList<ProviderDTO>getAllUsersPovidersActive(Session session);
}
