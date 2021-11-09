package jdbc_demo.tdg;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import jdbc_demo.BelegungService;

public class BelegungServiceTDGImpl implements BelegungService {

	private StudentTG stg;
	private VeranstaltungTG vtg;
	private BelegungTG btg;

	public BelegungServiceTDGImpl() {
		super();
	}

	public void setStg(StudentTG stg) {
		this.stg = stg;
	}

	public void setVtg(VeranstaltungTG vtg) {
		this.vtg = vtg;
	}

	public void setBtg(BelegungTG btg) {
		this.btg = btg;
	}

	/**
	 * belegeErstsemester in unstrukturierter Mischung
	 */
	@Override
	public void belegeErstsemester(int matrNr, String name, String vorname) {
		try {
			// 1. Student in Tabelle eintragen
			stg.insert(matrNr, name, vorname);
			// 2. Veranstaltungen des 1. Semester suchen
			try (ResultSet r = vtg.findByFachsemester(1)) {
				while (r.next()) {
					int veranstNr = r.getInt("VeranstNr");
					// 3. Für diese Veranstaltungen Zulassungen eintragen
					btg.insert(matrNr, veranstNr, "ZU", new Date());
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
