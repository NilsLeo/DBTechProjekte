package de.htwberlin.dao;

import de.htwberlin.object.Buchung;
import de.htwberlin.object.Buchungsstatus;

public interface BuchungStatusDao {

    /**
     * aktualisiert einen Buchungsstatus
     *
     * @param buchungsstatus der buchungsstatus, welches gespeichert werden soll
     **/
    public void updateBuchungStatus(Buchungsstatus buchungsstatus);

}
