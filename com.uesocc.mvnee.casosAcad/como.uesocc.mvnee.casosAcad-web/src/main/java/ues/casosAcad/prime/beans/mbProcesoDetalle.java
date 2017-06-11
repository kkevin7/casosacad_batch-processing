package ues.casosAcad.prime.beans;

import com.uesocc.entities.casosAcad.Paso;
import com.uesocc.entities.casosAcad.Proceso;
import com.uesocc.entities.casosAcad.ProcesoDetalle;
import com.uesocc.facades.casosAcad.PasoFacadeLocal;
import com.uesocc.facades.casosAcad.ProcesoDetalleFacadeLocal;
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
import org.omnifaces.cdi.ViewScoped;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;




@Named(value = "mbProcesoDetalle")
@ViewScoped
public class mbProcesoDetalle implements Serializable{
    @EJB
    private ProcesoDetalleFacadeLocal fl;
    @EJB
    private PasoFacadeLocal paso;
    private LazyDataModel<ProcesoDetalle> ldm;
    private ProcesoDetalle c = new ProcesoDetalle();
    private ProcesoDetalle selectedProcesoDetalle;
    private Proceso selectedProceso;
    private Paso selectedPaso;
    private String mensaje;
    private List<Paso> filtrados = new ArrayList<>();
    private List<Paso> paraAgregar = new ArrayList<>();
    private List<Paso> resumen = new ArrayList<>();

    public Paso getSelectedPaso() {
        return selectedPaso;
    }

    public void setSelectedPaso(Paso selectedPaso) {
        this.selectedPaso = selectedPaso;
    }
    

    public List<Paso> getResumen() {
        return resumen;
    }

