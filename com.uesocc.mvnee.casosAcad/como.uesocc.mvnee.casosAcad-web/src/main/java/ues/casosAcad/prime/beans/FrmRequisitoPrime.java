package ues.casosAcad.prime.beans;

import com.uesocc.entities.casosAcad.Requisito;
import com.uesocc.facades.casosAcad.RequisitoFacadeLocal;
import com.uesocc.facades.casosAcad.TipoRequisitoFacadeLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import org.omnifaces.cdi.ViewScoped;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

@Named(value = "frmRequisitoPrime")
@ViewScoped
public class FrmRequisitoPrime implements Serializable{

    @EJB
    private RequisitoFacadeLocal ejbRequisito;
    @EJB
    private TipoRequisitoFacadeLocal trfl;
    
    private boolean btnadd = false; //encapsulado
    private boolean btnedit = false; //encapsulado
    private boolean btnremove = false; //encapsulado
    private boolean frmcrud = false; //encapsulado
    private boolean frmcrudsts = true; // encapsulado

    
    private LazyDataModel<Requisito> modelo;
    private Requisito registro;
    //private boolean btnadd = true; //encapsulado
    
    MensajesFormularios mensaje = new MensajesFormularios(); //Mensaje de Validacion
        
    /*---------Getter and Setter----------*/

    public RequisitoFacadeLocal getEjbRequisito() {
        return ejbRequisito;
    }

    public void setEjbRequisito(RequisitoFacadeLocal ejbRequisito) {
        this.ejbRequisito = ejbRequisito;
    }

    public TipoRequisitoFacadeLocal getTrfl() {
        return trfl;
    }

    public void setTrfl(TipoRequisitoFacadeLocal trfl) {
        this.trfl = trfl;
    }

    public LazyDataModel<Requisito> getModelo() {
        return modelo;
    }

    public void setModelo(LazyDataModel<Requisito> modelo) {
        this.modelo = modelo;
    }

    public Requisito getRegistro() {
        return registro;
    }

    public void setRegistro(Requisito registro) {
        this.registro = registro;
    }

    
    /*--------End Setter and Getter -------------------*/
    
    
       @Deprecated
    public List<Requisito> obtenerTodos(){
        List<Requisito> salida = new ArrayList();
            try {
                if (ejbRequisito != null) {
                    salida = ejbRequisito.findAll();
                }
            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            }
            return salida;
        }

    public FrmRequisitoPrime() {
    }
    
    @PostConstruct
    private void inicio(){
        
        registro = new Requisito();
        
        try {
            
            modelo = new LazyDataModel<Requisito>() {
                @Override
                public Object getRowKey(Requisito object){
                    if(object != null){
                        return object.getIdRequisito();
                    }
                    return null;
                }
                
                @Override
                public  Requisito getRowData(String rowKey){
                    if(rowKey != null && !rowKey.isEmpty() && this.getWrappedData() != null){
                        try {
                            Integer buscado = new Integer(rowKey);
                            for (Requisito thi : (List<Requisito>) getWrappedData()) {
                                if(thi.getIdRequisito().compareTo(buscado)  == 0){
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
                public List<Requisito> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
                    List<Requisito> salida = new ArrayList();                  
                    
                    if (filters == null || filters.isEmpty()) {
                    try {
                        if(ejbRequisito != null){
                            this.setRowCount(ejbRequisito.count());
                            salida= ejbRequisito.findRange(first,pageSize);
                        }
                    } catch (Exception e) {
                    }
                    return salida;
                }
                salida = null;
                    try {
                        if (!filters.isEmpty() && (filters.containsKey("idRequisito")||filters.containsKey("nombre")||filters.containsKey("descripcion")||filters.containsKey("prioridad")||filters.containsKey("idTipoRequisito"))) {
                            
                            if(filters.containsKey("idRequisito")){
                                salida = ejbRequisito.findBy("idRequisito", filters.get("idRequisito").toString(), first, pageSize);
                            if (modelo != null) {
                                modelo.setRowCount(salida.size());
                            }
                            } else if(filters.containsKey("nombre")){
                                salida = ejbRequisito.findBy("nombre", filters.get("nombre").toString(), first, pageSize);
                            if (modelo != null) {
                                modelo.setRowCount(salida.size());
                            }
                            } else if(filters.containsKey("descripcion")){
                                salida = ejbRequisito.findBy("descripcion", filters.get("descripcion").toString(), first, pageSize);
                            if (modelo != null) {
                                modelo.setRowCount(salida.size());
                            }
                            } else if(filters.containsKey("prioridad")){
                                salida = ejbRequisito.findBy("prioridad", filters.get("prioridad").toString(), first, pageSize);
                            if (modelo != null) {
                                modelo.setRowCount(salida.size());
                            }
                            }
                            else if(filters.containsKey("idTipoRequisito")){
                                salida = ejbRequisito.filtroForaneo("idTipoRequisito","nombre", filters.get("idTipoRequisito").toString(), first, pageSize);
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
    
    public void cambiarSeleccion(SelectEvent e){
      this.registro=(Requisito)e.getObject();
      setBtnedit(true);
        setBtnremove(true);
        setBtnadd(false);
        setFrmcrudsts(false);
    }  
    
    public void nuevo() {
        this.registro = new Requisito();
        setBtnadd(true);
        setBtnedit(false);
        setBtnremove(false);
        setFrmcrudsts(false);
    }
    
    public void crearRegistro(){
        if(this.registro.getNombre().isEmpty() != true && this.registro.getDescripcion().isEmpty() != true && this.registro.getPrioridad().isEmpty() != true && this.registro.getNombre() != null && this.registro.getDescripcion() != null  && this.registro.getPrioridad() !=null) {

        try {
            if(this.ejbRequisito != null && this.registro != null){
                this.ejbRequisito.create(registro);
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
            if (this.ejbRequisito != null && registro != null) {
                ejbRequisito.remove(this.registro);
                nuevo();
                mensaje.msgEliminacion();
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }
    
      public void editarRegistro(){
        if(this.registro.getNombre().isEmpty() != true && this.registro.getDescripcion().isEmpty() != true && this.registro.getPrioridad().isEmpty() != true && this.registro.getNombre() != null && this.registro.getDescripcion() != null  && this.registro.getPrioridad() !=null) {
  
	    try{
		if(this.registro != null && this.ejbRequisito != null){
		    this.ejbRequisito.edit(registro);
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
