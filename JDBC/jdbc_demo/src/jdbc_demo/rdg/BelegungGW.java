package jdbc_demo.rdg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class BelegungGW {

	private int matrNr;
	private int veranstNr;
	private String status;
	private Date datum;

	public BelegungGW(int matrNr, int veranstNr, String status, Date datum) {
		super();
		this.matrNr = matrNr;
		this.veranstNr = veranstNr;
		this.status = status;
		this.datum = datum;
	}

	public int getMatrNr() {
		return matrNr;
	}

	public void setMatrNr(int matrNr) {
		this.matrNr = matrNr;
	}

	public int getVeranstNr() {
		return veranstNr;
	}

	public void setVeranstNr(int veranstNr) {
		this.veranstNr = veranstNr;
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

	// insert
	public void insert(Connection connection) {
		try {
			try (PreparedStatement sb = connection
					.prepareStatement("INSERT INTO Belegung(MatrNr,VeranstNr,Status,Datum) VALUES (?,?,?,?)")) {
				sb.setInt(1, matrNr);
				sb.setInt(2, veranstNr);
				sb.setString(3, status);
				sb.setDate(4, new java.sql.Date(datum.getTime()));
				sb.executeUpdate();
			}
		} catch (SQLException exp) {
			throw new RuntimeException();
		}
	}
	// update
	// delete
}
