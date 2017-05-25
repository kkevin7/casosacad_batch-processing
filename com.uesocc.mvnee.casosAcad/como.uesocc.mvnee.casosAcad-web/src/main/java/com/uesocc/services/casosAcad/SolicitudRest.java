package com.uesocc.services.casosAcad;

import com.uesocc.entities.casosAcad.Proceso;
import com.uesocc.entities.casosAcad.Solicitudes;
import com.uesocc.facades.casosAcad.SolicitudesFacadeLocal;
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


@Path("solicitud")
public class SolicitudRest  implements Serializable{
        
    @EJB 
    private SolicitudesFacadeLocal ejbSolicitudes;
    
    @GET 
    @Produces({MediaType.APPLICATION_JSON})
    public List<Solicitudes> findall(){
        List salida= null;
        try {
            if(ejbSolicitudes!=null)
            {
                return ejbSolicitudes.findAll();
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
            if (ejbSolicitudes != null) {
                return ejbSolicitudes.count();
            }

        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return 0;
    }
   @GET
    @Path("id/{id}")
    @Produces({MediaType.APPLICATION_JSON + "; charset=utf-8"})
    public Solicitudes findById(
            @PathParam("id") Integer id
    ) {
        try {
            if (ejbSolicitudes != null) {
                return ejbSolicitudes.find(id);
            }

        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return new Solicitudes();
    }
    
    @GET 
    @Path("nueva/")
    @Produces({MediaType.TEXT_PLAIN})
    public String findByExt(
            @QueryParam("idproceso") int id,
            @QueryParam("carnet") String carnet

           
    ){
    
        String salida= null;
        try {
            if(ejbSolicitudes!=null)
            {
                Solicitudes solicitud = new Solicitudes();
                solicitud.setCarnet(carnet);
                solicitud.setNit(01);
                Proceso p = new Proceso();
                p.setIdProceso(id);
                solicitud.setIdProceso(p);
                java.util.Date fecha = new java.util.Date();
                solicitud.setFechaRecibida(fecha);
                solicitud.setEstado(true);
               
                ejbSolicitudes.create(solicitud);
                return "Creado con exito";

            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    
    return "No se creo la solicitud";
    }
 
    
    
}
