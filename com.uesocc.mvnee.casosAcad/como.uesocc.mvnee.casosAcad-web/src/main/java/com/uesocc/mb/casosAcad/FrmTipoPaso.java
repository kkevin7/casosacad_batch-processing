package com.uesocc.mb.casosAcad;

import com.uesocc.entities.casosAcad.TipoPaso;
import com.uesocc.facades.casosAcad.TipoPasoFacadeLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class FrmTipoPaso implements Serializable{
    
    @EJB
    TipoPasoFacadeLocal ejbTipoPaso;
    TipoPaso tipoPaso = new TipoPaso();

    public TipoPaso getTipoPaso() {
        return tipoPaso;
    }
    public void setTipoPaso(TipoPaso tipoPaso) {
        this.tipoPaso = tipoPaso;
    }

    public List<TipoPaso> obtenerTodos(){
        List<TipoPaso> salida = new ArrayList();
            try {
                if (ejbTipoPaso != null) {
                    salida = ejbTipoPaso.findAll();
                }
            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            }
            return salida;
    }
    
    public void nuevo(){
        this.tipoPaso=new TipoPaso();
    }
    public void obtener(TipoPaso tr){
    this.tipoPaso=tr;
    }
    public void eliminar(){
    try {  
            if (this.tipoPaso != null && this.ejbTipoPaso != null) {
                ejbTipoPaso.remove(this.tipoPaso);
            }
    } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }
    
      public void actualizar(){
          
   if(this.tipoPaso.getNombre().isEmpty() != true && this.tipoPaso.getDescripcion().isEmpty() != true && this.tipoPaso.getNombre() != null && this.tipoPaso.getDescripcion() != null) {     
          
    try {  
            if (this.tipoPaso != null && this.ejbTipoPaso != null) {
                ejbTipoPaso.edit(this.tipoPaso);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Se actualizo!", null) );

            }
    } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
   }
    }
    
    public void crear(){
        
        if(this.tipoPaso.getNombre().isEmpty() != true && this.tipoPaso.getDescripcion().isEmpty() != true && this.tipoPaso.getNombre() != null && this.tipoPaso.getDescripcion() != null) {
        
   try {  
            if (this.tipoPaso != null && this.ejbTipoPaso != null) {
                ejbTipoPaso.create(this.tipoPaso);
                this.tipoPaso=new TipoPaso();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Creado con Exito!", null) );
            }
    } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    } 
    }
}
