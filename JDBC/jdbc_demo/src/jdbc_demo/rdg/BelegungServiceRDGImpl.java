package jdbc_demo.rdg;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import jdbc_demo.BelegungService;

public class BelegungServiceRDGImpl implements BelegungService {

	private Connection connection;
	private VeranstaltungFinder vf;

	public BelegungServiceRDGImpl() {
		super();
	}

	public void setVf(VeranstaltungFinder vf) {
		this.vf = vf;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	/**
	 * belegeErstsemester in unstrukturierter Mischung
	 */
	@Override
	public void belegeErstsemester(int matrNr, String name, String vorname) {
		// 1. Student in Tabelle eintragen
		StudentGW student = new StudentGW(matrNr, name, vorname);
		student.insert(connection);
		// 2. Veranstaltungen des 1. Semester suchen
		List<VeranstaltungGW> veranstaltungen = vf.findByFachsemester(1);
		for (VeranstaltungGW veranstaltung : veranstaltungen) {
			int veranstNr = veranstaltung.getVeranstNr();
			// 3. Für diese Veranstaltungen Zulassungen eintragen
			BelegungGW belegung = new BelegungGW(matrNr, veranstNr, "ZU", new Date());
			belegung.insert(connection);
		}
	}

}
