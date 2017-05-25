/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uesocc.services.casosAcad;

import com.uesocc.entities.casosAcad.Paso;
import com.uesocc.facades.casosAcad.PasoFacadeLocal;
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
@Path("paso")
public class PasoRest  implements Serializable{
        
    @EJB 
    private PasoFacadeLocal ejbPaso;
    
    @GET
    
    @Produces({MediaType.APPLICATION_JSON})
    public List<Paso> findall(){
        List salida= null;
        try {
            if(ejbPaso!=null)
            {
                return ejbPaso.findAll();
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
            if (ejbPaso != null) {
                return ejbPaso.count();
            }

        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return 0;
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON + "; charset=utf-8"})
    public Paso findById(
            @PathParam("id") Integer id
    ) {
        try {
            if (ejbPaso != null) {
                return ejbPaso.find(id);
            }

        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return new Paso();
    }
    
    
    
}
