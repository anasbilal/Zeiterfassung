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
@Table(name = "aktivitaet")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Aktivitaet.findAll", query = "SELECT a FROM Aktivitaet a")
    , @NamedQuery(name = "Aktivitaet.findByAktid", query = "SELECT a FROM Aktivitaet a WHERE a.aktid = :aktid")
    , @NamedQuery(name = "Aktivitaet.findByAktname", query = "SELECT a FROM Aktivitaet a WHERE a.aktname = :aktname")
    , @NamedQuery(name = "Aktivitaet.findByBeschreibung", query = "SELECT a FROM Aktivitaet a WHERE a.beschreibung = :beschreibung")
    , @NamedQuery(name = "Aktivitaet.findByDatum", query = "SELECT a FROM Aktivitaet a WHERE a.datum = :datum")})
public class Aktivitaet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "aktid")
    private Integer aktid;
    @Column(name = "aktname")
    private String aktname;
    @Column(name = "beschreibung")
    private String beschreibung;
    @Column(name = "datum")
    @Temporal(TemporalType.DATE)
    private Date datum;
    @JoinColumn(name = "mitid", referencedColumnName = "mitid")
    @ManyToOne
    private Mitarbeiter mitid;
    @OneToMany(mappedBy = "aktid")
    private Collection<Kontakt> kontaktCollection;

    public Aktivitaet() {
    }

    public Aktivitaet(Integer aktid) {
        this.aktid = aktid;
    }

    public Integer getAktid() {
        return aktid;
    }

    public void setAktid(Integer aktid) {
        this.aktid = aktid;
    }

    public String getAktname() {
        return aktname;
    }

    public void setAktname(String aktname) {
        this.aktname = aktname;
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
        hash += (aktid != null ? aktid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Aktivitaet)) {
            return false;
        }
        Aktivitaet other = (Aktivitaet) object;
        if ((this.aktid == null && other.aktid != null) || (this.aktid != null && !this.aktid.equals(other.aktid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Aktivitaet[ aktid=" + aktid + " ]";
    }
    
}
