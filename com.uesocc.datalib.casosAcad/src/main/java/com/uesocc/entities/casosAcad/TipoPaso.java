/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uesocc.entities.casosAcad;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jssbbonilla
 */
@Entity
@Table(name = "tipo_paso", catalog = "casosAcad", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoPaso.findAll", query = "SELECT t FROM TipoPaso t")
    , @NamedQuery(name = "TipoPaso.findByIdTipoPaso", query = "SELECT t FROM TipoPaso t WHERE t.idTipoPaso = :idTipoPaso")
    , @NamedQuery(name = "TipoPaso.findByNombre", query = "SELECT t FROM TipoPaso t WHERE t.nombre = :nombre")
    , @NamedQuery(name = "TipoPaso.findByActivo", query = "SELECT t FROM TipoPaso t WHERE t.activo = :activo")})
public class TipoPaso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTipoPaso", nullable = false)
    private Integer idTipoPaso;
    @Basic(optional = false)
    @Column(name = "Nombre", nullable = false, length = 25)
    private String nombre;
    @Basic(optional = false)
    @Lob
    @Column(name = "Descripcion", nullable = false, length = 65535)
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "activo", nullable = false)
    private boolean activo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipoPaso")
    private List<Paso> pasoList;

    public TipoPaso() {
    }

    public TipoPaso(Integer idTipoPaso) {
        this.idTipoPaso = idTipoPaso;
    }

    public TipoPaso(Integer idTipoPaso, String nombre, String descripcion, boolean activo) {
        this.idTipoPaso = idTipoPaso;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.activo = activo;
    }

    public Integer getIdTipoPaso() {
        return idTipoPaso;
    }

    public void setIdTipoPaso(Integer idTipoPaso) {
        this.idTipoPaso = idTipoPaso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @XmlTransient
    public List<Paso> getPasoList() {
        return pasoList;
    }

    public void setPasoList(List<Paso> pasoList) {
        this.pasoList = pasoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoPaso != null ? idTipoPaso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoPaso)) {
            return false;
        }
        TipoPaso other = (TipoPaso) object;
        if ((this.idTipoPaso == null && other.idTipoPaso != null) || (this.idTipoPaso != null && !this.idTipoPaso.equals(other.idTipoPaso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.valueOf(idTipoPaso);
    }
    
}
