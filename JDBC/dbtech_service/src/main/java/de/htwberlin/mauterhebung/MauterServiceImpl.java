package de.htwberlin.mauterhebung;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.htwberlin.exceptions.AlreadyCruisedException;
import de.htwberlin.exceptions.DataException;
import de.htwberlin.exceptions.InvalidVehicleDataException;
import de.htwberlin.exceptions.UnkownVehicleException;

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
		//Prueft, ob die Achszahl über die gebuchte Mautkategorie ermittelbar ist
		if (!correctNumberOfAxles(achszahl)) {
			throw new AlreadyCruisedException("die Achszahl ist nicht über die gebuchte Mautkategorie ermittlebar");
		}


		float berechneteMaut = 0;
		return berechneteMaut;
	}
	/**
	 * Prueft, ob die Achszahl über die gebuchte Mautkategorie ermittelbar ist
	 * @param achszahl, die Achszahl der gebuchten Mautkategorie
	 * @return true wenn das Fahrzeug registiert ist || false wenn nicht
	 **/
	private boolean correctNumberOfAxles(int achszahl) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			String queryString = "SELECT ";
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

