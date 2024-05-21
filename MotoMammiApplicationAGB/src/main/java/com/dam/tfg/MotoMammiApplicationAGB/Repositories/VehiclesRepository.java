package com.dam.tfg.MotoMammiApplicationAGB.Repositories;

import java.util.List;

import org.hibernate.Session;

import com.dam.tfg.MotoMammiApplicationAGB.Models.CustomerDTO;
import com.dam.tfg.MotoMammiApplicationAGB.Models.PartsDTO;
import com.dam.tfg.MotoMammiApplicationAGB.Models.VehicleDTO;
import com.dam.tfg.MotoMammiApplicationAGB.Utils.HibernateUtil;

public class VehiclesRepository {
      @SuppressWarnings("deprecation")
    public void insertVehicleToMainTable(VehicleDTO vehicleDTO){
        Session session = null;
      try {
        session = HibernateUtil.getSession();

       if (session.get(VehicleDTO.class, vehicleDTO.getMatricula())!=null) { return;} //si existe ya en la base skipea
        session.beginTransaction();
        session.save(vehicleDTO);
        session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();           
        }finally{
            session.close();
        }
    }
    
    public VehicleDTO getVehicleByDNI(String DNI){
        Session session=null;
      try {
            session=  HibernateUtil.getSession();
            return session.createQuery("FROM mm_vehicles where dniUsuario = :DNI",VehicleDTO.class)
            .setParameter("DNI",DNI)
            .uniqueResult();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }finally{
            if (session != null ) {session.close();}
        }
     
     
    }
}
