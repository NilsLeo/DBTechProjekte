package main;

import dao.StudentDao;
import dao.StudentDaoImpl;
import object.Student;

/**
 * Eine Dienstklasse, die das DAO Pattern von Studenten zeigt.
 * 
 * @author Patrick Dohmeier
 * 
 * @version 1.0
 * 
 **/

public class DaoPatternDemo {



	/** startet das Programm **/
	public static void main(String[] args) {

		Student testStudent;
		
		/**
		 * Wenn eine beliebige Superklasse ein Interface implementiert (also in
		 * dem Fall die Klasse StudentDaoImpl()) und somit alle Datenelemente
		 * und Methoden erbt, so wird diese Implementierung auch an alle
		 * Subklassen dieser Vaterklasse weitergegeben. Ein einmal
		 * implementiertes Interface kommt also auch allen Subklassen zu gute.
		 **/
		StudentDao studentDao = new StudentDaoImpl();

		// gibt alle Studenten aus
		for (Student student : studentDao.getAllStudents()) {
			System.out.println("Student: [RollNo : " + student.getId()
					+ ", Name : " + student.getName() + " ]");
		}

		// get student "Robert" and set mame to "Michael" and update
		Student student = studentDao.getStudentById(0);
		student.setName("Michael");
		studentDao.updateStudent(student);

		// get the student
		studentDao.getStudentById(0);
		System.out.println("Student: [RollNo : " + student.getId()
				+ ", Name : " + student.getName() + " ]");

		// delete the student "John"
		studentDao.deleteStudent(studentDao.getStudentById(1));

		// print all students
		for (Student students : studentDao.getAllStudents()) {
			System.out.println("Student: [RollNo : " + students.getId()
					+ ", Name : " + students.getName() + " ]");
		}
		
		testStudent = new Student("Patrick",studentDao.getAllStudents().size()+1);
		//testStudent = new Student("Patrick",0);
		studentDao.saveStudent(testStudent);
		
		// print all students
				for (Student students : studentDao.getAllStudents()) {
					System.out.println("Student: [RollNo : " + students.getId()
							+ ", Name : " + students.getName() + " ]");
				}

	}
}