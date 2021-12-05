package jdbc_demo.dm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VeranstaltungMapper implements VeranstaltungDao {

	private Connection connection;

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	/* (non-Javadoc)
	 * @see jdbc_demo.dm.VeranstaltungDao#findByFachsemester(int)
	 */
	@Override
	public List<Veranstaltung> findByFachsemester(int fachsemester) {
		List<Veranstaltung> result = new ArrayList<Veranstaltung>();
		try (PreparedStatement s = connection
				.prepareStatement("SELECT * " + "FROM Veranstaltung WHERE Fachsemester = ?")) {
			s.setInt(1, fachsemester);
			ResultSet r = s.executeQuery();
			while (r.next()) {
				String bezeichnung = r.getString("Bezeichnung");
				int veranstNr = r.getInt("VeranstNr");
				Veranstaltung veranstaltung = new Veranstaltung(veranstNr, bezeichnung, r.getInt("Fachsemester"));
				result.add(veranstaltung);
			}
			return result;
		} catch (SQLException exp) {
			throw new DataAccessException();
		}
	}

}
