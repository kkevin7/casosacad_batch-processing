/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uesocc.batch;

import java.io.Console;
import java.io.IOException;
import org.primefaces.event.FileUploadEvent;
import java.io.Serializable;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.JobExecution;
import javax.batch.runtime.context.JobContext;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author jssbbonilla
 */
@Named(value = "frmBatch")
@ViewScoped
public class FrmBatchServlet implements Serializable {

 
    private String lol;
    private Long id;
 


   

        private UploadedFile file;

        public UploadedFile getFile() {
            return file;
        }

        public void setFile(UploadedFile file) {
            this.file = file;
        }

        public void upload() throws IOException {
          
            if (file != null) {
              
                FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
                FacesContext.getCurrentInstance().addMessage(null, message);
                System.out.println("pepe");
                
                
                
               
                

            }
        }
        
      

    public long startNewBatchJob()
throws Exception {
        JobOperator jobOperator = BatchRuntime.getJobOperator();
       Properties props = new Properties();
  
        return jobOperator.start("newJob", new Properties());
}

    public void process() {
        try {
            //STARTING JOB
            JobOperator jo = BatchRuntime.getJobOperator();
            long jid = jo.start("newJob", new Properties());
            JobExecution je = jo.getJobExecution(jid);  
            
            FacesMessage message = new FacesMessage("Trabajo con id ",je.getExecutionId()+ "Ha comenzado.");    
           FacesContext.getCurrentInstance().addMessage(null, message);
            

        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);

        }

    }

  


    /**
     * @return the lol
     */
    public String getLol() {
        return lol;
    }

    /**
     * @param lol the lol to set
     */
    public void setLol(String lol) {
        this.lol = lol;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

}
