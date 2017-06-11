/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ues.casosAcad.prime.beans;

import com.uesocc.entities.casosAcad.Caso;
import com.uesocc.entities.casosAcad.CasoDetalle;
import com.uesocc.entities.casosAcad.ProcesoDetalle;
import com.uesocc.entities.casosAcad.Solicitudes;
import com.uesocc.facades.casosAcad.CasoDetalleFacadeLocal;
import com.uesocc.facades.casosAcad.CasoFacadeLocal;
import com.uesocc.facades.casosAcad.ProcesoDetalleFacadeLocal;
import com.uesocc.facades.casosAcad.SolicitudesFacadeLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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



@Named(value = "mbSolicitudes")
@ViewScoped
public class mbSolicitudes implements Serializable {

    /**
     * @return the caso
     */
    public CasoFacadeLocal getCaso() {
        return caso;
    }

    /**
     * @param caso the caso to set
     */
    public void setCaso(CasoFacadeLocal caso) {
        this.caso = caso;
    }

    /**
     * @return the casoDetalle
     */
    public CasoDetalleFacadeLocal getCasoDetalle() {
        return casoDetalle;
    }

    /**
     * @param casoDetalle the casoDetalle to set
     */
    public void setCasoDetalle(CasoDetalleFacadeLocal casoDetalle) {
        this.casoDetalle = casoDetalle;
    }

    /**
     * @return the detalle
     */
    public ProcesoDetalleFacadeLocal getDetalle() {
        return detalle;
    }

    /**
     * @param detalle the detalle to set
     */
    public void setDetalle(ProcesoDetalleFacadeLocal detalle) {
        this.detalle = detalle;
    }

    /**
     * @return the selectedSolicitud
     */
    public Solicitudes getSelectedSolicitud() {
        return selectedSolicitud;
    }

    /**
     * @param selectedSolicitud the selectedSolicitud to set
     */
    public void setSelectedSolicitud(Solicitudes selectedSolicitud) {
        this.selectedSolicitud = selectedSolicitud;
    }

    @EJB
    private SolicitudesFacadeLocal fl;
    @EJB
    private CasoFacadeLocal caso;
    @EJB
    private CasoDetalleFacadeLocal casoDetalle;
    @EJB
    private ProcesoDetalleFacadeLocal detalle;
    private LazyDataModel<Solicitudes> ldm;
    private Solicitudes c = new Solicitudes();
    private Solicitudes selectedSolicitud;
    private boolean showDetail = true;
    private boolean btnAdd = true;
    private boolean btnEdit = false;

