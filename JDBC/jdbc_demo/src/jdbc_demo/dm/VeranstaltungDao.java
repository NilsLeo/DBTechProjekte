package jdbc_demo.dm;

import java.util.List;

public interface VeranstaltungDao {

	List<Veranstaltung> findByFachsemester(int fachsemester);

	// insert/update/delete

}