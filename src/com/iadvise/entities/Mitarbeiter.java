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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@Table(name = "mitarbeiter")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mitarbeiter.findAll", query = "SELECT m FROM Mitarbeiter m")
    , @NamedQuery(name = "Mitarbeiter.findByMitid", query = "SELECT m FROM Mitarbeiter m WHERE m.mitid = :mitid")
    , @NamedQuery(name = "Mitarbeiter.findByFirmaid", query = "SELECT m FROM Mitarbeiter m WHERE m.firmaid = :firmaid")
    , @NamedQuery(name = "Mitarbeiter.findByFirmatatus", query = "SELECT m FROM Mitarbeiter m WHERE m.firmatatus = :firmatatus")
    , @NamedQuery(name = "Mitarbeiter.findByPersonalnummer", query = "SELECT m FROM Mitarbeiter m WHERE m.personalnummer = :personalnummer")
    , @NamedQuery(name = "Mitarbeiter.findByVorname", query = "SELECT m FROM Mitarbeiter m WHERE m.vorname = :vorname")
    , @NamedQuery(name = "Mitarbeiter.findByNachname", query = "SELECT m FROM Mitarbeiter m WHERE m.nachname = :nachname")
    , @NamedQuery(name = "Mitarbeiter.findByVor/nachname", query = "SELECT m FROM Mitarbeiter m WHERE m.vorname = :vorname and m.nachname = :nachname")
    , @NamedQuery(name = "Mitarbeiter.findByGeburtstag", query = "SELECT m FROM Mitarbeiter m WHERE m.geburtstag = :geburtstag")
    , @NamedQuery(name = "Mitarbeiter.findByGeburtsort", query = "SELECT m FROM Mitarbeiter m WHERE m.geburtsort = :geburtsort")
    , @NamedQuery(name = "Mitarbeiter.findByStrasse", query = "SELECT m FROM Mitarbeiter m WHERE m.strasse = :strasse")
    , @NamedQuery(name = "Mitarbeiter.findByPlz", query = "SELECT m FROM Mitarbeiter m WHERE m.plz = :plz")
    , @NamedQuery(name = "Mitarbeiter.findByStadt", query = "SELECT m FROM Mitarbeiter m WHERE m.stadt = :stadt")
    , @NamedQuery(name = "Mitarbeiter.findByTelefonnummerIAdvise", query = "SELECT m FROM Mitarbeiter m WHERE m.telefonnummerIAdvise = :telefonnummerIAdvise")
    , @NamedQuery(name = "Mitarbeiter.findByMobilnummerIAdvise", query = "SELECT m FROM Mitarbeiter m WHERE m.mobilnummerIAdvise = :mobilnummerIAdvise")
    , @NamedQuery(name = "Mitarbeiter.findByFestnetzPrivat", query = "SELECT m FROM Mitarbeiter m WHERE m.festnetzPrivat = :festnetzPrivat")
    , @NamedQuery(name = "Mitarbeiter.findByMobilnummerPrivat", query = "SELECT m FROM Mitarbeiter m WHERE m.mobilnummerPrivat = :mobilnummerPrivat")
    , @NamedQuery(name = "Mitarbeiter.findByNotfallnummer", query = "SELECT m FROM Mitarbeiter m WHERE m.notfallnummer = :notfallnummer")
    , @NamedQuery(name = "Mitarbeiter.findByStandort", query = "SELECT m FROM Mitarbeiter m WHERE m.standort = :standort")
    , @NamedQuery(name = "Mitarbeiter.findByPosition", query = "SELECT m FROM Mitarbeiter m WHERE m.position = :position")
    , @NamedQuery(name = "Mitarbeiter.findByEmail", query = "SELECT m FROM Mitarbeiter m WHERE m.email = :email")})
