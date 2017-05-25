package com.uesocc.services.casosAcad;

import com.uesocc.entities.casosAcad.PasoRequisito;
import com.uesocc.facades.casosAcad.PasoRequisitoFacadeLocal;
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


@Path("pasorequisito/")
public class PasoRequisitoRest  implements Serializable{
        
    @EJB 
    private PasoRequisitoFacadeLocal ejbPasoRequisito;
    
    @GET 
    @Produces({MediaType.APPLICATION_JSON})
    public List<PasoRequisito> findall(){
        List salida= null;
        try {
            if(ejbPasoRequisito!=null)
            {
                return ejbPasoRequisito.findAll();
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
            if (ejbPasoRequisito != null) {
                return ejbPasoRequisito.count();
            }

        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return 0;
    }
   @GET
    @Path("id/{id}")
    @Produces({MediaType.APPLICATION_JSON + "; charset=utf-8"})
    public PasoRequisito
         findById(
            @PathParam("id") Integer id
    ) {
        try {
            if (ejbPasoRequisito != null) {
                return ejbPasoRequisito.find(id);
            }

        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return new PasoRequisito();
    }
    
    @GET 
    @Path("idrequisito/")
    @Produces({MediaType.APPLICATION_JSON})
    public List<PasoRequisito> findByExt(
            @QueryParam("idrequisito") String id
           
    ){
    
        List salida= null;
        try {
            if(ejbPasoRequisito!=null)
            {
                return ejbPasoRequisito.filtroForaneo("idPaso","idPaso",id,0,10);

            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    
    return salida;
    }
 
    
    
}