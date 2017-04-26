/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ues.casosAcad.prime.beans;

import com.uesocc.entities.casosAcad.Caso;
import com.uesocc.facades.casosAcad.CasoFacadeLocal;
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
@Named(value = "frmCasoPrime")
@ViewScoped
public class FrmCasoPrime implements Serializable{

    @EJB
    private CasoFacadeLocal ejbCaso;
    /*
    @EJB
    private RequisitoFacadeLocal ejbRequisito2;
    */
    
    private boolean btnadd = false; //encapsulado
    private boolean btnedit = false; //encapsulado
    private boolean btnremove = false; //encapsulado
    private boolean frmcrud = false; //encapsulado
    private boolean frmcrudsts = true; // encapsulado
    
    private LazyDataModel<Caso> modelo;
    private Caso registro;
    
    MensajesFormularios mensaje = new MensajesFormularios(); //Mensajes de validacion
    
    /*-------Getter and Setter --------*/

    public CasoFacadeLocal getEjbCaso() {
        return ejbCaso;
    }

    public void setEjbCaso(CasoFacadeLocal ejbCaso) {
        this.ejbCaso = ejbCaso;
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

    public LazyDataModel<Caso> getModelo() {
        return modelo;
    }

    public void setModelo(LazyDataModel<Caso> modelo) {
        this.modelo = modelo;
    }

    public Caso getRegistro() {
        return registro;
    }

    public void setRegistro(Caso registro) {
        this.registro = registro;
    }

    public MensajesFormularios getMensaje() {
        return mensaje;
    }

    public void setMensaje(MensajesFormularios mensaje) {
        this.mensaje = mensaje;
    }
    
    
    
    /*-------End Getter and Setter ---------*/
    
    public FrmCasoPrime() {
    }
    
        
    @Deprecated
    public List<Caso> obtenerTodos(){
        List<Caso> salida = new ArrayList();
            try {
                if (ejbCaso != null) {
                    salida = ejbCaso.findAll();
                }
            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            }
            return salida;
        }
    
    @PostConstruct
    private void inicio(){
        
        registro = new Caso();
        
        try {
            
            modelo = new LazyDataModel<Caso>() {
                @Override
                public Object getRowKey(Caso object){
                    if(object != null){
                        return object.getIdCaso();
                    }
                    return null;
                }
                
                @Override
                public  Caso getRowData(String rowKey){
                    if(rowKey != null && !rowKey.isEmpty() && this.getWrappedData() != null){
                        try {
                            Integer buscado = new Integer(rowKey);
                            for (Caso thi : (List<Caso>) getWrappedData()) {
                                if(thi.getIdCaso().compareTo(buscado)  == 0){
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
                public List<Caso> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
                    List<Caso> salida = new ArrayList(); 
                    
                    if (filters == null || filters.isEmpty()) {
                    try {
                        if(ejbCaso != null){
                            this.setRowCount(ejbCaso.count());
                            salida= ejbCaso.findRange(first,pageSize);
                        }
                    } catch (Exception e) {
                         Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
                    }
                    return salida;
                }
                salida = null;
                    try {
                        if (!filters.isEmpty() && (filters.containsKey("idCaso")||filters.containsKey("idSolicitud")||filters.containsKey("idProceso"))) {
                            
                            if(filters.containsKey("idCaso")){
                                salida = ejbCaso.findBy("idCaso", filters.get("idCaso").toString(), first, pageSize);
                            if (modelo != null) {
                                modelo.setRowCount(salida.size());
                            }
                            
                            } else if(filters.containsKey("idSolicitud")){
                                salida = ejbCaso.filtroForaneo("idSolicitud", "descripcionSolicitud", filters.get("idSolicitud").toString(), first, pageSize);
                            if (modelo != null) {
                                modelo.setRowCount(salida.size());
                            }

                            } else if(filters.containsKey("idProceso")){
                                salida = ejbCaso.filtroForaneo("idProceso", "nombre", filters.get("idProceso").toString(), first, pageSize);
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
        this.registro = new Caso();
            setBtnadd(true);
            setBtnedit(false);
            setBtnremove(false);
            setFrmcrudsts(false);
    }
    
    public void crearRegistro(){
//    if(this.registro.get().isEmpty() != true && this.registro.getNombre().isEmpty() != true  && this.registro.getDescripcion()!= null && this.registro.getNombre()!= null) {

        try {
            if(this.ejbCaso != null && this.registro != null){
                this.ejbCaso.create(registro);
                nuevo();
                mensaje.msgCreadoExito();
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
                }
//    else{
//        mensaje.msgFaltanCampos();
//    }
//    }

    public void eliminar() {
        try {
            if (this.ejbCaso  != null && registro != null) {
                ejbCaso.remove(this.registro);
                nuevo();
                mensaje.msgEliminacion();
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }
    
    public void editarRegistro(){
//        if(this.registro.getDescripcion().isEmpty() != true && this.registro.getNombre().isEmpty() != true  && this.registro.getDescripcion()!= null && this.registro.getNombre()!= null) {

	    try{
		if(this.registro != null && this.ejbCaso != null){
		    this.ejbCaso.edit(registro);
                     nuevo();
                     mensaje.msgModificacion();
		}
	    }catch(Exception e){
		Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
	    }
        }
//        else{
//            mensaje.msgFaltanCampos();
//        }
//	}
    
    public void cambiarSeleccion(SelectEvent e){
      this.registro=(Caso)e.getObject();
      setBtnedit(true);
      setBtnremove(true);
      setBtnadd(false);
      setFrmcrudsts(false);
    }

    
}
