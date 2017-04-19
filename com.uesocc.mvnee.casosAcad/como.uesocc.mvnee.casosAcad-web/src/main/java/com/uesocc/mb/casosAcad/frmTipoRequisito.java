package com.uesocc.mb.casosAcad;

import com.uesocc.entities.casosAcad.TipoRequisito;
import com.uesocc.facades.casosAcad.TipoRequisitoFacadeLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

@Named(value = "frmTipoRequisito")
@ViewScoped
public class frmTipoRequisito implements Serializable{
        @EJB
        TipoRequisitoFacadeLocal ejbTipoRequisito;
        TipoRequisito tipoRequisito = new TipoRequisito();boolean validar=true;
                     
    public TipoRequisito getTipoRequisito() {
      
        return tipoRequisito;
       
    }

    public void setTipoRequisito(TipoRequisito tipoRequisito) {
        this.tipoRequisito = tipoRequisito;
         
    }
        
    public frmTipoRequisito(){
    }
    
    public boolean command(){
    return true;
    }
    
    public List<TipoRequisito> obtenerTodos(){
        List<TipoRequisito> salida = new ArrayList();
            try {
                if (ejbTipoRequisito != null) {
                    salida = ejbTipoRequisito.findAll();
                }
            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            }
            return salida;
        }
    
        
    public void validar(FacesContext context, UIComponent toValidate, Object value){
        context= FacesContext.getCurrentInstance();
        String texto=(String)value;
        if(texto.isEmpty() || texto.startsWith(" ") || texto==null){
            
                    
           ((UIInput)toValidate).setValid(false); 
           context.addMessage(toValidate.getClientId(context), new FacesMessage("Campo Obligatorio"));
           
            }
    
    }
    
    public void nuevo(){
    this.tipoRequisito=new TipoRequisito();
    
    }
    
    public void obtener(TipoRequisito tr){
    this.tipoRequisito=tr;
    }
    
    public void eliminar(){
     try {  
            if (this.tipoRequisito != null && this.ejbTipoRequisito != null) {
                ejbTipoRequisito.remove(this.tipoRequisito);
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }
    
    public void actualizar(){
        
        if(this.tipoRequisito.getNombre().isEmpty() != true && this.tipoRequisito.getObservaciones().isEmpty() !=true &&this.tipoRequisito.getNombre() != null && this.tipoRequisito.getObservaciones() !=null  ){
        
        try {  
            if (this.tipoRequisito != null && this.ejbTipoRequisito != null) {
                ejbTipoRequisito.edit(this.tipoRequisito);   
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Se actualizo!", null) );

            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        
        }
    }
   
    public void crear(){
        
         if(this.tipoRequisito.getNombre().isEmpty() != true && this.tipoRequisito.getObservaciones().isEmpty() !=true &&this.tipoRequisito.getNombre() != null && this.tipoRequisito.getObservaciones() !=null  ){
        
        try {  
            if (this.tipoRequisito != null && this.ejbTipoRequisito != null ) {
                 ejbTipoRequisito.create(this.tipoRequisito);
                 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Creado con Exito!", null) );
                 nuevo();
                }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    } 
    }
}
