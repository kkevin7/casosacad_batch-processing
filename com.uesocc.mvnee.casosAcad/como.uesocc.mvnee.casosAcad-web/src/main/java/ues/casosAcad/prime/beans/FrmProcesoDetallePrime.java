/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ues.casosAcad.prime.beans;

import com.uesocc.entities.casosAcad.ProcesoDetalle;
import com.uesocc.facades.casosAcad.ProcesoDetalleFacadeLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;


/**
 *
 * @author kevin
 */
@Named(value = "frmProcesoDetallePrime")
@ViewScoped
public class FrmProcesoDetallePrime implements Serializable{

    @EJB
    private ProcesoDetalleFacadeLocal ejbProcesoDetalle;
    
    private boolean btnadd = false; //encapsulado
    private boolean btnedit = false; //encapsulado
    private boolean btnremove = false; //encapsulado
    private boolean frmcrud = false; //encapsulado
    private boolean frmcrudsts = true; // encapsulado
    
    private LazyDataModel<ProcesoDetalle> modelo;
    private ProcesoDetalle registro;
    
    MensajesFormularios mensaje = new MensajesFormularios(); //Mensajes de validacion
    
    /*---------- Getter and Settter ----------------------*/

    public ProcesoDetalleFacadeLocal getEjbProcesoDetalle() {
        return ejbProcesoDetalle;
    }

    public void setEjbProcesoDetalle(ProcesoDetalleFacadeLocal ejbProcesoDetalle) {
        this.ejbProcesoDetalle = ejbProcesoDetalle;
    }

    public boolean isBtnadd() {
        return btnadd;
    }

    public void setBtnadd(boolean btnadd) {
        this.btnadd = btnadd;
    }

    public boolean isBtnedit() {
        return btnedit;
    }

    public void setBtnedit(boolean btnedit) {
        this.btnedit = btnedit;
    }

    public boolean isBtnremove() {
        return btnremove;
    }

    public void setBtnremove(boolean btnremove) {
        this.btnremove = btnremove;
    }

    public boolean isFrmcrud() {
        return frmcrud;
    }

    public void setFrmcrud(boolean frmcrud) {
        this.frmcrud = frmcrud;
    }

    public boolean isFrmcrudsts() {
        return frmcrudsts;
    }

    public void setFrmcrudsts(boolean frmcrudsts) {
        this.frmcrudsts = frmcrudsts;
    }

    public LazyDataModel<ProcesoDetalle> getModelo() {
        return modelo;
    }

    public void setModelo(LazyDataModel<ProcesoDetalle> modelo) {
        this.modelo = modelo;
    }

    public ProcesoDetalle getRegistro() {
        return registro;
    }

    public void setRegistro(ProcesoDetalle registro) {
        this.registro = registro;
    }

    public MensajesFormularios getMensaje() {
        return mensaje;
    }

    public void setMensaje(MensajesFormularios mensaje) {
        this.mensaje = mensaje;
    }
    
    
    /*---------- End Getter and Setter --------------------*/
    
    public FrmProcesoDetallePrime() {
    }
    
    @Deprecated
    public List<ProcesoDetalle> obtenerTodos(){
        List<ProcesoDetalle> salida = new ArrayList();
            try {
                if (ejbProcesoDetalle != null) {
                    salida = ejbProcesoDetalle.findAll();
                }
            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            }
            return salida;
        }
    
    @PostConstruct
    private void inicio(){
        
        registro = new ProcesoDetalle();
        
        try {
            
            modelo = new LazyDataModel<ProcesoDetalle>() {
                @Override
                public Object getRowKey(ProcesoDetalle object){
                    if(object != null){
                        return object.getIdProcesoDetalle();
                    }
                    return null;
                }
                
                @Override
                public  ProcesoDetalle getRowData(String rowKey){
                    if(rowKey != null && !rowKey.isEmpty() && this.getWrappedData() != null){
                        try {
                            Integer buscado = new Integer(rowKey);
                            for (ProcesoDetalle thi : (List<ProcesoDetalle>) getWrappedData()) {
                                if(thi.getIdProcesoDetalle().compareTo(buscado)  == 0){
                                    return thi;
                                }
                            }
                        } catch (Exception e) {
                            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
                        }
                    }
                    return null;
                }
                
                @Override
                public List<ProcesoDetalle> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
                    List<ProcesoDetalle> salida = new ArrayList(); 
                    
                    if (filters == null || filters.isEmpty()) {
                    try {
                        if(ejbProcesoDetalle != null){
                            this.setRowCount(ejbProcesoDetalle.count());
                            salida= ejbProcesoDetalle.findRange(first,pageSize);
                        }
                    } catch (Exception e) {
                         Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
                    }
                    return salida;
                }
                salida = null;
                    try {
                        if (!filters.isEmpty() && (filters.containsKey("idProcesoDetalle")||filters.containsKey("idProceso")||filters.containsKey("idPaso")||filters.containsKey("indice"))) {
                            
                            if(filters.containsKey("idProcesoDetalle")){
                                salida = ejbProcesoDetalle.findBy("idProcesoDetalle", filters.get("idProcesoDetalle").toString(), first, pageSize);
                            if (modelo != null) {
                                modelo.setRowCount(salida.size());
                            }
                            
                            } else if(filters.containsKey("idProceso")){
                                salida = ejbProcesoDetalle.filtroForaneo("idProceso", "nombre", filters.get("idProceso").toString(), first, pageSize);
                            if (modelo != null) {
                                modelo.setRowCount(salida.size());
                            }
                            
                            } else if(filters.containsKey("idPaso")){
                                salida = ejbProcesoDetalle.filtroForaneo("idPaso", "nombre", filters.get("idPaso").toString(), first, pageSize);
                            if (modelo != null) {
                                modelo.setRowCount(salida.size());
                            }
                            
                            }
                            else if(filters.containsKey("indice")){
                                salida = ejbProcesoDetalle.findBy("indice", filters.get("indice").toString(), first, pageSize);
                            if (modelo != null) {
                                modelo.setRowCount(salida.size());
                            }
                            }
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
                    } finally {
                        if (salida == null) {
                            salida = new ArrayList();
                        }
                    }
                    return salida;
                }
            };

        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }
    
    
     public void nuevo() {
        this.registro = new ProcesoDetalle();
            setBtnadd(true);
            setBtnedit(false);
            setBtnremove(false);
            setFrmcrudsts(false);
    }
    
    public void crearRegistro(){
    if(this.registro.getIndice() !=0) {

        try {
            if(this.ejbProcesoDetalle!= null && this.registro != null){
                this.ejbProcesoDetalle.create(registro);
                nuevo();
                mensaje.msgCreadoExito();
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
                }
    else{
        mensaje.msgFaltanCampos();
    }
    }

    public void eliminar() {
        try {
            if (this.ejbProcesoDetalle != null && registro != null) {
                ejbProcesoDetalle.remove(this.registro);
                nuevo();
                mensaje.msgEliminacion();
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }
    
    public void editarRegistro(){
        if(this.registro.getIndice() !=0) {

	    try{
		if(this.registro != null && this.ejbProcesoDetalle != null){
		    this.ejbProcesoDetalle.edit(registro);
                     nuevo();
                     mensaje.msgModificacion();
		}
	    }catch(Exception e){
		Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
	    }
        }
        else{
            mensaje.msgFaltanCampos();
        }
	}
    
    public void cambiarSeleccion(SelectEvent e){
      this.registro=(ProcesoDetalle)e.getObject();
      setBtnedit(true);
      setBtnremove(true);
      setBtnadd(false);
      setFrmcrudsts(false);
    }
    
}
