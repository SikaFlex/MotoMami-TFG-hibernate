package com.dam.tfg.MotoMammiApplicationAGB.Repositories;

import org.hibernate.Session;

import com.dam.tfg.MotoMammiApplicationAGB.Models.InterfazDTO;
import com.dam.tfg.MotoMammiApplicationAGB.Models.Translation;
import com.dam.tfg.MotoMammiApplicationAGB.Utils.Errors;
import com.dam.tfg.MotoMammiApplicationAGB.Utils.HibernateUtil;

public class TranslationRepository {
    

public String getTraductionByProvider(String codExternal,String provider){
        Session session=null;
      try {
            session = HibernateUtil.getSession();
                return session.createQuery("from mm_translation_aux where idProv=:CODPROV and cod_ext =:EXTERNAL_COD",Translation.class)
            .setParameter("EXTERNAL_COD", codExternal)
            .setParameter("CODPROV", provider)
            .uniqueResult()
            .getCod_int();//devuelve el codigo interno que es la traduccion
            
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return Errors.ERROR_TRADUCTION;
        }finally{
            session.close();
        }
}

}
