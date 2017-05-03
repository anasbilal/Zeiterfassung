/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iadvise.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
 * @author Teftazani1
 */
@Entity
@Table(name = "kampagne")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kampagne.findAll", query = "SELECT k FROM Kampagne k")
    , @NamedQuery(name = "Kampagne.findByKampid", query = "SELECT k FROM Kampagne k WHERE k.kampid = :kampid")
    , @NamedQuery(name = "Kampagne.findByKampname", query = "SELECT k FROM Kampagne k WHERE k.kampname = :kampname")
    , @NamedQuery(name = "Kampagne.findByBeschreibung", query = "SELECT k FROM Kampagne k WHERE k.beschreibung = :beschreibung")
    , @NamedQuery(name = "Kampagne.findByDatum", query = "SELECT k FROM Kampagne k WHERE k.datum = :datum")})
public class Kampagne implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "kampid")
    private Integer kampid;
    @Column(name = "kampname")
    private String kampname;
    @Column(name = "beschreibung")
    private String beschreibung;
    @Column(name = "datum")
    @Temporal(TemporalType.DATE)
    private Date datum;
    @JoinColumn(name = "mitid", referencedColumnName = "mitid")
    @ManyToOne
    private Mitarbeiter mitid;
    @OneToMany(mappedBy = "kampid")
    private Collection<Kontakt> kontaktCollection;

    public Kampagne() {
    }

    public Kampagne(Integer kampid) {
        this.kampid = kampid;
    }

    public Integer getKampid() {
        return kampid;
    }

    public void setKampid(Integer kampid) {
        this.kampid = kampid;
    }

    public String getKampname() {
        return kampname;
    }

    public void setKampname(String kampname) {
        this.kampname = kampname;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public Mitarbeiter getMitid() {
        return mitid;
    }

    public void setMitid(Mitarbeiter mitid) {
        this.mitid = mitid;
    }

    @XmlTransient
    public Collection<Kontakt> getKontaktCollection() {
        return kontaktCollection;
    }

    public void setKontaktCollection(Collection<Kontakt> kontaktCollection) {
        this.kontaktCollection = kontaktCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kampid != null ? kampid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Kampagne)) {
            return false;
        }
        Kampagne other = (Kampagne) object;
        if ((this.kampid == null && other.kampid != null) || (this.kampid != null && !this.kampid.equals(other.kampid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Kampagne[ kampid=" + kampid + " ]";
    }
    
}
