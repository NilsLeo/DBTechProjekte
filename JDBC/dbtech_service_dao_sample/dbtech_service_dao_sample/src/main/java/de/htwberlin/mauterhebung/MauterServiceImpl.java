package de.htwberlin.mauterhebung;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.htwberlin.dao.BuchungDao;
import de.htwberlin.dao.BuchungDaoImpl;
import de.htwberlin.exceptions.AlreadyCruisedException;
import de.htwberlin.exceptions.DataException;
import de.htwberlin.exceptions.InvalidVehicleDataException;
import de.htwberlin.exceptions.UnkownVehicleException;
import de.htwberlin.object.Buchung;

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
		
		
		BuchungDao b_dao = new BuchungDaoImpl(getConnection());
		Buchung b = b_dao.findBuchung(1111);
		b.setB_id(3);
		b_dao.updateBuchung(b);

		return 0;
	}

	
	/**
	 * @author patrick dohmeier
	 * @param kennzeichen - das kennzeichen das geprueft werden soll
	 * @return true = bekannt || false nicht bekannt
	 * 
	 **/

	public boolean isVehicleRegistered(String kennzeichen) {

		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean er = false;
		String query = "select sum(Anzahl) as Anzahl from(\r\n"
				+ "SELECT count(fz.kennzeichen) as Anzahl FROM fahrzeug fz JOIN fahrzeuggerat   fzg ON fz.fz_id = fzg.fz_id WHERE fz.kennzeichen = ? AND fz.abmeldedatum IS NULL AND fzg.status = 'active'\r\n"
				+ "union all\r\n"
				+ "SELECT count(b.kennzeichen) as Anzahl FROM buchung  b JOIN buchungstatus  bs ON b.b_id = bs.b_id WHERE b.kennzeichen = ?  and b.b_id = 1\r\n"
				+ ")";

		try {
			ps = getConnection().prepareStatement(query);
			ps.setString(1, kennzeichen);
			ps.setString(2, kennzeichen);
			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getInt("Anzahl") > 0;
			}

		} catch (SQLException e) {

		}

		return er;
	}

}
