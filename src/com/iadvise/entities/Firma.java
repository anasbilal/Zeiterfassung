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
@Table(name = "firma")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Firma.findAll", query = "SELECT f FROM Firma f")
    , @NamedQuery(name = "Firma.findByFirmaid", query = "SELECT f FROM Firma f WHERE f.firmaid = :firmaid")
    , @NamedQuery(name = "Firma.findByAktid", query = "SELECT f FROM Firma f WHERE f.aktid = :aktid")
    , @NamedQuery(name = "Firma.findByRechtsform", query = "SELECT f FROM Firma f WHERE f.rechtsform = :rechtsform")
    , @NamedQuery(name = "Firma.findByBoersennotierung", query = "SELECT f FROM Firma f WHERE f.boersennotierung = :boersennotierung")
    , @NamedQuery(name = "Firma.findByFondsvolumen", query = "SELECT f FROM Firma f WHERE f.fondsvolumen = :fondsvolumen")
    , @NamedQuery(name = "Firma.findByLetzterkontakt", query = "SELECT f FROM Firma f WHERE f.letzterkontakt = :letzterkontakt")
    , @NamedQuery(name = "Firma.findByEmail", query = "SELECT f FROM Firma f WHERE f.email = :email")
    , @NamedQuery(name = "Firma.findByTelefongeschaeftl", query = "SELECT f FROM Firma f WHERE f.telefongeschaeftl = :telefongeschaeftl")
    , @NamedQuery(name = "Firma.findByTelefonzentrale", query = "SELECT f FROM Firma f WHERE f.telefonzentrale = :telefonzentrale")
    , @NamedQuery(name = "Firma.findByFax", query = "SELECT f FROM Firma f WHERE f.fax = :fax")
    , @NamedQuery(name = "Firma.findByZusatzadresse", query = "SELECT f FROM Firma f WHERE f.zusatzadresse = :zusatzadresse")
    , @NamedQuery(name = "Firma.findByStrasse", query = "SELECT f FROM Firma f WHERE f.strasse = :strasse")
    , @NamedQuery(name = "Firma.findByHausnummer", query = "SELECT f FROM Firma f WHERE f.hausnummer = :hausnummer")
    , @NamedQuery(name = "Firma.findByPlz", query = "SELECT f FROM Firma f WHERE f.plz = :plz")
    , @NamedQuery(name = "Firma.findByOrt", query = "SELECT f FROM Firma f WHERE f.ort = :ort")
    , @NamedQuery(name = "Firma.findByLand", query = "SELECT f FROM Firma f WHERE f.land = :land")
    , @NamedQuery(name = "Firma.findByUmsatzMitIadvise", query = "SELECT f FROM Firma f WHERE f.umsatzMitIadvise = :umsatzMitIadvise")
    , @NamedQuery(name = "Firma.findByKontaktqualitaet", query = "SELECT f FROM Firma f WHERE f.kontaktqualitaet = :kontaktqualitaet")})
