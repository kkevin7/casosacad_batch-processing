/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uesocc.batch;

import com.uesocc.facades.casosAcad.SolicitudesFacadeLocal;
import java.util.List;
import com.uesocc.entities.casosAcad.Solicitudes;
import javax.batch.api.chunk.AbstractItemWriter;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Named;


@Dependent
@Named(value="newItemWriter")
public class NewItemWriter extends AbstractItemWriter {

    @EJB
    SolicitudesFacadeLocal ejbSolicitudes;

    @Override
    public void writeItems(List list) {
        System.out.println("writeItems: " + list);
        for (Object solicitudes : list) {
            ejbSolicitudes.create((Solicitudes)solicitudes);
        }
    }
}
