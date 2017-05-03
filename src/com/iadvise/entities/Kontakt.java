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
@Table(name = "kontakt")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kontakt.findAll", query = "SELECT k FROM Kontakt k")
    , @NamedQuery(name = "Kontakt.findByKontaktid", query = "SELECT k FROM Kontakt k WHERE k.kontaktid = :kontaktid")
    , @NamedQuery(name = "Kontakt.findByAnrede", query = "SELECT k FROM Kontakt k WHERE k.anrede = :anrede")
    , @NamedQuery(name = "Kontakt.findByVorname", query = "SELECT k FROM Kontakt k WHERE k.vorname = :vorname")
    , @NamedQuery(name = "Kontakt.findByName", query = "SELECT k FROM Kontakt k WHERE k.name = :name")
    , @NamedQuery(name = "Kontakt.findByTitel", query = "SELECT k FROM Kontakt k WHERE k.titel = :titel")
    , @NamedQuery(name = "Kontakt.findByAbschluss", query = "SELECT k FROM Kontakt k WHERE k.abschluss = :abschluss")
    , @NamedQuery(name = "Kontakt.findByPosition", query = "SELECT k FROM Kontakt k WHERE k.position = :position")
    , @NamedQuery(name = "Kontakt.findByKontaktqualitaet", query = "SELECT k FROM Kontakt k WHERE k.kontaktqualitaet = :kontaktqualitaet")
    , @NamedQuery(name = "Kontakt.findByLetzterkontakt", query = "SELECT k FROM Kontakt k WHERE k.letzterkontakt = :letzterkontakt")
    , @NamedQuery(name = "Kontakt.findByEmail", query = "SELECT k FROM Kontakt k WHERE k.email = :email")
    , @NamedQuery(name = "Kontakt.findByTelefonGeschaeftl", query = "SELECT k FROM Kontakt k WHERE k.telefonGeschaeftl = :telefonGeschaeftl")
    , @NamedQuery(name = "Kontakt.findByPersoenlicheBemerkung", query = "SELECT k FROM Kontakt k WHERE k.persoenlicheBemerkung = :persoenlicheBemerkung")})
public class Kontakt implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "kontaktid")
    private Integer kontaktid;
    @Column(name = "anrede")
    private String anrede;
    @Column(name = "vorname")
    private String vorname;
    @Column(name = "name")
    private String name;
    @Column(name = "titel")
    private String titel;
    @Column(name = "abschluss")
    private String abschluss;
    @Column(name = "position")
    private String position;
    @Column(name = "kontaktqualitaet")
    private String kontaktqualitaet;
    @Column(name = "letzterkontakt")
    @Temporal(TemporalType.DATE)
    private Date letzterkontakt;
    @Column(name = "email")
    private String email;
    @Column(name = "telefon_geschaeftl")
    private String telefonGeschaeftl;
    @Column(name = "persoenliche_bemerkung")
    private String persoenlicheBemerkung;
    @JoinColumn(name = "aktid", referencedColumnName = "aktid")
    @ManyToOne
    private Aktivitaet aktid;
    @JoinColumn(name = "mitid", referencedColumnName = "mitid")
    @ManyToOne
    private Mitarbeiter mitid;
    @JoinColumn(name = "firmaid", referencedColumnName = "firmaid")
    @ManyToOne
    private Firma firmaid;
    @JoinColumn(name = "kampid", referencedColumnName = "kampid")
    @ManyToOne
    private Kampagne kampid;

    public Kontakt() {
    }

    public Kontakt(Integer kontaktid) {
        this.kontaktid = kontaktid;
    }

    public Integer getKontaktid() {
        return kontaktid;
    }

    public void setKontaktid(Integer kontaktid) {
        this.kontaktid = kontaktid;
    }

    public String getAnrede() {
        return anrede;
    }

    public void setAnrede(String anrede) {
        this.anrede = anrede;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getAbschluss() {
        return abschluss;
    }

    public void setAbschluss(String abschluss) {
        this.abschluss = abschluss;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getKontaktqualitaet() {
        return kontaktqualitaet;
    }

    public void setKontaktqualitaet(String kontaktqualitaet) {
        this.kontaktqualitaet = kontaktqualitaet;
    }

    public Date getLetzterkontakt() {
        return letzterkontakt;
    }

    public void setLetzterkontakt(Date letzterkontakt) {
        this.letzterkontakt = letzterkontakt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefonGeschaeftl() {
        return telefonGeschaeftl;
    }

    public void setTelefonGeschaeftl(String telefonGeschaeftl) {
        this.telefonGeschaeftl = telefonGeschaeftl;
    }

    public String getPersoenlicheBemerkung() {
        return persoenlicheBemerkung;
    }

    public void setPersoenlicheBemerkung(String persoenlicheBemerkung) {
        this.persoenlicheBemerkung = persoenlicheBemerkung;
    }

    public Aktivitaet getAktid() {
        return aktid;
    }

    public void setAktid(Aktivitaet aktid) {
        this.aktid = aktid;
    }

    public Mitarbeiter getMitid() {
        return mitid;
    }

    public void setMitid(Mitarbeiter mitid) {
        this.mitid = mitid;
    }

    public Firma getFirmaid() {
        return firmaid;
    }

    public void setFirmaid(Firma firmaid) {
        this.firmaid = firmaid;
    }

    public Kampagne getKampid() {
        return kampid;
    }

    public void setKampid(Kampagne kampid) {
        this.kampid = kampid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kontaktid != null ? kontaktid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Kontakt)) {
            return false;
        }
        Kontakt other = (Kontakt) object;
        if ((this.kontaktid == null && other.kontaktid != null) || (this.kontaktid != null && !this.kontaktid.equals(other.kontaktid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Kontakt[ kontaktid=" + kontaktid + " ]";
    }
    
}
