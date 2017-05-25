/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uesocc.services.casosAcad;

import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;




@ApplicationPath("websources")
public class ApplicationConfig extends Application{   
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        resources.add(TipoRequisitoRest.class);
        resources.add(ProcesoRest.class);
        resources.add(PasoRest.class);   
        resources.add(SolicitudRest.class);

        
        resources.add(PasoRequisitoRest.class);
        resources.add(ProcesoDetalleRest.class);
        resources.add(NewCrossOriginResourceSharingFilter.class);
        return resources;
    }
    private void addRestResourceClasses(Set<Class<?>> resources){
        resources.add(com.uesocc.services.casosAcad.NewCrossOriginResourceSharingFilter.class);
        resources.add(com.uesocc.services.casosAcad.PasoRequisitoRest.class);
        resources.add(com.uesocc.services.casosAcad.PasoRest.class);
        resources.add(com.uesocc.services.casosAcad.ProcesoDetalleRest.class);
        resources.add(com.uesocc.services.casosAcad.ProcesoRest.class);
        resources.add(com.uesocc.services.casosAcad.SolicitudRest.class);
        resources.add(com.uesocc.services.casosAcad.TipoPasoRest.class);
        resources.add(com.uesocc.services.casosAcad.TipoRequisitoRest.class);
    }  
}
