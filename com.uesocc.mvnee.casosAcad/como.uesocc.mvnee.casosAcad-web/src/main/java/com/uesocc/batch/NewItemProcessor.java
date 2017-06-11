/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uesocc.batch;

import com.uesocc.entities.casosAcad.Proceso;
import com.uesocc.entities.casosAcad.Solicitudes;
import com.uesocc.facades.casosAcad.ProcesoFacadeLocal;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.batch.api.chunk.ItemProcessor;
import javax.batch.runtime.context.JobContext;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

@Dependent
@Named(value = "newItemProcessor")
public class NewItemProcessor implements ItemProcessor {

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    Solicitudes nsolicitud = new Solicitudes();
    Date fecha = null;
    Proceso nProceso = new Proceso();
    @Inject
    protected JobContext jobContext;
    @EJB
    private ProcesoFacadeLocal ejbProceso;
    String rs;

    @Override

    public Object processItem(Object item) {
     
            System.out.println("processItem: " + item);
            
            rs = item.toString().replace("\"", "");
            StringTokenizer tokens = new StringTokenizer(rs, "|");
            
            String carnet = tokens.nextToken();
            int idProceso;
            idProceso = Integer.valueOf(tokens.nextToken());
            nProceso = ejbProceso.find(idProceso);
             
              try {
            String fechatexto = tokens.nextToken();            
            fecha = format.parse(fechatexto);
            
            
        } catch (ParseException ex) {
            Logger.getLogger(NewItemProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
              return new Solicitudes(fecha, nProceso, carnet);
    }
}
