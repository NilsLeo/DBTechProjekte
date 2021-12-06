package de.htwberlin.object;

public class Mautkategorie {
    int kategorie_id;
    int sskl_id;
    String kat_bezeichnung;
    String achszahl;
    float mautsatz_je_km;

    public int getKategorie_id() {
        return kategorie_id;
    }

    public void setKategorie_id(int kategorie_id) {
        this.kategorie_id = kategorie_id;
    }

    public int getSskl_id() {
        return sskl_id;
    }

    public void setSskl_id(int sskl_id) {
        this.sskl_id = sskl_id;
    }

    public String getKat_bezeichnung() {
        return kat_bezeichnung;
    }

    public void setKat_bezeichnung(String kat_bezeichnung) {
        this.kat_bezeichnung = kat_bezeichnung;
    }

    public String getAchszahl() {
        return achszahl;
    }

    public void setAchszahl(String achszahl) {
        this.achszahl = achszahl;
    }

    public float getMautsatz_je_km() {
        return mautsatz_je_km;
    }

    public void setMautsatz_je_km(float mautsatz_je_km) {
        this.mautsatz_je_km = mautsatz_je_km;
    }
}


