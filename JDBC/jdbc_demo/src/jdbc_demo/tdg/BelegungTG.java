package jdbc_demo.tdg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class BelegungTG {
	private Connection connection;

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	// insert
	public void insert(int matrNr, int veranstNr, String status, Date datum) {
		try {
			try (PreparedStatement sb = connection.prepareStatement(
					"INSERT INTO Belegung(MatrNr,VeranstNr,Status,Datum) VALUES (?,?,?,?)")) {
				sb.setInt(1, matrNr);
				sb.setInt(2, veranstNr);
				sb.setString(3, status);
				sb.setDate(4, new java.sql.Date(datum.getTime()));
				sb.executeUpdate();
			}
		} catch (SQLException exp) {
			throw new RuntimeException();
		}
	}
	// update
	// delete
	// find-Methoden

}
