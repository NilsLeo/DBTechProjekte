package jdbc_demo.dm;

import java.util.Date;
import java.util.List;

import jdbc_demo.BelegungService;

public class BelegungServiceDMImpl implements BelegungService {

	private StudentDao sDao;
	private VeranstaltungDao vDao;
	private BelegungDao bDao;

	public BelegungServiceDMImpl() {
		super();
	}

	public StudentDao getsDao() {
		return sDao;
	}

	public void setsDao(StudentDao sDao) {
		this.sDao = sDao;
	}

	public VeranstaltungDao getvDao() {
		return vDao;
	}

	public void setvDao(VeranstaltungDao vDao) {
		this.vDao = vDao;
	}

	public BelegungDao getbDao() {
		return bDao;
	}

	public void setbDao(BelegungDao bDao) {
		this.bDao = bDao;
	}

	/**
	 * belegeErstsemester in unstrukturierter Mischung
	 */
	@Override
	public void belegeErstsemester(int matrNr, String name, String vorname) {
		// 1. Student in Tabelle eintragen
		Student student = new Student(matrNr, name, vorname);
		sDao.insert(student);
		// 2. Veranstaltungen des 1. Semester suchen
		List<Veranstaltung> veranstaltungen = vDao.findByFachsemester(1);
		for (Veranstaltung veranstaltung : veranstaltungen) {
			// 3. Für diese Veranstaltungen Zulassungen eintragen
			Belegung belegung = new Belegung(student, veranstaltung, "ZU", new Date());
			bDao.insert(belegung);
		}
	}

}
