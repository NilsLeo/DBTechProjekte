package jdbc_demo.rdg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VeranstaltungFinder {

	private Connection connection;

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public List<VeranstaltungGW> findByFachsemester(int fachsemester) {
		List<VeranstaltungGW> result = new ArrayList<VeranstaltungGW>();
		try (PreparedStatement s = connection
				.prepareStatement("SELECT * " + "FROM Veranstaltung WHERE Fachsemester = ?")) {
			s.setInt(1, fachsemester);
			ResultSet r = s.executeQuery();
			while (r.next()) {
				String bezeichnung = r.getString("Bezeichnung");
				int veranstNr = r.getInt("VeranstNr");
				VeranstaltungGW veranstaltung = new VeranstaltungGW(veranstNr, bezeichnung, r.getInt("Fachsemester"));
				result.add(veranstaltung);
			}
			return result;
		} catch (SQLException exp) {
			throw new RuntimeException();
		}

	}
}
