package de.htwberlin.mauterhebung;


import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.htwberlin.dao.BuchungDao;
import de.htwberlin.dao.BuchungDaoImpl;
import de.htwberlin.object.Buchung;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.htwberlin.exceptions.AlreadyCruisedException;
import de.htwberlin.exceptions.DataException;
import de.htwberlin.exceptions.InvalidVehicleDataException;
import de.htwberlin.exceptions.UnkownVehicleException;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;


import javax.script.ScriptException;

/**
 * Die Klasse realisiert den AusleiheService.
 *
 * @author Patrick Dohmeier
 */

public class MauterServiceImpl implements IMauterhebung {

	private static final Logger L = LoggerFactory.getLogger(MauterServiceImpl.class);
	private Connection connection;
	/**
	 * Speichert die uebergebene Datenbankverbindung in einer Instanzvariablen.
	 * @param connection die Verbuindung
	 * @author Ingo Classen
	 */
	@Override
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	private Connection getConnection() {
		if (connection == null) {
			throw new DataException("Connection not set");
		}
		return connection;
	}
	/***
	 * Die Methode realisiert einen Algorithmus, der die übermittelten
	 * Fahrzeugdaten mit der Datenbank auf Richtigkeit überprüft und für einen
	 * mautpflichtigen Streckenabschnitt die zu zahlende Maut für ein Fahrzeug
	 * im Automatischen Verfahren berechnet.
	 *
	 * Zuvor wird überprüft, ob das Fahrzeug registriert ist und über ein
	 * eingebautes Fahrzeuggerät verfügt und die übermittelten Daten des
	 * Kontrollsystems korrekt sind. Bei Fahrzeugen im Manuellen Verfahren wird
	 * darüberhinaus geprüft, ob es noch offene Buchungen für den Mautabschnitt
	 * gibt oder eine Doppelbefahrung aufgetreten ist. Besteht noch eine offene
	 * Buchung für den Mautabschnitt, so wird diese Buchung für das Fahrzeug auf
	 * abgeschlossen gesetzt.
	 *
	 * Sind die Daten des Fahrzeugs im Automatischen Verfahren korrekt, wird
	 * anhand der Mautkategorie (die sich aus der Achszahl und der
	 * Schadstoffklasse des Fahrzeugs zusammensetzt) und der Mautabschnittslänge
	 * die zu zahlende Maut berechnet, in der Mauterhebung gespeichert und
	 * letztendlich zurückgegeben.
	 *
	 *
	 * @param mautAbschnitt
	 *            - identifiziert einen mautpflichtigen Abschnitt
	 * @param achszahl
	 *            - identifiziert die Anzahl der Achsen für das Fahrzeug das
	 *            durch ein Kontrollsystem erfasst worden ist
	 * @param kennzeichen
	 *            - idenfiziert das amtliche Kennzeichen des Fahrzeugs das durch
	 *            das Kontrollsystem erfasst worden ist
	 * @throws UnkownVehicleException
	 *             - falls das Fahrzeug weder registriert ist, noch eine offene
	 *             Buchung vorliegt
	 * @throws InvalidVehicleDataException
	 *             - falls Daten des Kontrollsystems nicht mit den hinterlegten
	 *             Daten in der Datenbank übereinstimmt
	 * @throws AlreadyCruisedException
	 *             - falls eine Doppelbefahrung für Fahrzeuge im Manuellen
	 *             Verfahren vorliegt
	 * @return die berechnete Maut für das Fahrzeug im Automatischen Verfahren
	 *         auf dem Streckenabschnitt anhand der Fahrzeugdaten
	 */
	@Override
	public float berechneMaut(int mautAbschnitt, int achszahl, String kennzeichen)
			throws UnkownVehicleException, InvalidVehicleDataException, AlreadyCruisedException {

		// Prueft, ob das Fahrzeug registriert ist, sprich ob es "aktiv" ist, ein
		// Fahrzeuggeraet verbaut hat oder im manuellen Verfahren eine offene
		// Buchung des Fahrzeugs vorliegt
		if (!isVehicleRegistered(kennzeichen)) {
			throw new UnkownVehicleException("Das Fahrzeug ist nicht bekannt!-> Mautpreller");
		}
		if(isVehicleRegistered(kennzeichen)&&automaticProcedure(kennzeichen)) {
			if (!compareAxlesAutomatic(kennzeichen, achszahl)) {
				throw new InvalidVehicleDataException("die Achszahl AUtomatic");
			}
		}
		if(isVehicleRegistered(kennzeichen)&&!automaticProcedure(kennzeichen)) {
			if (!compareNoOfAxlesManuel(kennzeichen, achszahl)) {
				throw new InvalidVehicleDataException("die Achszahl Manuel");
			}
		}
		if(isVehicleRegistered(kennzeichen)&&!automaticProcedure(kennzeichen)){
			if(openManualProcedure(kennzeichen,mautAbschnitt)) {
				BuchungDao b_dao = new BuchungDaoImpl(getConnection());
				Buchung b = b_dao.findBuchung(b_dao.findBuchungId(kennzeichen,mautAbschnitt));
				b.setB_id(3);
				java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
				b.setBefahrungsdatum(date);
				b_dao.updateBuchung(b);
			} else{
				throw new AlreadyCruisedException("Es liegt eine Doppelbefahrung vor");
			}
		}
		DecimalFormat df = new DecimalFormat("0.00");
		float mautsatzJeKm = berechneMautsatzJeKm(kennzeichen);
		float mautAbschnittslänge = berechneMautAbschnittslänge(mautAbschnitt);
		float input = mautsatzJeKm * mautAbschnittslänge;
		float berechneteMaut = (float) (Math.round(input * 100.0) / 100.0);
		return berechneteMaut;
	}
	/**
	 * berechnet die mautabschnittslänge
	 * @param mautAbschnitt der Mautabschnitt
	 * @return mautAbschnittslänge die mautabschnittslänge in km
	 **/
	private float berechneMautAbschnittslänge(int mautAbschnitt) {
		float mautAbschnittslänge = 0;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			String queryString = "SELECT LAENGE FROM MAUTABSCHNITT WHERE ABSCHNITTS_ID = ?";
			preparedStatement = getConnection().prepareStatement(queryString);
			preparedStatement.setInt(1, mautAbschnitt);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				mautAbschnittslänge = resultSet.getInt("LAENGE") / 1000;
			}
			return mautAbschnittslänge;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	/**
	 * berechnet den Mautsatz je km
	 * @param kennzeichen der Mautabschnitt
	 * @return mautsatzJeKm der Mautsatz je Km in Euro
	 **/
	private float berechneMautsatzJeKm(String kennzeichen){
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int achszahl = 0;
		int sskl_id = 0;
		float mautsatzJeKm = 0;
		try {
			String queryString = "SELECT ACHSEN, SSKL_ID FROM FAHRZEUG WHERE KENNZEICHEN = ?";
			preparedStatement = getConnection().prepareStatement(queryString);
			preparedStatement.setString(1, kennzeichen);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				achszahl = resultSet.getInt("ACHSEN");
				sskl_id = resultSet.getInt("SSKL_ID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		try {
			String qString = "SELECT MAUTSATZ_JE_KM FROM MAUTKATEGORIE WHERE ACHSZAHL= ? AND SSKL_ID = ?";
			pst = getConnection().prepareStatement(qString);
			pst.setString(1, ">= " + String.valueOf(achszahl));
			pst.setInt(2, sskl_id);
			rs = pst.executeQuery();
			while (rs.next()) {
				mautsatzJeKm = rs.getFloat("MAUTSATZ_JE_KM")/100;
			}
			return mautsatzJeKm;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	/**
	 * Überprüft, ob es sich um ein manuelles Verfahren mit offenem Buchungsstatus handelt
	 * @param kennzeichen das Kennzeichen des Fahrzeugs
	 * @param mautAbschnitt
	 * @return true, wenn das Verfahren offen ist || false, wenn nicht
	 **/
	private boolean openManualProcedure(String kennzeichen,int mautAbschnitt){
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			String queryString = "SELECT bs.STATUS FROM BUCHUNGSTATUS bs,BUCHUNG b WHERE b.B_ID=bs.B_ID AND b.KENNZEICHEN=? AND b.ABSCHNITTS_ID=? AND bs.STATUS LIKE 'offen'";
			preparedStatement = getConnection().prepareStatement(queryString);
			preparedStatement.setString(1, kennzeichen);
			preparedStatement.setInt(2, mautAbschnitt);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()){
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	/*
	private int findbuchungsid(String kennzeichen,int mautAbschnitt){
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			String queryString = "SELECT b.B_ID FROM BUCHUNGSTATUS bs,BUCHUNG b WHERE b.B_ID=bs.B_ID AND b.KENNZEICHEN=? AND b.ABSCHNITTS_ID=? AND bs.STATUS LIKE 'offen'";
			preparedStatement = getConnection().prepareStatement(queryString);
			preparedStatement.setString(1, kennzeichen);
			preparedStatement.setInt(2, mautAbschnitt);
			resultSet = preparedStatement.executeQuery();
			return resultSet.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	 */
	/**
	 * Überprüft, ob es sich um ein automatisches Verfahren handelt
	 * @param kennzeichen das Kennzeichen des Fahrzeugs
	 * @return true, wenn es sich um ein automatisches Verfahren handelt || false, wenn nicht
	 **/
	private boolean automaticProcedure(String kennzeichen){
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			String queryString = "SELECT fg.FZG_ID AS ANZAHL FROM FAHRZEUG f,FAHRZEUGGERAT fg WHERE f.FZ_ID = fg.FZ_ID AND " +
					"KENNZEICHEN = ? AND fg.AUSBAUDATUM IS NULL";
			preparedStatement = getConnection().prepareStatement(queryString);
			preparedStatement.setString(1, kennzeichen);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	/**
	 * prueft, ob das Fahrzeug bereits registriert und aktiv ist oder eine
	 * manuelle offene Buchung für das Fahrzeug vorliegt
	 * @param kennzeichen das Kennzeichen des Fahrzeugs
	 * @return true, wenn das Fahrzeug registriert ist || false, wenn nicht
	 **/
	public boolean isVehicleRegistered(String kennzeichen) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			String queryString = "SELECT SUM( ANZAHL ) AS ANZAHL FROM (SELECT COUNT(F.KENNZEICHEN) AS ANZAHL FROM FAHRZEUG F"
					+ " JOIN FAHRZEUGGERAT FZG ON F.FZ_ID = FZG.FZ_ID AND F.ABMELDEDATUM IS NULL AND FZG.STATUS = 'active' "
					+ " AND  F.KENNZEICHEN =  ?  UNION ALL SELECT COUNT(KENNZEICHEN) AS ANZAHL FROM BUCHUNG WHERE"
					+ " KENNZEICHEN = ?  AND B_ID = 1)";
			preparedStatement = getConnection().prepareStatement(queryString);
			preparedStatement.setString(1, kennzeichen);
			preparedStatement.setString(2, kennzeichen);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return resultSet.getLong("ANZAHL") > 0;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}
	/**
	 * Überprüft beim manuellen Verfahren, ob das Fahrzeug mit der korrekten Achszahl unterwegs ist
	 * @param kennzeichen das Kennzeichen des Fahrzeugs
	 * @param achszahl
	 * @return true, wenn das Fahrzeug mit der korrekten Achszahl unterwegs ist || false, wenn nicht
	 **/
	private boolean compareNoOfAxlesManuel(String kennzeichen, int achszahl) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			String queryString = "SELECT ACHSZAHL FROM BUCHUNG " +
					"INNER JOIN MAUTKATEGORIE ON BUCHUNG.KATEGORIE_ID = MAUTKATEGORIE.KATEGORIE_ID WHERE KENNZEICHEN = ? AND B_ID =1";
			preparedStatement = getConnection().prepareStatement(queryString);
			preparedStatement.setString(1, kennzeichen);
			resultSet = preparedStatement.executeQuery();
			List<String> achszahlList = new ArrayList<String>();
			if(resultSet.next()) {
				String zahl=resultSet.getString("ACHSZAHL");
				if (zahl.contains(">=")) {
					if(achszahl>=Integer.parseInt(zahl.replaceAll("[^0-9]", "")))
						return true;
					else return false;
				}
				if(zahl.contains("=")){
					if(achszahl==Integer.parseInt(zahl.replaceAll("[^0-9]", "")))
						return true;
					else return false;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * Überprüft beim automatischen Verfahren, ob das Fahrzeug mit der korrekten Achszahl unterwegs ist
	 * @param kennzeichen das Kennzeichen des Fahrzeugs
	 * @param achszahl
	 * @return true, wenn das Fahrzeug mit der korrekten Achszahl unterwegs ist|| false wenn nicht
	 **/
	private boolean compareAxlesAutomatic(String kennzeichen, int achszahl) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			String queryString = "SELECT ACHSEN FROM FAHRZEUG WHERE KENNZEICHEN = ?";
			preparedStatement = getConnection().prepareStatement(queryString);
			preparedStatement.setString(1, kennzeichen);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next() && resultSet.getInt(1) == achszahl) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
