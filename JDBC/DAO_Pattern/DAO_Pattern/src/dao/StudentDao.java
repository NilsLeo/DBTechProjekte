package dao;

import java.util.List;

import object.Student;

/**
 * Ein Interface, das alle Standardoperationen fuer einen Studenten definiert.
 * 
 * @author Patrick Dohmeier
 * @version 1.0
 **/
public interface StudentDao {

	/** Eine List mit allen Studenten **/
	public List<Student> getAllStudents();

	/** liefert ein Objekt: Student, des gesuchten Studenten **/
	public Student getStudentById(int id);

	/** aktualisiert einen Studenten in der DB **/
	public void updateStudent(Student student);

	/** loescht einen Studenten **/
	public void deleteStudent(Student student);

	/** speichert den Studenten **/
	public void saveStudent(Student student);
}