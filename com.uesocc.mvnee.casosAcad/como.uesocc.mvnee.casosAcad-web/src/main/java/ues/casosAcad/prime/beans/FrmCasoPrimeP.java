package ues.casosAcad.prime.beans;


import com.uesocc.entities.casosAcad.Caso;
import com.uesocc.entities.casosAcad.Proceso;
import com.uesocc.facades.casosAcad.CasoFacadeLocal;
import com.uesocc.facades.casosAcad.ProcesoFacadeLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;



@Named(value="mbAgregarCaso")
@ViewScoped
public class FrmCasoPrimeP implements Serializable{
    
   
    @EJB
    private CasoFacadeLocal fl;
    private boolean showDetail = true;
  //  private Messages msg = new Messages();
    private Caso selectedAgregarCaso = new Caso();
    private Proceso selectedProceso = new Proceso();
    private LazyDataModel<Proceso> ldm;
    private ProcesoFacadeLocal fl2;
    private Caso c = new Caso();

    public Caso getC() {
        return c;
    }

    public void setC(Caso c) {
        this.c = c;
    }

    public ProcesoFacadeLocal getFl2() {
        return fl2;
    }

    public void setFl2(ProcesoFacadeLocal fl2) {
        this.fl2 = fl2;
    }

    public LazyDataModel<Proceso> getLdm() {
        return ldm;
    }

    public void setLdm(LazyDataModel<Proceso> ldm) {
        this.ldm = ldm;
    }

  

    public Proceso getSelectedProceso() {
        return selectedProceso;
    }

    public void setSelectedProceso(Proceso selectedProceso) {
        this.selectedProceso = selectedProceso;
    }

    
    public CasoFacadeLocal getFl() {
        return fl;
    }

    public void setFl(CasoFacadeLocal fl) {
        this.fl = fl;
    }
   
    public Caso getSelectedAgregarCaso() {
        return selectedAgregarCaso;
    }

    
    @PostConstruct
    private void init() {
        selectedProceso = new Proceso();
        try {
            this.setLdm(new LazyDataModel<Proceso>() {
                @Override
                public Object getRowKey(Proceso object) {
                    if (object != null) {
                        return object.getIdProceso();
                    }
                    return null;
                }

                @Override
                public Proceso getRowData(String rowKey) {
                    if (rowKey != null && !rowKey.isEmpty() && this.getWrappedData() != null) {
                        try {
                            Integer buscado = new Integer(rowKey);
                            for (Proceso thi : (List<Proceso>) getWrappedData()) {
                                if (thi.getIdProceso().compareTo(buscado) == 0) {
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
                            if (fl != null) {
                                this.setRowCount(getFl().count());
                                salida = getFl2().findRange(first, pageSize);
                            }

                        } catch (Exception e) {
                            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
                        }
                        return salida;
                    }
                    salida = null;
                    try {
                        if (!filters.isEmpty() && (filters.containsKey("idProceso") || filters.containsKey("nombre") || filters.containsKey("descripcion"))) {

                            if (filters.containsKey("idProceso")) {
                                salida = fl2.findByAll("idProceso", filters.get("idProceso").toString());
                                if (ldm != null) {
                                    ldm.setRowCount(salida.size());
                                }
                            } else if (filters.containsKey("nombre")) {
                                salida = fl2.findByAll("nombre", filters.get("nombre").toString());
                                if (ldm != null) {
                                    ldm.setRowCount(salida.size());
                                }
                            } else if (filters.containsKey("descripcion")) {
                                salida = fl2.findByAll("descripcion", filters.get("descripcion").toString());
                                if (ldm != null) {
                                    ldm.setRowCount(salida.size());
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
            });

        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }
    
    public void create() {
        if (this.selectedAgregarCaso.getIdProceso().toString().isEmpty() != true){ 
        try {
                this.getFl().create(this.selectedAgregarCaso);
                this.selectedAgregarCaso = new Caso();
                nuevo();
           //     msg.MsgCreado();
            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            
        } 
        }else {
        //    msg.MsgIncompleto();
        }

    }
    public void nuevo() {
        this.selectedAgregarCaso = new Caso();
        
        
    }
   
  

    public boolean isShowDetail() {
        return showDetail;
    }

    public void setShowDetail(boolean showDetail) {
        this.showDetail = showDetail;
    }
    public void changeSelected(SelectEvent se) {
        if (se.getObject() != null) {
            try {
                this.setSelectedProceso((Proceso) se.getObject());
                this.selectedAgregarCaso.setIdProceso(selectedProceso);
                this.setShowDetail(true);
               
            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }
}
