package jdbc_demo.dm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StudentMapper implements StudentDao {

	private Connection connection;

	public void setConnection(Connection connection) {
		this.connection = connection;
	}


	// insert
	public void insert(Student student) {
		try {
			try (PreparedStatement s = connection
					.prepareStatement("INSERT INTO Student(MatrNr, Name, Vorname) " + "VALUES (?,?,?)")) {
				s.setInt(1, student.getMatrNr());
				s.setString(2, student.getName());
				s.setString(3, student.getVorname());
				s.executeUpdate();
			}
		} catch (SQLException exp) {
			throw new DataAccessException();
		}
	}
	// update
	// delete

}
