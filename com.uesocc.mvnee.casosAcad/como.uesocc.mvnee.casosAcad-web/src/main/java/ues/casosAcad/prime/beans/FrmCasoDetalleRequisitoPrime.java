/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ues.casosAcad.prime.beans;

import com.uesocc.entities.casosAcad.CasoDetalleRequisito;
import com.uesocc.facades.casosAcad.CasoDetalleRequisitoFacadeLocal;
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
@Named(value = "frmCasoDetalleRequisitoPrime")
@ViewScoped
public class FrmCasoDetalleRequisitoPrime implements Serializable{

    @EJB
    private CasoDetalleRequisitoFacadeLocal ejbCasoDetalleRequisito;
    
    private boolean btnadd = false; //encapsulado
    private boolean btnedit = false; //encapsulado
    private boolean btnremove = false; //encapsulado
    private boolean frmcrud = false; //encapsulado
    private boolean frmcrudsts = true; // encapsulado
    
    private LazyDataModel<CasoDetalleRequisito> modelo;
    private CasoDetalleRequisito registro;
    
    MensajesFormularios mensaje = new MensajesFormularios(); //Mensajes de validacion
    
    /*-------Getter and Setter --------*/

    public CasoDetalleRequisitoFacadeLocal getEjbCasoDetalleRequisito() {
        return ejbCasoDetalleRequisito;
    }

    public void setEjbCasoDetalleRequisito(CasoDetalleRequisitoFacadeLocal ejbCasoDetalleRequisito) {
        this.ejbCasoDetalleRequisito = ejbCasoDetalleRequisito;
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

    public LazyDataModel<CasoDetalleRequisito> getModelo() {
        return modelo;
    }

    public void setModelo(LazyDataModel<CasoDetalleRequisito> modelo) {
        this.modelo = modelo;
    }

    public CasoDetalleRequisito getRegistro() {
        return registro;
    }

    public void setRegistro(CasoDetalleRequisito registro) {
        this.registro = registro;
    }

    public MensajesFormularios getMensaje() {
        return mensaje;
    }

    public void setMensaje(MensajesFormularios mensaje) {
        this.mensaje = mensaje;
    }
    
    /*-------End Getter and Setter -------*/
    
    public FrmCasoDetalleRequisitoPrime() {
    }
    
    @Deprecated
    public List<CasoDetalleRequisito> obtenerTodos(){
        List<CasoDetalleRequisito> salida = new ArrayList();
            try {
                if (ejbCasoDetalleRequisito != null) {
                    salida = ejbCasoDetalleRequisito.findAll();
                }
            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            }
            return salida;
        }
    
    @PostConstruct
    private void inicio(){
        
        registro = new CasoDetalleRequisito();
        
        try {
            
            modelo = new LazyDataModel<CasoDetalleRequisito>() {
                @Override
                public Object getRowKey(CasoDetalleRequisito object){
                    if(object != null){
                        return object.getIdCasoDR();
                    }
                    return null;
                }
                
                @Override
                public  CasoDetalleRequisito getRowData(String rowKey){
                    if(rowKey != null && !rowKey.isEmpty() && this.getWrappedData() != null){
                        try {
                            Integer buscado = new Integer(rowKey);
                            for (CasoDetalleRequisito thi : (List<CasoDetalleRequisito>) getWrappedData()) {
                                if(thi.getIdCasoDR().compareTo(buscado)  == 0){
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
                public List<CasoDetalleRequisito> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
                    List<CasoDetalleRequisito> salida = new ArrayList(); 
                    
                    if (filters == null || filters.isEmpty()) {
                    try {
                        if(ejbCasoDetalleRequisito != null){
                            this.setRowCount(ejbCasoDetalleRequisito.count());
                            salida= ejbCasoDetalleRequisito.findRange(first,pageSize);
                        }
                    } catch (Exception e) {
                         Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
                    }
                    return salida;
                }
                salida = null;
                    try {
                        if (!filters.isEmpty() && (filters.containsKey("idCDR")||filters.containsKey("idCasoDetalle")||filters.containsKey("idPasoRequisito")||filters.containsKey("EstadoRequisito")||filters.containsKey("Fecha"))) {
                            
                            if(filters.containsKey("idCDR")){
                                salida = ejbCasoDetalleRequisito.findBy("idCDR", filters.get("idCDR").toString(), first, pageSize);
                            if (modelo != null) {
                                modelo.setRowCount(salida.size());
                            }
                            
                            } else if(filters.containsKey("idCasoDetalle")){
                                salida = ejbCasoDetalleRequisito.filtroForaneo("idCasoDetalle", "Estado", filters.get("idCasoDetalle").toString(), first, pageSize);
                            if (modelo != null) {
                                modelo.setRowCount(salida.size());
                            }
                            
                            } else if(filters.containsKey("idPasoRequisito")){
                                salida = ejbCasoDetalleRequisito.findBy("idPasoRequisito", filters.get("idPasoRequisito").toString(), first, pageSize);
                            if (modelo != null) {
                                modelo.setRowCount(salida.size());
                            }
                            
                            }
                            else if(filters.containsKey("EstadoRequisito")){
                                salida = ejbCasoDetalleRequisito.findBy("EstadoRequisito", filters.get("EstadoRequisito").toString(), first, pageSize);
                            if (modelo != null) {
                                modelo.setRowCount(salida.size());
                            }
                            
                            }
                            else if(filters.containsKey("Fecha")){
                                salida = ejbCasoDetalleRequisito.findBy("Fecha", filters.get("Fecha").toString(), first, pageSize);
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
        this.registro = new CasoDetalleRequisito();
            setBtnadd(true);
            setBtnedit(false);
            setBtnremove(false);
            setFrmcrudsts(false);
    }
    
    public void crearRegistro(){
    if(this.registro.getEstadoRequisito().isEmpty() != true && this.registro.getFecha() != null) {

        try {
            if(this.ejbCasoDetalleRequisito!= null && this.registro != null){
                this.ejbCasoDetalleRequisito.create(registro);
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
            if (this.ejbCasoDetalleRequisito != null && registro != null) {
                ejbCasoDetalleRequisito.remove(this.registro);
                nuevo();
                mensaje.msgEliminacion();
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }
    
    public void editarRegistro(){
        if(this.registro.getEstadoRequisito().isEmpty() != true && this.registro.getFecha() != null) {

	    try{
		if(this.registro != null && this.ejbCasoDetalleRequisito != null){
		    this.ejbCasoDetalleRequisito.edit(registro);
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
      this.registro=(CasoDetalleRequisito)e.getObject();
      setBtnedit(true);
      setBtnremove(true);
      setBtnadd(false);
      setFrmcrudsts(false);
    }
    
}
