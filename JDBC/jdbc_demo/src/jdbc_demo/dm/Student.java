package jdbc_demo.dm;

public class Student {

	private int matrNr;
	private String name;
	private String vorname;

	public Student(int matrNr, String name, String vorname) {
		super();
		this.matrNr = matrNr;
		this.name = name;
		this.vorname = vorname;
	}

	public int getMatrNr() {
		return matrNr;
	}

	public void setMatrNr(int matrNr) {
		this.matrNr = matrNr;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

}
