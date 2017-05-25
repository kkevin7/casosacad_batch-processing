/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ues.casosAcad.prime.beans;

import com.uesocc.entities.casosAcad.DbCdra;
import com.uesocc.facades.casosAcad.DbCdraFacadeLocal;
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
import org.primefaces.model.SortOrder;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author kevin
 */
@Named(value = "frmCasoDetalleRequisitoAtestadoPrime")
@ViewScoped
public class FrmCasoDetalleRequisitoAtestadoPrime implements Serializable{

    @EJB
    private DbCdraFacadeLocal ejbCasoDetalleRequisitoAtestado;
    
    private boolean btnadd = false; //encapsulado
    private boolean btnedit = false; //encapsulado
    private boolean btnremove = false; //encapsulado
    private boolean frmcrud = false; //encapsulado
    private boolean frmcrudsts = true; // encapsulado
    private LazyDataModel<DbCdra> modelo;
    private DbCdra registro;
    
    MensajesFormularios mensaje = new MensajesFormularios(); //Mensajes de validacion
    
        /*-------Getter and Setter -------*/

    /**
     * @return the ejbCasoDetalleRequisitoAtestado
     */
    public DbCdraFacadeLocal getEjbCasoDetalleRequisitoAtestado() {
        return ejbCasoDetalleRequisitoAtestado;
    }

    /**
     * @param ejbCasoDetalleRequisitoAtestado the ejbCasoDetalleRequisitoAtestado to set
     */
    public void setEjbCasoDetalleRequisitoAtestado(DbCdraFacadeLocal ejbCasoDetalleRequisitoAtestado) {
        this.ejbCasoDetalleRequisitoAtestado = ejbCasoDetalleRequisitoAtestado;
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

    /**
     * @return the frmcrudsts
     */
    public boolean isFrmcrudsts() {
        return frmcrudsts;
    }

    /**
     * @param frmcrudsts the frmcrudsts to set
     */
    public void setFrmcrudsts(boolean frmcrudsts) {
        this.frmcrudsts = frmcrudsts;
    }

    /**
     * @return the registro
     */
    public DbCdra getRegistro() {
        return registro;
    }

    /**
     * @param registro the registro to set
     */
    public void setRegistro(DbCdra registro) {
        this.registro = registro;
    }
    public LazyDataModel<DbCdra> getModelo() {
        return modelo;
    }

    public void setModelo(LazyDataModel<DbCdra> modelo) {
        this.modelo = modelo;
    }
        /*-------End Getter and Setter -------*/

     
    public FrmCasoDetalleRequisitoAtestadoPrime() {
    }
    
    public List<DbCdra> obtenerTodos(){
        List<DbCdra> salida = new ArrayList();
            try {
                if (ejbCasoDetalleRequisitoAtestado != null) {
                    salida = ejbCasoDetalleRequisitoAtestado.findAll();
                }
            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            }
            return salida;
        }
    
    @PostConstruct
    private void inicio(){
        
        registro = new DbCdra();
       
        try {
            
            modelo = new LazyDataModel<DbCdra>() {
                @Override
                public Object getRowKey(DbCdra object){
                    if(object != null){
                        return object.getIdCDRA();
                    }
                    return null;
                }
                
                @Override
                public  DbCdra getRowData(String rowKey){
                    if(rowKey != null && !rowKey.isEmpty() && this.getWrappedData() != null){
                        try {
                            Integer buscado = new Integer(rowKey);
                            for (DbCdra thi : (List<DbCdra>) getWrappedData()) {
                                if(thi.getIdCDRA().compareTo(buscado)  == 0){
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
                public List<DbCdra> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
                    List<DbCdra> salida = new ArrayList(); 
                    
                    if (filters == null || filters.isEmpty()) {
                    try {
                        if(ejbCasoDetalleRequisitoAtestado != null){
                            this.setRowCount(ejbCasoDetalleRequisitoAtestado.count());
                            salida= ejbCasoDetalleRequisitoAtestado.findRange(first,pageSize);
                        }
                    } catch (Exception e) {
                         Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
                    }
                    return salida;
                }
                salida = null;
                    try {
                        if (!filters.isEmpty() && (filters.containsKey("idCDRA")||filters.containsKey("idCasoDR")||filters.containsKey("Fecha"))) {
                            
                            if(filters.containsKey("idCDRA")){
                                salida = ejbCasoDetalleRequisitoAtestado.findBy("idCDR", filters.get("idCDR").toString(), first, pageSize);
                            if (modelo != null) {
                                modelo.setRowCount(salida.size());
                            }
                            
                            } else if(filters.containsKey("idCasoDR")){
                                salida = ejbCasoDetalleRequisitoAtestado.filtroForaneo("idCasoDR", "Estado", filters.get("idCasoDR").toString(), first, pageSize);
                            if (modelo != null) {
                                modelo.setRowCount(salida.size());
                            }
                            
                            } 
                            else if(filters.containsKey("Fecha")){
                                salida = ejbCasoDetalleRequisitoAtestado.findBy("Fecha", filters.get("Fecha").toString(), first, pageSize);
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
        this.registro = new DbCdra();
            setBtnadd(true);
            setBtnedit(false);
            setBtnremove(false);
            setFrmcrudsts(false);
    }
    
    public void crearRegistro(){
    if(this.registro.getDescripcion().isEmpty() != true && this.registro.getFecha() != null) {

        try {
            if(this.ejbCasoDetalleRequisitoAtestado!= null && this.registro != null){
                this.ejbCasoDetalleRequisitoAtestado.create(registro);
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
            if (this.ejbCasoDetalleRequisitoAtestado != null && registro != null) {
                ejbCasoDetalleRequisitoAtestado.remove(this.registro);
                nuevo();
                mensaje.msgEliminacion();
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }
    
    public void editarRegistro(){
        if(this.registro.getDescripcion().isEmpty() != true && this.registro.getFecha() != null) {

	    try{
		if(this.registro != null && this.ejbCasoDetalleRequisitoAtestado != null){
		    this.ejbCasoDetalleRequisitoAtestado.edit(registro);
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
      this.registro=(DbCdra)e.getObject();
      setBtnedit(true);
      setBtnremove(true);
      setBtnadd(false);
      setFrmcrudsts(false);
    }
   
    
}
