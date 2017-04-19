/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uesocc.services.casosAcad;

import com.uesocc.entities.casosAcad.TipoRequisito;
import com.uesocc.facades.casosAcad.TipoRequisitoFacadeLocal;
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
@Path("tiporequisito")
public class TipoRequisitoRest implements Serializable{
    
    @EJB 
    private TipoRequisitoFacadeLocal ejbTipoRequisito;
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<TipoRequisito> findall(){
        List salida= null;
        try {
            if(ejbTipoRequisito!=null)
            {
                return ejbTipoRequisito.findAll();
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
            if (ejbTipoRequisito != null) {
                return ejbTipoRequisito.count();
            }

        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return 0;
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON + "; charset=utf-8"})
    public TipoRequisito findById(
            @PathParam("id") Integer id
    ) {
        try {
            if (ejbTipoRequisito != null) {
                return ejbTipoRequisito.find(id);
            }

        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return new TipoRequisito();
    }
    
    
}
