package de.htwberlin.object;

import java.sql.Date;

public class Zahlart {
public String z_id;
public int ztyp_id;
public int nutzer_id;
public String status;
public Date gueltig_von;
public Date gueltig_bis;
public String emmitent;
public int betrag;
public String name;
public String vorname;
public String nachname;
public int bin_code;

    public String getZ_id() {
        return z_id;
    }

    public void setZ_id(String z_id) {
        this.z_id = z_id;
    }

    public int getZtyp_id() {
        return ztyp_id;
    }

    public void setZtyp_id(int ztyp_id) {
        this.ztyp_id = ztyp_id;
    }

    public int getNutzer_id() {
        return nutzer_id;
    }

    public void setNutzer_id(int nutzer_id) {
        this.nutzer_id = nutzer_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getGueltig_von() {
        return gueltig_von;
    }

    public void setGueltig_von(Date gueltig_von) {
        this.gueltig_von = gueltig_von;
    }

    public Date getGueltig_bis() {
        return gueltig_bis;
    }

    public void setGueltig_bis(Date gueltig_bis) {
        this.gueltig_bis = gueltig_bis;
    }

    public String getEmmitent() {
        return emmitent;
    }

    public void setEmmitent(String emmitent) {
        this.emmitent = emmitent;
    }

    public int getBetrag() {
        return betrag;
    }

    public void setBetrag(int betrag) {
        this.betrag = betrag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getBin_code() {
        return bin_code;
    }

    public void setBin_code(int bin_code) {
        this.bin_code = bin_code;
    }
}
