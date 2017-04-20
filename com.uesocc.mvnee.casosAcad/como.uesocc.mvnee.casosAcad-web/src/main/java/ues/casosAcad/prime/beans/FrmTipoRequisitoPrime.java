package ues.casosAcad.prime.beans;

import com.uesocc.entities.casosAcad.TipoPaso;
import com.uesocc.entities.casosAcad.TipoRequisito;
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
import javax.faces.view.ViewScoped;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;


@Named(value = "frmTipoRequisitoPrime")
@ViewScoped
public class FrmTipoRequisitoPrime implements Serializable{

    @EJB
    private TipoRequisitoFacadeLocal trfl;
    private LazyDataModel<TipoRequisito> modelo; //encapsulado
    private TipoRequisito registro; //encapsulado
    private boolean btnadd = false; //encapsulado
    private boolean btnedit = false; //encapsulado
    private boolean btnremove = false; //encapsulado
    private boolean frmcrud = false; //encapsulado
    private boolean frmcrudsts = true; // encapsulado

    
    MensajesFormularios mensaje = new MensajesFormularios(); //Mensaje de Validacion
    
    /*-------Setter and Getter --------------*/

    public TipoRequisitoFacadeLocal getTrfl() {
        return trfl;
    }

    public void setTrfl(TipoRequisitoFacadeLocal trfl) {
        this.trfl = trfl;
    }

    public LazyDataModel<TipoRequisito> getModelo() {
        return modelo;
    }

    public void setModelo(LazyDataModel<TipoRequisito> modelo) {
        this.modelo = modelo;
    }

    public TipoRequisito getRegistro() {
        return registro;
    }

    public void setRegistro(TipoRequisito registro) {
        this.registro = registro;
    }

    public boolean isBtnadd() {
        return btnadd;
    }

    public void setBtnadd(boolean btnadd) {
        this.btnadd = btnadd;
    }

    /*----------- End Setter and Getter --------------*/
    
    
    
    
    public FrmTipoRequisitoPrime() {
    }
    
    
    @Deprecated
    public List<TipoRequisito> obtenerTodos(){
        List<TipoRequisito> salida = new ArrayList();
            try {
                if (trfl != null) {
                    salida = trfl.findAll();
                }
            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            }
            return salida;
        }
    
    @PostConstruct
    private void inicio(){
        
        registro = new TipoRequisito();
        
        try {
            
            modelo = new LazyDataModel<TipoRequisito>() {
                @Override
                public Object getRowKey(TipoRequisito object){
                    if(object != null){
                        return object.getIdTipoRequisito();
                    }
                    return null;
                }
                
                @Override
                public  TipoRequisito getRowData(String rowKey){
                    if(rowKey != null && !rowKey.isEmpty() && this.getWrappedData() != null){
                        try {
                            Integer buscado = new Integer(rowKey);
                            for (TipoRequisito thi : (List<TipoRequisito>) getWrappedData()) {
                                if(thi.getIdTipoRequisito().compareTo(buscado)  == 0){
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
                public List<TipoRequisito> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
                    List<TipoRequisito> salida = new ArrayList();
                    
                    if (filters == null || filters.isEmpty()) {
                    try {
                        if(trfl != null){
                            this.setRowCount(trfl.count());
                            salida= trfl.findRange(first,pageSize);
                        }
                    } catch (Exception e) {
                    }
                    return salida;
                }
                salida = null;
                    try {
                        if (!filters.isEmpty() && (filters.containsKey("idTipoRequisito")||filters.containsKey("nombre")||filters.containsKey("activo")||filters.containsKey("observaciones"))) {
                            
                            if(filters.containsKey("idTipoRequisito")){
                                salida = trfl.findBy("idTipoRequisito", filters.get("idTipoRequisito").toString(), first, pageSize);
                            if (modelo != null) {
                                modelo.setRowCount(salida.size());
                            }
                            } else if(filters.containsKey("nombre")){
                                salida = trfl.findBy("nombre", filters.get("nombre").toString(), first, pageSize);
                            if (modelo != null) {
                                modelo.setRowCount(salida.size());
                            }
                            } else if(filters.containsKey("activo")){
                               
                                salida = trfl.findBy("activo", filters.get("activo").toString(), first, pageSize);
                               
                                 if (modelo != null) {
                                modelo.setRowCount(salida.size());
                            }
                           
                            }else if(filters.containsKey("observaciones")){
                                salida = trfl.findBy("observaciones", filters.get("observaciones").toString(), first, pageSize);
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

     @Deprecated
    public void guardarRegitro() {
        try {
            if (this.registro != null && this.trfl != null) {
                if (this.trfl.creator(registro)) {
                    this.btnadd = !this.btnadd;
                }
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }
    
    public void nuevo() {
        this.registro = new TipoRequisito();
         setBtnadd(true);
         setBtnedit(false);
         setBtnremove(false);
         setFrmcrudsts(false);
    }
    
    public void crearRegistro(){
        if(this.registro.getNombre().isEmpty() != true && this.registro.getObservaciones().isEmpty() != true && this.registro.getNombre() != null && this.registro.getObservaciones() != null) {

        
        try {
            if(this.trfl != null && this.registro != null){
                this.trfl.create(registro);
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
            if (this.trfl != null && registro != null) {
                trfl.remove(this.registro);
                nuevo();
                mensaje.msgEliminacion();
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }
    
      public void editarRegistro(){
            if(this.registro.getNombre().isEmpty() != true && this.registro.getObservaciones().isEmpty() != true && this.registro.getNombre() != null && this.registro.getObservaciones() != null) {

	    try{
		if(this.registro != null && this.trfl != null){
		    this.trfl.edit(registro);
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
//    
    public void cambiarSeleccion(SelectEvent e){
      this.registro=(TipoRequisito)e.getObject();
      setBtnedit(true);
      setBtnremove(true);
      setBtnadd(false);
      setFrmcrudsts(false);
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
