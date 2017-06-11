/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uesocc.batch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.batch.api.chunk.AbstractItemReader;
import javax.batch.runtime.context.JobContext;
import javax.el.ELContext;
import javax.enterprise.context.Dependent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Dependent
@Named
public class NewItemReader extends AbstractItemReader {

    private BufferedReader reader;
    @Inject
    JobContext jobContext;
    InputStream inputStream;
    private File file;

    @Override

    public void open(Serializable checkpoint) throws Exception {
//        
//      ELContext elContext = FacesContext.getCurrentInstance().getELContext();
//FrmBatchServlet neededBean 
//    = (FrmBatchServlet) FacesContext.getCurrentInstance().getApplication()
//    .getELResolver().getValue(elContext, null, "frmBatch");
//


        
        
        
       file = new File("/home/jssbbonilla/KKKKKKKKKK/mvncasostpi/com.uesocc.mvnee.casosAcad/como.uesocc.mvnee.casosAcad-web/solicitudes.csv");
        InputStream targetStream = new FileInputStream(file);
         reader = new BufferedReader(
                new InputStreamReader(targetStream));
        reader.readLine();
         
    }

    @Override
    public String readItem() {
        try {
            
            return reader.readLine();
        } catch (IOException ex) {
            Logger.getLogger(NewItemReader.class.getName()).log(Level.ALL.SEVERE, null, ex);
        }
        return null;
    }
}
