package jdbc_demo.mocktest;

import java.util.ArrayList;
import java.util.List;

import jdbc_demo.dm.Belegung;
import jdbc_demo.dm.BelegungDao;

public class BelegungDaoMock implements BelegungDao {

	private List<Belegung> db = new ArrayList<Belegung>();

	@Override
	public void insert(Belegung belegung) {
		db.add(belegung);
	}

	public int count() {
		return db.size();
	}
	
}
