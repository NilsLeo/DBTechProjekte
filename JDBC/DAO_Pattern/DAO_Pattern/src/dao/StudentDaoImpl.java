package dao;

import java.util.ArrayList;
import java.util.List;

import object.Student;

/**
 * Eine Superklasse, die das Interface StudentDao implementiert. Hier werden
 * alle im Interface definierten Methoden vererbt (siehe @Override). Diese
 * müssen natürlich erst umgesetzt werden. In der Methode getAllStudents()
 * könnte also auch ein Datennankzugriff erfolgen, der alle Studenten aus der
 * Datenbank holt und in einer Liste von Studentenobjekte List<Student>
 * zurückgibt.
 * 
 * Nicht vergessen: "Implementiert eine Klasse ein Interface (also StudentDao),
 * so muss sie alle Methoden des Interface überschreiben, da diese implizit als
 * abstrakt deklariert werden. "
 * 
 * @author Patrick Dohmeier
 * @version 1.0
 **/
public class StudentDaoImpl implements StudentDao {

	/**
	 * Diese Liste symbolisiert unsere Datenbank. Die Daten werden in diesem
	 * Fall nur im Hauptspeicher gehalten.
	 **/
	List<Student> students;

	/** initialisiert unser Daten (Studenten) **/
	public StudentDaoImpl() {
		students = new ArrayList<Student>();
		Student student1 = new Student("Robert", 0);
		Student student2 = new Student("John", 1);
		students.add(student1);
		students.add(student2);
	}

	@Override
	public void deleteStudent(Student student) {
		students.remove(student.getId());
		System.out.println("Student: Roll No " + student.getId()
				+ ", deleted from database");
	}

	// liefert die Liste von Studenten aus unserer symbolisierten Datenbank
	@Override
	public List<Student> getAllStudents() {
		return students;
	}

	@Override
	public Student getStudentById(int id) {
		return students.get(id);
	}

	@Override
	public void updateStudent(Student student) {
		students.get(student.getId()).setName(student.getName());
		System.out.println("Student: Roll No " + student.getId()
				+ ", updated in the database");
	}

	@Override
	public void saveStudent(Student student) {
		students.add(student);
		System.out.println("Student: Roll No " + student.getId()
				+ ", saved in the database");
	}

	
}