public class Firma implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "firmaid")
    private Integer firmaid;
    @Basic(optional = false)
    @Column(name = "aktid")
    private int aktid;
    @Column(name = "firmaname")
    private String firmaname;
    @Column(name = "rechtsform")
    private String rechtsform;
    @Column(name = "boersennotierung")
    private String boersennotierung;
    @Column(name = "fondsvolumen")
    private Integer fondsvolumen;
    @Column(name = "letzterkontakt")
    @Temporal(TemporalType.DATE)
    private Date letzterkontakt;
    @Column(name = "email")
    private String email;
    @Column(name = "telefongeschaeftl")
    private String telefongeschaeftl;
    @Column(name = "telefonzentrale")
    private String telefonzentrale;
    @Column(name = "fax")
    private String fax;
    @Column(name = "zusatzadresse")
    private String zusatzadresse;
    @Column(name = "strasse")
    private String strasse;
    @Column(name = "hausnummer")
    private Integer hausnummer;
    @Column(name = "plz")
    private String plz;
    @Column(name = "ort")
    private String ort;
    @Column(name = "land")
    private String land;
    @Column(name = "umsatz_mit_iadvise")
    private Long umsatzMitIadvise;
    @Column(name = "kontaktqualitaet")
    private String kontaktqualitaet;
    @OneToMany(mappedBy = "firmaid")
    private Collection<Projekt> projektCollection;
    @OneToMany(mappedBy = "firmaid")
    private Collection<Kontakt> kontaktCollection;
    @JoinColumn(name = "brid", referencedColumnName = "brID")
    @ManyToOne(optional = false)
    private Branche brid;

    public Firma() {
    }

    public Firma(Integer firmaid) {
        this.firmaid = firmaid;
    }

    public Firma(Integer firmaid, int aktid) {
        this.firmaid = firmaid;
        this.aktid = aktid;
    }

    public Integer getFirmaid() {
        return firmaid;
    }

    public void setFirmaid(Integer firmaid) {
        this.firmaid = firmaid;
    }
    
     public String getFirmaname() {
		return firmaname;
	}

	public void setFirmaname(String firmaname) {
		this.firmaname = firmaname;
	}

	public int getAktid() {
        return aktid;
    }

    public void setAktid(int aktid) {
        this.aktid = aktid;
    }

    public String getRechtsform() {
        return rechtsform;
    }

    public void setRechtsform(String rechtsform) {
        this.rechtsform = rechtsform;
    }

    public String getBoersennotierung() {
        return boersennotierung;
    }

    public void setBoersennotierung(String boersennotierung) {
        this.boersennotierung = boersennotierung;
    }

    public Integer getFondsvolumen() {
        return fondsvolumen;
    }

    public void setFondsvolumen(Integer fondsvolumen) {
        this.fondsvolumen = fondsvolumen;
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

    public String getTelefongeschaeftl() {
        return telefongeschaeftl;
    }

    public void setTelefongeschaeftl(String telefongeschaeftl) {
        this.telefongeschaeftl = telefongeschaeftl;
    }

    public String getTelefonzentrale() {
        return telefonzentrale;
    }

    public void setTelefonzentrale(String telefonzentrale) {
        this.telefonzentrale = telefonzentrale;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getZusatzadresse() {
        return zusatzadresse;
    }

    public void setZusatzadresse(String zusatzadresse) {
        this.zusatzadresse = zusatzadresse;
    }

    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public Integer getHausnummer() {
        return hausnummer;
    }

    public void setHausnummer(Integer hausnummer) {
        this.hausnummer = hausnummer;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public String getLand() {
        return land;
    }

    public void setLand(String land) {
        this.land = land;
    }

    public Long getUmsatzMitIadvise() {
        return umsatzMitIadvise;
    }

    public void setUmsatzMitIadvise(Long umsatzMitIadvise) {
        this.umsatzMitIadvise = umsatzMitIadvise;
    }

    public String getKontaktqualitaet() {
        return kontaktqualitaet;
    }

    public void setKontaktqualitaet(String kontaktqualitaet) {
        this.kontaktqualitaet = kontaktqualitaet;
    }

    @XmlTransient
    public Collection<Projekt> getProjektCollection() {
        return projektCollection;
    }

    public void setProjektCollection(Collection<Projekt> projektCollection) {
        this.projektCollection = projektCollection;
    }

    @XmlTransient
    public Collection<Kontakt> getKontaktCollection() {
        return kontaktCollection;
    }

    public void setKontaktCollection(Collection<Kontakt> kontaktCollection) {
        this.kontaktCollection = kontaktCollection;
    }

    public Branche getBrid() {
        return brid;
    }

    public void setBrid(Branche brid) {
        this.brid = brid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (firmaid != null ? firmaid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Firma)) {
            return false;
        }
        Firma other = (Firma) object;
        if ((this.firmaid == null && other.firmaid != null) || (this.firmaid != null && !this.firmaid.equals(other.firmaid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Firma[ firmaid=" + firmaid + " ]";
    }
    
}
