package jdbc_demo.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.htwberlin.utils.JdbcUtils;
import jdbc_demo.BelegungService;
import jdbc_demo.dm.BelegungMapper;
import jdbc_demo.dm.BelegungServiceDMImpl;
import jdbc_demo.dm.StudentMapper;
import jdbc_demo.dm.VeranstaltungMapper;
import jdbc_demo.rdg.BelegungServiceRDGImpl;
import jdbc_demo.rdg.VeranstaltungFinder;
import jdbc_demo.tdg.BelegungServiceTDGImpl;
import jdbc_demo.tdg.BelegungTG;
import jdbc_demo.tdg.StudentTG;
import jdbc_demo.tdg.VeranstaltungTG;

public class BelegungServiceTest {

	static private Connection connection;
	private BelegungService service;
	private int matrNr = 50105;

	@BeforeClass
	static public void initialize() {
		// 1. Laden des Treibers
//		JdbcUtils.loadDriver("oracle.jdbc.driver.OracleDriver");
//		connection = JdbcUtils.getConnectionViaDriverManager( // DB-URL,
//				"jdbc:oracle:thin:@oraceins.f4.htw-berlin.de:1521:oradb1", "u101", "p101"); // UserId, Password

		JdbcUtils.loadDriver("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		connection = JdbcUtils.getConnectionViaDriverManager( // DB-URL,
				"jdbc:sqlserver://sqlservervier.f4.htw-berlin.de", "u101", "p101"); // UserId, Password
//		jdbc:sqlserver://[serverName[\instanceName][:portNumber]][;property=value[;property=value]]
	}

	@Before
	public void setUp() {
//		this.service = new BelegungServiceImpl(connection);
//		setUpTDG();
//		setUpRDG();
		setUpDM();

		if (checkStudent(matrNr)) {
			deleteBelegungen(matrNr);
			deleteStudent(matrNr);
		}
	}

	private void setUpDM() {
		BelegungServiceDMImpl service = new BelegungServiceDMImpl();
		StudentMapper sm = new StudentMapper();
		sm.setConnection(connection);
		VeranstaltungMapper vm = new VeranstaltungMapper();
		vm.setConnection(connection);
		BelegungMapper bm = new BelegungMapper();
		bm.setConnection(connection);
//		service.setBm(bm);
//		service.setSm(sm);
//		service.setVm(vm);
		service.setbDao(bm);
		service.setsDao(sm);
		service.setvDao(vm);
		this.service = service;
	}

	private void setUpRDG() {
		BelegungServiceRDGImpl service = new BelegungServiceRDGImpl();
		service.setConnection(connection);
		VeranstaltungFinder vf = new VeranstaltungFinder();
		vf.setConnection(connection);
		service.setVf(vf);
		this.service = service;
	}

	private void setUpTDG() {
		BelegungServiceTDGImpl service = new BelegungServiceTDGImpl();
		StudentTG stg = new StudentTG();
		stg.setConnection(connection);
		VeranstaltungTG vtg = new VeranstaltungTG();
		vtg.setConnection(connection);
		BelegungTG btg = new BelegungTG();
		btg.setConnection(connection);
		service.setBtg(btg);
		service.setStg(stg);
		service.setVtg(vtg);
		this.service = service;
	}

	@AfterClass
	static public void uninitialize() {
		// Schließen der Verbindung
		JdbcUtils.closeConnectionQuietly(connection);
	}

	@Test
	public void testBelegeErstsemester() {
		// Arrange
		String name = "Kemper";
		String vorname = "Martin";
		// Act
		service.belegeErstsemester(matrNr, name, vorname);
		// Assert
		Assert.assertTrue(checkStudent(matrNr));
	}

	private boolean checkStudent(int matrNr) {
		try {
			try (PreparedStatement s = connection
					.prepareStatement("SELECT COUNT(MatrNr) AS Anzahl FROM Student WHERE MatrNr = ?")) {
				s.setInt(1, matrNr);
				ResultSet r = s.executeQuery();
				if (r.next() && r.getInt("Anzahl") == 1) {
					return true;
				} else {
					return false;
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}

	private void deleteStudent(int matrNr) {
		try {
			try (PreparedStatement s = connection.prepareStatement("DELETE FROM Student WHERE MatrNr = ?")) {
				s.setInt(1, matrNr);
				s.executeUpdate();
			}
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}

	private void deleteBelegungen(int matrNr) {
		try {
			try (PreparedStatement s = connection.prepareStatement("DELETE FROM Belegung WHERE MatrNr = ?")) {
				s.setInt(1, matrNr);
				s.executeUpdate();
			}
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}

}
