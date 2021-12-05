package jdbc_demo.dm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BelegungMapper implements BelegungDao {

	private Connection connection;

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	// insert
	/* (non-Javadoc)
	 * @see jdbc_demo.dm.BelegungDao#insert(jdbc_demo.dm.Belegung)
	 */
	@Override
	public void insert(Belegung belegung) {
		try {
			try (PreparedStatement sb = connection
					.prepareStatement("INSERT INTO Belegung(MatrNr,VeranstNr,Status,Datum) VALUES (?,?,?,?)")) {
				sb.setInt(1, belegung.getStudent().getMatrNr());
				sb.setInt(2, belegung.getVeranstaltung().getVeranstNr());
				sb.setString(3, belegung.getStatus());
				sb.setDate(4, new java.sql.Date(belegung.getDatum().getTime()));
				sb.executeUpdate();
			}
		} catch (SQLException exp) {
			throw new DataAccessException();
		}
	}
	// update
	// delete

}