    @PostConstruct
    private void init() {
        setSelectedSolicitud(new Solicitudes());
        try {
            this.setLdm(new LazyDataModel<Solicitudes>() {
                @Override
                public Object getRowKey(Solicitudes object) {
                    if (object != null) {
                        return object.getIdSolicitud();
                    }
                    return null;
                }

                @Override
                public Solicitudes getRowData(String rowKey) {
                    if (rowKey != null && !rowKey.isEmpty() && this.getWrappedData() != null) {
                        try {
                            Integer buscado = new Integer(rowKey);
                            for (Solicitudes thi : (List<Solicitudes>) getWrappedData()) {
                                if (thi.getIdSolicitud().compareTo(buscado) == 0) {
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
                            if (fl != null) {
                                this.setRowCount(fl.count());
                                salida = fl.findByAll("estado", "1");
                            }

                        } catch (Exception e) {
                            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
                        }
                        return salida;
                    }
                    salida = null;
                    try {
                        if (!filters.isEmpty() && (filters.containsKey("idSolicitud") || filters.containsKey("carnet") || filters.containsKey("nit") || filters.containsKey("usuario") || filters.containsKey("descripcionSolicitud") || filters.containsKey("fechaRecibida"))) {

                            if (filters.containsKey("idSolicitud")) {
                                salida = fl.findByAll("idSolicitud", filters.get("idSolicitud").toString());
                                if (ldm != null) {
                                    ldm.setRowCount(salida.size());
                                }
                            } else if (filters.containsKey("carnet")) {
                                salida = fl.findByAll("carnet", filters.get("carnet").toString());
                                if (ldm != null) {
                                    ldm.setRowCount(salida.size());
                                }
                            } else if (filters.containsKey("descripcionSolicitud")) {
                                salida = fl.findByAll("descripcionSolicitud", filters.get("descripcionSolicitud").toString());
                                if (ldm != null) {
                                    ldm.setRowCount(salida.size());
                                }
                            } else if (filters.containsKey("nit")) {

                                salida = fl.findByAll("nit", filters.get("nit").toString());

                                if (ldm != null) {
                                    ldm.setRowCount(salida.size());
                                }
                            } else if (filters.containsKey("usuario")) {

                                salida = fl.findByAll("usuario", filters.get("usuario").toString());

                                if (ldm != null) {
                                    ldm.setRowCount(salida.size());
                                }
                            } else if (filters.containsKey("fechaRecibida")) {

                                salida = fl.findByAll("fechaRecibida", filters.get("fechaRecibida").toString());

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

    public Solicitudes getC() {
        return c;
    }

    public void setC(Solicitudes c) {
        this.c = c;
    }

    public SolicitudesFacadeLocal getFl() {
        return fl;
    }

    public void setFl(SolicitudesFacadeLocal fl) {
        this.fl = fl;
    }

    public List<Solicitudes> findAll() {
        return getFl().findAll();
    }

    public void generarCaso(Solicitudes s) {
        Caso cas = new Caso();
        Calendar fecha = Calendar.getInstance();
        cas.setCarnet(s.getCarnet());
        cas.setNit(s.getNit());
        cas.setIdSolicitud(s);
        cas.setIdProceso(s.getIdProceso());
        getCaso().create(cas);
        s.setEstado(false);
        fl.edit(s);
        List<ProcesoDetalle> listCaca = detalle.findByJoined("idProceso", s.getIdProceso());
        for (ProcesoDetalle proc : listCaca) {

            CasoDetalle casDet = new CasoDetalle();
            casDet.setEstado("Activo");
            casDet.setFecha(fecha.getTime());
            casDet.setIdCaso(cas);
            casDet.setIdProcesoDetalle(proc.getIdPaso().getIdPaso());
            getCasoDetalle().create(casDet);
        }

    }

    public void create() {
        if (this.getSelectedSolicitudes().getCarnet().isEmpty() != true) {
            try {
                java.util.Date fecha = new java.util.Date();
                getSelectedSolicitudes().setFechaRecibida(fecha);
                this.getFl().create(this.getSelectedSolicitudes());
                setSelectedSolicitud(new Solicitudes());
                btnAdd = true;
                btnEdit = false;

            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            }
        } else {

        }

    }

    public void remove() {
        try {
            this.getFl().remove(this.getSelectedSolicitudes());
            setSelectedSolicitud(new Solicitudes());
            btnAdd = true;
            btnEdit = false;

        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void edit(Solicitudes t) {
        this.setC(t);
    }

    public void edit() {

        try {
            this.getFl().edit(this.getSelectedSolicitudes());
            setSelectedSolicitud(new Solicitudes());
            btnAdd = true;
            btnEdit = false;

        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void empty() {
        this.setC(new Solicitudes());
    }

    public Solicitudes getSelectedSolicitudes() {
        return getSelectedSolicitud();
    }

    public void setSelectedSolicitudes(Solicitudes selecSolicitud) {
        this.setSelectedSolicitud(selecSolicitud);
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
                this.setSelectedSolicitud((Solicitudes) se.getObject());
                this.setShowDetail(true);
                this.setBtnAdd(false);
                this.setBtnEdit(true);

            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }

    public void nuevo() {

        this.setSelectedSolicitud(new Solicitudes());
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
}
