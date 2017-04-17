
package ues.casosAcad.prime.beans;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author kevin
 */
public class MensajesFormularios {
 
    
    public void msgCreadoExito() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Creado!", "Creado con Exito");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void msgFaltanCampos() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Existen campos vacios"));
    }
    
    public void msgModificacion() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Guardado!", "Modificación exitosa"));
    }
    
    public void msgEliminacion() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Eliminado!", "Borrado con exito"));
    }
    
}
