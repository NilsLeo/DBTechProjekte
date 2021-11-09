package jdbc_demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BelegungServiceImpl implements BelegungService {

	private Connection connection;

	public BelegungServiceImpl(Connection connection) {
		super();
		this.connection = connection;
	}

	/**
	 * belegeErstsemester in unstrukturierter Mischung
	 */
	@Override
	public void belegeErstsemester(int matrNr, String name, String vorname) {
		try {
			// 1. Student in Tabelle eintragen
			try (PreparedStatement s = connection
					.prepareStatement("INSERT INTO Student(MatrNr, Name, Vorname) " + "VALUES (?,?,?)")) {
				s.setInt(1, matrNr);
				s.setString(2, name);
				s.setString(3, vorname);
				s.executeUpdate();
			}
			// 2. Veranstaltungen des 1. Semester suchen
			try (PreparedStatement s = connection
					.prepareStatement("SELECT * " + "FROM Veranstaltung WHERE Fachsemester = 1")) {
				ResultSet r = s.executeQuery();
				while (r.next()) {
					int veranstNr = r.getInt("VeranstNr");
					// 3. Für diese Veranstaltungen Zulassungen eintragen
					try (PreparedStatement sb = connection
							.prepareStatement("INSERT INTO Belegung(MatrNr,VeranstNr,Status,Datum) VALUES (?,?,'ZU',GETDATE())")) {
						sb.setInt(1, matrNr);
						sb.setInt(2, veranstNr);
						sb.executeUpdate();
					}
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
