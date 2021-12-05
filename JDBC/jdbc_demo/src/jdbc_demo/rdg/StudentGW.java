package jdbc_demo.rdg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StudentGW {

	private int matrNr;
	private String name;
	private String vorname;

	public StudentGW(int matrNr, String name, String vorname) {
		super();
		this.matrNr = matrNr;
		this.name = name;
		this.vorname = vorname;
	}

	public int getMatrNr() {
		return matrNr;
	}

	public void setMatrNr(int matrNr) {
		this.matrNr = matrNr;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	// insert
	public void insert(Connection connection) {
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
}
