package de.htwberlin.object;


import java.sql.Date;

public class Fahrzeuggerat {
    public int fzg_id;
    public int fz_id;
    public String status;
    public String typ;
    public Date einbaudatum;
    public Date ausbaudatum;

    public int getFzg_id() {
        return fzg_id;
    }

    public void setFzg_id(int fzg_id) {
        this.fzg_id = fzg_id;
    }

    public int getFz_id() {
        return fz_id;
    }

    public void setFz_id(int fz_id) {
        this.fz_id = fz_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public Date getEinbaudatum() {
        return einbaudatum;
    }

    public void setEinbaudatum(Date einbaudatum) {
        this.einbaudatum = einbaudatum;
    }

    public Date getAusbaudatum() {
        return ausbaudatum;
    }

    public void setAusbaudatum(Date ausbaudatum) {
        this.ausbaudatum = ausbaudatum;
    }
}
