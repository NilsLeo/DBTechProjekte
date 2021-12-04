package de.htwberlin.object;

import java.sql.Date;

public class Mauterhebung {
public int maut_id;
public int abschnitts_id;
public int fzg_id;
public int kategorie_id;
public Date befahrungsdatum;
public int kosten;

    public int getMaut_id() {
        return maut_id;
    }

    public void setMaut_id(int maut_id) {
        this.maut_id = maut_id;
    }

    public int getAbschnitts_id() {
        return abschnitts_id;
    }

    public void setAbschnitts_id(int abschnitts_id) {
        this.abschnitts_id = abschnitts_id;
    }

    public int getFzg_id() {
        return fzg_id;
    }

    public void setFzg_id(int fzg_id) {
        this.fzg_id = fzg_id;
    }

    public int getKategorie_id() {
        return kategorie_id;
    }

    public void setKategorie_id(int kategorie_id) {
        this.kategorie_id = kategorie_id;
    }

    public Date getBefahrungsdatum() {
        return befahrungsdatum;
    }

    public void setBefahrungsdatum(Date befahrungsdatum) {
        this.befahrungsdatum = befahrungsdatum;
    }

    public int getKosten() {
        return kosten;
    }

    public void setKosten(int kosten) {
        this.kosten = kosten;
    }
}
