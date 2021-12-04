package de.htwberlin.object;

import java.sql.Date;

public class Fahrzeug {
public int fz_id;
public int sskl_id;
public int nutzer_id;
public String kennzeichen;
public String fin;
public int achsen;
public int gewicht;
public Date anmeldedatum;
public Date abmeldedatum;
public String zulassungsland;

    public int getFz_id() {
        return fz_id;
    }

    public void setFz_id(int fz_id) {
        this.fz_id = fz_id;
    }

    public int getSskl_id() {
        return sskl_id;
    }

    public void setSskl_id(int sskl_id) {
        this.sskl_id = sskl_id;
    }

    public int getNutzer_id() {
        return nutzer_id;
    }

    public void setNutzer_id(int nutzer_id) {
        this.nutzer_id = nutzer_id;
    }

    public String getKennzeichen() {
        return kennzeichen;
    }

    public void setKennzeichen(String kennzeichen) {
        this.kennzeichen = kennzeichen;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    public int getAchsen() {
        return achsen;
    }

    public void setAchsen(int achsen) {
        this.achsen = achsen;
    }

    public int getGewicht() {
        return gewicht;
    }

    public void setGewicht(int gewicht) {
        this.gewicht = gewicht;
    }

    public Date getAnmeldedatum() {
        return anmeldedatum;
    }

    public void setAnmeldedatum(Date anmeldedatum) {
        this.anmeldedatum = anmeldedatum;
    }

    public Date getAbmeldedatum() {
        return abmeldedatum;
    }

    public void setAbmeldedatum(Date abmeldedatum) {
        this.abmeldedatum = abmeldedatum;
    }

    public String getZulassungsland() {
        return zulassungsland;
    }

    public void setZulassungsland(String zulassungsland) {
        this.zulassungsland = zulassungsland;
    }
}
