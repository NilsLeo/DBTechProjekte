package jdbc_demo.dm;

import java.util.Date;

public class Belegung {

	private Student student;
	private Veranstaltung veranstaltung;
	private String status;
	private Date datum;

	public Belegung(Student student, Veranstaltung veranstaltung, String status, Date datum) {
		super();
		this.student = student;
		this.veranstaltung = veranstaltung;
		this.status = status;
		this.datum = datum;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Veranstaltung getVeranstaltung() {
		return veranstaltung;
	}

	public void setVeranstaltung(Veranstaltung veranstaltung) {
		this.veranstaltung = veranstaltung;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

}
