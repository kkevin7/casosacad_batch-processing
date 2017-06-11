package ues.casosAcad.prime.beans;

import com.uesocc.entities.casosAcad.Paso;
import com.uesocc.entities.casosAcad.PasoRequisito;
import com.uesocc.entities.casosAcad.Requisito;
import com.uesocc.entities.casosAcad.TipoPaso;
import com.uesocc.facades.casosAcad.RequisitoFacadeLocal;
import com.uesocc.facades.casosAcad.PasoRequisitoFacadeLocal;
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
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.SortOrder;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DualListModel;
import org.primefaces.model.LazyDataModel;



@Named(value = "mbPasoRequisito")
@ViewScoped
public class FrmPasoRequisitoPrime implements Serializable {

    @EJB
    private PasoRequisitoFacadeLocal fl;
    @EJB
    private RequisitoFacadeLocal req;
    private LazyDataModel<PasoRequisito> ldm;
    private PasoRequisito c = new PasoRequisito();
    private PasoRequisito selectedPasoRequisito;
    private Requisito selectedRequisito;
    private Paso selectedPaso;
    private String mensaje;
    private boolean showDetail = false;
    private boolean btnAdd = false;
    private boolean btnEdit = false;
    private boolean skip;
    private List<Requisito> filtrados = new ArrayList<>();
    private List<Requisito> paraAgregar = new ArrayList<>();
    private List<Requisito> resumen = new ArrayList<>();
    private List<Requisito> source = new ArrayList<>();
    private List<Requisito> target = new ArrayList<>();
    private DualListModel<Requisito> lista;

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    @PostConstruct
    private void init() {
        this.setSelectedPasoRequisito(new PasoRequisito());
        this.setSelectedPaso(new Paso());
        this.setSelectedRequisito(new Requisito());
        source = req.findAll();
        lista = new DualListModel<>(source, target);
        try {

            this.setLdm(new LazyDataModel<PasoRequisito>() {
                @Override
                public Object getRowKey(PasoRequisito object) {
                    if (object != null) {
                        return object.getIdPasoRequisito();
                    }
                    return null;
                }

                @Override
                public PasoRequisito getRowData(String rowKey) {
                    if (rowKey != null && !rowKey.isEmpty() && this.getWrappedData() != null) {
                        try {
                            Integer buscado = new Integer(rowKey);
                            for (PasoRequisito thi : (List<PasoRequisito>) getWrappedData()) {
                                if (thi.getIdPasoRequisito().compareTo(buscado) == 0) {
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
                public List<PasoRequisito> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
                    List<PasoRequisito> salida = new ArrayList();
                    try {
    if (!filters.isEmpty() && (filters.containsKey("idPaso")||filters.containsKey("descripcion")||filters.containsKey("tiempo")||filters.containsKey("idTipoPaso.idTipoPaso"))) {
                            
                            if(filters.containsKey("idPaso")){
                                salida = fl.findByAll("idPaso", filters.get("idPaso").toString());
                            if (ldm != null) {
                                ldm.setRowCount(salida.size());
                            }
                            } else if(filters.containsKey("descripcion")){
                                salida = fl.findByAll("descripcion", filters.get("decripcion").toString());
                            if (ldm != null) {
                                ldm.setRowCount(salida.size());
                            }
                            } else if(filters.containsKey("tiempo")){
                                salida = fl.findByAll("tiempo", filters.get("tiempo").toString());
                            if (ldm != null) {
                                ldm.setRowCount(salida.size());
                            }
                            
                            }
                            else if(filters.containsKey("idTipoPaso.idTipoPaso")){
                                salida = fl.findByJoined("idTipoPaso", (TipoPaso) filters.get("idTipoPaso.idTipoPaso"));
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
        source = req.findAll();
    }

    public String onFlowProcess(FlowEvent event) {
        if (skip) {
            skip = false;   //reset in case user goes back
            return "confirm";
        } else {
            return event.getNewStep();
        }
    }

    public boolean tieneLista(Paso p) {
        return fl.findByJoined("idPaso", p).size() > 0;
    }

    public List<PasoRequisito> obtenerLista() {
        return fl.findByJoined("idPaso", getSelectedPaso());
    }

    public PasoRequisito getC() {
        return c;
    }

    public void setC(PasoRequisito c) {
        this.c = c;
    }

    public PasoRequisitoFacadeLocal getFl() {
        return fl;
    }

    public void setFl(PasoRequisitoFacadeLocal fl) {
        this.fl = fl;
    }

    public List<PasoRequisito> findAll() {
        return getFl().findAll();
    }

    public void create() {
        Paso addPaso = this.selectedPaso;
        for (int i = 0; i < this.getResumen().size(); i++) {
            if (this.getFl().findByMultiple("idPaso", addPaso, "idRequisito", req.find(Integer.parseInt(String.valueOf(this.getResumen().get(i))))).size() > 0) {
                PasoRequisito addPasoRequisito = this.getFl().findByMultiple("idPaso", addPaso, "idRequisito", req.find(Integer.parseInt(String.valueOf(this.getResumen().get(i))))).get(0);
                addPasoRequisito.setIndice(i + 1);
                this.getFl().edit(addPasoRequisito);
            } else {
                PasoRequisito addPasoRequisito = new PasoRequisito();
                addPasoRequisito.setIdPaso(addPaso);
                addPasoRequisito.setIdRequisito(req.find(Integer.parseInt(String.valueOf(this.getResumen().get(i)))));
                addPasoRequisito.setIndice(i + 1);
                this.getFl().edit(addPasoRequisito);
            }
            
        }
        selectedPaso=new Paso();
            paraAgregar.removeAll(paraAgregar);
    }

    public void remove(PasoRequisito pr) {
        this.getFl().remove(pr);
    } 

    public void edit(PasoRequisito t) {
        this.setC(t);
    }

    public void edit() {
        this.getFl().edit(this.getSelectedPasoRequisito());

    }

    public void empty() {
        this.setC(new PasoRequisito());
    }

    public PasoRequisito getSelectedPasoRequisito() {
        return selectedPasoRequisito;
    }

    public void setSelectedPasoRequisito(PasoRequisito selectedPasoRequisito) {
        this.selectedPasoRequisito = selectedPasoRequisito;
    }

    public LazyDataModel getLdm() {
        return ldm;
    }

    public void setLdm(LazyDataModel ldm) {
        this.ldm = ldm;
    }

    public void changeSelected(SelectEvent se) {
        if (se.getObject() != null) {
            try {

                this.setSelectedPasoRequisito((PasoRequisito) se.getObject());
                this.setSelectedPaso((Paso) se.getObject());
                cargarFiltrados();
                this.setShowDetail(true);
                this.setBtnAdd(false);
                this.setBtnEdit(true);

            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }

    public void changeSelectedPaso(SelectEvent se) {
        if (se.getObject() != null) {
            try {
                this.setSelectedPaso((Paso) se.getObject());
                cargarFiltrados();
                paraAgregar.removeAll(paraAgregar);
                resumen.removeAll(resumen);
                for (int i = 0; i < fl.findByJoined("idPaso", getSelectedPaso()).size(); i++) {
                    resumen.add(fl.findByJoined("idPaso", getSelectedPaso()).get(i).getIdRequisito());

                }

                resumen.addAll(paraAgregar);
            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }

    public void changeSelectedRequisito(SelectEvent se) {
        if (se.getObject() != null) {
            try {
                this.setSelectedRequisito((Requisito) se.getObject());
                paraAgregar.add(getSelectedRequisito());
                resumen.add(getSelectedRequisito());
                filtrados.remove(getSelectedRequisito());
            } catch (Exception e) {
                
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }

    public void nuevo() {

        this.setSelectedPasoRequisito(new PasoRequisito());
        this.setBtnAdd(true);
        this.setBtnEdit(false);
        this.setShowDetail(true);
    }

    public boolean isShowDetail() {
        return showDetail;
    }

    public void setShowDetail(boolean showDetail) {
        this.showDetail = showDetail;
    }

    public boolean isBtnAdd() {
        return btnAdd;
    }

    public void setBtnAdd(boolean btnAdd) {
        this.btnAdd = btnAdd;
    }

    public boolean isBtnEdit() {
        return btnEdit;
    }

    public void setBtnEdit(boolean btnEdit) {
        this.btnEdit = btnEdit;
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
            builder.append(((PasoRequisito) item).getIdPaso()).append("<br />");
        }

        FacesMessage msg = new FacesMessage();
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
        msg.setSummary("Items Transferred");
        msg.setDetail(builder.toString());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onSelect(SelectEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Selected", event.getObject().toString()));
    }

    public void onUnselect(UnselectEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Unselected", event.getObject().toString()));
    }

    public void onReorder() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "List Reordered", null));
    }

    public DualListModel<Requisito> getLista() {
        return lista;
    }

    public void setLista(DualListModel<Requisito> lista) {
        this.lista = lista;
    }

    /**
     * @return the selectedPaso
     */
    public Paso getSelectedPaso() {
        return selectedPaso;
    }

    /**
     * @param selectedPaso the selectedPaso to set
     */
    public void setSelectedPaso(Paso selectedPaso) {
        this.selectedPaso = selectedPaso;
    }

   

    public void cargarFiltrados() {
        List<Requisito> listaCaca = new ArrayList<>();
        listaCaca = req.findAll();
        for (int m = 0; m < fl.findByJoined("idPaso", getSelectedPaso()).size(); m++) {
            listaCaca.remove(fl.findByJoined("idPaso", getSelectedPaso()).get(m).getIdRequisito());
        }
        setFiltrados(listaCaca);
    }

    /**
     * @return the filtrados
     */
    public List<Requisito> getFiltrados() {
        return filtrados;
    }

    /**
     * @param filtrados the filtrados to set
     */
    public void setFiltrados(List<Requisito> filtrados) {
        this.filtrados = filtrados;
    }

    /**
     * @return the paraAgregar
     */
    public List<Requisito> getParaAgregar() {
        return paraAgregar;
    }

    /**
     * @param paraAgregar the paraAgregar to set
     */
    public void setParaAgregar(List<Requisito> paraAgregar) {
        this.paraAgregar = paraAgregar;
    }

    /**
     * @return the selectedRequisito
     */
    public Requisito getSelectedRequisito() {
        return selectedRequisito;
    }

    public Requisito obtenerRequisito(String r) {
        return req.find(Integer.parseInt(r));
    }

    /**
     * @param selectedRequisito the selectedRequisito to set
     */
    public void setSelectedRequisito(Requisito selectedRequisito) {
        this.selectedRequisito = selectedRequisito;
    }

    /**
     * @return the resumen
     */
    public List<Requisito> getResumen() {
        return resumen;
    }

    /**
     * @param resumen the resumen to set
     */
    public void setResumen(List<Requisito> resumen) {
        this.resumen = resumen;
    }
}
