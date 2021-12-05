package de.htwberlin.mauterhebung;


import java.sql.*;
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

	@Override
	public float berechneMaut(int mautAbschnitt, int achszahl, String kennzeichen)
			throws UnkownVehicleException, InvalidVehicleDataException, AlreadyCruisedException {

		// Prueft, ob das Fahrzeug registriert ist, sprich ob es "aktiv" ist, ein
		// Fahrzeuggeraet verbaut hat oder im manuellen Verfahren eine offene
		// Buchung des Fahrzeugs vorliegt
		if (!isVehicleRegistered(kennzeichen)) {
			throw new UnkownVehicleException("Das Fahrzeug ist nicht bekannt!-> Mautpreller");
		}
		if (!compareAxles(kennzeichen, achszahl)) {
			throw new InvalidVehicleDataException("die Achszahl stimmt nicht überein");
		}
		if(manualProcedure(kennzeichen) == true){
			if(alreadyCruised(kennzeichen)){
				throw new AlreadyCruisedException("Es liegt eine Doppelbefahrung vor");
			}
			if(openManualProcedure(kennzeichen) == true) {
				BuchungDao b_dao = new BuchungDaoImpl(getConnection());
				Buchung b = b_dao.findBuchung(1111);
				b.setB_id(3);
				java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
				b.setBefahrungsdatum(date);
				b_dao.updateBuchung(b);
			}
		}
		//Prueft, ob die Achszahl über die gebuchte Mautkategorie ermittelbar ist


		float berechneteMaut = 0;
		return berechneteMaut;
	}

	private boolean openManualProcedure(String kennzeichen){
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			String queryString = "SELECT STATUS FROM BUCHUNG "
					+ "INNER JOIN BUCHUNGSTATUS ON BUCHUNGSTATUS.B_ID = BUCHUNG.B_ID "
					+ "INNER JOIN MAUTABSCHNITT ON BUCHUNG.ABSCHNITTS_ID = MAUTABSCHNITT.ABSCHNITTS_ID WHERE KENNZEICHEN = ?";
			preparedStatement = getConnection().prepareStatement(queryString);
			preparedStatement.setString(1, kennzeichen);
			resultSet = preparedStatement.executeQuery();
			List<String> statusList = new ArrayList<String>();
				while (resultSet.next()){
					statusList.add(resultSet.getString("STATUS"));
				}
				if(statusList.contains(String.valueOf("offen"))){
					return true;
				} else {
					return false;
				}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private boolean manualProcedure(String kennzeichen){
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			String queryString = "SELECT FZG_ID FROM FAHRZEUG INNER JOIN FAHRZEUGGERAT ON FAHRZEUG.FZ_ID = FAHRZEUGGERAT.FZ_ID WHERE KENNZEICHEN = ?";
			preparedStatement = getConnection().prepareStatement(queryString);
			preparedStatement.setString(1, kennzeichen);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return false;
			} else {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	private boolean alreadyCruised(String kennzeichen) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			String queryString = "SELECT * FROM BUCHUNG WHERE KENNZEICHEN = ? AND B_ID=3";
			preparedStatement = getConnection().prepareStatement(queryString);
			preparedStatement.setString(1, kennzeichen);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	private boolean compareAxles(String kennzeichen, int achszahl) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			String queryString = "SELECT ACHSZAHL FROM BUCHUNG " +
					"INNER JOIN MAUTKATEGORIE ON BUCHUNG.KATEGORIE_ID = MAUTKATEGORIE.KATEGORIE_ID WHERE KENNZEICHEN = ?";
			preparedStatement = getConnection().prepareStatement(queryString);
			preparedStatement.setString(1, kennzeichen);
			resultSet = preparedStatement.executeQuery();
			List<String> achszahlList = new ArrayList<String>();
			if (resultSet.next()) {
				achszahlList.add(resultSet.getString("STATUS"));
				for (int i = 0; i<achszahlList.size();i++){
					if(achszahlList)
				}

				ScriptEngineManager mgr = new ScriptEngineManager();
				ScriptEngine engine = mgr.getEngineByName("JavaScript");
				String expression = String.valueOf(achszahl) + resultSet.getString("ACHSZAHL");
				return Boolean.valueOf();
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	/*
	private boolean compareAxles(String kennzeichen, int achszahl) {
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

	 */

	/**
	 * prueft, ob das Fahrzeug bereits registriert und aktiv ist oder eine
	 * manuelle offene Buchung fuer das Fahrzeug vorliegt
	 *
	 * @param kennzeichen
	 *            , das Kennzeichen des Fahrzeugs
	 * @return true wenn das Fahrzeug registiert ist || false wenn nicht
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

}
