package de.htwberlin.object;

public class Mautabschnitt {
public int abschnitts_id;
public int laenge;
public String start_koordinate;
public String ziel_koordinate;
public String name;
public String abschnittstyp;

    public int getAbschnitts_id() {
        return abschnitts_id;
    }

    public void setAbschnitts_id(int abschnitts_id) {
        this.abschnitts_id = abschnitts_id;
    }

    public int getLaenge() {
        return laenge;
    }

    public void setLaenge(int laenge) {
        this.laenge = laenge;
    }

    public String getStart_koordinate() {
        return start_koordinate;
    }

    public void setStart_koordinate(String start_koordinate) {
        this.start_koordinate = start_koordinate;
    }

    public String getZiel_koordinate() {
        return ziel_koordinate;
    }

    public void setZiel_koordinate(String ziel_koordinate) {
        this.ziel_koordinate = ziel_koordinate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbschnittstyp() {
        return abschnittstyp;
    }

    public void setAbschnittstyp(String abschnittstyp) {
        this.abschnittstyp = abschnittstyp;
    }
}
