/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iadvise.entities;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Teftazani1
 */
@Entity
@Table(name = "engagement")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Engagement.findAll", query = "SELECT e FROM Engagement e")
    , @NamedQuery(name = "Engagement.findByEngid", query = "SELECT e FROM Engagement e WHERE e.engid = :engid")
    , @NamedQuery(name = "Engagement.findBySummmeStunden", query = "SELECT e FROM Engagement e WHERE e.summmeStunden = :summmeStunden")
    , @NamedQuery(name = "Engagement.findByStandardsatz", query = "SELECT e FROM Engagement e WHERE e.standardsatz = :standardsatz")
    , @NamedQuery(name = "Engagement.findByVorjahrUebertrag", query = "SELECT e FROM Engagement e WHERE e.vorjahrUebertrag = :vorjahrUebertrag")
    , @NamedQuery(name = "Engagement.findByAddSubUev", query = "SELECT e FROM Engagement e WHERE e.addSubUev = :addSubUev")
    , @NamedQuery(name = "Engagement.findByRechnungsbetrag", query = "SELECT e FROM Engagement e WHERE e.rechnungsbetrag = :rechnungsbetrag")
    , @NamedQuery(name = "Engagement.findByNochAbzurechnen", query = "SELECT e FROM Engagement e WHERE e.nochAbzurechnen = :nochAbzurechnen")
    , @NamedQuery(name = "Engagement.findByAddSubAb", query = "SELECT e FROM Engagement e WHERE e.addSubAb = :addSubAb")
    , @NamedQuery(name = "Engagement.findByLeistungen", query = "SELECT e FROM Engagement e WHERE e.leistungen = :leistungen")
    , @NamedQuery(name = "Engagement.findByRealisation", query = "SELECT e FROM Engagement e WHERE e.realisation = :realisation")
    , @NamedQuery(name = "Engagement.findByStundensatz", query = "SELECT e FROM Engagement e WHERE e.stundensatz = :stundensatz")
    , @NamedQuery(name = "Engagement.findByAbweichStandardpreisPositiv", query = "SELECT e FROM Engagement e WHERE e.abweichStandardpreisPositiv = :abweichStandardpreisPositiv")
    , @NamedQuery(name = "Engagement.findByAbweichStandardpreisNegativ", query = "SELECT e FROM Engagement e WHERE e.abweichStandardpreisNegativ = :abweichStandardpreisNegativ")
    , @NamedQuery(name = "Engagement.findByBudget", query = "SELECT e FROM Engagement e WHERE e.budget = :budget")
    , @NamedQuery(name = "Engagement.findByRestBudget", query = "SELECT e FROM Engagement e WHERE e.restBudget = :restBudget")})
public class Engagement implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "engid")
    private Integer engid;
    @Column(name = "summme_stunden")
    private Integer summmeStunden;
    @Column(name = "standardsatz")
    private Long standardsatz;
    @Column(name = "vorjahr_uebertrag")
    private Long vorjahrUebertrag;
    @Column(name = "add_sub_uev")
    private Long addSubUev;
    @Column(name = "rechnungsbetrag")
    private Long rechnungsbetrag;
    @Column(name = "noch_abzurechnen")
    private Long nochAbzurechnen;
    @Column(name = "add_sub_ab")
    private Long addSubAb;
    @Column(name = "leistungen")
    private Long leistungen;
    @Column(name = "realisation")
    private Long realisation;
    @Column(name = "stundensatz")
    private Long stundensatz;
    @Column(name = "abweich_standardpreis_positiv")
    private Long abweichStandardpreisPositiv;
    @Column(name = "abweich_standardpreis_negativ")
    private Long abweichStandardpreisNegativ;
    @Column(name = "budget")
    private Long budget;
    @Column(name = "rest_budget")
    private Long restBudget;
    @JoinColumn(name = "prjid", referencedColumnName = "prjid")
    @ManyToOne
    private Projekt prjid;
    @JoinColumn(name = "mitid", referencedColumnName = "mitid")
    @ManyToOne
    private Mitarbeiter mitid;

    public Engagement() {
    }

    public Engagement(Integer engid) {
        this.engid = engid;
    }

    public Integer getEngid() {
        return engid;
    }

    public void setEngid(Integer engid) {
        this.engid = engid;
    }

    public Integer getSummmeStunden() {
        return summmeStunden;
    }

    public void setSummmeStunden(Integer summmeStunden) {
        this.summmeStunden = summmeStunden;
    }

    public Long getStandardsatz() {
        return standardsatz;
    }

    public void setStandardsatz(Long standardsatz) {
        this.standardsatz = standardsatz;
    }

    public Long getVorjahrUebertrag() {
        return vorjahrUebertrag;
    }

    public void setVorjahrUebertrag(Long vorjahrUebertrag) {
        this.vorjahrUebertrag = vorjahrUebertrag;
    }

    public Long getAddSubUev() {
        return addSubUev;
    }

    public void setAddSubUev(Long addSubUev) {
        this.addSubUev = addSubUev;
    }

    public Long getRechnungsbetrag() {
        return rechnungsbetrag;
    }

    public void setRechnungsbetrag(Long rechnungsbetrag) {
        this.rechnungsbetrag = rechnungsbetrag;
    }

    public Long getNochAbzurechnen() {
        return nochAbzurechnen;
    }

    public void setNochAbzurechnen(Long nochAbzurechnen) {
        this.nochAbzurechnen = nochAbzurechnen;
    }

    public Long getAddSubAb() {
        return addSubAb;
    }

    public void setAddSubAb(Long addSubAb) {
        this.addSubAb = addSubAb;
    }

    public Long getLeistungen() {
        return leistungen;
    }

    public void setLeistungen(Long leistungen) {
        this.leistungen = leistungen;
    }

    public Long getRealisation() {
        return realisation;
    }

    public void setRealisation(Long realisation) {
        this.realisation = realisation;
    }

    public Long getStundensatz() {
        return stundensatz;
    }

    public void setStundensatz(Long stundensatz) {
        this.stundensatz = stundensatz;
    }

    public Long getAbweichStandardpreisPositiv() {
        return abweichStandardpreisPositiv;
    }

    public void setAbweichStandardpreisPositiv(Long abweichStandardpreisPositiv) {
        this.abweichStandardpreisPositiv = abweichStandardpreisPositiv;
    }

    public Long getAbweichStandardpreisNegativ() {
        return abweichStandardpreisNegativ;
    }

    public void setAbweichStandardpreisNegativ(Long abweichStandardpreisNegativ) {
        this.abweichStandardpreisNegativ = abweichStandardpreisNegativ;
    }

    public Long getBudget() {
        return budget;
    }

    public void setBudget(Long budget) {
        this.budget = budget;
    }

    public Long getRestBudget() {
        return restBudget;
    }

    public void setRestBudget(Long restBudget) {
        this.restBudget = restBudget;
    }

    public Projekt getPrjid() {
        return prjid;
    }

    public void setPrjid(Projekt prjid) {
        this.prjid = prjid;
    }

    public Mitarbeiter getMitid() {
        return mitid;
    }

    public void setMitid(Mitarbeiter mitid) {
        this.mitid = mitid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (engid != null ? engid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Engagement)) {
            return false;
        }
        Engagement other = (Engagement) object;
        if ((this.engid == null && other.engid != null) || (this.engid != null && !this.engid.equals(other.engid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Engagement[ engid=" + engid + " ]";
    }
    
}
