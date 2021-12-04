package de.htwberlin.object;

import java.sql.Date;

public class Rechnung {
public int r_id;
public int status_id;
public int nutzer_id;
public Date buchungsdatum;
public int summe;

    public int getR_id() {
        return r_id;
    }

    public void setR_id(int r_id) {
        this.r_id = r_id;
    }

    public int getStatus_id() {
        return status_id;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }

    public int getNutzer_id() {
        return nutzer_id;
    }

    public void setNutzer_id(int nutzer_id) {
        this.nutzer_id = nutzer_id;
    }

    public Date getBuchungsdatum() {
        return buchungsdatum;
    }

    public void setBuchungsdatum(Date buchungsdatum) {
        this.buchungsdatum = buchungsdatum;
    }

    public int getSumme() {
        return summe;
    }

    public void setSumme(int summe) {
        this.summe = summe;
    }
}
