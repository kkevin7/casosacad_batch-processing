/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uesocc.services.casosAcad;

import com.uesocc.entities.casosAcad.TipoPaso;
import com.uesocc.facades.casosAcad.TipoPasoFacadeLocal;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author jssbbonilla
 */
@Path("tipopaso")
public class TipoPasoRest  implements Serializable{
        
    @EJB 
    private TipoPasoFacadeLocal ejbTipoPaso;
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<TipoPaso> findall(){
        List salida= null;
        try {
            if(ejbTipoPaso!=null)
            {
                return ejbTipoPaso.findAll();
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    
    return salida;
    }
    
    
    
    
    @Path("count")
    @GET
    @Produces({MediaType.TEXT_PLAIN})
    public Integer count() {

        try {
            if (ejbTipoPaso != null) {
                return ejbTipoPaso.count();
            }

        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return 0;
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON + "; charset=utf-8"})
    public TipoPaso findById(
            @PathParam("id") Integer id
    ) {
        try {
            if (ejbTipoPaso != null) {
                return ejbTipoPaso.find(id);
            }

        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return new TipoPaso();
    }
    
    
    
}
