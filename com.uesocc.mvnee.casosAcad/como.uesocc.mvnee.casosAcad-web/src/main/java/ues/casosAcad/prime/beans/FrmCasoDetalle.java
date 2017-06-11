package ues.casosAcad.prime.beans;

import com.uesocc.entities.casosAcad.Caso;
import com.uesocc.entities.casosAcad.CasoDetalle;
import com.uesocc.entities.casosAcad.CasoDetalleRequisito;
import com.uesocc.entities.casosAcad.PasoRequisito;
import com.uesocc.entities.casosAcad.ProcesoDetalle;
import com.uesocc.facades.casosAcad.CasoDetalleFacadeLocal;
import com.uesocc.facades.casosAcad.CasoDetalleRequisitoFacadeLocal;
import com.uesocc.facades.casosAcad.CasoFacadeLocal;
import com.uesocc.facades.casosAcad.PasoRequisitoFacadeLocal;
import com.uesocc.facades.casosAcad.ProcesoDetalleFacadeLocal;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;



@Named(value = "mbCasoDetalle")
@ViewScoped
public class FrmCasoDetalle implements Serializable {

    private String parameter;
    @EJB
    private CasoFacadeLocal caso;
    private Caso selectedCaso;
    @EJB
    private CasoDetalleFacadeLocal casoDetalle;
    @EJB
    private ProcesoDetalleFacadeLocal procesoDetalle;
    @EJB
    private PasoRequisitoFacadeLocal pasoRequisito;
    private CasoDetalle selectedCasoDetalle;
    private CasoDetalleRequisito selectedCasoDetalleRequisito;
    @EJB
    private CasoDetalleRequisitoFacadeLocal casoDetalleRequisito;

    private TreeNode root;
    private TreeNode selectedNode;

    private List<ProcesoDetalle> pasoList;
    private List<PasoRequisito> requisitoList;
    private ProcesoDetalle selectedPaso;
    private PasoRequisito selectedRequisito;

