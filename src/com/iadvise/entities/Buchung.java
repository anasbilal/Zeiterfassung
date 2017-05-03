/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iadvise.entities;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Teftazani1
 */
@Entity
@Table(name = "buchung")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Buchung.findAll", query = "SELECT b FROM Buchung b")
    , @NamedQuery(name = "Buchung.findByBuID", query = "SELECT b FROM Buchung b WHERE b.buID = :buID")
    , @NamedQuery(name = "Buchung.findByStichtag", query = "SELECT b FROM Buchung b WHERE b.stichtag = :stichtag")
    , @NamedQuery(name = "Buchung.findByBudgetstunden", query = "SELECT b FROM Buchung b WHERE b.budgetstunden = :budgetstunden")})
public class Buchung implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "buID")
    private Integer buID;
    @Column(name = "stichtag")
    @Temporal(TemporalType.DATE)
    private Date stichtag;
    @Column(name = "budgetstunden")
    private Integer budgetstunden;
    @JoinColumn(name = "mitid", referencedColumnName = "mitid")
    @ManyToOne
    private Mitarbeiter mitid;
    @JoinColumn(name = "prjid", referencedColumnName = "prjid")
    @ManyToOne
    private Projekt prjid;

    public Buchung() {
    }

    public Buchung(Integer buID) {
        this.buID = buID;
    }

    public Integer getBuID() {
        return buID;
    }

    public void setBuID(Integer buID) {
        this.buID = buID;
    }

    public Date getStichtag() {
        return stichtag;
    }

    public void setStichtag(Date stichtag) {
        this.stichtag = stichtag;
    }

    public Integer getBudgetstunden() {
        return budgetstunden;
    }

    public void setBudgetstunden(Integer budgetstunden) {
        this.budgetstunden = budgetstunden;
    }

    public Mitarbeiter getMitid() {
        return mitid;
    }

    public void setMitid(Mitarbeiter mitid) {
        this.mitid = mitid;
    }

    public Projekt getPrjid() {
        return prjid;
    }

    public void setPrjid(Projekt prjid) {
        this.prjid = prjid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (buID != null ? buID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Buchung)) {
            return false;
        }
        Buchung other = (Buchung) object;
        if ((this.buID == null && other.buID != null) || (this.buID != null && !this.buID.equals(other.buID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Buchung[ buID=" + buID + " ]";
    }
    
}
