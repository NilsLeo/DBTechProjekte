package object;

/**
 * Eine Klasse, die einen Studenten repräsentiert. Ein Student hat einen Namen
 * und eine Id.
 * 
 * @author Patrick Dohmeier
 * @version 1.0
 **/
public class Student {

	/** Der Name des Studenten **/
	private String name;

	/** Die Id des Studenten **/
	private int id;

	/**
	 * setzt die Daten für den Studenten
	 * **/
	public Student(String name, int id) {
		this.name = name;
		this.id = id;
	}

	/**
	 * liefert den Namen des Studenten
	 * 
	 * @return name des Studenten
	 **/
	public String getName() {
		return name;
	}

	/**
	 * setzt den Namen des Studenten
	 * 
	 * @param name
	 *            des Studenten
	 * **/
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * liefert die Id des Studenten
	 * 
	 * @return id des Studenten
	 **/
	public int getId() {
		return id;
	}

	/**
	 * setzt die id des Studenten
	 * 
	 * @param id
	 *            des Studenten
	 **/
	public void setId(int id) {
		this.id = id;
	}
}
