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
@Table(name = "projekt")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Projekt.findAll", query = "SELECT p FROM Projekt p")
    , @NamedQuery(name = "Projekt.findByPrjid", query = "SELECT p FROM Projekt p WHERE p.prjid = :prjid")
    , @NamedQuery(name = "Projekt.findByProjektname", query = "SELECT p FROM Projekt p WHERE p.projektname = :projektname")
    , @NamedQuery(name = "Projekt.findByProjektnummer", query = "SELECT p FROM Projekt p WHERE p.projektnummer = :projektnummer")
    , @NamedQuery(name = "Projekt.findByGesetzlPflichtpruefung", query = "SELECT p FROM Projekt p WHERE p.gesetzlPflichtpruefung = :gesetzlPflichtpruefung")
    , @NamedQuery(name = "Projekt.findByGesiegelt", query = "SELECT p FROM Projekt p WHERE p.gesiegelt = :gesiegelt")
    , @NamedQuery(name = "Projekt.findByRechnungslegung", query = "SELECT p FROM Projekt p WHERE p.rechnungslegung = :rechnungslegung")
    , @NamedQuery(name = "Projekt.findByBescheinigungsdatum", query = "SELECT p FROM Projekt p WHERE p.bescheinigungsdatum = :bescheinigungsdatum")
    , @NamedQuery(name = "Projekt.findByBudget", query = "SELECT p FROM Projekt p WHERE p.budget = :budget")
    , @NamedQuery(name = "Projekt.findByProjektende", query = "SELECT p FROM Projekt p WHERE p.projektende = :projektende")
    , @NamedQuery(name = "Projekt.findByStundensatz", query = "SELECT p FROM Projekt p WHERE p.stundensatz = :stundensatz")})
public class Projekt implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "prjid")
    private Integer prjid;
    @Column(name = "projektname")
    private String projektname;
    @Column(name = "projektnummer")
    private Integer projektnummer;
    @Column(name = "gesetzl_pflichtpruefung")
    private String gesetzlPflichtpruefung;
    @Column(name = "gesiegelt")
    private String gesiegelt;
    @Column(name = "rechnungslegung")
    private String rechnungslegung;
    @Column(name = "bescheinigungsdatum")
    @Temporal(TemporalType.DATE)
    private Date bescheinigungsdatum;
    @Column(name = "budget")
    private Long budget;
    @Column(name = "projektende")
    @Temporal(TemporalType.DATE)
    private Date projektende;
    @Column(name = "stundensatz")
    private Long stundensatz;
    @JoinColumn(name = "firmaid", referencedColumnName = "firmaid")
    @ManyToOne
    private Firma firmaid;
    @JoinColumn(name = "mitid", referencedColumnName = "mitid")
    @ManyToOne
    private Mitarbeiter mitid;
    @OneToMany(mappedBy = "prjid")
    private Collection<Engagement> engagementCollection;
    @OneToMany(mappedBy = "prjid")
    private Collection<Buchung> buchungCollection;

    public Projekt() {
    }

    public Projekt(Integer prjid) {
        this.prjid = prjid;
    }

    public Integer getPrjid() {
        return prjid;
    }

    public void setPrjid(Integer prjid) {
        this.prjid = prjid;
    }

    public String getProjektname() {
        return projektname;
    }

    public void setProjektname(String projektname) {
        this.projektname = projektname;
    }

    public Integer getProjektnummer() {
        return projektnummer;
    }

    public void setProjektnummer(Integer projektnummer) {
        this.projektnummer = projektnummer;
    }

    public String getGesetzlPflichtpruefung() {
        return gesetzlPflichtpruefung;
    }

    public void setGesetzlPflichtpruefung(String gesetzlPflichtpruefung) {
        this.gesetzlPflichtpruefung = gesetzlPflichtpruefung;
    }

    public String getGesiegelt() {
        return gesiegelt;
    }

    public void setGesiegelt(String gesiegelt) {
        this.gesiegelt = gesiegelt;
    }

    public String getRechnungslegung() {
        return rechnungslegung;
    }

    public void setRechnungslegung(String rechnungslegung) {
        this.rechnungslegung = rechnungslegung;
    }

    public Date getBescheinigungsdatum() {
        return bescheinigungsdatum;
    }

    public void setBescheinigungsdatum(Date bescheinigungsdatum) {
        this.bescheinigungsdatum = bescheinigungsdatum;
    }

    public Long getBudget() {
        return budget;
    }

    public void setBudget(Long budget) {
        this.budget = budget;
    }

    public Date getProjektende() {
        return projektende;
    }

    public void setProjektende(Date projektende) {
        this.projektende = projektende;
    }

    public Long getStundensatz() {
        return stundensatz;
    }

    public void setStundensatz(Long stundensatz) {
        this.stundensatz = stundensatz;
    }

    public Firma getFirmaid() {
        return firmaid;
    }

    public void setFirmaid(Firma firmaid) {
        this.firmaid = firmaid;
    }

    public Mitarbeiter getMitid() {
        return mitid;
    }

    public void setMitid(Mitarbeiter mitid) {
        this.mitid = mitid;
    }

    @XmlTransient
    public Collection<Engagement> getEngagementCollection() {
        return engagementCollection;
    }

    public void setEngagementCollection(Collection<Engagement> engagementCollection) {
        this.engagementCollection = engagementCollection;
    }

    @XmlTransient
    public Collection<Buchung> getBuchungCollection() {
        return buchungCollection;
    }

    public void setBuchungCollection(Collection<Buchung> buchungCollection) {
        this.buchungCollection = buchungCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (prjid != null ? prjid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Projekt)) {
            return false;
        }
        Projekt other = (Projekt) object;
        if ((this.prjid == null && other.prjid != null) || (this.prjid != null && !this.prjid.equals(other.prjid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Projekt[ prjid=" + prjid + " ]";
    }
    
}