    public FrmCasoDetalle() {
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public void changeSelectedRequisito(SelectEvent se) {
        if (se.getObject() instanceof ProcesoDetalle) {
            selectedPaso = (ProcesoDetalle) se.getObject();
            requisitoList = pasoRequisito.findByJoined("idPaso", selectedPaso.getIdPaso());
            selectedCasoDetalle = casoDetalle.findByMultiple("idProcesDetalle", selectedPaso.getIdPaso().getIdPaso(), "idCaso", selectedCaso).get(0);
            System.out.println(selectedCasoDetalle.getIdCasoDetalle());
        } else if (se.getObject() instanceof PasoRequisito) {
            selectedRequisito =  (PasoRequisito) se.getObject();
            
        }

    }

    public void obtenerCaso() {
        selectedCaso = caso.find(Integer.parseInt(parameter));
        selectedCasoDetalle = casoDetalle.findByJoined("idCaso", selectedCaso).get(0);
        selectedCasoDetalleRequisito = new CasoDetalleRequisito();
        selectedPaso = new ProcesoDetalle();
        root = new DefaultTreeNode("Root", null);
        List<ProcesoDetalle> lstProc = procesoDetalle.findByJoined("idProceso", selectedCaso.getIdProceso());
        for (ProcesoDetalle proc : lstProc) {

            TreeNode node = new DefaultTreeNode(proc.getIdPaso(), root);
            List<PasoRequisito> listPR = pasoRequisito.findByJoined("idPaso", proc.getIdPaso());
            for (PasoRequisito pr : listPR) {
                TreeNode node0 = new DefaultTreeNode(pr.getIdRequisito(), node);
            }

        }

        pasoList = procesoDetalle.findByJoined("idProceso", selectedCaso.getIdProceso());

    }

    public void avanzarRequisito() {
            Calendar fecha = Calendar.getInstance(); 
            CasoDetalleRequisito agregarCDR = new CasoDetalleRequisito();
            agregarCDR.setEstadoRequisito("activo");
            agregarCDR.setFecha(fecha.getTime());
            agregarCDR.setIdCasoDetalle(casoDetalle.findByMultiple("idProcesDetalle", selectedPaso.getIdPaso().getIdPaso(), "idCaso", selectedCaso).get(0));
            agregarCDR.setIdPasoRequisito(selectedRequisito.getIdRequisito().getIdRequisito());
            casoDetalleRequisito.create(agregarCDR);
           
            if(casoDetalleRequisito.findByMultiple("idCasoDetalle", selectedCasoDetalle, "estadoRequisito", "activo").size()>=pasoRequisito.findByJoined("idPaso", selectedPaso.getIdPaso()).size()){
                System.out.println("CACA");
                selectedCasoDetalle.setEstado("inactivo");
                casoDetalle.edit(selectedCasoDetalle);
                
            }
    }

    public boolean comprobarListo(Object parameter) {
       int mostrado =0;
        if (parameter instanceof ProcesoDetalle) {
            ProcesoDetalle pasoSeleccionado = (ProcesoDetalle) parameter;
            
            return casoDetalle.findByTriple("idProcesDetalle", pasoSeleccionado.getIdPaso().getIdPaso(), "idCaso", selectedCaso, "estado", "inactivo").size()>0;
        } else if (parameter instanceof PasoRequisito) {
            PasoRequisito requisitoSeleccionado = (PasoRequisito) parameter;
            mostrado = requisitoSeleccionado.getIdRequisito().getIdRequisito();
            return casoDetalleRequisito.findByMultiple("idPasoRequisito", mostrado,"idCasoDetalle", selectedCasoDetalle).size()>0;

        }
        return false;
       
    }

    public CasoFacadeLocal getCaso() {
        return caso;
    }

    public void setCaso(CasoFacadeLocal caso) {
        this.caso = caso;
    }

    public Caso getSelectedCaso() {
        return selectedCaso;
    }

    public void setSelectedCaso(Caso selectedCaso) {
        this.selectedCaso = selectedCaso;
    }

    public CasoDetalleFacadeLocal getCasoDetalle() {
        return casoDetalle;
    }

    public void setCasoDetalle(CasoDetalleFacadeLocal casoDetalle) {
        this.casoDetalle = casoDetalle;
    }

    public CasoDetalle getSelectedCasoDetalle() {
        return selectedCasoDetalle;
    }

    public void setSelectedCasoDetalle(CasoDetalle selectedCasoDetalle) {
        this.selectedCasoDetalle = selectedCasoDetalle;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public TreeNode createTree() {
        TreeNode rootNode = new DefaultTreeNode(new ProcesoDetalle(), null);
        List<ProcesoDetalle> documentRootNodeList = procesoDetalle.findByJoined("idProceso", selectedCaso.getIdProceso());
        for (ProcesoDetalle doc : documentRootNodeList) {
            TreeNode node = new DefaultTreeNode(doc, rootNode);
            createSubNode(doc, node);
        }
        return rootNode;
    }

    public void createSubNode(ProcesoDetalle doc, TreeNode node) {
        List<ProcesoDetalle> documentList = procesoDetalle.findByJoined("idProceso", selectedCaso.getIdProceso());
        for (ProcesoDetalle subDoc : documentList) {
            TreeNode subNode = new DefaultTreeNode(subDoc, node);
            createSubNode(subDoc, subNode);
        }
    }

    public ProcesoDetalleFacadeLocal getProcesoDetalle() {
        return procesoDetalle;
    }

    public void setProcesoDetalle(ProcesoDetalleFacadeLocal procesoDetalle) {
        this.procesoDetalle = procesoDetalle;
    }

    public PasoRequisitoFacadeLocal getPasoRequisito() {
        return pasoRequisito;
    }

    public void setPasoRequisito(PasoRequisitoFacadeLocal pasoRequisito) {
        this.pasoRequisito = pasoRequisito;
    }

    public CasoDetalleRequisito getSelectedCasoDetalleRequisito() {
        return selectedCasoDetalleRequisito;
    }

    public void setSelectedCasoDetalleRequisito(CasoDetalleRequisito selectedCasoDetalleRequisito) {
        this.selectedCasoDetalleRequisito = selectedCasoDetalleRequisito;
    }

    public CasoDetalleRequisitoFacadeLocal getCasoDetalleRequisito() {
        return casoDetalleRequisito;
    }

    public void setCasoDetalleRequisito(CasoDetalleRequisitoFacadeLocal casoDetalleRequisito) {
        this.casoDetalleRequisito = casoDetalleRequisito;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public List<ProcesoDetalle> getPasoList() {
        return pasoList;
    }

    public void setPasoList(List<ProcesoDetalle> pasoList) {
        this.pasoList = pasoList;
    }

    public List<PasoRequisito> getRequisitoList() {
        return requisitoList;
    }

    public void setRequisitoList(List<PasoRequisito> requisitoList) {
        this.requisitoList = requisitoList;
    }

    public ProcesoDetalle getSelectedPaso() {
        return selectedPaso;
    }

    public void setSelectedPaso(ProcesoDetalle selectedPaso) {
        this.selectedPaso = selectedPaso;
    }

    public PasoRequisito getSelectedRequisito() {
        return selectedRequisito;
    }

    public void setSelectedRequisito(PasoRequisito selectedRequisito) {
        this.selectedRequisito = selectedRequisito;
    }

}
