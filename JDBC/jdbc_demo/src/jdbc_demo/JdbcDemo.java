package jdbc_demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.htwberlin.utils.JdbcUtils;

public class JdbcDemo {

	private static final Log LOGGER = LogFactory.getLog(JdbcDemo.class);

	public static void main(String[] args) {
		LOGGER.debug("main gestartet");
		// 1. Laden des Treibers
		JdbcUtils.loadDriver("oracle.jdbc.driver.OracleDriver");
//		new JdbcDemo().printAllStudents();
		new JdbcDemo().printStudent(50103);
		LOGGER.debug("main beendet");
	}

	public void printAllStudents() {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			// 2. Erzeugen der Verbindung
			connection = JdbcUtils.getConnectionViaDriverManager( // DB-URL,
					"jdbc:oracle:thin:@oraceins.f4.htw-berlin.de:1521:oradb1", "u101", "p101"); // UserId, Password
			// 3. Erzeugen eine Datenbankanweisung
			statement = connection.createStatement();
			// 4. Ausführen der Datenbankanweisung und
			// Erzeugung der Ergebnismenge
			resultSet = statement.executeQuery("SELECT * FROM Student");
			// 5. Iteration durch die Ergebnismenge
			while (resultSet.next()) {
				// Typgerechter Zugriff mit getInt(), getString(), usw.
				System.out.println(resultSet.getInt("MatrNr"));
				System.out.println(resultSet.getString("Name"));
				System.out.println(resultSet.getString("Vorname"));
//				System.out.println(resultSet.getDate("Imma_Datum"));
				System.out.println(resultSet.getString("Studiengang"));
				System.out.println("\n");
			}
		} catch (SQLException e) {
			// Ausnahmebehandlung
			e.printStackTrace();
		} finally {
			// 6. Ganz wichtig: Freigeben der Ressourcen. Wird wegen
			// finally auf jeden Fall durchgeführt, d. h. auch beim
			// Auftreten von Ausnahmen
			JdbcUtils.closeResultSetQuietly(resultSet);
			JdbcUtils.closeStatementQuietly(statement);
			JdbcUtils.closeConnectionQuietly(connection);
		}
	}

	public void printAllStudentsNew() {
		// 2. Erzeugen der Verbindung
		try (Connection connection = JdbcUtils.getConnectionViaDriverManager( // DB-URL,
				"jdbc:oracle:thin:@oraceins.f4.htw-berlin.de:1521:oradb1", "u101", "p101")) {
			// 3. Erzeugen eine Datenbankanweisung
			try (Statement statement = connection.createStatement()) {
				// 4. Ausführen der Datenbankanweisung und
				// Erzeugung der Ergebnismenge
				ResultSet resultSet = statement.executeQuery("SELECT * FROM Student");
				// 5. Iteration durch die Ergebnismenge
				while (resultSet.next()) {
					// Typgerechter Zugriff mit getInt(), getString(), usw.
					System.out.println(resultSet.getInt("MatrNr"));
					System.out.println(resultSet.getString("Name"));
					System.out.println(resultSet.getString("Vorname"));
//				System.out.println(resultSet.getDate("Imma_Datum"));
					System.out.println(resultSet.getString("Studiengang"));
					System.out.println("\n");
				}
			}
		} catch (SQLException e) {
			// Ausnahmebehandlung
			e.printStackTrace();
		}
	}

	void printStudent(int matrNr) {
		// 2. Erzeugen der Verbindung
		try (Connection connection = JdbcUtils.getConnectionViaDriverManager( // DB-URL,
				"jdbc:oracle:thin:@oraceins.f4.htw-berlin.de:1521:oradb1", "u101", "p101")) {
			try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM Student WHERE MatrNr= ?")) {
				// Setzen des ersten Parameters auf den Wert 50101
				statement.setInt(1, matrNr);
				ResultSet resultSet = statement.executeQuery();
				if (resultSet.next()) {
					// Typgerechter Zugriff mit getInt(), getString(), usw.
					System.out.println(resultSet.getInt("MatrNr"));
					System.out.println(resultSet.getString("Name"));
					System.out.println(resultSet.getString("Vorname"));
					System.out.println(resultSet.getString("Studiengang"));
					System.out.println("\n");
				}
			}
		} catch (SQLException e) {
			// Ausnahmebehandlung
			e.printStackTrace();
		}
	}

}