    public void setResumen(List<Paso> resumen) {
        this.resumen = resumen;
    }
  

    
     @PostConstruct
    private void init() {
        this.setSelectedProcesoDetalle(new ProcesoDetalle());
        this.setSelectedPaso(new Paso());
        this.setSelectedProceso(new Proceso());
       
        try {

            this.setLdm(new LazyDataModel<ProcesoDetalle>() {
                @Override
                public Object getRowKey(ProcesoDetalle object) {
                    if (object != null) {
                        return object.getIdProcesoDetalle();
                    }
                    return null;
                }

                @Override
                public ProcesoDetalle getRowData(String rowKey) {
                    if (rowKey != null && !rowKey.isEmpty() && this.getWrappedData() != null) {
                        try {
                            Integer buscado = new Integer(rowKey);
                            for (ProcesoDetalle thi : (List<ProcesoDetalle>) getWrappedData()) {
                                if (thi.getIdProcesoDetalle().compareTo(buscado) == 0) {
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
                    try {
    if (!filters.isEmpty() && (filters.containsKey("idPaso.idPaso")||filters.containsKey("idProceso.idProceso")||filters.containsKey("indice")||filters.containsKey("idProcesoDetalle"))) {
                            
                            if(filters.containsKey("idProcesoDetalle")){
                                salida = fl.findByAll("idProcesoDetalle", filters.get("idProcesoDetalle").toString());
                            if (ldm != null) {
                                ldm.setRowCount(salida.size());
                            }
                            } else if(filters.containsKey("idPaso.idPaso")){
                                salida = fl.findByJoined("idPaso.idPaso",(Paso) filters.get("idPaso.idPaso"));
                            if (ldm != null) {
                                ldm.setRowCount(salida.size());
                            }
                            } else if(filters.containsKey("idProceso.idProceso")){
                                salida = fl.findByJoined("idProceso.idProceso", (Proceso) filters.get("idProceso.idProceso"));
                            if (ldm != null) {
                                ldm.setRowCount(salida.size());
                            }
                            
                            }
                            else if(filters.containsKey("indice")){
                                salida = fl.findByJoined("indice", filters.get("indice").toString());
                            if (ldm != null) {
                                ldm.setRowCount(salida.size());
                            }
                            } 
                            
                             }              
    }catch (Exception e) {
                        Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
                    }
                    return salida;
                }
            });
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        
    }

    
   
   public boolean tieneLista(Proceso p) {
        return fl.findByJoined("idProceso", p).size() > 0;
    }

    public Proceso getSelectedProceso() {
        return selectedProceso;
    }

    public void setSelectedProceso(Proceso selectedProceso) {
        this.selectedProceso = selectedProceso;
    }
    
     public ProcesoDetalle getSelectedProcesoDetalle() {
        return selectedProcesoDetalle;
    }

    public void setSelectedProcesoDetalle(ProcesoDetalle selectedProcesoDetalle) {
        this.selectedProcesoDetalle = selectedProcesoDetalle;
    }
    public LazyDataModel<ProcesoDetalle> getLdm() {
        return ldm;
    }

    public void setLdm(LazyDataModel<ProcesoDetalle> ldm) {
        this.ldm = ldm;
    }
    
    public ProcesoDetalleFacadeLocal getFl() {
        return fl;
    }

    public void setFl(ProcesoDetalleFacadeLocal fl) {
        this.fl = fl;
    }
    public List<ProcesoDetalle> obtenerLista() {
        return fl.findByJoined("idProceso", getSelectedProceso());
    }
     public ProcesoDetalle getC() {
        return c;
    }

    public void setC(ProcesoDetalle c) {
        this.c = c;
    }
     public List<ProcesoDetalle> findAll() {
        return getFl().findAll();
    }
     
    public void create() {
        Proceso addProceso = this.selectedProceso;
        for (int i = 0; i < this.getResumen().size(); i++) {
            if (this.getFl().findByMultiple("idProceso", addProceso, "idPaso", paso.find(Integer.parseInt(String.valueOf(this.getResumen().get(i))))).size() > 0) {
                ProcesoDetalle addProcesoDetalle = this.getFl().findByMultiple("idProceso", addProceso, "idPaso", paso.find(Integer.parseInt(String.valueOf(this.getResumen().get(i))))).get(0);
                addProcesoDetalle.setIndice(i + 1);
                this.getFl().edit(addProcesoDetalle);
            } else {
                ProcesoDetalle addProcesoDetalle = new ProcesoDetalle();
                addProcesoDetalle.setIdProceso(addProceso);
                addProcesoDetalle.setIdPaso(paso.find(Integer.parseInt(String.valueOf(this.getResumen().get(i)))));
                addProcesoDetalle.setIndice(i + 1);
                this.getFl().edit(addProcesoDetalle);
            }
            
        }
        selectedProceso=new Proceso();
            paraAgregar.removeAll(paraAgregar);
            resumen.removeAll(resumen);
            
    }
    public void changeSelectedProceso(SelectEvent se) {
        if (se.getObject() != null) {
            try {
                this.setSelectedProceso((Proceso) se.getObject());
                cargarFiltrados();
                paraAgregar.removeAll(paraAgregar);
                resumen.removeAll(resumen);
                for (int i = 0; i < fl.findByJoined("idProceso", getSelectedProceso()).size(); i++) {
                    resumen.add(fl.findByJoined("idProceso", getSelectedProceso()).get(i).getIdPaso());

                }

                resumen.addAll(paraAgregar);
            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }
    public void changeSelectedPaso(SelectEvent se) {
        if (se.getObject() != null) {
            try {
                this.setSelectedPaso((Paso) se.getObject());
                paraAgregar.add(getSelectedPaso());
                resumen.add(getSelectedPaso());
                getFiltrados().remove(getSelectedPaso());
            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }
    public void cargarFiltrados() {
        List<Paso> listaCaca = new ArrayList<>();
        listaCaca = paso.findAll();
        for (int m = 0; m < fl.findByJoined("idProceso", getSelectedProceso()).size(); m++) {
            listaCaca.remove(fl.findByJoined("idProceso", getSelectedProceso()).get(m).getIdPaso());
        }
        setFiltrados(listaCaca);
    }
    
    public void remove(ProcesoDetalle pr) {
        this.getFl().remove(pr);
    }
    
    /**
     * @param filtrados the filtrados to set
     */
    public void setFiltrados(List<Paso> filtrados) {
        this.filtrados = filtrados;
    }
    /**
     * @return the paraAgregar
     */
    public List<Paso> getParaAgregar() {
        return paraAgregar;
    }

    /**
     * @param paraAgregar the paraAgregar to set
     */
    public void setParaAgregar(List<Paso> paraAgregar) {
        this.paraAgregar = paraAgregar;
    }
    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void sendMessage() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(this.getMensaje()));
    }
    public void onTransfer(TransferEvent event) {
        StringBuilder builder = new StringBuilder();
        for (Object item : event.getItems()) {
            builder.append(((ProcesoDetalle) item).getIdProceso()).append("<br />");
        }

        FacesMessage msg = new FacesMessage();
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
        msg.setSummary("Items Transferred");
        msg.setDetail(builder.toString());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     public Paso obtenerPaso(String r) {
        return paso.find(Integer.parseInt(r));
    }

    /**
     * @return the filtrados
     */
    public List<Paso> getFiltrados() {
        return filtrados;
    }
}
