package de.htwberlin.dao;

import de.htwberlin.object.Buchung;

/**
 * beschreibt die Schnittstelle zu einer Buchung
 *
 * @author Patrick Dohmeier
 **/

public interface BuchungDao {

	/**
	 * aktualisiert eine Buchung
	 *
	 * @param buchung das Objekt Buchung, welches gespeichert werden soll
	 **/
	public void updateBuchung(Buchung buchung);

	/**
	 * findet eine Buchung
	 *
	 * @param Buchungid
	 * @return das Objekt Buchung, welches gesucht wird
	 */
	public Buchung findBuchung(int Buchungid);

	/**
	 * erzeugt eine neue Buchung
	 *
	 * @param Buchungsnummer
	 * @return gibt eine neu Buchung zurück
	 */
	public Buchung createBuchung(int Buchungsnummer);
}
