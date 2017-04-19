/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ues.part2.otrosfrmprimes;

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
/*---------------- se borrarla esta linea -----------*/
import ues.casosAcad.prime.beans.MensajesFormularios;
/*---------------------------------------------------------*/


/**
 *
 * @author kevin
 */
@Named(value = "frmProcesoDetalle")
@ViewScoped
public class FrmProcesoDetalle implements Serializable{

    @EJB
    private ProcesoDetalleFacadeLocal ejbProcesoDetalle;
    
    private boolean btnadd = false; //encapsulado
    private boolean btnedit = false; //encapsulado
    private boolean btnremove = false; //encapsulado
    private boolean frmcrud = false; //encapsulado
    
    private LazyDataModel<ProcesoDetalle> modelo;
    private ProcesoDetalle registro;
    
    MensajesFormularios mensaje = new MensajesFormularios(); //Mensajes de validacion
    
    /*---------- Getter and Settter ----------------------*/
    
    
    
    /*---------- End Getter and Setter --------------------*/
    
    public FrmProcesoDetalle() {
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
                        return object.getIdPaso();
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
                        if (!filters.isEmpty() && (filters.containsKey("idProcesoDetalle")||filters.containsKey("tiempo")||filters.containsKey("descripcion")||filters.containsKey("idTipoPaso"))) {
                            
                            if(filters.containsKey("idProcesoDetalle")){
                                salida = ejbProcesoDetalle.findBy("idProcesoDetalle", filters.get("idProcesoDetalle").toString(), first, pageSize);
                            if (modelo != null) {
                                modelo.setRowCount(salida.size());
                            }
                            
//                            } else if(filters.containsKey("descripcion")){
//                                salida = ejbProcesoDetalle.findBy("descripcion", filters.get("descripcion").toString(), first, pageSize);
//                            if (modelo != null) {
//                                modelo.setRowCount(salida.size());
//                            }
//                            
//                            //Tiempo
//                            } else if(filters.containsKey("tiempo")){
//                                salida = ejbProcesoDetalle.findBy("tiempo", filters.get("tiempo").toString(), first, pageSize);
//                            if (modelo != null) {
//                                modelo.setRowCount(salida.size());
//                            }
//                            //fin tiempo
//                            
//                            }
//                            else if(filters.containsKey("idTipoPaso")){
//                                salida = ejbProcesoDetalle.filtroForaneo("idTipoPaso", "nombre", filters.get("idTipoPaso").toString(), first, pageSize);
//                            if (modelo != null) {
//                                modelo.setRowCount(salida.size());
//                            }
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
    
    
//     public void nuevo() {
//        this.registro = new ProcesoDetalle();
//            setBtnadd(true);
//            setBtnedit(false);
//            setBtnremove(false);
//    }
//    
//    public void crearRegistro(){
//    if(this.registro.getDescripcion.isEmpty() != true && this.registro.getTiempo().isEmpty() != true  && this.registro.getDescripcion()!= null && this.registro.getTiempo()!= null) {
//
//        try {
//            if(this.ejbPaso != null && this.registro != null){
//                this.ejbPaso.create(registro);
//                nuevo();
//                mensaje.msgCreadoExito();
//            }
//        } catch (Exception e) {
//            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
//        }
//                }
//    else{
//        mensaje.msgFaltanCampos();
//    }
//    }
//
//    public void eliminar() {
//        try {
//            if (this.ejbPaso != null && registro != null) {
//                ejbPaso.remove(this.registro);
//                nuevo();
//                mensaje.msgEliminacion();
//            }
//        } catch (Exception e) {
//            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
//        }
//    }
//    
//    public void editarRegistro(){
//        if(this.registro.getDescripcion().isEmpty() != true && this.registro.getTiempo().isEmpty() != true  && this.registro.getDescripcion()!= null && this.registro.getTiempo()!= null) {
//
//	    try{
//		if(this.registro != null && this.ejbPaso != null){
//		    this.ejbPaso.edit(registro);
//                     nuevo();
//                     mensaje.msgModificacion();
//		}
//	    }catch(Exception e){
//		Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
//	    }
//        }
//        else{
//            mensaje.msgFaltanCampos();
//        }
//	}
//    
//    public void cambiarSeleccion(SelectEvent e){
//      this.registro=(Paso)e.getObject();
//      setBtnedit(true);
//      setBtnremove(true);
//      setBtnadd(false);
//    }
//
//    /**
//     * @return the btnadd
//     */
//    public boolean isBtnadd() {
//        return btnadd;
//    }
//
//    /**
//     * @param btnadd the btnadd to set
//     */
//    public void setBtnadd(boolean btnadd) {
//        this.btnadd = btnadd;
//    }
//
//    /**
//     * @return the btnedit
//     */
//    public boolean isBtnedit() {
//        return btnedit;
//    }
//
//    /**
//     * @param btnedit the btnedit to set
//     */
//    public void setBtnedit(boolean btnedit) {
//        this.btnedit = btnedit;
//    }
//
//    /**
//     * @return the btnremove
//     */
//    public boolean isBtnremove() {
//        return btnremove;
//    }
//
//    /**
//     * @param btnremove the btnremove to set
//     */
//    public void setBtnremove(boolean btnremove) {
//        this.btnremove = btnremove;
//    }
//
//    /**
//     * @return the frmcrud
//     */
//    public boolean isFrmcrud() {
//        return frmcrud;
//    }
//
//    /**
//     * @param frmcrud the frmcrud to set
//     */
//    public void setFrmcrud(boolean frmcrud) {
//        this.frmcrud = frmcrud;
//    }
    
}
