/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ues.part2.otrosfrmprimes;

import com.uesocc.entities.casosAcad.Paso;
import com.uesocc.entities.casosAcad.PasoRequisito;
import com.uesocc.facades.casosAcad.PasoFacadeLocal;
import com.uesocc.facades.casosAcad.PasoRequisitoFacadeLocal;
import com.uesocc.facades.casosAcad.RequisitoFacadeLocal;
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
/*--------- Borrar esta linea al unirlo-------------------*/
import ues.casosAcad.prime.beans.MensajesFormularios;
/*-------------------------------------------------------*/

/**
 *
 * @author kevin
 */
@Named(value = "frmPasoRequisito")
@ViewScoped
public class FrmPasoRequisito implements Serializable{
    
    @EJB
    private PasoRequisitoFacadeLocal ejbPasoRequisito;
    
    @EJB
    private PasoFacadeLocal ejbPaso2;
    @EJB
    private RequisitoFacadeLocal ejbRequisito2;
    
    
    private boolean btnadd = false; //encapsulado
    private boolean btnedit = false; //encapsulado
    private boolean btnremove = false; //encapsulado
    private boolean frmcrud = false; //encapsulado
    
    private LazyDataModel<PasoRequisito> modelo;
    private PasoRequisito registro;
    
    MensajesFormularios mensaje = new MensajesFormularios(); //Mensajes de validacion
    
    /*-------Getter and Setter --------*/

    public PasoRequisitoFacadeLocal getEjbPasoRequisito() {
        return ejbPasoRequisito;
    }

    public void setEjbPasoRequisito(PasoRequisitoFacadeLocal ejbPasoRequisito) {
        this.ejbPasoRequisito = ejbPasoRequisito;
    }

    public PasoFacadeLocal getEjbPaso2() {
        return ejbPaso2;
    }

    public void setEjbPaso2(PasoFacadeLocal ejbPaso2) {
        this.ejbPaso2 = ejbPaso2;
    }

    public RequisitoFacadeLocal getEjbRequisito2() {
        return ejbRequisito2;
    }

    public void setEjbRequisito2(RequisitoFacadeLocal ejbRequisito2) {
        this.ejbRequisito2 = ejbRequisito2;
    }

    public LazyDataModel<PasoRequisito> getModelo() {
        return modelo;
    }

    public void setModelo(LazyDataModel<PasoRequisito> modelo) {
        this.modelo = modelo;
    }

    public PasoRequisito getRegistro() {
        return registro;
    }

    public void setRegistro(PasoRequisito registro) {
        this.registro = registro;
    }

    public MensajesFormularios getMensaje() {
        return mensaje;
    }

    public void setMensaje(MensajesFormularios mensaje) {
        this.mensaje = mensaje;
    }
    
    
    /*-------Getter and Setter --------*/
    
    
    public FrmPasoRequisito() {
    }
    
    @Deprecated
    public List<PasoRequisito> obtenerTodos(){
        List<PasoRequisito> salida = new ArrayList();
            try {
                if (ejbPasoRequisito != null) {
                    salida = ejbPasoRequisito.findAll();
                }
            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            }
            return salida;
        }
    
       
    @PostConstruct
    private void inicio(){
        
        registro = new PasoRequisito();
        
        try {
            
            modelo = new LazyDataModel<PasoRequisito>() {
                @Override
                public Object getRowKey(PasoRequisito object){
                    if(object != null){
                        return object.getIdPasoRequisito();
                    }
                    return null;
                }
                
                @Override
                public  PasoRequisito getRowData(String rowKey){
                    if(rowKey != null && !rowKey.isEmpty() && this.getWrappedData() != null){
                        try {
                            Integer buscado = new Integer(rowKey);
                            for (PasoRequisito thi : (List<PasoRequisito>) getWrappedData()) {
                                if(thi.getIdPasoRequisito().compareTo(buscado)  == 0){
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
                public List<PasoRequisito> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
                    List<PasoRequisito> salida = new ArrayList(); 
                    
                    if (filters == null || filters.isEmpty()) {
                    try {
                        if(ejbPasoRequisito != null){
                            this.setRowCount(ejbPasoRequisito.count());
                            salida= ejbPasoRequisito.findRange(first,pageSize);
                        }
                    } catch (Exception e) {
                         Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
                    }
                    return salida;
                }
                salida = null;
                    try {
                        if (!filters.isEmpty() && (filters.containsKey("idPasoRequisito")||filters.containsKey("tiempo")||filters.containsKey("descripcion")||filters.containsKey("idTipoPaso"))) {
                            
                            if(filters.containsKey("idPasoRequisito")){
                                salida = ejbPasoRequisito.findBy("idPasoRequsito", filters.get("idPasoRequisito").toString(), first, pageSize);
                            if (modelo != null) {
                                modelo.setRowCount(salida.size());
                            }
                            
                            } else if(filters.containsKey("descripcion")){
                                salida = ejbPasoRequisito.findBy("descripcion", filters.get("descripcion").toString(), first, pageSize);
                            if (modelo != null) {
                                modelo.setRowCount(salida.size());
                            }
                            
                            //Tiempo
                            } else if(filters.containsKey("tiempo")){
                                salida = ejbPasoRequisito.findBy("tiempo", filters.get("tiempo").toString(), first, pageSize);
                            if (modelo != null) {
                                modelo.setRowCount(salida.size());
                            }
                            //fin tiempo
                            
                            }
                            else if(filters.containsKey("idTipoPaso")){
                                salida = ejbPasoRequisito.filtroForaneo("idTipoPaso", "nombre", filters.get("idTipoPaso").toString(), first, pageSize);
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
        this.registro = new PasoRequisito();
            setBtnadd(true);
            setBtnedit(false);
            setBtnremove(false);
    }
    
    public void crearRegistro(){
    if(this.registro.getIndice() != 0) {

        try {
            if(this.ejbPasoRequisito != null && this.registro != null){
                this.ejbPasoRequisito.create(registro);
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
            if (this.ejbPasoRequisito != null && registro != null) {
                ejbPasoRequisito.remove(this.registro);
                nuevo();
                mensaje.msgEliminacion();
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }
    
    public void editarRegistro(){
        if(this.registro.getIndice() != 0) {

	    try{
		if(this.registro != null && this.ejbPasoRequisito != null){
		    this.ejbPasoRequisito.edit(registro);
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
      this.registro=(PasoRequisito)e.getObject();
      setBtnedit(true);
      setBtnremove(true);
      setBtnadd(false);
    }

    /**
     * @return the btnadd
     */
    public boolean isBtnadd() {
        return btnadd;
    }

    /**
     * @param btnadd the btnadd to set
     */
    public void setBtnadd(boolean btnadd) {
        this.btnadd = btnadd;
    }

    /**
     * @return the btnedit
     */
    public boolean isBtnedit() {
        return btnedit;
    }

    /**
     * @param btnedit the btnedit to set
     */
    public void setBtnedit(boolean btnedit) {
        this.btnedit = btnedit;
    }

    /**
     * @return the btnremove
     */
    public boolean isBtnremove() {
        return btnremove;
    }

    /**
     * @param btnremove the btnremove to set
     */
    public void setBtnremove(boolean btnremove) {
        this.btnremove = btnremove;
    }

    /**
     * @return the frmcrud
     */
    public boolean isFrmcrud() {
        return frmcrud;
    }

    /**
     * @param frmcrud the frmcrud to set
     */
    public void setFrmcrud(boolean frmcrud) {
        this.frmcrud = frmcrud;
    }
    
}
