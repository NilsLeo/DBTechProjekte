package jdbc_demo.mocktest;

import java.util.ArrayList;
import java.util.List;

import jdbc_demo.dm.Student;
import jdbc_demo.dm.StudentDao;

public class StudentDaoMock implements StudentDao {

	private List<Student> db = new ArrayList<Student>();
	
	@Override
	public void insert(Student student) {
		db.add(student);
	}

	public int count() {
		return db.size();
	}
	
}
