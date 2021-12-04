package de.htwberlin.object;

public class Nutzer {
public int nutzer_id;
public String status;
public String firmenname;
public String vorname;
public String nachname;
public String land;
public String strasse;
public int hausnummer;
public int postleitzahl;
public int rechnungsintervall;
public String rechnungszustellung;

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

    public String getFirmenname() {
        return firmenname;
    }

    public void setFirmenname(String firmenname) {
        this.firmenname = firmenname;
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

    public String getLand() {
        return land;
    }

    public void setLand(String land) {
        this.land = land;
    }

    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public int getHausnummer() {
        return hausnummer;
    }

    public void setHausnummer(int hausnummer) {
        this.hausnummer = hausnummer;
    }

    public int getPostleitzahl() {
        return postleitzahl;
    }

    public void setPostleitzahl(int postleitzahl) {
        this.postleitzahl = postleitzahl;
    }

    public int getRechnungsintervall() {
        return rechnungsintervall;
    }

    public void setRechnungsintervall(int rechnungsintervall) {
        this.rechnungsintervall = rechnungsintervall;
    }

    public String getRechnungszustellung() {
        return rechnungszustellung;
    }

    public void setRechnungszustellung(String rechnungszustellung) {
        this.rechnungszustellung = rechnungszustellung;
    }
}
