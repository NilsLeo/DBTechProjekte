package jdbc_demo.tdg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StudentTG {

	private Connection connection;

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	// insert
	public void insert(int matrNr, String name, String vorname) {
		try {
			try (PreparedStatement s = connection
					.prepareStatement("INSERT INTO Student(MatrNr, Name, Vorname) " + "VALUES (?,?,?)")) {
				s.setInt(1, matrNr);
				s.setString(2, name);
				s.setString(3, vorname);
				s.executeUpdate();
			}
		} catch (SQLException exp) {
			throw new RuntimeException();
		}
	}
	// update
	// delete
	// find-Methoden

}