public class Mitarbeiter implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "mitid")
    private Integer mitid;
    @Column(name = "firmaid")
    private Integer firmaid;
    @Column(name = "firmatatus")
    private String firmatatus;
    @Column(name = "personalnummer")
    private Integer personalnummer;
    @Column(name = "vorname")
    private String vorname;
    @Column(name = "nachname")
    private String nachname;
    @Column(name = "geburtstag")
    @Temporal(TemporalType.DATE)
    private Date geburtstag;
    @Column(name = "geburtsort")
    private String geburtsort;
    @Column(name = "strasse")
    private String strasse;
    @Column(name = "plz")
    private String plz;
    @Column(name = "stadt")
    private String stadt;
    @Column(name = "telefonnummer_i_advise")
    private String telefonnummerIAdvise;
    @Column(name = "mobilnummer_i_advise")
    private String mobilnummerIAdvise;
    @Column(name = "festnetz_privat")
    private String festnetzPrivat;
    @Column(name = "mobilnummer_privat")
    private String mobilnummerPrivat;
    @Column(name = "notfallnummer")
    private String notfallnummer;
    @Column(name = "standort")
    private String standort;
    @Column(name = "position")
    private String position;
    @Column(name = "email")
    private String email;
    @OneToMany(mappedBy = "mitid")
    private Collection<Aktivitaet> aktivitaetCollection;
    @OneToMany(mappedBy = "mitid")
    private Collection<Projekt> projektCollection;
    @OneToMany(mappedBy = "mitid")
    private Collection<Engagement> engagementCollection;
    @OneToMany(mappedBy = "mitid")
    private Collection<Kampagne> kampagneCollection;
    @OneToMany(mappedBy = "mitid")
    private Collection<Kontakt> kontaktCollection;
    @OneToMany(mappedBy = "mitid")
    private Collection<Buchung> buchungCollection;
    @OneToMany(mappedBy = "mitid")
    private Collection<User> usersCollection;

    public Mitarbeiter() {
    }

    public Mitarbeiter(Integer mitid) {
        this.mitid = mitid;
    }

    public Integer getMitid() {
        return mitid;
    }

    public void setMitid(Integer mitid) {
        this.mitid = mitid;
    }

    public Integer getFirmaid() {
        return firmaid;
    }

    public void setFirmaid(Integer firmaid) {
        this.firmaid = firmaid;
    }

    public String getFirmatatus() {
        return firmatatus;
    }

    public void setFirmatatus(String firmatatus) {
        this.firmatatus = firmatatus;
    }

    public Integer getPersonalnummer() {
        return personalnummer;
    }

    public void setPersonalnummer(Integer personalnummer) {
        this.personalnummer = personalnummer;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public Date getGeburtstag() {
        return geburtstag;
    }

    public void setGeburtstag(Date geburtstag) {
        this.geburtstag = geburtstag;
    }

    public String getGeburtsort() {
        return geburtsort;
    }

    public void setGeburtsort(String geburtsort) {
        this.geburtsort = geburtsort;
    }

    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public String getStadt() {
        return stadt;
    }

    public void setStadt(String stadt) {
        this.stadt = stadt;
    }

    public String getTelefonnummerIAdvise() {
        return telefonnummerIAdvise;
    }

    public void setTelefonnummerIAdvise(String telefonnummerIAdvise) {
        this.telefonnummerIAdvise = telefonnummerIAdvise;
    }

    public String getMobilnummerIAdvise() {
        return mobilnummerIAdvise;
    }

    public void setMobilnummerIAdvise(String mobilnummerIAdvise) {
        this.mobilnummerIAdvise = mobilnummerIAdvise;
    }

    public String getFestnetzPrivat() {
        return festnetzPrivat;
    }

    public void setFestnetzPrivat(String festnetzPrivat) {
        this.festnetzPrivat = festnetzPrivat;
    }

    public String getMobilnummerPrivat() {
        return mobilnummerPrivat;
    }

    public void setMobilnummerPrivat(String mobilnummerPrivat) {
        this.mobilnummerPrivat = mobilnummerPrivat;
    }

    public String getNotfallnummer() {
        return notfallnummer;
    }

    public void setNotfallnummer(String notfallnummer) {
        this.notfallnummer = notfallnummer;
    }

    public String getStandort() {
        return standort;
    }

    public void setStandort(String standort) {
        this.standort = standort;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @XmlTransient
    public Collection<Aktivitaet> getAktivitaetCollection() {
        return aktivitaetCollection;
    }

    public void setAktivitaetCollection(Collection<Aktivitaet> aktivitaetCollection) {
        this.aktivitaetCollection = aktivitaetCollection;
    }

    @XmlTransient
    public Collection<Projekt> getProjektCollection() {
        return projektCollection;
    }

    public void setProjektCollection(Collection<Projekt> projektCollection) {
        this.projektCollection = projektCollection;
    }

    @XmlTransient
    public Collection<Engagement> getEngagementCollection() {
        return engagementCollection;
    }

    public void setEngagementCollection(Collection<Engagement> engagementCollection) {
        this.engagementCollection = engagementCollection;
    }

    @XmlTransient
    public Collection<Kampagne> getKampagneCollection() {
        return kampagneCollection;
    }

    public void setKampagneCollection(Collection<Kampagne> kampagneCollection) {
        this.kampagneCollection = kampagneCollection;
    }

    @XmlTransient
    public Collection<Kontakt> getKontaktCollection() {
        return kontaktCollection;
    }

    public void setKontaktCollection(Collection<Kontakt> kontaktCollection) {
        this.kontaktCollection = kontaktCollection;
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
        hash += (mitid != null ? mitid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mitarbeiter)) {
            return false;
        }
        Mitarbeiter other = (Mitarbeiter) object;
        if ((this.mitid == null && other.mitid != null) || (this.mitid != null && !this.mitid.equals(other.mitid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Mitarbeiter[ mitid=" + mitid + " ]";
    }

    @XmlTransient
    public Collection<User> getUsersCollection() {
        return usersCollection;
    }

    public void setUsersCollection(Collection<User> usersCollection) {
        this.usersCollection = usersCollection;
    }
    
}
