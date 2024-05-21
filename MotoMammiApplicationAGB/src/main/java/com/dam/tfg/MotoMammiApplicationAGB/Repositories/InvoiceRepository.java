package com.dam.tfg.MotoMammiApplicationAGB.Repositories;





import java.sql.Date;
import java.util.List;

import org.hibernate.Session;

import com.dam.tfg.MotoMammiApplicationAGB.Models.InvoiceDTO;
import com.dam.tfg.MotoMammiApplicationAGB.Utils.HibernateUtil;

public class InvoiceRepository {

   public List<InvoiceDTO> getInvoiceFromUntilToday(String codProv,Date lastMonth){
         Session session=null;
      try {
            session=  HibernateUtil.getSession();
            return session.createQuery("FROM mm_invoices where codProv = :EXTERNALCOD AND fecha_emision  BETWEEN :DATE AND now()",InvoiceDTO.class)
            .setParameter("EXTERNALCOD",codProv)
            .setParameter("DATE",lastMonth)
            .list();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }finally{
            if (session != null ) {session.close();}
        }
     
    }
}
