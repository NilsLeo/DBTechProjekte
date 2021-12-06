package de.htwberlin.dao;

import de.htwberlin.object.Buchung;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

	public Buchung findBuchung(String kennzeichen);

	/**
	 * erzeugt eine neue Buchung
	 *
	 * @param Buchungsnummer
	 * @return gibt eine neu Buchung zurück
	 */
	public Buchung createBuchung(int Buchungsnummer);
}
