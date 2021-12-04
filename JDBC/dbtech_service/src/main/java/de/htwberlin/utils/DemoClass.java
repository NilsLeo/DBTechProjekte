package de.htwberlin.utils;
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

public class DemoClass {
    public static void demoMethod(int achszahl) {

//1) Treiber laden
        JdbcUtils.loadDriver("oracle.jdbc.driver.OracleDriver");
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String queryString = "SELECT SUM( ANZAHL ) AS ANZAHL FROM (SELECT COUNT(ACHSEN) AS ANZAHL FROM FAHRZEUG"
                + "WHERE ACHSEN = ? UNION ALL SELECT COUNT(ACHSZAHL) AS ANZAHL FROM MAUTKATEGORIE WHERE ACHSZAHL = ?)";
        try {

//2)Verbindung (Connection) herstellen- Connection con = DriverManager.getConnection(url, username, password)
            con = DriverManager.getConnection(DbCred.url, DbCred.user, DbCred.password);

            // 3) SQL-Anweisung (Statement) erzeugen und Parameter setzen
            preparedStatement = con.prepareStatement(queryString);
            preparedStatement.setInt(1, achszahl);
            preparedStatement.setString(2, "= "+ achszahl);
//4)SQL-Anweisung ausführen
            resultSet = preparedStatement.executeQuery();

//5)Ergebnismenge (ResultSet) verarbeiten
            while (resultSet.next()) {
                Long Anzahl = resultSet.getLong(1);
                System.out.println("Anzahl" + Anzahl);
            }

        } catch (SQLException e) {
            // Ausnahmebehandlung
        } finally {
            // 6. Ganz wichtig: Freigeben der Ressourcen. Wird wegen
            // finally auf jeden Fall durchgeführt, d. h. auch beim
            // Auftreten von Ausnahmen
            JdbcUtils.closeResultSetQuietly(resultSet);
            JdbcUtils.closeStatementQuietly(preparedStatement);
            JdbcUtils.closeConnectionQuietly(con);
        }
    }

    public static void main(String[] args) {
        demoMethod(4);
    }
}
