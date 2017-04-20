/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uesocc.entities.casosAcad;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jssbbonilla
 */
@Entity
@Table(name = "paso", catalog = "casosAcad", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Paso.findAll", query = "SELECT p FROM Paso p")
    , @NamedQuery(name = "Paso.findByIdPaso", query = "SELECT p FROM Paso p WHERE p.idPaso = :idPaso")
    , @NamedQuery(name = "Paso.findByTiempo", query = "SELECT p FROM Paso p WHERE p.tiempo = :tiempo")
    , @NamedQuery(name = "Paso.findByNombre", query = "SELECT p FROM Paso p WHERE p.nombre = :nombre")})
public class Paso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPaso", nullable = false)
    private Integer idPaso;
    @Basic(optional = false)
    @Lob
    @Column(name = "Descripcion", nullable = false, length = 65535)
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "Tiempo", nullable = false, length = 20)
    private String tiempo;
    @Basic(optional = false)
    @Column(name = "Nombre", nullable = false, length = 100)
    private String nombre;
    @JoinColumn(name = "idTipoPaso", referencedColumnName = "idTipoPaso", nullable = false)
    @ManyToOne(optional = false)
    private TipoPaso idTipoPaso;

    public Paso() {
    }

    public Paso(Integer idPaso) {
        this.idPaso = idPaso;
    }

    public Paso(Integer idPaso, String descripcion, String tiempo, String nombre) {
        this.idPaso = idPaso;
        this.descripcion = descripcion;
        this.tiempo = tiempo;
        this.nombre = nombre;
    }

    public Integer getIdPaso() {
        return idPaso;
    }

    public void setIdPaso(Integer idPaso) {
        this.idPaso = idPaso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoPaso getIdTipoPaso() {
        return idTipoPaso;
    }

    public void setIdTipoPaso(TipoPaso idTipoPaso) {
        this.idTipoPaso = idTipoPaso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPaso != null ? idPaso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Paso)) {
            return false;
        }
        Paso other = (Paso) object;
        if ((this.idPaso == null && other.idPaso != null) || (this.idPaso != null && !this.idPaso.equals(other.idPaso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.uesocc.entities.casosAcad.Paso[ idPaso=" + idPaso + " ]";
    }
    
}
