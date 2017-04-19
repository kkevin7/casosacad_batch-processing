/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ues.part2.otrosfrmprimes;

import com.uesocc.entities.casosAcad.Solicitudes;
import com.uesocc.facades.casosAcad.CasoFacadeLocal;
import com.uesocc.facades.casosAcad.SolicitudesFacadeLocal;
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
@Named(value = "frmSolicitudesPrime")
@ViewScoped
public class FrmSolicitudesPrime implements Serializable{

    @EJB
    private SolicitudesFacadeLocal ejbSolicitudes;
    
    @EJB
    private CasoFacadeLocal ejbCaso2;
    
    
    private boolean btnadd = false; //encapsulado
    private boolean btnedit = false; //encapsulado
    private boolean btnremove = false; //encapsulado
    private boolean frmcrud = false; //encapsulado
    
    private LazyDataModel<Solicitudes> modelo;
    private Solicitudes registro;
    
    MensajesFormularios mensaje = new MensajesFormularios(); //Mensajes de validacion
    
    /*-------Getter and Setter --------*/

    public SolicitudesFacadeLocal getEjbSolicitudes() {
        return ejbSolicitudes;
    }

    public void setEjbSolicitudes(SolicitudesFacadeLocal ejbSolicitudes) {
        this.ejbSolicitudes = ejbSolicitudes;
    }

    public CasoFacadeLocal getEjbCaso2() {
        return ejbCaso2;
    }

    public void setEjbCaso2(CasoFacadeLocal ejbCaso2) {
        this.ejbCaso2 = ejbCaso2;
    }

    public LazyDataModel<Solicitudes> getModelo() {
        return modelo;
    }

    public void setModelo(LazyDataModel<Solicitudes> modelo) {
        this.modelo = modelo;
    }

    public Solicitudes getRegistro() {
        return registro;
    }

    public void setRegistro(Solicitudes registro) {
        this.registro = registro;
    }
       
    
    /*-------End Getter and Setter ---------*/
    
    public FrmSolicitudesPrime() {
    }
    
    @Deprecated
    public List<Solicitudes> obtenerTodos(){
        List<Solicitudes> salida = new ArrayList();
            try {
                if (ejbSolicitudes != null) {
                    salida = ejbSolicitudes.findAll();
                }
            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            }
            return salida;
        }
    
    @PostConstruct
    private void inicio(){
        
        registro = new Solicitudes();
        
        try {
            
            modelo = new LazyDataModel<Solicitudes>() {
                @Override
                public Object getRowKey(Solicitudes object){
                    if(object != null){
                        return object.getIdSolicitud();
                    }
                    return null;
                }
                
                @Override
                public  Solicitudes getRowData(String rowKey){
                    if(rowKey != null && !rowKey.isEmpty() && this.getWrappedData() != null){
                        try {
                            Integer buscado = new Integer(rowKey);
                            for (Solicitudes thi : (List<Solicitudes>) getWrappedData()) {
                                if(thi.getIdSolicitud().compareTo(buscado)  == 0){
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
                public List<Solicitudes> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
                    List<Solicitudes> salida = new ArrayList(); 
                    
                    if (filters == null || filters.isEmpty()) {
                    try {
                        if(ejbSolicitudes != null){
                            this.setRowCount(ejbSolicitudes.count());
                            salida= ejbSolicitudes.findRange(first,pageSize);
                        }
                    } catch (Exception e) {
                         Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
                    }
                    return salida;
                }
                salida = null;
                    try {
                        if (!filters.isEmpty() && (filters.containsKey("idSolicitud")||filters.containsKey("carnet")||filters.containsKey("nit")||filters.containsKey("fechaRecibida")||filters.containsKey("usuario")||filters.containsKey("descripcionSolicitud"))) {
                            
                            if(filters.containsKey("idSolicitud")){
                                salida = ejbSolicitudes.findBy("idSolicitud", filters.get("idSolicitud").toString(), first, pageSize);
                            if (modelo != null) {
                                modelo.setRowCount(salida.size());
                            }
                            
                            } else if(filters.containsKey("carnet")){
                                salida = ejbSolicitudes.findBy("carnet", filters.get("carnet").toString(), first, pageSize);
                            if (modelo != null) {
                                modelo.setRowCount(salida.size());
                            }
                            } else if(filters.containsKey("nit")){
                                salida = ejbSolicitudes.findBy("nit", filters.get("nit").toString(), first, pageSize);
                            if (modelo != null) {
                                modelo.setRowCount(salida.size());
                            }
                            } else if(filters.containsKey("fechaRecibida")){
                                salida = ejbSolicitudes.findBy("fechaRecibida", filters.get("fechaRecibida").toString(), first, pageSize);
                            if (modelo != null) {
                                modelo.setRowCount(salida.size());
                            }
                            } else if(filters.containsKey("usuario")){
                                salida = ejbSolicitudes.findBy("usuario", filters.get("usuario").toString(), first, pageSize);
                            if (modelo != null) {
                                modelo.setRowCount(salida.size());
                            }} else if(filters.containsKey("descripcionSolicitud")){
                                salida = ejbSolicitudes.findBy("descripcionSolicitud", filters.get("descripcionSolicitud").toString(), first, pageSize);
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
        this.registro = new Solicitudes();
            setBtnadd(true);
            setBtnedit(false);
            setBtnremove(false);
    }
    
    public void crearRegistro(){
        //&& this.registro.getFechaRecibida().toString().isEmpty() != true
    if(this.registro.getCarnet().isEmpty() != true && this.registro.getNit() != 0 && this.registro.getUsuario() != 0 && this.registro.getDescripcionSolicitud().isEmpty() != true && this.registro.getCarnet() != null && this.registro.getFechaRecibida()!= null && this.registro.getDescripcionSolicitud() != null) {

        try {
            if(this.ejbSolicitudes != null && this.registro != null){
                this.ejbSolicitudes.create(registro);
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
            if (this.ejbSolicitudes != null && registro != null) {
                ejbSolicitudes.remove(this.registro);
                nuevo();
                mensaje.msgEliminacion();
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }
    
    public void editarRegistro(){
           //&& this.registro.getFechaRecibida().toString().isEmpty() != true
    if(this.registro.getCarnet().isEmpty() != true && this.registro.getNit() != 0 && this.registro.getUsuario() != 0 && this.registro.getDescripcionSolicitud().isEmpty() != true && this.registro.getCarnet() != null && this.registro.getFechaRecibida()!= null && this.registro.getDescripcionSolicitud() != null) {
	    try{
		if(this.registro != null && this.ejbSolicitudes != null){
		    this.ejbSolicitudes.edit(registro);
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
      this.registro=(Solicitudes)e.getObject();
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
