package jdbc_demo.mocktest;

import java.util.ArrayList;
import java.util.List;

import jdbc_demo.dm.Student;
import jdbc_demo.dm.Veranstaltung;
import jdbc_demo.dm.VeranstaltungDao;

public class VeranstaltungDaoMock implements VeranstaltungDao {

	private List<Veranstaltung> db = new ArrayList<Veranstaltung>();

	public void insert(Veranstaltung veranstaltung) {
		db.add(veranstaltung);
	}

	@Override
	public List<Veranstaltung> findByFachsemester(int fachsemester) {
		List<Veranstaltung> result = new ArrayList<Veranstaltung>();
		
		for (Veranstaltung v: db) {
			if (v.getFachsemester() == fachsemester) {
				result.add(v);
			}
		}
		return result;
	}

}
