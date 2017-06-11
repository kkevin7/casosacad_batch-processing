/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uesocc.entities.casosAcad;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jssbbonilla
 */
@Entity
@Table(name = "solicitudes", catalog = "casosAcad", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Solicitudes.findAll", query = "SELECT s FROM Solicitudes s")
    , @NamedQuery(name = "Solicitudes.findByIdSolicitud", query = "SELECT s FROM Solicitudes s WHERE s.idSolicitud = :idSolicitud")
    , @NamedQuery(name = "Solicitudes.findByCarnet", query = "SELECT s FROM Solicitudes s WHERE s.carnet = :carnet")
    , @NamedQuery(name = "Solicitudes.findByNit", query = "SELECT s FROM Solicitudes s WHERE s.nit = :nit")
    , @NamedQuery(name = "Solicitudes.findByFechaRecibida", query = "SELECT s FROM Solicitudes s WHERE s.fechaRecibida = :fechaRecibida")
    , @NamedQuery(name = "Solicitudes.findByEstado", query = "SELECT s FROM Solicitudes s WHERE s.estado = :estado")})
public class Solicitudes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idSolicitud", nullable = false)
    private Integer idSolicitud;
    @Column(name = "carnet", length = 7)
    private String carnet;
    @Column(name = "NIT")
    private Integer nit;
    @Basic(optional = false)
    @Column(name = "FechaRecibida", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaRecibida;
    @Basic(optional = false)
    @Column(name = "estado", nullable = false)
    private boolean estado;
    @OneToMany(mappedBy = "idSolicitud")
    private List<Caso> casoList;
    @JoinColumn(name = "idProceso", referencedColumnName = "idProceso", nullable = false)
    @ManyToOne(optional = false)
    private Proceso idProceso;

    public Solicitudes() {
    }
    

    public Solicitudes(Integer idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public Solicitudes( Date fechaRecibida,Proceso idProceso, String carnet) {
        this.carnet = carnet;
        this.fechaRecibida = fechaRecibida;
        this.estado = false;
        this.idProceso = idProceso;
        this.nit = 02071312;
    }
    public Solicitudes(Integer idSolicitud, Date fechaRecibida, boolean estado) {
        this.idSolicitud = idSolicitud;
        this.fechaRecibida = fechaRecibida;
        this.estado = estado;
    }

    public Integer getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Integer idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }

    public Integer getNit() {
        return nit;
    }

    public void setNit(Integer nit) {
        this.nit = nit;
    }

    public Date getFechaRecibida() {
        return fechaRecibida;
    }

    public void setFechaRecibida(Date fechaRecibida) {
        this.fechaRecibida = fechaRecibida;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @XmlTransient
    public List<Caso> getCasoList() {
        return casoList;
    }

    public void setCasoList(List<Caso> casoList) {
        this.casoList = casoList;
    }

    public Proceso getIdProceso() {
        return idProceso;
    }

    public void setIdProceso(Proceso idProceso) {
        this.idProceso = idProceso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSolicitud != null ? idSolicitud.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Solicitudes)) {
            return false;
        }
        Solicitudes other = (Solicitudes) object;
        if ((this.idSolicitud == null && other.idSolicitud != null) || (this.idSolicitud != null && !this.idSolicitud.equals(other.idSolicitud))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.valueOf(idSolicitud);
    }
    
}
