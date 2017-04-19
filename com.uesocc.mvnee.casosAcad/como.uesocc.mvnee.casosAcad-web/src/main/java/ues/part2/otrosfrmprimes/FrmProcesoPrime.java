/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ues.part2.otrosfrmprimes;

import com.uesocc.entities.casosAcad.Proceso;
import com.uesocc.facades.casosAcad.ProcesoDetalleFacadeLocal;
import com.uesocc.facades.casosAcad.ProcesoFacadeLocal;
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
@Named(value = "frmProcesoPrime")
@ViewScoped
public class FrmProcesoPrime implements Serializable{

    @EJB
    private ProcesoFacadeLocal ejbProceso;
    
    @EJB
    private ProcesoDetalleFacadeLocal ejbProcesoDetalle2;
    
    
    private boolean btnadd = false; //encapsulado
    private boolean btnedit = false; //encapsulado
    private boolean btnremove = false; //encapsulado
    private boolean frmcrud = false; //encapsulado
    
    private LazyDataModel<Proceso> modelo;
    private Proceso registro;
    
    MensajesFormularios mensaje = new MensajesFormularios(); //Mensajes de validacion
    
    /*-------Getter and Setter --------*/

    public ProcesoFacadeLocal getEjbProceso() {
        return ejbProceso;
    }

    public void setEjbProceso(ProcesoFacadeLocal ejbProceso) {
        this.ejbProceso = ejbProceso;
    }

    public ProcesoDetalleFacadeLocal getEjbProcesoDetalle2() {
        return ejbProcesoDetalle2;
    }

    public void setEjbProcesoDetalle2(ProcesoDetalleFacadeLocal ejbProcesoDetalle2) {
        this.ejbProcesoDetalle2 = ejbProcesoDetalle2;
    }

    public LazyDataModel<Proceso> getModelo() {
        return modelo;
    }

    public void setModelo(LazyDataModel<Proceso> modelo) {
        this.modelo = modelo;
    }

    public Proceso getRegistro() {
        return registro;
    }

    public void setRegistro(Proceso registro) {
        this.registro = registro;
    }
      
    /*-------End Getter and Setter ----------*/
    
    public FrmProcesoPrime() {
    }
    
    @Deprecated
    public List<Proceso> obtenerTodos(){
        List<Proceso> salida = new ArrayList();
            try {
                if (ejbProceso != null) {
                    salida = ejbProceso.findAll();
                }
            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            }
            return salida;
        }
    
    @PostConstruct
    private void inicio(){
        
        registro = new Proceso();
        
        try {
            
            modelo = new LazyDataModel<Proceso>() {
                @Override
                public Object getRowKey(Proceso object){
                    if(object != null){
                        return object.getIdProceso();
                    }
                    return null;
                }
                
                @Override
                public  Proceso getRowData(String rowKey){
                    if(rowKey != null && !rowKey.isEmpty() && this.getWrappedData() != null){
                        try {
                            Integer buscado = new Integer(rowKey);
                            for (Proceso thi : (List<Proceso>) getWrappedData()) {
                                if(thi.getIdProceso().compareTo(buscado)  == 0){
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
                public List<Proceso> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
                    List<Proceso> salida = new ArrayList(); 
                    
                    if (filters == null || filters.isEmpty()) {
                    try {
                        if(ejbProceso != null){
                            this.setRowCount(ejbProceso.count());
                            salida= ejbProceso.findRange(first,pageSize);
                        }
                    } catch (Exception e) {
                         Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
                    }
                    return salida;
                }
                salida = null;
                    try {
                        if (!filters.isEmpty() && (filters.containsKey("idProceso")||filters.containsKey("nombre")||filters.containsKey("descripcion"))) {
                            
                            if(filters.containsKey("idProceso")){
                                salida = ejbProceso.findBy("idProceso", filters.get("idProceso").toString(), first, pageSize);
                            if (modelo != null) {
                                modelo.setRowCount(salida.size());
                            }
                            
                            } else if(filters.containsKey("nombre")){
                                salida = ejbProceso.findBy("nombre", filters.get("nombre").toString(), first, pageSize);
                            if (modelo != null) {
                                modelo.setRowCount(salida.size());
                            }
                            } else if(filters.containsKey("descripcion")){
                                salida = ejbProceso.findBy("descripcion", filters.get("descripcion").toString(), first, pageSize);
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
        this.registro = new Proceso();
            setBtnadd(true);
            setBtnedit(false);
            setBtnremove(false);
    }
    
    public void crearRegistro(){
    if(this.registro.getDescripcion().isEmpty() != true && this.registro.getNombre().isEmpty() != true  && this.registro.getDescripcion()!= null && this.registro.getNombre()!= null) {

        try {
            if(this.ejbProceso != null && this.registro != null){
                this.ejbProceso.create(registro);
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
            if (this.ejbProceso != null && registro != null) {
                ejbProceso.remove(this.registro);
                nuevo();
                mensaje.msgEliminacion();
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }
    
    public void editarRegistro(){
        if(this.registro.getDescripcion().isEmpty() != true && this.registro.getNombre().isEmpty() != true  && this.registro.getDescripcion()!= null && this.registro.getNombre()!= null) {

	    try{
		if(this.registro != null && this.ejbProceso != null){
		    this.ejbProceso.edit(registro);
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
      this.registro=(Proceso)e.getObject();
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
