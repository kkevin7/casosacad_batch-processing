package ues.casosAcad.prime.beans;

import com.uesocc.entities.casosAcad.Paso;
import com.uesocc.facades.casosAcad.PasoFacadeLocal;
import com.uesocc.facades.casosAcad.TipoPasoFacadeLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author kevin
 */
@Named(value = "frmPasoPrime")
@ViewScoped
public class FrmPasoPrime implements Serializable{
    
    @EJB
    private PasoFacadeLocal ejbPaso;
    @EJB
    private TipoPasoFacadeLocal ejbTipoPaso;
    
    private boolean btnadd = false; //encapsulado
    private boolean btnedit = false; //encapsulado
    private boolean btnremove = false; //encapsulado
    private boolean frmcrud = false; //encapsulado
    private boolean frmcrudsts = true; // encapsulado

    
    private LazyDataModel<Paso> modelo;
    private Paso registro;
    //private boolean btnadd = true; //encapsulado
    
    MensajesFormularios mensaje = new MensajesFormularios(); //Mensajes de validacion

    public PasoFacadeLocal getEjbPaso() {
        return ejbPaso;
    }

    public void setEjbPaso(PasoFacadeLocal ejbPaso) {
        this.ejbPaso = ejbPaso;
    }

    public TipoPasoFacadeLocal getEjbTipoPaso() {
        return ejbTipoPaso;
    }

    public void setEjbTipoPaso(TipoPasoFacadeLocal ejbTipoPaso) {
        this.ejbTipoPaso = ejbTipoPaso;
    }

    public LazyDataModel<Paso> getModelo() {
        return modelo;
    }

    public void setModelo(LazyDataModel<Paso> modelo) {
        this.modelo = modelo;
    }

    public Paso getRegistro() {
        return registro;
    }

    public void setRegistro(Paso registro) {
        this.registro = registro;
    }
    
    
    
    public FrmPasoPrime() {
    }
    
    @Deprecated
    public List<Paso> obtenerTodos(){
        List<Paso> salida = new ArrayList();
            try {
                if (ejbPaso != null) {
                    salida = ejbPaso.findAll();
                }
            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            }
            return salida;
        }
    
    
    
    @PostConstruct
    private void inicio(){
        
        registro = new Paso();
        
        try {
            
            modelo = new LazyDataModel<Paso>() {
                @Override
                public Object getRowKey(Paso object){
                    if(object != null){
                        return object.getIdPaso();
                    }
                    return null;
                }
                
                @Override
                public  Paso getRowData(String rowKey){
                    if(rowKey != null && !rowKey.isEmpty() && this.getWrappedData() != null){
                        try {
                            Integer buscado = new Integer(rowKey);
                            for (Paso thi : (List<Paso>) getWrappedData()) {
                                if(thi.getIdPaso().compareTo(buscado)  == 0){
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
                public List<Paso> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
                    List<Paso> salida = new ArrayList(); 
                    
                    if (filters == null || filters.isEmpty()) {
                    try {
                        if(ejbPaso != null){
                            this.setRowCount(ejbPaso.count());
                            salida= ejbPaso.findRange(first,pageSize);
                        }
                    } catch (Exception e) {
                         Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
                    }
                    return salida;
                }
                salida = null;
                    try {
                        if (!filters.isEmpty() && (filters.containsKey("idPaso")||filters.containsKey("tiempo")||filters.containsKey("descripcion")||filters.containsKey("idTipoPaso"))) {
                            
                            if(filters.containsKey("idPaso")){
                                salida = ejbPaso.findBy("idPaso", filters.get("idPaso").toString(), first, pageSize);
                            if (modelo != null) {
                                modelo.setRowCount(salida.size());
                            }
                            
                            } else if(filters.containsKey("descripcion")){
                                salida = ejbPaso.findBy("descripcion", filters.get("descripcion").toString(), first, pageSize);
                            if (modelo != null) {
                                modelo.setRowCount(salida.size());
                            }
                            
                            //Tiempo
                            } else if(filters.containsKey("tiempo")){
                                salida = ejbPaso.findBy("tiempo", filters.get("tiempo").toString(), first, pageSize);
                            if (modelo != null) {
                                modelo.setRowCount(salida.size());
                            }
                            //fin tiempo
                            
                            }
                            else if(filters.containsKey("idTipoPaso")){
                                salida = ejbPaso.filtroForaneo("idTipoPaso", "nombre", filters.get("idTipoPaso").toString(), first, pageSize);
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
        this.registro = new Paso();
            setBtnadd(true);
            setBtnedit(false);
            setBtnremove(false);
            setFrmcrudsts(false);
    }
    
    public void crearRegistro(){
    if(this.registro.getDescripcion().isEmpty() != true && this.registro.getTiempo().isEmpty() != true  && this.registro.getDescripcion()!= null && this.registro.getTiempo()!= null) {

        try {
            if(this.ejbPaso != null && this.registro != null){
                this.ejbPaso.create(registro);
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
            if (this.ejbPaso != null && registro != null) {
                ejbPaso.remove(this.registro);
                nuevo();
                mensaje.msgEliminacion();
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }
    
    public void editarRegistro(){
        if(this.registro.getDescripcion().isEmpty() != true && this.registro.getTiempo().isEmpty() != true  && this.registro.getDescripcion()!= null && this.registro.getTiempo()!= null) {

	    try{
		if(this.registro != null && this.ejbPaso != null){
		    this.ejbPaso.edit(registro);
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
      this.registro=(Paso)e.getObject();
      setBtnedit(true);
      setBtnremove(true);
      setBtnadd(false);
        setFrmcrudsts(false);
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
    
    
}
