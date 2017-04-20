package ues.casosAcad.prime.beans;

import com.uesocc.entities.casosAcad.TipoPaso;
import com.uesocc.facades.casosAcad.TipoPasoFacadeLocal;
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

@Named(value = "frmTipoPasoPrime")
@ViewScoped
public class FrmTipoPasoPrime implements Serializable {

    @EJB
    private TipoPasoFacadeLocal ejbTipoPaso;

    private LazyDataModel<TipoPaso> modelo; //encapsulado
    private TipoPaso registro; //encapsulado
    private boolean btnadd = false; //encapsulado
    private boolean btnedit = false; //encapsulado
    private boolean btnremove = false; //encapsulado
    private boolean frmcrud = false; //encapsulado
    private boolean frmcrudsts = true; // encapsulado

    MensajesFormularios mensaje = new MensajesFormularios(); //Mensaje de Validacion

    /*-------Setter and Getter ----------*/
    public TipoPasoFacadeLocal getEjbTipoPaso() {
        return ejbTipoPaso;
    }

    public void setEjbTipoPaso(TipoPasoFacadeLocal ejbTipoPaso) {
        this.ejbTipoPaso = ejbTipoPaso;
    }

    public LazyDataModel<TipoPaso> getModelo() {
        return modelo;
    }

    public void setModelo(LazyDataModel<TipoPaso> modelo) {
        this.modelo = modelo;
    }

    public TipoPaso getRegistro() {
        return registro;
    }

    public void setRegistro(TipoPaso registro) {
        this.registro = registro;
    }

    /*-------- End Setter and Getter -----------*/
    public FrmTipoPasoPrime() {
    }

    @Deprecated
    public List<TipoPaso> obtenerTodos() {
        List<TipoPaso> salida = new ArrayList();
        try {
            if (ejbTipoPaso != null) {
                salida = ejbTipoPaso.findAll();
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return salida;
    }

    @PostConstruct
    private void inicio() {

        registro = new TipoPaso();

        try {

            modelo = new LazyDataModel<TipoPaso>() {
                @Override
                public Object getRowKey(TipoPaso object) {
                    if (object != null) {
                        return object.getIdTipoPaso();
                    }
                    return null;
                }

                @Override
                public TipoPaso getRowData(String rowKey) {
                    if (rowKey != null && !rowKey.isEmpty() && this.getWrappedData() != null) {
                        try {
                            Integer buscado = new Integer(rowKey);
                            for (TipoPaso thi : (List<TipoPaso>) getWrappedData()) {
                                if (thi.getIdTipoPaso().compareTo(buscado) == 0) {
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
                public List<TipoPaso> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
                    List<TipoPaso> salida = new ArrayList();

                    if (filters == null || filters.isEmpty()) {
                        try {
                            if (ejbTipoPaso != null) {
                                this.setRowCount(ejbTipoPaso.count());
                                salida = ejbTipoPaso.findRange(first, pageSize);
                            }
                        } catch (Exception e) {
                        }
                        return salida;
                    }
                    salida = null;
                    try {
                        if (!filters.isEmpty() && (filters.containsKey("idTipoPaso") || filters.containsKey("nombre") || filters.containsKey("descripcion") || filters.containsKey("activo"))) {

                            if (filters.containsKey("idTipoPaso")) {
                                salida = ejbTipoPaso.findBy("idTipoPaso", filters.get("idTipoPaso").toString(), first, pageSize);
                                if (modelo != null) {
                                    modelo.setRowCount(salida.size());
                                }
                            } else if (filters.containsKey("nombre")) {
                                salida = ejbTipoPaso.findBy("nombre", filters.get("nombre").toString(), first, pageSize);
                                if (modelo != null) {
                                    modelo.setRowCount(salida.size());
                                }
                            } else if (filters.containsKey("descripcion")) {
                                salida = ejbTipoPaso.findBy("descripcion", filters.get("descripcion").toString(), first, pageSize);
                                if (modelo != null) {
                                    modelo.setRowCount(salida.size());
                                }
                            } else if (filters.containsKey("activo")) {
                                salida = ejbTipoPaso.findBy("activo", filters.get("activo").toString(), first, pageSize);
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
        this.registro = new TipoPaso();
        setBtnadd(true);
        setBtnedit(false);
        setBtnremove(false);
        setFrmcrudsts(false);
    }

    public void crearRegistro() {

        if (this.registro.getNombre().isEmpty() != true && this.registro.getDescripcion().isEmpty() != true && this.registro.getNombre() != null && this.registro.getDescripcion() != null) {

            try {
                if (this.ejbTipoPaso != null && this.registro != null) {
                    this.ejbTipoPaso.create(registro);
                    nuevo();
                    mensaje.msgCreadoExito();
                }
            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            }
        } else {
            mensaje.msgFaltanCampos();
        }
    }

    public void eliminar() {
        try {
            if (this.ejbTipoPaso != null && registro != null) {
                ejbTipoPaso.remove(this.registro);
                nuevo();
                mensaje.msgEliminacion();
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void editarRegistro() {
        if (this.registro.getNombre().isEmpty() != true && this.registro.getDescripcion().isEmpty() != true && this.registro.getNombre() != null && this.registro.getDescripcion() != null) {
            try {
                if (this.registro != null && this.ejbTipoPaso != null) {
                    this.ejbTipoPaso.edit(registro);
                    nuevo();
                    mensaje.msgModificacion();
                }
            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            }
        } else {
            mensaje.msgFaltanCampos();
        }
    }

    public void cambiarSeleccion(SelectEvent e) {
        this.registro = (TipoPaso) e.getObject();
        setBtnedit(true);
        setBtnremove(true);
        setBtnadd(false);
       setFrmcrudsts(false);
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
     * @return the btnedit
     */
    public boolean isBtnedit() {
        return btnedit;
    }

    /**
     * @return the btnremove
     */
    public boolean isBtnremove() {
        return btnremove;
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
     * @param btnedit the btnedit to set
     */
    public void setBtnedit(boolean btnedit) {
        this.btnedit = btnedit;
    }

    /**
     * @param btnremove the btnremove to set
     */
    public void setBtnremove(boolean btnremove) {
        this.btnremove = btnremove;
    }

}

//     @Deprecated
//    public void guardarRegitro() {
//        try {
//            if (this.registro != null && this.ejbTipoPaso != null) {
//                if (this.ejbTipoPaso.creator(registro)) {
//                    this.btnadd = !this.btnadd;
//                }
//            }
//        } catch (Exception e) {
//            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
//        }
//    }
