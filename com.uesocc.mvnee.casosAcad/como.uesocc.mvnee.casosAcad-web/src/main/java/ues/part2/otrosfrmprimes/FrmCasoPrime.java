/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ues.part2.otrosfrmprimes;

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
/*--------- Borrar esta linea al unirlo-------------------*/
import ues.casosAcad.prime.beans.MensajesFormularios;
/*-------------------------------------------------------*/

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
    
    private LazyDataModel<Caso> modelo;
    private Caso registro;
    
    MensajesFormularios mensaje = new MensajesFormularios(); //Mensajes de validacion
    
    /*-------Getter and Setter --------*/
    
    
    
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
                        if (!filters.isEmpty() && (filters.containsKey("idCaso")||filters.containsKey("nombre")||filters.containsKey("descripcion"))) {
                            
                            if(filters.containsKey("idCaso")){
                                salida = ejbCaso.findBy("idCaso", filters.get("idCaso").toString(), first, pageSize);
                            if (modelo != null) {
                                modelo.setRowCount(salida.size());
                            }
                            
//                            } else if(filters.containsKey("nombre")){
//                                salida = ejbCaso.findBy("nombre", filters.get("nombre").toString(), first, pageSize);
//                            if (modelo != null) {
//                                modelo.setRowCount(salida.size());
//                            }
//                            } else if(filters.containsKey("descripcion")){
//                                salida = ejbCaso.findBy("descripcion", filters.get("descripcion").toString(), first, pageSize);
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
    
    public void nuevo() {
        this.registro = new Caso();
            setBtnadd(true);
            setBtnedit(false);
            setBtnremove(false);
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
