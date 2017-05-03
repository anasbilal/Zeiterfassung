/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iadvise.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Teftazani1
 */
@Entity
@Table(name = "branche")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Branche.findAll", query = "SELECT b FROM Branche b")
    , @NamedQuery(name = "Branche.findByBrID", query = "SELECT b FROM Branche b WHERE b.brID = :brID")
    , @NamedQuery(name = "Branche.findByBeschreibung", query = "SELECT b FROM Branche b WHERE b.beschreibung = :beschreibung")})
public class Branche implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "brID")
    private Integer brID;
    @Column(name = "beschreibung")
    private String beschreibung;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "brid")
    private Collection<Firma> firmaCollection;

    public Branche() {
    }

    public Branche(Integer brID) {
        this.brID = brID;
    }

    public Integer getBrID() {
        return brID;
    }

    public void setBrID(Integer brID) {
        this.brID = brID;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    @XmlTransient
    public Collection<Firma> getFirmaCollection() {
        return firmaCollection;
    }

    public void setFirmaCollection(Collection<Firma> firmaCollection) {
        this.firmaCollection = firmaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (brID != null ? brID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Branche)) {
            return false;
        }
        Branche other = (Branche) object;
        if ((this.brID == null && other.brID != null) || (this.brID != null && !this.brID.equals(other.brID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Branche[ brID=" + brID + " ]";
    }
    
}
