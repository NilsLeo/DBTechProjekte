package de.htwberlin;
/*
1) Treiber laden
2) Verbindung (Connection) herstellen
3) SQL-Anweisung (Statement) erzeugen und Parameter setzen
4) SQL-Anweisung ausführen
5) Ergebnismenge (ResultSet) verarbeiten
6) Schließen der Ergebnismenge, des SQL-Befehls und der Verbindung
 */

//1)Import the package

import java.sql.*;
import de.htwberlin.utils.DbCred;
import de.htwberlin.utils.JdbcUtils;

public class DemoClass {
    public static void demoMethod() {

//1) Treiber laden
        JdbcUtils.loadDriver("oracle.jdbc.driver.OracleDriver");
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql ="SELECT Vorname, Nachname FROM Nutzer WHERE Firmenname = ?";

        try {

//2)Verbindung (Connection) herstellen- Connection con = DriverManager.getConnection(url, username, password)
            con = DriverManager.getConnection(DbCred.url, DbCred.user, DbCred.password);

            // 3) SQL-Anweisung (Statement) erzeugen und Parameter setzen
            pst = con.prepareStatement(sql);
            pst.setString(1, "Coca-Cola Erfrischungsgetraenke AG");
//4)SQL-Anweisung ausführen
            rs = pst.executeQuery();

//5)Ergebnismenge (ResultSet) verarbeiten
            while (rs.next()) {
                String CocaColaMitarbeiterName = rs.getString(1) + " " + rs.getString(2); // returns String value in column
                // 1 and string value in column
                // 2
                System.out.println(CocaColaMitarbeiterName);
            }

        } catch (SQLException e) {
            // Ausnahmebehandlung
        } finally {
            // 6. Ganz wichtig: Freigeben der Ressourcen. Wird wegen
            // finally auf jeden Fall durchgeführt, d. h. auch beim
            // Auftreten von Ausnahmen
            JdbcUtils.closeResultSetQuietly(rs);
            JdbcUtils.closeStatementQuietly(pst);
            JdbcUtils.closeConnectionQuietly(con);
        }
    }

    public static void main(String[] args) {
        demoMethod();
    }
}