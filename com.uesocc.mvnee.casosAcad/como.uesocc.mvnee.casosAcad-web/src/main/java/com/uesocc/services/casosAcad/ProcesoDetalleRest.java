package com.uesocc.services.casosAcad;

import com.uesocc.entities.casosAcad.ProcesoDetalle;
import com.uesocc.facades.casosAcad.ProcesoDetalleFacadeLocal;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;


@Path("procesodetalle")
public class ProcesoDetalleRest  implements Serializable{
        
    @EJB 
    private ProcesoDetalleFacadeLocal ejbProcesoDetalle;
    
    @GET 
    @Produces({MediaType.APPLICATION_JSON})
    public List<ProcesoDetalle> findall(){
        List salida= null;
        try {
            if(ejbProcesoDetalle!=null)
            {
                return ejbProcesoDetalle.findAll();
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
            if (ejbProcesoDetalle != null) {
                return ejbProcesoDetalle.count();
            }

        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return 0;
    }
   @GET
    @Path("id/{id}")
    @Produces({MediaType.APPLICATION_JSON + "; charset=utf-8"})
    public ProcesoDetalle findById(
            @PathParam("id") Integer id
    ) {
        try {
            if (ejbProcesoDetalle != null) {
                return ejbProcesoDetalle.find(id);
            }

        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return new ProcesoDetalle();
    }
    
    @GET 
    @Path("idproceso/")
    @Produces({MediaType.APPLICATION_JSON})
    public List<ProcesoDetalle> findByExt(
            @QueryParam("idproceso") String id
           
    ){
    
        List salida= null;
        try {
            if(ejbProcesoDetalle!=null)
            {
                return ejbProcesoDetalle.filtroForaneo("idProceso","idProceso",id,0,10);

            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    
    return salida;
    }
 
    
    
}
