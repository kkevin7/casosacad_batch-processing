/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ues.casosAcad.prime.beans;

import com.uesocc.entities.casosAcad.CasoDetalle;
import com.uesocc.facades.casosAcad.CasoDetalleFacadeLocal;
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
@Named(value = "frmCasoDetallePrime")
@ViewScoped
public class FrmCasoDetallePrime implements Serializable{

        @EJB
    private CasoDetalleFacadeLocal ejbCasoDetalle;
    
    private boolean btnadd = false; //encapsulado
    private boolean btnedit = false; //encapsulado
    private boolean btnremove = false; //encapsulado
    private boolean frmcrud = false; //encapsulado
    private boolean frmcrudsts = true; // encapsulado
    
    private LazyDataModel<CasoDetalle> modelo;
    private CasoDetalle registro;
    
    MensajesFormularios mensaje = new MensajesFormularios(); //Mensajes de validacion
    
    /*-------------Getter and Setter ----------*/

    public CasoDetalleFacadeLocal getEjbCasoDetalle() {
        return ejbCasoDetalle;
    }

    public void setEjbCasoDetalle(CasoDetalleFacadeLocal ejbCasoDetalle) {
        this.ejbCasoDetalle = ejbCasoDetalle;
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

    public LazyDataModel<CasoDetalle> getModelo() {
        return modelo;
    }

    public void setModelo(LazyDataModel<CasoDetalle> modelo) {
        this.modelo = modelo;
    }

    public CasoDetalle getRegistro() {
        return registro;
    }

    public void setRegistro(CasoDetalle registro) {
        this.registro = registro;
    }

    public MensajesFormularios getMensaje() {
        return mensaje;
    }

    public void setMensaje(MensajesFormularios mensaje) {
        this.mensaje = mensaje;
    }
    
    /*-------------End Getter and Setter --------*/
    
    public FrmCasoDetallePrime() {
    }
    
    @Deprecated
    public List<CasoDetalle> obtenerTodos(){
        List<CasoDetalle> salida = new ArrayList();
            try {
                if (ejbCasoDetalle != null) {
                    salida = ejbCasoDetalle.findAll();
                }
            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            }
            return salida;
        }
    
    @PostConstruct
    private void inicio(){
        
        registro = new CasoDetalle();
        
        try {
            
            modelo = new LazyDataModel<CasoDetalle>() {
                @Override
                public Object getRowKey(CasoDetalle object){
                    if(object != null){
                        return object.getIdCasoDetalle();
                    }
                    return null;
                }
                
                @Override
                public  CasoDetalle getRowData(String rowKey){
                    if(rowKey != null && !rowKey.isEmpty() && this.getWrappedData() != null){
                        try {
                            Integer buscado = new Integer(rowKey);
                            for (CasoDetalle thi : (List<CasoDetalle>) getWrappedData()) {
                                if(thi.getIdCasoDetalle().compareTo(buscado)  == 0){
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
                public List<CasoDetalle> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
                    List<CasoDetalle> salida = new ArrayList(); 
                    
                    if (filters == null || filters.isEmpty()) {
                    try {
                        if(ejbCasoDetalle != null){
                            this.setRowCount(ejbCasoDetalle.count());
                            salida= ejbCasoDetalle.findRange(first,pageSize);
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
                                salida = ejbCasoDetalle.findBy("idProcesoDetalle", filters.get("idProcesoDetalle").toString(), first, pageSize);
                            if (modelo != null) {
                                modelo.setRowCount(salida.size());
                            }
                            
                            } else if(filters.containsKey("idProceso")){
                                salida = ejbCasoDetalle.filtroForaneo("idProceso", "nombre", filters.get("idProceso").toString(), first, pageSize);
                            if (modelo != null) {
                                modelo.setRowCount(salida.size());
                            }
                            
                            } else if(filters.containsKey("idPaso")){
                                salida = ejbCasoDetalle.filtroForaneo("idPaso", "nombre", filters.get("idPaso").toString(), first, pageSize);
                            if (modelo != null) {
                                modelo.setRowCount(salida.size());
                            }
                            
                            }
                            else if(filters.containsKey("indice")){
                                salida = ejbCasoDetalle.findBy("indice", filters.get("indice").toString(), first, pageSize);
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
        this.registro = new CasoDetalle();
            setBtnadd(true);
            setBtnedit(false);
            setBtnremove(false);
            setFrmcrudsts(false);
    }
    
    public void crearRegistro(){
    if(this.registro.getEstado().isEmpty() != true && this.registro.getFecha() != null) {

        try {
            if(this.ejbCasoDetalle!= null && this.registro != null){
                this.ejbCasoDetalle.create(registro);
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
            if (this.ejbCasoDetalle != null && registro != null) {
                ejbCasoDetalle.remove(this.registro);
                nuevo();
                mensaje.msgEliminacion();
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }
    
    public void editarRegistro(){
        if(this.registro.getEstado().isEmpty() != true && this.registro.getFecha() != null) {

	    try{
		if(this.registro != null && this.ejbCasoDetalle != null){
		    this.ejbCasoDetalle.edit(registro);
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
      this.registro=(CasoDetalle)e.getObject();
      setBtnedit(true);
      setBtnremove(true);
      setBtnadd(false);
      setFrmcrudsts(false);
    }
    
}
