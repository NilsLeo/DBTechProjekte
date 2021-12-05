package jdbc_demo.rdg;

public class VeranstaltungGW {

	private int veranstNr;
	private String bezeichnung;
	private int fachsemester;

	public VeranstaltungGW(int veranstNr, String bezeichnung, int fachsemester) {
		super();
		this.veranstNr = veranstNr;
		this.bezeichnung = bezeichnung;
		this.fachsemester = fachsemester;
	}

	public int getVeranstNr() {
		return veranstNr;
	}

	public void setVeranstNr(int veranstNr) {
		this.veranstNr = veranstNr;
	}

	public String getBezeichnung() {
		return bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

	public int getFachsemester() {
		return fachsemester;
	}

	public void setFachsemester(int fachsemester) {
		this.fachsemester = fachsemester;
	}

	// insert/update/delete

}
