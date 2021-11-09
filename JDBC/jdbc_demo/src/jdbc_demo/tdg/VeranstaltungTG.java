package jdbc_demo.tdg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VeranstaltungTG {

	private Connection connection;

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	// insert/update/delete

	public ResultSet findByFachsemester(int fachsemester) {
		try {
			PreparedStatement s = connection
					.prepareStatement("SELECT * " + "FROM Veranstaltung WHERE Fachsemester = ?");
			s.setInt(1, fachsemester);
			ResultSet r = s.executeQuery();
			return r;
		} catch (SQLException exp) {
			throw new RuntimeException();
		}

	}
}
